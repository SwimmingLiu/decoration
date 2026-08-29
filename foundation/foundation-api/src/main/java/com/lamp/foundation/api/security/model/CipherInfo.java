package com.lamp.foundation.api.security.model;

import com.lamp.foundation.api.security.SecurityBaseType.SecurityEnum;

import lombok.Data;

@Data
public class CipherInfo {

    private String name;

    private SecurityEnum securityEnum;

    private String algorithm;

    private String workingMode;

    private String padding;

    private Integer length;

    private String provider;

    private String cipher;

    private String namedCurve;

    private String publicKey;

    private String privateKey;

    private String signature;

}
