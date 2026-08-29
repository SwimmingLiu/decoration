package com.lamp.decoration.foundation.network.redis.annotation;

import java.lang.reflect.Method ;
import java.util.HashMap;

import com.lamp.decoration.foundation.network.redis.utils.AnniationUtils;


public class LedisAanntationCollection {

	
	private  HashMap< String , OperationsEntity > operationsEntity_map = new HashMap<>( ) ;
	
	private void isExistence(String name){
		if( name  == null ){
			
		}
		if( "".equals( name )){
			
		}
		if( !operationsEntity_map.containsKey( name )){
			
		}
	}

	public void addOperationEntity(Class<?> clazz) {
		if( clazz.isInterface( )){
			Method[] me = clazz.getMethods( );
			int meLength = me.length;
			if(meLength == 0) {
                return;
            }
			Method m;
			Class<?>  meClazz;
            for (Method method : me) {
                m = method;
                meClazz = m.getReturnType();
                if (isType(meClazz, false)) {
                    return;
                }
                OperationsEntity entity = AnniationUtils.getOperationsEntity(m, meClazz);
                if (entity.isEntity()) {
                    operationsEntity_map.put(m.getName(), entity);
                }
            }
		}
	}
	
	public void addOperation(Class<?> clazz ){
		OperationList operationList;
		 if( clazz.isInterface( )){
			 Method[] me = clazz.getMethods( );
			 Class<?>  meClazz;
			 for(Method m : me){
				  operationList=  m.getAnnotation( OperationList.class  );
				  if( operationList != null){
					  meClazz = m.getReturnType( );
					  isType(meClazz , false);
					  setOperationListMap( meClazz , operationList );
				  }			 
			 }
			return; 
		 }
		 isType(clazz , true);
		 operationList= clazz.getAnnotation( OperationList.class );
		 setOperationListMap( clazz , operationList );
	}
	
	
	public void setOperationListMap( Class<?>  clazz, OperationList operationList){
		 OperationsObject[] operationsArray = operationList.operationsObject( );
		 OperationsEntity oe ;
		 for(OperationsObject operations : operationsArray){
			 isExistence( operations.name( ) );
			 oe = new OperationsEntity( );
			 oe.setName(  operations.name( ) );
			 oe.setOperationListMap( operations ,  clazz);
			 operationsEntity_map.put( operations.name( ) , oe );
		 }
	}
	
	public HashMap< String , OperationsEntity >  getOperationsEntityMap(){
		return this.operationsEntity_map;
	}
	
	public boolean isType(Class<?> clazz ,boolean bo){
		if( clazz.equals( Void.class ) || clazz.isInterface( ) || clazz.isAnnotation( ) || clazz.isEnum( ) || clazz.isPrimitive( )){
			  return true;
		}
		return false;
	}
	
}
