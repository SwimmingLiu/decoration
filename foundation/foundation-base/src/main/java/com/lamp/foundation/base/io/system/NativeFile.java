package com.lamp.foundation.base.io.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.lamp.foundation.api.lang.io.DecorationFile;

public class NativeFile implements DecorationFile {

    private File file;

    private String path;


    public NativeFile(String path) {
        this.path = path;
    }

    public NativeFile(File file) {
        this.file = file;
    }

    private void createFile() {
        if (Objects.nonNull(file)) {
            return;
        }
        synchronized (this) {
            if (Objects.isNull(file)) {
                this.file = new File(path);
            }
        }
    }

    @Override
    public String getName() {
        this.createFile();
        return this.file.getName();
    }

    @Override
    public String getPath() {
        this.createFile();
        return this.file.getPath();
    }

    @Override
    public String getAbsolutePath() {
        this.createFile();
        return this.file.getAbsolutePath();
    }

    @Override
    public String getParent() {
        this.createFile();
        return this.file.getParent();
    }

    @Override
    public long length() {
        this.createFile();
        return this.file.length();
    }

    @Override
    public long lastModified() {
        this.createFile();
        return this.file.lastModified();
    }

    @Override
    public boolean isFile() {
        this.createFile();
        return this.file.isFile();
    }

    @Override
    public boolean isDirectory() {
        this.createFile();
        return this.file.isDirectory();
    }

    @Override
    public boolean canRead() {
        this.createFile();
        return this.file.canRead();
    }

    @Override
    public boolean canWrite() {
        this.createFile();
        return this.file.canWrite();
    }

    @Override
    public boolean exists() {
        this.createFile();
        return this.file.exists();
    }

    @Override
    public boolean createNewFile() throws IOException {
        this.createFile();
        return this.file.createNewFile();
    }

    @Override
    public boolean mkdir() {
        this.createFile();
        return this.file.mkdir();
    }

    @Override
    public boolean mkdirs() {
        this.createFile();
        return this.file.mkdirs();
    }

    @Override
    public boolean delete() {
        this.createFile();
        return this.file.delete();
    }

    @Override
    public boolean renameTo(DecorationFile decorationFile) {
        this.createFile();
        if (decorationFile instanceof NativeFile) {
            NativeFile nativeFile = (NativeFile) decorationFile;
            return this.file.renameTo(nativeFile.file);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> list() {
        this.createFile();
        String[] arrays = this.file.list();
        if (arrays == null || arrays.length == 0) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>(arrays.length);
        Collections.addAll(list, arrays);
        return list;
    }

    @Override
    public List<DecorationFile> listFiles() {
        this.createFile();
        File[] arrays = this.file.listFiles();
        if (arrays == null || arrays.length == 0) {
            return Collections.emptyList();
        }
        List<DecorationFile> list = new ArrayList<>(arrays.length);
        for (File file : arrays) {
            DecorationFile decorationFile = new NativeFile(file);
            list.add(decorationFile);
        }
        return list;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        this.createFile();
        return Files.newInputStream(this.file.toPath());
    }
}
