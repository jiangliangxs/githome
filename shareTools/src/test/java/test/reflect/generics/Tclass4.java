package test.reflect.generics;

public class Tclass4 {
	
	public static void main(String[] args) {
		A12 a = new A12();
		a.m1();
		a = getT(a);
		a.m1();
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T getT(Object o1){
		return (T)o1;
	}

}
class A12{
	public void m1(){
		System.out.println("A->m1()");
	}
}
