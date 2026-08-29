package com.lamp.foundation.base.extension.conversion;

import java.security.Provider;
import java.security.Security;

import org.junit.Test;

import com.lamp.foundation.base.extension.conversion.string.StringToBaseType;

public class ConversionServiceTest {

    ConversionService conversionService = new ConversionService();

    @Test
    public void test() throws NoSuchFieldException {
        Provider[] providers = Security.getProviders();
        conversionService.register(StringToBaseType.class);
    }

}
