package com.lamp.decoration.foundation.network.http.execute;


import io.netty.handler.codec.http.HttpResponse;

import com.lamp.decoration.foundation.network.http.handler.AsyncReturn;

public class CallAsynsExecuteRevert extends AbstractExecuteRevert {

    @Override
    public <T> T execute(HttpResponse httpResponse, AsyncReturn asyncReturn) {
        return null;
    }

}
