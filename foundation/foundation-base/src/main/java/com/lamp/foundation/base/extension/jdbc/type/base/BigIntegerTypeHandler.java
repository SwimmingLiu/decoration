package com.lamp.foundation.base.extension.jdbc.type.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lamp.foundation.api.extension.jdbc.TypeMetadata;
import com.lamp.foundation.base.extension.jdbc.type.BaseTypeHandler;

public class BigIntegerTypeHandler extends BaseTypeHandler<BigInteger> {

    @Override
    public int[] types() {
        return new int[0];
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, BigInteger parameter, TypeMetadata typeMetadata) throws SQLException {
        ps.setBigDecimal(i, new BigDecimal(parameter));
    }

    @Override
    public BigInteger getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException {
        BigDecimal bigDecimal = rs.getBigDecimal(columnName);
        return bigDecimal == null ? null : bigDecimal.toBigInteger();
    }

    @Override
    public BigInteger getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        BigDecimal bigDecimal = rs.getBigDecimal(columnIndex);
        return bigDecimal == null ? null : bigDecimal.toBigInteger();
    }

    @Override
    public BigInteger getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException {
        BigDecimal bigDecimal = cs.getBigDecimal(columnIndex);
        return bigDecimal == null ? null : bigDecimal.toBigInteger();
    }
}
