package test.proxy.test.t0629;

import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;


public abstract class Parent extends Parent2 {
	private String pa;

	public String getPa() {
		return pa;
	}

	public void setPa(String pa) {
		this.pa = pa;
	}
	@InProgressMethod(excuteMethod = Constants.ExecuteCons.DOEXCUTE)
	public void testHello(){
		System.out.println("test...hello...");
	}
}
