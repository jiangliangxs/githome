package test.xml.other;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.EventFilter;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestStax {

	@Test
	public void test08() {
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("./stax/books2.xml");
			// 创建文档处理对象
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// 通过DocumentBuilder创建doc的文档对象
			Document doc = db.parse(is);
			// 创建XPath
			XPath xpath = XPathFactory.newInstance().newXPath();
			Transformer tran = TransformerFactory.newInstance().newTransformer();
			tran.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tran.setOutputProperty(OutputKeys.INDENT, "yes");
			// 第一个参数就是xpath,第二参数就是文档
			NodeList list = (NodeList) xpath.evaluate("//book[title='Learning XML']", doc, XPathConstants.NODESET);
			// 获取price节点
			Element be = (Element) list.item(0);
			Element e = (Element) (be.getElementsByTagName("price").item(0));
			System.out.println("-------------------------" + e.getTextContent());
			e.setTextContent("333.9");
			Result result = new StreamResult(System.out);
			// 通过tranformer修改节点
			tran.transform(new DOMSource(doc), result);
			// tran.transform(new DOMSource(e), result);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test07_1() {
		Map<String, Object> m = new HashMap<String, Object>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("c1", "1c1");
		m2.put("c2", "1c2");
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("d1", "1d1");
		m3.put("d2", "1d2");
		m2.put("e1", m3);
		m.put("b1", m2);
		m.put("a1", "1a1");
		m.put("a2", "1a2");
		String ss = map2xml(m);
		System.out.println("XML:\n" + ss);
	}

	public String map2xml(Map<String, Object> m) {
		try {
			StringWriter sw = new StringWriter();
			XMLStreamWriter xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
			Map<String, Boolean> isEndMap = new HashMap<String, Boolean>();
			for (String key : m.keySet()) {
				Object value = m.get(key);
				callMapKeyValue(xsw, key, value, isEndMap);
			}
			String xml = sw.getBuffer().toString();
			xsw.flush();
			xsw.close();
			sw.flush();
			sw.close();
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public void callMapKeyValue(XMLStreamWriter xsw, String key, Object value, Map<String, Boolean> isEndMap) throws Exception {
		if (value instanceof Map) {
			xsw.writeStartElement(key);
			isEndMap.put(key, false);
			Map<String, Object> mapNew = (Map<String, Object>) value;
			for (String keyNew : mapNew.keySet()) {
				Object valueNew = mapNew.get(keyNew);
				callMapKeyValue(xsw, keyNew, valueNew, isEndMap);
			}
			xsw.writeEndElement();
		} else {
			xsw.writeStartElement(key);
			xsw.writeCharacters(value == null ? "" : "" + value);
			xsw.writeEndElement();
		}
	}

	@Test
	public void test07() {
		try {
			XMLStreamWriter xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
			xsw.writeStartDocument("UTF-8", "1.0");
			xsw.writeEndDocument();
			xsw.writeComment("命名空间");
			String ns = "http://11:dd";
			xsw.writeStartElement("nsadfsadf", "person", ns);
			// 有顺序(writeNamespace在writeStartElement之后)
			xsw.writeNamespace("nsadfsadf", ns);
			xsw.writeStartElement(ns, "id");
			xsw.writeCharacters("1");
			xsw.writeEndElement();
			xsw.writeEndElement();

			xsw.flush();
			xsw.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test06() {
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("./stax/books2.xml");
			// 创建文档处理对象
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// 通过DocumentBuilder创建doc的文档对象
			Document doc = db.parse(is);
			// 创建XPath
			XPath xpath = XPathFactory.newInstance().newXPath();
			// 第一个参数就是xpath,第二参数就是文档
			NodeList list = (NodeList) xpath.evaluate("//book[@category='WEB']", doc, XPathConstants.NODESET);
			for (int i = 0; i < list.getLength(); i++) {
				// 遍历输出相应的结果
				Element e = (Element) list.item(i);
				System.out.println(e.getElementsByTagName("title").item(0).getTextContent());
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test05() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("./stax/books.xml");
			// 基于Filter的过滤方式，可以有效的过滤掉不用进行操作的节点，效率会高一些
			XMLEventReader reader = factory.createFilteredReader(factory.createXMLEventReader(is), new EventFilter() {
				@Override
				public boolean accept(XMLEvent event) {
					// 返回true表示会显示，返回false表示不显示
					if (event.isStartElement()) {
						String name = event.asStartElement().getName().toString();
						if (name.equals("title") || name.equals("price"))
							return true;
					}
					return false;
				}
			});
			int num = 0;
			while (reader.hasNext()) {
				// 通过XMLEvent来获取是否是某种节点类型
				XMLEvent event = reader.nextEvent();
				if (event.isStartElement()) {
					// 通过event.asxxx转换节点
					String name = event.asStartElement().getName().toString();
					if (name.equals("title")) {
						System.out.print(reader.getElementText() + ":");
					}
					if (name.equals("price")) {
						System.out.print(reader.getElementText() + "\n");
					}
				}
				num++;
			}
			System.out.println(num);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test04() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("./stax/books.xml");
			// 基于迭代模型的操作方式
			XMLEventReader reader = factory.createXMLEventReader(is);
			int num = 0;
			while (reader.hasNext()) {
				// 通过XMLEvent来获取是否是某种节点类型
				XMLEvent event = reader.nextEvent();
				if (event.isStartElement()) {
					// 通过event.asxxx转换节点
					String name = event.asStartElement().getName().toString();
					if (name.equals("title")) {
						System.out.print(reader.getElementText() + ":");
					}
					if (name.equals("price")) {
						System.out.print(reader.getElementText() + "\n");
					}
				}
				num++;
			}
			System.out.println(num);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test03() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("./stax/books.xml");
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					String name = reader.getName().toString();
					if (name.equals("title")) {
						System.out.print(reader.getElementText() + ":");
					}
					if (name.equals("price")) {
						System.out.print(reader.getElementText() + "\n");
					}
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test02() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("./stax/books.xml");
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					String name = reader.getName().toString();
					if (name.equals("book")) {
						int count = reader.getAttributeCount();
						for (int i = 0; i < count; i++) {
							System.out.println(reader.getAttributeName(i) + ":" + reader.getAttributeValue(i));
						}
					}
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test01() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("./stax/books.xml");
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			while (reader.hasNext()) {
				int type = reader.next();
				// System.out.println(type);
				// 判断节点类型是否是开始或者结束或者文本节点,之后根据情况及进行处理
				if (type == XMLStreamConstants.START_ELEMENT) {
					System.out.println(reader.getName());
				} else if (type == XMLStreamConstants.CHARACTERS) {
					System.out.println(reader.getText().trim());
				} else if (type == XMLStreamConstants.END_ELEMENT) {
					System.out.println("/" + reader.getName());
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
