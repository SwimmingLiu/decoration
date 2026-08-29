package com.lamp.foundation.base.lang.forward;

import java.util.HexFormat;

import com.lamp.foundation.base.lang.util.string.StringByteConverter.Hex;

public class Hex17 implements Hex {


    @Override
    public String encode(byte[] bytes) {
        return HexFormat.of().formatHex(bytes);
    }

    @Override
    public byte[] decode(String string) {
        return HexFormat.of().parseHex(string);
    }
}
