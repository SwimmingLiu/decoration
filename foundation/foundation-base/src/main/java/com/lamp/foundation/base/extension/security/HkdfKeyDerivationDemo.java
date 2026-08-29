package com.lamp.foundation.base.extension.security;

import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class HkdfKeyDerivationDemo {

    public static byte[] aesDecrypt(byte[] combined, byte[] keyBytes) throws Exception {
        // 提取 IV
        byte[] iv = Arrays.copyOfRange(combined, 0, 12);
        byte[] ciphertext = Arrays.copyOfRange(combined, 12, combined.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);

        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
        return cipher.doFinal(ciphertext);
    }

    // ================= AES-GCM 加密辅助方法 =================
    public static byte[] aesEncrypt(byte[] plaintext, byte[] keyBytes) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        // GCM 需要 12字节 IV
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv); // 128-bit tag
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

        byte[] ciphertext = cipher.doFinal(plaintext);

        // 将 IV 拼接到密文前，以便解密时使用
        byte[] combined = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(ciphertext, 0, combined, iv.length, ciphertext.length);

        return combined;
    }

    /**
     * 使用 HKDF 派生密钥
     *
     * @param ikm    输入主密钥
     * @param salt   盐
     * @param info   上下文信息
     * @param length 输出密钥长度（字节）
     */
    public static byte[] deriveKey(byte[] ikm, byte[] salt, byte[] info, int length) {
        HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA256Digest());
        HKDFParameters params = new HKDFParameters(ikm, salt, info);
        hkdf.init(params);

        byte[] output = new byte[length];
        hkdf.generateBytes(output, 0, length);
        return output;
    }

    // ================= HMAC 辅助方法 =================
    public static byte[] generateHmac(byte[] data, byte[] keyBytes) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(keySpec);
        return mac.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== HKDF 密钥派生与分离演示 ===\n");

        // 1. 准备主密钥 (IKM) - 实际场景中可能来自 Diffie-Hellman 交换或随机生成
        byte[] masterKey = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(masterKey);
        System.out.println("1. 主密钥 (Base64): " + Base64.getEncoder().encodeToString(masterKey));

        // 2. 定义盐 (Salt) - 可选，但建议提供以增加随机性
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // 3. 定义上下文信息 (Info) - 用于区分不同用途的密钥
        byte[] infoEncryption = "encryption-key".getBytes();
        byte[] infoAuthentication = "authentication-key".getBytes();

        // 4. 派生子密钥
        // 派生 AES-256 密钥 (32字节)
        byte[] aesKeyBytes = deriveKey(masterKey, salt, infoEncryption, 32);
        // 派生 HMAC-SHA256 密钥 (32字节)
        byte[] hmacKeyBytes = deriveKey(masterKey, salt, infoAuthentication, 32);

        System.out.println("2. 派生的 AES 密钥 (Base64): " + Base64.getEncoder().encodeToString(aesKeyBytes));
        System.out.println("3. 派生的 HMAC 密钥 (Base64): " + Base64.getEncoder().encodeToString(hmacKeyBytes));

        // 验证两个密钥是否不同
        if (!Arrays.equals(aesKeyBytes, hmacKeyBytes)) {
            System.out.println("✅ 密钥分离成功：AES 密钥与 HMAC 密钥不同。\n");
        } else {
            System.out.println("❌ 错误：密钥相同！\n");
        }

        // 5. 使用派生的 AES 密钥加密数据
        String plaintext = "Sensitive Data";
        byte[] ciphertext = aesEncrypt(plaintext.getBytes(), aesKeyBytes);
        System.out.println("4. AES 加密密文 (Base64): " + Base64.getEncoder().encodeToString(ciphertext));

        // 6. 使用派生的 HMAC 密钥对密文进行签名
        byte[] mac = generateHmac(ciphertext, hmacKeyBytes);
        System.out.println("5. HMAC 签名 (Base64): " + Base64.getEncoder().encodeToString(mac));

        // 7. 验证完整性并解密
        boolean isMacValid = verifyHmac(ciphertext, mac, hmacKeyBytes);
        if (isMacValid) {
            String decrypted = new String(aesDecrypt(ciphertext, aesKeyBytes));
            System.out.println("6. 验签通过，解密明文: " + decrypted);
        } else {
            System.out.println("6. 验签失败，数据可能被篡改！");
        }
    }

    public static boolean verifyHmac(byte[] data, byte[] expectedMac, byte[] keyBytes) throws Exception {
        byte[] actualMac = generateHmac(data, keyBytes);
        return java.security.MessageDigest.isEqual(actualMac, expectedMac); // 恒定时间比较
    }

    static {
        // 注册 Bouncy Castle 提供者
        Security.addProvider(new BouncyCastleProvider());
    }

}
