package com.lamp.foundation.base.io.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

import com.lamp.foundation.api.enums.DataFormat;
import com.lamp.foundation.api.extension.serialize.Serialize;
import com.lamp.foundation.api.model.ByteEncode;
import com.lamp.foundation.base.extension.security.MessageDigestWrapper;
import com.lamp.foundation.base.extension.serialize.SerializeService;
import com.lamp.foundation.base.lang.util.string.StringUtils;

/**
 *
 */
public class FileDataLoad<T> {

    public static void ofString(String content, String path) throws IOException {
        FileDataLoad<String> fileDataLoad = new FileDataLoad<>(path);
        fileDataLoad.persist(content);
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void write(File file, String data, boolean create) throws IOException {
        if (create) {
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            os.write(data.getBytes());
        }
    }

    private final String path;

    private final Class<T> clazz;

    private Serialize serialize;

    private File file;


    public FileDataLoad(String path) {
        this(path, null);
    }

    public FileDataLoad(String path, Class<T> clazz) {
        this(path, clazz, null);
    }

    public FileDataLoad(String filePath, Class<T> clazz, Serialize serialize) {
        this.path = filePath;
        this.clazz = clazz;
        this.init();
        if (Objects.nonNull(serialize)) {
            this.serialize = serialize;
            return;
        }
        if (Objects.isNull(clazz)) {
            return;
        }
        String suffix = StringUtils.suffix(filePath);
        if (Objects.isNull(suffix)) {
            this.serialize = SerializeService.getDefault().getDefaultSerialize();
            return;
        }
        DataFormat dataFormat = DataFormat.valueOf(suffix.toUpperCase());
        this.serialize = SerializeService.getDefault().getDefaultDataFormat(dataFormat);

    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public void init() {
        File file = new File(path);
        File fileParent = file.getParentFile();
        if (Objects.nonNull(fileParent)) {
            fileParent.mkdirs();
        }
        this.file = file;
    }

    public boolean exists() {
        return this.file.exists();
    }

    public T read() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        boolean isBak = false;
        byte[] data = this.readByte(file);
        if (Objects.isNull(data)) {
            File bakFile = new File(path + ".bak");
            isBak = true;
            if (!bakFile.exists()) {
                return null;
            }
            data = this.readByte(file);
            if (Objects.isNull(data)) {
                return null;
            }
        }
        try {
            return serialize.deserialization(data, this.clazz);
        } catch (Exception e) {
            if (isBak) {
                File bakFile = new File(path + ".bak");
                if (!bakFile.exists()) {
                    throw e;
                }
                data = this.readByte(file);
                if (Objects.isNull(data)) {
                    throw e;
                }
                assert serialize != null;
                return serialize.deserialization(data, this.clazz);
            } else {
                throw e;
            }
        }
    }

    public synchronized void persist(T obj) throws IOException {
        File file = new File(path);
        String oldData = this.read(file);
        if (Objects.nonNull(oldData)) {
            String bakName = path + ".bak";
            File backName = new File(bakName);
            write(backName, oldData, true);
        }
        String newData;
        if (obj instanceof String) {
            newData = (String) obj;
        } else {
            newData = serialize.serialize(obj);
            if (obj instanceof VerificationCode) {
                ByteEncode byteEncode = MessageDigestWrapper.get().md5(newData.getBytes());
                //noinspection PatternVariableCanBeUsed
                VerificationCode verificationCode = (VerificationCode) obj;
                verificationCode.setCode(byteEncode.hex());
                newData = serialize.serialize(obj);
            }
        }
        write(file, newData, true);
    }

    public byte[] readByte(File file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        byte[] bytes = new byte[(int) file.length()];
        boolean result;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            int len = inputStream.read(bytes);
            result = len == bytes.length;
        }
        return result ? bytes : null;
    }

    public String read(File file) throws IOException {
        byte[] bytes = this.readByte(file);

        return Objects.isNull(bytes) ? null : new String(bytes);
    }

    public boolean delete() throws IOException {
        File file = new File(path);
        return file.delete();
    }


}
