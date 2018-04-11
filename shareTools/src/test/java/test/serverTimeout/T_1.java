package test.serverTimeout;

public class T_1 extends T_2{
	public void m1() {
		while (true) {

		}
	}

	public void m2() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public  void m3() {
		try {
			System.out.println("休眠2秒...");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void m4() {
		while(true){
			
		}
	}
	public void m5() {
		System.out.println("hello");
	}
	
	private String m(){
		return "aaaaaaaaaaaaa";
	}
}
