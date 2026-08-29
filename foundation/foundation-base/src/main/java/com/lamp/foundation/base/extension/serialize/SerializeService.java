package com.lamp.foundation.base.extension.serialize;

import org.apache.commons.lang3.JavaVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.foundation.api.enums.DataFormat;
import com.lamp.foundation.api.extension.serialize.Serialize;


public class SerializeService {

    private static volatile SerializeService DEFAULT;


    public static SerializeService getDefault() {
        if (Objects.nonNull(DEFAULT)) {
            return DEFAULT;
        }
        synchronized (SerializeService.class) {
            if (Objects.isNull(DEFAULT)) {
                DEFAULT = createDefault();
            }
        }
        return DEFAULT;
    }

    public static SerializeService createDefault() {
        SerializeService defaultService = new SerializeService();

        if (JavaVersion.JAVA_RECENT.atMost(JavaVersion.JAVA_10)) {
            FastJsonSerialize fastJsonSerializer = new FastJsonSerialize();
            defaultService.registerDefault(fastJsonSerializer);
        } else {
            FastJson2Serialize fastJson2Serialize = new FastJson2Serialize();
            defaultService.registerDefault(fastJson2Serialize);
        }
        YamlSerialize yamlSerialize = new YamlSerialize();
        defaultService.registerDefaultDataFormat(yamlSerialize);

        return defaultService;
    }


    @lombok.Getter
    private Serialize defaultSerialize;

    private final Serialize stringSerialize = new StringSerialize();

    private final Map<DataFormat, Serialize> defaultDataFormatMap = new HashMap<>();

    private final Map<DataFormat, Map<String, Serialize>> dataFormatMap = new HashMap<>();

    {
        for (DataFormat dataFormat : DataFormat.values()) {
            dataFormatMap.put(dataFormat, new ConcurrentHashMap<>());
        }
    }

    public Serialize getDefaultDataFormat(DataFormat dataFormat) {
        Serialize serialize = defaultDataFormatMap.get(dataFormat);
        return Objects.isNull(serialize) ? stringSerialize : serialize;
    }

    public void registerDefault(Serialize defaultSerialize) {
        this.defaultSerialize = defaultSerialize;
        this.registerDefaultDataFormat(defaultSerialize);
    }

    public void registerDefaultDataFormat(Serialize defaultSerialize) {
        this.defaultDataFormatMap.put(defaultSerialize.dataFormat(), defaultSerialize);
        this.registerSerialize(defaultSerialize);
    }

    public void registerSerialize(Serialize serialize) {
        this.dataFormatMap.get(serialize.dataFormat()).put(serialize.supplier(), serialize);
    }

}
