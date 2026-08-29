package com.lamp.foundation.base.extension.jdbc.type.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class CharacterTypeHandler extends BaseTypeHandler<Character> {

    @Override
    public int[] types() {
        return new int[]{Types.CHAR};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Character parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public Character getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        String columnValue = rs.getString(columnName);
        if (columnValue != null && !columnValue.isEmpty()) {
            return columnValue.charAt(0);
        }
        return null;
    }

    @Override
    public Character getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        if (columnValue != null && !columnValue.isEmpty()) {
            return columnValue.charAt(0);
        }
        return null;
    }

    @Override
    public Character getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        if (columnValue != null && !columnValue.isEmpty()) {
            return columnValue.charAt(0);
        }
        return null;
    }
}
