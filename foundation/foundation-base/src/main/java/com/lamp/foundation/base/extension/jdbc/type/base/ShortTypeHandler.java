package com.lamp.foundation.base.extension.jdbc.type.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class ShortTypeHandler extends BaseTypeHandler<Short> {

    @Override
    public int[] types() {
        return new int[]{Types.SMALLINT};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Short parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setShort(i, parameter);
    }

    @Override
    public Short getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        short result = rs.getShort(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Short getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        short result = rs.getShort(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Short getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        short result = cs.getShort(columnIndex);
        return result == 0 && cs.wasNull() ? null : result;
    }
}
