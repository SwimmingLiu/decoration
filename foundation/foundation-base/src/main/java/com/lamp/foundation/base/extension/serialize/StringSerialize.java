package com.lamp.foundation.base.extension.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

import com.lamp.foundation.api.enums.DataFormat;
import com.lamp.foundation.api.extension.serialize.Serialize;

public class StringSerialize implements Serialize {

    @Override
    public String supplier() {
        return "";
    }

    @Override
    public DataFormat dataFormat() {
        return DataFormat.STRING;
    }

    @Override
    public String serialize(Object object) {
        return (String) object;
    }

    @Override
    public byte[] serializeByte(Object object) {
        return this.serialize(object).getBytes();
    }

    @Override
    public void serialize(Object object, OutputStream outputStream) throws IOException {
        byte[] bytes = this.serializeByte(object);
        outputStream.write(bytes);
    }

    @Override
    public <T> T deserialization(InputStream inputStream, Type t) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialization(String data, Type t) {
        return (T) data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialization(byte[] data, Type t) {
        return (T) new String(data);
    }
}
