package com.web.persistence.custom;


import com.web.entity.EventType;
import com.web.entity.MaterialExpenditure;
import com.web.service.imp.TagsServiceImpl;
import com.web.wrapper.dao.AverageSpeedWrapper;
import com.web.wrapper.dao.ExpenditureWrapper;
import com.web.wrapper.dao.TagTimestampWrapper;
import com.web.wrapper.dao.TagsCounterWrapper;
import com.web.wrapper.response.DataCurrentWrapper;
import com.web.wrapper.response.StatisticWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created on 21.06.16.
 */
@Service
public class CustomDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    EntityManager entityManager;

    @Value("${JPQL.CURRENT.DATA}")
    private String JPQL_CURRENT_DATA;

    @Value("${JPQL.FIRST.EVENT.DAY}")
    private String JPQL_FIRST_EVENT_DAY;

    @Value("${JPQL.LAST.EVENT.DAY}")
    private String JPQL_LAST_EVENT_DAY;

    @Value("${JPQL.PERIOD.WITH.MATERIAL.LINE1}")
    private String JPQL_PERIOD_WITH_MATERIAL_LINE1;

    @Value("${JPQL.PERIOD.WITH.MATERIAL.LINE2}")
    private String JPQL_PERIOD_WITH_MATERIAL_LINE2;

    @Value("${JPQL.DOWN.TIME.LINE1}")
    private String JPQL_DOWN_TIME_LINE1;

    @Value("${JPQL.DOWN.TIME.LINE2}")
    private String JPQL_DOWN_TIME_LINE2;

    @Value("${JPQL.AVR.SPEED.LINE1}")
    private String JPQL_AVR_SPEED_LINE1;

    @Value("${JPQL.AVR.SPEED.LINE2}")
    private String JPQL_AVR_SPEED_LINE2;

    @Value("${JPQL.EXPENDITURE.DAY.LINE1}")
    private String JPQL_EXPENDITURE_DAY_LINE1;

    @Value("${JPQL.EXPENDITURE.DAY.LINE2}")
    private String JPQL_EXPENDITURE_DAY_LINE2;

    @Value("${JPQL.EXPENDITURE.DAY}")
    private String JPQL_EXPENDITURE_DAY;

    @Value("${JPQL.TAG.AMOUNT.POWER.OK}")
    private String JPQL_TAG_AMOUNT_POWER_OK;

    @Transactional(readOnly = true)
    public DataCurrentWrapper getCurrentDataWrapper() {
        List<DataCurrentWrapper> dataCurrentList = jdbcTemplate.query(JPQL_CURRENT_DATA, new DataCurrentRowMapper());
        return dataCurrentList.isEmpty() ? null : dataCurrentList.get(0);
    }


    @Transactional(readOnly = true)
    public TagTimestampWrapper getTimeFirstEventForDay(String eventType, Long date) {
        List<TagTimestampWrapper> timestamps = jdbcTemplate.query(JPQL_FIRST_EVENT_DAY, new TagTimestampRowMapper(), eventType, new Date(date));
        return timestamps.isEmpty() ? null : timestamps.get(0);
    }

    @Transactional(readOnly = true)
    public TagTimestampWrapper getTimeLastEventForDay(String eventType, Long date) {
        List<TagTimestampWrapper> timestamps = jdbcTemplate.query(JPQL_LAST_EVENT_DAY, new TagTimestampRowMapper(), eventType, new Date(date));
        return timestamps.isEmpty() ? null : timestamps.get(0);
    }

    @Transactional(readOnly = true)
    public TagsCounterWrapper getTagsAmountPowerOk(Long date) {
        List<TagsCounterWrapper> tagsCounterList = jdbcTemplate.query(JPQL_TAG_AMOUNT_POWER_OK, new TagsCounterRowMapper(), new Date(date));
        return tagsCounterList.isEmpty() ? null : tagsCounterList.get(0);
    }

    @Transactional(readOnly = true)
    public TagsCounterWrapper getTimeWorkWithMaterialForDayLine1(Long date) {
        List<TagsCounterWrapper> tagsCounterList = jdbcTemplate.query(JPQL_PERIOD_WITH_MATERIAL_LINE1, new TagsCounterRowMapper(), new Date(date));
        return tagsCounterList.isEmpty() ? null : tagsCounterList.get(0);
    }

    @Transactional(readOnly = true)
    public TagsCounterWrapper getTimeWorkWithMaterialForDayLine2(Long date) {
        List<TagsCounterWrapper> tagsCounterList = jdbcTemplate.query(JPQL_PERIOD_WITH_MATERIAL_LINE2, new TagsCounterRowMapper(), new Date(date));
        return tagsCounterList.isEmpty() ? null : tagsCounterList.get(0);
    }

    @Transactional(readOnly = true)
    public TagsCounterWrapper getDownTimeForDayLine1(Long date) {
        List<TagsCounterWrapper> tagsCounterList = jdbcTemplate.query(JPQL_DOWN_TIME_LINE1, new TagsCounterRowMapper(), new Date(date));
        return tagsCounterList.isEmpty() ? null : tagsCounterList.get(0);
    }

    @Transactional(readOnly = true)
    public TagsCounterWrapper getDownTimeForDayLine2(Long date) {
        List<TagsCounterWrapper> tagsCounterList = jdbcTemplate.query(JPQL_DOWN_TIME_LINE2, new TagsCounterRowMapper(), new Date(date));
        return tagsCounterList.isEmpty() ? null : tagsCounterList.get(0);
    }

    @Transactional(readOnly = true)
    public AverageSpeedWrapper getAverageSpeedLine1(Long date) {
        List<AverageSpeedWrapper> tagsCounterList = jdbcTemplate.query(JPQL_AVR_SPEED_LINE1, new AverageSpeedRowMapper(), new Date(date));
        return tagsCounterList.isEmpty() ? null : tagsCounterList.get(0);
    }

    @Transactional(readOnly = true)
    public AverageSpeedWrapper getAverageSpeedLine2(Long date) {
        List<AverageSpeedWrapper> tagsCounterList = jdbcTemplate.query(JPQL_AVR_SPEED_LINE2, new AverageSpeedRowMapper(), new Date(date));
        return tagsCounterList.isEmpty() ? null : tagsCounterList.get(0);
    }

    @Transactional(readOnly = true)
    public ExpenditureWrapper getExpenditureLine1(Long date) {
        List<ExpenditureWrapper> expenditureList = jdbcTemplate.query(JPQL_EXPENDITURE_DAY_LINE1, new ExpenditureRowMapper(), new Date(date));
        return expenditureList.isEmpty() ? null : expenditureList.get(0);
    }

    @Transactional(readOnly = true)
    public ExpenditureWrapper getExpenditureLine2(Long date) {
        List<ExpenditureWrapper> expenditureList = jdbcTemplate.query(JPQL_EXPENDITURE_DAY_LINE2, new ExpenditureRowMapper(), new Date(date));
        return expenditureList.isEmpty() ? null : expenditureList.get(0);
    }

    @Transactional(readOnly = true)
    public MaterialExpenditure getExpenditureForDay(Long date) {
        List<MaterialExpenditure> expenditureList = jdbcTemplate.query(JPQL_EXPENDITURE_DAY, new MaterialExpenditureRowMapper(), new Date(date));
        return expenditureList.isEmpty() ? null : expenditureList.get(0);
    }

    @Transactional(readOnly = true)
    public Long getTimePowerOff(Long date) {

        TagsCounterWrapper tagsPowerOkCounter = getTagsAmountPowerOk(date);

        Long timeNow = Calendar.getInstance().getTimeInMillis();
        Long timeDayBegining = getTimeDayBegining(date);

        Calendar calDate = Calendar.getInstance();
        calDate.setTimeInMillis(date);

        Calendar calDateNow = Calendar.getInstance();
        calDateNow.setTimeInMillis(timeNow);

        Long totalTimePowerOff;
        if ((calDate.get(Calendar.MONTH) == calDateNow.get(Calendar.MONTH)) &&
                (calDate.get(Calendar.YEAR) == calDateNow.get(Calendar.YEAR)) &&
                (calDate.get(Calendar.DAY_OF_MONTH) == calDateNow.get(Calendar.DAY_OF_MONTH))) {
            totalTimePowerOff = timeNow - timeDayBegining - tagsPowerOkCounter.getTagsamount() * TagsServiceImpl.scheduledPeriod;
        } else {
            totalTimePowerOff = 86400000L - tagsPowerOkCounter.getTagsamount() * TagsServiceImpl.scheduledPeriod;

        }
        //ignore if less than 10 min
        if (totalTimePowerOff < 600000L) {
            totalTimePowerOff = 0L;
        }
        return totalTimePowerOff;

    }

    @Transactional(readOnly = true)
    public StatisticWrapper getDayStatistic(String wallpaperLineName, Long date) {
        StatisticWrapper dayStatistic = new StatisticWrapper();

        TagTimestampWrapper timestampWrapper;

        if (wallpaperLineName.equals("line1")) {
            timestampWrapper = getTimeFirstEventForDay(EventType.LINE1_ON.name(), date);
            if (timestampWrapper != null) {
                dayStatistic.setTurnOnTime(timestampWrapper.getTimestamp());
            }
            timestampWrapper = getTimeLastEventForDay(EventType.LINE1_OFF.name(), date);
            if (timestampWrapper != null) {
                dayStatistic.setTurnOffTime(timestampWrapper.getTimestamp());
            }

            dayStatistic.setPeriodWorkWithMaterial(getTimeWorkWithMaterialForDayLine1(date).getTagsamount() * TagsServiceImpl.scheduledPeriod);
            dayStatistic.setDowntime(getDownTimeForDayLine1(date).getTagsamount() * TagsServiceImpl.scheduledPeriod);
            Double avrSpeedLine1 = getAverageSpeedLine1(date).getSpeed();
            if (avrSpeedLine1 != null) {
                dayStatistic.setAverageSpeed(avrSpeedLine1);
            }

            ExpenditureWrapper expenditureTodayLine1 = getExpenditureLine1(date);
            if (expenditureTodayLine1 != null) {
                dayStatistic.setExpenditureOfMaterial(expenditureTodayLine1.getExpenditure());
            }


        } else {
            timestampWrapper = getTimeFirstEventForDay(EventType.LINE2_ON.name(), date);
            if (timestampWrapper != null) {
                dayStatistic.setTurnOnTime(timestampWrapper.getTimestamp());
            }
            timestampWrapper = getTimeLastEventForDay(EventType.LINE2_OFF.name(), date);
            if (timestampWrapper != null) {
                dayStatistic.setTurnOffTime(timestampWrapper.getTimestamp());
            }

            dayStatistic.setPeriodWorkWithMaterial(getTimeWorkWithMaterialForDayLine2(date).getTagsamount() * TagsServiceImpl.scheduledPeriod);
            dayStatistic.setDowntime(getDownTimeForDayLine2(date).getTagsamount() * TagsServiceImpl.scheduledPeriod);

            Double avrSpeedLine2 = getAverageSpeedLine2(date).getSpeed();
            if (avrSpeedLine2 != null) {
                dayStatistic.setAverageSpeed(avrSpeedLine2);
            }

            ExpenditureWrapper expenditureTodayLine2 = getExpenditureLine2(date);
            if (expenditureTodayLine2 != null) {
                dayStatistic.setExpenditureOfMaterial(expenditureTodayLine2.getExpenditure());
            }
        }

        dayStatistic.setStatisticAbout(wallpaperLineName);

        dayStatistic.setTimePowerOff(getTimePowerOff(date));
        dayStatistic.setDate(date);

        return dayStatistic;
    }

    private Long getTimeDayBegining(Long now) {
        Calendar today = new GregorianCalendar();
        today.setTimeInMillis(now);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today.getTime().getTime();
    }
}
