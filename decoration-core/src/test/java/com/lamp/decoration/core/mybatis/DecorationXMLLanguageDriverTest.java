package com.lamp.decoration.core.mybatis;

import org.apache.dubbo.common.extension.ExtensionDirector;
import org.apache.dubbo.common.extension.ExtensionLoader;

import org.junit.Test;

public class DecorationXMLLanguageDriverTest {

    DecorationXMLLanguageDriver driver = new DecorationXMLLanguageDriver();

    @Test
    public void test_init() {
        ExtensionLoader extensionLoader = ExtensionLoader.getExtensionLoader(Compiler.class);
        ExtensionDirector extensionDirector = new ExtensionDirector(null, null, null);
        extensionDirector.getExtensionLoader(org.apache.dubbo.common.compiler.Compiler.class).getAdaptiveExtension();
    }
}
