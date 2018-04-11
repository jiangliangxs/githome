package test.proxy.test.t0629;

import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;


public class MyTest {

	
	public String ss ="sdd";
	
	@InProgressMethod(excuteMethod=Constants.ExecuteCons.DOAFTER)
	public void firstTest(String value){
		ss ="cc";
		System.out.println("a : " + value);
	}
	
	
	
	
}
