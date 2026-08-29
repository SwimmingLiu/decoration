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

public interface TempletDispose {

	
	public String mergeTemplateString(String id , Map<String,Object> context);
	
	public Writer mergeTemplateWriterString(String id , Map<String,Object> context);
	
	public void mergeTemplateWriter( String id , Map<String,Object> context , Writer writer);
}
