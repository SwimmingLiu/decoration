package com.lamp.foundation.api.extension.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OnceMetadataResultSetHandler {

    MapOnceMetadataResultSetHandler MAP_ONCE_METADATA_RESULT_SET_HANDLER = new MapOnceMetadataResultSetHandler();

    Object handler(ResultSet resultSet, List<String> columnName, int index) throws SQLException;


    class OnecExecuteResultSetHandler implements OnceMetadataResultSetHandler {

        private OnceResultSetHandler onceResultSetHandler;

        public OnecExecuteResultSetHandler() {
        }

        public OnecExecuteResultSetHandler(OnceResultSetHandler onceResultSetHandler) {
            this.onceResultSetHandler = onceResultSetHandler;
        }

        @Override
        public Object handler(ResultSet resultSet, List<String> columnName, int index) throws SQLException {
            return this.onceResultSetHandler.handler(resultSet);
        }
    }

    class MapOnceMetadataResultSetHandler implements OnceMetadataResultSetHandler {

        @Override
        public Object handler(ResultSet resultSet, List<String> columnName, int index) throws SQLException {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < columnName.size(); i++) {
                map.put(columnName.get(i), resultSet.getObject(i + 1));
            }
            return map;
        }
    }
}
