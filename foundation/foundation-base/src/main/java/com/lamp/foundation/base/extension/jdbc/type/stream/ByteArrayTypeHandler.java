package com.lamp.foundation.base.extension.jdbc.type.stream;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class ByteArrayTypeHandler extends BaseTypeHandler<byte[]> {

    @Override
    public int[] types() {
        return new int[] {Types.BLOB, Types.LONGVARBINARY};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, byte[] parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setBytes(i, parameter);
    }

    @Override
    public byte[] getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return rs.getBytes(columnName);
    }

    @Override
    public byte[] getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return rs.getBytes(columnIndex);
    }

    @Override
    public byte[] getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return cs.getBytes(columnIndex);
    }
}
