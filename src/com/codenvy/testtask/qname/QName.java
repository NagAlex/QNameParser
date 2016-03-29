/*
 * Class: QName
 * Version: 1.0
 * Copyright(c) March 2016 Cherkassy, Ukraine
 * Author: Nekora Alexander
 */

package com.codenvy.testtask.qname;

public class QName {
	private String prefix;
	private String localName;
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public String getAsString() {
		return (prefix == null) ? localName : prefix + ":" + localName;
	}
	
	public static QName parse(String name) throws IllegalNameException {
		return new QNameParser().parse(name);
	}
	
	public boolean equals(QName qName) {
		return (getAsString().equals(qName.getAsString()));
	}
	
}
