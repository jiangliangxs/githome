package test.thread;

public class SyncErrDemo {
	int i = 1;

	public int getNumber() {
		// 临界资源
		if (i == 20) {
			throw new RuntimeException("Over");
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + "中断了");
		}
		return i++;
	}

	class MyThread extends Thread {
		public void run() {
			while (true) {
				System.out.println(getNumber());
			}
		}
	}

	public void go() {
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		t1.start();
		t2.start();
	}

	public static void main(String[] args) {
		new SyncErrDemo().go();
	}

}
