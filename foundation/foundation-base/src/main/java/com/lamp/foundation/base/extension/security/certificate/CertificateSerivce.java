package com.lamp.foundation.base.extension.security.certificate;

import java.util.Objects;

import com.lamp.foundation.api.security.model.CompleteEurvenInfo;
import com.lamp.foundation.api.security.model.KeyWrapper;
import com.lamp.foundation.base.extension.security.create.AsymmetricCreate;
import com.lamp.foundation.base.extension.security.create.AsymmetricCreateObject;
import com.lamp.foundation.base.extension.security.create.CertificateCreate;
import com.lamp.foundation.base.extension.security.create.CertificateCreateObject;
import com.lamp.foundation.base.extension.security.key.DefaultKeyWrapper;
import com.lamp.foundation.base.extension.security.key.FileKeyLoad;
import com.lamp.foundation.base.io.system.FileDataLoad;

public class CertificateSerivce {


    private final AsymmetricCreate asymmetricCreate = new AsymmetricCreate();

    private final CertificateCreate certificateCreate = new CertificateCreate();


    public void buildByFile(CertificateBuildData certificateBuildData) throws Exception {

        this.keyLoad(certificateBuildData, null);

        if (!Objects.nonNull(certificateBuildData.getChildCertificateBuildData())) {
            return;
        }
        for (CertificateBuildData data : certificateBuildData.getChildCertificateBuildData()) {
            this.fill(data, certificateBuildData);
            CertificateCreateObject certificateCreateObject = certificateBuildData.getCertificateCreateObject();
            if (Objects.isNull(certificateCreateObject)) {
                throw new RuntimeException("certificateCreateObject is null");
            }
            this.keyLoad(data, certificateBuildData);
        }
    }

    private void fill(CertificateBuildData child, CertificateBuildData root) {
        if (Objects.isNull(child.getCertificateCreateObject().getAsymmetricCreateObject())) {
            AsymmetricCreateObject asymmetricCreateObject = root.getCertificateCreateObject().getAsymmetricCreateObject();
            child.getCertificateCreateObject().setAsymmetricCreateObject(asymmetricCreateObject);
        }
    }

    private void keyLoad(CertificateBuildData certificateBuildData, CertificateBuildData root) throws Exception {
        CompleteEurvenInfo completeEurvenInfo = certificateBuildData.getCompleteEurvenInfo();
        FileDataLoad<KeyWrapper> publicKey = FileKeyLoad.of(certificateBuildData.getPublicPath());
        FileDataLoad<KeyWrapper> privateKey = FileKeyLoad.of(certificateBuildData.getPrivatePath());
        if (privateKey.exists() && privateKey.exists()) {
            try {
                publicKey.read();
                privateKey.read();
            } catch (Exception e) {
                publicKey.delete();
                privateKey.delete();
            }
        }
        CertificateCreateObject certificateCreateObject = certificateBuildData.getCertificateCreateObject();
        if (Objects.isNull(certificateCreateObject)) {
            throw new RuntimeException("certificateCreateObject is null");
        }

        AsymmetricCreateObject asymmetricCreateObject;
        if (Objects.nonNull(certificateBuildData.getCertificateCreateObject().getAsymmetricCreateObject())) {
            asymmetricCreateObject = certificateBuildData.getCertificateCreateObject().getAsymmetricCreateObject();
        } else {
            asymmetricCreateObject = AsymmetricCreateObject.of(completeEurvenInfo, certificateBuildData.getCompleteInfo());
        }

        certificateBuildData.getCertificateCreateObject().setAsymmetricCreateObject(asymmetricCreateObject);
        if (!publicKey.exists() || !publicKey.exists()) {
            KeyWrapper keyWrapper = asymmetricCreate.create(asymmetricCreateObject);
            certificateCreateObject.setPrivateKeyWrapper(DefaultKeyWrapper.of(keyWrapper.getPrivateKey()));
            certificateCreateObject.setPublicKeyWrapper(DefaultKeyWrapper.of(keyWrapper.getPublicKey()));

            publicKey.persist(certificateCreateObject.getPublicKeyWrapper());
            privateKey.persist(certificateCreateObject.getPrivateKeyWrapper());
        } else {
            certificateCreateObject.setPublicKeyWrapper(publicKey.read());
            certificateCreateObject.setPrivateKeyWrapper(privateKey.read());
            FileDataLoad<KeyWrapper> certificate = FileKeyLoad.of(certificateBuildData.getCertPath());
            if (certificate.exists()) {
                certificateCreateObject.setCertificateWrapper(certificate.read());
                return;
            }
        }
        if (Objects.nonNull(root)) {
            certificateCreateObject.setPrivateKeyWrapper(root.getCertificateCreateObject().getPrivateKeyWrapper());
            if (Objects.isNull(certificateBuildData.getCompleteEurvenInfo())) {
                certificateBuildData.setCompleteEurvenInfo(root.getCompleteEurvenInfo());
            }
            if (Objects.isNull(certificateBuildData.getCompleteInfo())) {
                certificateBuildData.setCompleteInfo(root.getCompleteInfo());
            }
            certificateCreateObject.setCertificateWrapper(root.getCertificateCreateObject().getCertificateWrapper());
            certificateCreateObject.setDistinguishedName(root.getCertificateCreateObject().getDistinguishedName());
        }
        KeyWrapper keyWrapper = certificateCreate.create(certificateCreateObject);
        certificateCreateObject.setCertificateWrapper(keyWrapper);
        FileDataLoad<KeyWrapper> certificate = FileKeyLoad.of(certificateBuildData.getCertPath());
        certificate.persist(keyWrapper);
    }

}
