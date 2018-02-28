package com.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by alex on 19.12.17.
 */

@Entity
@Table(name = "module_counter")
public class ModuleCounter {

    @Id
    @Column(name = "module_id")
    private Long moduleId;

    @NotNull
    @Column(name = "counter_i1")
    private Integer counterI1;

    @NotNull
    @Column(name = "counter_i5")
    private Integer counterI5;

    @NotNull
    @Column(name = "total_counter_i1")
    private Long totalCounterI1;

    @NotNull
    @Column(name = "total_counter_i5")
    private Long totalCounterI5;

    public ModuleCounter() {
    }

    public ModuleCounter(Long moduleId, Integer counterI1, Integer counterI5, Long totalCounterI1, Long totalCounterI5) {
        this.moduleId = moduleId;
        this.counterI1 = counterI1;
        this.counterI5 = counterI5;
        this.totalCounterI1 = totalCounterI1;
        this.totalCounterI5 = totalCounterI5;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getCounterI1() {
        return counterI1;
    }

    public void setCounterI1(Integer counterI1) {
        this.counterI1 = counterI1;
    }

    public Integer getCounterI5() {
        return counterI5;
    }

    public void setCounterI5(Integer counterI5) {
        this.counterI5 = counterI5;
    }

    public Long getTotalCounterI1() {
        return totalCounterI1;
    }

    public void setTotalCounterI1(Long totalCounterI1) {
        this.totalCounterI1 = totalCounterI1;
    }

    public Long getTotalCounterI5() {
        return totalCounterI5;
    }

    public void setTotalCounterI5(Long totalCounterI5) {
        this.totalCounterI5 = totalCounterI5;
    }
}
