package com.lamp.foundation.api.lang.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 主要的目的是统一 File  与 zip 的行为
 * <pre>
 *     可以抽象文件相关操作行为，oss，file，zip，hdfs
 * </pre>
 */
public interface DecorationFile {


    String getName();

    String getPath();

    String getAbsolutePath();

    String getParent();

    long length();

    long lastModified();

    boolean isFile();

    boolean isDirectory();

    boolean canRead();

    boolean canWrite();

    boolean exists();

    boolean createNewFile() throws IOException;

    boolean mkdir();

    boolean mkdirs();

    boolean delete();

    boolean renameTo(DecorationFile decorationFile);

    List<String> list();

    List<DecorationFile> listFiles();

    InputStream getInputStream() throws IOException;


}