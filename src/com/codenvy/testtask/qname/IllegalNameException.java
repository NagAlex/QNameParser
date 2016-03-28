/*
 * Class: IllegalNameException
 * Version: 1.0
 * Copyright(c) March 2016 Cherkassy, Ukraine
 * Author: Nekora Alexander
 */

package com.codenvy.testtask.qname;

public class IllegalNameException extends Exception {
	/**
	 *  Default serial version
	 */
	static final long serialVersionUID = 1L;
	
	public IllegalNameException() {
		super();
	}
	
	public IllegalNameException(String name) {
		super("Illegal value. String \"" + name + "\" can't be converted to type QName.");
	}
}
