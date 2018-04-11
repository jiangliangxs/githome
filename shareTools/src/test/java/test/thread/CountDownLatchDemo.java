package test.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			ExecutorService executor = Executors.newCachedThreadPool();

			CountDownLatch latch = new CountDownLatch(3);

			Worker w1 = new Worker(latch, Thread.currentThread().getName() + "张三");
			Worker w2 = new Worker(latch, Thread.currentThread().getName() + "李四");
			Worker w3 = new Worker(latch, Thread.currentThread().getName() + "王二");

			Boss boss = new Boss(latch);

			executor.execute(boss);
			executor.execute(w3);
			executor.execute(w2);
			executor.execute(w1);

			executor.shutdown();

		}
	}

}
