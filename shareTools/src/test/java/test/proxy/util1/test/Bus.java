package test.proxy.util1.test;

import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;
import test.proxy.util1.test.Business1;

public class Bus implements Business1, Business2 {
	public String test;
	
	
	@InProgressMethod(excuteMethod = Constants.ExecuteCons.DOEXCUTE)
	public void begin() {
		System.out.println("begin...");
	}

	public void doSomething() {
		System.out.println("Bus target ...doSomething");
	}
	@InProgressMethod(excuteMethod = Constants.ExecuteCons.DOEXCUTE)
	public void doSomething1() {
		test = "hewllo";
		System.out.println("Bus target ...doSomething1");
	}
	@InProgressMethod(excuteMethod = Constants.ExecuteCons.DOEXCUTE)
	public void doSomething2() {
		System.out.println("Bus target ...doSomething2");
	}

	public void end() {
		System.out.println("end ...");
	}
}
