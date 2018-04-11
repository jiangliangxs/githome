package test.thread;

public class SleepDemo {
	public static void main(String[] args) {
		Thread t = new Thread() {
			public void run() {
				for (int i = 0; i < 5; i++) {
					System.out.println(Thread.currentThread().getName() + i + "睡觉");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// e.printStackTrace();
						System.out.println(Thread.currentThread().getName() + i + "干嘛那! 不睡觉了,破相了!");
						break;
					}
				}
			}
		};
		t.start();

		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + i + "砸墙");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				System.out.println(Thread.currentThread().getName() + i + "干嘛那! 不砸墙了,破相了!");
			}
			if (i % 2 == 0) {
				// 当前线程打断线程此循环的sleep
				Thread.currentThread().interrupt();
			}
		}
		System.out.println("砸穿了!");
		// 抛出InterruptedException异常
		t.interrupt();// 当前线程打断线程t的sleep
	}

}
