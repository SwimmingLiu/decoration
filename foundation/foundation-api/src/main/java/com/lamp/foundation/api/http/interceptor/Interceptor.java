/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.foundation.api.http.interceptor;


import com.lamp.foundation.api.http.request.RequestWrapper;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;


public interface Interceptor {

    default Object[] handlerBefore(RequestWrapper requestWrapper, Object[] args) {
        return args;
    }

    default HttpRequest handlerRequest(RequestWrapper requestWrapper, HttpRequest defaultFullHttpRequest) {
        return defaultFullHttpRequest;
    }

    default void handlerError(Throwable throwable, RequestWrapper requestWrapper, Object[] args) {

    }

    default void handlerResponse(HttpResponse defaultHttpResponse) {
    }

    default void handlerAfter(RequestWrapper requestWrapper, HttpResponse defaultHttpResponse) {

    }
}
