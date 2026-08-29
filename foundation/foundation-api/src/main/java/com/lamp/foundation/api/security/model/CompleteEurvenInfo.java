package com.lamp.foundation.api.security.model;

import com.lamp.foundation.api.security.SecurityBaseType.Asymmetric;

import lombok.Data;

@Data
public class CompleteEurvenInfo {

    public static CompleteEurvenInfo of(Asymmetric asymmetric) {
        CompleteEurvenInfo completeEurvenInfo = new CompleteEurvenInfo();
        completeEurvenInfo.setAlgorithm(asymmetric.algorithm());
        completeEurvenInfo.setCipher(asymmetric.getCipher());
        completeEurvenInfo.setProvider(asymmetric.getProvider());
        completeEurvenInfo.setNamedCurve(asymmetric.defaultCurves().name());
        return completeEurvenInfo;
    }


    private String algorithm;

    private String provider;

    private String cipher;

    private String namedCurve;

}
