package test.proxy.util2.test;

import test.proxy.util2.Resource;

public class Field1 {
	@Resource
	public Field2 f2;
	
	public void testf1(){
		System.out.println("test...f1 begin");
		//f2.testf2();
		System.out.println("test...f1 stop");
	}

}
