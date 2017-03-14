package com.spring.xml_unit_example;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class ParserTest {

	Parser p = null;
	Map<String, Map<String, String>> dataMap1 = null;
	Map<String, Map<String, String>> dataMap2 = null;

	@Before
	public void setup() throws XPathExpressionException, FileNotFoundException,
			UnsupportedEncodingException {
		p = new Parser();
		dataMap1 = p.getXmlToDataMap("file1.xml");
		dataMap2 = p.getXmlToDataMap("file2.xml");

	}

	@Test
	// data matching
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

		List<Integer> childNodeCounter1 = p.childNodeCount(dataMap1);
		List<Integer> childNodeCounter2 = p.childNodeCount(dataMap2);

		System.out
				.println("child node counts at each parent positions of file1.xml");
		for (int c : childNodeCounter1) {

			System.out.print(c + ",\t");
		}

		System.out
				.println("\nchild node counts at each parent positions of file2.xml");
		for (int c : childNodeCounter2) {

			System.out.print(c + ",\t");
		}
		System.out
				.println("\ncomparing childnode counts of 2 files respective to parent node psoitions");
		Assert.assertEquals(childNodeCounter1, childNodeCounter2);

	}

	@After
	public void tear() {
		p = null;
		dataMap1 = null;
		dataMap2 = null;
	}
}
