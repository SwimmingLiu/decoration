package com.lamp.foundation.base.extension.jdbc;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.lamp.foundation.api.extension.jdbc.JDBCOperation;
import com.lamp.foundation.api.extension.jdbc.OnceResultSetHandler;
import com.lamp.foundation.api.extension.jdbc.ResultSetHandler;

public class JDBCOperationImpl implements JDBCOperation {

    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    private final JDBCService jdbcService = new JDBCService();

    private final DataSource dataSource;

    public JDBCOperationImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        Connection connection = THREAD_LOCAL.get();
        if (connection == null) {
            connection = dataSource.getConnection();
            THREAD_LOCAL.set(connection);
        }
        return connection;
    }

    @Override
    public JDBCOperation setAutoCommit(boolean auto) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(auto);
        THREAD_LOCAL.set(connection);
        return this;
    }

    @Override
    public JDBCOperation commit() throws SQLException {
        Connection connection = THREAD_LOCAL.get();
        if (Objects.isNull(connection)) {
            return this;
        }
        THREAD_LOCAL.remove();
        connection.commit();
        return this;
    }

    @Override
    public JDBCOperation rollback() throws SQLException {
        Connection connection = THREAD_LOCAL.get();
        if (Objects.isNull(connection)) {
            return this;
        }
        THREAD_LOCAL.remove();
        connection.rollback();
        return this;
    }

    @Override
    public JDBCOperation readOnly() throws SQLException {
        Connection connection = THREAD_LOCAL.get();
        connection.setReadOnly(true);
        return this;
    }

    @Override
    public int update(String sql, List<Object> objects) throws SQLException {
        return (int) this.executeStatement(sql, objects, true, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T query(String sql, List<Object> objects, ResultSetHandler resultSetHandler) throws SQLException {
        return (T) this.executeStatement(sql, objects, false, resultSetHandler);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> queryByList(String sql, List<Object> objects, ResultSetHandler resultSetHandler) throws SQLException {
        return (List<T>) this.executeStatement(sql, objects, false, resultSetHandler);
    }

    @Override
    public ResultSetHandler newInstance(Class<?> clazz, boolean onceResultSet, OnceResultSetHandler onceResultSetHandler) {
        DefaultResultSetHandler defaultResultSetHandler = DefaultResultSetHandler.newInstance(clazz, onceResultSet, onceResultSetHandler);
        defaultResultSetHandler.setJdbcService(this.jdbcService);
        return defaultResultSetHandler;
    }


    private Object executeStatement(String sql, List<Object> objects, boolean update, ResultSetHandler resultSetHandler) throws SQLException {
        try (Connection connection = this.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            if (Objects.nonNull(objects) && !objects.isEmpty()) {
                for (int i = 0, size = objects.size(); i < size; i++) {
                    prepareStatement.setObject(i + 1, objects.get(i));
                }
            }
            if (update) {
                if (StringUtils.startsWith(sql, "insert into") || StringUtils.startsWith(sql, "INSERT INTO")) {
                    int count = prepareStatement.executeUpdate();
                    try (ResultSet keys = prepareStatement.getGeneratedKeys()) {
                        if (Objects.isNull(keys)) {
                            return count;
                        }
                        ResultSetHandler keysResultSetHandler =
                            DefaultResultSetHandler.newInstance(null, false, OnceResultSetHandler.LONG_RESULT_SET_HANDLER);
                        keysResultSetHandler.handler(keys);
                        return keysResultSetHandler.handler(keys);
                    }
                } else {
                    return prepareStatement.executeUpdate();
                }
            }
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                return resultSetHandler.handler(resultSet);
            }
        } catch (SQLException e) {
            this.rollback();
            String message = "SQL: " + sql + "\n" + e.getMessage();
            throw new SQLException(message, e);
        } finally {
            THREAD_LOCAL.remove();
        }
    }
}
