package com.lamp.foundation.base.extension.jdbc.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.lamp.foundation.api.extension.jdbc.TypeHandler;
import com.lamp.foundation.api.extension.jdbc.TypeMetadata;

public class ProxyTypeHandler implements TypeHandler<Object> {

    private TypeHandler<Object> typeHandler;

    @Override
    public int[] types() {
        return new int[0];
    }

    @Override
    public int[] independent() {
        return new int[0];
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, TypeMetadata typeMetadata) throws SQLException {
        if (Objects.isNull(parameter)) {
            if (Objects.isNull(typeMetadata) || Objects.isNull(typeMetadata.getJavaType())) {
                throw new SQLException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                ps.setNull(i, typeMetadata.getJdbcType());
            } catch (SQLException e) {
                String message = "setNull(" + i + "),handler " + this.typeHandler.getClass() + "  Cause: " + e;
                throw new SQLException(message, e);
            }
        } else {
            try {
                this.typeHandler.setParameter(ps, i, parameter, typeMetadata);
            } catch (Exception e) {
                String message = "setParameter(" + i + ") , handler " + this.typeHandler.getClass() + " + Cause: " + e;
                throw new SQLException(message, e);
            }
        }
    }

    @Override
    public Object getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        try {
            return typeHandler.getResult(rs, columnName, typeMetadata);
        } catch (SQLException e) {
            String message = " getResult data fail, columnName is " + columnName + " and type is " + typeMetadata.getJdbcType() + " Cause:" + e;
            throw new SQLException(message, e);
        }
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        try {
            return typeHandler.getResult(rs, columnIndex, typeMetadata);
        } catch (SQLException e) {
            String message = " getResult data fail, columnIndex is " + columnIndex + " and type is " + typeMetadata.getJdbcType() + " Cause:" + e;
            throw new SQLException(message, e);
        }
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        try {
            return typeHandler.getResult(cs, columnIndex, typeMetadata);
        } catch (SQLException e) {
            String message =
                " getResult data fail, type is CallableStatement  columnIndex is " + columnIndex + " and type is " + typeMetadata.getJdbcType()
                + " Cause:" + e;
            throw new SQLException(message, e);
        }
    }
}
