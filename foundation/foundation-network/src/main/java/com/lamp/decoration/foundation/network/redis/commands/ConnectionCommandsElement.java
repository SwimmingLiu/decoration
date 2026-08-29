package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.protocol.ResolveNetProtocol;

public interface ConnectionCommandsElement {

	
	public static final CombinationElement AUTH   = CombinationElement.create().setComman( "auth" ).setLength( 2 ).buildBoolean();
	
	public static final CombinationElement ECHO   = CombinationElement.create().setComman( "echo" ).setLength( 2 ).setResolveNetProtocol( ResolveNetProtocol.resolveStringNetProtocol ).build();
	
	public static final CombinationElement PING   = CombinationElement.create().setComman( "ping" ).setLength( 1 ).setResolveNetProtocol( ResolveNetProtocol.resolveStringNetProtocol ).build();
	
	public static final CombinationElement QUIT   = CombinationElement.create().setComman( "quit" ).setLength( 1 ).buildBoolean();
	
	public static final CombinationElement SELECT = CombinationElement.create().setComman( "select" ).setLength( 2 ).buildBoolean();
}
