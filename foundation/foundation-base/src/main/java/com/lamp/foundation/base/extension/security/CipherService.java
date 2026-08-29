package com.lamp.foundation.base.extension.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.foundation.api.security.model.SecurityConfig;
import com.lamp.foundation.base.extension.security.generate.GenerateCipher;
import com.lamp.foundation.base.extension.security.generate.ProxyGenerateCipher;

public class CipherService {

    private Map<String, GenerateCipher> ciphers = new ConcurrentHashMap<>();

    public void register() {

    }

    public void register(String name, GenerateCipher cipher) {
        this.ciphers.put(name, cipher);
    }

    public void unregister(String name) {
        this.ciphers.remove(name);
    }

    public GenerateCipher create(String name, GenerateCipher cipher, boolean encryption) {
        GenerateCipher generateCipher = this.ciphers.get(name);
        ProxyGenerateCipher proxyGenerateCipher = new ProxyGenerateCipher();
        if (encryption) {
            proxyGenerateCipher.setEncryption(cipher);
            proxyGenerateCipher.setDecryption(generateCipher);
        } else {
            proxyGenerateCipher.setEncryption(generateCipher);
            proxyGenerateCipher.setDecryption(cipher);
        }
        return proxyGenerateCipher;
    }

    /**
     * <pre>
     *     对称密钥，只能加解密
     *     非对称密钥，可以加解密，以及摘要
     *     派生密钥
     * </pre>
     */
    public GenerateCipher create(SecurityConfig config) {
        return null;
    }
}
