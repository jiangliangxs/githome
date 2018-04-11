package test.xml.other;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Student")
public class Student {
	private String ii;
	private int id;
	private String name;
	private int age;
	private Classroom classroom;
	private String aa;
	@XmlAttribute
	public String getAa() {
		return aa;
	}
	public void setAa(String aa) {
		this.aa = aa;
	}
	@XmlAttribute(name="ii")
	public String getIi() {
		return ii;
	}
	public void setIi(String ii) {
		this.ii = ii;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Classroom getClassroom() {
		return classroom;
	}
	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	public Student(int id, String name, int age, Classroom classroom) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.classroom = classroom;
	}
	public Student() {
		super();
	}
	
	
}
