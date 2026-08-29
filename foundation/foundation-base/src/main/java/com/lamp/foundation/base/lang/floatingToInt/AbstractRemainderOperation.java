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
package com.lamp.foundation.base.lang.floatingToInt;

import java.math.BigDecimal;

public abstract class AbstractRemainderOperation<T extends Number> implements RemainderOperation<T> {
	int multiple;
	
	
	public AbstractRemainderOperation(int multiple){
		this.multiple = multiple;
	}
	

	@Override
	public T truncate(String num) {
		return truncate(Double.valueOf(num));
	}
	@Override
	public T truncate(Number num) {
		return truncate( (Double)num);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T truncate(Double num) {
		return (T)(Double.valueOf(Math.floor(num*multiple)));
	}
	@SuppressWarnings("unchecked")
	@Override
	public T round(String num) {
		return (T) Long.valueOf( new BigDecimal( num ).setScale(0, BigDecimal.ROUND_HALF_UP).longValue() );
	}
	
	@Override
	public T round(Number num) {
		
		return round( num.toString());
	}
	@Override
	public T round(Double num) {
		return round( num.toString());
	}

	
	@Override
	public T ceiling(String num) {
		
		return ceiling(Double.valueOf(num));
	}
	@Override
	public T ceiling(Number num) {		
		return truncate( (Double)num);
	}
	@SuppressWarnings("unchecked")
	@Override
	public T ceiling(Double num) {	
		return (T) Double.valueOf(Math.ceil(num*multiple));
	}

}
