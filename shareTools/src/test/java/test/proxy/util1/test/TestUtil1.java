package test.proxy.util1.test;

import test.proxy.util1.InterfaceFactory;
import test.proxy.util1.Parent;

public class TestUtil1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Business1 target3 = new Bus();
		Object obj = InterfaceFactory.getInterface(target3, Parent.class
				.getClassLoader(), new Son1(target3), Business1.class,
				Business2.class);
//		((Bus)target3).doSomething();
		((Business1) obj).doSomething1();
//		((Business2) obj).doSomething2();

	}

}
