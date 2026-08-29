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


public class TempletConfigure {

	private String filePath;
	
	private String encoding;
	
	private String templetRealization;
	
	private TempletSource templetSource;
	
	public static TempletConfigure create(){
		return new TempletConfigure();
	}
	
	public final TempletConfigure setFilePath(String filePath){
		this.filePath = filePath;
		return this;
	}
	
	public final TempletConfigure setEncoding(String encoding){
		this.encoding = encoding;
		return this;
	}
	
	public final TempletConfigure setTempletRealization(String templetRealization){
		this.templetRealization = templetRealization;
		return this;
	}
	
	public final TempletSource build(){
		
		Map<String,String> map = null;
		
		if(this.filePath != null){
			
		}
		if( this.encoding == null){
			this.encoding = "utf-8";
		}
		this.templetRealization = "velocity-string";
		if(this.templetRealization == null){
			this.templetRealization = "independent-string";
		}
		
		
		if( "independent-string".equals( this.templetRealization)){
			
		}else if( "velocity-string".equals(this.templetRealization)){
			//templetSource = new TempletVeocityStringSource();
		}
		
		if(this.filePath == null){
			this.templetSource.init();
		}else{
			this.templetSource.init( map );
		}
		this.templetSource.setEncoding( this.encoding );
		return templetSource;
	}
}
