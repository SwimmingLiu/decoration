package com.lamp.decoration.foundation.network.redis.cluster;

public class Slot {
	private int startSlot ;

	private int endSlot ;

	public Slot(int startSlot, int endSlot) {
		super( ) ;
		this.startSlot = startSlot ;
		this.endSlot = endSlot ;
	}

	public int getStartSlot ( ) {
		return startSlot ;
	}

	public void setStartSlot ( int startSlot ) {
		this.startSlot = startSlot ;
	}

	public int getEndSlot ( ) {
		return endSlot ;
	}

	public void setEndSlot ( int endSlot ) {
		this.endSlot = endSlot ;
	}

}