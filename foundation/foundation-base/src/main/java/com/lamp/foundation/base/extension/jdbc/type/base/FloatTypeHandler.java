package com.lamp.foundation.base.extension.jdbc.type.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class FloatTypeHandler extends BaseTypeHandler<Float> {

    @Override
    public int[] types() {
        return new int[]{Types.FLOAT};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Float parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setFloat(i, parameter);
    }

    @Override
    public Float getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        float result = rs.getFloat(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Float getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        float result = rs.getFloat(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Float getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        float result = cs.getFloat(columnIndex);
        return result == 0 && cs.wasNull() ? null : result;
    }
}
