package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.protocol.EecutionMode;

public interface BasicsCommandsElement {

	static final CombinationElement DEL        = CombinationElement.create().setComman( "del"      ).setLength( 2 ).buildBoolean();
	
	static final CombinationElement DEL_MORE   = CombinationElement.create().setComman( "del"      ).setExecutioMode( EecutionMode.GET_VALUE ).build();
	
	static final CombinationElement EXISTS     = CombinationElement.create().setComman( "exists"   ).setLength( 2 ).buildBoolean();
	
	static final CombinationElement EXPIRE     = CombinationElement.create().setComman( "expire"   ).setLength( 3 ).buildBoolean();
	
	static final CombinationElement EXPIREAT   = CombinationElement.create().setComman( "expireat" ).setLength( 3 ).buildBoolean();
	
	static final CombinationElement PEXPIRE    = CombinationElement.create().setComman( "pexpire"  ).setLength( 3 ).buildBoolean();
	
	static final CombinationElement PEXPIREAT  = CombinationElement.create().setComman( "pexpireat").setLength( 3 ).buildBoolean();
	
	static final CombinationElement PERSIST    = CombinationElement.create().setComman( "persist"  ).setLength( 2 ).buildBoolean();
	
	static final CombinationElement RENAME     = CombinationElement.create().setComman( "rename"   ).setLength( 3 ).buildBoolean();
	
	static final CombinationElement RENAMENX   = CombinationElement.create().setComman( "renameNX" ).setLength( 3 ).buildBoolean();
	
}
