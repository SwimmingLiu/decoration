package com.lamp.decoration.foundation.network.redis.cluster;

import java.util.ArrayList ;
import java.util.List ;

public class ClusterNodeInfo {

	private static final String  FLAGS = "myself";
	
	private static final String  MASTER = "master";
	
	private static final String  SLAVE = "slave";
	
	private String id;
	
	private String masterId;
	
	private String ip;
	
	private int port;
	
	private String flags;
	
	private String master;
	
	private long pingSent;
	
	private long pongRecv;
	
	private int configEpoch;
	
	private String linkState;
	
	private List<Slot> slotList = new ArrayList<>( );
	
	public ClusterNodeInfo(String data){
		info( data );
	}
	
	private void info(String data){
		int index= 0;
		String[] strArray = data.split( " " );
		this.id = strArray[ index++ ];
		String[] ipAndPort = strArray[1].split( ":" );
		this.ip = ipAndPort[ index++ ];
		this.port = Integer.valueOf( ipAndPort[ 1 ] );
		String flags = ipAndPort[2];
		if( FLAGS.equals( flags ) ){
			this.flags = flags;
			this.master = strArray[ index++ ];
		}else{
			this.master = flags;
		}
		
		
		this.masterId = strArray[ index++];
		this.pingSent = Long.valueOf(  strArray[ index++] );
		this.pongRecv = Long.valueOf(  strArray[ index++] );
		this.configEpoch = Integer.valueOf( strArray[ index++] );
		this.linkState = strArray[index++];
		
		if( index < strArray.length ){
			String[] slot;
			for( ; index <= strArray.length ; index++){
				slot = strArray[ index ].split( "-" );
				this.slotList.add( new Slot(Integer.valueOf( slot[0] ), Integer.valueOf( slot[1] )));
			}
		}
		
	}
	
	
}
