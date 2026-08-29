package com.lamp.decoration.foundation.network.redis.net.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.bootstrap.Bootstrap;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import com.lamp.decoration.foundation.network.http.handler.Http11Factory.ChannelWrapper;

public class CircuitNettyClinet {


    private final Bootstrap bootstrap = new Bootstrap();
    private DefaultEventExecutorGroup defaultEventExecutorGroup;
    private final Lock lockChannelTables = new ReentrantLock();
    private final ConcurrentMap<String /* addr */, ChannelWrapper> channelTables = new ConcurrentHashMap<String, ChannelWrapper>();

    public void init() {

    }

    // 回调对象
    // chanle管理对象
    // 同步 异步
    // 编译解析
    // cleint 配置
    // 链接管理对象

    public void start() {
    }

}
