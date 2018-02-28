package com.web.wrapper.comport;

/**
 * Created on 01.07.16.
 */
public class ComPortDataWrapper {

    private boolean isLine1OnOff;

    private boolean isWithMaterialLine1;

    private Long impulseCounterLine1;

    private int differenceCounterLine1 = 0;

    private boolean isLine2OnOff;

    private boolean isWithMaterialLine2;

    private Long impulseCounterLine2;

    private int differenceCounterLine2 = 0;

    private Boolean connectionComPortOk;

    private Boolean powerOn;

    public ComPortDataWrapper() {
        this.isLine1OnOff = false;
        this.isWithMaterialLine1 = false;

        this.impulseCounterLine1 = 0L;
        this.isLine2OnOff = false;
        this.isWithMaterialLine2 = false;

        this.impulseCounterLine2 = 0L;
        this.connectionComPortOk = false;
        this.powerOn = false;
    }

    public Boolean getPowerOn() {
        return powerOn;
    }

    public void setPowerOn(Boolean powerOn) {
        this.powerOn = powerOn;
    }

    public Boolean getConnectionComPortOk() {
        return connectionComPortOk;
    }

    public void setConnectionComPortOk(Boolean connectionComPortOk) {
        this.connectionComPortOk = connectionComPortOk;
    }

    public boolean isLine1OnOff() {
        return isLine1OnOff;
    }

    public void setLine1OnOff(boolean line1OnOff) {
        isLine1OnOff = line1OnOff;
    }

    public boolean isWithMaterialLine1() {
        return isWithMaterialLine1;
    }

    public void setWithMaterialLine1(boolean withMaterialLine1) {
        isWithMaterialLine1 = withMaterialLine1;
    }

    public Long getImpulseCounterLine1() {
        return impulseCounterLine1;
    }

    public void setImpulseCounterLine1(Long impulseCounterLine1) {
        this.impulseCounterLine1 = impulseCounterLine1;
    }

    public void setImpulseCounterLine2(Long impulseCounterLine2) {
        this.impulseCounterLine2 = impulseCounterLine2;
    }

    public boolean isLine2OnOff() {
        return isLine2OnOff;
    }

    public void setLine2OnOff(boolean line2OnOff) {
        isLine2OnOff = line2OnOff;
    }

    public boolean isWithMaterialLine2() {
        return isWithMaterialLine2;
    }

    public void setWithMaterialLine2(boolean withMaterialLine2) {
        isWithMaterialLine2 = withMaterialLine2;
    }

    public Long getImpulseCounterLine2() {
        return impulseCounterLine2;
    }

    public int getDifferenceCounterLine1() {
        return differenceCounterLine1;
    }

    public void setDifferenceCounterLine1(int differenceCounterLine1) {
        this.differenceCounterLine1 = differenceCounterLine1;
    }

    public int getDifferenceCounterLine2() {
        return differenceCounterLine2;
    }

    public void setDifferenceCounterLine2(int differenceCounterLine2) {
        this.differenceCounterLine2 = differenceCounterLine2;
    }
}
