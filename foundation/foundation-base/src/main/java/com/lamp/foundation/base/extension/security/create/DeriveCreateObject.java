package com.lamp.foundation.base.extension.security.create;

import com.lamp.foundation.api.security.model.CreateObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hahaha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeriveCreateObject extends CreateObject {

    private byte[] mainKey;

    private byte[] salt;

    private String info;

    private int length;

}
