package com.web.service.imp;


import com.web.entity.ModuleCounter;
import com.web.exception.ComportException;
import com.web.persistence.ModuleCountersRepository;
import com.web.service.DataProvider;
import com.web.wrapper.comport.ComPortDataWrapper;
import com.web.wrapper.comport.ComPortDataWriteWrapper;
import jssc.SerialPort;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created on 8/4/16.
 */
@Service
public class DataProviderImpl implements DataProvider {

    private static final Logger logger = Logger.getLogger(DataProviderImpl.class);

    @Autowired
    ModuleCountersRepository moduleCountersRepository;

    public static final Long MAIN_MODULE_ID = 1L;

    @Value("${comport.name}")
    private String serialName;



    @Override
    public synchronized ComPortDataWrapper loadTagsData(ComPortDataWriteWrapper dataWrite) {

        SerialPort serial = new SerialPort(this.serialName);
        ComPortDataWrapper readResult = new ComPortDataWrapper();
        readResult.setConnectionComPortOk(false);

        try {
            openPort(serial);

            readResult.setConnectionComPortOk(true);

            askInputsCurrentStatus(serial);
            byte[] result = handleResponse(serial, 7, 50);
            logger.debug("Ask inputs amount = " + result.length);
            fillInputsCurrentStatus(readResult, result);

            serial.purgePort(SerialPort.PURGE_RXCLEAR);
            serial.purgePort(SerialPort.PURGE_TXCLEAR);
            try {
                Thread.sleep(5L);
            } catch (InterruptedException ex) {}

            askCountersValues(serial);
            result = handleResponse(serial, 37, 150);
            logger.debug("Ask counters amount = " + result.length);
            fillCountersValues(readResult, result);

        } catch (Exception e) {
            logger.error("Read COM port error, " + e.getMessage(), e);
            readResult = new ComPortDataWrapper();
            readResult.setConnectionComPortOk(false);
        } finally {
            try {
                if (serial.isOpened()) {
                    logger.debug("Try to close COM port");
                    serial.closePort();
                }

            } catch (Exception e) {
                logger.error("Cant't close COM port error, " + e.getMessage(), e);
            }

        }
        return readResult;
    }

    private void openPort(SerialPort serial) {
        try {
            logger.debug("Start open COM port name = " + this.serialName);

            serial.openPort();

            serial.setParams(SerialPort.BAUDRATE_19200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);


            logger.debug("Opened COM port name = " + this.serialName);

        } catch (Exception e) {
            logger.error("Error Open COM port after init", e);
            throw new ComportException("Error Open COM port after init");
        }
    }

    private byte[] handleResponse(SerialPort serial, int amount, int waitTime) {
        try {

            logger.debug("Start Read tags from COM port, expected amount = " + amount + ", timeout = " + waitTime);
            byte[] inputBytes = serial.readBytes(amount, waitTime);

            if (inputBytes.length == 0) {
                throw new ComportException("Empty response from tag board");
            }


            if (checkCRC(inputBytes, inputBytes.length)) {
                return inputBytes;
            } else {
                throw new ComportException("Read COM port CRC error, amount of Response bytes = " + inputBytes.length);
            }
        } catch (Exception ex) {
            try {
                logger.error("Read COM port error - Exception: " + ex.getMessage() + ", byte amount" + serial.getInputBufferBytesCount());
            }catch (Exception e){}
            throw new ComportException("Read COM port error - Exception: " + ex.getMessage());
        }
    }

    private void askInputsCurrentStatus(SerialPort serial) {
        ArrayList<Character> byteArrayWriteComport = new ArrayList<>();

        byteArrayWriteComport.add((char) 16);
        byteArrayWriteComport.add((char) 3);
        byteArrayWriteComport.add((char) 0);
        byteArrayWriteComport.add((char) 51);
        byteArrayWriteComport.add((char) 0);
        byteArrayWriteComport.add((char) 1);

        writeDataToPort(serial, byteArrayWriteComport);
    }

    private void fillInputsCurrentStatus(ComPortDataWrapper readResult, byte[] bytes) {
        char digitalInputs = (char) bytes[4];

        readResult.setLine1OnOff(getBitValue(digitalInputs, (char) 0x02));
        readResult.setLine2OnOff(getBitValue(digitalInputs, (char) 0x20));

        readResult.setPowerOn(getBitValue(digitalInputs, (char) 0x08));

        readResult.setWithMaterialLine1(getBitValue(digitalInputs, (char) 0x04));
        readResult.setWithMaterialLine2(getBitValue(digitalInputs, (char) 0x40));

        //digitalInputs = (char) bytes[3];


    }

    private void askCountersValues(SerialPort serial) {
        ArrayList<Character> byteArrayWriteComport = new ArrayList<>();

        byteArrayWriteComport.add((char) 16);
        byteArrayWriteComport.add((char) 3);
        byteArrayWriteComport.add((char) 0);
        byteArrayWriteComport.add((char) 64);
        byteArrayWriteComport.add((char) 0);
        byteArrayWriteComport.add((char) 16);

        writeDataToPort(serial, byteArrayWriteComport);
    }

