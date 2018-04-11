package test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 */
public class TestThread_6 {

	public static void main(String args[]) throws InterruptedException {
		ExecutorService exe = Executors.newFixedThreadPool(50);
		for (int i = 1; i <= 115; i++) {
			exe.execute(new SubThread(i));
		}
		exe.shutdown();
		while (true) {
			if (exe.isTerminated()) {
				System.out.println("结束了！");
				break;
			}
			Thread.sleep(1);
		}
	}
}

class SubThread extends Thread {

	private final int i;

	public SubThread(int i) {
		this.i = i;
	}

	@Override
	public void run() {
		System.out.println(i);
	}
}
