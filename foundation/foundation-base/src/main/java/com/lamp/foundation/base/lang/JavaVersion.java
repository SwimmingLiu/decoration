package com.lamp.foundation.base.lang;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.regex.Pattern;

public enum JavaVersion {


    JAVA_0_9(1.5f, "0.9"),

    JAVA_1_1(1.1f, "1.1"),

    JAVA_1_2(1.2f, "1.2"),

    JAVA_1_3(1.3f, "1.3"),

    JAVA_1_4(1.4f, "1.4"),

    JAVA_1_5(1.5f, "1.5"),

    JAVA_1_6(1.6f, "1.6"),

    JAVA_1_7(1.7f, "1.7"),

    JAVA_1_8(1.8f, "1.8"),

    JAVA_9(9.0f, "9"),

    JAVA_10(10.0f, "10"),

    JAVA_11(11.0f, "11"),

    JAVA_12(12.0f, "12"),

    JAVA_13(13.0f, "13"),

    JAVA_14(14.0f, "14"),

    JAVA_15(15.0f, "15"),

    JAVA_16(16.0f, "16"),

    JAVA_17(17.0f, "17"),

    JAVA_18(18.0f, "18"),

    JAVA_19(19.0f, "19"),

    JAVA_20(20, "20"),

    JAVA_21(21, "21"),

    JAVA_22(22, "22"),

    JAVA_23(23, "23"),

    JAVA_24(24, "24"),

    JAVA_25(25, "25"),

    JAVA_26(26, "26"),

    JAVA_RECENT(maxVersion(), Float.toString(maxVersion()));



    static JavaVersion get(final String versionStr) {
        if (versionStr == null) {
            return null;
        }
        switch (versionStr) {
            case "0.9":
                return JAVA_0_9;
            case "1.1":
                return JAVA_1_1;
            case "1.2":
                return JAVA_1_2;
            case "1.3":
                return JAVA_1_3;
            case "1.4":
                return JAVA_1_4;
            case "1.5":
                return JAVA_1_5;
            case "1.6":
                return JAVA_1_6;
            case "1.7":
                return JAVA_1_7;
            case "1.8":
                return JAVA_1_8;
            case "9":
                return JAVA_9;
            case "10":
                return JAVA_10;
            case "11":
                return JAVA_11;
            case "12":
                return JAVA_12;
            case "13":
                return JAVA_13;
            case "14":
                return JAVA_14;
            case "15":
                return JAVA_15;
            case "16":
                return JAVA_16;
            case "17":
                return JAVA_17;
            case "18":
                return JAVA_18;
            case "19":
                return JAVA_19;
            case "20":
                return JAVA_20;
            case "21":
                return JAVA_21;
            case "22":
                return JAVA_22;
            case "23":
                return JAVA_23;
            case "24":
                return JAVA_24;
            case "25":
                return JAVA_25;
            default:
                final float v = toFloatVersion(versionStr);
                if (v - 1. < 1.) { // then we need to check decimals > .9
                    final int firstComma = Math.max(versionStr.indexOf('.'), versionStr.indexOf(','));
                    final int end = Math.max(versionStr.length(), versionStr.indexOf(',', firstComma));
                    if (Float.parseFloat(versionStr.substring(firstComma + 1, end)) > .9f) {
                        return JAVA_RECENT;
                    }
                } else if (v > 10) {
                    return JAVA_RECENT;
                }
                return null;
        }
    }

    private static float maxVersion() {
        final float v = toFloatVersion(SystemProperties.getJavaSpecificationVersion("99.0"));
        return v > 0 ? v : 99f;
    }

    static String[] split(final String value) {
        return Pattern.compile("\\.").split(value);
    }

    /**
     * Parses a float value from a String.
     *
     * @param value the String to parse.
     * @return the float value represented by the string or -1 if the given String cannot be parsed.
     */
    private static float toFloatVersion(final String value) {
        final int defaultReturnValue = -1;
        if (!value.contains(".")) {
            return NumberUtils.toFloat(value, defaultReturnValue);
        }
        final String[] toParse = split(value);
        if (toParse.length >= 2) {
            return NumberUtils.toFloat(toParse[0] + '.' + toParse[1], defaultReturnValue);
        }
        return defaultReturnValue;
    }

    private final float value;

    private final String name;

    JavaVersion(final float value, final String name) {
        this.value = value;
        this.name = name;
    }

    public boolean atLeast(JavaVersion requiredVersion) {
        return this.value >= requiredVersion.value;
    }

    public boolean atMost(final JavaVersion requiredVersion) {
        return this.value <= requiredVersion.value;
    }

    @Override
    public String toString() {
        return name;
    }
}


