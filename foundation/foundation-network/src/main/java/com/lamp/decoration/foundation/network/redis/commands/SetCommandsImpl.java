package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List ;

import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;
import com.lamp.decoration.foundation.network.redis.utils.DataConversionUtils;


public class SetCommandsImpl extends BasicsCommandsImpl< String > implements SetCommands , SetCommandsElement{

	public SetCommandsImpl(KeyCreate< String > keyCreate, String dataSource) {
		super( keyCreate , dataSource ) ;
	}

	@Override
	public long sadd ( String key , String member ) {
		return combination( SADD , DataConversionUtils.getDataConversionList( key , member ) ) ;
	}

	@Override
	public long sadd ( String key , List< String > member ) {
		return combinationString( SADD__MORE , DataConversionUtils.getDataConversionList( key ) , member) ;
	}

	@Override
	public long scard ( String key ) {
		return combination( SCARD , DataConversionUtils.getDataConversionList( key ) ) ;
	}

	@Override
	public List< String > sdiff ( List< String > key ) {
		return combinationString( SDIFF , DataConversion.getListDataConversion( ) , key) ;
	}

	@Override
	public long sdiffstore ( String destination , List< String > key ) {
		return combinationString( SDIFFSTORE , DataConversionUtils.getDataConversionList( destination ), key) ;
	}

	@Override
	public List< String > sinter ( List< String > key ) {
		return combinationString( SINTER , DataConversion.getListDataConversion( ) , key) ;
	}

	@Override
	public long sinterstore ( String destination , List< String > key ) {
		return combinationString( SINTERSTORE , DataConversionUtils.getDataConversionList( destination ), key) ;
	}

	@Override
	public boolean sismember ( String key , String member ) {
		return combination( SISMEMBER , DataConversionUtils.getDataConversionList( key , member ) ) ;
	}

	@Override
	public List< String > smemebers ( String key ) {
		return combination( SMEMBERS , DataConversionUtils.getDataConversionList( key  ) ) ;
	}

	@Override
	public boolean smove ( String source , String destination , String member ) {
		return combination( SMOVE , DataConversionUtils.getDataConversionList( source , destination , member ) ) ;
	}

	@Override
	public String spop ( String key ) {
		return combination( SPOP , DataConversionUtils.getDataConversionList( key  ) ) ;
	}

	@Override
	public String srandmember ( String key ) {
		List<String> strList = srandmember(  key , 1  );
		return strList.isEmpty( ) ? null: strList.get( 0 ) ;
	}

	@Override
	public List< String > srandmember ( String key , int count ) {
		return combination( SRANDMEMBER , DataConversionUtils.getDataConversionList( key , count  ) ) ;
	}

	@Override
	public boolean srem ( String key , String member ) {
		return combination( SREM , DataConversionUtils.getDataConversionList( key , member  ) ) ;
	}

	@Override
	public long srem ( String key , List< String > member ) {
		return combinationString( SREM_MORE , DataConversionUtils.getDataConversionList( key   ) , member) ;
	}

	@Override
	public List< String > sunion ( List< String > key ) {
		return combinationString( SUNION , DataConversion.getListDataConversion( ) , key) ;
	}

	@Override
	public long sunionstore ( String destination , List< String > key ) {
		return combinationString( SUNIONSTORE , DataConversionUtils.getDataConversionList( destination   ) , key) ;
	}
	
}
