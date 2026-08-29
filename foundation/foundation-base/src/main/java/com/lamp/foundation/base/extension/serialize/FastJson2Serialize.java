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

package com.lamp.foundation.base.extension.serialize;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

import com.alibaba.fastjson2.JSON;
import com.lamp.foundation.api.enums.DataFormat;
import com.lamp.foundation.api.extension.serialize.Serialize;

public class FastJson2Serialize implements Serialize {

    @Override
    public String supplier() {
        return "FastJson";
    }

    @Override
    public DataFormat dataFormat() {
        return DataFormat.JSON;
    }

    @Override
    public String serialize(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public byte[] serializeByte(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public void serialize(Object object, OutputStream outputStream) {
        JSON.writeTo(outputStream, object);
    }

    @Override
    public <T> T deserialization(InputStream inputStream,Type t) {
        return JSON.parseObject(inputStream, t);
    }

    @Override
    public <T> T deserialization(String data,Type t) {
        return JSON.parseObject(data, t);
    }

    @Override
    public <T> T deserialization(byte[] data,Type t) {
        return JSON.parseObject(data, t);
    }

}
