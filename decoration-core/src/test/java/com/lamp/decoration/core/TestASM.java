package com.lamp.decoration.core;

import org.apache.dubbo.common.compiler.support.JavassistCompiler;
import org.apache.dubbo.common.utils.IOUtils;
import org.apache.dubbo.metrics.report.MetricsReporterFactory;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.jupiter.api.Test;

public class TestASM {


    @Test
    public void test() throws Throwable {

        String path = System.getProperty("java.class.path");
        String[] strings = path.split(File.pathSeparator);
        List<URL> urls = new ArrayList<>();
        for (String s : strings) {
            if (s.contains("dubbo")) {
                urls.add(new URL("file:" + s));
                ZipFile zipFile = new ZipFile(s);
                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                System.out.println("ZIP文件中的所有条目:");
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    //System.out.println(entryName);
                }

            }
        }
        URLClassLoader ucl = new URLClassLoader(urls.toArray(new URL[0]),this.getClass().getClassLoader());
        URL u = TestASM.class.getResource(".");
        if (u == null) {
            return;
        }
        File dir = new File(u.getFile() + "/code.txt");
        FileReader fr = new FileReader(dir);
        String data = IOUtils.read(fr);
        JavassistCompiler compiler = new JavassistCompiler();
        compiler.compile(MetricsReporterFactory.class, data, ucl);
    }

}
