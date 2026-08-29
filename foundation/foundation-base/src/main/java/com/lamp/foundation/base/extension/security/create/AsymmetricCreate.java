package com.lamp.foundation.base.extension.security.create;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Objects;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.lamp.foundation.api.security.CreateCipher;
import com.lamp.foundation.api.security.model.CompleteEurvenInfo;
import com.lamp.foundation.api.security.model.KeyWrapper;
import com.lamp.foundation.base.extension.security.key.DefaultKeyWrapper;


/**
 * @author hahaha
 */
public class AsymmetricCreate implements CreateCipher<AsymmetricCreateObject> {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    @Override
    public KeyWrapper create(AsymmetricCreateObject asymmetricCreateObject) throws Exception {
        KeyPairGenerator keyPairGenerator;
        if (Objects.nonNull(asymmetricCreateObject.getCompleteEurvenInfo())) {
            CompleteEurvenInfo completeEurvenInfo = asymmetricCreateObject.getCompleteEurvenInfo();
            keyPairGenerator =
                Objects.isNull(completeEurvenInfo.getProvider()) ? KeyPairGenerator.getInstance(completeEurvenInfo.getAlgorithm())
                    : KeyPairGenerator.getInstance(completeEurvenInfo.getAlgorithm(), completeEurvenInfo.getProvider());
            // 2. 创建安全的随机数生成器
            // getInstanceStrong() 通常推荐使用，因为它会选择系统上最强的可用算法
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            ECGenParameterSpec ecSpec = new ECGenParameterSpec(completeEurvenInfo.getNamedCurve());
            keyPairGenerator.initialize(ecSpec, secureRandom);
        } else {
            SymmetryCreateObject symmetryCreateObject = asymmetricCreateObject.getSymmetryCreateObject();
            keyPairGenerator = KeyPairGenerator.getInstance(symmetryCreateObject.getAlgorithm());
            keyPairGenerator.initialize(symmetryCreateObject.getLength());
        }
        return DefaultKeyWrapper.of(keyPairGenerator.generateKeyPair());
    }
}
