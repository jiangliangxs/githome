package test.sort;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import zj.java.util.JavaUtil;
import zj.reflect.util.FieldUtil;
import zj.sort.util.SortUtil;

public class TestSortUtil {
	@Test
	public void test2() throws Exception {
		SortUtil<SortBean> util = new SortUtil<SortBean>();
		util.setSortOrder(SortUtil.ASC);
		util.setSortName("b");
		SortedSet<SortBean> set = new TreeSet<SortBean>(util);
		for (int i = 0; i < 10; i++) {
			SortBean sb = new SortBean();
			sb.setS("a" + i);
			sb.setB(new BigDecimal(i));
			sb.setI(i);
			set.add(sb);
		}
		for (SortBean sb : set) {
			System.out.println(sb.getS() + "," + sb.getB() + "," + sb.getI());
		}
	}

	@Test
	public void test1() throws Exception {
		SortUtil<Integer> util = new SortUtil<Integer>();
		util.setSortOrder(SortUtil.DESC);
		SortedSet<Integer> set = new TreeSet<Integer>(util);
		set.add(100);
		set.add(123);
		set.add(11);
		set.add(33);
		set.add(15);
		for (Integer i : set) {
			System.out.println("i:" + i);
		}
		System.out.println("--------------------");
		// SortUtil<BigDecimal> util = new SortUtil<BigDecimal>();
		// util.setSortOrder(SortUtil.DESC);
		// SortedSet<BigDecimal> set = new TreeSet<BigDecimal>(util);
		// set.add(new BigDecimal("1241.121"));
		// set.add(new BigDecimal("111221.122"));
		// set.add(new BigDecimal("1221.123"));
		// set.add(new BigDecimal("12131.124"));
		// set.add(new BigDecimal("1211.125"));
		// set.add(new BigDecimal("121.126"));
		// for (BigDecimal i : set) {
		// System.out.println("i:" + i);
		// }
	}

	@Test
	public void testOther() throws Exception {
		TestComp comp = null;
		TestCompSon son = null;
		comp = new TestComp();
		son = new TestCompSon();
		son.setI_1(1);
		son._i_1 = 3;
		comp.setSon(son);
		comp._son = son;
		comp._name = "hello-e";
		String sortName = "son.i_1";
		// sortName = "_name";
		String[] names = JavaUtil.split(sortName, ".");
		Object obj = comp;
		Object value = null;
		for (String fieldName : names) {
			value = FieldUtil.get(obj, fieldName, true);
			if (value != null) {
				obj = value;
				FieldUtil.set(obj, fieldName, "222");
				System.out.println("****:" + comp.getSon().getI_1());
			}
			System.out.println("->" + fieldName + ":" + value);
		}
		Object oo = null;

	}

	@Test
	public void test() throws Exception {
		for (int i = 0; i < 10; i++) {
			testSort();
			System.out.println("======================================");
		}
	}

	@Test
	public void testSort() throws Exception {
		SortUtil<TestComp> cu = new SortUtil<TestComp>();
		cu.setSortName("name");
		cu.setSortOrder(SortUtil.ASC);
		cu.setFilterEqual(false);
		SortedSet<TestComp> set = new TreeSet<TestComp>(cu);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TestComp comp = null;
		TestCompSon son = null;
		comp = new TestComp(10, "zhang", format.parse("2014-05-01 10:10:10"));
		son = new TestCompSon();
		son.setI_1(1);
		son._i_1 = 3;
		comp.setSon(son);
		comp._son = son;
		comp._name = "hello-e";
		comp._fst = format.parse("2014-08-01 10:10:10");
		set.add(comp);
		// ----------------------------------------------------
		comp = new TestComp(11, "zhang2", format.parse("2014-02-01 10:10:10"));
		son = new TestCompSon();
		son.setI_1(8);
		son._i_1 = 5;
		comp.setSon(son);
		comp._son = son;
		comp._name = "hello-a";
		comp._fst = format.parse("2014-10-01 10:10:10");
		set.add(comp);
		// ----------------------------------------------------
		comp = new TestComp(12, "zhang2", format.parse("2014-03-01 10:10:10"));
		son = new TestCompSon();
		son.setI_1(3);
		son._i_1 = 83;
		comp.setSon(son);
		comp._son = son;
		comp._name = "hello-ga";
		comp._fst = format.parse("2014-7-01 10:10:10");
		set.add(comp);
		// ----------------------------------------------------
		comp = new TestComp(13, "aazhang3", format.parse("2014-02-01 10:10:10"));
		son = new TestCompSon();
		son.setI_1(5);
		son._i_1 = 6;
		comp.setSon(son);
		comp._son = son;
		comp._name = "abc-ga";
		comp._fst = format.parse("2014-8-01 10:10:10");
		set.add(comp);
		// ----------------------------------------------------
		Iterator<TestComp> it = set.iterator();
		int index = 0;
		while (it.hasNext()) {
			TestComp s = it.next();
			System.out.print((++index) + "\t" + s.getId2() + "\t" + s.getName() + "\t" + format.format(s.getFst2()) + "\t" + s.getSon().getI_1());
			System.out.println("\t|\t" + s._name + "\t" + format.format(s._fst) + "\t" + s._son._i_1);
		}
	}
}

class TestCompSon {
	private int i_1;
	public int _i_1;

	public int getI_1() {
		return i_1;
	}

	public void setI_1(int i_1) {
		this.i_1 = i_1;
	}

}

class TestComp extends TestComp2 {
	private int id;
	private String name;
	public String _name;
	private double dou;
	private Date fst;
	public Date _fst;
	private TestCompSon son;
	public TestCompSon _son;

	public TestCompSon getSon() {
		return son;
	}

	public void setSon(TestCompSon son) {
		this.son = son;
	}

	public Date getFst() {
		return fst;
	}

	public void setFst(Date fst) {
		this.fst = fst;
	}

	public TestComp() {
	}

	public TestComp(int id, String name, Date fst) {
		super();
		this.setId2(id);
		this.name = name;
		this.setFst2(fst);
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

	public double getDou() {
		return dou;
	}

	public void setDou(double dou) {
		this.dou = dou;
	}

}

class TestComp2 {
	private int id2;
	private Date fst2;

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public Date getFst2() {
		return fst2;
	}

	public void setFst2(Date fst2) {
		this.fst2 = fst2;
	}

	public TestComp2(int id2, Date fst2) {
		super();
		this.id2 = id2;
		this.fst2 = fst2;
	}

	public TestComp2() {
		super();
	}

}