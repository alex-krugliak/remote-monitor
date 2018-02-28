package com.web.wrapper.comport;

/**
 * Created on 8/4/16.
 */
public class ComPortDataWriteWrapper {

    private Boolean firstLineRunPermission;

    private Boolean secondLineRunPermission;

    private Boolean resetExpenditureCounterLine1;

    private Boolean resetExpenditureCounterLine2;

    public ComPortDataWriteWrapper() {
        this.firstLineRunPermission = true;
        this.secondLineRunPermission = true;
        this.resetExpenditureCounterLine1 = false;
        this.resetExpenditureCounterLine2 = false;
    }

    public Boolean getResetExpenditureCounterLine1() {
        return resetExpenditureCounterLine1;
    }

    public void setResetExpenditureCounterLine1(Boolean resetExpenditureCounterLine1) {
        this.resetExpenditureCounterLine1 = resetExpenditureCounterLine1;
    }

    public Boolean getResetExpenditureCounterLine2() {
        return resetExpenditureCounterLine2;
    }

    public void setResetExpenditureCounterLine2(Boolean resetExpenditureCounterLine2) {
        this.resetExpenditureCounterLine2 = resetExpenditureCounterLine2;
    }

    public Boolean getFirstLineRunPermission() {
        return firstLineRunPermission;
    }

    public void setFirstLineRunPermission(Boolean firstLineRunPermission) {
        this.firstLineRunPermission = firstLineRunPermission;
    }

    public Boolean getSecondLineRunPermission() {
        return secondLineRunPermission;
    }

    public void setSecondLineRunPermission(Boolean secondLineRunPermission) {
        this.secondLineRunPermission = secondLineRunPermission;
    }
}
