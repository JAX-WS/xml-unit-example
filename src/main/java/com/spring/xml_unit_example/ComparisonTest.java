package com.spring.xml_unit_example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ComparisonTest {

	public static void main(String[] args) throws FileNotFoundException,
			XPathExpressionException {

		ComparisonTest test = new ComparisonTest();
		test.validateNode();
		test.displayParentNodeCounts();
		test.displayChildNodes();

	}

	public void displayChildNodes() throws XPathExpressionException,
			FileNotFoundException {
		System.out.println("\n");
		int[] arr1 = this.countChildNodes("reference.xml");
		int[] arr2 = this.countChildNodes("comparison.xml");

		for (int i = 0; i < arr1.length; i++) {
			System.out.println("in reference.xml at node position: "+(i+1)+" child node count: "+arr1[i]);
		}
		System.out.println("\n");
		for (int i = 0; i < arr2.length; i++) {
			System.out.println("in comparison.xml at node position: "+(i+1)+" child node count: "+arr2[i]);
		}

	}

	public void displayParentNodeCounts() throws XPathExpressionException,
			FileNotFoundException {
		System.out.println("\n");
		int parentNode1 = (int) this.countParentNodes("reference.xml");
		int parentNode2 = (int) this.countParentNodes("comparison.xml");
		System.out.println("total parent node [book] in reference.xml: "
				+ parentNode1);
		System.out.println("total parent node [book] in comparison.xml: "
				+ parentNode2);
	}

	public int[] countChildNodes(String fileName)
			throws XPathExpressionException, FileNotFoundException {

		String countParentXpathExp = "count(//books/book)";
		XPath xp = XPathFactory.newInstance().newXPath();
		InputSource xml = new InputSource(this.getClass().getClassLoader()
				.getResourceAsStream(fileName));
		double parentCount = (Double) xp.evaluate(countParentXpathExp, xml,
				XPathConstants.NUMBER);

		int[] arr = new int[(int) parentCount];

		for (int i = 1; i <= parentCount; i++) {
			xml = new InputSource(this.getClass().getClassLoader()
					.getResourceAsStream(fileName));
			String countChildNodesXPathExp = "count(//books/book[" + i + "]/*)";
			double count = (Double) xp.evaluate(countChildNodesXPathExp, xml,
					XPathConstants.NUMBER);
			/*
			 * System.out.println("filename: " + fileName + " under the xpath: "
			 * + "//books/book[" + (int) i + "]" + " childNodes: " + count);
			 */
			arr[i - 1] = (int) count;
		}

		return arr;
	}

	public double countParentNodes(String fileName)
			throws XPathExpressionException, FileNotFoundException {

		String countXpathExp = "count(//books/book)";
		XPath xp = XPathFactory.newInstance().newXPath();
		InputSource xml = new InputSource(this.getClass().getClassLoader()
				.getResourceAsStream(fileName));
		double count = (Double) xp.evaluate(countXpathExp, xml,
				XPathConstants.NUMBER);

		return count;

	}

	public void validateNode() {
		InputStreamReader isr1 = null;
		InputStreamReader isr2 = null;
		isr1 = new InputStreamReader(ComparisonTest.class.getClassLoader()
				.getResourceAsStream("reference.xml"));
		isr2 = new InputStreamReader(ComparisonTest.class.getClassLoader()
				.getResourceAsStream("comparison.xml"));

		try {
			Diff diff = new Diff(isr1, isr2);
			System.out.println("Similar? " + diff.similar());
			System.out.println("Identical? " + diff.identical());

			DetailedDiff detDiff = new DetailedDiff(diff);
			List differences = detDiff.getAllDifferences();
			for (Object object : differences) {
				Difference difference = (Difference) object;
				System.out.println("***********************");
				System.out.println(difference);
				System.out.println("***********************");
			}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}