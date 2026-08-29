package com.lamp.foundation.base.model;

import java.io.InputStream;

import com.lamp.foundation.api.model.ByteDecode;
import com.lamp.foundation.base.lang.util.string.StringByteConverter;

/**
 * @author hahaha
 */
public class DefaultByteDecode implements ByteDecode{

    public static ByteDecode of(byte[] data) {
        DefaultByteDecode base = new DefaultByteDecode();
        base.data = data;
        return base;
    }

    public static ByteDecode of(String data) {
        return of(data.getBytes());
    }

    /**
     *  TODO 1
     */
    public static ByteDecode of(InputStream data) {
        return null;
    }

    public static ByteDecode ofBase(String data) {
        return of(StringByteConverter.base64(data));
    }

    public static ByteDecode ofHex(String data) {
        return of(StringByteConverter.hex(data));
    }

    private byte[] data;

    @Override
    public byte[] data() {
        return data;

    }
}
