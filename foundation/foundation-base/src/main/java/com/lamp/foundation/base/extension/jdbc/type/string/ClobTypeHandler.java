package com.lamp.foundation.base.extension.jdbc.type.string;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class ClobTypeHandler extends BaseTypeHandler<String> {


    @Override
    public boolean useType() {
        return false;
    }

    @Override
    public int[] types() {
        return new int[0];
    }

    @Override
    public int[] independent() {
        return new int[] {Types.CLOB};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, TypeMetadata typeMetadata) throws SQLException {
        StringReader reader = new StringReader(parameter);
        ps.setCharacterStream(i, reader, parameter.length());
    }

    @Override
    public String getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return toString(rs.getClob(columnName));
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return toString(rs.getClob(columnIndex));
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return toString(cs.getClob(columnIndex));
    }

    private String toString(Clob clob) throws SQLException {
        return clob == null ? null : clob.getSubString(1, (int) clob.length());
    }
}
