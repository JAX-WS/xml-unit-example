package com.xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.NamespaceContext;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Comparison {

	private static final String FILENAME = "C:\\XMLComparator\\test\\xmlcompare.txt";

	public static void main(String[] args) throws XpathException {

		FileReader fr1 = null;
		FileReader fr2 = null;
		try {
			fr1 = new FileReader("C:/XMLComparator/test/part-ngp.xml");
			fr2 = new FileReader("C:/XMLComparator/test/part-gforce.xml");
			PrintWriter writer = new PrintWriter(FILENAME, "UTF-8");

			
			
			Map<String, Integer> m1 = processParentElements(fr1);
			Map<String, Integer> m2 = processParentElements(fr2);
			
			if(m1.size() != m2.size()) {
				writer.println("There is diffrence in total number of elements in both the files :: Expected Number of elements " + m1.size() + " but Was " + m2.size());
				System.out.println("There is difference in total number of elements in both the files :: Expected Number of elements " + m1.size() + " but Was " + m2.size());
			}
			
			for (String key : m1.keySet()) {
				if(m1.get(key) != m2.get(key)) {
					writer.println("There is difference in number of "+ key +" element :: Expected " + m1.get(key) + " but Was " + m2.get(key));
					System.out.println("There is difference in number of "+ key +" element :: Expected " + m1.get(key) + " but Was " + m2.get(key));
				}
			}
			
			fr1 = new FileReader("C:/XMLComparator/test/part-ngp.xml");
			fr2 = new FileReader("C:/XMLComparator/test/part-gforce.xml");
			
			Map<String, Map<String,String>> m3 = processChildElements(fr1);
			
			Map<String, Map<String,String>> m4 = processChildElements(fr2);
			
			if(m3.size() > m4.size()) {
				System.out.println("********COMPARISION********* ");
				writer.println("********COMPARISION*********");
				for (String key : m3.keySet()) {
					System.out.println("**********First file Parent Element " + key + " has " + m3.get(key).size() + " child elements. While Second file Parent element "+ key + " has "+ ((m4.get(key)!=null)?m4.get(key).size():0) + " child elements");
					writer.println("**********First file Parent Element " + key + " has " + m3.get(key).size() + " child elements. While Second file Parent element "+ key + " has "+ ((m4.get(key)!=null)?m4.get(key).size():0) + " child elements");
					System.out.println("**********Parent Element " + key + " has below child values");
					writer.println("**********Parent Element " + key + " has below child values");
					Map<String, String> childMap1 = m3.get(key);
					Map<String, String> childMap2 = m4.get(key);
					if(childMap2 == null) {
						childMap2 = new HashMap<String, String>();
					}
					if(childMap1.size() > childMap2.size()){
						for (String childkey : childMap1.keySet()) {
							System.out.println(childkey + " in 1st file is :: " + childMap1.get(childkey)+ " in 2nd file is :: " + childMap2.get(childkey));
							writer.println(childkey + " in 1st file is :: " + childMap1.get(childkey)+ " in 2nd file is :: " + childMap2.get(childkey));
						}
					} else {
						for (String childkey : childMap2.keySet()) {
							System.out.println(childkey + " in 1st file is :: " + childMap1.get(childkey)+ " in 2nd file is :: " + childMap2.get(childkey));
							writer.println(childkey + " in 1st file is :: " + childMap1.get(childkey)+ " in 2nd file is :: " + childMap2.get(childkey));
						}
					}
					
					
				}
			} else {
				System.out.println("********COMPARISION********* ");
				writer.println("********COMPARISION*********");
				for (String key : m4.keySet()) {
					System.out.println("**********First file Parent Element " + key + " has " + m4.get(key).size() + " child elements. While Second file Parent element "+ key + " has "+ ((m3.get(key)!=null)?m3.get(key).size():0) + " child elements");
					writer.println("**********First file Parent Element " + key + " has " + m4.get(key).size() + " child elements. While Second file Parent element "+ key + " has "+ ((m3.get(key)!=null)?m3.get(key).size():0) + " child elements");
					System.out.println("**********Parent Element " + key + " has below child values");
					writer.println("**********Parent Element " + key + " has below child values");
					Map<String, String> childMap1 = m4.get(key);
					Map<String, String> childMap2 = m3.get(key);
					if(childMap2 == null) {
						childMap2 = new HashMap<String, String>();
					}
					if(childMap1.size() > childMap2.size()){
						for (String childkey : childMap1.keySet()) {
							System.out.println(childkey + " in 1st file is :: " + childMap1.get(childkey)+ " in 2nd file is :: " + childMap2.get(childkey));
							writer.println(childkey + " in 1st file is :: " + childMap1.get(childkey)+ " in 2nd file is :: " + childMap2.get(childkey));
						}
					} else {
						for (String childkey : childMap2.keySet()) {
							System.out.println(childkey + " in 1st file is :: " + childMap1.get(childkey)+ " in 2nd file is :: " + childMap2.get(childkey));
							writer.println(childkey + " in 1st file is :: " + childMap1.get(childkey)+ " in 2nd file is :: " + childMap2.get(childkey));
						}
					}
					
				}
			}
			
			
			
			fr1 = new FileReader("C:/XMLComparator/test/part-ngp.xml");
			fr2 = new FileReader("C:/XMLComparator/test/part-gforce.xml");
			Diff diff = new Diff(fr1, fr2);
			
			
			writer.println("Similar? " + diff.similar());
			writer.println("Identical? " + diff.identical());
			
			if (diff.identical()) {
				writer.println("Both XML are identical");
			}

			DetailedDiff detDiff = new DetailedDiff(diff);
			
			List differences = detDiff.getAllDifferences();
			int count=1;
			for (Object object : differences) {
				
				
				Difference difference = (Difference)object;
				writer.println(count+"#Difference");
				writer.println("____________________________________________________________________________");
				writer.println(difference.toString());
				writer.println("****************************************************************************");
				count++;
				
			}
			writer.close();
			
			
			
			

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Integer> processParentElements(FileReader fr) throws SAXException, IOException, XpathException {
		FileReader reader = fr;
		Document d = XMLUnit.buildControlDocument(readFile(reader)); 
		Map<String, Integer> m = new HashMap<String, Integer>(); 
		NamespaceContext ctx = new SimpleNamespaceContext(m);
		XpathEngine engine = XMLUnit.newXpathEngine(); 
		engine.setNamespaceContext(ctx);
		NodeList l = engine.getMatchingNodes("//*", d); 
		
		
		for(int i=0;i<l.getLength();i++) {
			NodeList c = engine.getMatchingNodes("//"+l.item(i).getNodeName(), d); 
			
			if (m.get(l.item(i).getNodeName()) == null && c.item(0).getChildNodes().getLength() > 1) {
				m.put(l.item(i).getNodeName(),(Integer) c.getLength());
				
			}	
		}
		
		return m;
	}
	
	public static Map<String, Map<String,String>> processChildElements(FileReader fr) throws SAXException, IOException, XpathException {
		FileReader reader = fr;
		Document d = XMLUnit.buildControlDocument(readFile(reader)); 
		Map<String, Map<String,String>> m = new HashMap<String, Map<String,String>>(); 
		NamespaceContext ctx = new SimpleNamespaceContext(m);
		XpathEngine engine = XMLUnit.newXpathEngine(); 
		engine.setNamespaceContext(ctx);
		NodeList l = engine.getMatchingNodes("//*", d); 
		
		
		for(int i=1;i<l.getLength()-1;i++) {
			NodeList c = engine.getMatchingNodes("//"+l.item(i).getNodeName(), d); 
			
			for(int j=0;j<c.getLength();j++) {
				if (c.item(j).getChildNodes().getLength() > 1) {
					Map<String, String> childValues = new HashMap<String, String>(); 
					NodeList cn = c.item(j).getChildNodes();
					
					for(int k=0;k<cn.getLength();k++) {
						childValues.put(cn.item(k).getNodeName()+"_", cn.item(k).getTextContent());
					}
					
					m.put(c.item(j).getNodeName()+"_"+j,childValues);
				}
			}
				
		}
		
		return m;
	}
	private static String readFile(FileReader fr1) throws IOException {
	    BufferedReader reader = new BufferedReader(fr1);
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	

}