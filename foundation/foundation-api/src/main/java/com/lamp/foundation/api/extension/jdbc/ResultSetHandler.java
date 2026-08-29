package com.lamp.foundation.api.extension.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler {

    Object handler(ResultSet rs) throws SQLException;


}
