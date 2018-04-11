package test.thread;

public class InnerThreadDemo {
	public static void main(String[] args) {
		new Thread() {
			public void run() {
				for (int i = 0; i < 5; i++) {
					System.out.println(Thread.currentThread().getName() + i + "HI");
				}
			}
		}.start();

		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 5; i++) {
					System.out.println(Thread.currentThread().getName() + i + "HI");
				}
			}
		}).start();

	}

}
