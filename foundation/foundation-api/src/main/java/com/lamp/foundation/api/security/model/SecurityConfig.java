/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */

package com.lamp.foundation.api.security.model;


import java.util.Objects;

import com.lamp.foundation.api.model.ByteDecode;
import com.lamp.foundation.api.security.CipherStream;
import com.lamp.foundation.api.security.SecurityBaseType.SecurityBehavior;
import com.lamp.foundation.api.security.SecurityBaseType.SecurityEnum;

import lombok.Data;

/**
 * <pre>
 *     1. 密钥
 *          1. keyWrapper
 *     2. 密钥信息
 *          2. CompleteValue
 *     2. SecurityBehavior
 *
 *     3.
 * </pre>
 */
@Data
public class SecurityConfig {


    public static SecurityEntityBuilder create() {
        return new SecurityEntityBuilder();
    }

    private KeyWrapper keyWrapper;

    private CompleteEurvenInfo completeEurvenInfo;

    private CompleteInfo completeInfo;

    private ByteDecode iv;

    private String signName;

    private boolean isSymmetric;

    private SecurityEnum securityEnum;

    private SecurityBehavior securityBehavior;

    private int encryptionCipherLength;

    private int decryptionCipherLength;

    private CipherStream cipherStream;


    public static class SecurityEntityBuilder {

        private KeyWrapper keyWrapper;

        private CompleteEurvenInfo completeEurvenInfo;

        private CompleteInfo completeInfo;

        private String signName;

        private SecurityBehavior securityBehavior;

        private String iv;

        private CipherStream cipherStream;


        public SecurityEntityBuilder setSignName(String sign) {
            this.signName = sign;
            return this;
        }


        public SecurityEntityBuilder setCipherStream(CipherStream cipherStream) {
            this.cipherStream = cipherStream;
            return this;
        }


        public SecurityConfig builder() {
            SecurityConfig securityEntity = new SecurityConfig();

            securityEntity.setSignName(this.signName);

            securityEntity.setCipherStream(cipherStream);

            this.calculateLength(securityEntity);
            return securityEntity;
        }

        private void calculateLength(SecurityConfig securityEntity) {
            if (Objects.isNull(this.completeInfo)) {
                return;
            }
            if (!Objects.equals(this.completeInfo.getAsymmetric(), "RSA")) {
                return;
            }

            int i = 11;
            if (Objects.equals("1", "RSA/ECB/PKCS1Padding")) {
                i = 0;
            }
            // int capacity = (privateKey.length/256)*2*256
            int length = (this.keyWrapper.privateKey().length / 256) << 9;
            securityEntity.setEncryptionCipherLength((length >> 3) - i);
            securityEntity.setDecryptionCipherLength((length >> 3));
        }
    }

}
