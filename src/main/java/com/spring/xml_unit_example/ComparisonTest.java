package com.spring.xml_unit_example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.xml.sax.SAXException;

public class ComparisonTest {

	public static void main(String[] args) throws FileNotFoundException {

		InputStreamReader isr1 = null;
		InputStreamReader isr2 = null;
		isr1 =new InputStreamReader(ComparisonTest.class.getClassLoader().getResourceAsStream("reference.xml"));
		isr2 = new InputStreamReader(ComparisonTest.class.getClassLoader().getResourceAsStream("comparison.xml"));

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