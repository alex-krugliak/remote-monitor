package com.web.persistence.custom;

import com.web.wrapper.dao.AverageSpeedWrapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alex on 26.01.18.
 */
public class AverageSpeedRowMapper implements RowMapper<AverageSpeedWrapper> {

    @Override
    public AverageSpeedWrapper mapRow(ResultSet resultSet, int i) throws SQLException {
        AverageSpeedWrapper averageSpeedWrapper = new AverageSpeedWrapper();

        averageSpeedWrapper.setSpeed(resultSet.getDouble("speed"));
        return averageSpeedWrapper;
    }
}
