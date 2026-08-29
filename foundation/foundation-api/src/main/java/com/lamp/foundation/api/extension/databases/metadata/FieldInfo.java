package com.lamp.foundation.api.extension.databases.metadata;

public class FieldInfo {

    private String fieldName;

    private String newFieldName;

    private Boolean notNull;


    private String logicalOperators = "and";

    private String relationSymbol = "=";

    private String value;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNewFieldName() {
        return newFieldName;
    }

    public void setNewFieldName(String newFieldName) {
        this.newFieldName = newFieldName;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public String getLogicalOperators() {
        return logicalOperators;
    }

    public void setLogicalOperators(String logicalOperators) {
        this.logicalOperators = logicalOperators;
    }

    public String getRelationSymbol() {
        return relationSymbol;
    }

    public void setRelationSymbol(String relationSymbol) {
        this.relationSymbol = relationSymbol;
    }
}
