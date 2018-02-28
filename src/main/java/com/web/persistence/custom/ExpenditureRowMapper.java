package com.web.persistence.custom;

import com.web.wrapper.dao.ExpenditureWrapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alex on 26.01.18.
 */
public class ExpenditureRowMapper implements RowMapper<ExpenditureWrapper> {

    @Override
    public ExpenditureWrapper mapRow(ResultSet resultSet, int i) throws SQLException {
        ExpenditureWrapper expenditureWrapper = new ExpenditureWrapper();

        expenditureWrapper.setExpenditure(resultSet.getDouble("expenditure"));
        return expenditureWrapper;
    }
}
