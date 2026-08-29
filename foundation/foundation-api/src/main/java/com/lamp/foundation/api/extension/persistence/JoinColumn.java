package com.lamp.foundation.api.extension.persistence;

/**
 * @author hahaha
 */
public @interface JoinColumn {

    Class<?> entity();

    String columnName();
}
