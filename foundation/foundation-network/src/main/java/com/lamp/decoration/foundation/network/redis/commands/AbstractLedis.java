
package com.lamp.decoration.foundation.network.redis.commands;

import java.io.IOException ;
import java.io.OutputStream ;
import java.nio.ByteBuffer ;
import java.util.LinkedList ;
import java.util.List ;

import com.lamp.decoration.foundation.network.redis.create.AmsTypeReference;
import com.lamp.decoration.foundation.network.redis.create.KeyConfigure;
import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.create.StringKeyCreate;
import com.lamp.decoration.foundation.network.redis.entity.AsynResult;
import com.lamp.decoration.foundation.network.redis.entity.DefaultAsynResult;
import com.lamp.decoration.foundation.network.redis.net.Connection;
import com.lamp.decoration.foundation.network.redis.net.ConnectionFactory;
import com.lamp.decoration.foundation.network.redis.net.ConnectionPattern;
import com.lamp.decoration.foundation.network.redis.net.netty.AgreementPretreatment;
import com.lamp.decoration.foundation.network.redis.pipi.PipiRedisCommands;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;
import com.lamp.decoration.foundation.network.redis.protocol.EecutionMode;


public abstract class AbstractLedis< T > {

	@SuppressWarnings("unchecked")
	protected final  static KeyCreate<String> STRING_KEYCREATE = new StringKeyCreate(new KeyConfigure<>( null, null, null, null, AmsTypeReference.stringAsmType));
	
	protected KeyCreate< T > keyCreate ;

	protected ConnectionPattern connectionPattern ;

	private static final ThreadLocal< PipiAsynResult >  PIPI_ASYN_RESULT = new ThreadLocal<>( );
 
	

	public AbstractLedis(KeyCreate< T > keyCreate, String dataSource) {
		super( ) ;
		this.keyCreate = keyCreate ;
		if ( dataSource == null ) {
			this.connectionPattern = ConnectionFactory.getInstance( ).getConnectionPattern( ) ;
		} else {
			this.connectionPattern = ConnectionFactory.getInstance( ).getConnectionPattern( dataSource ) ;
		}
	}
	
	
	
	@SuppressWarnings({ "unchecked", "hiding" })
	public   < T > T combination( CombinationElement ce , List <DataConversion> dataList ){
		if( getPipiAsynResult().getIsOpen( ) ){
			return ( T ) combinationWrite( ce , dataList );
		}else{
			return defaultCombination( ce , dataList );
		}
	} 
	
	@SuppressWarnings({ "unchecked", "hiding" })
	public   < T > T defaultCombination( CombinationElement ce , List < DataConversion > dataList ){
		Connection conn   = null;
		ByteBuffer buffer = null;
		try {
			conn = connectionPattern.getConnection( ) ;
			OutputStream out = conn.getOutputStream( ) ;
			ce.getAgreementPretreatment( ).perteatmentOut( out , 0 ) ;
			AgreementPretreatment.ListReferenceAgreementPretreatment( out , dataList , ce.getAgreementPretreatment( ).getLength( ) ) ;
			out.flush( ) ;
			buffer = DataConversion.getBuffer( ) ;
			Object t = ce.getResolveNetProtocol( ).analysis( conn.getInputStream( ) , buffer , this.keyCreate , null ) ;
			if ( t == null && buffer.position( ) == 0 ) {
				return ( T ) ce.getResultHandle( ).getNullOjbect( keyCreate ) ;
			}
			return ( T ) ( t == null ? ce.getResultHandle( ).handle( buffer , keyCreate ) : t ) ;
		} catch ( Exception e ) {
			return ( T ) ce.getResultHandle( ).getNullOjbect( keyCreate ) ;
		} finally {
			if ( buffer != null )
				buffer.clear( ) ;
			connectionPattern.setConnection( conn ) ;

		}
	}
	
	
	
	@SuppressWarnings({ "unchecked" })
	public   < V >V combination( CombinationElement ce,List<DataConversion> dataList ,List<T> objectList , KeyCreate<T> keyCreat){
		if( getPipiAsynResult().getIsOpen( ) ){
			return ( V ) combinationWrite( ce , dataList , objectList , keyCreat );
		}else{
			return defaultCombination( ce , dataList , objectList , keyCreat );
		}
	} 

