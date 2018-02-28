package com.web.wrapper.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.web.util.CustomTimeJsonSerializer;

import java.util.Date;

/**
 * Created on 17.06.16.
 */
public class DataCurrentWrapper {

    private Double currentSpeedLine1;

    private Boolean line1OnOff;

    private Boolean withMaterialLine1;

    private Double expenditureOfMaterialLine1;

    private Long periodWorkWithMaterialLine1;

    private Long downtimeLine1;

    @JsonSerialize(using = CustomTimeJsonSerializer.class)
    private Date turnOnTimeTodayLine1;

    @JsonSerialize(using = CustomTimeJsonSerializer.class)
    private Date turnOffTimeLine1;

    private Double currentSpeedLine2;

    private Boolean line2OnOff;

    private Boolean withMaterialLine2;

    private Double expenditureOfMaterialLine2;

    private Long periodWorkWithMaterialLine2;

    private Long downtimeLine2;

    @JsonSerialize(using = CustomTimeJsonSerializer.class)
    private Date turnOnTimeTodayLine2;

    @JsonSerialize(using = CustomTimeJsonSerializer.class)
    private Date turnOffTimeLine2;

    private Boolean hardwareConnected;

    private Long timePowerOff;

    public Long getTimePowerOff() {
        return timePowerOff;
    }

    public void setTimePowerOff(Long timePowerOff) {
        this.timePowerOff = timePowerOff;
    }

    public Boolean getHardwareConnected() {
        return hardwareConnected;
    }

    public void setHardwareConnected(Boolean hardwareConnected) {
        this.hardwareConnected = hardwareConnected;
    }

    public Double getCurrentSpeedLine1() {
        return currentSpeedLine1;
    }

    public void setCurrentSpeedLine1(Double currentSpeedLine1) {
        this.currentSpeedLine1 = currentSpeedLine1;
    }

    public Boolean getLine1OnOff() {
        return line1OnOff;
    }

    public void setLine1OnOff(Boolean line1OnOff) {
        this.line1OnOff = line1OnOff;
    }

    public Boolean getWithMaterialLine1() {
        return withMaterialLine1;
    }

    public void setWithMaterialLine1(Boolean withMaterialLine1) {
        this.withMaterialLine1 = withMaterialLine1;
    }

    public Double getExpenditureOfMaterialLine1() {
        return expenditureOfMaterialLine1;
    }

    public void setExpenditureOfMaterialLine1(Double expenditureOfMaterialLine1) {
        this.expenditureOfMaterialLine1 = expenditureOfMaterialLine1;
    }

    public Long getPeriodWorkWithMaterialLine1() {
        return periodWorkWithMaterialLine1;
    }

    public void setPeriodWorkWithMaterialLine1(Long periodWorkWithMaterialLine1) {
        this.periodWorkWithMaterialLine1 = periodWorkWithMaterialLine1;
    }

    public Long getDowntimeLine1() {
        return downtimeLine1;
    }

    public void setDowntimeLine1(Long downtimeLine1) {
        this.downtimeLine1 = downtimeLine1;
    }

    public Date getTurnOnTimeTodayLine1() {
        return turnOnTimeTodayLine1;
    }

    public void setTurnOnTimeTodayLine1(Date turnOnTimeTodayLine1) {
        this.turnOnTimeTodayLine1 = turnOnTimeTodayLine1;
    }

    public Date getTurnOffTimeLine1() {
        return turnOffTimeLine1;
    }

    public void setTurnOffTimeLine1(Date turnOffTimeLine1) {
        this.turnOffTimeLine1 = turnOffTimeLine1;
    }

    public Double getCurrentSpeedLine2() {
        return currentSpeedLine2;
    }

    public void setCurrentSpeedLine2(Double currentSpeedLine2) {
        this.currentSpeedLine2 = currentSpeedLine2;
    }

    public Boolean getLine2OnOff() {
        return line2OnOff;
    }

    public void setLine2OnOff(Boolean line2OnOff) {
        this.line2OnOff = line2OnOff;
    }

    public Boolean getWithMaterialLine2() {
        return withMaterialLine2;
    }

    public void setWithMaterialLine2(Boolean withMaterialLine2) {
        this.withMaterialLine2 = withMaterialLine2;
    }

    public Double getExpenditureOfMaterialLine2() {
        return expenditureOfMaterialLine2;
    }

    public void setExpenditureOfMaterialLine2(Double expenditureOfMaterialLine2) {
        this.expenditureOfMaterialLine2 = expenditureOfMaterialLine2;
    }

    public Long getPeriodWorkWithMaterialLine2() {
        return periodWorkWithMaterialLine2;
    }

    public void setPeriodWorkWithMaterialLine2(Long periodWorkWithMaterialLine2) {
        this.periodWorkWithMaterialLine2 = periodWorkWithMaterialLine2;
    }

    public Long getDowntimeLine2() {
        return downtimeLine2;
    }

    public void setDowntimeLine2(Long downtimeLine2) {
        this.downtimeLine2 = downtimeLine2;
    }

    public Date getTurnOnTimeTodayLine2() {
        return turnOnTimeTodayLine2;
    }

    public void setTurnOnTimeTodayLine2(Date turnOnTimeTodayLine2) {
        this.turnOnTimeTodayLine2 = turnOnTimeTodayLine2;
    }

    public Date getTurnOffTimeLine2() {
        return turnOffTimeLine2;
    }

    public void setTurnOffTimeLine2(Date turnOffTimeLine2) {
        this.turnOffTimeLine2 = turnOffTimeLine2;
    }
}
