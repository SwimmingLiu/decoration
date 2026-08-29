package com.lamp.foundation.api.security.model;

import java.util.Set;

import com.lamp.foundation.api.security.SecurityBaseType.Asymmetric;
import com.lamp.foundation.api.security.SecurityBaseType.Padding;
import com.lamp.foundation.api.security.SecurityBaseType.Symmetry;
import com.lamp.foundation.api.security.SecurityBaseType.WorkingMode;

import lombok.Data;

@Data
public class CompleteKey {

    private Asymmetric asymmetric;

    private Symmetry symmetry;

    private WorkingMode workingMode;

    private Set<WorkingMode> workingModes;

    private Padding padding;

    private Set<Padding> paddings;

}
