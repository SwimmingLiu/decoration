package com.lamp.foundation.api.security;

import static com.lamp.foundation.api.security.SecurityBaseType.Padding.NoPadding;
import static com.lamp.foundation.api.security.SecurityBaseType.Padding.PKCS5Padding;
import static com.lamp.foundation.api.security.SecurityBaseType.Padding.PKCS7Padding;
import static com.lamp.foundation.api.security.SecurityBaseType.Padding.ZeroPadding;
import static com.lamp.foundation.api.security.SecurityBaseType.SecurityEnum.ASYMMETRIC;
import static com.lamp.foundation.api.security.SecurityBaseType.WorkingMode.CBC;
import static com.lamp.foundation.api.security.SecurityBaseType.WorkingMode.CFB;
import static com.lamp.foundation.api.security.SecurityBaseType.WorkingMode.ECB;
import static com.lamp.foundation.api.security.SecurityBaseType.WorkingMode.GCM;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lamp.foundation.api.lang.util.BitsetOperation;
import com.lamp.foundation.api.lang.util.BitsetOperation.Bitset;
import com.lamp.foundation.api.security.model.CompleteInfo;
import com.lamp.foundation.api.security.model.CompleteKey;

import lombok.Getter;

/**
 * @author hahaha
 */
public interface SecurityBaseType {

    String BC = "BC";

    /**
     * <pre>
     *     签名四个循序不能变
     * </pre>
     */
    enum SecurityBehavior {

        ENCRYPTION,

        DECRYPT,

        SIGN,

        VERIFY,

        ENCRYPTION_DECRYPT(ENCRYPTION, DECRYPT),

        ENCRYPTION_SIGN(ENCRYPTION, SIGN),

        ENCRYPTION_VERIFY(ENCRYPTION, VERIFY),

        DECRYPT_SIGN(DECRYPT, SIGN),

        DECRYPT_VERIFY(DECRYPT, VERIFY),

        VERIFY_SIGN(VERIFY, SIGN),

        ALL(ENCRYPTION, DECRYPT, VERIFY, SIGN),
        ;

        private int current = -1;

        SecurityBehavior() {
        }

        SecurityBehavior(SecurityBehavior... securityBehaviors) {
            int current = 0;
            for (SecurityBehavior securityBehavior : securityBehaviors) {
                current += securityBehavior.ordinal();
            }
            this.current = current;
        }

        public Bitset getBitSet() {
            return BitsetOperation.getDefault(this.current == -1 ? this.ordinal() : this.current);
        }

    }


    enum SecurityEnum {

        SYMMETRY,

        ASYMMETRIC,

        DERIVE,


    }

    enum Symmetry {

        /**
         * 推荐
         */
        AES,

        AES_128_GCM,

        AES_256_GCM,

        /**
         * 国密
         */
        SM4,

        ChaCha20,

        ChaCha20_Poly1305,

        @Deprecated
        ARCFOUR,

        @Deprecated
        BLOWFISH,

        @Deprecated
        DES,

        @Deprecated
        DESEDE,

        @Deprecated
        RC2,

    }

    enum Asymmetric {

        RSA("RSA"),

        /**
         * 国密
         */
        SM2(EurveAlgorithm.EC, "SM2", BC),

        /**
         * 推荐
         */
        ECC(EurveAlgorithm.EC, "ECIES ECIESwithAES", BC),

        /**
         * Curve25519 / Curve448 家族（现代高性能）
         * <pre>
         *     KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519"); // 不需要指定曲线名，算法即曲线
         * </pre>
         * </p>
         * 极快‌，广泛用于 Signal、WhatsApp、TLS 1.3。
         */
        Curve25519,

        /**
         * 极快且安全‌，替代 ECDSA 签名的最佳选择。
         */
        Ed25519,

        /**
         * 提供更高安全强度（~224位），速度稍慢于25519。
         */
        Curve448,

        /**
         * 高安全强度的签名算法。
         */
        Ed448,

        /**
         * 欧洲算法
         */
        brainpool(EurveAlgorithm.EC, "ECIES", BC),

        ;


        private GMNamedCurves curve;

        private EurveAlgorithm curveAlgorithm;

        private String algorithm;

        @Getter
        private String provider;

        @Getter
        private String cipher;

        private SignatureAlgorithm signatureAlgorithm;


        Asymmetric() {
        }

        Asymmetric(String algorithm) {
            this.algorithm = algorithm;
        }

        Asymmetric(EurveAlgorithm curveAlgorithm, String cipher, String provider) {
            this.curveAlgorithm = curveAlgorithm;
            this.cipher = cipher;
            this.provider = provider;

        }

        public String algorithm() {
            return Objects.nonNull(this.algorithm) ? this.algorithm : curveAlgorithm.name();
        }

        public GMNamedCurves defaultCurves() {
            if (Objects.nonNull(this.curve)) {
                return this.curve;
            }
            GMNamedCurves curves = this.getCurve();
            this.curve = curves;
            return curves;
        }

