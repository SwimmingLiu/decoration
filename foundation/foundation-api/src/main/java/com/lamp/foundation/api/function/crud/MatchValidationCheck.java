package com.lamp.foundation.api.function.crud;

import java.lang.annotation.Annotation;

import com.lamp.foundation.api.extension.databases.metadata.DBMatedata;

/**
 * @author hahaha
 */
public interface MatchValidationCheck<T extends DBMatedata> {

    Annotation match();


    String check(T t);

}
