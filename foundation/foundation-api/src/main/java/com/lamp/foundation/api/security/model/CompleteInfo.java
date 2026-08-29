package com.lamp.foundation.api.security.model;

import java.util.Objects;

import lombok.Data;

/**
 * 主要
 */
@Data
public class CompleteInfo {

    private String asymmetric;

    private String symmetry;

    private String workingMode;

    private String padding;

    private Integer length;

    public CompleteInfo() {

    }

    public String algorithm() {
        String algorithm = Objects.nonNull(asymmetric) ? asymmetric : symmetry;
        if (Objects.nonNull(workingMode)) {
            algorithm = algorithm + "/" + workingMode;
        }
        if (Objects.nonNull(padding)) {
            algorithm = algorithm + "/" + padding;
        }
        return algorithm;
    }

}
