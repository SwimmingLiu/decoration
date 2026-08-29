package com.lamp.foundation.api.security;

import java.security.SignatureException;

import com.lamp.foundation.api.model.ByteDecode;
import com.lamp.foundation.api.model.ByteEncode;

public interface CipherSummary {

    ByteEncode sign(ByteDecode byteDecode) throws SignatureException;

    ByteEncode verify(ByteDecode update, ByteDecode verify) throws SignatureException;

}
