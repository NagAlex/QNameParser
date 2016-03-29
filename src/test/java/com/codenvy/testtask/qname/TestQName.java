package com.codenvy.testtask.qname;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestQName {
	
	@Test
	public void testGetPrefix() throws IllegalNameException {
		String qname = "_param:Luis";
		QName qName = QName.parse(qname);
		assertEquals("prefix should be _param", "_param", qName.getPrefix());
	}

	@Test
	public void testGetLocalName() throws IllegalNameException {
		String qname = "ar7-me:A.G.n ag";
		QName qName = QName.parse(qname);
		assertEquals("localname should be A.G.n ag", "A.G.n ag", qName.getLocalName());
	}

	@Test
	public void testGetAsString() throws IllegalNameException {
		String qname = "_p7txd:na me.";
		QName qName = QName.parse(qname);
		assertEquals("String should be _p7txd:na me.", "_p7txd:na me.", qName.getAsString());
	}

	@Test
	public void testGetAsStringWithoutPrefix() throws IllegalNameException {
		String qname = "justAname";
		QName qName = QName.parse(qname);
		assertEquals("String should be justAname", "justAname", qName.getAsString());
	}

	@Test
	public void testParse() throws IllegalNameException {
		String qname = "prefix:name";
		QName qName = QName.parse(qname);
		assertNotNull("Object should not be null", qName);
	}
	
	@Test
	public void testParseUnicodNames() throws IllegalNameException {
		String qname = "justAn\u2785\u3370ame";
		QName qName = QName.parse(qname);
		assertEquals("String should be justAn\u2785\u3370ame", "justAn\u2785\u3370ame", qName.getAsString());
		
	}

	@Test(expected=IllegalNameException.class)
	public void testParseEmptyString() throws IllegalNameException{
		QName.parse("");
	}
	
	@Test(expected=IllegalNameException.class)
	public void testParseNullString() throws IllegalNameException{
		QName.parse(null);
	}
	
	@Test(expected=IllegalNameException.class)
	public void testParseColonString() throws IllegalNameException{
		QName.parse(":name");
	}
	
	@Test(expected=IllegalNameException.class)
	public void testParseDot() throws IllegalNameException{
		QName.parse(".");
	}
	
	@Test(expected=IllegalNameException.class)
	public void testParseDotDot() throws IllegalNameException{
		QName.parse("..");
	}
	
	@Test(expected=IllegalNameException.class)
	public void testParsePrefixColon() throws IllegalNameException{
		QName.parse("runner:");
	}

	@Test(expected=IllegalNameException.class)
	public void testParseSpaceFollowedString() throws IllegalNameException{
		QName.parse(" fghj");
	}
	
	@Test(expected=IllegalNameException.class)
	public void testParseSpaceBeforePrefix() throws IllegalNameException{
		QName.parse(" fghj:poiy");
	}
	
	@Test(expected=IllegalNameException.class)
	public void testParsePrefixSpaceName() throws IllegalNameException{
		QName.parse("rtp: poiy");
	}

	@Test(expected=IllegalNameException.class)
	public void testParsePrefixNameSpace() throws IllegalNameException{
		QName.parse("rtp:poiy ");
	}

	@Test(expected=IllegalNameException.class)
	public void testParseSpaceInPrefix() throws IllegalNameException{
		QName.parse("fg hj:poiy");
	}

	@Test(expected=IllegalNameException.class)
	public void testParsePrefixStartsWithXML() throws IllegalNameException{
		QName.parse("xmlfghj:poiy");
	}
	
	@Test(expected=IllegalNameException.class)
	public void testParseWrongSymbols() throws IllegalNameException{
		QName.parse("fghj]poiy");
	}

	@Test(expected=IllegalNameException.class)
	public void testParseWrongQuotes() throws IllegalNameException{
		QName.parse("fghj\"poiy");
	}
	
	@Test(expected=IllegalNameException.class)
	public void testParseMoreColons() throws IllegalNameException{
		QName.parse("fghj:p:oiy");
	}

}
