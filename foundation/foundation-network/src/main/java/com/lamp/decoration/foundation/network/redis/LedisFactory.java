package com.lamp.decoration.foundation.network.redis;

import com.lamp.decoration.foundation.network.redis.annotation.OperationsEntity;
import com.lamp.decoration.foundation.network.redis.commands.CommandsObjectFactory;
import com.lamp.decoration.foundation.network.redis.commands.RedisCommands;
import com.lamp.decoration.foundation.network.redis.configuration.SingleConfiguration;
import com.lamp.decoration.foundation.network.redis.exception.ConfigureException;
import com.lamp.decoration.foundation.network.redis.net.ConnectionFactory;
import com.lamp.decoration.foundation.network.redis.net.NetAndPoolConfig;
import com.lamp.decoration.foundation.network.redis.net.NetConfigure;
import com.lamp.decoration.foundation.network.redis.net.PoolConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class LedisFactory {

	private SingleConfiguration  defaultSingleConfiguration;
	
	private Map<String , SingleConfiguration> singleConfigurationMap =  new HashMap<>();

	public SingleConfiguration getDefaultSingleConfiguration ( ) {
		return defaultSingleConfiguration ;
	}

	public void setDefaultSingleConfiguration ( SingleConfiguration singleConfiguration ) {
		this.defaultSingleConfiguration = singleConfiguration ;
	}
	
	public void addSingleConfiguration( SingleConfiguration singleConfiguration ) throws ConfigureException {
		String name = singleConfiguration.getName( );
		if( name == null && "".equals( name )){			
			throw new ConfigureException("非默认 singleConfiguration 必须要有 name，错误配置是：" +  singleConfiguration.toString( ));
		}
		if( this.singleConfigurationMap.containsKey( name )){
			throw new ConfigureException("已经存在 name 的 singleConfiguration ，错误配置是：" +  singleConfiguration.toString( ));
		}
		this.singleConfigurationMap.put( singleConfiguration.getName( )  , singleConfiguration );
	}
	
	public void addSingleConfiguration( Map<String , SingleConfiguration> singleConfigurationMap ) throws ConfigureException{
		Iterator<Entry<String, SingleConfiguration>> it = singleConfigurationMap.entrySet().iterator();
		Entry<String, SingleConfiguration> e ;
		SingleConfiguration singleConfiguration;
		while(it.hasNext()) {
			e = it.next();
			singleConfiguration = e.getValue();
			singleConfiguration.setName(e.getKey());
			addSingleConfiguration(singleConfiguration);
		}
	}
	
	public void init() throws ConfigureException{
		if( defaultSingleConfiguration == null && this.singleConfigurationMap.isEmpty( ) ){
			throw new ConfigureException("defaultSingleConfiguration 与  singleConfigurationMap 不能都为空");
		}
				
		if( defaultSingleConfiguration == null){
			throw new ConfigureException("defaultSingleConfiguration 不能为空");
		}
		NetAndPoolConfig netAndPoolConfig = getNetAndPoolConfig( defaultSingleConfiguration );
		if( !this.singleConfigurationMap.isEmpty( ) ){
			List<NetAndPoolConfig> list = new ArrayList<>(singleConfigurationMap.size( ) );
			Collection< SingleConfiguration > singleConfigurationCollection = singleConfigurationMap.values( );
			for(SingleConfiguration singleConfiguration  : singleConfigurationCollection){
				list.add( getNetAndPoolConfig( singleConfiguration ) );
			}
			ConnectionFactory.getInstance( ).init( netAndPoolConfig , list );
		}else{
			ConnectionFactory.getInstance( ).init( netAndPoolConfig.getNetConfigure( ) , netAndPoolConfig.getPoolConfig( ) );
		}
	}
	
	private NetAndPoolConfig getNetAndPoolConfig( SingleConfiguration singleConfiguration){
		NetConfigure netConfigure = new NetConfigure( );
		netConfigure.setHost( singleConfiguration.getHost( ) );
		if(singleConfiguration.getPost( ) != 0){
			
			netConfigure.setPost( singleConfiguration.getPost( ) );
		}
		PoolConfig poolConfig   = new PoolConfig( );
		poolConfig.setConnectionMode( singleConfiguration.getConnectionMode( ) );
		poolConfig.setNetMode( singleConfiguration.getNetMode( ) );
		poolConfig.setMaxTotal( singleConfiguration.getConnectionNum( ) );
		poolConfig.setSelect( singleConfiguration.getSelect( ) );
		
		NetAndPoolConfig netAndPoolConfig = new NetAndPoolConfig( );	
		netAndPoolConfig.setName( singleConfiguration.getName( ) );
		netAndPoolConfig.setNetConfigure( netConfigure );
		netAndPoolConfig.setPoolConfig( poolConfig );
		return netAndPoolConfig;
	}
	
	
	@SuppressWarnings ( "rawtypes" )
	public RedisCommands getRedisCommands( OperationsEntity operationsEntity ){
		return CommandsObjectFactory.getInstance( ).getRedisCommands( operationsEntity );
	}
	
	@SuppressWarnings ( "rawtypes" )
	public RedisCommands getRedisCommands( String name ){
		return CommandsObjectFactory.getInstance( ).getRedisCommands( name );
	}
	
}
 