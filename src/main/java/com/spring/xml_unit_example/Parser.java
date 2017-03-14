package com.spring.xml_unit_example;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Parser {

	Map<String, Map<String, String>> dataMap1 = null;
	// System.out.println(dataMap1);
	Map<String, Map<String, String>> dataMap2 = null;
	PrintWriter writer = null;

	public Parser() throws XPathExpressionException, FileNotFoundException,
			UnsupportedEncodingException {
		super();
		// TODO Auto-generated constructor stub
		dataMap1 = getXmlToDataMap("file1.xml");
		dataMap2 = getXmlToDataMap("file2.xml");
		writer = new PrintWriter(String.valueOf("compare_"
				+ System.currentTimeMillis())
				+ ".txt", "UTF-8");
	}

	public static void main(String[] args) throws XPathExpressionException,
			FileNotFoundException, UnsupportedEncodingException {

		Parser t = new Parser();

		System.out.println("total parent nodes in file1.xml: "
				+ t.dataMap1.size());
		t.writeToAFile("total parent nodes in file1.xml: " + t.dataMap1.size());
		System.out.println("total parent nodes in file2.xml: "
				+ t.dataMap2.size());
		t.writeToAFile("\ntotal parent nodes in file2.xml: "
				+ t.dataMap2.size());
		int nodePos = 1;
		if (t.dataMap1.size() > t.dataMap2.size()) {
			for (Map.Entry<String, Map<String, String>> entry : t.dataMap1
					.entrySet()) {
				String key1 = entry.getKey();
				Map<String, String> list1 = entry.getValue();
				Map<String, String> list2 = t.dataMap2.get(key1);
				System.out.println("\n*******************************");
				t.writeToAFile("\n*******************************");
				System.out.println("For Parent node at //books/book[" + nodePos
						+ "]");
				t.writeToAFile("\nFor Parent node at //books/book[" + nodePos
						+ "]");
				System.out.println("****************************\n");
				t.writeToAFile("\n****************************\n");
				nodePos++;
				if (list2 == null) {
					System.out.println("Total child nodes in file1: "
							+ list1.size());
					t.writeToAFile("\nTotal child nodes in file1: "
							+ list1.size());
					System.out.println("Total child nodes in file2: " + "0");
					t.writeToAFile("\nTotal child nodes in file2: " + "0");
					t.displaySingleList(list1);
				} else {
					t.displayList(list1, list2);
				}
			}
		} else {

			for (Map.Entry<String, Map<String, String>> entry : t.dataMap2
					.entrySet()) {
				String key2 = entry.getKey();
				Map<String, String> list2 = entry.getValue();
				Map<String, String> list1 = t.dataMap1.get(key2);

				System.out.println("\n*******************************");
				t.writeToAFile("\n*******************************");
				System.out.println("For Parent node at //books/book[" + nodePos
						+ "]");
				t.writeToAFile("\nFor Parent node at //books/book[" + nodePos
						+ "]");
				nodePos++;
				if (list1 == null) {
					System.out.println("Total child nodes in file1: " + "0");
					t.writeToAFile("\nTotal child nodes in file1: " + "0");
					System.out.println("Total child nodes in file2: "
							+ list2.size());
					t.writeToAFile("\nTotal child nodes in file2: "
							+ list2.size());
					t.displaySingleList(list2);
				} else {
					t.displayList(list1, list2);
				}
			}

			t.writer.close();
		}
	}

	public Map<String, Map<String, String>> getXmlToDataMap(String fileName)
			throws XPathExpressionException {
		Map<String, Map<String, String>> dataMap = new LinkedHashMap<String, Map<String, String>>();
		for (int i = 1;; i++) {
			Map<String, String> childNodes = this.displayNode(fileName, i);
			if (childNodes == null) {
				break;
			} else {
				dataMap.put(String.valueOf(i), childNodes);
			}
		}
		return dataMap;
	}

	public void displaySingleList(Map<String, String> list) {
		Set<String> keys = list.keySet();
		for (String key : keys) {
			System.out.println("\n");
			this.writeToAFile("\n");
			
			System.out.print("in file1: [nodeName,nodeValue]=" + "[" + key
					+ "," + list.get(key) + "]");
			this.writeToAFile("in file1: [nodeName,nodeValue]=" + "[" + key
					+ "," + list.get(key) + "]");
		}
	}

	public void displayList(Map<String, String> list1, Map<String, String> list2) {
		System.out.println("Total child nodes in file1: " + list1.size());
		this.writeToAFile("\nTotal child nodes in file1: " + list1.size());
		System.out.println("Total child nodes in file2: " + list2.size());
		this.writeToAFile("\nTotal child nodes in file2: " + list2.size());

		Set<String> keys1 = list1.keySet();
		Set<String> keys2 = list2.keySet();
		if (keys1.size() > keys2.size()) {
			for (String key : keys1) {
				System.out.println("\n");
				this.writeToAFile("\n");
				System.out.print("in file1: [nodeName,nodeValue]=" + "[" + key
						+ "," + list1.get(key) + "]");
				this.writeToAFile("in file1: [nodeName,nodeValue]=" + "[" + key
						+ "," + list1.get(key) + "]");

				try {
					System.out.print(",\tin file2: [nodeName,nodeValue]=" + "["
							+ key + "," + list2.get(key) + "]");
					this.writeToAFile(",\tin file2: [nodeName,nodeValue]="
							+ "[" + key + "," + list2.get(key) + "]");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.print(",\tin file2: No node found");
					this.writeToAFile(",\tin file2: No node found");
				}
			}
		} else {
			for (String key : keys2) {
				System.out.println("\n");
				this.writeToAFile("\n");
				try {
					System.out.print("in file1: [nodeName,nodeValue]=" + "["
							+ key + "," + list1.get(key) + "]");
					this.writeToAFile("in file1: [nodeName,nodeValue]=" + "["
							+ key + "," + list1.get(key) + "]");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.print("in file1: No node found");
					this.writeToAFile("in file1: No node found");
				}

				System.out.print(",\tin file2: [nodeName,nodeValue]=" + "["
						+ key + "," + list2.get(key) + "]");
				this.writeToAFile(",\tin file2: [nodeName,nodeValue]=" + "["
						+ key + "," + list2.get(key) + "]");
			}
		}
	}

	public Map<String, String> displayNode(String fileName, int pos)
			throws XPathExpressionException {

		NodeList elements = null;
		Map<String, String> childNodes = new LinkedHashMap<String, String>();

		String childNodeExpression = "/books/book[" + (pos) + "]/*";
		String parentNodeExpression = "/books/book[" + (pos) + "]";
		XPath xPath = XPathFactory.newInstance().newXPath();
		InputSource xml1 = new InputSource(this.getClass().getClassLoader()
				.getResourceAsStream(fileName));
		InputSource xml2 = new InputSource(this.getClass().getClassLoader()
				.getResourceAsStream(fileName));
		// System.out.println("retrieving child nodes for: " + expression);

		Node node = (Node) xPath.evaluate(parentNodeExpression, xml1,
				XPathConstants.NODE);
		if (node == null) {
			return null;
		}
		
		elements = (NodeList) xPath.compile(childNodeExpression).evaluate(xml2,
				XPathConstants.NODESET);
		
		if (elements.getLength() == 0) {
			return childNodes;
		}
		for (int i = 0; i < elements.getLength(); i++) {
			// System.out.println(elements.item(i).getNodeName() + ":" +
			// elements.item(i).getTextContent());
			childNodes.put(elements.item(i).getNodeName(), elements.item(i)
					.getTextContent());

		}

		return childNodes;
	}

	public void writeToAFile(String str) {
		writer.write(str);
	}
	
	
	public List<Integer> childNodeCount(Map<String, Map<String, String>> dataMap) {
		List<Integer> nodeCounter = null;
		nodeCounter = new ArrayList<Integer>();
		for (Map.Entry<String, Map<String, String>> entry : dataMap.entrySet()) {
			nodeCounter.add(entry.getValue().size());
		}
		return nodeCounter;
	}

}