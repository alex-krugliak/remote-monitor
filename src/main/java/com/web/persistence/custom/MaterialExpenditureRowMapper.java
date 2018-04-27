package com.web.persistence.custom;

import com.web.entity.MaterialExpenditure;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alex on 26.01.18.
 */
public class MaterialExpenditureRowMapper implements RowMapper<MaterialExpenditure> {

    @Override
    public MaterialExpenditure mapRow(ResultSet resultSet, int i) throws SQLException {
        MaterialExpenditure materialExpenditure = new MaterialExpenditure();

        materialExpenditure.setId(resultSet.getLong("id"));
        materialExpenditure.setExpenditureline2(resultSet.getDouble("expenditure_line2"));
        materialExpenditure.setExpenditureline1(resultSet.getDouble("expenditure_line1"));
        materialExpenditure.setTimestamp(resultSet.getDate("time_stamp"));
        return materialExpenditure;
    }
}
