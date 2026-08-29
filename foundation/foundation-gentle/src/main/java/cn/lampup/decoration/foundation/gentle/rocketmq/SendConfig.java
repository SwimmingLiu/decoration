package cn.lampup.decoration.foundation.gentle.rocketmq;

import java.lang.reflect.Method;

public class SendConfig {
    
    private String name;
    
    private String topic;
    
    private Method key;
    
    private Method tag;
    
    private long sendMsgTimeout = 3000;
    
    private Serialize serialize; 
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Method getKey() {
        return key;
    }

    public void setKey(Method key) {
        this.key = key;
    }

    public Method getTag() {
        return tag;
    }

    public void setTag(Method tag) {
        this.tag = tag;
    }

    public long getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(long sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    public Serialize getSerialize() {
        return serialize;
    }

    public void setSerialize(Serialize serialize) {
        this.serialize = serialize;
    }
    
    

}
