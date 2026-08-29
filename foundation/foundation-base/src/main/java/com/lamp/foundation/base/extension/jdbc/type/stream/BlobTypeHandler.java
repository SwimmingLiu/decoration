package com.lamp.foundation.base.extension.jdbc.type.stream;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class BlobTypeHandler extends BaseTypeHandler<byte[]> {

    @Override
    public int[] types() {
        return new int[0];
    }

    @Override
    public boolean useType() {
        return false;
    }

    @Override
    public int[] independent() {
        return new int[] {Types.LONGVARBINARY, Types.BLOB};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, byte[] parameter, TypeMetadata typeMetadata) throws SQLException {
        ByteArrayInputStream bis = new ByteArrayInputStream(parameter);
        ps.setBinaryStream(i, bis, parameter.length);
    }

    @Override
    public byte[] getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return this.getByte(rs.getBlob(columnName));
    }

    @Override
    public byte[] getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return this.getByte(rs.getBlob(columnIndex));
    }

    @Override
    public byte[] getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return this.getByte(cs.getBlob(columnIndex));
    }

    private byte[] getByte(Blob blob) throws SQLException {
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        return returnValue;
    }
}
