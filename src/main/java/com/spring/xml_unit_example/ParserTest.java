package com.spring.xml_unit_example;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ParserTest {
	static Map<String, List<Node>> dataMap = null;

	public static void main(String[] args) throws XPathExpressionException {

		ParserTest t = new ParserTest();
		dataMap = new LinkedHashMap<String, List<Node>>();
		Map<String, List<Node>> dataMap1 = t.displayNode("file1.xml", 0, dataMap);
		// System.out.println(dataMap1);

		dataMap = new LinkedHashMap<String, List<Node>>();
		Map<String, List<Node>> dataMap2 = t.displayNode("file2.xml", 0, dataMap);
		// System.out.println(dataMap2);

		System.out.println("total parent nodes in file1.xml: " + dataMap1.size());
		System.out.println("total parent nodes in file2.xml: " + dataMap2.size());

		int nodePos = 1;
		if (dataMap1.size() > dataMap2.size()) {
			for (Map.Entry<String, List<Node>> entry : dataMap1.entrySet()) {
				String key1 = entry.getKey();
				List<Node> list1 = entry.getValue();
				List<Node> list2 = dataMap2.get(key1);
				System.out.println("\n*******************************");
				System.out.println("For Parent node at //books/book[" + nodePos + "]");
				System.out.println("****************************\n");
				nodePos++;
				if (list2 == null) {
					System.out.println("Total child nodes in file1: " + list1.size());
					System.out.println("Total child nodes in file2: " + "0");
					t.displaySingleList(list1);
				} else {
					t.displayList(list1, list2);
				}
			}
		} else {
			System.out.println(dataMap1.size());
			for (Map.Entry<String, List<Node>> entry : dataMap2.entrySet()) {
				String key2 = entry.getKey();
				List<Node> list2 = entry.getValue();
				List<Node> list1 = dataMap1.get(key2);

				System.out.println("\n*******************************");
				System.out.println("For Parent node at //books/book[" + nodePos + "]");
				nodePos++;
				if (list1 == null) {
					System.out.println("Total child nodes in file1: " + "0");
					System.out.println("Total child nodes in file2: " + list2.size());
					t.displaySingleList(list2);
				} else {
					t.displayList(list1, list2);
				}
			}
		}
	}

	public void displaySingleList(List<Node> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("\n");
			System.out.print("in file1: [nodeName,nodeValue]=" + list.get(i));
		}
	}

	public void displayList(List<Node> list1, List<Node> list2) {
		System.out.println("Total child nodes in file1: " + list1.size());
		System.out.println("Total child nodes in file2: " + list2.size());
		if (list1.size() > list2.size()) {
			for (int i = 0; i < list1.size(); i++) {
				System.out.println("\n");
				System.out.print("in file1: [nodeName,nodeValue]=" + list1.get(i));

				try {
					System.out.print(",\tin file2: [nodeName,nodeValue]=" + list2.get(i));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.print(",\tin file2: No node found");
				}
			}
		} else {
			for (int i = 0; i < list2.size(); i++) {
				System.out.println("\n");

				try {
					System.out.print("in file1: [nodeName,nodeValue]=" + list1.get(i));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.print("in file1: No node found");
				}

				System.out.print(",\tin file2: [nodeName,nodeValue]=" + list2.get(i));

			}
		}
	}

	public Map<String, List<Node>> displayNode(String fileName, int pos, Map<String, List<Node>> dataMap)
			throws XPathExpressionException {

		NodeList elements = null;
		pos = pos + 1;
		String expression = "/books/book[" + (pos) + "]/*";
		XPath xPath = XPathFactory.newInstance().newXPath();
		InputSource xml = new InputSource(this.getClass().getClassLoader().getResourceAsStream(fileName));
		// System.out.println("retrieving child nodes for: " + expression);
		elements = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);
		if (elements.getLength() == 0) {
			return dataMap;
		}
		List<Node> childNodes = new ArrayList<Node>();
		for (int i = 0; i < elements.getLength(); i++) {
			// System.out.println(elements.item(i).getNodeName() + ":" +
			// elements.item(i).getTextContent());
			childNodes.add(new Node(elements.item(i).getNodeName(), elements.item(i).getTextContent()));

		}
		dataMap.put(String.valueOf(pos), childNodes);
		displayNode(fileName, pos, dataMap);
		return dataMap;
	}

	class Node {
		String nodeName;
		String nodeValue;

		public Node(String nodeName, String nodeValue) {
			super();
			this.nodeName = nodeName;
			this.nodeValue = nodeValue;
		}

		public Node() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Node [nodeName=" + nodeName + ", nodeValue=" + nodeValue + "]";
		}

	}
}
