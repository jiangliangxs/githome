package test.swing;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class TestCompUtil {
	public static void main(String[] args) {
		CompUtilExport<TestComp> cu = new CompUtilExport<TestComp>();
		cu.attrName = "name";
		cu.sort = CompUtilExport.ASC;
		SortedSet<TestComp> set = new TreeSet<TestComp>(cu);
		set.add(new TestComp(10, "zhang"));
		set.add(new TestComp(11, "ahang1"));
		set.add(new TestComp(12, "fhang2"));
		set.add(new TestComp(13, "zhang3"));
		set.add(new TestComp(14, "hhang"));
		set.add(new TestComp(15, "fhang6"));
		set.add(new TestComp(16, "dhang5"));
		set.add(new TestComp(10, "zhang"));
		Iterator<TestComp> it = set.iterator();
		while (it.hasNext()) {
			TestComp s = it.next();
			System.out.println(s.getId() + "\t" + s.getName());
		}
	}
}

class TestComp {
	private int id;
	private String name;
	private double dou;

	public TestComp(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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