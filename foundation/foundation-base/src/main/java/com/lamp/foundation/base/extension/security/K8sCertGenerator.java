package com.lamp.foundation.base.extension.security;


import java.io.FileWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class K8sCertGenerator {

    // 证书有效期：10年
    private static final long VALIDITY_PERIOD_MS = 10L * 365 * 24 * 60 * 60 * 1000;

    public static void main(String[] args) throws Exception {
        // 1. 生成 CA 根证书
        System.out.println("Generating CA Root Certificate...");
        KeyPair caKeyPair = generateKeyPair();
        X509Certificate caCert = generateCACertificate(caKeyPair, "CN=kubernetes-ca");
        saveCertAndKey(caCert, caKeyPair.getPrivate(), "ca");

        // 2. 生成 API Server 证书 (服务端)
        System.out.println("Generating API Server Certificate...");
        KeyPair apiServerKey = generateKeyPair();
        // 注意：实际生产中需替换为真实的 Master IP 和 Cluster IP
        String[] apiServerSans = {
            "kubernetes",
            "kubernetes.default",
            "kubernetes.default.svc",
            "kubernetes.default.svc.cluster.local",
            // Master Node IP
            "192.168.1.100",
            // Cluster IP
            "10.96.0.1"
        };
        X509Certificate apiServerCert = generateServerCertificate(
            apiServerKey, caCert, caKeyPair.getPrivate(),
            "CN=kube-apiserver", apiServerSans
        );
        saveCertAndKey(apiServerCert, apiServerKey.getPrivate(), "apiserver");

        // 3. 生成 Etcd 证书 (服务端)
        System.out.println("Generating Etcd Certificate...");
        KeyPair etcdKey = generateKeyPair();
        String[] etcdSans = {"localhost", "127.0.0.1", "192.168.1.100"};
        X509Certificate etcdCert = generateServerCertificate(
            etcdKey, caCert, caKeyPair.getPrivate(),
            "CN=etcd-server", etcdSans
        );
        saveCertAndKey(etcdCert, etcdKey.getPrivate(), "etcd-server");

        // 4. 生成 Admin 客户端证书
        System.out.println("Generating Admin Client Certificate...");
        KeyPair adminKey = generateKeyPair();
        X509Certificate adminCert = generateClientCertificate(
            adminKey, caCert, caKeyPair.getPrivate(),
            "CN=admin", "O=system:masters"
        );
        saveCertAndKey(adminCert, adminKey.getPrivate(), "admin");

        // 5. 生成 Kube-controller-manager 客户端证书
        System.out.println("Generating Kube-controller-manager Certificate...");
        KeyPair cmKey = generateKeyPair();
        X509Certificate cmCert = generateClientCertificate(
            cmKey, caCert, caKeyPair.getPrivate(),
            "CN=system:kube-controller-manager", "O=system:kube-controller-manager"
        );
        saveCertAndKey(cmCert, cmKey.getPrivate(), "kube-controller-manager");

        // 6. 生成 Kube-scheduler 客户端证书
        System.out.println("Generating Kube-scheduler Certificate...");
        KeyPair schedKey = generateKeyPair();
        X509Certificate schedCert = generateClientCertificate(
            schedKey, caCert, caKeyPair.getPrivate(),
            "CN=system:kube-scheduler", "O=system:kube-scheduler"
        );
        saveCertAndKey(schedCert, schedKey.getPrivate(), "kube-scheduler");

        // 7. 生成 Kube-proxy 客户端证书
        System.out.println("Generating Kube-proxy Certificate...");
        KeyPair proxyKey = generateKeyPair();
        X509Certificate proxyCert = generateClientCertificate(
            proxyKey, caCert, caKeyPair.getPrivate(),
            "CN=system:kube-proxy", "O=system:node-proxier"
        );
        saveCertAndKey(proxyCert, proxyKey.getPrivate(), "kube-proxy");

        // 8. 生成 Service Account Key Pair (用于签发 Token)
        System.out.println("Generating Service Account Key...");
        KeyPair saKey = generateKeyPair();
        savePrivateKey(saKey.getPrivate(), "sa.key");
        // SA 公钥通常不需要证书形式，而是直接作为公钥文件分发
        savePublicKey(saKey.getPublic(), "sa.pub");

        System.out.println("All certificates generated successfully.");
    }

    /**
     * 生成 RSA 密钥对
     */
    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    /**
     * 生成自签名 CA 证书
     */
    private static X509Certificate generateCACertificate(KeyPair keyPair, String dn) throws Exception {
        X500Name subject = new X500Name(dn);
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + VALIDITY_PERIOD_MS);

        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
            subject, BigInteger.valueOf(System.currentTimeMillis()),
            startDate, endDate, subject, keyPair.getPublic()
        );

        // 添加基本约束：CA=TRUE
        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
        // 添加密钥用法
        certBuilder.addExtension(Extension.keyUsage, true,
            new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyCertSign | KeyUsage.cRLSign));

        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());
        X509CertificateHolder certHolder = certBuilder.build(signer);
        return new JcaX509CertificateConverter().getCertificate(certHolder);
    }

    /**
     * 保存证书和私钥到 PEM 文件
     */
    private static void saveCertAndKey(X509Certificate cert, PrivateKey key, String prefix) throws Exception {
        // 保存证书 (.crt)
        try (FileWriter fw = new FileWriter(prefix + ".crt")) {
            fw.write("-----BEGIN CERTIFICATE-----\n");
            fw.write(java.util.Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(cert.getEncoded()));
            fw.write("\n-----END CERTIFICATE-----\n");
        }

        // 保存私钥 (.key)
        savePrivateKey(key, prefix + ".key");
    }

    /**
     * 生成服务端证书 (带有 SAN)
     */
    private static X509Certificate generateServerCertificate(
        KeyPair keyPair, X509Certificate caCert, PrivateKey caKey,
        String dn, String[] sans) throws Exception {

        X500Name subject = new X500Name(dn);
        X500Name issuer = new X500Name(caCert.getSubjectX500Principal().getName());
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + VALIDITY_PERIOD_MS);

        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
            issuer, BigInteger.valueOf(System.currentTimeMillis()),
            startDate, endDate, subject, keyPair.getPublic()
        );

        // 添加 SAN (Subject Alternative Name)
        if (sans != null && sans.length > 0) {
            GeneralName[] generalNames = new GeneralName[sans.length];
            for (int i = 0; i < sans.length; i++) {
                String san = sans[i];
                if (san.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
                    generalNames[i] = new GeneralName(GeneralName.iPAddress, san);
                } else {
                    generalNames[i] = new GeneralName(GeneralName.dNSName, san);
                }
            }
            certBuilder.addExtension(Extension.subjectAlternativeName, false, new GeneralNames(generalNames));
        }

        // 密钥用法：数字签名、密钥加密
        certBuilder.addExtension(Extension.keyUsage, true,
            new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        // 扩展密钥用法：服务端认证
        certBuilder.addExtension(Extension.extendedKeyUsage, false,
            new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));

        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(caKey);
        X509CertificateHolder certHolder = certBuilder.build(signer);
        return new JcaX509CertificateConverter().getCertificate(certHolder);
    }

    /**
     * 生成客户端证书
     */
    private static X509Certificate generateClientCertificate(
        KeyPair keyPair, X509Certificate caCert, PrivateKey caKey,
        String cn, String org) throws Exception {

        X500Name subject = new X500Name("CN=" + cn + ", O=" + org);
        X500Name issuer = new X500Name(caCert.getSubjectX500Principal().getName());
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + VALIDITY_PERIOD_MS);

        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
            issuer, BigInteger.valueOf(System.currentTimeMillis()),
            startDate, endDate, subject, keyPair.getPublic()
        );

        // 密钥用法
        certBuilder.addExtension(Extension.keyUsage, true,
            new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        // 扩展密钥用法：客户端认证
        certBuilder.addExtension(Extension.extendedKeyUsage, false,
            new ExtendedKeyUsage(KeyPurposeId.id_kp_clientAuth));

        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(caKey);
        X509CertificateHolder certHolder = certBuilder.build(signer);
        return new JcaX509CertificateConverter().getCertificate(certHolder);
    }

    private static void savePrivateKey(PrivateKey key, String filename) throws Exception {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("-----BEGIN RSA PRIVATE KEY-----\n");
            fw.write(java.util.Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(key.getEncoded()));
            fw.write("\n-----END RSA PRIVATE KEY-----\n");
        }
    }

    private static void savePublicKey(PublicKey key, String filename) throws Exception {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("-----BEGIN PUBLIC KEY-----\n");
            fw.write(java.util.Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(key.getEncoded()));
            fw.write("\n-----END PUBLIC KEY-----\n");
        }
    }
}

