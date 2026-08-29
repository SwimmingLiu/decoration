package com.lamp.decoration.foundation.network.redis.entity;

import java.util.AbstractList ;
import java.util.ArrayList;
import java.util.List;

public class SortedSetParameter  extends AbstractList< String > implements List<String>{


	private static final String withsocres_str = "withscores";
	
	private static final String limit_str = "limit";
	
	private static final String max_inf="-inf";
	
	private static final String min_inf="+inf";
	
	private static final String start = "0";
	
	private static final String stop = "-1";
	
	public static final SortedSetParameter create(){
		return new SortedSetParameter();
	}
	
	public static final SortedSetParameter createInf(){
		SortedSetParameter sortedSetParameter = new SortedSetParameter();
		sortedSetParameter.setMax( max_inf ).setMin( min_inf );
		return sortedSetParameter;
	}
	
	public static final SortedSetParameter createRange(){  
		SortedSetParameter sortedSetParameter = new SortedSetParameter();
		sortedSetParameter.setStart( start ).setStop( stop );
		return sortedSetParameter;
	}
	

	private String max;
	
	private String min;
	
	@SuppressWarnings( "unused" )
	private boolean withsocres = true;
	
	private Integer offset ;
	
	private Integer count ;


	public SortedSetParameter noWithsocres(){
		this.withsocres = true;
		return this;
	}
	
	


	public SortedSetParameter setMax( String max ) {
		this.max = max;
		return this;
	}


	public SortedSetParameter setMin( String min ) {
		this.min = min;
		return this;
	}
	
	public SortedSetParameter setStart( String start ) {
		this.max = start;
		return this;
	}


	public SortedSetParameter setStop ( String stop  ) {
		this.min = stop;
		return this;
	}
	



	public SortedSetParameter  setOffset( Integer offset ) {
		this.offset = offset;
		return this;
	}



	public SortedSetParameter setCount( Integer count ) {
		if(this.offset == null){
			throw new RuntimeException( "offset is not null" );
		}
		this.count = count;
		return this;
	}
	
	public List<String> Builder(){
		List<String> list = new ArrayList<>( );
		if( max== null && "".equals( max ) ){
			
		}
		if( min== null && "".equals( min ) ){
			
		}
		list.add( max );
		list.add( min );
		list.add( withsocres_str );
		if( count != null){
			list.add( limit_str );
			list.add( offset.toString( ) );
			list.add( count.toString( ) );
		}
		return list;
	}

	@Override
	public String get ( int index ) {
		switch (index) {
			case 0:
				return max;
			case 1:
				return min;
			case 2:
				return withsocres_str;
			case 3:
				return limit_str;			
			case 4:
				return offset.toString( );
			case 5:
				return count.toString( );
		}
		throw new RuntimeException( "offset is not null" );
	}

	@Override
	public int size ( ) {
		if( max== null && "".equals( max ) ){
			
		}
		if( min== null && "".equals( min ) ){
			
		}
		int size =3;
		if( count != null){
			size= size+3;
		}
		return size ;
	}
	
}
