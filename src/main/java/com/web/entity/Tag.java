package com.web.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created on 27.10.15.
 */
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Date timeStamp;

    private Double currentSpeedLine1;

    private Boolean line1OnOff;

    private Boolean withMaterialLine1;

    private Double expenditureOfMaterialLine1;

    private Double currentSpeedLine2;

    private Boolean line2OnOff;

    private Boolean withMaterialLine2;

    private Double expenditureOfMaterialLine2;

    private Boolean connectionOk;

    private Boolean powerOk;

    public Boolean getPowerOk() {
        return powerOk;
    }

    public void setPowerOk(Boolean powerOk) {
        this.powerOk = powerOk;
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

    public Boolean getConnectionOk() {
        return connectionOk;
    }

    public void setConnectionOk(Boolean connectionOk) {
        this.connectionOk = connectionOk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return id != null ? id.equals(tag.id) : tag.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
