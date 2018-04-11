package test.serverTimeout;

import java.lang.reflect.Method;

public class Test {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws Exception{
//		T_1 t1 = new T_1();
//		try {
//			Object o = ServiceConnect.getServiceValue(t1, "t_2_m3",new Object[]{new TP()}, 30111100);
//			System.out.println("o:"+o);
//		} catch (TimeOutException e) {
//			e.printStackTrace();
//		}
		Method mm = C2.class.getDeclaredMethod("mm", C1.class);
		mm.setAccessible(true);
		System.out.println(mm.invoke(new C2(), (I1)(new C1())));
		
//		C2 t1 = new C2();
//		try {
//			Object o = ServiceConnect.getServiceValue(t1, "mm",new Object[]{new C1()}, 30111100);
//			System.out.println("o:"+o);
//		} catch (TimeOutException e) {
//			e.printStackTrace();
//		}
	}
	public void a(){
		
	}
	private void b(){
		
	}
}
