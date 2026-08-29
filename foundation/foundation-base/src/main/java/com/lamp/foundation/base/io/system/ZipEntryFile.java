package com.lamp.foundation.base.io.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.lamp.foundation.api.lang.io.DecorationFile;

public class ZipEntryFile implements DecorationFile {

    private ZipEntry entry;

    private ZipFile zipFile;

    @Override
    public String getName() {
        return entry.getName();
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public String getAbsolutePath() {
        return "";
    }

    @Override
    public String getParent() {
        return "";
    }

    @Override
    public long length() {
        return this.entry.getSize();
    }

    @Override
    public long lastModified() {
        return this.entry.getTime();
    }

    @Override
    public boolean isFile() {
        return this.isDirectory();
    }

    @Override
    public boolean isDirectory() {
        return this.entry.isDirectory();
    }

    @Override
    public boolean canRead() {
        return true;
    }

    @Override
    public boolean canWrite() {
        return true;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public boolean createNewFile() throws IOException {
        return false;
    }

    @Override
    public boolean mkdir() {
        return false;
    }

    @Override
    public boolean mkdirs() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean renameTo(DecorationFile decorationFile) {
        return false;
    }

    @Override
    public List<String> list() {
        return Collections.emptyList();
    }

    @Override
    public List<DecorationFile> listFiles() {
        return Collections.emptyList();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.zipFile.getInputStream(this.entry);
    }
}
