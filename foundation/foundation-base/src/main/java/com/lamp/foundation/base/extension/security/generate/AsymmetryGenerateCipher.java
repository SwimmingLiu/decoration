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

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.NoSuchPaddingException;

import com.lamp.foundation.api.model.ByteDecode;
import com.lamp.foundation.api.model.ByteEncode;
import com.lamp.foundation.api.security.model.CompleteInfo;
import com.lamp.foundation.api.security.model.SecurityConfig;
import com.lamp.foundation.base.model.DefaultByteEncode;

/**
 * 执行类 操作类
 * <p>
 * 把所有的 非对称加解密的 算法测试完成
 *
 * @author yuki
 */
public class AsymmetryGenerateCipher extends AbstractGenerateCipher {

    private Signature sign;

    private Signature verify;


    public AsymmetryGenerateCipher(String algorithm, byte[] publicKeyByte, byte[] privateKeyByte)
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        this(algorithm, null, publicKeyByte, privateKeyByte);

    }

    public AsymmetryGenerateCipher(String algorithm, String sign, byte[] publicKeyByte, byte[] privateKeyByte)
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        super(algorithm, sign);
        KeyFactory keyFactory = getKeyFactory();
        if (Objects.nonNull(publicKeyByte)) {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyByte);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            this.setPublicSpec(publicKey);
        }
        if (Objects.nonNull(privateKeyByte)) {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            this.setPrivateSpec(privateKey);
        }


    }

    public AsymmetryGenerateCipher(SecurityConfig securityConfig) {
        super(null, null);
        CompleteInfo completeInfo = securityConfig.getCompleteInfo();
        if (Objects.nonNull(completeInfo)) {
            completeInfo.algorithm();
        } else if (Objects.nonNull(securityConfig.getCompleteEurvenInfo())) {

        }
    }

    @Override
    public ByteEncode sign(ByteDecode byteDecode)
        throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = this.getSign();
        signature.update(byteDecode.data());
        return new DefaultByteEncode(signature.sign());
    }

    @Override
    public boolean verify(ByteDecode update, ByteDecode verify)
        throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = this.getVerify();
        signature.update(update.data());
        return signature.verify(verify.data());
    }

    @Override
    Object createSignature(boolean sign, Key key) {
        try {
            Signature signature;
            if (this.isProvider()) {
                signature = Signature.getInstance(this.securityConfig.getSignName(), this.securityConfig.getCompleteEurvenInfo().getProvider());
            } else {
                signature = Signature.getInstance(this.securityConfig.getSignName());
            }
            if (sign) {
                signature.initSign((PrivateKey) key);
            } else {
                signature.initVerify((PublicKey) key);
            }
            return signature;
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }
}
