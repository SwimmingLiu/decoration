package com.lamp.foundation.base.extension.security.certificate;

import java.util.List;

import com.lamp.foundation.api.security.model.CompleteEurvenInfo;
import com.lamp.foundation.api.security.model.CompleteInfo;
import com.lamp.foundation.base.extension.security.create.CertificateCreateObject;

import lombok.Data;

/**
 * @author hahaha
 */
@Data
public class CertificateBuildData {

    private CompleteEurvenInfo completeEurvenInfo;

    private CompleteInfo completeInfo;

    private CertificateCreateObject certificateCreateObject;

    private String publicPath;

    private String privatePath;

    private String certPath;


    private List<CertificateBuildData> childCertificateBuildData;

}
