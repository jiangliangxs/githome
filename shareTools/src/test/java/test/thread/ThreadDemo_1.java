package test.thread;

public class ThreadDemo_1 {
	public static void main(String[] args) {
		m2();
	}

	public static void m2() {
		MyThread2 t = new MyThread2();
		YourThread t2 = new YourThread();
		t.start();
		// Thread.yield();// 当前线程让出处理器, 使当前线程进入Runnable等待
		t2.start();
		System.out.println("Over!");
	}

	public static void m1() {
		MyThread t = new MyThread();
		YourThread t2 = new YourThread();
		t.start();
		// Thread.yield();//当前线程让出处理器, 使当前线程进入Runnable等待
		t2.start();
		System.out.println("Over!");
	}

	public static class YourThread extends Thread {
		public void run() {
			for (int i = 0; i < 100; i++) {
				System.out.println("你干啥那?");
				Thread.yield();
			}
		}
	}

	public static class MyThread extends Thread {
		public void run() {
			for (int i = 0; i < 100; i++) {
				System.out.println("我抽出皮筋作弹弓子," + "打你们家玻璃!");
			}
		}
	}

	public static class MyThread2 extends Thread {
		public void run() {
			for (int i = 0; i < 100; i++) {
				System.out.println("我抽出皮筋作弹弓子," + "打你们家玻璃!");
				if (i % 2 == 0) {
					Thread.yield();
				}
			}
		}
	}
}
