package com.lamp.foundation.base.model;

import java.io.IOException;
import java.io.OutputStream;

import com.lamp.foundation.api.model.ByteEncode;
import com.lamp.foundation.base.lang.util.string.StringByteConverter;

/**
 * @author hahaha
 */
public class DefaultByteEncode implements ByteEncode {

    private final byte[] digest;

    public DefaultByteEncode(byte[] digest) {
        this.digest = digest;
    }

    @Override
    public byte[] data() {
        return this.digest;
    }

    @Override
    public String hex() {
        return StringByteConverter.hex(this.digest);
    }

    @Override
    public String base64() {
        return StringByteConverter.base64(this.digest);
    }

    @Override
    public void output(OutputStream out) throws IOException {
        out.write(this.digest);
    }

}
