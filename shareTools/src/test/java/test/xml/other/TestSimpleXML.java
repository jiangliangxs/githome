package test.xml.other;

import java.io.File;
import java.io.Serializable;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestSimpleXML implements Serializable {
	private static final long serialVersionUID = 1L;
	private Document doc;
	private String fileName;
	public TestSimpleXML(String fileName) {
		this.fileName = fileName;
		try {
			SAXReader reader = new SAXReader();
			this.doc = reader.read(new File(this.fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getPassword(String userNameKey, String passwordKey,String userName) {
		try {
			Element emt_username = (Element) this.doc.selectNodes(userNameKey).get(0);
			if (emt_username.getTextTrim().equals(userName)) {
				Element emt_password = (Element) this.doc.selectNodes(passwordKey).get(0);
				return emt_password.getText();
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}
	public static void main(String[] args) {
		TestSimpleXML sim = new TestSimpleXML("E:\\haoYi\\sources\\testsvn\\zjsystem\\resources\\database.xml");
		String passowrd = sim.getPassword("/Config/DataSource/DB/user_id", "/Config/DataSource/DB/password","iqcdbuser");
		System.out.println(passowrd);

	}
}
