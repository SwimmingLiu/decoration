package com.lamp.decoration.foundation.network.redis.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Content {
	private List<String>    contentList   = new ArrayList<>();
	
	private List<parameter> parameterList = new ArrayList<>();
	
	private int b = 0;
	
	private int length;
	
	private String content;
	
	private static final char LEFTPARENTHESIS = '{';
	
	private static final char RIGHTPARENTHESIS = '}';
	
	
	
	public Content(String conStr){
		this.length = conStr.length();
		into(conStr);
	}
	
	/**
	 * 找到，替换成key 与args就可以。两个循环就好。
	 * 直接使用{} 或者[] , 还是使用key还args
	 * @param conStr
	 */
	private void into(String conStr){
		int i = 0 , ii , iii;
		i     = conStr.indexOf(LEFTPARENTHESIS, i);
		if(i == -1){
			contentList = null;
			parameterList = null;
			content = conStr;
			return;
		}
		iii   = conStr.indexOf(RIGHTPARENTHESIS, i+1);
		ii    = conStr.indexOf(LEFTPARENTHESIS, iii);
		if(ii > -1){
			contentList.add(conStr.substring(0, i));
			
			parameterList.add(new parameter(conStr.substring(i+1, iii)));
			contentList.add(null);
			contentList.add(conStr.substring(iii+1, ii));
			for( ; ;){
				iii  = conStr.indexOf(RIGHTPARENTHESIS, ii);
				parameterList.add(new parameter(conStr.substring(ii+1, iii)));
				contentList.add(null);
				ii  = conStr.indexOf(LEFTPARENTHESIS, iii);
				if(ii == -1)
					break;
				contentList.add(conStr.substring(iii+1, ii));
			}
			b = 2;
		}else{
			contentList.add(conStr.substring(0, i));
			parameterList.add(new parameter(conStr.substring(i+1, iii)));
			contentList.add(null);
			b = 1;
		}
		if(iii != conStr.length()){
			contentList.add(conStr.substring(iii+1, conStr.length()));
		}
	}
	
	public String getContent(Object object){
		if(b == 0)
			return content;
		StringBuilder sb = new StringBuilder(length);
		if(b == 1){
			sb.append(contentList.get(0));
			if(object instanceof String){
				sb.append((String)object);
			}else{
				parameter parameter = parameterList.get(0);
				parameter.getMethod(object);
				sb.append(parameter.getMethod(object));
			}
			if(contentList.size() == 3){
				sb.append(contentList.get(2));
			}
		}else if(b == 2){
			int leng = contentList.size();
			int i = 0;
			int pi = 0;
			String com ;
			for( ;  ; ){
				com = contentList.get(i++);
				if(com == null){
					parameter parameter = parameterList.get(pi++);
					com = parameter.getParemeString(object);
				}
				sb.append(com);
				if(leng == i)
					break;
			}
		}
		return sb.toString();
	}
	
	static class parameter{
		private String parName;
		
		private Method  met;
		
		parameter(String parName){
			this.parName = parName;
		}
		
		@SuppressWarnings("unchecked")
		String getMapPareme(Object object){
			return ((Map<String,String>)object).get(parName);
		}
		
		String getMethod(Object object){
			if(met == null){
				try {
					System.out.println("get"+Character.toUpperCase(parName.charAt(0)) + parName.substring(1));
					met = object.getClass().getMethod("get"+Character.toUpperCase(parName.charAt(0)) + parName.substring(1), new Class[0]);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
			String str = null;
			if(met != null){
				try {
					str = met.invoke(object, new Object[0]).toString();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			return str;
		}
		
		public String getParemeString(Object object){
			if(object instanceof Map){
				return getMapPareme(object);
			}else{
				return getMethod(object);
			}
		}
	}
}
