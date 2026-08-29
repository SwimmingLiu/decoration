package com.lamp.foundation.base.extension.security.key;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import com.lamp.foundation.api.security.model.KeyWrapper;
import com.lamp.foundation.base.io.system.FileDataLoad;
import com.lamp.foundation.base.lang.util.string.StringByteConverter;

import lombok.Getter;

/**
 * @author hahaha
 */
public class DefaultKeyWrapper implements KeyWrapper {

    public static DefaultKeyWrapper of(Object key) {
        DefaultKeyWrapper defaultKeyWrapper = new DefaultKeyWrapper();
        defaultKeyWrapper.key = key;
        return defaultKeyWrapper;
    }

    public static DefaultKeyWrapper of(byte[] publicKey, byte[] privateKey) {
        DefaultKeyWrapper defaultKeyWrapper = new DefaultKeyWrapper();
        defaultKeyWrapper.publicKey = publicKey;
        defaultKeyWrapper.privateKey = privateKey;
        return defaultKeyWrapper;
    }

    public static DefaultKeyWrapper ofBase64(String publicKey, String privateKey) {
        return of(StringByteConverter.base64(publicKey), StringByteConverter.base64(privateKey));
    }

    public static DefaultKeyWrapper ofByBc(String base64) {
        byte[] bytes = StringByteConverter.base64(base64);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        Reader reader = new InputStreamReader(inputStream);
        PEMParser pemParser = new PEMParser(reader);
        try {
            DefaultKeyWrapper defaultKeyWrapper = new DefaultKeyWrapper();
            pemParser.readObject();
            defaultKeyWrapper.key = pemParser.readObject();
            return defaultKeyWrapper;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static DefaultKeyWrapper ofFile(String filePath) throws IOException {
        FileDataLoad<DefaultKeyWrapper> fileDataLoad = new FileDataLoad<>(filePath);
        return fileDataLoad.read();
    }

    public static DefaultKeyWrapper ofSecretKeySpec(byte[] base64Key, String algorithm) {
        DefaultKeyWrapper defaultKeyWrapper = new DefaultKeyWrapper();
        defaultKeyWrapper.publicKey = base64Key;
        defaultKeyWrapper.algorithm = algorithm;
        return defaultKeyWrapper;
    }

    public static DefaultKeyWrapper ofSecretKeySpec(String base64Key, String algorithm) {
        return ofSecretKeySpec(StringByteConverter.base64(base64Key), algorithm);
    }

    private byte[] publicKey;

    private byte[] privateKey;

    private String algorithm;

    @Getter
    private Object key;


    @Override
    public Object key() {
        return key;
    }

    @Override
    public byte[] secreteKey() {
        if (Objects.nonNull(this.publicKey)) {
            return this.publicKey;
        }
        return this.getSecretKey().getEncoded();
    }


    @Override
    public SecretKey getSecretKey() {
        return (SecretKey) key;
    }

    @Override
    public byte[] privateKey() {
        if (Objects.nonNull(this.publicKey)) {
            return this.publicKey;
        }
        PrivateKey key = this.getPrivateKey();
        return key.getEncoded();
    }

    @Override
    public PrivateKey getPrivateKey() {
        if (key instanceof PrivateKey) {
            return (PrivateKey) key;
        }
        if (key instanceof PrivateKeyInfo) {
            try {
                return new JcaPEMKeyConverter().setProvider("BC").getPrivateKey((PrivateKeyInfo) key);
            } catch (PEMException e) {
                throw new RuntimeException(e);
            }
        }
        if (key instanceof KeyPair) {
            return ((KeyPair) key).getPrivate();
        }
        if (key instanceof PEMKeyPair) {
            PEMKeyPair pemKeyPair = (PEMKeyPair) key;
            try {
                return new JcaPEMKeyConverter().setProvider("BC").getPrivateKey(pemKeyPair.getPrivateKeyInfo());
            } catch (PEMException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Cannot get private key");
    }

    @Override
    public byte[] publicKey() {
        if (Objects.nonNull(this.publicKey)) {
            return this.publicKey;
        }
        return this.getPublicKey().getEncoded();
    }

    @Override
    public PublicKey getPublicKey() {
        if (key instanceof PublicKey) {
            return (PublicKey) key;
        }
        if (key instanceof SubjectPublicKeyInfo) {
            try {
                return new JcaPEMKeyConverter().setProvider("BC").getPublicKey((SubjectPublicKeyInfo) key);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (key instanceof KeyPair) {
            return ((KeyPair) key).getPublic();
        }
        if (key instanceof PEMKeyPair) {
            PEMKeyPair pemKeyPair = (PEMKeyPair) key;
            try {
                return new JcaPEMKeyConverter().setProvider("BC").getPublicKey(pemKeyPair.getPublicKeyInfo());
            } catch (PEMException e) {
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException("key object is not of type PublicKey");
    }

    @Override
    public KeyPair getKeyPair() {
        if (this.key instanceof KeyPair) {
            return (KeyPair) this.key;
        }
        if (this.key instanceof PEMKeyPair) {
            try {
                return new JcaPEMKeyConverter().setProvider("BC").getKeyPair((PEMKeyPair) this.key);
            } catch (PEMException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("key is not a key object");
    }

    @Override
    public X509Certificate getX509Certificate() {
        if (this.key instanceof X509Certificate) {
            return (X509Certificate) this.key;
        }
        if (this.key instanceof X509CertificateHolder) {
            try {
                return converter().getCertificate((X509CertificateHolder) this.key);
            } catch (CertificateException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("key is not a key object");
    }

    private JcaX509CertificateConverter converter() {
        return new JcaX509CertificateConverter().setProvider("BC");
    }

}
