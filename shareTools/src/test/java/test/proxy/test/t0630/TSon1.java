package test.proxy.test.t0630;

import test.proxy.common.InProgressMethod;
import test.proxy.util4.ParentCgLib;

public class TSon1 {
	@InProgressMethod
	public void m1(String str1, int i1) {
		System.out.println("执行TSon1方法m1()");
		getTSon1_1().m1();
	}

	private TSon1_1 getTSon1_1() {
		try {
			return new ParentCgLib().getCgLibInstance(new TSon1_1());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new TSon1_1();
	}
}
