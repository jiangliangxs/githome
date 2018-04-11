package test.reflect.other;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import test.reflect.bean.Constant;
import test.reflect.util.TB22;
import zj.java.util.JavaUtil;
import zj.reflect.util.FieldUtil;
import zj.reflect.util.TypeUtil;

public class TestA {
	@Test
	public void m12() throws Exception {
		BigDecimal bd = new BigDecimal("192912.121212323434");
		System.out.println(TypeUtil.Primitive.getNumericValue(bd));
		Date d = new Date();
		Map<String, Object> format = new HashMap<String, Object>();
		format.put("date", "yyyy-MM-dd");
		System.out.println(TypeUtil.Primitive.getNumericValue(d,format));
	}

	@Test
	public void m11() throws Exception {
		Constant c = new Constant();
		FieldUtil.fieldNewInstance(c, "pk");
		System.out.println(FieldUtil.get(c, "pk", true));
		System.out.println(c.getPk());
		Constant c2 = new Constant();
		System.out.println(c2.getPk());
	}

	@Test
	public void test9() throws Exception {
		System.out.println(Integer.class.isPrimitive());
	}

	@Test
	public void test8() throws Exception {
		Map<String, String> idNameValue = new HashMap<String, String>();
		String s1 = null;
		String s2 = "";
		System.out.println(idNameValue.get(null));
		System.out.println(JavaUtil.trim(s1));
		System.out.println(JavaUtil.trim(s2));
		System.out.println(s1 == s2);
	}

	@Test
	public void test7() throws Exception {
		TB tb = new TB();
		System.out.println(tb);
		test6(tb);
		System.out.println(tb);
		System.out.println(tb.name);
	}

	public void test6(TB tb) throws Exception {
		tb = new TB();
		tb.name = "123";
		System.out.println("tb:" + tb);
		System.out.println(tb.name);
	}

	public void test5(Object tb) throws Exception {
		tb = new TB();
		((TB) tb).name = "123";
		System.out.println("tb:" + tb);
		System.out.println(((TB) tb).name);

	}

	@Test
	public void test4() throws Exception {
		Object tb = new TB();
		((TB) tb).name = "ab";
		System.out.println(tb);
		test5(tb);
		System.out.println(tb);
		System.out.println(((TB) tb).name);
	}

	public void test3(Object tb) throws Exception {
		Object tb2 = ((TB) tb).clone();
		TB t = (TB) tb2;
		t.name = "123";
		System.out.println(t.name);
	}

	@Test
	public void test2() throws Exception {
		TA ta = new TA();

		TB tb = new TB();
		tb.name = "abcd";

		String fieldName = "tb2.name";
		System.out.println(ta);
		FieldUtil.set(ta, fieldName, true, "zhangjun2");
		System.out.println(ta);
		Object o1 = FieldUtil.get(ta, fieldName, true);
		System.out.println(ta);
		System.out.println(o1);
		// System.out.println(((TB)o1).name);
	}

	@Test
	public void test1() throws Exception {
		TA ta = new TA();
		TB tb = new TB();
		tb.name = "abcd";
		Object o1 = FieldUtil.get(ta, "tb2.name", true);
		System.out.println(o1);
		System.out.println(ta);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test10() throws Exception {
		TA ta = new TA();
		// FieldUtil.fieldNewInstance(ta, "tb22.tb33.name");
		FieldUtil.set(ta, "tb22.tb33.name", true, "hehe");
		System.out.println(FieldUtil.get(ta, "tb22.tb33.name", true));
	}

	private class TA extends TA1 {
		public TB22 tb22;
		private int i1;
		private Integer i2;
		private Date d1;

	}

	private class TA1 {
		public TB tb;
		public String s1;

	}
}

class TB implements Cloneable {
	public String name;

	public TB() {
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}