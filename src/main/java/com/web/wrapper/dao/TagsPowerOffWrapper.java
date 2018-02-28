package com.web.wrapper.dao;

import java.util.Date;

/**
 * Created on 10/16/16.
 */
public class TagsPowerOffWrapper {
    private Long id;

    private Date timestamp;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
}
