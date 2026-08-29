package com.lamp.foundation.base.io.system;

import java.util.Objects;

public class TestData {

    private String path;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TestData)) {
            return false;
        }
        TestData testData = (TestData) o;
        return Objects.equals(path, testData.path) && Objects.equals(fileName, testData.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, fileName);
    }
}
