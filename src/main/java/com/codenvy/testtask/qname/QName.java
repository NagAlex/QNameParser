/**
 * Class: QName
 * Version: 1.2
 * Copyright(c) March 2016 Cherkassy, Ukraine
 * @author: Nekora Alexander
 */

package com.codenvy.testtask.qname;

import java.util.regex.*;

/**
 * Constructs an object of valid qualified name.
 * Checks all the rules to verify if a string given to the 
 * method parse() is a valid qualified name and return 
 * an QName object if it is. 
 * Uses the rules of BNF format
 * Uses java.util.regex package for verification  
 */
public class QName {
	private String prefix;
	private String localName;

	/**
	 * Pattern to check onecharsimplename rule 
	 */
	private static final Pattern SIMPLE_NAME_PATTERN = 
			Pattern.compile("[\\S&&[^./:\\[\\]*\'\"|]]");
	
	/**
	 * Pattern for prefix rule. In combination with method prefixRule() checks for
	 * valid XML names
	 * */
	private static final Pattern XML_NAMES = 
			Pattern.compile("_?\\p{Alpha}+[\\p{Alnum}-_.]*");
	
	/**
	 * Pattern for nonspace rule 
	 */
	private static final Pattern NONSPACE = 
			Pattern.compile("[\\S&&[^/:\\[\\]*\'\"|]]");

	/**
	 * private constructor to let build a QName object through 
	 * parse() method only  
	 */
	private QName() {}
		
	private void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * @return prefix part of this qualified name 
	 */
	public String getPrefix() {
		return prefix;
	}
	
