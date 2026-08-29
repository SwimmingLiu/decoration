package com.lamp.decoration.foundation.network.redis.entity;

import java.util.AbstractList ;
import java.util.List;

public class ZunionstoreParameter extends AbstractList< String >{

	private static final String aggregate_str = "aggregate";
	
	private static final String weights_str = "weights";
	
	public static final String SUM = "sum";
	
	public static final String MIN = "min";
	
	public static final String MAX = "max";
	
	
	private String key;
	
	private String weights;
	
	private String aggregate;
	
	private List< String > weightsList;

	@Override
	public String get ( int index ) {
		// TODO 自动生成的方法存根
		return null ;
	}

	@Override
	public int size ( ) {
		// TODO 自动生成的方法存根
		return 0 ;
	}
}
