package com.web.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by alex on 02.04.18.
 */
@Entity
@Table(name = "day_total")
public class DayTotalData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name="time_stamp", columnDefinition = "date", nullable = false, unique = true)
    private Date timeStamp;
    @Column(name="expenditure_of_material_line1")
    private Double expenditureOfMaterialLine1;
    @Column(name="period_work_with_material_line1")
    private Long periodWorkWithMaterialLine1;
    @Column(name="downtime_line1")
    private Long downtimeLine1;
    @Column(name="turn_on_time_today_line1")
    private Date turnOnTimeTodayLine1;
    @Column(name="turn_off_time_line1")
    private Date turnOffTimeLine1;
    @Column(name="average_speed1")
    private Double averageSpeed1;
    @Column(name="time_power_off1")
    private Long timePowerOff1;
    @Column(name="expenditure_of_material_line2")
    private Double expenditureOfMaterialLine2;
    @Column(name="period_work_with_material_line2")
    private Long periodWorkWithMaterialLine2;
    @Column(name="down_time_line2")
    private Long downtimeLine2;
    @Column(name="turn_on_time_today_line2")
    private Date turnOnTimeTodayLine2;
    @Column(name="turn_off_time_line2")
    private Date turnOffTimeLine2;
    @Column(name="average_speed2")
    private Double averageSpeed2;
    @Column(name="time_power_off2")
    private Long timePowerOff2;

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

    public Long getTimePowerOff1() {
        return timePowerOff1;
    }

    public void setTimePowerOff1(Long timePowerOff1) {
        this.timePowerOff1 = timePowerOff1;
    }

    public Long getTimePowerOff2() {
        return timePowerOff2;
    }

    public void setTimePowerOff2(Long timePowerOff2) {
        this.timePowerOff2 = timePowerOff2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getAverageSpeed1() {
        return averageSpeed1;
    }

    public void setAverageSpeed1(Double averageSpeed1) {
        this.averageSpeed1 = averageSpeed1;
    }

    public Double getAverageSpeed2() {
        return averageSpeed2;
    }

    public void setAverageSpeed2(Double averageSpeed2) {
        this.averageSpeed2 = averageSpeed2;
    }
}
