package com.lamp.decoration.foundation.network.redis.net;

import org.junit.Before;
import org.junit.Test;

import com.lamp.decoration.foundation.network.redis.annotation.OperationEntity;
import com.lamp.decoration.foundation.network.redis.annotation.OperationsEntity;
import com.lamp.decoration.foundation.network.redis.commands.BasicsCommands;
import com.lamp.decoration.foundation.network.redis.commands.BasicsCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.ConnectionCommands;
import com.lamp.decoration.foundation.network.redis.commands.ConnectionCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.HashCommands;
import com.lamp.decoration.foundation.network.redis.commands.HashCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.ListCommands;
import com.lamp.decoration.foundation.network.redis.commands.ListCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.PubSubCommands;
import com.lamp.decoration.foundation.network.redis.commands.PubSubCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.RedisCommands;
import com.lamp.decoration.foundation.network.redis.commands.RedisCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.ScriptCommands;
import com.lamp.decoration.foundation.network.redis.commands.ScriptCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.ServerCommands;
import com.lamp.decoration.foundation.network.redis.commands.ServerCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.SetCommands;
import com.lamp.decoration.foundation.network.redis.commands.SetCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.SortedSetCommands;
import com.lamp.decoration.foundation.network.redis.commands.SortedSetCommandsImpl;
import com.lamp.decoration.foundation.network.redis.commands.StringCommands;
import com.lamp.decoration.foundation.network.redis.commands.StringCommandsImpl;
import com.lamp.decoration.foundation.network.redis.entity.TestEntity;
import com.lamp.decoration.foundation.network.redis.utils.KeyCreateUtils;


public class ConnectionFactoryTest {

	protected StringCommands< TestEntity > sc;

	protected ListCommands<TestEntity> lc;

	protected HashCommands< Integer , TestEntity > hc;
	
	protected SetCommands setc;

	protected SortedSetCommands< TestEntity > ssc;
	
	protected ConnectionCommands cc;
	
	protected BasicsCommands< String > bc;
	
	protected RedisCommands<Integer, TestEntity> rc;
	
	protected ScriptCommands sco;
	
	protected PubSubCommands psc;
	
	protected ServerCommands serverc;
	
	@SuppressWarnings ( "unchecked" )
	@Before
	public void bo( ) {
		try {

			OperationEntity oe = new OperationEntity( );
			oe.setClazz( TestEntity.class );
			oe.setKey( "id" );
			oe.setMapKey( "appId" );
			oe.setSeparator( "_" );
			oe.setPrefix( "user_" );
			sc = new StringCommandsImpl< TestEntity >(  KeyCreateUtils.getInstance( ).createKeyCreate( oe )  , null);
			hc = new HashCommandsImpl< Integer , TestEntity >(KeyCreateUtils.getInstance( ).createKeyCreate( oe ) , null );
			lc = new ListCommandsImpl< TestEntity >(KeyCreateUtils.getInstance( ).createKeyCreate( "com.lamp.ledis.entity.TestEntity" , "id" , null , null , null )  , null);
			
			setc = new SetCommandsImpl(KeyCreateUtils.getInstance( ).createKeyCreate( oe ) , null );
			oe.setMapKey( "name" );
			oe.setValue( "wages" );
			ssc = new SortedSetCommandsImpl<>(KeyCreateUtils.getInstance( ).createKeyCreate( oe ) , null );
			cc = new ConnectionCommandsImpl( null , null );
			bc = new BasicsCommandsImpl< String >( null , null  );
			sco = new ScriptCommandsImpl(null, null);
			psc = new PubSubCommandsImpl(null,null);
			serverc = new ServerCommandsImpl(null, null);
			if( true ){
				OperationsEntity ose = new OperationsEntity();
				ose.setSortedSet(oe);
				oe.setValue( null );
				ose.setHash(oe);
				oe.setMapKey( null );
				ose.setList(oe);
				ose.setString( oe );
				ose.setSet( oe );
				RedisCommands< Integer , TestEntity > rc   = new RedisCommandsImpl<Integer , TestEntity>(ose) ;
				sc = rc;
				hc = rc;
				lc = rc;
				setc = rc;
				ssc  = rc;
			}
			System.out.println( sc.getClass().getName() );
			System.out.println( " commands init succer" );

		} catch ( Exception e ) {
			// TODO 自动生成的 catch 块
			e.printStackTrace( );
		}
	}

	@Before
	public void info( ) {
		ConnectionFactory.getInstance( ).init( );
	}

	@Test
	public void connectionTest( ) {

	}

}
