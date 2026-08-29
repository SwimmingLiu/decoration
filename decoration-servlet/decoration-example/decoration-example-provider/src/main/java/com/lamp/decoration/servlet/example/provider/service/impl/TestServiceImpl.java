package com.lamp.decoration.servlet.example.provider.service.impl;

import org.apache.dubbo.config.annotation.DubboService;

import com.lamp.decoration.servlet.example.service.TestService;

@DubboService
public class TestServiceImpl implements TestService {

    @Override
    public String test() {
        return "123";
    }
}
