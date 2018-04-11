package test.reflect.generics;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TA {
	public static void main(String[] args) {
		B b = new B();
		B b1 = new D();
		B b2 = new F();
		TA ta = new TA();
		new TA().addList(b);
	}

	public void upperBound(List<? extends Date> list, Date date) {
		Date now = list.get(0);
		System.out.println("now==>" + now);
		// list.add(date); //这句话无法编译
		list.add(null);// 这句可以编译，因为null没有类型信息
	}

	public void testUpperBound() {
//		List<Date> list = new ArrayList<Date>();
//		Date date = new Date();
//		list.add(date);
		List<Date> list = new ArrayList<Date>();
		Timestamp date = new Timestamp(System.currentTimeMillis());
		list.add(date);
		upperBound(list, date);
	}

	public void lowerBound(List<? super Timestamp> list) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		list.add(now);
//		list.add(new Date());
		// Timestamp time = list.get(0); //不能编译
	}
	public void lowerBound2(List<? super Date> list) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		list.add(now);
		// Timestamp time = list.get(0); //不能编译
	}
	public void testLowerBound2() {
		List<Timestamp> list = new ArrayList<Timestamp>();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		list.add(now);
		lowerBound(list);
	}
	public void testLowerBound() {
		List<Date> list = new ArrayList<Date>();
		list.add(new Date());
		lowerBound(list);
	}

	/**
	 * 添加T或T的子类
	 * 
	 * @param list
	 * @param t
	 */
	public <T> void addList(T t) {
		List<? super T> list = new ArrayList<T>();
		list.add(t); // 编译通过
	}

	public void addList(List<? super B> list) {
		list.add(new D()); // 编译通过
		list.add(new F()); // 编译通过
		list.add(new B()); // 编译通过
//		list.add(new A()); // 编译错误

	}

	public void addList2() {
		List<? extends B> list = new ArrayList<D>();
	}
}

class A1 extends A {
}

class A {
}

class B extends A {
}

class C extends A {
}

class D extends B {
}

class E extends C {
}

class F extends B {
}