	private void setLocalName(String localName) {
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
	 * @return String representation of QName object 
	 */
	public String toString() {
		return "QName [" + getAsString() + "]";
	}

	/**
	 * Checks if this object is equal to this QName object 
	 * @param objectToCompare - object to be compared to this one
	 * @return true if objects are equal
	 */
	public final boolean equals(Object objectToCompare) {
		
		if (this == objectToCompare) {
			return true;
		}
		if ((objectToCompare == null) || (!(objectToCompare instanceof QName))) {
			return false;
		}
		QName compareObj = (QName) objectToCompare;
		if ((getAsString() == null) || (compareObj.getAsString() == null)) {
			return false;
		}
		return getAsString().equals(compareObj.getAsString());
	}
	
	/**
	 * Generates the hash code for this QName instance
	 * @return hashCode of this QName instance
	 */
	public final int hashCode() {
		int prefixHash = (prefix == null) ? 0 : prefix.hashCode();
		int localNameHash = (localName == null) ? 0 : localName.hashCode();
		return prefixHash ^ localNameHash;
	}

	/**
	 * Creates an object of type QName
	 * @param name - string to be converted to QName
	 * @return new object of QName type
	 * @throws IllegalNameException if parameter is not valid   
	 */
	public static QName parse(String name) throws IllegalNameException {
		return checkRules(name);
	}

	/**
	 * Checks all the rules of valid qualified name and constructs 
	 * an object of qualified name, if the rules passed
	 * @param name - the string to be checked
	 * @return an object of type QName   
	 * @throws IllegalNameException 
	 */
	private static QName checkRules(String name) throws IllegalNameException {
		if ((name == null) || (name.isEmpty())) 
			throw new IllegalNameException ("Cannot create Qname from null " +
							"or empty string");
		try {
			if (nameRule(name)) {
				int index = name.indexOf(':');
				QName qName = new QName();
				if (index > 0) {
					qName.setPrefix(name.substring(0, index));
				}	
				qName.setLocalName(name.substring(index + 1));
				return qName;
			} else {
				return null;
			}
		} catch (IllegalNameException e) {
			throw new IllegalNameException("An error occured while building " + 
					"QName from \"" + name + "\". " +e.getMessage());
		}
	}

	/**
	 * checks the rule <pre> name ::= simplename | prefixedname </pre>
	 * @param string name to be checked
	 * @return true if the name is valid  
	 * @throws IllegalNameException in other case
	 */
	private static boolean nameRule(String name) throws IllegalNameException {
		int colonIndex = name.indexOf(':');
		if (colonIndex > 0) {
			return prefixedNameRule(name); 
		} else {
			return simpleNameRule(name);
		}
	}
	
	/**
	 * checks the rule <pre> simplename ::= onecharsimplename 
	 * 			| twocharsimplename | threeormorecharname </pre>
	 * @param simpleName string to be checked
	 * @return true if the simpleName is valid  
	 * @throws IllegalNameException in other case
	 */
	private static boolean simpleNameRule(String simpleName) 
			throws IllegalNameException {
		if (oneCharSimpleNameRule(simpleName) 
				|| twoCharSimpleNameRule(simpleName)
				|| threeOrMoreCharNameRule(simpleName)) {
			return true;
		} else {
			throw new IllegalNameException("Cannot create QName from \"" + 
							simpleName + "\"");
		}
	}
	
	/**
	 * checks the rule <pre> prefixedname ::= prefix ':' localname </pre>
	 * @param prefixedName string to be checked
	 * @return true if the prefixedName is valid  
	 * @throws IllegalNameException  in other case
	 */
	private static boolean prefixedNameRule(String prefixedName) 
			throws IllegalNameException {
		int prefixIndex = prefixedName.indexOf(':');
		if (prefixIndex == (prefixedName.length() - 1)) {
			throw new IllegalNameException("Cannot create QName from \"" + 
							prefixedName + "\"");
		}
		return (prefixRule(prefixedName.substring(0, prefixIndex)) 
				&& localNameRule(prefixedName.substring(prefixIndex + 1)));
			
	}

	/**
	 * checks the rule <pre> localname ::= onecharlocalname 
	 * 			| twocharlocalname | threeormorecharname </pre>
	 * @param localName string to be checked
	 * @return true if the localName is valid  
	 * @throws IllegalNameException 
	 */
	private static boolean localNameRule(String localName) 
			throws IllegalNameException {
		return (oneCharLocalNameRule(localName) || twoCharLocalNameRule(localName)
				|| threeOrMoreCharNameRule(localName));
	} 

	/**
	 * checks the rule <pre> onecharsimplename ::= (* Any unicode character except:
	 * 					'.', '/', ':', '[', ']', '*', ''',
	 * 					'"', '|' or any whitespace 
	 * 					character *) </pre>
	 * @param oneCharSimpleName string to be checked
	 * @return true if the oneCharSimpleName is valid  
	 */
	private static boolean oneCharSimpleNameRule(String oneCharSimpleName) {
		Matcher simpleNameMatcher = SIMPLE_NAME_PATTERN.matcher(oneCharSimpleName);
		return simpleNameMatcher.matches();
	}

	/**
	 * checks the rule <pre> twocharsimplename ::= 
	 *			'.' onecharsimplename 
	 * 			| onecharsimplename '.'
	 * 			| onecharsimplename onecharsimplename </pre>
	 * @param twoCharSimpleName string to be checked
	 * @return true if the twoCharSimpleName is valid  
	 */
	private static boolean twoCharSimpleNameRule(String twoCharSimpleName) {
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

	/**
	 * checks the rule <pre> onecharlocalname ::= nonspace </pre>
	 * @param oneCharLocalName string to be checked
	 * @return true if the oneCharLocalName is valid  
	 */
	private static boolean oneCharLocalNameRule(String oneCharLocalName) {
		if (oneCharLocalName.length() != 1) {
			return false;
		}
		return nonSpaceRule(oneCharLocalName);
	}

	/**
	 * checks the rule <pre> twocharlocalname ::= nonspace nonspace </pre>
	 * @param twoCharLocalName string to be checked
	 * @return true if the twoCharLocalName is valid  
	 */
	private static boolean twoCharLocalNameRule(String twoCharLocalName) {
		if (twoCharLocalName.length() != 2) {
			return false;
		}
		return (nonSpaceRule(twoCharLocalName.substring(0, 1)) 
				&& nonSpaceRule(twoCharLocalName.substring(1)));
	}

	/**
	 * checks the rule <pre> threeormorecharname ::= nonspace string nonspace </pre>
	 * @param threeOrMoreCharString string to be checked
	 * @return true if the threeOrMoreCharString is valid  
	 * @throws IllegalNameException in other case
	 */
	private static boolean threeOrMoreCharNameRule(String threeOrMoreCharString) 
			throws IllegalNameException {
		if (threeOrMoreCharString.length() < 3) {
			return false;
		}
		String firstSymbol = threeOrMoreCharString.substring(0, 1);
		String lastSymbol = threeOrMoreCharString.
				     substring(threeOrMoreCharString.length() - 1);
		if (!nonSpaceRule(firstSymbol)) {
			throw new IllegalNameException("Localname cannot start with \'" + 
							firstSymbol + "\'");
		}
		if (!nonSpaceRule(lastSymbol)) {
			throw new IllegalNameException("Localname cannot end with \'" + 
							lastSymbol + "\'");
		}
		return stringRule(threeOrMoreCharString.
				   substring(1, threeOrMoreCharString.length() - 1)); 
	}

	/**
	 * checks the rule <pre> prefix ::= (*Any valid XML name*) </pre>
	 * @param prefix string to be checked
	 * @return true if a prefix is valid  
	 * @throws IllegalNameException if a prefix is not valid XML name
	 */
	private static boolean prefixRule(String prefix) 
			throws IllegalNameException {
		if (prefix.length() > 2) {
			String startOfPrefix = prefix.substring(0, 3);
			if (startOfPrefix.equalsIgnoreCase("xml")) {
				throw new IllegalNameException("Prefix cannot start with \"" + 
								startOfPrefix + "\"");			
			}
		}
		Matcher xmlNamesMatcher = XML_NAMES.matcher(prefix);
		if (!xmlNamesMatcher.matches()) {
			throw new IllegalNameException("Prefix \"" + prefix + "\" " + 
				"is not a valid XML name. Prefix must be a valid XML name");
		} else {
			return true;
		}
	}

	/**
	 * checks the rule <pre> string ::= char | string char </pre>
	 * @param stringString string to be checked
	 * @return true if the stringString is valid  
	 * @throws IllegalNameException 
	 */
	private static boolean stringRule(String stringString) 
			throws IllegalNameException {
		if (stringString.length() == 1) {
			return (charRule(stringString));
		}
		return (stringRule(stringString.substring(0, stringString.length() - 1)) 
					&& charRule(stringString.substring(stringString.length() - 1)));
	}

	/**
	 * checks the rule <pre> char ::= nonspace | ' ' </pre>
	 * @param character string to be checked
	 * @return true if the character is valid  
	 * @throws IllegalNameException in other case
	 */
	private static boolean charRule(String character) 
			throws IllegalNameException {
		if ((character.length() == 1) && 
				(nonSpaceRule(character) || character.equals(" "))) {
			return true;
		} else {
			throw new IllegalNameException("Localname cannot contain \'" + 
					character + "\' symbol"); 
		}
	}

	/**
	 * checks the rule <pre> nonspace ::= (* Any Unicode character except:
	 * 				   '/', ':', '[', ']', '*', ''', '"', 
	 * 				   '|' or any whitespace character *) </pre>
	 * @param nonSpaceString string to be checked
	 * @return true if the nonSpaceString is valid  
	 */
	private static boolean nonSpaceRule(String nonSpaceString) {
		Matcher nonspaceMatcher = NONSPACE.matcher(nonSpaceString);
		return (nonspaceMatcher.matches());
	}

}
