/*
 * Class: QNameParser
 * Version: 1.0
 * Copyright(c) March 2016 Cherkassy, Ukraine
 * Author: Nekora Alexander
 */

package com.codenvy.testtask.qname;

import java.util.regex.*;

public class QNameParser {

	public QName parse(String name) throws IllegalNameException {
		if ((name == null) || (name.isEmpty())) 
			throw new IllegalNameException(name);
		if (nameRule(name)) {
			int index = name.indexOf(':');
			QName qName = new QName();
			if (index > 0) {
				qName.setPrefix(name.substring(0, index));
			}	
			qName.setLocalName(name.substring(index + 1));
			return qName;
		} else {
			throw new IllegalNameException(name);
		}
	}
	
	private boolean nonSpaceRule(String nonSpaceString) {
		Pattern nonspace = Pattern.compile("[\\S&&[^/:\\[\\]*\'\"|]]");
		Matcher nonspaceMatcher = nonspace.matcher(nonSpaceString);
		return (nonspaceMatcher.matches());
	}
	
	private boolean charRule(String character) {
		return ((character.length() == 1) && 
				(nonSpaceRule(character) || character.equals(" ")));
	}
	
	private boolean stringRule(String stringString) {
		if (stringString.length() == 1) {
			return  charRule(stringString);
		}
		return (stringRule(stringString.substring(0, stringString.length() - 1)) 
					&& charRule(stringString.substring(stringString.length() - 1)));
	}
	
	private boolean prefixRule(String prefix) {
		if (prefix.length() > 2) {
			String startOfPrefix = prefix.substring(0, 3);
			if (startOfPrefix.equalsIgnoreCase("xml")) {
				return false;			
			}
		}
		Pattern xmlNames = Pattern.compile("_?\\p{Alpha}+[\\p{Alnum}-_.]*");
		Matcher xmlNamesMatcher = xmlNames.matcher(prefix);
		return xmlNamesMatcher.matches();
	}
	
	private boolean threeOrMoreCharNameRule(String threeOrMoreCharString) {
		if (threeOrMoreCharString.length() < 3) {
			return false;
		}
		return (nonSpaceRule(threeOrMoreCharString.substring(0, 1))	
				&& stringRule(threeOrMoreCharString.
						substring(1, threeOrMoreCharString.length() - 1)) 
				&& nonSpaceRule(threeOrMoreCharString.
						substring(threeOrMoreCharString.length() - 1)));
	}
	
	private boolean twoCharLocalNameRule(String twoCharLocalName) {
		if (twoCharLocalName.length() != 2) {
			return false;
		}
		return (nonSpaceRule(twoCharLocalName.substring(0, 1)) 
				&& nonSpaceRule(twoCharLocalName.substring(1)));
	}
	
	private boolean oneCharLocalNameRule(String oneCharLocalName) {
		if (oneCharLocalName.length() != 1) {
			return false;
		}
		return nonSpaceRule(oneCharLocalName);
	}
	
	private boolean oneCharSimpleNameRule(String oneCharSimpleName) {
		Pattern simpleNamePattern = Pattern.compile("[\\S&&[^./:\\[\\]*\'\"|]]");
		Matcher simpleNameMatcher = simpleNamePattern.matcher(oneCharSimpleName);
		return simpleNameMatcher.matches();
	}
	
	private boolean twoCharSimpleNameRule(String twoCharSimpleName) {
		if (twoCharSimpleName.length() != 2) {
			return false;
		}
		return (((twoCharSimpleName.substring(0 , 1).equals(".")) 
					&& oneCharSimpleNameRule(twoCharSimpleName.substring(1)))
				|| ((twoCharSimpleName.substring(1).equals(".")) 
					&& oneCharSimpleNameRule(twoCharSimpleName.substring(0, 1)))
				|| (oneCharSimpleNameRule(twoCharSimpleName.substring(0, 1)) 
					&& oneCharSimpleNameRule(twoCharSimpleName.substring(1))));
	}
	
	private boolean localNameRule(String localName) {
		return (oneCharLocalNameRule(localName) || twoCharLocalNameRule(localName)
				|| threeOrMoreCharNameRule(localName));
	} 

	/*
	 * 
	 * if verification of (prefixedName.IndexOf(':') != prefixedName.lastIndexOf(':'))
	 * added, character ':' can be deleted from pattern of nonSpaceRule()
	 */		
	private boolean prefixedNameRule(String prefixedName) {
		int prefixIndex = prefixedName.indexOf(':');
		if (prefixIndex <= 0) { 
			return false;
		}
		return (prefixRule(prefixedName.substring(0, prefixIndex)) 
				&& localNameRule(prefixedName.substring(prefixIndex + 1)));
	}

	private boolean simpleNameRule(String simpleName) {
		return (oneCharSimpleNameRule(simpleName) 
				|| twoCharSimpleNameRule(simpleName)
				|| threeOrMoreCharNameRule(simpleName));
	}
	
	private boolean nameRule(String name) {
		return (simpleNameRule(name) || prefixedNameRule(name));
	}
}