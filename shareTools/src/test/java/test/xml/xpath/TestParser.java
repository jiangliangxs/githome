package test.xml.xpath;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

public class TestParser {

	private String filePath = TestParser.class.getResource("xpath1.xml").getFile();

	private Document document;
	
	@Before
	public void load() {
		File file = new File(filePath);
		if (file.exists()) {
			SAXReader saxReader = new SAXReader();
			try {
				document = saxReader.read(file);
			} catch (DocumentException e) {
				System.out.println("文件加载异常：" + filePath);
				e.printStackTrace();
			}
		} else {
			System.out.println("文件不存在 : " + filePath);
		}
	}

	public Element getElementObject(String elementPath) {
		return (Element) document.selectSingleNode(elementPath);
	}

	@SuppressWarnings("unchecked")
	public List<Element> getElementObjects(String elementPath) {
		return document.selectNodes(elementPath);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getChildrenInfoByElement(Element element) {
		Map<String, String> map = new HashMap<String, String>();
		List<Element> children = element.elements();
		for (Element e : children) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	public boolean isExist(String elementPath) {
		boolean flag = false;
		Element element = this.getElementObject(elementPath);
		if (element != null)
			flag = true;
		return flag;
	}

	public String getElementText(String elementPath) {
		Element element = this.getElementObject(elementPath);
		if (element != null) {
			return element.getText().trim();
		} else {
			return null;
		}
	}

	@Test
	public void test1() {
		List<Element> es = getElementObjects("//div[@class='panel datagrid' and @sizcache=\"1\"]//div[@class='datagrid-cell-rownumber' and last()]");
		es = getElementObjects("//a[@class='aa'][@field='bb']");
		es = getElementObjects("//a[@class='aa' and @field='bb']");
		es = getElementObjects("//a[@class='aa' or @field='bb']");
		es = getElementObjects("//a2//a[@class='aa' and @field='bb'][last()]");
		es = getElementObjects("//a2//a[@class='aa'][last()]");
		es = getElementObjects("//a3//a[contains(@class,'aax')][last()]");
		es = getElementObjects("//a3//a[contains(@class,'aax')]/a[last()]");
		es = getElementObjects("//div[@class='panel datagrid' and @sizcache='1']//tr[@class='datagrid-row'][last()]//div[@class='datagrid-cell-rownumber']");
		System.out.println(es.size());
		for (Element e : es) {
			System.out.println(e.getStringValue());
		}
	}
}