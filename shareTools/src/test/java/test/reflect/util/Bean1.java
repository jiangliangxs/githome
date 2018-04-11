package test.reflect.util;

import zj.reflect.bean.AutowiredMapFormBean;

public class Bean1 {
	@AutowiredMapFormBean
	private String s1;
	@AutowiredMapFormBean
	public String p1;
	@AutowiredMapFormBean
	private int i1;
	@AutowiredMapFormBean
	private boolean b1;
	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public int getI1() {
		return i1;
	}
	public void setI1(int i1) {
		this.i1 = i1;
	}
	public boolean isB1() {
		return b1;
	}
	public void setB1(boolean b1) {
		this.b1 = b1;
	}
	@AutowiredMapFormBean
	public String getSm1(){
		return "sm1...";
	}
	@AutowiredMapFormBean
	public String get(){
		return "get...";
	}
	@AutowiredMapFormBean
	public void getVoid(){
		System.out.println("getVoid...");
	}
	@AutowiredMapFormBean
	public String abcde(){
		return "abcde...";
	}
}