        public SignatureAlgorithm defaultSignatureAlgorithm() {
            if (Objects.nonNull(this.signatureAlgorithm)) {
                return this.signatureAlgorithm;
            }
            SignatureAlgorithm signatureAlgorithm = this.getSignatureAlgorithm();
            this.signatureAlgorithm = signatureAlgorithm;
            return signatureAlgorithm;
        }

        private GMNamedCurves getCurve() {
            for (GMNamedCurves gmNamedCurves : GMNamedCurves.values()) {
                if (gmNamedCurves.defaultCurves(this)) {
                    return gmNamedCurves;
                }
            }
            return null;
        }

        private SignatureAlgorithm getSignatureAlgorithm() {
            SignatureAlgorithm one = null;
            for (SignatureAlgorithm signatureAlgorithm : SignatureAlgorithm.values()) {
                if (signatureAlgorithm.getAsymmetric().equals(this)) {
                    if (signatureAlgorithm.isDefaults()) {
                        return signatureAlgorithm;
                    } else {
                        if (Objects.isNull(one)) {
                            one = signatureAlgorithm;
                        }
                    }
                }
            }
            return one;

        }

    }

    enum DeriveAlgorithm {

        PBKDF2,

        HKDF,

        Scrypt,

        Argon2,

        Bcrypt,


    }

    @Getter
    enum SignatureAlgorithm {

        SM3withSM2(Asymmetric.SM2, true),

        SM2DSA(Asymmetric.SM2),

        SM2MLDSA(Asymmetric.SM2),

        sha1WithRSAEncryption(Asymmetric.RSA),

        sha256WithRSAEncryption(Asymmetric.RSA, true),

        sha384WithRSAEncryption(Asymmetric.RSA),

        sha512WithRSAEncryption(Asymmetric.RSA),

        ;

        private Asymmetric asymmetric;

        private boolean defaults;

        SignatureAlgorithm() {

        }

        SignatureAlgorithm(Asymmetric algorithm) {
            this(algorithm, false);
        }

        SignatureAlgorithm(Asymmetric algorithm, boolean defaults) {
            this.asymmetric = algorithm;
            this.defaults = defaults;
        }

    }


    /**
     * 分组算法
     */
    enum GroupAlgorithm {

        AES(GCM, PKCS7Padding, new WorkingMode[] {CFB, CBC, ECB}, new Padding[] {PKCS5Padding, ZeroPadding, NoPadding}),

        AES_128_GCM(AES.completeKey),

        AES_256_GCM(AES.completeKey),

        SM4(AES.completeKey),

        IDEA(AES.completeKey),

        PRESENT,

        CLEFIA,

        FBC,

        DES,

        _3DES,

        ;

        private CompleteKey completeKey;

        private CompleteInfo completeInfo;


        GroupAlgorithm() {
        }

        GroupAlgorithm(CompleteKey completeKey) {
            this.completeKey = completeKey;
            this.build();
        }

        GroupAlgorithm(WorkingMode workingMode, Padding padding, WorkingMode[] workingModes, Padding[] paddings) {
            CompleteKey completeKey = new CompleteKey();
            completeKey.setWorkingMode(workingMode);
            completeKey.setPaddings(Arrays.stream(paddings)
                .collect(Collectors.toSet()));
            completeKey.setPadding(padding);
            completeKey.setWorkingModes(Arrays.stream(workingModes)
                .collect(Collectors.toSet()));

            this.completeKey = completeKey;
            this.build();
        }

        void build() {
            CompleteInfo completeInfo = new CompleteInfo();

        }

    }

    /**
     * 工作模式，AES/CBC/PKCS7Padding
     * <pre>
     *     AES/CBC/PKCS7Padding
     *     {SecurityEnum}/{WorkingMode}/{Padding}
     * </pre>
     */
    enum WorkingMode {
        NONE,

        CBC,

        CCM,

        CFB,

        CFBx,

        CTR,

        CTS,

        EBC,

        GCM,

        OFB,

        OFBx,

        /**
         * 推荐
         */
        PCBC,


        ECB
    }

    enum Padding {

        /**
         * 不建议使用
         */
        PKCS1Padding,

        /**
         * 默认。缺少 n 字节，则填充 n 个值为 n 的字节。解密时可精准去除，安全性高
         */
        PKCS7Padding,

        /**
         * 不建议是用。 用0字节填充至块大小。缺点是无法区分真实数据末尾的0和填充的0，仅适用于以非0结尾的数据
         */
        ZeroPadding,

        /**
         * 不建议使用，不填充，要求明文长度必须是块大小的整数倍，否则报错
         */
        NoPadding,

        @Deprecated
        ISO10126Padding,

        @Deprecated
        SSL3Padding,

        @Deprecated
        OAEPPadding,

        /**
         * 不建议使用
         */
        PKCS5Padding(ASYMMETRIC, "PKCS5Padding"),

        /**
         * 不建议使用，使用 SHA-1 作为主哈希算法，MGF1 默认也使用 SHA-1。这是较旧的配置，安全性相对较低，仅用于兼容旧系统
         */
        OAEPWithSHA_1_AndMGF1Padding(ASYMMETRIC, "OAEPWithSHA-1AndMGF1Padding"),

