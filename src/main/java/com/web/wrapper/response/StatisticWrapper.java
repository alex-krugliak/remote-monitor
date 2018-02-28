package com.web.wrapper.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.web.util.CustomDateJsonSerializer;
import com.web.util.CustomTimeJsonSerializer;

import java.util.Date;

/**
 * Created on 10.08.16.
 */
public class StatisticWrapper {

    @JsonSerialize(using = CustomDateJsonSerializer.class)
    private Date date;

    private Double averageSpeed;

    @JsonSerialize(using = CustomTimeJsonSerializer.class)
    private Date turnOnTime;

    @JsonSerialize(using = CustomTimeJsonSerializer.class)
    private Date turnOffTime;

    private Long downtime;

    private Long periodWorkWithMaterial;

    private Double expenditureOfMaterial;

    private String statisticAbout;

    private Long periodPowerFail;

    private Long timePowerOff;

    public Long getTimePowerOff() {
        return timePowerOff;
    }

    public void setTimePowerOff(Long timePowerOff) {
        this.timePowerOff = timePowerOff;
    }

    public Long getPeriodPowerFail() {
        return periodPowerFail;
    }

    public void setPeriodPowerFail(Long periodPowerFail) {
        this.periodPowerFail = periodPowerFail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = new Date(date);
    }

    public String getStatisticAbout() {
        return statisticAbout;
    }

    public void setStatisticAbout(String statisticAbout) {
        this.statisticAbout = statisticAbout;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Date getTurnOnTime() {
        return turnOnTime;
    }

    public void setTurnOnTime(Date turnOnTime) {
        this.turnOnTime = turnOnTime;
    }

    public Date getTurnOffTime() {
        return turnOffTime;
    }

    public void setTurnOffTime(Date turnOffTime) {
        this.turnOffTime = turnOffTime;
    }

    public Long getDowntime() {
        return downtime;
    }

    public void setDowntime(Long downtime) {
        this.downtime = downtime;
    }

    public Long getPeriodWorkWithMaterial() {
        return periodWorkWithMaterial;
    }

    public void setPeriodWorkWithMaterial(Long periodWorkWithMaterial) {
        this.periodWorkWithMaterial = periodWorkWithMaterial;
    }

    public Double getExpenditureOfMaterial() {
        return expenditureOfMaterial;
    }

    public void setExpenditureOfMaterial(Double expenditureOfMaterial) {
        this.expenditureOfMaterial = expenditureOfMaterial;
    }
}
