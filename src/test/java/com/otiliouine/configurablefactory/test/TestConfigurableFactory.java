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
package com.otiliouine.configurablefactory.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.otiliouine.configurablefactory.ConfigurableFactory;
import com.otiliouine.configurablefactory.InvalidMappingValuesException;
import com.otiliouine.configurablefactory.NoFactoryDefinitionException;

public class TestConfigurableFactory {
	private ConfigurableFactory configurableFactory ;
	
	@Before
	public void before() {
		this.configurableFactory = new ConfigurableFactory("com/otiliouine/objectfactory/test-configurablefactory-config.xml");
	}

	@Test
	public void testCreateInstance() {		
		Collection c = configurableFactory.createInstance(Collection.class, Collections.singleton("value"));
		Assert.assertTrue(c instanceof ArrayList);
		Assert.assertEquals(1, c.size());
		Assert.assertEquals("value", c.iterator().next());
	}

	@Test(expected=InvalidMappingValuesException.class)
	public void testInvalidMappingException() {
		Map c = configurableFactory.createInstance(Map.class);
	}
	
	@Test(expected = NoFactoryDefinitionException.class)
	public void testNoFactoryDefinitionException() {
		Runnable c = configurableFactory.createInstance(Runnable.class);
	}
}
