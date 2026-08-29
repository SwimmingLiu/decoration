package com.lamp.foundation.base.extension.security.create;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;

import com.lamp.foundation.api.security.CreateCipher;
import com.lamp.foundation.api.security.model.KeyWrapper;
import com.lamp.foundation.base.extension.security.key.DefaultKeyWrapper;

/**
 * @author hahaha
 */
public class DeriveCreate implements CreateCipher<DeriveCreateObject> {

    @Override
    public KeyWrapper create(DeriveCreateObject object) throws Exception {
        HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA256Digest());
        HKDFParameters params = new HKDFParameters(object.getMainKey(), object.getSalt(), object.getInfo().getBytes());
        hkdf.init(params);
        byte[] output = new byte[object.getLength()];
        hkdf.generateBytes(output, 0, object.getLength());
        return DefaultKeyWrapper.of(output);
    }
}
