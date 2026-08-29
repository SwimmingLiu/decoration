/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */

package com.lamp.decoration.core.databases.mybatis;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.sql.DataSource;

/**
 * 1. 编写期间可以使用 spring配置的内容。开发期间，可以识别 target 目录，读取文件 2. 构建期间的 test，通过读取环境变量识别 构建期间 3.
 *
 * @author laohu
 */
@SuppressWarnings("unchecked")
public class TestMyBatis<T> {

    protected T mapper;

    protected SQLAndParameterWrapper sqlAndParameterWrapper;

    {
        SqlSessionFactory sqlSessionFactory =
            new SqlSessionFactoryBuilder().build(getConfiguration());
        Class<?> clazz = getClazz();
        mapper = (T) sqlSessionFactory.openSession().getMapper(clazz);
        try {
            testMapper(this.getClazz(), sqlSessionFactory);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    private void testMapper(Class<?> clazz, SqlSessionFactory sqlSessionFactory) throws ClassNotFoundException {
        TestMapper testMapper = clazz.getAnnotation(TestMapper.class);
        for (Class<?> dependent : testMapper.dependent()) {
            try {
                sqlSessionFactory.openSession().getMapper(dependent);
            } catch (Exception e) {
                throw new RuntimeException("load mapper fail " + dependent, e);
            }
        }
        for (String script : testMapper.script()) {
            try {
                InputStream input = clazz.getClassLoader().getResourceAsStream(script);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[2048];
                int i = 0;
                while ((i = input.read(bytes)) > -1) {
                    outputStream.write(bytes, 0, i);
                }
                sqlSessionFactory.openSession().insert(outputStream.toString());
            } catch (Exception e) {
                throw new RuntimeException("load script fail " + script + "\r\n" + e.getMessage(), e);
            }
        }
    }

    /**
     *
     */
    private Configuration getConfiguration() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("Production", transactionFactory, this.getDataSource());
        Configuration configuration = new Configuration(environment);
        configuration.setLazyLoadingEnabled(true);
        // to test legacy style reference (#{0} #{1})
        configuration.setUseActualParamName(false);
        configuration.setUseGeneratedKeys(true);
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }

    /**
     * 拦截 UnpooledDataSource 的 连接，代理 这样可以获得 sql，以及结果参数
     */
    private DataSource getDataSource() {
        UnpooledDataSource dataSource = new DecorationUnpooledDataSource();
        dataSource.setDriver("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:.");
        // mem: 内存模式， rdrs 内存数据名字，
        //dataSource.setUrl("jdbc:hsqldb:mem:rdrs");
        return dataSource;
    }

    /**
     *
     */
    private Class<?> getClazz() {
        Type superClass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    private void setSqlAndParameterWrapper(String sql) {
        this.sqlAndParameterWrapper = new SQLAndParameterWrapper();
        this.sqlAndParameterWrapper.sql = sql;
    }

    private void setParameter(Object object) {
        this.sqlAndParameterWrapper.parameter.add(object);
    }

    private TestPreparedStatement buildTestPreparedStatement(PreparedStatement preparedStatement) {
        TestPreparedStatement testPreparedStatement = new TestPreparedStatement();
        testPreparedStatement.proxy = preparedStatement;
        return testPreparedStatement;
    }

    protected void assertSQL(String sql, List<Object> parameter) {
        this.assertSql(sql);
        this.assertParameter(parameter);
    }

    protected void assertSql(String sql) {
        if (!Objects.equals(sql, this.sqlAndParameterWrapper.sql)) {
            throw new RuntimeException("sql");
        }
    }

    protected void assertParameter(List<Object> parameter) {
        for (int i = 0; i < parameter.size(); i++) {
            if (!Objects.equals(parameter.get(i), this.sqlAndParameterWrapper.parameter.get(i))) {
                throw new RuntimeException("sql");
            }
        }
    }

    static class SQLAndParameterWrapper {

        private String sql;

        private List<Object> parameter = new ArrayList<>();
    }

    public class DecorationUnpooledDataSource extends UnpooledDataSource {


        @Override
        public Connection getConnection() throws SQLException {
            Connection connection = super.getConnection();
            ProxyConnection proxyConnection = new ProxyConnection();
            proxyConnection.proxy = connection;
            return proxyConnection;
        }

    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    public class ProxyConnection implements Connection {

        private Connection proxy;

        @Override
        public Statement createStatement() throws SQLException {
            return proxy.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return proxy.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return proxy.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return proxy.nativeSQL(sql);
        }

        @SuppressWarnings("TypeParameterHidesVisibleType")
        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return this.proxy.unwrap(iface);
        }        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            this.proxy.setAutoCommit(autoCommit);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return this.proxy.isWrapperFor(iface);
        }        @Override
        public boolean getAutoCommit() throws SQLException {
            return this.proxy.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            this.proxy.commit();
        }

        @Override
        public void rollback() throws SQLException {
            this.proxy.rollback();
        }

        @Override
        public void close() throws SQLException {
            this.proxy.close();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return this.proxy.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return this.proxy.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            this.proxy.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return this.proxy.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            this.proxy.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return this.proxy.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            this.proxy.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return this.proxy.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return this.proxy.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            this.proxy.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return this.proxy.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return buildTestPreparedStatement(this.proxy.prepareStatement(sql, resultSetType, resultSetConcurrency));
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return this.proxy.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return this.proxy.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            this.proxy.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            this.proxy.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return this.proxy.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return this.proxy.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return this.proxy.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            this.proxy.rollback();
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            this.proxy.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return this.proxy.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
            setSqlAndParameterWrapper(sql);
            return buildTestPreparedStatement(this.proxy.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return this.proxy.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return buildTestPreparedStatement(this.proxy.prepareStatement(sql, autoGeneratedKeys));
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return buildTestPreparedStatement(this.proxy.prepareStatement(sql, columnIndexes));
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return buildTestPreparedStatement(this.proxy.prepareStatement(sql, columnNames));
        }

        @Override
        public Clob createClob() throws SQLException {
            return this.proxy.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return this.proxy.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return this.proxy.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return this.proxy.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return this.proxy.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            this.proxy.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            this.proxy.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return this.proxy.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return this.proxy.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return this.proxy.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return this.proxy.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            this.proxy.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return this.proxy.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            this.proxy.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            this.proxy.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return this.proxy.getNetworkTimeout();
        }




    }

    @SuppressWarnings({"SqlSourceToSinkFlow", "AliDeprecation"})
    private class TestPreparedStatement implements PreparedStatement {

        private PreparedStatement proxy;


        @Override
        public ResultSet executeQuery() throws SQLException {
            return this.proxy.executeQuery();
        }

        @Override
        public int executeUpdate() throws SQLException {
            return this.proxy.executeUpdate();
        }

        @Override
        public void setNull(int parameterIndex, int sqlType) throws SQLException {
            setParameter(null);
            this.proxy.setNull(parameterIndex, sqlType);
        }

        @Override
        public void setBoolean(int parameterIndex, boolean x) throws SQLException {
            setParameter(x);
            this.proxy.setBoolean(parameterIndex, x);
        }

        @Override
        public void setByte(int parameterIndex, byte x) throws SQLException {
            setParameter(x);
            this.proxy.setByte(parameterIndex, x);
        }

        @Override
        public void setShort(int parameterIndex, short x) throws SQLException {
            setParameter(x);
            this.proxy.setShort(parameterIndex, x);
        }

        @Override
        public void setInt(int parameterIndex, int x) throws SQLException {
            setParameter(x);
            this.proxy.setInt(parameterIndex, x);
        }

        @Override
        public void setLong(int parameterIndex, long x) throws SQLException {
            setParameter(x);
            this.proxy.setLong(parameterIndex, x);
        }

        @Override
        public void setFloat(int parameterIndex, float x) throws SQLException {
            setParameter(x);
            this.proxy.setFloat(parameterIndex, x);
        }

        @Override
        public void setDouble(int parameterIndex, double x) throws SQLException {
            setParameter(x);
            this.proxy.setDouble(parameterIndex, x);
        }

        @Override
        public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
            setParameter(x);
            this.proxy.setBigDecimal(parameterIndex, x);
        }

        @Override
        public void setString(int parameterIndex, String x) throws SQLException {
            setParameter(x);
            this.proxy.setString(parameterIndex, x);
        }

        @Override
        public void setBytes(int parameterIndex, byte[] x) throws SQLException {
            setParameter(x);
            this.proxy.setBytes(parameterIndex, x);
        }

        @Override
        public void setDate(int parameterIndex, Date x) throws SQLException {
            setParameter(x);
            this.proxy.setDate(parameterIndex, x);
        }

        @Override
        public void setTime(int parameterIndex, Time x) throws SQLException {
            setParameter(x);
            this.proxy.setTime(parameterIndex, x);
        }

        @Override
        public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
            setParameter(x);
            this.proxy.setTimestamp(parameterIndex, x);
        }

        @Override
        public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
            setParameter(x);
            this.proxy.setAsciiStream(parameterIndex, x, length);
        }

