package com.lamp.foundation.api.security.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hahaha
 */
public class CertificateInfo {

    private String rootCipherId;

    private String rootCipherName;

    private String rootCertificateId;

    private String rootCertificateName;

    private String cipherId;

    private String cipherName;

    private String content;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String distinguishedName;

    private String issuer;

    private BigInteger serialNumber;

    private List<String> dnsNames;

    private List<String> ipAddresses;

    private String signatureAlgorithm;

    /**
     * <pre>
     *     下面的数据，由申请组织信息生成。
     *     关于 地理 位置，可以组件类型，服务类型等构成
     * </pre>
     * <p>
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
