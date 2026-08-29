package com.lamp.foundation.base.extension.jdbc.type.stream;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;
import com.lamp.foundation.base.lang.util.WrapperConverter;

public class ByteObjectArrayTypeHandler extends BaseTypeHandler<Byte[]> {

    @Override
    public int[] types() {
        return new int[]{Types.BLOB, Types.LONGVARBINARY};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Byte[] parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setBytes(i, WrapperConverter.convertToByteArray(parameter));
    }

    @Override
    public Byte[] getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return WrapperConverter.convertToObjectArray(rs.getBytes(columnName));
    }

    @Override
    public Byte[] getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return WrapperConverter.convertToObjectArray(rs.getBytes(columnIndex));
    }

    @Override
    public Byte[] getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return WrapperConverter.convertToObjectArray(cs.getBytes(columnIndex));
    }
}
