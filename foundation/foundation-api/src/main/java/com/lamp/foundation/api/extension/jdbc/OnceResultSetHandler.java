package com.lamp.foundation.api.extension.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface OnceResultSetHandler {

    LongResultSetHandler LONG_RESULT_SET_HANDLER = new LongResultSetHandler();

    IntResultSetHandler INT_RESULT_SET_HANDLER = new IntResultSetHandler();

    StringResultSetHandler STRING_RESULT_SET_HANDLER = new StringResultSetHandler();

    Object handler(ResultSet resultSet) throws SQLException;


    class LongResultSetHandler implements OnceResultSetHandler {

        @Override
        public Long handler(ResultSet resultSet) throws SQLException {
            return resultSet.getLong(1);
        }
    }

    class IntResultSetHandler implements OnceResultSetHandler {

        @Override
        public Integer handler(ResultSet resultSet) throws SQLException {
            return resultSet.getInt(1);
        }
    }

    class StringResultSetHandler implements OnceResultSetHandler {

        @Override
        public String handler(ResultSet resultSet) throws SQLException {
            return resultSet.getString(1);
        }
    }

}
