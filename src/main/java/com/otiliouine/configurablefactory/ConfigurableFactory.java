/*
 * Copyright 2013 Othmen Tiliouine
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.otiliouine.configurablefactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.ConstructorUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A generic configurable factory for creating an object. this factory use an
 * XML resource to map interfaces to their implementation classes use XML is
 * like this 
 * <p/>
 * <code>
 * 	&lt;factories&gt;
 * <p/>
 * 		&lt;factory interface="java.util.Collection" implementation="java.util.ArrayList"/&gt;
 * 	
 * <p/>
 * 		&lt;factory interface="com.example.MyInterface" implementation="com.example.MyImplementation1"/&gt;
 * <p/>
 * 	&lt;/factories&gt;
 * </code> 
 * <p/>
 * when using this XML, if you request a Collection to the
 * ConfigurableFactory, it will return a new instance of ArrayList
 * 
 * @author Othmen Tiliouine
 */
public class ConfigurableFactory {

	private Map<Class<?>, Class<?>> mapping = new HashMap<Class<?>, Class<?>>();

	public ConfigurableFactory(File file) {
		try {
			loadConfiguration(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new ConfigFileException(
					"IOException parsing XML configuration file " + file, e);
		}
	}

	public ConfigurableFactory(String path) {
		InputStream in = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(path);
		loadConfiguration(in);
	}

	/**
	 * Creates an instance of the implementation class mapped to <b>interfazz</b>
	 * 
	 * @param interfazz the Interface or class which represents the abstraction of instance types
	 * @param args the arguments to use when creating the new instance
	 * @return a new instance of the implementation class mapped to <b>interfazz</b>
	 */
	public <T> T createInstance(Class<T> interfazz, Object... args) {
		Class<T> clazz = (Class<T>) mapping.get(interfazz);
		if (clazz == null) {
			throw new NoFactoryDefinitionException(
					"No factory is defined for class "
							+ interfazz.getCanonicalName());
		}
		try {
			T instance = (T) ConstructorUtils.invokeConstructor(clazz, args);
			return interfazz.cast(instance);
		} catch (ClassCastException e) {
			throw new InvalidMappingValuesException(
					"invalid factory configuration, class "
							+ clazz.getCanonicalName(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	private void loadConfiguration(InputStream inputStream) {
		try {
			Document document = loadDocument(inputStream);
			document.getDocumentElement().normalize();
			NodeList nList = document.getElementsByTagName("factory");
			for (int index = 0; index < nList.getLength(); index++) {
				Element node = (Element) nList.item(index);
				String superClass = node.getAttribute("interface");
				String implementation = node.getAttribute("implementation");
				Class<?> superClazz = Class.forName(superClass);
				Class<?> subClazz = Class.forName(implementation);
				mapping.put(superClazz, subClazz);
			}
		} catch (ClassNotFoundException e) {
			throw new InvalidMappingValuesException(e.getMessage(), e);
		}
	}

	private Document loadDocument(InputStream file) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.parse(file);
			return document;
		} catch (ParserConfigurationException e) {
			throw new ConfigFileException(
					"Parser configuration exception parsing XML configuration file",
					e);
		} catch (SAXException e) {
			throw new ConfigFileException("invalid XML configuration file", e);
		} catch (IOException e) {
			throw new ConfigFileException(
					"IOException parsing XML configuration file", e);
		}

	}
}
