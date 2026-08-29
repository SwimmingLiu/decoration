/*
package com.lamp.decoration.foundation.network.redis.spring;

import java.io.IOException ;
import java.lang.annotation.Annotation ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.Map.Entry ;
import java.util.Set ;

import org.springframework.beans.MutablePropertyValues ;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition ;
import org.springframework.beans.factory.config.BeanDefinitionHolder ;
import org.springframework.beans.factory.support.BeanDefinitionRegistry ;
import org.springframework.beans.factory.support.GenericBeanDefinition ;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner ;
import org.springframework.core.type.classreading.MetadataReader ;
import org.springframework.core.type.classreading.MetadataReaderFactory ;
import org.springframework.core.type.filter.AnnotationTypeFilter ;
import org.springframework.core.type.filter.AssignableTypeFilter ;
import org.springframework.core.type.filter.TypeFilter ;

import com.lamp.decoration.foundation.network.redis.annotation.LedisAanntationCollection;
import com.lamp.decoration.foundation.network.redis.annotation.Mapper;
import com.lamp.decoration.foundation.network.redis.annotation.OperationsEntity;
import com.lamp.decoration.foundation.network.redis.commands.RedisCommands;


*/
/**
 * 1，先扫描
 * 
 * @author laohu
 *
 *//*

public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

	private Class< ? extends Annotation > annotationClass = Mapper.class ;

	private Class< ? > markerInterface ;

	private MapperFactoryBean<RedisCommands<Object, Object>> mapperFactoryBean = new MapperFactoryBean<RedisCommands<Object, Object>>( ) ;

	public ClassPathMapperScanner(BeanDefinitionRegistry registry) {
		super( registry ) ;
	}

	public void setAnnotationClass ( Class< ? extends Annotation > annotationClass ) {
		this.annotationClass = annotationClass ;
	}

	public void setMarkerInterface ( Class< ? > markerInterface ) {
		this.markerInterface = markerInterface ;
	}

	public void registerFilters ( ) {
		boolean acceptAllInterfaces = true ;
		if ( this.annotationClass != null ) {
			addIncludeFilter( new AnnotationTypeFilter( this.annotationClass ) ) ;
			acceptAllInterfaces = false ;
		}
		if ( this.markerInterface != null ) {
			addIncludeFilter( new AssignableTypeFilter( this.markerInterface ) {
				@Override
				protected boolean matchClassName ( String className ) {
					return false ;
				}
			} ) ;
			acceptAllInterfaces = false ;
		}

		if ( acceptAllInterfaces ) {
			// default include filter that accepts all classes
			addIncludeFilter( new TypeFilter( ) {
				@Override
				public boolean match ( MetadataReader metadataReader , MetadataReaderFactory metadataReaderFactory )
						throws IOException {
					return true ;
				}
			} ) ;
		}

		// exclude package-info.java
		addExcludeFilter( new TypeFilter( ) {
			@Override
			public boolean match ( MetadataReader metadataReader , MetadataReaderFactory metadataReaderFactory )
					throws IOException {
				String className = metadataReader.getClassMetadata( ).getClassName( ) ;
				return className.endsWith( "package-info" ) ;
			}
		} ) ;
	}

	@Override
	public Set< BeanDefinitionHolder > doScan ( String... basePackages ) {
		Set< BeanDefinitionHolder > beanDefinitions = super.doScan( basePackages ) ;
		if ( beanDefinitions.isEmpty( ) ) {
			logger.warn( "No MyBatis mapper was found in '" + Arrays.toString( basePackages )
					+ "' package. Please check your configuration." ) ;
		} else {
			beanDefinitions = processBeanDefinitions( beanDefinitions ) ;
		}
		return beanDefinitions ;
	}

	private Set< BeanDefinitionHolder > processBeanDefinitions ( Set< BeanDefinitionHolder > beanDefinitions ) {
		try {
			GenericBeanDefinition definition = null ;
			LedisAanntationCollection lac = new LedisAanntationCollection( ) ;
			
			Class< ? > clazz ;
			for ( BeanDefinitionHolder holder : beanDefinitions ) {
				definition = ( GenericBeanDefinition ) holder.getBeanDefinition( ) ;
				clazz = Class.forName( definition.getBeanClassName( ) ) ;

				lac.addOperationEntity( clazz ) ;
			}
			HashMap< String , OperationsEntity> map = lac.getOperationsEntityMap( ) ;
			Iterator< Entry< String , OperationsEntity > > it = map.entrySet( ).iterator( ) ;
			Entry< String , OperationsEntity > e ;
			MutablePropertyValues mp ;
			while ( it.hasNext( ) ) {
				e = it.next( ) ;
				definition = new GenericBeanDefinition( ) ;
				definition.setBeanClass( mapperFactoryBean.getClass( ) ) ;
				definition.setInitMethodName( "init" ) ;
				mp = new MutablePropertyValues( ) ;
				definition.setPropertyValues( mp ) ;
				mp.add( "operationsEntity" , e.getValue( ) ) ;
				this.getRegistry( ).registerBeanDefinition( e.getKey( ) , definition ) ;
			}
			 
		} catch ( ClassNotFoundException e1 ) {
			e1.printStackTrace( ) ;
		}
		return null ;
	}
	
	  @Override
	  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
	    return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
	  }
}
*/
