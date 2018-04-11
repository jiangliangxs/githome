package test.xml.other;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;


import zj.check.util.CheckUtil;
import zj.java.util.JavaUtil;
import zj.xml.util.XmlConstant;
import zj.xml.util.XmlUtil;
import zj.xml.util.XmlUtil.ICallBackMap2xmlByXMLStream;

import com.alibaba.fastjson.JSON;

public class TestUtil {
	@Test
	public void testXml() {
		String title = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		String xml = "<Package><Header><SendTime>2012-01-02 10:11:12</SendTime><ThirdSerial>1234567890</ThirdSerial><Asyn>1</Asyn><ReturnUrl>http://www.back.com<Version>1.0</Version><Request><UserInfo><ThirdUserId>wx15656856396</ThirdUserId></UserInfo></Request></ReturnUrl></Header></Package>";
		xml = title + xml;
		xml = formatXML(xml);
		System.out.println(xml);
	}

	public String formatXML(String inputXML) {
		String requestXML = null;
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new StringReader(inputXML));
			XMLWriter writer = null;
			if (document != null) {
				try {
					StringWriter stringWriter = new StringWriter();
					OutputFormat format = new OutputFormat(" ", true);
					writer = new XMLWriter(stringWriter, format);
					writer.write(document);
					writer.flush();
					requestXML = stringWriter.getBuffer().toString();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
						}
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return requestXML;
	}

	@Test
	public void m5() throws Exception {
		Set<String> listSuffixes = new HashSet<String>();
		listSuffixes.add("Lists");
		String key = "ProductList";
		for (String listSuffix : listSuffixes) {
			if (key.endsWith(listSuffix)) {
				// 如果存在后缀
				key = key.substring(0, key.length() - listSuffix.length());
				break;
			}
		}
		System.out.println(key);
		// String xml = TestData.getXML();
		// Map<String, Object> m = XmlUtil.xmlToMapByXStream(xml,true);
		// System.out.println(m);
	}

	@Test
	public void m4() throws Exception {
		String xml = TestData.getXML();
		try {
			Map<String, Object> m = XmlUtil.xmlToMapByXStream(xml);
			System.out.println("old:" + m);
		} catch (Exception e) {
			String[] rootAry = JavaUtil.split(e.getMessage(), ":");
			String aliasName = "";
			for (String root : rootAry) {
				if (CheckUtil.isNotNull(root)) {
					aliasName = root;
					break;
				}
			}
			System.out.println(rootAry[0]);
			Map<String, Object> m = XmlUtil.xmlToMapByXStream(xml, aliasName, true);
			System.out.println("new:" + m);
		}
	}

