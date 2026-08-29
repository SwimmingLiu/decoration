package com.lamp.foundation.base.extension.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import com.lamp.foundation.api.enums.DataFormat;
import com.lamp.foundation.api.extension.serialize.Serialize;

public class YamlSerialize implements Serialize {

    private final Yaml yaml;

    public YamlSerialize() {
        this(new Yaml());
    }

    public YamlSerialize(Yaml yaml) {
        this.yaml = yaml;
    }

    @Override
    public String supplier() {
        return "snakeyaml";
    }

    @Override
    public DataFormat dataFormat() {
        return DataFormat.YAML;
    }

    @Override
    public String serialize(Object object) {
        return yaml.dumpAs(object, Tag.MAP, DumperOptions.FlowStyle.BLOCK);
    }

    @Override
    public byte[] serializeByte(Object object) {
        return yaml.dump(object).getBytes();
    }

    @Override
    public void serialize(Object object, OutputStream outputStream) throws IOException {
        outputStream.write(yaml.dump(object).getBytes());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialization(InputStream inputStream, Type t) {
        return yaml.loadAs(inputStream, (Class<T>) t);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialization(String data, Type t) {
        return yaml.loadAs(data, (Class<T>) t);
    }

    @Override
    public <T> T deserialization(byte[] data, Type t) {
        return this.deserialization(new String(data), t);
    }

}