	public final < V > V combination ( CombinationElement ce , List< DataConversion > dataList , List< T > objectList ) {
		return combination( ce , dataList , objectList , this.keyCreate ) ;
	}

	@SuppressWarnings( "unchecked" )
	public final < V > V combinationString ( CombinationElement ce , List< DataConversion > dataList , List< String > objectList ) {
		return combination( ce , dataList , ( List< T > ) objectList , this.keyCreate ) ;
	}

	@SuppressWarnings( { "unchecked" } )
	public final < V > V combinationLong ( CombinationElement ce , List< DataConversion > dataList , List< ? extends Number > objectList ) {
		return combination( ce , dataList , ( List< T > ) objectList , null ) ;
	}
	
	
	@SuppressWarnings("unchecked")
	public  < V >V  defaultCombination( CombinationElement ce,List<DataConversion> dataList ,List<T> objectList , KeyCreate<T> keyCreate ){
		Connection conn   = null;
		ByteBuffer buffer = null;
		try {
			if ( objectList.size( ) == 0 ) {
				keyCreate = ( KeyCreate< T > ) this.keyCreate ;
				return ( V ) ce.getResultHandle( ).getNullOjbect( keyCreate ) ;
			}
			conn = connectionPattern.getConnection( ) ;
			OutputStream out = conn.getOutputStream( ) ;
			EecutionMode ecutionMode = ce.getAgreementPretreatment( ).getExecutioMode( ) ;
			ce.getAgreementPretreatment( ).perteatmentOut( out ,ecutionMode.getMultiple( ) * objectList.size( ) + ecutionMode.getBase( ) ) ;
			if ( ecutionMode == EecutionMode.STRING_MGET || ecutionMode == EecutionMode.MAP_MGET ) {
				AgreementPretreatment.ListReferenceAgreementPretreatment( out , dataList , objectList , 1 ,keyCreate ) ;
			} else if ( ecutionMode == EecutionMode.STRING_MSET || ecutionMode == EecutionMode.MAP_MSET ) {
				AgreementPretreatment.HashReferenceAgreementPretreatment( out , dataList , objectList , keyCreate ) ;
			} else if ( ecutionMode == EecutionMode.MAP_MSET ) {
				int index = 0 ;

				for ( ;; ) {

					objectList.get( index++ ) ;
				}
			}
			out.flush( ) ;
			buffer = DataConversion.getBuffer( ) ;
			T t = ( T ) ce.getResolveNetProtocol( ).analysis( conn.getInputStream( ) , buffer , keyCreate , null ) ;
			keyCreate = ( KeyCreate< T > ) this.keyCreate ;
			if ( t == null && buffer.position( ) == 0 ) {
				return ( V ) ce.getResultHandle( ).getNullOjbect( keyCreate ) ;
			}
			return ( V ) ( t == null ? ce.getResultHandle( ).handle( buffer , keyCreate ) : t ) ;
		} catch ( Exception e ) {
			e.printStackTrace( ) ;
			return null ;
		} finally {
			if ( buffer != null )
				buffer.clear( ) ;
			connectionPattern.setConnection( conn ) ;

		}
	}

	@SuppressWarnings( { "unchecked" } )
	public final AsynResult< T > combinationWrite ( CombinationElement ce , List< DataConversion > dataList ) {
		Connection conn = null ;
		DefaultAsynResult< T , Object > defaultResult = new DefaultAsynResult< T , Object >( ce , keyCreate ,this.keyCreate ,conn ) ;
		AsynResult< T > result = ( AsynResult< T > ) defaultResult ;
		getPipiAsynResult( ).add( defaultResult );
		try {
			conn = connectionPattern.getConnection( ) ;
			OutputStream out = conn.getOutputStream( ) ;
			ce.getAgreementPretreatment( ).perteatmentOut( out , 0 ) ;
			AgreementPretreatment.ListReferenceAgreementPretreatment( out , dataList ,ce.getAgreementPretreatment( ).getLength( ) ) ;
			return result ;
		} catch ( Exception e ) {
			result.setException( e ) ;
			result.setIsException( Boolean.TRUE ) ;
			return result ;
		} 
	}

