package com.lamp.foundation.base.extension.jdbc.type.stream;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class InputStreamTypeHandler extends BaseTypeHandler<InputStream> {

    @Override
    public int[] types() {
        return new int[0];
    }

    @Override
    public int[] independent() {
        return new int[0];
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, InputStream parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setBlob(i, parameter);
    }

    @Override
    public InputStream getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return this.toInputStream(rs.getBlob(columnName));
    }

    @Override
    public InputStream getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return this.toInputStream(rs.getBlob(columnIndex));
    }

    @Override
    public InputStream getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return this.toInputStream(cs.getBlob(columnIndex));
    }

    private InputStream toInputStream(Blob blob) throws SQLException {
        if (blob == null) {
            return null;
        }
        return blob.getBinaryStream();
    }
}
