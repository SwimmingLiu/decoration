package com.lamp.foundation.base.extension.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class MapResultSetHandler extends AbstractResultSetHandler {

    public static final  MapResultSetHandler INSTANCE = new MapResultSetHandler();

    @Override
    public List<Object> handler(ResultSet rs) throws SQLException {
        List<Object> list = new ArrayList<>();
        List<String> nameList = this.metadata(rs);
        while (rs.next()) {
            Map<String, Object> map = new HashMap<>();
            list.add(map);
            for (int i = 0; i < nameList.size(); i++) {
                map.put(nameList.get(i), rs.getObject(i + 1));
            }
        }
        return list;
    }
}
