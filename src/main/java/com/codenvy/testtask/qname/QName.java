/**
 * Class: QName
 * Version: 1.1
 * Copyright(c) March 2016 Cherkassy, Ukraine
 * @author: Nekora Alexander
 */

package com.codenvy.testtask.qname;

/**
 * Constructs an object of valid qualified name.
 * Depends on {@link QNameParser} for matching to valid qualified name.
 * All checks was transferred to class {@link QNameParser} for case if 
 * some rules will be changed or added/deleted.  
 */
public class QName {
	private String prefix;
	private String localName;
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * @return prefix part of this qualified name 
	 */
	public String getPrefix() {
		return prefix;
	}
	
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	
	/**
	 * @return localname part of this qualified name 
	 */
	public String getLocalName() {
		return localName;
	}
	
	/**
	 * @return full representation of this qualified name 
	 */
	public String getAsString() {
		return (prefix == null) ? localName : prefix + ":" + localName;
	}
	
	/**
	 * Creates an object of type QName
	 * @param name - string to be converted to QName
	 * @return new object of QName type
	 * @throws IllegalNameException if parameter is not valid   
	 */
	public static QName parse(String name) throws IllegalNameException {
		return new QNameParser().parse(name);
	}
	
	/**
	 * Checks if this object is equal to qName 
	 * @param qName - object to be compared to this one
	 * @return true if objects are equal
	 */
	public boolean equals(QName qName) {
		return (getAsString().equals(qName.getAsString()));
	}
	
}
