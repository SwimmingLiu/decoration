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
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import com.lamp.foundation.api.model.ByteDecode;
import com.lamp.foundation.api.model.ByteEncode;
import com.lamp.foundation.base.extension.security.MessageDigestWrapper;
import com.lamp.foundation.base.extension.security.MessageDigestWrapper.MacExecute;

/**
 * @author hahaha
 */
public class SymmetricGenerateCipher extends AbstractGenerateCipher {

    private MacExecute macExecute;


    public SymmetricGenerateCipher(String algorithm, byte[] keyByte) {
        super(algorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyByte, algorithm);
        this.setPrivateSpec(secretKeySpec);
        this.setPrivateSpec(secretKeySpec);
    }

    @Override
    public ByteEncode sign(ByteDecode byteDecode)
        throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException {
        return this.macExecute.digest(byteDecode.data());
    }

    @Override
    public boolean verify(ByteDecode update, ByteDecode verify)
        throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException {
        ByteEncode bytes = this.macExecute.digest(update.data());
        byte[] verifyBytes = verify.data();
        byte[] signatureBytes = bytes.data();
        if (verifyBytes.length != signatureBytes.length) {
            return false;
        }
        for (int i = 0; i < verifyBytes.length; i++) {
            if (verifyBytes[i] != signatureBytes[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    Object createSignature(boolean sign, Key key) {
        try {
            if (Objects.isNull(this.macExecute)) {
                this.macExecute = MessageDigestWrapper.get().createMac("", null);
                return this.macExecute;
            } else {
                MacExecute macExecute = this.macExecute;
                this.macExecute = null;
                return macExecute;
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