    private void fillCountersValues(ComPortDataWrapper readResult, byte[] bytes) {
        //In module counter changes from 0 to 65535 with every impulse and after that it starts from 0 again.
        //we saving the former counter value and total amount impulses for today
        int impulseCounter1 = bytesToInt(bytes[4], bytes[3]);
        int impulseCounter5 = bytesToInt(bytes[12], bytes[11]);

        ModuleCounter mainModuleCounters = moduleCountersRepository.findOne(MAIN_MODULE_ID);
        if (mainModuleCounters == null) {
            mainModuleCounters = moduleCountersRepository.save(new ModuleCounter(MAIN_MODULE_ID, impulseCounter1, impulseCounter5, 0L, 0L));
        }

        int differenceCounterLine1 = getDifferenceModuleCounter(mainModuleCounters.getCounterI1(), impulseCounter1);
        readResult.setDifferenceCounterLine1(differenceCounterLine1);
        Long totalImpulseCounterI1;
        if (readResult.isWithMaterialLine1()) {
            totalImpulseCounterI1 = mainModuleCounters.getTotalCounterI1() + differenceCounterLine1;
        } else {
            totalImpulseCounterI1 = mainModuleCounters.getTotalCounterI1();
        }

        int differenceCounterLine5 = getDifferenceModuleCounter(mainModuleCounters.getCounterI5(), impulseCounter5);
        readResult.setDifferenceCounterLine2(differenceCounterLine5);
        Long totalImpulseCounterI5;
        if (readResult.isWithMaterialLine2()) {
            totalImpulseCounterI5 = mainModuleCounters.getTotalCounterI5() + differenceCounterLine5;
        } else {
            totalImpulseCounterI5 = mainModuleCounters.getTotalCounterI5();
        }

        readResult.setImpulseCounterLine1(totalImpulseCounterI1);
        readResult.setImpulseCounterLine2(totalImpulseCounterI5);

        moduleCountersRepository.save(new ModuleCounter(MAIN_MODULE_ID, impulseCounter1, impulseCounter5, totalImpulseCounterI1, totalImpulseCounterI5));
    }

    private int getDifferenceModuleCounter(Integer moduleCounter, Integer impulseCounter) {
        int differenceCounter;

        if (impulseCounter >= moduleCounter) {
            differenceCounter = impulseCounter - moduleCounter;
        } else {
            differenceCounter = (65535 - moduleCounter) + impulseCounter;
        }

        return differenceCounter;
    }

    private void writeDataToPort(SerialPort serial, ArrayList<Character> byteArrayWriteComport) {

        char crc = getCRC(byteArrayWriteComport.toArray(new Character[0]), byteArrayWriteComport.size(), 0);
        byteArrayWriteComport.add((char) (crc & 0xFF));
        byteArrayWriteComport.add((char) ((crc & 0XFF00) >> 8));


        final byte[] outputBytes = toPrimitives(byteArrayWriteComport.toArray(new Character[0]));
        try {
            logger.debug("Start Write to COM port, start time = " + System.currentTimeMillis());

            serial.writeBytes(outputBytes);

            logger.debug("Finish Write to COM port, finish time = " + System.currentTimeMillis());


        } catch (Throwable ex) {
            logger.error("Hardware error - Write COM port, ", ex);
        }

    }

    private char updateCRC(char CurByte, char CurCrc) {
        boolean carry;
        CurCrc = (char) (CurByte ^ CurCrc);
        for (int i = 0; i <= 7; i++) {
            carry = (CurCrc & 1) != 0;
            CurCrc = (char) (CurCrc >> 1);
            if (carry) CurCrc = (char) (0xA001 ^ CurCrc);
        }
        return CurCrc;
    }

    private char getCRC(Character[] bytes, int n, int StartAdres) {
        char res = 0xFFFF;
        int LastByte;
        LastByte = n + StartAdres;
        for (int j = StartAdres; j < LastByte; j++) {
            char b = bytes[j];
            res = updateCRC(b, res);
        }
        return res;
    }

    private boolean checkCRC(byte[] bytes, int Number) {
        Character[] buffer = bytesInChar(bytes);

        char crcPr = getCRC(buffer, Number - 2, 0);
        return (crcPr & 0x00FF) == buffer[Number - 2] && ((crcPr & 0XFF00) >> 8) == buffer[Number - 1];

    }

    private Character[] bytesInChar(byte[] bytes) {
        Character[] buffer = new Character[bytes.length];
        for (int j = 0; j < buffer.length; j++) {
            buffer[j] = (char) (bytes[j] & 0x00FF);
        }

        return buffer;
    }

    private byte[] toPrimitives(Character[] characters) {

        byte[] bytes = new byte[characters.length];

        for (int i = 0; i < characters.length; i++) {
            bytes[i] = (byte) characters[i].charValue();
        }

        return bytes;
    }

    private boolean getBitValue(char digitalInputs, char mask) {
        return (digitalInputs & mask) > 0;
    }

    private long bytesToLong(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result <<= 8;
            result |= (bytes[i] & 0xFF);
        }
        return result;
    }

    private int bytesToInt(byte lowByte, byte highByte) {
        int result = 0;
        result <<= 8;
        result |= (highByte & 0xFF);
        result <<= 8;
        result |= (lowByte & 0xFF);

        return result;
    }

}
