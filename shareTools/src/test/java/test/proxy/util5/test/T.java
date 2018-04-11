package test.proxy.util5.test;

public class T {
	public static void main(String[] args) {
		A a = new A();
		a.setStr("hello");
		System.out.println(a.getStr());
		B b = new B();
//		A a1 = (A)b;
//		a1.setStr("world");
		b = (B)a;
		System.out.println("b..." + a.getStr());
	}

}
