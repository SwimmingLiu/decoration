package com.lamp.foundation.base.extension.jdbc.type.string;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class ClobReaderTypeHandler extends BaseTypeHandler<Reader> {

    @Override
    public int[] types() {
        return new int[0];
    }

    @Override
    public int[] independent() {
        return new int[0];
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Reader parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setClob(i, parameter);
    }

    @Override
    public Reader getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return toReader(rs.getClob(columnName));
    }

    @Override
    public Reader getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return toReader(rs.getClob(columnIndex));
    }

    @Override
    public Reader getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return toReader(cs.getClob(columnIndex));
    }

    private Reader toReader(Clob clob) throws SQLException {
        if (clob == null) {
            return null;
        }
        return clob.getCharacterStream();
    }
}
