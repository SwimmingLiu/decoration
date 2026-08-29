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

public final class RemainderOperationUtil {

	public static final RemainderOperation<Integer> IntATimes          = new IntRemainderOperation(1);
	
	public static final RemainderOperation<Integer> IntBestTimes       = new IntRemainderOperation(100);
	
	public static final RemainderOperation<Integer> IntThousandTimes   = new IntRemainderOperation(10000);
	
	public static final RemainderOperation<Integer> IntWanTimes        = new IntRemainderOperation(1000000);
	
	public static final RemainderOperation<Long>    LongATimes         = new LongRemainderOperation(1);
	
	public static final RemainderOperation<Long>    LongBestTimes      = new LongRemainderOperation(100);
	
	public static final RemainderOperation<Long>    LongThousandTimes  = new LongRemainderOperation(10000);
	
	public static final RemainderOperation<Long>    LongWanTimes       = new LongRemainderOperation(1000000);
	
}
