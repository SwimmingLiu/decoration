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

package com.lamp.foundation.base.extension.security.generate;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import com.lamp.foundation.api.model.ByteDecode;
import com.lamp.foundation.api.model.ByteEncode;


/**
 * <pre>
 * <a href="http://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html">...</a>
 * <a href="http://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Signature">...</a>
 * 加密模式有很多中。java文档中有多种， 每个平台至少支持 ecb，cbc 加密模式很好博客 ：
 * <a href="http://blog.csdn.net/fw0124/article/details/8472560">...</a>
 * AlgorithmParameters
 * 请看中文api文档
 *
 * 私钥加密，公钥解密
 * </pre>
 **/
public interface GenerateCipher {

    List<Integer> CIPHER_LENGTH = getCipherLength();

    List<String> WORKING_MODE = getWorkingMode();

    List<String> PADDING = getPadding();

    List<String> SYMMETRIC = getSymmetric();

    List<String> ASYMMETRIC = getAsymmetry();

    static String getAlgorithm(String algorithm) {
        return algorithm.indexOf('/') == -1 ? algorithm : algorithm.substring(0, algorithm.indexOf('/') - 1);
    }

    static List<String> getAsymmetry() {
        List<String> asymmetric = new ArrayList<>();
        asymmetric.add("DIFFIEHELLMAN");
        asymmetric.add("DSA");
        asymmetric.add("RSA");
        asymmetric.add("EC");
        return asymmetric;
    }

    static List<Integer> getCipherLength() {
        List<Integer> cipherLength = new ArrayList<>();
        int base = 32;
        for (int i = 1; i < 8; i++) {
            cipherLength.add(base << i);
        }
        return cipherLength;
    }

    static List<String> getPadding() {
        List<String> padding = new ArrayList<>();
        padding.add("NoPadding");
        padding.add("PKCS1Padding");
        padding.add("PKCS5Padding");
        padding.add("ISO10126Padding");
        padding.add("SSL3Padding");
        padding.add("OAEPPadding");
        return padding;
    }

    static List<String> getSymmetric() {
        List<String> symmetric = new ArrayList<>();
        symmetric.add("AES");
        symmetric.add("ARCFOUR");
        symmetric.add("BLOWFISH");
        symmetric.add("DES");
        symmetric.add("DESEDE");
        //symmetric.add( "HmacMD5" );
        //symmetric.add( "HmacSHA1" );
        //symmetric.add( "HmacSHA224" );
        //symmetric.add( "HmacSHA256" );
        //symmetric.add( "HmacSHA384" );
        //symmetric.add( "HmacSHA512" );
        symmetric.add("RC2");
        return symmetric;
    }

    static List<String> getWorkingMode() {
        List<String> workingMode = new ArrayList<>();
        workingMode.add("NONE");
        workingMode.add("CBC");
        workingMode.add("CCM");
        workingMode.add("CFB");
        workingMode.add("CFBx");
        workingMode.add("CTR");
        workingMode.add("CTS");
        workingMode.add("ECB");
        workingMode.add("GCM");
        workingMode.add("OFB");
        workingMode.add("OFBx");
        workingMode.add("PCBC");
        return workingMode;
    }

    static boolean isSymmetric(String algorithm) {
        if (SYMMETRIC.contains(algorithm)) {
            return true;
        } else if (ASYMMETRIC.contains(algorithm)) {
            return false;
        }
        throw new RuntimeException();
    }


    Cipher getPublicCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    Cipher getPrivateCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException;


    ByteEncode sign(ByteDecode byteDecode) throws NoSuchAlgorithmException, InvalidKeyException,
        InvalidAlgorithmParameterException, SignatureException;

    boolean verify(ByteDecode update, ByteDecode verify)
        throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException;
}
