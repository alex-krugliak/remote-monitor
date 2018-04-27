package com.web.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created on 10/16/16.
 */
@Entity
@Table(name = "expenditure")
public class MaterialExpenditure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name="time_stamp")
    private Date timestamp;
    @Column(name="expenditure_line1")
    private Double expenditureline1;
    @Column(name="expenditure_line2")
    private Double expenditureline2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getExpenditureline1() {
        return expenditureline1;
    }

    public void setExpenditureline1(Double expenditureline1) {
        this.expenditureline1 = expenditureline1;
    }

    public Double getExpenditureline2() {
        return expenditureline2;
    }

    public void setExpenditureline2(Double expenditureline2) {
        this.expenditureline2 = expenditureline2;
    }
}
