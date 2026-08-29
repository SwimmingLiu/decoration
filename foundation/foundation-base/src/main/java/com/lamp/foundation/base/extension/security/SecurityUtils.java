package com.lamp.foundation.base.extension.security;

import java.security.Security;
import java.util.Set;

public class SecurityUtils {

    public static Set<String> SUPPORT_MESSAGEDIGEST = Security.getAlgorithms("MessageDigest");


    public static boolean isMessageDigest(String algorithm) {
        return SUPPORT_MESSAGEDIGEST.contains(algorithm);
    }
}
