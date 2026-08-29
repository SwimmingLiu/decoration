package com.lamp.foundation.api.function.channel;

import java.util.Collection;
import java.util.List;

import com.lamp.foundation.api.function.channel.model.PullChannelResponse;
import com.lamp.foundation.api.function.channel.model.RegisterRequest;


public class ChannelModelUtils {


    public static <T> RegisterRequest<T> createRegisterRequestObject(Collection<Object> insert, Collection<Object> update,
        Collection<Object> delete) {
        return createRegisterRequestObject(null, insert, update, delete);
    }

    @SuppressWarnings("unchecked")
    public static <T> RegisterRequest<T> createRegisterRequestObject(Collection<Object> all, Collection<Object> insert, Collection<Object> update,
        Collection<Object> delete) {
        return createRegisterRequest((Collection<T>) all, (Collection<T>) insert, (Collection<T>) update, (Collection<T>) delete);
    }

    public static <T> RegisterRequest<T> createRegisterRequest(Collection<T> insert, Collection<T> update, Collection<T> delete) {
        return createRegisterRequest(null, insert, update, delete);
    }

    public static <T> RegisterRequest<T> createRegisterRequest(Collection<T> all, Collection<T> insert, Collection<T> update, Collection<T> delete) {
        RegisterRequest<T> registerRequest = new RegisterRequest<>();
        registerRequest.setAll(all);
        registerRequest.setInsert(insert);
        registerRequest.setUpdate(update);
        registerRequest.setDelete(delete);
        return registerRequest;
    }

    public static <T> PullChannelResponse<T> createPullChannelResponse(List<T> data) {
        PullChannelResponse<T> pullChannelResponse = new PullChannelResponse<>();
        pullChannelResponse.setData(data);
        return pullChannelResponse;
    }
}
