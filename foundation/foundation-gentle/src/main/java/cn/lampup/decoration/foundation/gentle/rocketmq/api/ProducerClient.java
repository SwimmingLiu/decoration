package cn.lampup.decoration.foundation.gentle.rocketmq.api;


import cn.lampup.decoration.foundation.gentle.rocketmq.SendConfig;

public interface ProducerClient {
    

    public void sendOneway(Object sendObjct , SendConfig sendConfig) throws Exception ;

    public void send(Object sendObjct,  SendConfig sendConfig) throws Exception;
    
    public void sendAsyn(Object sendObjct, SendConfig sendConfig);
    
}
