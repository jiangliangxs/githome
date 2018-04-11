package test.reflect.generics;

import java.util.ArrayList;
import java.util.List;

public class Tclass5 {
	public static void main(String[] args) {
		A11<String> a = null;
		A11<Integer> a1 = null;
		A11<Object> a2 = null;
		a = new A11<String>();
		a1 = new A11<Integer>();// 编译不过
		a2 = new A11<Object>();
		/**
		 * 子类上限为String
		 * */
		setV1(a,a);
		// setV1(a1);//编译不通过
		// setV1(a2);//编译不通过
		/**
		 * 子类下限为String
		 * */
		setV2(a);
		// setV2(a1);//编译不通过
		setV2(a2);

	}

	public static <E>A11<? extends String> setV1(A11<? extends String> a,E e) {
		List<E> lst = new ArrayList<E>();//编译通过
		System.out.println(a);
		return null;
	}

	public static A11<? extends String> setV2(A11<? super String> a/*,E e编译不通过*/) {
//		List<E> lst = new ArrayList<E>();//编译不通过
		System.out.println(a);
		return null;
	}
}

class A11<T> {

}
