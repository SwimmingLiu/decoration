package com.lamp.decoration.foundation.network.redis.net;

public class NodeConnection {

	private Node node;
	

	private 
	
	
	Node getNode(int slot){
		Node node = this.node;
		for( ; ; ){
			if( node.slotExistenceNode( slot )){
				return node;
			}else{
				node = node.getNext( );
				if( node == null){
					
				}
			}
		}
	}
	
	
	static class Node{
		private Node next;
		
		private int nodeId;
		
		private int rangeStart;
		
		private int randeEnd;
		
		private ReadWriteConnection  connection;

		public Node(Node next, int rangeStart, int randeEnd, ReadWriteConnection connection) {
			super( ) ;
			this.next = next ;
			this.rangeStart = rangeStart ;
			this.randeEnd = randeEnd ;
			this.connection = connection ;
		}

		public Node getNext ( ) {
			return next ;
		}

		public int getRangeStart ( ) {
			return rangeStart ;
		}

		public int getRandeEnd ( ) {
			return randeEnd ;
		}

		public ReadWriteConnection getConnection ( ) {
			return connection ;
		}
		
		public boolean slotExistenceNode(int slot){
			return slot >= rangeStart && slot <= randeEnd;
		}
	}
}
