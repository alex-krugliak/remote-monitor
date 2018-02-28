package com.web.persistence.custom;

import com.web.wrapper.response.DataCurrentWrapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alex on 26.01.18.
 */

class DataCurrentRowMapper implements RowMapper<DataCurrentWrapper> {

    @Override
    public DataCurrentWrapper mapRow(ResultSet resultSet, int i) throws SQLException {
        DataCurrentWrapper dataCurrentWrapper = new DataCurrentWrapper();

        dataCurrentWrapper.setCurrentSpeedLine1(resultSet.getDouble("currentSpeedLine1"));
        dataCurrentWrapper.setExpenditureOfMaterialLine1(resultSet.getDouble("expenditureOfMaterialLine1"));
        dataCurrentWrapper.setLine1OnOff(resultSet.getBoolean("line1OnOff"));
        dataCurrentWrapper.setWithMaterialLine1(resultSet.getBoolean("withMaterialLine1"));
        dataCurrentWrapper.setCurrentSpeedLine2(resultSet.getDouble("currentSpeedLine2"));
        dataCurrentWrapper.setExpenditureOfMaterialLine2(resultSet.getDouble("expenditureOfMaterialLine2"));
        dataCurrentWrapper.setLine2OnOff(resultSet.getBoolean("line2OnOff"));
        dataCurrentWrapper.setWithMaterialLine2(resultSet.getBoolean("withMaterialLine2"));
        dataCurrentWrapper.setHardwareConnected(resultSet.getBoolean("hardwareConnected"));

        return dataCurrentWrapper;
    }
}
