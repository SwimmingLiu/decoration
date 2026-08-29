package com.lamp.foundation.base.extension.jdbc.type.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class LongTypeHandler extends BaseTypeHandler<Long> {

    @Override
    public int[] types() {
        return new int[] {Types.BIGINT};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Long parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setLong(i, parameter);
    }

    @Override
    public Long getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        long result = rs.getLong(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Long getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        long result = rs.getLong(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Long getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        long result = cs.getLong(columnIndex);
        return result == 0 && cs.wasNull() ? null : result;
    }
}
