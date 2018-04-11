package test.proxy.test.t0630;

import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;

public class TSon1_1 {
	//@InProgressMethod(excuteMethod = Constants.ExecuteCons.DOAFTER)
	public void m1() {
		System.out.println("执行TSon1_1方法m1()");
		m2();
	}

	@InProgressMethod(excuteMethod = Constants.ExecuteCons.DOEXCUTE)
	public void m2() {
		System.out.println("执行TSon1_1方法m2()");
	}
}
