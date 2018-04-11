package test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestExecutorService {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// 创建一个固定大小的线程池
		ExecutorService service = Executors.newFixedThreadPool(3);
		// 可以发现线程被循环创建，但是启动线程却不是连续的，而是由ExecutorService决定的。
		for (int i = 0; i < 10; i++) {
			System.out.println("开始创建线程" + i);
			Runnable run = new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName()+"启动线程休眠开始");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"启动线程休眠结束");
				}
			};
			// 在未来某个时间执行给定的命令。该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor实现决定。
			service.execute(run);
			System.out.println("结束创建线程" + i);
		}
		// 关闭启动线程
		service.shutdown();
		// 等待子线程结束，再继续执行下面的代码
		// 请求关闭、发生超时或者当前线程中断，无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行。既是等待所有子线程执行结束。
		// service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		service.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println("all thread complete");
	}

}
