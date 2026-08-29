package com.lamp.foundation.base.extension.jdbc.type.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

    @Override
    public int[] types() {
        return new int[]{Types.BOOLEAN,Types.BIT};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setBoolean(i, parameter);
    }

    @Override
    public Boolean getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return rs.getBoolean(columnName);
    }

    @Override
    public Boolean getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return rs.getBoolean(columnIndex);
    }

    @Override
    public Boolean getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return cs.getBoolean(columnIndex);
    }
}
