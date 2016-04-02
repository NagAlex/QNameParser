/**
 * Class: IllegalNameException
 * Version: 1.0
 * Copyright(c) March 2016 Cherkassy, Ukraine
 * @author: Nekora Alexander
 */

package com.codenvy.testtask.qname;

/**
 * This exception is thrown when trying to parse an object of type QName with invalid string 
 */
public class IllegalNameException extends Exception {
	
	/* Default serial version */
	static final long serialVersionUID = 1L;
	
	public IllegalNameException() {
		super();
	}
	
	/**
	 * Constructor with own message 
	 */
	public IllegalNameException(String message) {
		super(message);
	}
}