        /**
         * 使用 SHA-256 作为主哈希算法。‌注意‌：在大多数 Java 实现中，MGF1 的哈希算法默认仍为 SHA-1，除非通过 OAEPParameterSpec 显式指定
         */
        OAEPWithSHA_256_AndMGF1Padding(ASYMMETRIC, "OAEPWithSHA-256AndMGF1Padding"),

        /**
         * 384
         */
        OAEPWithSHA_384_AndMGF1Padding(ASYMMETRIC, "OAEPWithSHA-384AndMGF1Padding"),

        /**
         * 512
         */
        OAEPWithSHA_512_AndMGF1Padding(ASYMMETRIC, "OAEPWithSHA-512AndMGF1Padding");


        private final SecurityEnum securityEnum;

        private String name;

        Padding() {
            this(SecurityEnum.SYMMETRY, null);
            this.name = this.name();
        }

        Padding(SecurityEnum securityEnum) {
            this(securityEnum, null);
            this.name = this.name();
        }

        Padding(SecurityEnum securityEnum, String name) {
            this.securityEnum = securityEnum;
            if (name != null) {
                this.name = name;
            }
        }
    }

    /**
     * 不同曲线类型
     */
    enum EurveAlgorithm {

        EC,

        /**
         * Curve25519 / Curve448 家族（现代高性能）
         * <pre>
         *     KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519"); // 不需要指定曲线名，算法即曲线
         * </pre>
         * </p>
         * 极快‌，广泛用于 Signal、WhatsApp、TLS 1.3。
         */
        Curve25519,

        /**
         * 极快且安全‌，替代 ECDSA 签名的最佳选择。
         */
        Ed25519,

        /**
         * 提供更高安全强度（~224位），速度稍慢于25519。
         */
        Curve448,

        /**
         * 高安全强度的签名算法。
         */
        Ed448,


    }

    enum SecurityLevel {

        AES_128,

        AES_192,

        AES_256,
    }

    /**
     * EC ,
     */
    @Getter
    enum GMNamedCurves {

        secp256r1(Asymmetric.ECC, 256, SecurityLevel.AES_128, true),

        secp384r1(Asymmetric.ECC, 384, SecurityLevel.AES_192),

        secp521r1(Asymmetric.ECC, 521, SecurityLevel.AES_256),

        /**
         * 比特币、以太坊等区块链系统
         */
        secp256k1(Asymmetric.ECC, 256, SecurityLevel.AES_128),

        /**
         * 国密 SM2 标准曲线‌。在 Bouncy Castle 中常用此名称生成 SM2 密钥对。
         */
        sm2p256v1(Asymmetric.SM2, 256, SecurityLevel.AES_128, true),

        brainpoolP256r1(Asymmetric.brainpool, 256, SecurityLevel.AES_128, true),

        brainpoolP384r1(Asymmetric.brainpool, 384, SecurityLevel.AES_192),

        brainpoolP521r1(Asymmetric.brainpool, 521, SecurityLevel.AES_256),

        ;

        private final Asymmetric asymmetric;

        private boolean defaults;

        private SecurityLevel securityLevel;

        GMNamedCurves() {
            this.asymmetric = Asymmetric.ECC;
        }

        GMNamedCurves(Asymmetric asymmetric, int length, SecurityLevel securityLevel) {
            this(asymmetric, length, securityLevel, false);
        }

        GMNamedCurves(Asymmetric asymmetric, int length, SecurityLevel securityLevel, boolean defaults) {
            this.asymmetric = asymmetric;
            this.defaults = defaults;
        }

        public boolean defaultCurves(Asymmetric asymmetric) {
            return Objects.equals(asymmetric, this.asymmetric) && this.defaults;
        }

    }


    @Getter
    enum SignatureType {

        SHA256withRSA(Asymmetric.RSA),

        SHA384withRSA(Asymmetric.RSA),

        SHA512withRSA(Asymmetric.RSA),

        SHA256withRSAPSSPadding(Asymmetric.RSA),

        SHA384withRSAPSSPadding(Asymmetric.RSA),

        SHA512withRSAPSSPadding(Asymmetric.RSA),

        /**
         * DSA
         */
        SHA256withECDSA(Asymmetric.ECC),

        SHA384withECDSA(Asymmetric.ECC),

        SHA512withECDSA(Asymmetric.ECC),

        /**
         * ECDSA
         */
        SHA1withECDSA(Asymmetric.ECC),

        /**
         *
         */
        NONEwithECDSA(Asymmetric.ECC),

        /**
         *
         */
        Ed25519(Asymmetric.Ed25519),

        Ed448(Asymmetric.Ed448),


        SM2withSM3(Asymmetric.SM2),

        SM3withSM2(Asymmetric.SM2),

        ;

        private Asymmetric asymmetric;

        SignatureType(Asymmetric asymmetric) {

        }
    }


    @Getter
    enum EurveKeyAgreement {

        ECDH(Asymmetric.ECC),

        DHE(null),

        SM2(Asymmetric.SM2),

        MQV(null),

        ML_KEM(null),

        ;
        private Asymmetric asymmetric;

        EurveKeyAgreement(Asymmetric asymmetric) {

        }
    }

}
