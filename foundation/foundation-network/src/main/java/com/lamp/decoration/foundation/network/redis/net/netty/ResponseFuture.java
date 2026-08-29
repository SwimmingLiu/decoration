package com.lamp.decoration.foundation.network.redis.net.netty;

import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.lamp.decoration.foundation.network.redis.commands.CombinationElement;
import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;


public class ResponseFuture {
    private Channel processChannel;
    private final InvokeCallback invokeCallback;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private Object object;
    
    private CombinationElement ce ;
    
    private List <DataConversion> dataList ;
    
    private KeyCreate< ? > keyCreate;
    
    private List<Object> objectList;
    
    private KeyCreate<Object> codeKeyCreate;

    private final AtomicBoolean executeCallbackOnlyOnce = new AtomicBoolean(false);
    private volatile Throwable cause;
    
    
    public ResponseFuture(Channel channel,InvokeCallback invokeCallback ,  CombinationElement ce , List < DataConversion > dataList, KeyCreate< ? > keyCreate , List<Object> objectList , KeyCreate<Object> codeKeyCreate ) {
    	this(channel, invokeCallback, ce, dataList, codeKeyCreate);
    	this.objectList = objectList;
    	this.codeKeyCreate = codeKeyCreate;
    }
    
    public ResponseFuture(Channel channel,InvokeCallback invokeCallback ,  CombinationElement ce , List < DataConversion > dataList, KeyCreate< ? > keyCreate ) {
    	this.ce = ce;
    	this.keyCreate = keyCreate;
    	this.dataList = dataList;
        this.processChannel = channel;
        this.invokeCallback = invokeCallback;
    }

    public void executeInvokeCallback() {
        if (invokeCallback != null) {
            if (this.executeCallbackOnlyOnce.compareAndSet(false, true)) {
                invokeCallback.operationComplete(this);
            }
        }
    }
    
    public Object waitResponse(final long timeoutMillis) throws InterruptedException {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.object;
    }

    public void putResponse(final Object object) {
        this.object = object;
        this.countDownLatch.countDown();
    }

    public InvokeCallback getInvokeCallback() {
        return invokeCallback;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public Channel getProcessChannel() {
        return processChannel;
    }

    public void setProcessChannel(Channel processChannel) {
        this.processChannel = processChannel;
    }

	public CombinationElement getCombinationElement() {
		return ce;
	}

	public void setCombinationElement(CombinationElement ce) {
		this.ce = ce;
	}

	public List<DataConversion> getDataList() {
		return dataList;
	}

	public void setDataList(List<DataConversion> dataList) {
		this.dataList = dataList;
	}

	public KeyCreate<?> getKeyCreate() {
		return keyCreate;
	}

	public void setKeyCreate(KeyCreate<?> keyCreate) {
		this.keyCreate = keyCreate;
	}

	public List<Object> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<Object> objectList) {
		this.objectList = objectList;
	}

	public KeyCreate<Object> getCodeKeyCreate() {
		return codeKeyCreate;
	}

	public void setCodeKeyCreate(KeyCreate<Object> codeKeyCreate) {
		this.codeKeyCreate = codeKeyCreate;
	}
}