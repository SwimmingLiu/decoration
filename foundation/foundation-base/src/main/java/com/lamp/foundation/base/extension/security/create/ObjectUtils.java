package com.lamp.foundation.base.extension.security.create;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

import com.lamp.foundation.base.extension.security.create.CertificateCreateObject.Subject;

/**
 * @author hahaha
 */
public class ObjectUtils {


    public static void supplement(CertificateCreateObject object) {
        if (Objects.isNull(object.getSerialNumber())) {
            BigInteger bigInteger = BigInteger.valueOf(System.currentTimeMillis());
            object.setSerialNumber(bigInteger);
        }
        if (Objects.isNull(object.getNotBefore())) {
            object.setNotBefore(new Date());
        }
        if (Objects.isNull(object.getNotAfter()) && Objects.isNull(object.getDuration())) {
            throw new RuntimeException("");
        }
        if (Objects.isNull(object.getNotAfter())) {
            Date notAfter = new Date(System.currentTimeMillis() + object.getDuration());
            object.setNotAfter(notAfter);
        }
        if (Objects.isNull(object.getSubject())) {
            object.setSubject(new Subject());
        }
        supplement(object.getSubject());
    }

    public static void supplement(Subject subject) {
        if (Objects.isNull(subject.getCommonName())) {
            subject.setCommonName("lamp");
        }
        if (Objects.isNull(subject.getOrganization())) {
            subject.setOrganization("lamp");
        }
        if (Objects.isNull(subject.getOrganizationalUnit())) {
            subject.setOrganizationalUnit("lamp");
        }
        if (Objects.isNull(subject.getLocality())) {
            subject.setLocality("beijing");
        }
        if (Objects.isNull(subject.getState())) {
            subject.setState("beijing");
        }
        if (Objects.isNull(subject.getCountry())) {
            subject.setCountry("beijing");
        }
    }

}
