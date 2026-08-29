package com.lamp.foundation.base.extension.security.create;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.lamp.foundation.api.security.model.CreateObject;
import com.lamp.foundation.api.security.model.KeyWrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hahaha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CertificateCreateObject extends CreateObject {

    private CaObjectType caObjectType;

    /**
     * 当前 证书 的 公钥
     */
    private KeyWrapper publicKeyWrapper;


    /**
     * 根证书，需要获得 distinguishedName
     */
    private KeyWrapper certificateWrapper;

    private String distinguishedName;

    /**
     * 根证书 的 私钥
     */
    private KeyWrapper privateKeyWrapper;

    private AsymmetricCreateObject asymmetricCreateObject;

    private String issuer;

    private BigInteger serialNumber;

    private Date notBefore;

    private Date notAfter;

    private Long duration;

    private Subject subject;

    private List<String> dnsNames;

    private List<String> ipAddresses;

    private String signatureAlgorithm;


    public enum CaObjectType {

        ROOT,

        SERVICE,

        CLIENT,

    }

    @Data
    public static class Subject {

        /**
         * 通用名称‌。对于服务器证书，通常是‌域名‌（如 www.example.com）；对于个人证书，通常是姓名。这是最关键的字段。 CN=localhost
         */
        private String commonName;

        /**
         * 组织名称‌。持有证书的公司或机构全称。O=Example Inc
         */
        private String organization;

        /**
         * 组织单位‌。公司内部的部门或分支。 OU=IT Department
         */
        private String organizationalUnit;

        /**
         * 地区/城市‌。持有者所在的城市。L=Beijing
         */
        private String locality;

        /**
         * 州/省‌。持有者所在的省份或州。ST=Beijing
         */
        private String state;

        /**
         * ‌国家‌。两位字母的国家代码。 	C=CN
         */
        private String country;
    }

}
