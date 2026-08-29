package com.lamp.foundation.base.lang.util.string;

import java.util.Base64;

import com.lamp.foundation.base.lang.forward.Hex17;

/**
 * 字符串 与 字符数组 的转换
 * </p>
 *  支持 base 64 ， 十六进制
 * @author hahaha
 */
public class StringByteConverter {

    private static Hex hex;

    public static String base64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] base64(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static String hex(byte[] data) {
        return hex.encode(data);
    }

    public static byte[] hex(String data) {
        return hex.decode(data);
    }

    static {
        try {
            Class.forName("java.util.HexFormat");
            hex = new Hex17();
        } catch (ClassNotFoundException e) {
            hex = new LampHex();
        }
    }

    /**
     * byte array 转 十六进制
     */
    public interface Hex {

        String encode(byte[] bytes);

        byte[] decode(String string);

    }

    /**
     * lamp 实现的 byte array 转 十六进制
     */
    private static class LampHex implements Hex {

        @Override
        public String encode(byte[] bytes) {
            if (bytes == null || bytes.length == 0) {
                return "";
            }
            char[] hexChars = new char[bytes.length * 2];
            for (int i = 0; i < bytes.length; i++) {
                int v = bytes[i] & 0xFF;
                hexChars[i * 2] = toHexChar(v >>> 4);
                hexChars[i * 2 + 1] = toHexChar(v & 0x0F);
            }
            return new String(hexChars);
        }

        @Override
        public byte[] decode(String hex) {
            if (hex == null || hex.isEmpty()) {
                return new byte[0];
            }
            if (hex.length() % 2 != 0) {
                throw new IllegalArgumentException("Hex string must have even length");
            }

            int len = hex.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                int high = Character.digit(hex.charAt(i), 16);
                int low = Character.digit(hex.charAt(i + 1), 16);
                if (high == -1 || low == -1) {
                    throw new IllegalArgumentException("Invalid hex character at index: " + i);
                }
                data[i / 2] = (byte) ((high << 4) + low);
            }
            return data;
        }

        private char toHexChar(int value) {
            if (value < 10) {
                return (char) ('0' + value);
            } else {
                return (char) ('A' + value - 10);
            }
        }
    }

}
