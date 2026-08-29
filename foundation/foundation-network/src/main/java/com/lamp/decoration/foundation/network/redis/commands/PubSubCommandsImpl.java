package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.utils.DataConversionUtils;

public class PubSubCommandsImpl<T> extends AbstractLedis<T> implements PubSubCommands,PubSubCommandsElement{

	
	public PubSubCommandsImpl(KeyCreate<T> keyCreate, String dataSource) {
		super(keyCreate, dataSource);
	}

	@Override
	public String psubscribe(String pattern) {
		return combination(PSUBSCRIBE, DataConversionUtils.getDataConversionList(pattern));
	}

	@Override
	public long publish(String pattern,String message) {
		return combination(PUBLISH,DataConversionUtils.getDataConversionList(pattern, message));
	}

	@Override
	public String pubsub(String channel) {
		return combination(PUBSUB, DataConversionUtils.getDataConversionList(channel));
	}
	
	@Override
	public String pubsub(String channel, String message) {
		return combination(PUBSUB, DataConversionUtils.getDataConversionList(channel, message));
	}

	@Override
	public String pubsubscribe(String pattern) {

		return combination(PUBSUBSCRIBE ,DataConversionUtils.getDataConversionList(pattern));
	}

	@Override
	public String subscribe(String pattern) {
		return combination(SUBSCRIBE, DataConversionUtils.getDataConversionList(pattern));
		
	}

	@Override
	public String unsubscribe(String pattern) {
		return combination(UNSUBSCRIBE,DataConversionUtils.getDataConversionList(pattern));
	}
						   

}
