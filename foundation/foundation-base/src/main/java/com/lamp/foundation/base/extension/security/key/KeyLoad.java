package com.lamp.foundation.base.extension.security.key;

import java.security.cert.X509Certificate;

public interface KeyLoad {


    byte[] publicKey();

    byte[] privateKey();


    X509Certificate certificate();
}
