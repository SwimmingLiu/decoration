package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.protocol.ResolveNetProtocol;
import com.lamp.decoration.foundation.network.redis.protocol.ResultHandle;

public interface PubSubCommandsElement {

	/**
	 * 订阅一个或多个符合给定模式的频道
	 */
	static final CombinationElement PSUBSCRIBE  = CombinationElement.create( ).setComman( "psubscribe" ).setLength( 3 ).setResolveNetProtocol( ResolveNetProtocol.resolveStringNetProtocol ).build( );

	/**
	 * 将信息 message 发送到指定的频道 channel
	 */
	static final CombinationElement PUBLISH = CombinationElement.create().setComman("publish").setLength(3).setResolveNetProtocol(ResolveNetProtocol.resolveIntNetProtocol).build();

	/**
	 * 一个查看订阅与发布系统状态的内省命令， 它由数个不同格式的子命令组成
	 */
	static final CombinationElement PUBSUB = CombinationElement.create().setComman("pubsub").setLength(4)
											.setResolveNetProtocol(ResolveNetProtocol.resolveStringNetProtocol).setResultHandle(ResultHandle.typeReferenceListString).build();
	
	/**
	 * 退订所有给定模式
	 */
	static final CombinationElement PUBSUBSCRIBE = CombinationElement.create().setComman("pubsubscribe").setLength(2).setResolveNetProtocol(ResolveNetProtocol.resolveIntToBooleanNetProtocol).build();

	/**
	 * 订阅给定的一个或多个频道的信息
	 */
	static final CombinationElement SUBSCRIBE = CombinationElement.create().setComman("subscribe").setLength(2).setResolveNetProtocol(ResolveNetProtocol.resolveStringNetProtocol).build();

	/**
	 * 退订给定的频道
	 */
	static final CombinationElement UNSUBSCRIBE = CombinationElement.create().setComman("unsubscribe").setLength(2).setResolveNetProtocol(ResolveNetProtocol.resolveIntNetProtocol).build();

}
