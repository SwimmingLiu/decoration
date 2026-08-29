package com.lamp.foundation.base.extension.security.create;

import java.security.SecureRandom;

import javax.crypto.KeyGenerator;

import com.lamp.foundation.api.security.CreateCipher;
import com.lamp.foundation.api.security.model.KeyWrapper;
import com.lamp.foundation.base.extension.security.key.DefaultKeyWrapper;

/**
 * @author hahaha
 */
public class SymmetryCreate implements CreateCipher<SymmetryCreateObject> {

    @Override
    public KeyWrapper create(SymmetryCreateObject object) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(object.getAlgorithm());

        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        keyGenerator.init(object.getLength(), secureRandom);

        return DefaultKeyWrapper.of(keyGenerator.generateKey());
    }
}
