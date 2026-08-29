package com.lamp.foundation.base.extension.jdbc.type.base;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class BigDecimalTypeHandler extends BaseTypeHandler<BigDecimal> {

    @Override
    public int[] types() {
        return new int[] {Types.REAL, Types.DECIMAL, Types.NUMERIC};
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, BigDecimal parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setBigDecimal(i, parameter);
    }

    @Override
    public BigDecimal getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        return rs.getBigDecimal(columnName);
    }

    @Override
    public BigDecimal getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return rs.getBigDecimal(columnIndex);
    }

    @Override
    public BigDecimal getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        return cs.getBigDecimal(columnIndex);
    }
}
