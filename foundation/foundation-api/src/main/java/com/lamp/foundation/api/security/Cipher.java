package com.lamp.foundation.api.security;

import com.lamp.foundation.api.model.ByteDecode;
import com.lamp.foundation.api.model.ByteEncode;

public interface Cipher {


    ByteEncode encryption(ByteDecode byteDecode) throws Exception;

    ByteEncode decryption(ByteDecode byteDecode) throws Exception;

}
