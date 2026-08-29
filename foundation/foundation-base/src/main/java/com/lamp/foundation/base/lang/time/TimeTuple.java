/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.foundation.base.lang.time;

import java.util.List;

/**
 * @author muqi
 *
 */
public class TimeTuple<T> {

	private T beginTime;
	
	private T endTime;
	
	public TimeTuple(T beginTime , T endTime){
		this.beginTime = beginTime;
		this.endTime   = endTime;
	}

	public T getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(T beginTime) {
		this.beginTime = beginTime;
	}

	public T getEndTime() {
		return endTime;
	}

	public void setEndTime(T endTime) {
		this.endTime = endTime;
	}
	
	public List<T> getMiddleDate(){
		return null;
	}

	@Override
	public String toString() {
		return "TimeTuple [beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}
	
	
	
}
