package test.xml.other;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class TestDom {
	@Test
	public void m7() throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("a", "a1");
		m.put("b", "a2");
		String s = maptoXml(m);
		System.out.println(s);
	}

	/**
	 * map to xml xml <node><key label="key1">value1</key><key label="key2">value2</key>......</node>
	 * 
	 * @param map
	 * @return
	 */
	public static String maptoXml(Map<String, Object> map) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("root");
		for (Object obj : map.keySet()) {
			Element keyElement = nodeElement.addElement("key");
			keyElement.addAttribute("label", String.valueOf(obj));
			keyElement.setText(String.valueOf(map.get(obj)));
		}
		return doc2String(document);
	}


	/**
	 * 
	 * @param document
	 * @return
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("   ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			s = out.toString("UTF-8");
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}
}
