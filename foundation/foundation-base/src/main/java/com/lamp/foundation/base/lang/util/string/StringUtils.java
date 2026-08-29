package com.lamp.foundation.base.lang.util.string;

public class StringUtils {

    /**
     *  获得文件的文件类型
     */
    public static String suffix(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        return fileName.substring(index + 1);
    }




}
