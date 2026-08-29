package com.lamp.foundation.base.extension.jdbc.type.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class ByteTypeHandler extends BaseTypeHandler<Byte> {

    @Override
    public int[] types() {
        return new int[]{Types.TINYINT};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Byte parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setByte(i, parameter);
    }

    @Override
    public Byte getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        byte result = rs.getByte(columnName);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Byte getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        byte result = rs.getByte(columnIndex);
        return result == 0 && rs.wasNull() ? null : result;
    }

    @Override
    public Byte getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        byte result = cs.getByte(columnIndex);
        return result == 0 && cs.wasNull() ? null : result;
    }
}
