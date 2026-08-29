package com.lamp.foundation.base.extension.security.key;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.security.cert.X509Certificate;

import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import com.lamp.foundation.api.enums.DataFormat;
import com.lamp.foundation.api.extension.serialize.Serialize;
import com.lamp.foundation.api.security.model.KeyWrapper;
import com.lamp.foundation.base.io.system.FileDataLoad;

/**
 * @author hahaha
 */
public class FileKeyLoad implements KeyLoad {


    public static FileDataLoad<KeyWrapper> of(String path) {
        return new FileDataLoad<>(path, KeyWrapper.class, new PEMSerialize());
    }

    private final FileDataLoad<KeyWrapper> fileDataLoad;


    public FileKeyLoad(String path) {
        this.fileDataLoad = new FileDataLoad<>(path, null, new PEMSerialize());
    }

    public FileDataLoad<KeyWrapper> getFileDataLoad() {
        return fileDataLoad;
    }


    @Override
    public byte[] publicKey() {
        return new byte[0];
    }

    @Override
    public byte[] privateKey() {
        return new byte[0];
    }

    @Override
    public X509Certificate certificate() {
        return null;
    }

    static class PEMSerialize implements Serialize {

        @Override
        public String supplier() {
            return "";
        }

        @Override
        public DataFormat dataFormat() {
            return null;
        }

        @Override
        public String serialize(Object object) {
            try {
                KeyWrapper keyWrapper = (KeyWrapper) object;
                StringWriter bufferedWriter = new StringWriter();
                JcaPEMWriter writer = new JcaPEMWriter(bufferedWriter);
                writer.writeObject(keyWrapper.key());
                writer.flush();
                return bufferedWriter.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public byte[] serializeByte(Object object) {
            return serialize(object).getBytes();
        }

        @Override
        public void serialize(Object object, OutputStream outputStream) throws IOException {

        }

        @Override
        public <T> T deserialization(InputStream inputStream, Type t) throws IOException {
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T deserialization(String data, Type t) {
            StringReader reader = new StringReader(data);
            PEMParser pemParser = new PEMParser(reader);
            try {

                return (T) DefaultKeyWrapper.of(pemParser.readObject());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public <T> T deserialization(byte[] data, Type t) {
            return this.deserialization(new String(data), t);
        }
    }
}
