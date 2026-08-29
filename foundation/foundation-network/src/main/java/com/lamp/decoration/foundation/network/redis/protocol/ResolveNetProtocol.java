package com.lamp.decoration.foundation.network.redis.protocol;

import java.io.IOException ;
import java.nio.ByteBuffer ;
import java.nio.charset.Charset;
import java.util.ArrayList ;
import java.util.Collections ;
import java.util.List ;

import com.alibaba.fastjson2.JSON;
import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.create.Value;
import com.lamp.decoration.foundation.network.redis.entity.BlockListResult;
import com.lamp.decoration.foundation.network.redis.utils.LedisInputStream;


/**
 * 网络协议解析器， redis是一个比较简单的数据库，所以返回的数据，规范不是很明确，造成了这个解析器设计非常纠结
 * 
 * @author muqi
 *
 * @param <T>  未知
 */
public interface ResolveNetProtocol< T > {

	 ResolveNetProtocol< Boolean > resolveStateNetProtocol        = new ResolveStateNetProtocol( ) ;

	 ResolveNetProtocol< Long >    resolveIntNetProtocol          = new ResolveIntNetProtocol( ) ;

	 ResolveNetProtocol< Boolean > resolveIntToBooleanNetProtocol = new ResolveIntToBooleanNetProtocol( ) ;

	 ResolveNetProtocol< Long >    resolveSingleNetProtocol       = new ResolveSingleNetProtocol( ) ;

	 ResolveNetProtocol< Long >    resolveStringNetProtocol       = new ResolveSingleNetProtocol( ) ;

	 ResolveNetProtocol< Long >    resolveManyToListNetProtocol   = new ResolveManyToListNetProtocol( ) ;
	
	 ResolveNetProtocol< Long >    resolveManyToListStringNetProtocol = new ResolveManyToListStringNetProtocol();

	 ResolveNetProtocol< Long >    resolveManyToMapNetProtocol    = new ResolveManyToMapNetProtocol( ) ;

	 ResolveNetProtocol< Object >  listBlockNetProtocol           = new ListBlockNetProtocol( ) ;
	
	 ResolveNetProtocol< List<Object> > sortedSetNetProtocol      = new SortedSetNetProtocol();
	
	 ResolveNetProtocol< List<String> > setNetProtocol            = new SetNetProtocol( );

	public T analysis ( LedisInputStream in , ByteBuffer buffer ) throws IOException ;

	@SuppressWarnings( "rawtypes" )
	public T analysis ( LedisInputStream in , ByteBuffer buffer , KeyCreate keyCreate ,DynamicValue dynamicValue) throws IOException ;

	abstract class ResolveExceedinglyNetProtocol< T > implements ResolveNetProtocol< T > {

		@SuppressWarnings( "unused" )
		private static final String ASK_RESPONSE = "ASK" ;
		@SuppressWarnings( "unused" )
		private static final String MOVED_RESPONSE = "MOVED" ;
		@SuppressWarnings( "unused" )
		private static final String CLUSTERDOWN_RESPONSE = "CLUSTERDOWN" ;
		@SuppressWarnings( "unused" )
		private static final String BUSY_RESPONSE = "BUSY" ;
		@SuppressWarnings( "unused" )
		private static final String NOSCRIPT_RESPONSE = "NOSCRIPT" ;

		@SuppressWarnings( "rawtypes" )
		@Override
		public   T analysis ( LedisInputStream in , ByteBuffer buffer , KeyCreate keyCreate ,DynamicValue dynamicValue) throws IOException {
			return this.analysis(  in ,  buffer ) ;
		}

		int execcdingly ( LedisInputStream in , byte head ) throws IOException {
			int i = in.readHead( ) ;
			if ( i == '-' ) {
				analysis( in ) ;
			}
			return i ;
		}

		String getString ( ByteBuffer buffer ) {
			String str = new String( buffer.array( ) , 0 , buffer.position( ) ) ;
			buffer.clear( ) ;
			return str ;
		}