	@SuppressWarnings( { "unchecked" } )
	private < V > AsynResult< V > combinationWrite (CombinationElement ce , List< DataConversion > dataList , List< T > objectList , KeyCreate< T > keyCreate) {
		Connection conn = null ;
		DefaultAsynResult< T , V > result = new DefaultAsynResult<>( ce , keyCreate , this.keyCreate , conn ) ;
		getPipiAsynResult( ).add( result );
		try {
			if ( objectList.size( ) == 0 ) {
				keyCreate = ( KeyCreate< T > ) this.keyCreate ;
				result.setObject( ( V ) ce.getResultHandle( ).getNullOjbect( keyCreate ) ) ;
				return result ;
			}
			conn = connectionPattern.getConnection( ) ;
			OutputStream out = conn.getOutputStream( ) ;
			EecutionMode ecutionMode = ce.getAgreementPretreatment( ).getExecutioMode( ) ;
			ce.getAgreementPretreatment( ).perteatmentOut( out ,
					ecutionMode.getMultiple( ) * objectList.size( ) + ecutionMode.getBase( ) ) ;
			if ( ecutionMode == EecutionMode.STRING_MGET || ecutionMode == EecutionMode.MAP_MGET ) {
				AgreementPretreatment.ListReferenceAgreementPretreatment( out , dataList , objectList , 1 ,
						keyCreate ) ;
			} else if ( ecutionMode == EecutionMode.STRING_MSET || ecutionMode == EecutionMode.MAP_MSET ) {
				AgreementPretreatment.HashReferenceAgreementPretreatment( out , dataList , objectList , keyCreate ) ;
			}
			return result ;
		} catch ( Exception e ) {
			result.setException( e ) ;
			result.setIsException( Boolean.TRUE ) ;
			return null ;
		}
	}

	public < V > void combinationRead ( DefaultAsynResult< T , V > result ) {
		ByteBuffer buffer = null ;
		CombinationElement ce  = result.getCe( );
		V t ;
		try {
			buffer = DataConversion.getBuffer( ) ;
			t = ( V ) ce.getResolveNetProtocol( ).analysis( result.getCnn( ).getInputStream( ) , buffer , result.getKeyCreate( ) , null ) ;
			keyCreate = ( KeyCreate< T > ) this.keyCreate ;
			if ( t == null && buffer.position( ) == 0 ) {
				t = ( V ) ce.getResultHandle( ).getNullOjbect( keyCreate ) ;
			}
			t = ( t == null ? ( V ) ce.getResultHandle( ).handle( buffer , keyCreate ) : t ) ;
			result.setSet( Boolean.TRUE );
			result.setObject( t );
		} catch ( Exception e ) {
			result.setException( e ) ;
			result.setIsException( Boolean.TRUE ) ;
		}finally{
			buffer.clear( );
			try {
				result.getCnn( ).close( );
			} catch ( IOException e ) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	public PipiRedisCommands< Object ,T > pipoOpen() {
		getPipiAsynResult().setOpen( );
		return null ;
		
	}
	
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	public void pipoClose(){
		PipiAsynResult par = getPipiAsynResult();
		par.setClose( );
		List< DefaultAsynResult > arList = par.getDefaultAsynResult( );
		par.clear( );
		for(DefaultAsynResult ar : arList){
			combinationRead( ar );
		}
		
	}
	
	
	private static final PipiAsynResult  getPipiAsynResult(){
		PipiAsynResult par = PIPI_ASYN_RESULT.get( );
		if( par == null ){
			par = new PipiAsynResult();
		}
		return par;
	}
	
	static class PipiAsynResult{
		
		private boolean isOpen = false;
		
		@SuppressWarnings( "rawtypes" )
		private List< DefaultAsynResult >   defaultAsynResults =new LinkedList<>( );
		
		@SuppressWarnings( "rawtypes" )
		public List< DefaultAsynResult > getDefaultAsynResult(){
			return defaultAsynResults;
		}
		
		@SuppressWarnings( "rawtypes" )
		public void add(DefaultAsynResult defaultAsynResult){
			defaultAsynResults.add( defaultAsynResult );
		}
		
		public void clear(){
			defaultAsynResults.clear( );
		}
		
		public void setOpen(){
			isOpen = true;
		}
		
		public void setClose(){
			isOpen = false;
		}
		
		public boolean getIsOpen(){
			return isOpen;
		}
	}
}
