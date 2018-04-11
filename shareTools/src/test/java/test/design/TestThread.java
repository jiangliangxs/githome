package test.design;

public class TestThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		m2();
	}

	public static void m3() {
		for (int i = 0; i < 10; i++) {
			// synchronized(Runable2.class){
			Thread thread = new Thread(new Runable3(), String.valueOf(i));
			thread.start();
			// }
		}
	}

	private static class Runable3 implements Runnable {
		public void run() {
			String name = Thread.currentThread().getName();
			// if ("1".equals(name)){
			// myIns1.setName("name1");
			// }else if ("5".equals(name)){
			// myIns1.setName("name5");
			// }else{
			// myIns1.setName("未初使化"+name);
			// }
			// 效果 一样
			// synchronized (MySingleton1.class) {
			// MySingleton1 myIns1 = MySingleton1.getInstance();
			// myIns1.setName("初使化" + name);
			// System.out.println(name + ":" + myIns1 + ":" + myIns1.getName());
			// }
			MySingleton1 myIns1 = MySingleton1.getInstance();
			// 效果 一样
			synchronized (myIns1) {
				myIns1.setName("初使化" + name);
				System.out.println(name + ":" + myIns1 + ":" + myIns1.getName());
			}
		}
	}

	public static void m2() {
		for (int i = 0; i < 10; i++) {
			// synchronized(Runable2.class){
			Thread thread = new Thread(new Runable2(), String.valueOf(i));
			thread.start();
			// }
		}
	}

	private static class Runable2 implements Runnable {
		public void run() {
			String name = Thread.currentThread().getName();
			// if ("1".equals(name)){
			// myIns1.setName("name1");
			// }else if ("5".equals(name)){
			// myIns1.setName("name5");
			// }else{
			// myIns1.setName("未初使化"+name);
			// }
			MySingleton1 myIns1 = MySingleton1.getInstance();
			myIns1.setName("初使化" + name);
			System.out.println(name + ":" + myIns1 + ":" + myIns1.getName());
		}
	}

	public static void m1() {
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Runable1(), String.valueOf(i));
			thread.start();
		}
	}

	private static class Runable1 implements Runnable {
		public void run() {
			MySingleton1 myIns1 = MySingleton1.getInstance();
			System.out.println(myIns1 + ":" + myIns1.hashCode());
		}
	}
}
