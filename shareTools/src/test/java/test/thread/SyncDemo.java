package test.thread;

public class SyncDemo {
	Object monitor = new Object();
	int i = 1;

	// 整个方法同步
	// public synchronized int getNumber(){
	public int getNumber() {
		// 某个部分代码块同步
		synchronized (monitor) {// 可以使用this
			if (i == 20) {
				throw new RuntimeException("Over");
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			return i++;
		}
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
		new SyncDemo().go();
	}

}
