package test.proxy.test.t0630;

import zj.java.util.JavaUtil;


public class TMain1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		TSon1 t1 = new TSon1();
//		try {
//			//t1 = (TSon1) new ParentCgLib().getCgLibInstance(t1);
//			t1 = new ParentCgLib().getCgLibInstance(t1,new MthIntImpl1());
//			t1.m1("TSon1->m1->str1",1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		Class[] class1 = new Class[]{
				String.class,int.class
		};
		Class[] class2 = new Class[]{
				String.class,int.class
		};
		System.out.println(JavaUtil.arrayEquals(class1, class2));
	}

}
