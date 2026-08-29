package com.lamp.foundation.base.extension.security.generate;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import com.lamp.foundation.api.model.ByteDecode;
import com.lamp.foundation.api.model.ByteEncode;

import lombok.Setter;

/**
 * 服务端，只有一个私钥，但是会对应很多公钥
 *
 * @author hahaha
 */
@Setter
public class ProxyGenerateCipher implements GenerateCipher {

    /**
     * 加密
     */
    private GenerateCipher encryption;

    /**
     * 解密
     */
    private GenerateCipher decryption;

    @Override
    public Cipher getPublicCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return decryption.getPublicCipher();
    }

    @Override
    public Cipher getPrivateCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return encryption.getPrivateCipher();
    }

    @Override
    public ByteEncode sign(ByteDecode byteDecode)
        throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException {
        return null;
    }

    @Override
    public boolean verify(ByteDecode update, ByteDecode verify)
        throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException {
        return false;
    }

}
