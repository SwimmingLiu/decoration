package com.lamp.decoration.foundation.network.http.execute;


import io.netty.handler.codec.http.HttpResponse;

import com.lamp.decoration.foundation.network.http.handler.AsyncReturn;

public interface ExecuteRevert {

    
    <T>T execute(HttpResponse httpResponse , AsyncReturn asyncReturn);
}
