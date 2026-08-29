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
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Objects;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.lamp.foundation.api.security.SecurityBaseType.Asymmetric;
import com.lamp.foundation.api.security.model.CompleteEurvenInfo;

public class KeyPairCreate<T extends Key> {


    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

    }

    private KeyPair keyPair;

    private String algorithm;

    private int length;

    private CompleteEurvenInfo completeEurvenInfo;

    public KeyPairCreate(String algorithm, int length) {
        this.algorithm = algorithm;
        this.length = length;
    }


    public KeyPairCreate(Asymmetric asymmetric) {
        this(CompleteEurvenInfo.of(asymmetric));
    }

    public KeyPairCreate(CompleteEurvenInfo completeEurvenInfo) {

    }

    @SuppressWarnings("unchecked")
    public T getPublicKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {
        createKeyPair();
        return (T) keyPair.getPublic();
    }

    @SuppressWarnings("unchecked")
    public T getPrivateKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {
        createKeyPair();
        return (T) keyPair.getPrivate();
    }

    private void createKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = null;
        if (Objects.nonNull(this.completeEurvenInfo)) {
            keyPairGenerator =
                Objects.isNull(this.completeEurvenInfo.getProvider()) ? KeyPairGenerator.getInstance(this.completeEurvenInfo.getAlgorithm())
                    : KeyPairGenerator.getInstance(this.completeEurvenInfo.getAlgorithm(), this.completeEurvenInfo.getProvider());
            // 2. 创建安全的随机数生成器
            // getInstanceStrong() 通常推荐使用，因为它会选择系统上最强的可用算法
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            ECGenParameterSpec ecSpec = new ECGenParameterSpec(this.completeEurvenInfo.getNamedCurve());
            keyPairGenerator.initialize(ecSpec, secureRandom);
        } else {
            keyPairGenerator = KeyPairGenerator.getInstance(this.algorithm);
            keyPairGenerator.initialize(length);
        }
        this.keyPair = keyPairGenerator.generateKeyPair();
    }

}