        @Override
        public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
            setParameter(x);
            this.proxy.setUnicodeStream(parameterIndex, x, length);
        }

        @Override
        public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
            setParameter(x);
            this.proxy.setBinaryStream(parameterIndex, x, length);
        }

        @Override
        public void clearParameters() throws SQLException {
            this.proxy.clearParameters();
        }

        @Override
        public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
            setParameter(x);
            this.proxy.setObject(parameterIndex, x, targetSqlType);
        }

        @Override
        public void setObject(int parameterIndex, Object x) throws SQLException {
            setParameter(x);
            this.proxy.setObject(parameterIndex, x);
        }

        @Override
        public boolean execute() throws SQLException {
            return this.proxy.execute();
        }

        @Override
        public void addBatch() throws SQLException {
            this.proxy.addBatch();
        }

        @Override
        public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
            setParameter(reader);
            this.proxy.setCharacterStream(parameterIndex, reader, length);
        }

        @Override
        public void setRef(int parameterIndex, Ref x) throws SQLException {
            setParameter(x);
            this.proxy.setRef(parameterIndex, x);
        }

        @Override
        public void setBlob(int parameterIndex, Blob x) throws SQLException {
            setParameter(x);
            this.proxy.setBlob(parameterIndex, x);
        }

        @Override
        public void setClob(int parameterIndex, Clob x) throws SQLException {
            setParameter(x);
            this.proxy.setClob(parameterIndex, x);
        }

        @Override
        public void setArray(int parameterIndex, Array x) throws SQLException {
            setParameter(x);
            this.proxy.setArray(parameterIndex, x);
        }

        @Override
        public ResultSetMetaData getMetaData() throws SQLException {
            return this.proxy.getMetaData();
        }

        @Override
        public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
            setParameter(x);
            this.proxy.setDate(parameterIndex, x, cal);
        }

        @Override
        public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
            setParameter(x);
            this.proxy.setTime(parameterIndex, x, cal);
        }

        @Override
        public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
            setParameter(x);
            this.proxy.setTimestamp(parameterIndex, x, cal);
        }

        @Override
        public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
            setParameter(null);
            this.proxy.setNull(parameterIndex, sqlType, typeName);
        }

        @Override
        public void setURL(int parameterIndex, URL x) throws SQLException {
            setParameter(x);
            this.proxy.setURL(parameterIndex, x);
        }

        @Override
        public ParameterMetaData getParameterMetaData() throws SQLException {
            return this.proxy.getParameterMetaData();
        }

        @Override
        public void setRowId(int parameterIndex, RowId x) throws SQLException {
            setParameter(x);
            this.proxy.setRowId(parameterIndex, x);
        }

        @Override
        public void setNString(int parameterIndex, String value) throws SQLException {
            setParameter(value);
            this.proxy.setNString(parameterIndex, value);
        }

        @Override
        public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
            setParameter(value);
            this.proxy.setNCharacterStream(parameterIndex, value, length);
        }

        @Override
        public void setNClob(int parameterIndex, NClob value) throws SQLException {
            setParameter(value);
            this.proxy.setNClob(parameterIndex, value);
        }

        @Override
        public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
            setParameter(reader);
            this.proxy.setClob(parameterIndex, reader, length);
        }

        @Override
        public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
            setParameter(inputStream);
            this.proxy.setBlob(parameterIndex, inputStream, length);
        }

        @Override
        public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
            setParameter(reader);
            this.proxy.setNClob(parameterIndex, reader, length);
        }

        @Override
        public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
            setParameter(xmlObject);
            this.proxy.setSQLXML(parameterIndex, xmlObject);
        }

        @Override
        public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
            setParameter(x);
            this.proxy.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
        }

        @Override
        public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
            setParameter(x);
            this.proxy.setAsciiStream(parameterIndex, x, length);
        }

        @Override
        public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
            setParameter(x);
            this.proxy.setBinaryStream(parameterIndex, x, length);
        }

        @Override
        public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
            setParameter(reader);
            this.proxy.setCharacterStream(parameterIndex, reader, length);
        }

        @Override
        public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
            setParameter(x);
            this.proxy.setAsciiStream(parameterIndex, x);
        }

        @Override
        public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
            setParameter(x);
            this.proxy.setBinaryStream(parameterIndex, x);
        }

        @Override
        public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
            setParameter(reader);
            this.proxy.setCharacterStream(parameterIndex, reader);
        }

        @Override
        public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
            setParameter(value);
            this.proxy.setNCharacterStream(parameterIndex, value);
        }

        @Override
        public void setClob(int parameterIndex, Reader reader) throws SQLException {
            setParameter(reader);
            this.proxy.setClob(parameterIndex, reader);
        }

        @Override
        public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
            setParameter(inputStream);
            this.proxy.setBlob(parameterIndex, inputStream);
        }

        @Override
        public void setNClob(int parameterIndex, Reader reader) throws SQLException {
            setParameter(reader);
            this.proxy.setNClob(parameterIndex, reader);
        }

        @Override
        public ResultSet executeQuery(String sql) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return this.proxy.executeQuery(sql);
        }

        @Override
        public int executeUpdate(String sql) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return this.proxy.executeUpdate(sql);
        }

        @Override
        public void close() throws SQLException {
            this.proxy.close();
        }

        @Override
        public int getMaxFieldSize() throws SQLException {
            return this.proxy.getMaxFieldSize();
        }

        @Override
        public void setMaxFieldSize(int max) throws SQLException {
            this.proxy.setMaxFieldSize(max);
        }

        @Override
        public int getMaxRows() throws SQLException {
            return this.proxy.getMaxRows();
        }

        @Override
        public void setMaxRows(int max) throws SQLException {
            this.proxy.setMaxRows(max);
        }

        @Override
        public void setEscapeProcessing(boolean enable) throws SQLException {
            this.proxy.setEscapeProcessing(enable);
        }

        @Override
        public int getQueryTimeout() throws SQLException {
            return this.proxy.getQueryTimeout();
        }

        @Override
        public void setQueryTimeout(int seconds) throws SQLException {
            this.proxy.setQueryTimeout(seconds);
        }

        @Override
        public void cancel() throws SQLException {
            this.proxy.cancel();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return this.proxy.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            this.proxy.clearWarnings();
        }

        @Override
        public void setCursorName(String name) throws SQLException {
            this.proxy.setCursorName(name);
        }

        @Override
        public boolean execute(String sql) throws SQLException {
            return this.proxy.execute(sql);
        }

        @Override
        public ResultSet getResultSet() throws SQLException {
            return this.proxy.getResultSet();
        }

        @Override
        public int getUpdateCount() throws SQLException {
            return this.proxy.getUpdateCount();
        }

        @Override
        public boolean getMoreResults() throws SQLException {
            return this.proxy.getMoreResults();
        }

        @SuppressWarnings("TypeParameterHidesVisibleType")
        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return this.proxy.unwrap(iface);
        }        @Override
        public void setFetchDirection(int direction) throws SQLException {
            this.proxy.setFetchDirection(direction);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return this.proxy.isWrapperFor(iface);
        }        @Override
        public int getFetchDirection() throws SQLException {
            return this.proxy.getFetchDirection();
        }

        @Override
        public void setFetchSize(int rows) throws SQLException {
            this.proxy.setFetchSize(rows);
        }

        @Override
        public int getFetchSize() throws SQLException {
            return this.proxy.getFetchSize();
        }

        @Override
        public int getResultSetConcurrency() throws SQLException {
            return this.proxy.getResultSetConcurrency();
        }

        @Override
        public int getResultSetType() throws SQLException {
            return this.proxy.getResultSetType();
        }

        @Override
        public void addBatch(String sql) throws SQLException {
            setSqlAndParameterWrapper(sql);
            this.proxy.addBatch(sql);
        }

        @Override
        public void clearBatch() throws SQLException {
            this.proxy.clearBatch();
        }

        @Override
        public int[] executeBatch() throws SQLException {
            return this.proxy.executeBatch();
        }

        @Override
        public Connection getConnection() throws SQLException {
            return this.proxy.getConnection();
        }

        @Override
        public boolean getMoreResults(int current) throws SQLException {
            return this.proxy.getMoreResults();
        }

        @Override
        public ResultSet getGeneratedKeys() throws SQLException {
            return this.proxy.getGeneratedKeys();
        }

        @Override
        public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return this.proxy.executeUpdate(sql, autoGeneratedKeys);
        }

        @Override
        public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return this.proxy.executeUpdate(sql, columnIndexes);
        }

        @Override
        public int executeUpdate(String sql, String[] columnNames) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return this.proxy.executeUpdate(sql, columnNames);
        }

        @Override
        public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return this.proxy.execute(sql, autoGeneratedKeys);
        }

        @Override
        public boolean execute(String sql, int[] columnIndexes) throws SQLException {
            setSqlAndParameterWrapper(sql);
            return this.proxy.execute(sql, columnIndexes);
        }

        @Override
        public boolean execute(String sql, String[] columnNames) throws SQLException {
            return this.proxy.execute(sql, columnNames);
        }

        @Override
        public int getResultSetHoldability() throws SQLException {
            return this.proxy.getResultSetHoldability();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return this.proxy.isClosed();
        }

        @Override
        public void setPoolable(boolean poolable) throws SQLException {
            this.proxy.setPoolable(poolable);
        }

        @Override
        public boolean isPoolable() throws SQLException {
            return this.proxy.isPoolable();
        }

        @Override
        public void closeOnCompletion() throws SQLException {
            this.proxy.closeOnCompletion();
        }

        @Override
        public boolean isCloseOnCompletion() throws SQLException {
            return this.proxy.isCloseOnCompletion();
        }




    }

}
