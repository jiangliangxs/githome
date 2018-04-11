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

public class XMLHandler {
	public void createXML() throws FileNotFoundException {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			doc = docBuilder.newDocument();
			Element root = doc.createElement("root");
			doc.appendChild(root);

			Element country = doc.createElement("contry");
			country.appendChild(doc.createTextNode("China"));
			root.appendChild(country);

			Element city = doc.createElement("city");
			city.appendChild(doc.createTextNode("Beijing"));
			country.appendChild(city);

			city = doc.createElement("city");
			city.appendChild(doc.createTextNode("Shanghai"));
			country.appendChild(city);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			//换行
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			File file = new File("E://cities.xml");
			FileOutputStream out = new FileOutputStream(file);
			StreamResult xmlResult = new StreamResult(System.out);
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
		XMLHandler xh = new XMLHandler();
		try {
			xh.createXML();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
