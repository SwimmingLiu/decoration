package com.lamp.foundation.base.extension.jdbc.type.string;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class StringTypeHandler extends BaseTypeHandler<String> {

    @Override
    public int[] types() {
        return new int[]{Types.VARCHAR,Types.CHAR,Types.LONGNVARCHAR,Types.CLOB,Types.NCHAR,Types.NVARCHAR,Types.NCLOB};
    }

    @Override
    public int[] independent() {
        return new int[]{Types.VARCHAR,Types.CHAR,Types.LONGNVARCHAR};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return rs.getString(columnName);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return cs.getString(columnIndex);
    }
}