		public T analysis ( LedisInputStream in ) throws IOException {
			ByteBuffer buffer = ByteBuffer.allocate( 8148 ) ;
			in.readLindBytes( buffer ) ;
			String str = new String( buffer.array( ) , 0 , buffer.position( ) ) ;
			buffer.clear( ) ;
			throw new RuntimeException( str ) ;
		}
	}

	public static class ResolveStateNetProtocol extends ResolveExceedinglyNetProtocol< Boolean > {
		private final static byte head = '+' ;

		@Override
		public Boolean analysis ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			execcdingly( in , head ) ;
			return in.readOKCrLf( ) ;
		}
	}

	public static class ResolveIntNetProtocol extends ResolveExceedinglyNetProtocol< Long > {
		private static final byte head = ':' ;

		@Override
		public Long analysis ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			execcdingly( in , head ) ;
			return in.readLongCrLf( ) ;
		}

	}

	public static class ResolveIntToBooleanNetProtocol extends ResolveExceedinglyNetProtocol< Boolean > {
		private final static byte head = ':' ;

		@Override
		public Boolean analysis ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			execcdingly( in , head ) ;
			return in.readLongCrLf( ) == 1 ;
		}
	}

	abstract class ResolveManyNetProtocol extends ResolveExceedinglyNetProtocol< Long > {
		private final static byte head = '*' ;

		int base = 1 ;

		byte divisionSymblo ( ) {
			return ',' ;
		}

		byte stringDistinguish ( ) {
			return '"' ;
		}

		abstract byte startSymbol ( ) ;

		abstract byte endSymbol ( ) ;

		void getData ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			if ( in.readHead( ) == ':' ) {
				buffer.putLong( in.readLongCrLf( ) ) ;
			} else {
				// buffer.put( stringDistinguish());
				in.readLind( buffer ) ;
				// buffer.put( stringDistinguish());
			}
		}

		/**
		 * 数组 [1，2] map {1：2 , 3:3} 开始与结束符 ， 分隔符 map 的分隔符， 字符串双引号
		 * 
		 * @throws IOException io异常
		 */
		@Override
		public Long analysis ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			execcdingly( in , head ) ;
			long i = in.readLongCrLf( ) ;
			if ( i == 0 ) {
                return null ;
            }
			buffer.put( startSymbol( ) ) ;
			for ( ;; ) {
				supplement( in , buffer ) ;
				i = i - base ;
				if ( i > 0 ) {
					buffer.put( divisionSymblo( ) ) ;
				} else {
					buffer.put( endSymbol( ) ) ;
					return null ;
				}
			}
		}

		abstract void supplement ( LedisInputStream in , ByteBuffer buffer ) throws IOException ;

	}
	

	public static class ResolveManyToListNetProtocol extends ResolveManyNetProtocol {

		@Override
		byte startSymbol ( ) {
			return '[' ;
		}

		@Override
		byte endSymbol ( ) {
			return ']' ;
		}

		@Override
		void supplement ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			getData( in , buffer ) ;
		}
	}
	
	public static class ResolveManyToListStringNetProtocol extends ResolveManyToListNetProtocol{
		@Override
		void supplement ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			buffer.put( stringDistinguish());
			getData( in , buffer ) ;
			buffer.put( stringDistinguish());
		}
	}
	

	public static class ResolveManyToMapNetProtocol extends ResolveManyNetProtocol {

		private static final byte semicolon = ':' ;

		ResolveManyToMapNetProtocol() {
			base = 2 ;
		}

		@Override
		byte startSymbol ( ) {
			return '{' ;
		}

		@Override
		byte endSymbol ( ) {
			return '}' ;
		}

		@Override
		void supplement ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			getData( in , buffer ) ;
			buffer.put( semicolon ) ;
			getData( in , buffer ) ;
		}
	}

	public class ResolveSingleNetProtocol extends ResolveExceedinglyNetProtocol< Long > {
		private static final byte head = '$' ;

		boolean getByte ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			execcdingly( in , head ) ;
			return in.readLind( buffer ) ;
		}

		@Override
		public Long analysis ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			if ( execcdingly( in , head ) == ':' ) {
				return in.readLongCrLf( ) ;
			} else {
				in.readLind( buffer ) ;
				return null ;
			}
		}
	}

	public class ListBlockNetProtocol extends ResolveExceedinglyNetProtocol< Object > {

		@SuppressWarnings( { "rawtypes", "unchecked" } )
		@Override
		public Object analysis ( LedisInputStream in , ByteBuffer buffer ,KeyCreate keyCreate , DynamicValue dynamicValue) throws IOException {
			long i = in.readLongCrLf( ) ;
			in.readLindString(buffer);
			in.readLind( buffer );
			BlockListResult< Object > bock = null;
			if ( i == 2 ) {
				bock = new BlockListResult< Object >( in.readLindString(buffer) , 
													 JSON.parseObject(buffer.array(), 0, buffer.position(), Charset.defaultCharset(), keyCreate.getEntityClass()) ,
													 0  );
			}else{
				bock = new BlockListResult< Object >( in.readLindString(buffer) , 
						 JSON.parseObject(buffer.array(), 0, buffer.position(), Charset.defaultCharset(), keyCreate.getEntityClass()) ,
						 Double.parseDouble( in.readLindString(buffer) )  );
			}
			
			return bock ;
		}
		
		public Object getData ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			if ( in.readHead( ) == ':' ) {
				return in.readLongCrLf( ) ;
			} else {
				in.readLind( buffer ) ;
				return null ;
			}
		}

		@Override
		public Long analysis ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			// TODO 自动生成的方法存根
			return null ;
		}

	}

	public class SortedSetNetProtocol extends ResolveExceedinglyNetProtocol< List< Object > > {

		@SuppressWarnings( { "rawtypes", "unchecked" } )
		@Override
		public List< Object > analysis ( LedisInputStream in , ByteBuffer buffer , KeyCreate keyCreate , DynamicValue dynamicValue) throws IOException {
			execcdingly( in , ( byte ) ':' ) ;
			long i = in.readLongCrLf( ) / 2 ;
			if( i == 0){
				return Collections.EMPTY_LIST;
			}
			List< Object > list = new ArrayList<>( ( int ) i ) ;
			Value v = keyCreate.getValue( ) ;
			Object id , value;
			for ( ;; ) {
				getData( in , buffer );
				id = v.getKey( buffer );
				buffer.clear( );
				getData( in , buffer );
				value = v.getValue( buffer );
				buffer.clear( );
				list.add( v.setValue( id , value ) ) ;
				if ( -- i == 0 ) {
					return list ;
				}
			}
		}

		public Object getData ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			if ( in.readHead( ) == ':' ) {
				return in.readLongCrLf( ) ;
			} else {
				in.readLind( buffer ) ;
				return null ;
			}
		}

		@Override
		public List< Object > analysis ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			// TODO 自动生成的方法存根
			return null ;
		}
	}
	/**
	 * 这个只适合set集合
	 * 
	 * @author laohu
	 *
	 */
	public class SetNetProtocol extends ResolveExceedinglyNetProtocol< List<String> >{

		@SuppressWarnings( "unchecked" )
		@Override
		public List< String > analysis ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			execcdingly( in , ( byte ) ':' ) ;
			long i = in.readLongCrLf( ) ;
			if( i == 0){
				return Collections.EMPTY_LIST;
			}
			List< String > list = new ArrayList<>( ( int ) i ) ;
			int j = 0;
			for( ; ;){
				getData( in , buffer );
				list.add(  new String( buffer.array( ) , 0 , buffer.position( ) ) );
				buffer.clear( );
				if( ++j == i){
					return list ;
				}	
			}
		}
		
		void getData ( LedisInputStream in , ByteBuffer buffer ) throws IOException {
			if ( in.readHead( ) == ':' ) {
				buffer.putLong( in.readLongCrLf( ) ) ;
			} else {
				in.readLind( buffer ) ;
			}
		}
	}
}
