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

import java.io.Writer;
import java.util.Map;

public final class TempletManage {

	private static TempletSource templetSource;
	
	private static TempletDispose templetDispose;
	
	public static final void init() {
		init( TempletConfigure.create().build());
	}

	/**
	 * 解析有 自定义解析类， 与 velocity string 模板， 目前不支持 velocity file解析 默认初始化 自定义解析类
	 * 默认字符编码 utf-8
	 * @param templetSource 
	 */
	public static final void init(TempletSource templetSource) {
		TempletManage.templetSource = templetSource;
		TempletManage.templetDispose = (TempletDispose)templetSource;
	}
	
	
	public static String mergeTemplateString(String id , Map<String,Object> context){
		return TempletManage.templetDispose.mergeTemplateString(id, context);
	}
	
	public static Writer mergeTemplateWriterString(String id , Map<String,Object> context){
		return TempletManage.templetDispose.mergeTemplateWriterString(id, context);
	}
	
	public static void mergeTemplateWriter( String id , Map<String,Object> context , Writer writer){
		TempletManage.templetDispose.mergeTemplateWriter(id, context, writer);
	}
}
