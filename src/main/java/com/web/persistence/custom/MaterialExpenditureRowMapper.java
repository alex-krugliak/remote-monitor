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
        materialExpenditure.setExpenditureline2(resultSet.getDouble("expenditureline2"));
        materialExpenditure.setExpenditureline1(resultSet.getDouble("expenditureline1"));
        materialExpenditure.setTimestamp(resultSet.getDate("timestamp"));
        return materialExpenditure;
    }
}
