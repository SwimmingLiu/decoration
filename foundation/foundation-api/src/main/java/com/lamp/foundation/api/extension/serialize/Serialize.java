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

package com.lamp.foundation.api.extension.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

import com.lamp.foundation.api.enums.DataFormat;

public interface Serialize {

    String supplier();

    DataFormat dataFormat();

    String serialize(Object object);

    byte[] serializeByte(Object object);

    void serialize(Object object, OutputStream outputStream) throws IOException;

    <T> T deserialization(InputStream inputStream, Type t) throws IOException;

    <T> T deserialization(String data, Type t);

    <T> T deserialization(byte[] data, Type t);

}
