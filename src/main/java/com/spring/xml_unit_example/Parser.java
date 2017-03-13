package com.spring.xml_unit_example;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Parser {
	static Map<String, List<Node>> dataMap = null;

	static PrintWriter writer = null;
	static {
		try {
			writer = new PrintWriter(String.valueOf("compare_"
					+ System.currentTimeMillis())
					+ ".txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws XPathExpressionException {

		Parser t = new Parser();
		dataMap = new LinkedHashMap<String, List<Node>>();
		Map<String, Map<String, String>> dataMap1 = t
				.getXmlToDataMap("file1.xml");
		// System.out.println(dataMap1);
		Map<String, Map<String, String>> dataMap2 = t
				.getXmlToDataMap("file2.xml");

		System.out.println("total parent nodes in file1.xml: "
				+ dataMap1.size());
		t.writeToAFile("total parent nodes in file1.xml: " + dataMap1.size());
		System.out.println("total parent nodes in file2.xml: "
				+ dataMap2.size());
		t.writeToAFile("\ntotal parent nodes in file2.xml: " + dataMap2.size());
		int nodePos = 1;
		if (dataMap1.size() > dataMap2.size()) {
			for (Map.Entry<String, Map<String, String>> entry : dataMap1
					.entrySet()) {
				String key1 = entry.getKey();
				Map<String, String> list1 = entry.getValue();
				Map<String, String> list2 = dataMap2.get(key1);
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
			System.out.println(dataMap1.size());
			for (Map.Entry<String, Map<String, String>> entry : dataMap2
					.entrySet()) {
				String key2 = entry.getKey();
				Map<String, String> list2 = entry.getValue();
				Map<String, String> list1 = dataMap1.get(key2);

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

			writer.close();
		}
	}

	public Map<String, Map<String, String>> getXmlToDataMap(String fileName)
			throws XPathExpressionException {
		Map<String, Map<String, String>> dataMap = new LinkedHashMap<String, Map<String, String>>();
		for (int i = 1;; i++) {
			Map<String, String> childNodes = this.displayNode(fileName, i);
			if (childNodes.size() == 0) {
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

		String expression = "/books/book[" + (pos) + "]/*";
		XPath xPath = XPathFactory.newInstance().newXPath();
		InputSource xml = new InputSource(this.getClass().getClassLoader()
				.getResourceAsStream(fileName));
		// System.out.println("retrieving child nodes for: " + expression);
		elements = (NodeList) xPath.compile(expression).evaluate(xml,
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
			return "Node [nodeName=" + nodeName + ", nodeValue=" + nodeValue
					+ "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((nodeName == null) ? 0 : nodeName.hashCode());
			result = prime * result
					+ ((nodeValue == null) ? 0 : nodeValue.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (nodeName == null) {
				if (other.nodeName != null)
					return false;
			} else if (!nodeName.equals(other.nodeName))
				return false;
			if (nodeValue == null) {
				if (other.nodeValue != null)
					return false;
			} else if (!nodeValue.equals(other.nodeValue))
				return false;
			return true;
		}

		private Parser getOuterType() {
			return Parser.this;
		}

	}
}