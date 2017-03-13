package com.spring.xml_unit_example;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spring.xml_unit_example.Parser.Node;

@SuppressWarnings("deprecation")
public class ParserTest {

	Parser p = null;
	Map<String, Map<String,String>> dataMap1 = null;
	Map<String, Map<String,String>> dataMap2 = null;

	@Before
	public void setup() throws XPathExpressionException {
		p = new Parser();
		dataMap1 = p.getXmlToDataMap("file1.xml");
		dataMap2 = p.getXmlToDataMap("file2.xml");

	}

	@Test
	//  data matching
	public void testGetXmlToDataMap() {

		Assert.assertEquals(dataMap1, dataMap2);
	}

	@Test
	// parent code count comparison
	public void testParentNodeCount() {

		Assert.assertEquals(dataMap1.size(), dataMap2.size());
	}

	@Test
	// childnode count comparison
	public void testChildNodeCount() {

		Set<String> keySet1 = dataMap1.keySet();
		Set<String> keySet2 = dataMap2.keySet();

		int childNodeSum1 = 0;
		for (String s : keySet1) {
			childNodeSum1 = +dataMap1.get(s).size();
		}

		int childNodeSum2 = 0;
		for (String s : keySet2) {
			childNodeSum2 = +dataMap2.get(s).size();
		}

		Assert.assertEquals(childNodeSum1, childNodeSum2);
	}

	@After
	public void tear() {
		p = null;
		dataMap1 = null;
		dataMap2 = null;
	}
}
