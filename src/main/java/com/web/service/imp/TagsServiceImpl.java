package com.web.service.imp;


import com.web.entity.DayTotalData;
import com.web.entity.EventType;
import com.web.entity.Tag;
import com.web.persistence.DayTotalRepository;
import com.web.persistence.custom.CustomDAO;
import com.web.service.*;
import com.web.wrapper.comport.ComPortDataWrapper;
import com.web.wrapper.comport.ComPortDataWriteWrapper;
import com.web.wrapper.response.StatisticWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created on 28.10.15.
 */
@Service
public class TagsServiceImpl implements TagsService {

    //speed will be calculated according to this interval value
    public static final int scheduledPeriod = 5000;
    private static final Logger logger = Logger.getLogger(TagsServiceImpl.class);
    @Value("${app.connect.com.port}")
    private boolean isConnectComPort;
    private boolean isFirstStart = true;

    private AtomicBoolean isReadInProcess = new AtomicBoolean(false);

    private ExecutorService comPortReaderExecutorService = Executors.newSingleThreadExecutor();

    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private ArchiveService archiveService;
    @Autowired
    private DataProvider dataProvider;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DayTotalRepository dayTotalRepository;
    @Autowired
    private CustomDAO customDAO;


    @Scheduled(fixedRate = scheduledPeriod)
    private void fixedRateTask() {

        try {
            if (isReadInProcess.compareAndSet(false, true)) {
                saveCurrentTag();
                new Thread(() -> {
                    try {
                        Set<String> roles = new HashSet<>();
                        roles.add("ROLE_SERVER");
                        usersService.getAsUser("server", roles);

                        webSocketService.broadcastCurrentData(archiveService.getAllCurrentViewData());
                    } catch (Exception ex) {
                        logger.error("Websocket error - " + ex.getMessage());
                    }
                }).start();

            } else {
                logger.error("Preview process haven't been finished");
            }
        } catch (Exception ex) {
            logger.error("fixed rate task error - " + ex.getMessage());
        } finally {
            isReadInProcess.set(false);
        }
    }

    public void saveCurrentTag() {

        Tag formerTag = archiveService.getLastTag();
        Tag formerTagConnectionOK = archiveService.getLastTagConnectionOK();
        ComPortDataWriteWrapper dataWrite = new ComPortDataWriteWrapper();

        boolean isNewDay = !archiveService.isDayTheSame(formerTag);

        boolean isCountersMainModuleReseted = false;
        if (isNewDay) {
            archiveService.mainModuleCountersReset();
            isCountersMainModuleReseted = true;

            Calendar calDate = Calendar.getInstance();
            calDate.setTimeInMillis(new Date().getTime());
            int currentDayMonth = calDate.get(Calendar.DAY_OF_MONTH);
            calDate.set(Calendar.DAY_OF_MONTH, currentDayMonth - 1);
            try {
                DayTotalData dayTotalData = fillTotalDataLastDay(calDate);
                dayTotalRepository.save(dayTotalData);
            } catch (Exception e) {
                logger.error("Total data for this day already present in table, date - " + calDate.toString());
            }
        }

        ComPortDataWrapper comPortDataCurrent;
        if (isConnectComPort) {
            Callable<ComPortDataWrapper> reader = () -> dataProvider.loadTagsData( dataWrite);
            try {
                Future<ComPortDataWrapper> readerResult = comPortReaderExecutorService.submit(reader);
                comPortDataCurrent = readerResult.get();
            } catch (Exception e) {
                comPortDataCurrent = new ComPortDataWrapper();
            }


        } else {
            comPortDataCurrent = new ComPortDataWrapper();
        }

        Tag tag = archiveService.saveNewTag(comPortDataCurrent);
        if (tag == null) {
            throw new RuntimeException("Error when create new Tag");
        }

        if (this.isFirstStart) {
            archiveService.saveEvent(EventType.COMP_TURN_ON.name(), tag);
        }

        archiveService.saveCommonEvents(tag, formerTag, isNewDay, isCountersMainModuleReseted);

        if (tag.getConnectionOk()) {
            archiveService.saveMainModuleEvents(tag, formerTagConnectionOK, isNewDay);
        }

        isFirstStart = false;


    }

    private DayTotalData fillTotalDataLastDay(Calendar calDate) {
        DayTotalData dayTotalData = new DayTotalData();


        StatisticWrapper statisticWrapperLine1 = customDAO.getDayStatistic("line1", calDate.getTime().getTime());
        StatisticWrapper statisticWrapperLine2 = customDAO.getDayStatistic("line2", calDate.getTime().getTime());

        dayTotalData.setDowntimeLine1(statisticWrapperLine1.getDowntime());
        dayTotalData.setExpenditureOfMaterialLine1(statisticWrapperLine1.getExpenditureOfMaterial());
        dayTotalData.setPeriodWorkWithMaterialLine1(statisticWrapperLine1.getPeriodWorkWithMaterial());
        dayTotalData.setTurnOffTimeLine1(statisticWrapperLine1.getTurnOffTime());
        dayTotalData.setTurnOnTimeTodayLine1(statisticWrapperLine1.getTurnOnTime());
        dayTotalData.setAverageSpeed1(statisticWrapperLine1.getAverageSpeed());
        dayTotalData.setTimePowerOff1(statisticWrapperLine1.getTimePowerOff());


        dayTotalData.setDowntimeLine2(statisticWrapperLine2.getDowntime());
        dayTotalData.setExpenditureOfMaterialLine2(statisticWrapperLine2.getExpenditureOfMaterial());
        dayTotalData.setPeriodWorkWithMaterialLine2(statisticWrapperLine2.getPeriodWorkWithMaterial());
        dayTotalData.setTurnOffTimeLine2(statisticWrapperLine2.getTurnOffTime());
        dayTotalData.setTurnOnTimeTodayLine2(statisticWrapperLine2.getTurnOnTime());
        dayTotalData.setAverageSpeed2(statisticWrapperLine2.getAverageSpeed());
        dayTotalData.setTimePowerOff2(statisticWrapperLine2.getTimePowerOff());

        dayTotalData.setTimeStamp(new Date(calDate.getTime().getTime()));

        return dayTotalData;
    }

}
