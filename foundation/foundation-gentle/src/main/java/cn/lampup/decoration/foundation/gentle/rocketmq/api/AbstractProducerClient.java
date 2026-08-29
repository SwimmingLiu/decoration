package cn.lampup.decoration.foundation.gentle.rocketmq.api;

import cn.lampup.decoration.foundation.gentle.rocketmq.SendConfig;

import java.lang.reflect.InvocationTargetException;


public abstract class AbstractProducerClient  implements ProducerClient{

    abstract Object createMessage(Object sendObjct , SendConfig sendConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
