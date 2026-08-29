package com.lamp.foundation.base.extension.jdbc.type.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class IntegerTypeHandler extends BaseTypeHandler<Integer> {

    @Override
    public int[] types() {
        return new int[] {Types.INTEGER};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Integer parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setInt(i, parameter);
    }

    @Override
    public Integer getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        int result = rs.getInt(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Integer getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        int result = rs.getInt(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Integer getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        int result = cs.getInt(columnIndex);
        return result == 0 && cs.wasNull() ? null : result;
    }
}
