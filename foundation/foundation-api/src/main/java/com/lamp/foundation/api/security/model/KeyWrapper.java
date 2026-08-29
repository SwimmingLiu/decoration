package com.lamp.foundation.api.security.model;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import javax.crypto.SecretKey;

/**
 * @author hahaha
 */
public interface KeyWrapper {

    Object key();

    byte[] secreteKey();


    SecretKey getSecretKey();

    byte[] privateKey();

    PrivateKey getPrivateKey();

    byte[] publicKey();

    PublicKey getPublicKey();

    KeyPair getKeyPair();

    X509Certificate getX509Certificate();

}
