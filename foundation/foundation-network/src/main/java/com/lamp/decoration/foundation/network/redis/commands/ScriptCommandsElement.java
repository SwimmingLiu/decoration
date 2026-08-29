package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.protocol.ResolveNetProtocol;

/**
 * 只有两个命令，script load，evalsha、exists
 * 直接读取xml文件，
 * @author laohu
 *
 */
public interface ScriptCommandsElement {

	/**
	 * 将脚本 script 添加到脚本缓存中，但并不立即执行这个脚本
	 */                           																															  
	static final CombinationElement SCRIPT_LOAD = CombinationElement.create( ).setComman( "script" ).setLength( 3 ).setResolveNetProtocol( ResolveNetProtocol.resolveStringNetProtocol ).build( );
	
	/**
	 * 根据给定的 sha1 校验码，对缓存在服务器中的脚本进行求值
	 */
	static final CombinationElement EVALSHA = CombinationElement.create( ).setComman( "evalsha" ).setLength( 2 ).setResolveNetProtocol( ResolveNetProtocol.resolveStringNetProtocol ).build( );
	
	/**
	 * 校验和所指定的脚本是否已经被保存在缓存当中
	 */
	static final CombinationElement SCRIPT_EXISTS = CombinationElement.create().setComman("script").setLength(3).setResolveNetProtocol( ResolveNetProtocol.resolveIntToBooleanNetProtocol ).build( );
}