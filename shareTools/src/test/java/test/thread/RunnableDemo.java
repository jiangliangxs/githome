package test.thread;

public class RunnableDemo {
	public static void main(String[] args) {
		Thread t1 = new Thread(new Foo());
		Thread t2 = new Thread(new Foo());
		Thread t3 = new Thread(new Foo());
		t1.start();
		t2.start();
		t3.start();
	}

	public static class Foo implements Runnable {
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + i + "HI");
			}
		}
	}
}
