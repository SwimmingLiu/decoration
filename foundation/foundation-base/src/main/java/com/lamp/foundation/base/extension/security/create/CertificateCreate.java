package com.lamp.foundation.base.extension.security.create;

import org.apache.commons.collections.CollectionUtils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Objects;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import com.lamp.foundation.api.security.CreateCipher;
import com.lamp.foundation.api.security.SecurityBaseType.Asymmetric;
import com.lamp.foundation.api.security.model.KeyWrapper;
import com.lamp.foundation.base.extension.security.create.CertificateCreateObject.CaObjectType;
import com.lamp.foundation.base.extension.security.create.CertificateCreateObject.Subject;
import com.lamp.foundation.base.extension.security.key.DefaultKeyWrapper;

/**
 * <pre>
 *     证书构建有三种
 *     1. root 证书
 *      1. 服务端证书是 自己的 私钥签名
 *     2. 服务端证书
 *     3. 客服端证书
 * </pre>
 *
 * @author hahaha
 */
public class CertificateCreate implements CreateCipher<CertificateCreateObject> {


    @Override
    public KeyWrapper create(CertificateCreateObject data) throws Exception {
        this.check(data);
        X509v3CertificateBuilder certBuilder = this.buildX509v3(data);
        this.buildExtension(certBuilder, data);

        return DefaultKeyWrapper.of(this.build(data, certBuilder));
    }

    private void check(CertificateCreateObject object) {
        if (Objects.isNull(object.getSignatureAlgorithm())) {
            String cipher = object.getAsymmetricCreateObject().getCompleteEurvenInfo().getCipher();
            Asymmetric asymmetric = Asymmetric.valueOf(cipher);
            object.setSignatureAlgorithm(asymmetric.defaultSignatureAlgorithm().name());
        }
        if (Objects.isNull(object.getCaObjectType())) {
            throw new RuntimeException("caObjectType is null");
        }
    }

    private X509Certificate build(CertificateCreateObject data, X509v3CertificateBuilder certBuilder)
        throws OperatorCreationException, CertificateException {
        JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder(data.getSignatureAlgorithm());

        JcaX509CertificateConverter jca = new JcaX509CertificateConverter();

        String cipher = data.getAsymmetricCreateObject().getCompleteEurvenInfo().getCipher();
        Asymmetric asymmetric = Asymmetric.valueOf(cipher);
        if (Objects.nonNull(asymmetric.getProvider())) {
            jcaContentSignerBuilder.setProvider(asymmetric.getProvider());
            jca.setProvider(asymmetric.getProvider());
        }
        ContentSigner contentSigner = jcaContentSignerBuilder.build(data.getPrivateKeyWrapper().getPrivateKey());
        return jca.getCertificate(certBuilder.build(contentSigner));
    }

    private void buildExtension(X509v3CertificateBuilder certBuilder, CertificateCreateObject data) throws CertIOException {

        if (Objects.equals(data.getCaObjectType(), CaObjectType.ROOT)) {
            // 添加基本约束：CA=TRUE
            certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
            // 添加密钥用法
            certBuilder.addExtension(Extension.keyUsage, true,
                new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyCertSign | KeyUsage.cRLSign));
        } else if (Objects.equals(data.getCaObjectType(), CaObjectType.SERVICE)) {
            certBuilder.addExtension(Extension.subjectAlternativeName, false, this.buildGeneralNames(data));
            // 密钥用法：数字签名、密钥加密
            certBuilder.addExtension(Extension.keyUsage, true,
                new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
            // 扩展密钥用法：服务端认证
            certBuilder.addExtension(Extension.extendedKeyUsage, false,
                new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
        } else if (Objects.equals(data.getCaObjectType(), CaObjectType.CLIENT)) {
            // 密钥用法
            certBuilder.addExtension(Extension.keyUsage, true,
                new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
            // 扩展密钥用法：客户端认证
            certBuilder.addExtension(Extension.extendedKeyUsage, false,
                new ExtendedKeyUsage(KeyPurposeId.id_kp_clientAuth));
        }
    }

    private X509v3CertificateBuilder buildX509v3(CertificateCreateObject data) {
        X500Name subject = this.buildX500Name(data.getSubject());
        X500Name issuer = subject;
        if (Objects.equals(data.getCaObjectType(), CaObjectType.SERVICE) || Objects.equals(data.getCaObjectType(), CaObjectType.CLIENT)) {
            if (Objects.nonNull(data.getDistinguishedName())) {
                issuer = new X500Name(data.getDistinguishedName());
            } else {
                String issuerString = data.getCertificateWrapper().getX509Certificate().getIssuerX500Principal().getName();
                issuer = new X500Name(issuerString);
            }
        }
        return new JcaX509v3CertificateBuilder(
            issuer,
            data.getSerialNumber(),
            data.getNotBefore(),
            data.getNotAfter(),
            subject,
            data.getPublicKeyWrapper().getPublicKey()
        );
    }

    private X500Name buildX500Name(Subject subject) {
        StringBuilder x500Name = new StringBuilder();
        if (Objects.nonNull(subject.getCommonName())) {
            x500Name.append("CN=").append(subject.getCommonName());
        }
        if (Objects.nonNull(subject.getOrganization())) {
            x500Name.append(",O=").append(subject.getOrganization());
        }
        if (Objects.nonNull(subject.getOrganizationalUnit())) {
            x500Name.append(",OU=").append(subject.getOrganizationalUnit());
        }
        if (Objects.nonNull(subject.getLocality())) {
            x500Name.append(",L=").append(subject.getLocality());
        }
        if (Objects.nonNull(subject.getState())) {
            x500Name.append(",ST=").append(subject.getState());
        }
        if (Objects.nonNull(subject.getCountry())) {
            x500Name.append(",C=").append(subject.getCountry());
        }
        if (x500Name.isEmpty()) {
            return null;
        }

        return new X500Name(x500Name.toString());
    }

    private GeneralNames buildGeneralNames(CertificateCreateObject object) {
        if (CollectionUtils.isEmpty(object.getDnsNames()) && CollectionUtils.isEmpty(object.getIpAddresses())) {
            throw new RuntimeException("");
        }
        List<String> dnsNames = object.getDnsNames();
        List<String> ipAddresses = object.getIpAddresses();
        GeneralName[] generalNames = new GeneralName[(dnsNames != null ? dnsNames.size() : 0) + (ipAddresses != null ? ipAddresses.size() : 0)];
        int index = 0;
        if (dnsNames != null) {
            for (String dns : dnsNames) {
                generalNames[index++] = new GeneralName(GeneralName.dNSName, dns);
            }
        }

        if (ipAddresses != null) {
            for (String ip : ipAddresses) {
                generalNames[index++] = new GeneralName(GeneralName.iPAddress, ip);
            }
        }
        return new GeneralNames(generalNames);
    }
}
