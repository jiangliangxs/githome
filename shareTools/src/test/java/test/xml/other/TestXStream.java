package test.xml.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;


import zj.xml.bean.XStreamConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class TestXStream {
	@Test
	public void m8() {
		Class c = TreeMap.class;
		System.out.println(c);
		System.out.println(Map.class.isAssignableFrom(c));
		c = ArrayList.class;
		c = HashSet.class;
		System.out.println(c);
		System.out.println(Collection.class.isAssignableFrom(c));
	}
	@Test
	public void m7() {
		String xml = "<map a=\"aa\"><stars><star><id>1</id><name>littleflower</name></star><star><id>2</id><name>littleyellow</name></star></stars>   <filename>cnlab</filename>   <ra>147.0</ra>   <dec>0.0</dec>   <plate>0.0</plate>   <mdj>0.0</mdj> </map> ";
		xml = "<map2 a=\"a\"><stars b=\"b\"><star><id>1</id><name>littleflower</name></star><star><id>2</id><name>littleyellow</name></star></stars>   <filename>cnlab</filename>   <ra>147.0</ra>   <dec>0.0</dec>   <plate>0.0</plate>   <mdj>0.0</mdj> </map2> ";
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new XStreamConverter());
		xstream.alias("map2", Map.class);
		Map<String, Object> map = (Map<String, Object>) xstream.fromXML(xml);
		System.out.println(map);
		xml = xstream.toXML(map);
		System.out.println(xml);
		System.out.println((Map<String, Object>) xstream.fromXML(xml));
	}

	@Test
	public void m6() {
		String xml = "";
		// 声明部分
		// xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
		// 报文根-开始
		xml += "<Package>";
		// 报文头Header-开始
		xml += "<Header>";
		xml += "<RequestType>01</RequestType>";
		xml += "<SendTime>2012-01-02 10:11:12</SendTime>";
		xml += "<ThirdSerial>11111</ThirdSerial>";
		xml += "<Asyn></Asyn>";
		xml += "<ReturnUrl></ReturnUrl>";
		xml += "<PageReturnUrl></PageReturnUrl>";
		xml += "<ProductCode></ProductCode>";
		xml += "</Header>";
		// 报文头Header-结束
		// 报文体Request-开始
		xml += "<Request>";
		xml += "<Order>";
		xml += "<OrderId>22222</OrderId>";
		xml += "<TotalPremium>108.83</TotalPremium>";
		xml += "<InsBeginDate>2013-01-02 10:11:12</InsBeginDate>";
		xml += "</Order>";
		xml += "</Request>";
		// 报文体Request-结束
		xml += "</Package>";
		// 报文根-结束
		System.out.println("xml内容如下");
		System.out.println(xml);
		XStream xstream = new XStream(new DomDriver());
//		xstream.alias("Package", Map.class);
		xstream.alias("Package", List.class);
		// xstream.alias("Header", Map.class);
		// xstream.alias("RequestType", String.class);
		// xstream.alias("SendTime", String.class);
		// xstream.alias("ThirdSerial", String.class);
		// xstream.alias("Asyn", String.class);
		// xstream.alias("ReturnUrl", String.class);
		// xstream.alias("PageReturnUrl", String.class);
		// xstream.alias("ProductCode", String.class);
		//
		// xstream.alias("Request", Map.class);
		// xstream.alias("Order", Map.class);
		// xstream.alias("OrderId", String.class);
		// xstream.alias("TotalPremium", String.class);
		// xstream.alias("InsBeginDate", String.class);
		xstream.registerConverter(new XStreamConverter());

		System.out.println(xstream.fromXML(xml));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void m5() {
		XStream xstream = new XStream(new DomDriver());
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> list1 = new ArrayList<Object>();
		list1.add(new T("a1", "b1", "c1"));
		list1.add(new T("a2", "b2", "c2"));
		List<Object> list2 = new ArrayList<Object>();
		list2.add(new T("a3", "b3", "c3"));
		list2.add(new T("a4", "b4", "c4"));
		map.put("t1", list1);
		map.put("t2", list2);
		xstream.alias("classes", Map.class);
		xstream.alias("class", Map.Entry.class);
		xstream.alias("name", String.class);
		xstream.alias("fields", List.class);
		xstream.alias("field", T.class);
		xstream.aliasAttribute(T.class, "a", "a");
		xstream.aliasAttribute(T.class, "b", "b");
		xstream.aliasAttribute(T.class, "c", "c");
		System.out.println(xstream.toXML(map));
		System.out.println((Map<String, Object>) xstream.fromXML(xstream.toXML(map)));
	}

	@Test
	public void m4() {
		String xml = callXml2();
		System.out.println(xml);
		System.out.println("------------------");
		XStream stream = new XStream(new DomDriver());
		stream.alias("user", User2.class);
		Object o = stream.fromXML(xml);
		System.out.println(o);
	}

	@Test
	public void m3() {
		callXml();
	}

	@Test
	public void m2() {
		User2 u1 = new User2("id1", "name1");
		String xml = new XStream(new DomDriver()).toXML(u1);
		System.out.println(xml);
	}

	@Test
	public void m1() {
		String xmlRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><id>21</id><userName>10计算机应用技术</userName></user>";
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(User.class);
		Object o = xstream.fromXML(xmlRequest);
		if (o == null) {
			System.out.println("解析为NULL");
		} else {
			User user = (User) o;
			System.out.println(user.getId() + "," + user.getUserName());
		}
	}

	public String callXml2() {
		User2 u1 = new User2("id1", "name1");
		XStream stream = new XStream(new DomDriver());
		String xml = stream.toXML(u1);
		System.out.println(xml);
		return xml;
	}

	public String callXml() {
		User2 u1 = new User2("id1", "name1");
		XStream stream = new XStream(new DomDriver());
		stream.alias("user", User2.class);
		String xml = stream.toXML(u1);
		System.out.println(xml);
		return xml;
	}

	
}
