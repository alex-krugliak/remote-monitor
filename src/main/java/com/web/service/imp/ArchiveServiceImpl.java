package com.web.service.imp;

import com.web.entity.*;
import com.web.persistence.EventRepository;
import com.web.persistence.MaterialExpenditureRepository;
import com.web.persistence.ModuleCountersRepository;
import com.web.persistence.TagRepository;
import com.web.persistence.custom.CustomDAO;
import com.web.service.ArchiveService;
import com.web.wrapper.comport.ComPortDataWrapper;
import com.web.wrapper.dao.ExpenditureWrapper;
import com.web.wrapper.dao.TagTimestampWrapper;
import com.web.wrapper.response.DataCurrentWrapper;
import com.web.wrapper.response.StatisticWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created on 21.06.16.
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {

    private static final Logger logger = Logger.getLogger(ArchiveServiceImpl.class);

    @Autowired
    private CustomDAO customDAO;
    @Autowired
    private TagRepository tagsRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    ModuleCountersRepository moduleCountersRepository;
    @Autowired
    private MaterialExpenditureRepository materialExpenditureRepository;

    private static Map<String, Double> coefficients;

    static {
        coefficients = BundlesServiceImpl.getAllCoefficient("/coefficients.properties");
    }

    @Transactional(readOnly = true)
    @Override
    @Secured("ROLE_USER")
    public List<StatisticWrapper> getStatistic(String wallpaperLineName, Long dateFrom, Long dateTo) {
        List<StatisticWrapper> statisticList;

        Calendar calDateFrom = Calendar.getInstance();
        calDateFrom.setTimeInMillis(dateFrom);

        Calendar calDateNow = Calendar.getInstance();
        calDateNow.setTimeInMillis(new Date().getTime());

        if ((calDateFrom.get(Calendar.MONTH) == calDateNow.get(Calendar.MONTH)) &&
                (calDateFrom.get(Calendar.YEAR) == calDateNow.get(Calendar.YEAR))) {
            statisticList = getStatisticCurrentMonth(calDateNow, wallpaperLineName);
        } else {
            statisticList = getStatisticForMonth(calDateFrom, wallpaperLineName);

        }

        return statisticList;
    }

    private List<StatisticWrapper> getStatisticCurrentMonth(Calendar calDateCurrent, String wallpaperLineName) {
        List<StatisticWrapper> statisticList = new ArrayList<>();

        int myDayMonth = calDateCurrent.get(Calendar.DAY_OF_MONTH);
        calDateCurrent.set(Calendar.DAY_OF_MONTH, 1);

        for (int i = calDateCurrent.get(Calendar.DAY_OF_MONTH); i <= myDayMonth; i++) {
            statisticList.add(customDAO.getDayStatistic(wallpaperLineName, calDateCurrent.getTime().getTime()));

            calDateCurrent.add(Calendar.DAY_OF_MONTH, 1);
        }

        return statisticList;
    }

    private List<StatisticWrapper> getStatisticForMonth(Calendar calDateFrom, String wallpaperLineName) {
        List<StatisticWrapper> statisticList = new ArrayList<>();

        int amountOfDays = calDateFrom.getActualMaximum(Calendar.DAY_OF_MONTH);

        calDateFrom.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < amountOfDays; i++) {
            statisticList.add(customDAO.getDayStatistic(wallpaperLineName, calDateFrom.getTime().getTime()));

            calDateFrom.add(Calendar.DAY_OF_MONTH, 1);
        }

        return statisticList;
    }

    @Transactional(readOnly = true)
    @Override
    @Secured({"ROLE_USER", "ROLE_SERVER"})
    public DataCurrentWrapper getAllCurrentViewData() {
        DataCurrentWrapper dataCurrentLine = customDAO.getCurrentDataWrapper();

        if (dataCurrentLine == null) {
            return null;
        }
        Long now = new Date().getTime();

        TagTimestampWrapper timeLine1OnToday = customDAO.getTimeFirstEventForDay(EventType.LINE1_ON.name(), now);
        TagTimestampWrapper timeLine1OFFtoday = customDAO.getTimeLastEventForDay(EventType.LINE1_OFF.name(), now);

        ExpenditureWrapper expenditureTodayLine1 = customDAO.getExpenditureLine1(now);

        if (expenditureTodayLine1 != null) {
            dataCurrentLine.setExpenditureOfMaterialLine1(expenditureTodayLine1.getExpenditure());
        }
        if (timeLine1OnToday != null) {
            dataCurrentLine.setTurnOnTimeTodayLine1(timeLine1OnToday.getTimestamp());
        }
        if (timeLine1OFFtoday != null && !dataCurrentLine.getLine1OnOff()) {
            dataCurrentLine.setTurnOffTimeLine1(timeLine1OFFtoday.getTimestamp());
        }


        dataCurrentLine.setPeriodWorkWithMaterialLine1(customDAO.getTimeWorkWithMaterialForDayLine1(now).getTagsamount() * TagsServiceImpl.scheduledPeriod);
        dataCurrentLine.setDowntimeLine1(customDAO.getDownTimeForDayLine1(now).getTagsamount() * TagsServiceImpl.scheduledPeriod);


        TagTimestampWrapper timeLine2OnToday = customDAO.getTimeFirstEventForDay(EventType.LINE2_ON.name(), now);
        TagTimestampWrapper timeLine2OFFtoday = customDAO.getTimeLastEventForDay(EventType.LINE2_OFF.name(), now);

        ExpenditureWrapper expenditureTodayLine2 = customDAO.getExpenditureLine2(now);

        if (expenditureTodayLine2 != null) {
            dataCurrentLine.setExpenditureOfMaterialLine2(expenditureTodayLine2.getExpenditure());
        }
        if (timeLine2OnToday != null) {
            dataCurrentLine.setTurnOnTimeTodayLine2(timeLine2OnToday.getTimestamp());
        }
        if (timeLine2OFFtoday != null && !dataCurrentLine.getLine2OnOff()) {
            dataCurrentLine.setTurnOffTimeLine2(timeLine2OFFtoday.getTimestamp());
        }

        dataCurrentLine.setPeriodWorkWithMaterialLine2(customDAO.getTimeWorkWithMaterialForDayLine2(now).getTagsamount() * TagsServiceImpl.scheduledPeriod);
        dataCurrentLine.setDowntimeLine2(customDAO.getDownTimeForDayLine2(now).getTagsamount() * TagsServiceImpl.scheduledPeriod);

        dataCurrentLine.setTimePowerOff(customDAO.getTimePowerOff(now));
        return dataCurrentLine;
    }


    @Transactional
    @Override
    public Tag saveNewTag(ComPortDataWrapper comPortDataCurrent) {


        Tag tag = new Tag();

        tag.setTimeStamp(new Date());
        tag.setConnectionOk(comPortDataCurrent.getConnectionComPortOk());

        Double coefficientSpeedLine1 = coefficients.get("coefficientSpeedLine1");
        Double coefficientSpeedLine2 = coefficients.get("coefficientSpeedLine2");
        Double coefficientExpenditureOfMaterialLine1 = coefficients.get("coefficientExpenditureOfMaterialLine1");
        Double coefficientExpenditureOfMaterialLine2 = coefficients.get("coefficientExpenditureOfMaterialLine2");

        tag.setLine1OnOff(comPortDataCurrent.isLine1OnOff());
        tag.setWithMaterialLine1(comPortDataCurrent.isWithMaterialLine1());
        tag.setExpenditureOfMaterialLine1(comPortDataCurrent.getImpulseCounterLine1() * coefficientExpenditureOfMaterialLine1);

        int period = TagsServiceImpl.scheduledPeriod;

        Double speedLine1 = comPortDataCurrent.getDifferenceCounterLine1() * coefficientSpeedLine1 * 60000 / period;
        logger.debug("DifferenceCounterLine1 =" + comPortDataCurrent.getDifferenceCounterLine1() + ", coefficientSpeedLine1 = " +
                coefficientSpeedLine1 + ", 60000/period = " + 60000 / period + ", speed = " + speedLine1 + " m/min");

        Double speedLine2 = comPortDataCurrent.getDifferenceCounterLine2() * coefficientSpeedLine2 * 60000 / period;
        logger.debug("DifferenceCounterLine2 =" + comPortDataCurrent.getDifferenceCounterLine2() + ", coefficientSpeedLine2 = " +
                coefficientSpeedLine2 + ", 60000/period = " + 60000 / period + ", speed = " + speedLine2 + " m/min");

        if (comPortDataCurrent.isWithMaterialLine1()) {
            tag.setCurrentSpeedLine1(speedLine1);

        } else {
            tag.setCurrentSpeedLine1(0.0);
        }

        tag.setLine2OnOff(comPortDataCurrent.isLine2OnOff());
        tag.setWithMaterialLine2(comPortDataCurrent.isWithMaterialLine2());
        tag.setExpenditureOfMaterialLine2(comPortDataCurrent.getImpulseCounterLine2() * coefficientExpenditureOfMaterialLine2);
        if (comPortDataCurrent.isWithMaterialLine2()) {
            tag.setCurrentSpeedLine2(speedLine2);
        } else {
            tag.setCurrentSpeedLine2(0.0);
        }

        tag.setPowerOk(comPortDataCurrent.getPowerOn());
        tag = tagsRepository.save(tag);

        if (comPortDataCurrent.getConnectionComPortOk()) {
            saveExpenditure(comPortDataCurrent);
        }

        return tag;
    }

    private MaterialExpenditure getTodayMaterialExpenditure() {
        try {
            Date now = new Date();
            MaterialExpenditure materialExpenditure = customDAO.getExpenditureForDay(now.getTime());

            if (materialExpenditure == null) {
                materialExpenditure = new MaterialExpenditure();
                materialExpenditure.setTimestamp(now);
                materialExpenditure.setExpenditureline1(0.0);
                materialExpenditure.setExpenditureline2(0.0);

            }

            return materialExpenditure;
        } catch (Exception e) {
            logger.debug("error" + e.getMessage(), e);
            return null;
        }
    }

    private void saveExpenditure(ComPortDataWrapper comPortDataCurrent) {

        Double coefficientExpenditureOfMaterialLine1 = coefficients.get("coefficientExpenditureOfMaterialLine1");
        Double coefficientExpenditureOfMaterialLine2 = coefficients.get("coefficientExpenditureOfMaterialLine2");


        MaterialExpenditure materialExpenditureToday = getTodayMaterialExpenditure();
        materialExpenditureToday.setExpenditureline1(comPortDataCurrent.getImpulseCounterLine1() * coefficientExpenditureOfMaterialLine1);
        materialExpenditureToday.setExpenditureline2(comPortDataCurrent.getImpulseCounterLine2() * coefficientExpenditureOfMaterialLine2);

        materialExpenditureRepository.save(materialExpenditureToday);

    }

    @Transactional
    @Override
    public void saveMainModuleEvents(Tag tag, Tag formerTagConnectionOK, boolean isNewDay) {

        if (tag.getLine1OnOff() && (!formerTagConnectionOK.getLine1OnOff() || isNewDay)) {
            saveEvent(EventType.LINE1_ON.name(), tag);
        }
        if (!tag.getLine1OnOff() && formerTagConnectionOK.getLine1OnOff()) {
            saveEvent(EventType.LINE1_OFF.name(), tag);
        }
        if (tag.getWithMaterialLine1() && (!formerTagConnectionOK.getWithMaterialLine1() || isNewDay)) {
            saveEvent(EventType.MATERIAL_ON_LINE1.name(), tag);
        }
        if (!tag.getWithMaterialLine1() && formerTagConnectionOK.getWithMaterialLine1()) {
            saveEvent(EventType.MATERIAL_OFF_LINE1.name(), tag);
        }
        if (tag.getLine2OnOff() && (!formerTagConnectionOK.getLine2OnOff() || isNewDay)) {
            saveEvent(EventType.LINE2_ON.name(), tag);
        }
        if (!tag.getLine2OnOff() && formerTagConnectionOK.getLine2OnOff()) {
            saveEvent(EventType.LINE2_OFF.name(), tag);
        }
        if (tag.getWithMaterialLine2() && (!formerTagConnectionOK.getWithMaterialLine2() || isNewDay)) {
            saveEvent(EventType.MATERIAL_ON_LINE2.name(), tag);
        }
        if (!tag.getWithMaterialLine2() && formerTagConnectionOK.getWithMaterialLine2()) {
            saveEvent(EventType.MATERIAL_OFF_LINE2.name(), tag);
        }
//        if (!comPortDataCurrent.getPowerOn() && comPortFormer.getPowerOn()) {
//            saveEvent(EventType.POWER_FAIL.name(), tag);
//        }
//        if (comPortDataCurrent.getPowerOn() && !comPortFormer.getPowerOn()) {
//            saveEvent(EventType.POWER_ON.name(), tag);
//        }


    }

    @Transactional
    @Override
    public void saveCommonEvents(Tag tag, Tag formerTag, boolean isNewDay, boolean isCountersMainModuleReseted) {
        if (tag.getConnectionOk() && !formerTag.getConnectionOk()) {
            saveEvent(EventType.CONNECTION_UP.name(), tag);
        }
        if (!tag.getConnectionOk() && formerTag.getConnectionOk()) {
            saveEvent(EventType.CONNECTION_DOWN.name(), tag);
        }

        if (isNewDay) {
            saveEvent(EventType.NEW_DAY.name(), tag);
        }

        if (isCountersMainModuleReseted) {
            saveEvent(EventType.COUNTER_LINE1_WAS_RESET_NEW_DAY.name(), tag);
            saveEvent(EventType.COUNTER_LINE2_WAS_RESET_NEW_DAY.name(), tag);
        }
    }

    @Transactional
    @Override
    public Event saveEvent(String description, Tag tag) {
        Event event = new Event();
        event.setTag(tag);
        event.setDescription(description);

        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    @Override
    public Tag getLastTag() {
        return tagsRepository.findLastTag();
    }

    @Transactional(readOnly = true)
    @Override
    public Tag getLastTagConnectionOK() {
        return tagsRepository.findLastTagConnectionOK();
    }

    @Transactional
    @Override
    public void mainModuleCountersReset() {
        ModuleCounter mainModuleCounters = moduleCountersRepository.findOne(DataProviderImpl.MAIN_MODULE_ID);
        if (mainModuleCounters == null) {
            logger.error("Counters for main module haven't been saved yet");
            return;
        }
        mainModuleCounters.setTotalCounterI1(0L);
        mainModuleCounters.setTotalCounterI5(0L);
        moduleCountersRepository.save(mainModuleCounters);

    }

    public boolean isDayTheSame(Tag lastTag) {

        Date lastTagTimeStamp = lastTag.getTimeStamp();

        Calendar cal = Calendar.getInstance();
        cal.setTime(lastTagTimeStamp);
        int lastTagDay = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(new Date());
        int nowDay = cal.get(Calendar.DAY_OF_MONTH);

        return nowDay == lastTagDay;
    }


}
