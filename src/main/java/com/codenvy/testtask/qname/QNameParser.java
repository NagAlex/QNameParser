/**
 * Class: QNameParser
 * Version: 1.0
 * Copyright(c) March 2016 Cherkassy, Ukraine
 * @author: Nekora Alexander
 */

package com.codenvy.testtask.qname;

import java.util.regex.*;

/**
 * Checks all the rules to verify if the given 
 * string is a valid qualified name. 
 * Uses the rules of BNF format
 * Uses java.util.regex package for verification 
 */
class QNameParser {
	/**
	 * Pattern to check onecharsimplename rule 
	 */
	private static final Pattern SIMPLE_NAME_PATTERN = Pattern.compile("[\\S&&[^./:\\[\\]*\'\"|]]");
	
	/**
	 * Pattern for prefix rule. In combination with method prefixRule() checks for
	 * valid XML names
	 * */
	private static final Pattern XML_NAMES = Pattern.compile("_?\\p{Alpha}+[\\p{Alnum}-_.]*");
	
	/**
	 * Pattern for nonspace rule 
	 */
	private static final Pattern NONSPACE = Pattern.compile("[\\S&&[^/:\\[\\]*\'\"|]]");

	/**
	 * Checks all the rules of valid qualified name and constructs 
	 * an object of qualified name, if the rules passed
	 * @param name - the string to be checked
	 * @return an object of type QName   
	 * @throws IllegalNameException
	 */
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

	/**
	 * checks the rule <pre> name ::= simplename | prefixedname </pre>
	 * @param string name to be checked
	 * @return true if the name is valid  
	 */
	private boolean nameRule(String name) {
		return (simpleNameRule(name) || prefixedNameRule(name));
	}
	
	/**
	 * checks the rule <pre> simplename ::= onecharsimplename 
	 * 						    | twocharsimplename | threeormorecharname </pre>
	 * @param simpleName string to be checked
	 * @return true if the simpleName is valid  
	 */
	private boolean simpleNameRule(String simpleName) {
		return (oneCharSimpleNameRule(simpleName) 
				|| twoCharSimpleNameRule(simpleName)
				|| threeOrMoreCharNameRule(simpleName));
	}
	
	/**
	 * checks the rule <pre> prefixedname ::= prefix ':' localname </pre>
	 * @param prefixedName string to be checked
	 * @return true if the prefixedName is valid  
	 */
	private boolean prefixedNameRule(String prefixedName) {
		int prefixIndex = prefixedName.indexOf(':');
		if (prefixIndex <= 0) { 
			return false;
		}
		return (prefixRule(prefixedName.substring(0, prefixIndex)) 
				&& localNameRule(prefixedName.substring(prefixIndex + 1)));
	}

	/**
	 * checks the rule <pre> localname ::= onecharlocalname 
	 * 							| twocharlocalname | threeormorecharname </pre>
	 * @param localName string to be checked
	 * @return true if the localName is valid  
	 */
	private boolean localNameRule(String localName) {
		return (oneCharLocalNameRule(localName) || twoCharLocalNameRule(localName)
				|| threeOrMoreCharNameRule(localName));
	} 

	/**
	 * checks the rule <pre> onecharsimplename ::= (* Any unicode character except:
	 * 										  '.', '/', ':', '[', ']', '*', ''',
	 * 										  '"', '|' or any whitespace 
	 * 										  character *) </pre>
	 * @param oneCharSimpleName string to be checked
	 * @return true if the oneCharSimpleName is valid  
	 */
	private boolean oneCharSimpleNameRule(String oneCharSimpleName) {
		Matcher simpleNameMatcher = SIMPLE_NAME_PATTERN.matcher(oneCharSimpleName);
		return simpleNameMatcher.matches();
	}

	/**
	 * checks the rule <pre> twocharsimplename ::= '.' onecharsimplename 
	 * 								| onecharsimplename '.'
	 * 								| onecharsimplename onecharsimplename </pre>
	 * @param twoCharSimpleName string to be checked
	 * @return true if the twoCharSimpleName is valid  
	 */
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

	/**
	 * checks the rule <pre> onecharlocalname ::= nonspace </pre>
	 * @param oneCharLocalName string to be checked
	 * @return true if the oneCharLocalName is valid  
	 */
	private boolean oneCharLocalNameRule(String oneCharLocalName) {
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
	private boolean twoCharLocalNameRule(String twoCharLocalName) {
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
	 */
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

	/**
	 * checks the rule <pre> prefix ::= (*Any valid XML name*) </pre>
	 * @param prefix string to be checked
	 * @return true if the prefix is valid  
	 */
	private boolean prefixRule(String prefix) {
		if (prefix.length() > 2) {
			String startOfPrefix = prefix.substring(0, 3);
			if (startOfPrefix.equalsIgnoreCase("xml")) {
				return false;			
			}
		}
		Matcher xmlNamesMatcher = XML_NAMES.matcher(prefix);
		return xmlNamesMatcher.matches();
	}

	/**
	 * checks the rule <pre> string ::= char | string char </pre>
	 * @param stringString string to be checked
	 * @return true if the stringString is valid  
	 */
	private boolean stringRule(String stringString) {
		if (stringString.length() == 1) {
			return  charRule(stringString);
		}
		return (stringRule(stringString.substring(0, stringString.length() - 1)) 
					&& charRule(stringString.substring(stringString.length() - 1)));
	}

	/**
	 * checks the rule <pre> char ::= nonspace | ' ' </pre>
	 * @param character string to be checked
	 * @return true if the character is valid  
	 */
	private boolean charRule(String character) {
		return ((character.length() == 1) && 
				(nonSpaceRule(character) || character.equals(" ")));
	}

	/**
	 * checks the rule <pre> nonspace ::= (* Any Unicode character except:
	 * 								   '/', ':', '[', ']', '*', ''', '"', 
	 * 								   '|' or any whitespace character *) </pre>
	 * @param nonSpaceString string to be checked
	 * @return true if the nonSpaceString is valid  
	 */
	private boolean nonSpaceRule(String nonSpaceString) {
		Matcher nonspaceMatcher = NONSPACE.matcher(nonSpaceString);
		return (nonspaceMatcher.matches());
	}

}