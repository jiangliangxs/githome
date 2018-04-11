package test.proxy.util2.test;

import test.proxy.util2.Resource;

public class Field2 {
	@Resource
	public Field3 f3;
	public void testf2(){
		System.out.println("test...f3 start");
		f3.testf3();
		System.out.println("test...f3 stop");
	}

}
