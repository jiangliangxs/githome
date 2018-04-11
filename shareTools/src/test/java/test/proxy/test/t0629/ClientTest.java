package test.proxy.test.t0629;

import test.proxy.util4.MethodInterceptorImpl;
import test.proxy.util4.ParentCgLib;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Object obj = null;
		// **************************1
		try {
			obj = new ParentCgLib().getCgLibInstance(MyTest.class,
					new MethodInterceptorImpl());
			
			MyTest my = MyTest.class.cast(obj);
			
			my.firstTest("b");
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
