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

/**
 * Exception thrown when the configuration file of a ConfigurableFactory contain
 * invalid values
 * 
 * @author Othmen Tiliouine
 */
public class InvalidMappingValuesException extends RuntimeException {

	private static final long serialVersionUID = -3705616966266575381L;

	public InvalidMappingValuesException() {
		super();
	}

	public InvalidMappingValuesException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMappingValuesException(String message) {
		super(message);
	}

	public InvalidMappingValuesException(Throwable cause) {
		super(cause);
	}

}
