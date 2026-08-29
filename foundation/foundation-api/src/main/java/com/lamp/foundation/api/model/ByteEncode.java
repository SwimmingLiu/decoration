package com.lamp.foundation.api.model;

import java.io.IOException;
import java.io.OutputStream;

public interface ByteEncode {

    byte[] data();

    String hex();

    String base64();

    void output(OutputStream out) throws IOException;
}
