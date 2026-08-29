package cn.lampup.decoration.foundation.gentle.rocketmq.api;

import cn.lampup.decoration.foundation.gentle.rocketmq.SendConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * DefaultProducerClient <BR>
 * 1. 消费级别
 * 2. 批量接口操作
 * 3. 
 */
public class DefaultProducerClient {

    private Map<Class<?>, SendConfig> classAndSendConfig = new HashMap<>();

    public void sendOneway(Object sendObjct) {

    }

    public void send(Object sendObjct) {

    }

    public void sendAsyn(Object sendObjct) {

    }

    public void sendListOneway(List<Object> sendObjctList) {
        for (Object object : sendObjctList) {
            sendOneway(object);
        }
    }

    public void sendList(List<Object> sendObjctList) {
        for (Object object : sendObjctList) {
            send(object);
        }

    }

    public void sendAsynList(List<Object> sendObjctList) {
        for (Object object : sendObjctList) {
            sendAsyn(object);
        }

    }

}
