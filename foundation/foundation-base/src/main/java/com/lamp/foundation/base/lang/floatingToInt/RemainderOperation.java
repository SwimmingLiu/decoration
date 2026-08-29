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

public interface RemainderOperation<T extends Number> {

	double reduction(T t);
	
	double take(T num , int takeNum);
	
	
	T truncate(String str);
	T truncate(Number num);
	T truncate(Double num);
	
	T round(String str);
	T round(Number num);
	T round(Double num);

	T ceiling(String str);
	T ceiling(Number num);
	T ceiling(Double num);
}
