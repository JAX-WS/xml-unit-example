package com.spring.xml_unit_example;

import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import junit.framework.Assert;

import org.junit.Test;

import com.spring.xml_unit_example.Parser.Node;

@SuppressWarnings("deprecation")
public class ParserTest {

	@Test
	public void testGetXmlToDataMap() throws XPathExpressionException {
		Parser p = new Parser();

		Map<String, List<Node>> dataMap1 = p.getXmlToDataMap("file1.xml");
		Map<String, List<Node>> dataMap2 = p.getXmlToDataMap("file2.xml");

		Assert.assertEquals(dataMap1, dataMap2);
	}
}
