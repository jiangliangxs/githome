package test.proxy.test.t0629;

import test.proxy.util4.ParentCgLib;

public class TestUtil4 {
	public static void main(String[] args) throws Exception {
		new TestUtil4().test1();
	}

	public void test1() throws Exception {
		Son son = new Son();
//		son.setPa("parent...");
//		son.setP2("parent2....");
//		son.setPassword("hello.password");
		Object obj = new ParentCgLib().getCgLibInstance(son,
				new MethodInterceptorImplSon1());
		Son user = (Son)obj;
//		user.initClear();
//		System.out.println("parent2---->" + user.getP2());
		user.testHello();
	}
}
