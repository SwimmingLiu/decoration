package com.lamp.foundation.base.extension.security;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.lamp.foundation.api.model.ByteDecode;
import com.lamp.foundation.api.model.ByteEncode;
import com.lamp.foundation.base.lang.lock.DelayCreate;
import com.lamp.foundation.base.model.DefaultByteEncode;

public class MessageDigestWrapper {

    private static final ThreadLocal<MessageDigestWrapper> THREAD_LOCAL = ThreadLocal.withInitial(MessageDigestWrapper::new);

    public static MessageDigestWrapper get() {
        return THREAD_LOCAL.get();
    }

    private final DelayCreate<MessageDigestExecute> md5 = DelayCreate.of(() -> this.createMessageDigest("MD5"));

    private final DelayCreate<MessageDigestExecute> sha = DelayCreate.of(() -> this.createMessageDigest("SHA"));

    private final DelayCreate<MessageDigestExecute> sha256 = DelayCreate.of(() -> this.createMessageDigest("SHA-256"));

    private final DelayCreate<MessageDigestExecute> sha512 = DelayCreate.of(() -> this.createMessageDigest("SHA-512"));


    private final Map<String, DelayCreate<MessageDigestExecute>> delayCreateHashMap = new HashMap<>();

    private final Map<String, MessageDigestExecute> messageDigestExecuteMap = new HashMap<>();

    {
        delayCreateHashMap.put("MD5", this.md5);
        delayCreateHashMap.put("SHA", this.sha);
        delayCreateHashMap.put("SHA-256", this.sha256);
        delayCreateHashMap.put("SHA-512", this.sha512);
    }

    public MacExecute createMac(String algorithm, ByteDecode byteDecode) throws NoSuchAlgorithmException, InvalidKeyException {
        return new MacExecute(algorithm, byteDecode);
    }


    public ByteEncode md5(byte[] data) {
        return this.md5.get().digest(data);
    }

    public ByteEncode sha(byte[] data) {
        return this.sha.get().digest(data);
    }

    public ByteEncode sha256(byte[] data) {
        return this.sha256.get().digest(data);
    }

    public ByteEncode sha512(byte[] data) {
        return this.sha512.get().digest(data);
    }


    public ByteEncode digest(String algorithm, byte[] data) {
        if (!SecurityUtils.isMessageDigest(algorithm)) {
            throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }
        MessageDigestExecute messageDigestExecute = messageDigestExecuteMap.get(algorithm);
        if (Objects.nonNull(messageDigestExecute)) {
            return messageDigestExecute.digest(data);
        }
        DelayCreate<MessageDigestExecute> digestExecuteDelayCreate = delayCreateHashMap.get(algorithm);
        if (Objects.nonNull(digestExecuteDelayCreate)) {
            messageDigestExecute = digestExecuteDelayCreate.get();
            messageDigestExecuteMap.put(algorithm, messageDigestExecute);
            return messageDigestExecute.digest(data);
        }
        messageDigestExecute = new MessageDigestExecute(algorithm);
        messageDigestExecuteMap.put(algorithm, messageDigestExecute);
        return messageDigestExecute.digest(data);
    }

    private MessageDigestExecute createMessageDigest(String algorithm) {
        return new MessageDigestExecute(algorithm);
    }

    static abstract class Digest {

        private static final int size = 1024 * 1024 * 8;

        abstract void update(byte[] bytes);

        abstract void update(byte[] bytes, int offset, int len);

        abstract byte[] digest();

        public ByteEncode digest(byte[] bytes) {

            if (bytes == null || bytes.length == 0) {
                throw new IllegalArgumentException("bytes is null or empty");
            }
            if (bytes.length <= size) {
                this.update(bytes);
            } else {
                int index = 0;
                for (; ; ) {
                    this.update(bytes, index, size);
                    if (bytes.length - index <= size) {
                        this.update(bytes, index, bytes.length - size);
                        break;
                    }
                    index += size;
                }
            }
            return new DefaultByteEncode(digest());
        }
    }

    public static class MessageDigestExecute extends Digest {

        private static final int size = 1024 * 1024 * 8;

        private final MessageDigest messageDigest;

        public MessageDigestExecute(String algorithm) {
            try {
                this.messageDigest = MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        void update(byte[] bytes) {
            this.messageDigest.update(bytes);
        }

        @Override
        void update(byte[] bytes, int offset, int len) {
            this.messageDigest.update(bytes, offset, len);
        }

        @Override
        byte[] digest() {
            return this.messageDigest.digest();
        }
    }

    public static class MacExecute extends Digest {

        private final Mac mac;


        public MacExecute(String algorithm, ByteDecode byteDecode) throws NoSuchAlgorithmException, InvalidKeyException {
            Mac mac = Mac.getInstance(algorithm);
            SecretKeySpec keySpec = new SecretKeySpec(byteDecode.data(), algorithm);
            mac.init(keySpec);
            this.mac = mac;
        }

        @Override
        void update(byte[] bytes) {
            this.mac.update(bytes);
        }

        @Override
        void update(byte[] bytes, int offset, int len) {
            this.mac.update(bytes, offset, len);
        }

        @Override
        byte[] digest() {
            return this.mac.doFinal();
        }
    }
}
