package test.thread;

/** 
 * <br> 
 * do what you want to do and never stop it. 
 * <br> 
 */

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jack Jul 13, 2014 <br>
 */
public class TestSingleThreadExecutor implements Runnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestSingleThreadExecutor demo = new TestSingleThreadExecutor();
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(demo);

	}

	// 在线程池中，有一个是专门定义单线程的线程池，是Executors.newFixedThreadPool。为什么要单独定义一个池，这个池有什么特别的地方呢？
	// 1.主要是这个线程池可以在挂掉或者出异常的情况下，重新启动一个线程来执行接下去的任务。这个对开发人员就比较方便了。
	public void run() {
		int temp = 0;
		int i = 0;
		while (true) {
			int j = new Random().nextInt(100);
			System.out.println("temp=" + temp + ",j=" + j + ",i=" + i++);
			try {
				if (temp == 0 && j > 90) {
					temp = j / 0;
				}
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				temp = 0;
			}

		}

	}

}
