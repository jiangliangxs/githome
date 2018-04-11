package test.xml.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLHandler2 {
	public void createXML() throws FileNotFoundException {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			doc = docBuilder.newDocument();			
						
			Element country = doc.createElement("country");
			
			doc.appendChild(country);

			country.appendChild(doc.createTextNode("\n    "));
			
			Element china = doc.createElement("china");			
			country.appendChild(china);

			china.appendChild(doc.createTextNode("\n        "));
			
			Element city = doc.createElement("city");
			city.appendChild(doc.createTextNode("Beijing"));
			china.appendChild(city);
			
			china.appendChild(doc.createTextNode("\n        "));
			
			city = doc.createElement("city");
			city.appendChild(doc.createTextNode("Shanghai"));
			china.appendChild(city);

			china.appendChild(doc.createTextNode("\n    "));
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			File file = new File("E://cities.xml");
			FileOutputStream out = new FileOutputStream(file);
			StreamResult xmlResult = new StreamResult(System.out);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), xmlResult);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String args[]) {
		XMLHandler2 xh = new XMLHandler2();
		try {
			xh.createXML();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}