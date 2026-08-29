package com.lamp.decoration.foundation.network.redis.utils;

import java.io.File ;

import javax.xml.parsers.DocumentBuilder ;
import javax.xml.parsers.DocumentBuilderFactory ;

import org.w3c.dom.Document ;
import org.w3c.dom.NamedNodeMap ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;

public class ScriptXML {

	public static final void scriptXMLAnalysis ( String path ) throws Exception {
		File file = new File( path ) ;

		if ( file.isFile( ) ) {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance( ) ;
			DocumentBuilder builder = builderFactory.newDocumentBuilder( ) ;
			Document document = builder.parse( file ) ;
			NodeList script = document.getElementsByTagName( "script" ) ;
			Node node ;
			String clazz;
			for ( int i = 0 , length = script.getLength( ) ; i < length ; i++ ) {
				node = script.item( i ) ;
				NamedNodeMap attri = node.getAttributes( ) ;
				attri.getNamedItem( "name" ) ;
				clazz = attri.getNamedItem( "class" ).getNodeValue( ) ;
				Class.forName( clazz );
				attri.getNamedItem( "mode" ) ;
				node.getNodeValue( ) ;
			}
			
		}
	}

}
