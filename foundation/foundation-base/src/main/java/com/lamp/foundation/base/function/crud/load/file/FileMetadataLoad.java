package com.lamp.foundation.base.function.crud.load.file;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import com.lamp.foundation.api.extension.databases.metadata.TableInfo;

public class FileMetadataLoad {


    public List<TableInfo> formMetadtata;

    private String context;


    public void build(String context) {
        context = context.toLowerCase();
        String[] sqlArray = context.split(";");
        for (String sql : sqlArray) {
            sql = StringUtils.strip(sql);
            if (sql.startsWith("create")) {
                int bodyStart = sql.indexOf("(");
                String tableHead = sql.substring(bodyStart);
                String body = sql.substring(bodyStart + 1, sql.length() - 1);
                String[] cArray = body.split(",");

                for (String c : cArray) {
                    c = StringUtils.strip(c);
                    if(c.startsWith("key") || c.startsWith("primary") || c.startsWith("u")) {
                        // 当做key 处理
                    }
                }
            }

        }
        int index = 0;
        do {
            int createIndex = context.indexOf("create", index);
            if (createIndex == -1) {
                return;
            }
            int createEndIndex = context.indexOf(")", createIndex);
            if (createEndIndex == -1) {
                return;
            }
            context = context.substring(createIndex + 1, createEndIndex);

        } while (true);
    }

}
