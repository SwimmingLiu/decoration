package com.lamp.foundation.api.extension.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeHandler<T> {

    default boolean useType(){
        return true;
    }

    default int[] independent(){
        return new int[]{0};
    }


    int[] types();

    void setParameter(PreparedStatement ps, int i, T parameter, TypeMetadata typeMetadata) throws SQLException;

    T getResult(ResultSet rs, String columnName, TypeMetadata typeMetadata) throws SQLException;

    T getResult(ResultSet rs, int columnIndex, TypeMetadata typeMetadata) throws SQLException;

    T getResult(CallableStatement cs, int columnIndex, TypeMetadata typeMetadata) throws SQLException;

}
