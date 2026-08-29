package com.lamp.foundation.base.extension.security.create;

import java.util.Objects;

import com.lamp.foundation.api.security.SecurityBaseType.Asymmetric;
import com.lamp.foundation.api.security.model.CompleteEurvenInfo;
import com.lamp.foundation.api.security.model.CompleteInfo;
import com.lamp.foundation.api.security.model.CreateObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hahaha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AsymmetricCreateObject extends CreateObject {

    public static AsymmetricCreateObject of(Asymmetric asymmetric) {
        return of(CompleteEurvenInfo.of(asymmetric));
    }


    public static AsymmetricCreateObject of(CompleteEurvenInfo completeEurvenInfo, CompleteInfo completeInfo) {
        if (Objects.nonNull(completeEurvenInfo)) {
            return of(completeEurvenInfo);
        }
        if (Objects.nonNull(completeInfo)) {
            return of(SymmetryCreateObject.of(completeInfo.algorithm(), completeInfo.getLength()));
        }
        throw new IllegalArgumentException("Asymmetric CreateObject must not be null");
    }

    public static AsymmetricCreateObject of(CompleteEurvenInfo completeEurvenInfo) {
        AsymmetricCreateObject asymmetricCreateObject = new AsymmetricCreateObject();
        asymmetricCreateObject.setCompleteEurvenInfo(completeEurvenInfo);
        return asymmetricCreateObject;
    }

    public static AsymmetricCreateObject of(SymmetryCreateObject symmetryCreateObject) {
        AsymmetricCreateObject asymmetricCreateObject = new AsymmetricCreateObject();
        asymmetricCreateObject.setSymmetryCreateObject(symmetryCreateObject);
        return asymmetricCreateObject;
    }


    private CompleteEurvenInfo completeEurvenInfo;

    private SymmetryCreateObject symmetryCreateObject;

}
