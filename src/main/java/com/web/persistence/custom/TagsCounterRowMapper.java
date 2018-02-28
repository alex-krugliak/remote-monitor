package com.web.persistence.custom;

import com.web.wrapper.dao.TagsCounterWrapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alex on 26.01.18.
 */
public class TagsCounterRowMapper implements RowMapper<TagsCounterWrapper> {

    @Override
    public TagsCounterWrapper mapRow(ResultSet resultSet, int i) throws SQLException {
        TagsCounterWrapper tagsCounterWrapper = new TagsCounterWrapper();

        tagsCounterWrapper.setTagsamount(resultSet.getLong("tagsamount"));
        return tagsCounterWrapper;
    }
}
