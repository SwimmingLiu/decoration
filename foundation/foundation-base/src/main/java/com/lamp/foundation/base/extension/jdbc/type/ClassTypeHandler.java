package com.lamp.foundation.base.extension.jdbc.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;

public abstract class ClassTypeHandler<T> extends BaseTypeHandler<T> {

    @Override
    public int[] types() {
        return new int[0];
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public T getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return rs.getObject(columnName, this.clazz);
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return rs.getObject(columnIndex, this.clazz);
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return cs.getObject(columnIndex, this.clazz);
    }
}
