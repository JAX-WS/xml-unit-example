package com.xml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ComparisonTest {

	Map<String, Integer> m1 = null;
	Map<String, Integer> m2 = null;
	Comparison c = null;
	Map<String, Map<String, String>> dataMap1 = null;
	Map<String, Map<String, String>> dataMap2 = null;

	@Before
	public void setUp() throws XpathException, FileNotFoundException,
			SAXException, IOException {
		String path1 = "D:\\rpatra\\tech\\workspace\\projects\\spring-dev\\xml-unit-example\\src\\main\\resources\\file1.xml";
		String path2 = "D:\\rpatra\\tech\\workspace\\projects\\spring-dev\\xml-unit-example\\src\\main\\resources\\file2.xml";
		c = new Comparison();
		m1 = c.processParentElements(new FileReader(path1));
		m2 = c.processParentElements(new FileReader(path2));
		dataMap1 = c.processChildElements(new FileReader(path1));
		dataMap2 = c.processChildElements(new FileReader(path2));
	}

	@Test
	public void countParentNodes() {
		Assert.assertEquals(m1.size(), m2.size());
	}

	@Test
	public void countChildNodeForEachParent() {
		List<Map<String, String>> arrList1 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> arrList2 = new ArrayList<Map<String, String>>();

		for (Map.Entry<String, Map<String, String>> entry : dataMap1.entrySet()) {
			arrList1.add(entry.getValue());
		}

		for (Map.Entry<String, Map<String, String>> entry : dataMap2.entrySet()) {
			arrList2.add(entry.getValue());
		}

		Assert.assertEquals(arrList1, arrList2);
	}

}
