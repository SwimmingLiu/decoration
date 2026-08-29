package com.lamp.foundation.base.extension.security.create;

import com.lamp.foundation.api.security.SecurityBaseType.Symmetry;
import com.lamp.foundation.api.security.model.CreateObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hahaha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SymmetryCreateObject extends CreateObject {

    public static SymmetryCreateObject of(Symmetry symmetry) {
        return of(symmetry.name(), 128, null);
    }

    public static SymmetryCreateObject of(String symmetryName, int length) {
        return of(symmetryName, length, null);
    }


    public static SymmetryCreateObject of(String algorithm, int length, String provider) {
        SymmetryCreateObject symmetryCreateObject = new SymmetryCreateObject();
        symmetryCreateObject.setAlgorithm(algorithm);
        symmetryCreateObject.setLength(128);
        symmetryCreateObject.setProvider(provider);
        return symmetryCreateObject;
    }


    private String provider;

    private String algorithm;

    private int length;

}
