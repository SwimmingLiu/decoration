package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.protocol.EecutionMode;
import com.lamp.decoration.foundation.network.redis.protocol.ResolveNetProtocol;

public interface SetCommandsElement {

	public final static CombinationElement SADD       = CombinationElement.create( ).setComman( "sadd" ).setLength( 3 ).build( );
	
	public final static CombinationElement SADD__MORE = CombinationElement.create( ).setComman( "sadd" ).setExecutioMode( EecutionMode.MAP_MGET ).build( );
	
	public final static CombinationElement SCARD      = CombinationElement.create( ).setComman( "scard" ).setLength( 2 ).build( );
	
	/**
	 * 只有list
	 */
	public final static CombinationElement SDIFF      = CombinationElement.create( ).setComman( "sdiff" ).setExecutioMode( EecutionMode.STRING_MGET ).setResolveNetProtocol( ResolveNetProtocol.setNetProtocol ).build( );
	
	public final static CombinationElement SDIFFSTORE = CombinationElement.create( ).setComman( "sdiffstore" ).setExecutioMode( EecutionMode.MAP_MGET ).build( );
	
	public final static CombinationElement SINTER     = CombinationElement.create( ).setComman( "sinter" ).setExecutioMode( EecutionMode.STRING_MGET ).setResolveNetProtocol( ResolveNetProtocol.setNetProtocol ).build( );
	
	public final static CombinationElement SINTERSTORE = CombinationElement.create( ).setComman( "sinterstore" ).setExecutioMode( EecutionMode.MAP_MGET ).build( );
	
	public final static CombinationElement SISMEMBER   = CombinationElement.create( ).setComman( "sismember" ).setResolveNetProtocol( ResolveNetProtocol.resolveIntToBooleanNetProtocol ).setLength( 3 ).build( );
	
	public final static CombinationElement SMEMBERS    = CombinationElement.create( ).setComman( "smembers" ).setResolveNetProtocol( ResolveNetProtocol.setNetProtocol ).setLength( 2 ).build( );
	
	public final static CombinationElement SMOVE       = CombinationElement.create( ).setComman( "smove" ).setResolveNetProtocol( ResolveNetProtocol.resolveIntToBooleanNetProtocol ).setLength( 4 ).build( );
	
	public final static CombinationElement SPOP        = CombinationElement.create( ).setComman( "spop" ).setLength( 2 ).setResolveNetProtocol( ResolveNetProtocol.resolveStringNetProtocol ).build( );
	
	public final static CombinationElement SRANDMEMBER = CombinationElement.create( ).setComman( "srandmember" ).setResolveNetProtocol( ResolveNetProtocol.setNetProtocol ).setLength( 3 ).build( );
	
	public final static CombinationElement SREM        = CombinationElement.create( ).setComman( "srem" ).setResolveNetProtocol( ResolveNetProtocol.resolveIntToBooleanNetProtocol ).setLength( 3 ).build( );
	
	public final static CombinationElement SREM_MORE = CombinationElement.create( ).setComman( "srem" ).setExecutioMode( EecutionMode.MAP_MGET ).build( );
	
	public final static CombinationElement SUNION      = CombinationElement.create( ).setComman( "sunion" ).setExecutioMode( EecutionMode.STRING_MGET ).setResolveNetProtocol( ResolveNetProtocol.setNetProtocol ).build( );
	
	public final static CombinationElement SUNIONSTORE = CombinationElement.create( ).setComman( "sunionstore" ).setExecutioMode( EecutionMode.MAP_MGET ).build( );
	
}
