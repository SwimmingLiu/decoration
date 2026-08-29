package com.lamp.decoration.foundation.network.hmr;


import java.util.Set;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;


public class HmrProtocolBuilder {


    /**
     * 构造批量更新消息
     */
    public static String buildUpdateMessage(Set<String> paths) {
        JSONObject payload = new JSONObject();
        payload.put("type", "update");

        JSONArray updates = new JSONArray();
        long timestamp = System.currentTimeMillis();

        for (String path : paths) {
            JSONObject updateInfo = new JSONObject();
            // 简单判断类型，实际可根据后缀区分 js-update / css-update
            String type = path.endsWith(".css") ? "css-update" : "js-update";

            updateInfo.put("type", type);
            updateInfo.put("path", path);
            updateInfo.put("acceptedPath", path); // 关键：假设模块接受自身更新
            updateInfo.put("timestamp", timestamp);
            updates.add(updateInfo);
        }

        payload.put("updates", updates);
        return payload.toString();
    }

    /**
     * 构造全量刷新消息
     */
    public static String buildFullReloadMessage() {
        JSONObject payload = new JSONObject();
        payload.put("type", "full-reload");
        payload.put("timestamp", System.currentTimeMillis());
        return payload.toString();
    }
}

