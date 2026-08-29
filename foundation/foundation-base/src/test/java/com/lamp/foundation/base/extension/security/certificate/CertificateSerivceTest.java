package com.lamp.foundation.base.extension.security.certificate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lamp.foundation.api.security.SecurityBaseType.Asymmetric;
import com.lamp.foundation.api.security.model.CompleteEurvenInfo;
import com.lamp.foundation.base.extension.security.create.CertificateCreateObject;
import com.lamp.foundation.base.extension.security.create.CertificateCreateObject.CaObjectType;
import com.lamp.foundation.base.extension.security.create.ObjectUtils;
import com.lamp.foundation.base.lang.time.LocalDateTimeUtils;

public class CertificateSerivceTest {

    CertificateSerivce certificateSerivce = new CertificateSerivce();

    CertificateBuildData certificateBuildData = new CertificateBuildData();

    @Before
    public void init() {
        CompleteEurvenInfo completeEurvenInfo = CompleteEurvenInfo.of(Asymmetric.SM2);
        certificateBuildData.setCompleteEurvenInfo(completeEurvenInfo);
        certificateBuildData.setPrivatePath("./file/ca.key");
        certificateBuildData.setPublicPath("./file/ca.pub");
        certificateBuildData.setCertPath("./file/ca.crt");

        CertificateCreateObject certificateCreateObject = new CertificateCreateObject();
        certificateCreateObject.setCaObjectType(CaObjectType.ROOT);

        certificateCreateObject.setDuration(LocalDateTimeUtils.ONE_YEAR_IN_MILLIS);
        ObjectUtils.supplement(certificateCreateObject);
        certificateBuildData.setCertificateCreateObject(certificateCreateObject);

        List<CertificateBuildData> certificateBuildDataList = new ArrayList<>();
        certificateBuildData.setChildCertificateBuildData(certificateBuildDataList);

        String[] service = new String[] {"api-service", "etcd-service"};
        for (String s : service) {
            CertificateBuildData certificateBuildData = new CertificateBuildData();
            certificateBuildData.setPrivatePath("./file/" + s + "/" + s + ".key");
            certificateBuildData.setPublicPath("./file/" + s + "/" + s + ".pub");
            certificateBuildData.setCertPath("./file/" + s + "/" + s + ".crt");
            certificateCreateObject = new CertificateCreateObject();
            certificateCreateObject.setDuration(LocalDateTimeUtils.ONE_YEAR_IN_MILLIS);
            certificateCreateObject.setCaObjectType(CaObjectType.SERVICE);

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
            certificateCreateObject.setDnsNames(Arrays.asList(apiServerSans));
            ObjectUtils.supplement(certificateCreateObject);
            certificateBuildData.setCertificateCreateObject(certificateCreateObject);

            certificateBuildDataList.add(certificateBuildData);
        }
    }


    @Test
    public void test() throws Exception {
        certificateSerivce.buildByFile(this.certificateBuildData);
    }

}
