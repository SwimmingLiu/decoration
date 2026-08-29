package com.lamp.foundation.api.extension.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface JDBCOperation {

    JDBCOperation setAutoCommit(boolean auto) throws SQLException;

    JDBCOperation commit() throws SQLException;

    JDBCOperation rollback() throws SQLException;

    JDBCOperation readOnly() throws SQLException;


    int update(String sql, List<Object> objects) throws SQLException;


    default Map<String, Object> query(String sql, List<Object> objects) throws SQLException {
        return this.query(sql, Map.class, objects);
    }

    @SuppressWarnings("unchecked")
    default <T> List<Map<String, Object>> queryByList(String sql, List<Object> objects) throws SQLException {
        Class<T> clazz = (Class<T>) Map.class;
        return (List<Map<String, Object>>) this.queryByList(sql, clazz, objects);
    }

    default <T> T query(String sql, Class<T> clazz, List<Object> objects) throws SQLException {
        return this.query(sql, objects, newInstance(clazz, true, null));
    }

    default <T> List<T> queryByList(String sql, Class<T> clazz, List<Object> objects) throws SQLException {
        return this.queryByList(sql, objects, newInstance(clazz, false, null));
    }

    <T> T query(String sql, List<Object> objects, ResultSetHandler resultSetHandler) throws SQLException;

    <T> List<T> queryByList(String sql, List<Object> objects, ResultSetHandler resultSetHandler) throws SQLException;

    ResultSetHandler newInstance(Class<?> clazz, boolean onceResultSet, OnceResultSetHandler onceResultSetHandler);

    default <T> T queryFormOnce(String sql, List<Object> objects, OnceResultSetHandler resultSetHandler) throws SQLException {
        return this.query(sql, objects, newInstance(null, true, resultSetHandler));
    }

    default <T> List<T> queryByListFormOnce(String sql, List<Object> objects, OnceResultSetHandler resultSetHandler) throws SQLException {
        return this.query(sql, objects, newInstance(null, false, resultSetHandler));
    }


}
