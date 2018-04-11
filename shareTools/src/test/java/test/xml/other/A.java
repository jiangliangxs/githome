package test.xml.other;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(namespace = "cn.haoyitec")
@XmlAccessorType(XmlAccessType.FIELD)
public class A {
	@XmlElement
	private String s1;
	@XmlElement
	private String s2;
	private B  b;
	private int i1;
	private Date d1;
	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getS2() {
		return s2;
	}
	public void setS2(String s2) {
		this.s2 = s2;
	}
	public B getB() {
		return b;
	}
	public void setB(B b) {
		this.b = b;
	}
	public int getI1() {
		return i1;
	}
	public void setI1(int i1) {
		this.i1 = i1;
	}
	public Date getD1() {
		return d1;
	}
	public void setD1(Date d1) {
		this.d1 = d1;
	}
	
}