	@Test
	public void m3() throws Exception {
		Map<String, Object> Package = new HashMap<String, Object>();
		Map<String, Object> responseKey = new HashMap<String, Object>();
		Package.put("Package", responseKey);
		Map<String, Object> Header = new HashMap<String, Object>();
		responseKey.put("Header", Header);
		Map<String, Object> Request = new HashMap<String, Object>();
		responseKey.put("Request", Request);
		Map<String, Object> User = new HashMap<String, Object>();
		Request.put("User", User);
		List<Map<String, Object>> ProductListValue = new ArrayList<Map<String, Object>>();
		Request.put("Questions", ProductListValue);
		Map<String, Object> ProductValue = null;
		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put(XmlConstant.LIST_ALIAS_MAP_KEY, "Question");

		Header.put("RequestType", "DS-TH-017");
		Header.put("SendTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Header.put("ThirdSerial", "000000000000000");

		User.put("weChatId", "11111111111111");

		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put("title", "title1");
		ProductValue.put("answer", "answer1");

		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put("title", "title2");
		ProductValue.put("answer", "answer2");

		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put("title", "title3");
		ProductValue.put("answer", "answer3");

		System.out.println(Package);
		System.out.println("<?xml version=\"1.0\" encoding=\"GBK\"?>" + XmlUtil.mapToXmlByXStream(responseKey, "Package"));

		System.out.println(XmlUtil.mapToXmlGBKTitleByXMLStream(Package));
	}

	@Test
	public void m2() throws Exception {
		String xml = TestData.getXML();
		Map<String, Object> m = XmlUtil.xmlToMapByXStream(xml, "Package");
		String mapJson = JSON.toJSONString(m);
		String newXml = XmlUtil.mapToXmlByXStream(m, "Package");
		System.out.println("xml原始值:\n" + xml + "\nxml转换后map值:\n" + mapJson + "\nmap转换newXml值:\n" + newXml);

		Map<String, Object> nm = new HashMap<String, Object>();
		nm.put("Package", m);
		String ss = XmlUtil.mapToXmlGBKTitleByXMLStream(nm);
		System.out.println("XML1:\n" + ss);
		ss = XmlUtil.mapToXmlUTF8TitleByXMLStream(nm);
		System.out.println("XML2:\n" + ss);
		ss = XmlUtil.mapToXmlByXMLStream(nm);
		System.out.println("XML3:\n" + ss);
		ss = XmlUtil.mapToXmlByXMLStream(nm, new XmlUtil.ICallBackMap2xmlByXMLStream() {
			@Override
			public void loop(XMLStreamWriter xsw, String key, Object value) throws Exception {
				System.out.println("key:" + key + "-value:" + value);
			}

			@Override
			public void call(XMLStreamWriter xsw) throws Exception {
			}

			@Override
			public Map<String, Object> listAttrMap() {
				return null;
			}

		});
		System.out.println("XML4:\n" + ss);
	}

	@Test
	public void m1_1() throws Exception {
		Map<String, Object> responseKey = new HashMap<String, Object>();
		List<Map<String, Object>> ProductListValue = new ArrayList<Map<String, Object>>();
		responseKey.put("ProductList", ProductListValue);
		Map<String, Object> ProductValue = null;
		// ProductValue = new HashMap<String, Object>();
		// ProductListValue.add(ProductValue);
		// ProductValue.put(XmlConstant.LIST_ALIAS_MAP_KEY, "zhangjun");
		// ProductValue.put(XmlConstant.LIST_ALIAS_MAP_KEY, null);
		// ProductValue.put(XmlConstant.LIST_ALIAS_MAP_KEY, "");
		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put("id", "2123456789");
		ProductValue.put("name", "2投连险1");
		ProductValue.put("money", "2123.56");
		ProductValue.put("mult", "21873.28");
		ProductValue.put("ftype", "1");
		ProductValue.put("fdate", "2014-01-03");
		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put("id", "2123456789");
		ProductValue.put("name", "2投连险2");
		ProductValue.put("money", "2123.56");
		ProductValue.put("mult", "21873.28");
		ProductValue.put("ftype", "1");
		ProductValue.put("fdate", "2014-01-03");
		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put("id", "2123456789");
		ProductValue.put("name", "2投连险3");
		ProductValue.put("money", "2123.56");
		ProductValue.put("mult", "21873.28");
		ProductValue.put("ftype", "1");
		ProductValue.put("fdate", "2014-01-03");

		ProductValue.put("testInteger", 124345);
		ProductValue.put("testDate", new Date());
		ProductValue.put("testNull", null);

		Map<String, Object> listAttrMap = new HashMap<String, Object>();
		listAttrMap.put("ProductList", "AAA");
		String xml = null;
		xml = XmlUtil.mapToXmlTitleByXMLStream(responseKey, "UTF-8", listAttrMap);
		System.out.println(xml);
	}

	@Test
	public void m1() throws Exception {
		Map<String, Object> responseKey = new HashMap<String, Object>();
		List<Map<String, Object>> ProductListValue = new ArrayList<Map<String, Object>>();
		responseKey.put("ProductList", ProductListValue);
		Map<String, Object> ProductValue = null;
		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		// ProductValue.put(XmlConstant.LIST_ALIAS_MAP_KEY, "zhangjun");
		// ProductValue.put(XmlConstant.LIST_ALIAS_MAP_KEY, null);
		// ProductValue.put(XmlConstant.LIST_ALIAS_MAP_KEY, "");
		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put("id", "2123456789");
		ProductValue.put("name", "2投连险1");
		ProductValue.put("money", "2123.56");
		ProductValue.put("mult", "21873.28");
		ProductValue.put("ftype", "1");
		ProductValue.put("fdate", "2014-01-03");
		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put("id", "2123456789");
		ProductValue.put("name", "2投连险2");
		ProductValue.put("money", "2123.56");
		ProductValue.put("mult", "21873.28");
		ProductValue.put("ftype", "1");
		ProductValue.put("fdate", "2014-01-03");
		ProductValue = new HashMap<String, Object>();
		ProductListValue.add(ProductValue);
		ProductValue.put("id", "2123456789");
		ProductValue.put("name", "2投连险3");
		ProductValue.put("money", "2123.56");
		ProductValue.put("mult", "21873.28");
		ProductValue.put("ftype", "1");
		ProductValue.put("fdate", "2014-01-03");

		ProductValue.put("testInteger", 124345);
		ProductValue.put("testDate", new Date());
		ProductValue.put("testNull", null);

		List<String> l = new ArrayList<String>();
		responseKey.put("listTest", l);
		l.add(XmlConstant.LIST_ALIAS_MAP_KEY + "LIST标签");
		l.add("hello");
		l.add("world");

		String xml = null;
		xml = XmlUtil.mapToXmlByXStream(responseKey, "Response");
		System.out.println(xml);
		xml = XmlUtil.mapToXmlByXMLStream(responseKey);
		System.out.println(xml);
		xml = XmlUtil.mapToXmlGBKTitleByXMLStream(responseKey);
		System.out.println(xml);
	}
}
