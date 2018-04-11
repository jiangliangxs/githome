package test.xml;

import java.io.File;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import test.xml.auto.Tables;
import test.xml.other.B_A;
import test.xml.other.Classroom;
import test.xml.other.Student;
import zj.io.util.FileUtil;
import zj.xml.util.XmlUtil;

public class TestJaxb {
	@Test
	public void test05() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Tables><Table name='zhangjun'></Table></Tables>";
		Class<Tables> cla = Tables.class;
		Tables xmlValues = XmlUtil.xmlToObjByJaxb(cla, xml);
		System.out.println(xmlValues);
	}
	
	@Test
	public void test04() throws Exception {
//		System.out.println(this.getClass().getResource("generate/MybatisConfig.xml").getFile());
//		String xml = FileUtil.readString(new File(this.getClass().getResource("/ftl/MybatisConfig.xml").getFile()));
		String xml = FileUtil.readString(new File(this.getClass().getResource("generate/MybatisConfig.xml").getFile()));
		System.out.println(xml);
//		System.out.println(entity.resultMaps.size());
//		ResultMap rm = entity.resultMaps.get(0);
//		System.out.println(rm.tableName);
//		System.out.println(rm.results.size());
//		System.out.println(rm.results.get(1).property);
//		System.out.println(stu.getIi() + "," + stu.getName() + "," + stu.getClassroom().getName());
	}
	@Test
	public void test04_1() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Student ii=\"zhangjun\">abcd<age aa=\"hello\">21</age><classroom><grade>2010</grade><id>1</id><name>10计算机应用技术</name></classroom><id>1</id><name>张三</name></Student>";
		Class<Student> s = Student.class;
		Student stu = XmlUtil.xmlToObjByJaxb(s, xml);
		System.out.println(stu.getIi() + "," + stu.getName() + "," + stu.getClassroom().getName());
	}
	@Test
	public void test03() {
		try {
			JAXBContext ctx = JAXBContext.newInstance(Student.class);
			Marshaller marshaller = ctx.createMarshaller();
			Student stu = new Student(1, "张三", 21, new Classroom(1, "10计算机应用技术", 2010));
			marshaller.marshal(stu, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test02() {
		try {
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><student name=\"hello\"><age>21</age><classroom><grade>2010</grade><id>1</id><name>10计算机应用技术</name></classroom><id>1</id><name>张三</name></student>";
			JAXBContext ctx = JAXBContext.newInstance(Student.class);
			Unmarshaller um = ctx.createUnmarshaller();
			Student stu = (Student) um.unmarshal(new StringReader(xml));
			System.out.println(stu.getName() + "," + stu.getClassroom().getName());
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test01() throws Exception {
		B_A a = new B_A();
		String xml = XmlUtil.objToXmlByJaxb(a);
		System.out.println(xml);
	}
}
