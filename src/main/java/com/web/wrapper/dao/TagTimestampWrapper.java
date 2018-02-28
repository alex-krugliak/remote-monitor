package com.web.wrapper.dao;

import java.util.Date;

/**
 * Created on 23.06.16.
 */
public class TagTimestampWrapper {
    private Long id;

    private Date timestamp;

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
