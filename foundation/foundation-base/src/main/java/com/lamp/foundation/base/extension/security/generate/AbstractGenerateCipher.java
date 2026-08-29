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
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.lamp.foundation.api.lang.util.BitsetOperation.Bitset;
import com.lamp.foundation.api.security.SecurityBaseType.SecurityBehavior;
import com.lamp.foundation.api.security.model.SecurityConfig;
import com.lamp.foundation.base.lang.lock.DelayCreate;

import lombok.AccessLevel;
import lombok.Setter;


public abstract class AbstractGenerateCipher implements GenerateCipher {

    private static final ThreadLocal<SecurityObjectThreadLocal> THREAD_LOCAL = ThreadLocal.withInitial(SecurityObjectThreadLocal::new);

    private static final AtomicInteger INDEX = new AtomicInteger(0);

    static {
        // 注册 Bouncy Castle 提供者
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    protected SecurityConfig securityConfig;
    private final Integer index;
    private final String algorithm;
    private final String signs;
    @Setter(AccessLevel.PROTECTED)
    private Key publicSpec;

    @Setter(AccessLevel.PROTECTED)
    private Key privateSpec;


    public AbstractGenerateCipher(String algorithm) {
        this(algorithm, null);
    }

    public AbstractGenerateCipher(String algorithm, String signs) {
        this.algorithm = algorithm;
        this.signs = signs;
        this.index = INDEX.getAndIncrement();
        SecurityObject securityObject = THREAD_LOCAL.get().getSecurityObject(this);
        Bitset bitset = this.securityConfig.getSecurityBehavior().getBitSet();
        if (bitset.match(SecurityBehavior.DECRYPT.getBitSet())) {
            securityObject.decryptCipher = DelayCreate.of(() -> this.getCipher(Cipher.DECRYPT_MODE, privateSpec));
        }
        if (bitset.match(SecurityBehavior.ENCRYPTION.getBitSet())) {
            securityObject.encryptCipher = DelayCreate.of(() -> this.getCipher(Cipher.ENCRYPT_MODE, publicSpec));
        }
        if (bitset.match(SecurityBehavior.SIGN.getBitSet())) {
            securityObject.sign = DelayCreate.of(() -> this.createSignature(true, privateSpec));
        }
        if (bitset.match(SecurityBehavior.VERIFY.getBitSet())) {
            securityObject.verify = DelayCreate.of(() -> this.createSignature(false, publicSpec));
        }


    }

    abstract Object createSignature(boolean sign, Key key);

    @SuppressWarnings("RedundantThrows")
    @Override
    public Cipher getPublicCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return getSecurityObject().decryptCipher.get();
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public Cipher getPrivateCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return getSecurityObject().encryptCipher.get();
    }

    boolean isCompleteEurvenInfo() {
        return Objects.nonNull(this.securityConfig.getCompleteEurvenInfo());
    }

    boolean isProvider() {
        return this.isCompleteEurvenInfo() && Objects.nonNull(this.securityConfig.getCompleteEurvenInfo().getProvider());
    }

    @SuppressWarnings({"RedundantThrows", "unchecked"})
    <T> T getVerify() throws NoSuchAlgorithmException, InvalidKeyException {
        return (T) getSecurityObject().verify.get();
    }

    @SuppressWarnings({"RedundantThrows", "unchecked"})
    <T> T getSign() throws NoSuchAlgorithmException, InvalidKeyException {
        return (T) getSecurityObject().sign.get();
    }

    SecurityObject getSecurityObject() {
        return THREAD_LOCAL.get().getSecurityObject(this);
    }

    Cipher getCipher(int opmode, Key key) {
        try {
            Cipher cipher = null;
            if (this.isCompleteEurvenInfo() && this.isProvider()) {
                cipher = Cipher.getInstance(algorithm, this.securityConfig.getCompleteEurvenInfo().getProvider());
            } else {
                cipher = Cipher.getInstance(algorithm);
            }
            if (Objects.nonNull(this.securityConfig.getIv())) {
                IvParameterSpec ivSpec = new IvParameterSpec(this.securityConfig.getIv().data());
                cipher.init(opmode, key, ivSpec);
            } else {
                cipher.init(opmode, key);
            }
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | NoSuchProviderException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("RedundantThrows")
    KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
        return THREAD_LOCAL.get().getKeyGenerator(this.algorithm);
    }

    static class SecurityObjectThreadLocal {

        private final Map<String, KeyFactory> keyGenerator = new HashMap<>();

        private final Map<Integer, SecurityObject> decryptCipherMap = new HashMap<>();


        public KeyFactory getKeyGenerator(String algorithm) {
            return keyGenerator.computeIfAbsent(algorithm, (k) -> {
                try {
                    return KeyFactory.getInstance(algorithm);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        public SecurityObject getSecurityObject(AbstractGenerateCipher abstractGenerateCipher) {
            return decryptCipherMap.computeIfAbsent(abstractGenerateCipher.index, k -> new SecurityObject());
        }

        public void delete(AbstractGenerateCipher abstractGenerateCipher) {
            decryptCipherMap.remove(abstractGenerateCipher.index);
        }


    }

    static class SecurityObject {

        private DelayCreate<Cipher> decryptCipher;

        private DelayCreate<Cipher> encryptCipher;

        private DelayCreate<Object> verify;

        private DelayCreate<Object> sign;

    }
}
