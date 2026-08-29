package com.lamp.foundation.api.http.manufacturer;


import com.lamp.foundation.api.http.annotation.method.POST;

/**
 * @author hahaha
 */
public @interface Manufacturer {

    String name();

    String version() default "";

	POST post() default @POST;


}
