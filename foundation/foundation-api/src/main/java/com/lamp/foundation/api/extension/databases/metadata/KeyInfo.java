package com.lamp.foundation.api.extension.databases.metadata;

import java.util.List;

import lombok.Data;

@Data
public class KeyInfo {

    private String tableName;

    private String keyName;

    private KeyType type;

    private List<String> keys;

    private List<KeyLimitWrapper> limitKeys;

    @Data
    public static class KeyLimitWrapper {

        private String key;

        private Integer limit;

        private Sort sort;

        private Integer indexSchema;

    }

}
