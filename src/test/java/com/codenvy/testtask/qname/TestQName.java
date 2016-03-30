package com.codenvy.testtask.qname;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestQName {
	static QName qName = null;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		qName = new QName();
	}
	
	@Test
	public void testGetPrefix() throws IllegalNameException {
		String qname = "_param:Luis";
		qName = QName.parse(qname);
		assertEquals("prefix should be _param", "_param", qName.getPrefix());
	}

	@Test
	public void testGetLocalName() throws IllegalNameException {
		String qname = "ar7-me:A.G.n ag";
		qName = QName.parse(qname);
		assertEquals("localname should be A.G.n ag", "A.G.n ag", qName.getLocalName());
	}

	@Test
	public void testGetAsString() throws IllegalNameException {
		String qname = "_p7txd:na me.";
		qName = QName.parse(qname);
		assertEquals("String should be _p7txd:na me.", "_p7txd:na me.", qName.getAsString());
	}

	@Test
	public void testGetAsStringWithoutPrefix() throws IllegalNameException {
		String qname = "justAname";
		qName = QName.parse(qname);
		assertEquals("String should be justAname", "justAname", qName.getAsString());
	}

	@Test
	public void testParse() throws IllegalNameException {
		String qname = "prefix:name";
		qName = QName.parse(qname);
		assertNotNull("Object should not be null", qName);
	}
	
	@Test
	public void testParseUnicodNames() throws IllegalNameException {
		String qname = "justAn\u2785\u3370ame";
		qName = QName.parse(qname);
		assertEquals("String should be justAn\u2785\u3370ame", "justAn\u2785\u3370ame", qName.getAsString());
		
	}

	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseEmptyString() throws IllegalNameException{
		qName.parse("");
	}
	
	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseNullString() throws IllegalNameException{
		qName.parse(null);
	}
	
	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseColonString() throws IllegalNameException{
		qName.parse(":name");
	}
	
	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseDot() throws IllegalNameException{
		qName.parse(".");
	}
	
	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseDotDot() throws IllegalNameException{
		qName.parse("..");
	}
	
	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParsePrefixColon() throws IllegalNameException{
		qName.parse("runner:");
	}

	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseSpaceFollowedString() throws IllegalNameException{
		qName.parse(" fghj");
	}
	
	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseSpaceBeforePrefix() throws IllegalNameException{
		qName.parse(" fghj:poiy");
	}
	
	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParsePrefixSpaceName() throws IllegalNameException{
		qName.parse("rtp: poiy");
	}

	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParsePrefixNameSpace() throws IllegalNameException{
		qName.parse("rtp:poiy ");
	}

	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseSpaceInPrefix() throws IllegalNameException{
		qName.parse("fg hj:poiy");
	}

	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParsePrefixStartsWithXML() throws IllegalNameException{
		qName.parse("xmlfghj:poiy");
	}
	
	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseWrongSymbols() throws IllegalNameException{
		qName.parse("fghj]poiy");
	}

	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseWrongQuotes() throws IllegalNameException{
		qName.parse("fghj\"poiy");
	}
	
	@SuppressWarnings("static-access")
	@Test(expected=IllegalNameException.class)
	public void testParseMoreColons() throws IllegalNameException{
		qName.parse("fghj:p:oiy");
	}

}
