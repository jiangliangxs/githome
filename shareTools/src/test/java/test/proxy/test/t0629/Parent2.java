package test.proxy.test.t0629;

import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;

public abstract class Parent2 {

	private String p2;

	@InProgressMethod(excuteMethod = Constants.ExecuteCons.DOEXCUTE)
	public String getP2() {
		System.out.println("拦截getP2()...方法");
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

}
