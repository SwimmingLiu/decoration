package com.lamp.foundation.api.function.channel.model;

import java.util.List;

import lombok.Data;

@Data
public class PullChannelResponse<T> {


    private List<T> data;

}
