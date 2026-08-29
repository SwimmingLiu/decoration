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
package com.lamp.foundation.base.extension.templet;

import java.util.Map;

public interface TempletSource {
	public void init();
	
	public void init(Map<String,String> map);
	
	public int addTemplet(String templetId ,  String templetString);
	
	public int upTemplet(String templetId ,  String templetString);
	
	public int deleltTemplet( String tempetId);
	
	public int disableTemplet( String tempetId);
	
	public TempletStringResource queryTemplet(String temetId);
	
	public void setEncoding(String encoding);
}
