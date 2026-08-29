package com.lamp.foundation.base.extension.jdbc.type.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class DoubleTypeHandler extends BaseTypeHandler<Double> {

    @Override
    public int[] types() {
        return new int[] {Types.DOUBLE};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Double parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setDouble(i, parameter.doubleValue());
    }

    @Override
    public Double getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        double result = rs.getDouble(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Double getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        double result = rs.getDouble(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Double getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        double result = cs.getDouble(columnIndex);
        return result == 0 && cs.wasNull() ? null : result;
    }
}
