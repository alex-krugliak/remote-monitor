package com.web.persistence.custom;

import com.web.wrapper.dao.TagTimestampWrapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alex on 26.01.18.
 */
public class TagTimestampRowMapper implements RowMapper<TagTimestampWrapper> {

    @Override
    public TagTimestampWrapper mapRow(ResultSet resultSet, int i) throws SQLException {
        TagTimestampWrapper tagTimestampWrapper = new TagTimestampWrapper();

        tagTimestampWrapper.setId(resultSet.getLong("id"));
        tagTimestampWrapper.setTimestamp(resultSet.getTimestamp("time_stamp"));
        return tagTimestampWrapper;
    }
}
