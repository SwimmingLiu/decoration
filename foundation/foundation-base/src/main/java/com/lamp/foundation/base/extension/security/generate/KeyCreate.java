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

import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * KeyGenerator AES 	Key generator for use with the AES algorithm. ARCFOUR 	Key generator for use with the ARCFOUR (RC4) algorithm. Blowfish 	Key
 * generator for use with the Blowfish algorithm. DES 	Key generator for use with the DES algorithm. DESede 	Key generator for use with the DESede
 * (triple-DES) algorithm. HmacMD5 	Key generator for use with the HmacMD5 algorithm. HmacSHA1 HmacSHA224 HmacSHA256 HmacSHA384 HmacSHA512 	Keys
 * generator for use with the various flavors of the HmacSHA algorithms. RC2 	        Key generator for use with the RC2 algorithm.
 * <p>
 * SecretKeyFactory AES 	Constructs secret keys for use with the AES algorithm. ARCFOUR 	Constructs secret keys for use with the ARCFOUR algorithm.
 * DES 	Constructs secrets keys for use with the DES algorithm. DESede 	Constructs secrets keys for use with the DESede (Triple-DES) algorithm.
 * PBEWith  And PBEWith  And 	Secret-key factory for use with PKCS5 password-based encryption, where  is a message digest, is a pseudo-random
 * function, and  is an encryption algorithm. Examples: PBEWithMD5AndDES (PKCS #5, 1.5), PBEWithHmacSHA256AndAES_128 (PKCS #5, 2.0) Note: These all
 * use only the low order 8 bits of each password character. PBKDF2With 	PBKDF2With 	Password-based key-derivation algorithm found in PKCS #5 2.0
 * using the specified pseudo-random function (). Example: PBKDF2WithHmacSHA256.
 *
 * @author muqi
 */
public class KeyCreate<T extends SecretKey> {

    private SecretKey key;

    private String algorithm;

    private int length;


    public KeyCreate(String algorithm, int length) throws NoSuchAlgorithmException, InvalidParameterException {
        this.algorithm = algorithm;
        this.length = length;
    }

    public SecretKey getKey() throws NoSuchAlgorithmException {
        this.createKey();
        return key;
    }

    private void createKey() throws NoSuchAlgorithmException, InvalidParameterException {
        // 2. 创建安全的随机数生成器
        // getInstanceStrong() 通常推荐使用，因为它会选择系统上最强的可用算法
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        KeyGenerator keyGenerator = KeyGenerator.getInstance(this.algorithm);
        keyGenerator.init(this.length, secureRandom);
        this.key = keyGenerator.generateKey();
    }

}
