package com.lamp.decoration.foundation.network.redis.utils;

import java.util.List ;

import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;


/**
 * DataConversion 与 DataConversionUtils 一切为了性能。不是为了性能，这些设计真的不需要。
 * 因为DataConversion 的存在，基本没有byte[] 的创建，
 * DataConversionUtils出现减少了循环操作，和一些操作的友善
 * 这个类，太乱了。稳定之后，第一时间重构类之一。
 * 实在没办法
 * @author laohu
 *
 */
public final class DataConversionUtils {

	public static final <T>List<DataConversion> getKeyCreateAndValueCreate(Object object , KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object  , keyCreate  );
		dataList.get( 1 ) .setObjectAndKeyCreate( object  , keyCreate.getKeyCreate( ).getKeyCreateValue( ));
		dataList.get( 2 ) .setObjectAndKeyCreate( object   , keyCreate.getKeyCreate( ));
		return dataList;
	}
	
	
	public static final<T>List<DataConversion> getKeyStringValueList(Object object ,List<String> list , KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object  , keyCreate  );
		int i = list.size(),j=0;
		for(  ; ; ){
			dataList.get( j+1 ) .setObjectAndKeyCreate( list.get( j++ )  );
			if( i == j){
				return dataList;
			}
		}
	}

	public static final <T>List<DataConversion> getDataConversionListKey(Object object , Object value , Object valueTwo ,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object , keyCreate  );
		dataList.get( 1 ) .setObjectAndKeyCreate( value  );
		dataList.get( 2 ) .setObjectAndKeyCreate( valueTwo  );
		return dataList;	
	}
	
	public static final <T>List<DataConversion> getDataConversionListKey(Object object , Object value , KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object , keyCreate  );
		dataList.get( 1 ) .setObjectAndKeyCreate( value  );
		return dataList;	
	}
	
	public static final <T>List<DataConversion> getDataConversionListKey(Object object ,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object , keyCreate  );
		return dataList;	
	}
	
	public static final <T>List<DataConversion> getKeyToMapKey(Object object ,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object , keyCreate  );
		dataList.get( 1 ) .setObjectAndKeyCreate( object , keyCreate.getKeyCreate( )  );
		return dataList;	
	}
	
	
	public static final <T>List<DataConversion> getDataConversionListIsKey(Object object ,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object , keyCreate  );
		dataList.get( 1 ) .setObjectAndKeyCreate( object  );
		return dataList;	
		
	}
	
	public static final <T>List<DataConversion> getDataConversionListIsKey(Object object , Object value ,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object , keyCreate  );
		dataList.get( 1 ) .setObjectAndKeyCreate( object  );
		dataList.get( 2 ) .setObjectAndKeyCreate( value  );
		return dataList;	
		
	}
	
	public static final <T>List<DataConversion> getDataConversionListIsKey(Object object , Object value , Object valueTwo ,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object , keyCreate  );
		dataList.get( 1 ) .setObjectAndKeyCreate( object  );
		dataList.get( 2 ) .setObjectAndKeyCreate( value  );
		dataList.get( 3 ) .setObjectAndKeyCreate( valueTwo  );
		return dataList;	
		
	}
	
	//fengjiahui 20170702  server不需要key
	public static final List<DataConversion> getDataConversionList(){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		return dataList;	
	}
	
	public static final List<DataConversion> getDataConversionList(Object key){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( key  );
		return dataList;	
	}
	
	public static final List<DataConversion> getDataConversionList(Object key , Object field){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( key  );
		dataList.get( 1 ) .setObjectAndKeyCreate( field  );
		return dataList;	
	}
	
	public static final List<DataConversion> getDataConversionList(Object key , Object field , Object fileds){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( key  );
		dataList.get( 1 ) .setObjectAndKeyCreate( field  );
		dataList.get( 2 ) .setObjectAndKeyCreate( fileds  );
		return dataList;	
	}
	
	public static final List<DataConversion> getDataConversionList(Object key , Object field , Object fileds , Object value){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( key  );
		dataList.get( 1 ) .setObjectAndKeyCreate( field  );
		dataList.get( 2 ) .setObjectAndKeyCreate( fileds  );
		dataList.get( 3 ) .setObjectAndKeyCreate( value  );
		return dataList;	
	}
	
	public static final <T>List<DataConversion> getHashObjectToDataConversion( T t , KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( t , keyCreate  );
		dataList.get( 1 ) .setObjectAndKeyCreate( t , keyCreate.getKeyCreate( )  );
		dataList.get( 2 ) .setObjectAndKeyCreate( t );
		return dataList;	
	}
	
	public static final <T>List<DataConversion> getHashToDataConversion( Object object , T t ,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( object   );
		dataList.get( 1 ) .setObjectAndKeyCreate( t , keyCreate.getKeyCreate( )  );
		return dataList;	
	}
	
	public static final <T>List<DataConversion> keyIsKeyCreate(Object key ,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( key , keyCreate);
		return dataList;	
	}
	
	public static final <T>List<DataConversion> keyIsKeyCreate(Object key , Object field,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( key  , keyCreate);
		dataList.get( 1 ) .setObjectAndKeyCreate( field  );
		return dataList;	
	}
	
	public static final <T>List<DataConversion> keyIsKeyCreate(Object key , Object field , Object fileds,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( key  , keyCreate);
		dataList.get( 1 ) .setObjectAndKeyCreate( field  );
		dataList.get( 2 ) .setObjectAndKeyCreate( fileds  );
		return dataList;	
	}
	
	public static final <T>List<DataConversion> keyIsKeyCreate(Object key , Object field , Object fileds , Object value,KeyCreate< T > keyCreate){
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( key  , keyCreate);
		dataList.get( 1 ) .setObjectAndKeyCreate( field  );
		dataList.get( 2 ) .setObjectAndKeyCreate( fileds  );
		dataList.get( 3 ) .setObjectAndKeyCreate( value  );
		return dataList;	
	}
}
