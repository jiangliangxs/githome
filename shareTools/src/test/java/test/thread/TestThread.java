package test.thread;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import zj.java.util.JavaUtil;

public class TestThread {
	public static void main(String[] args) throws InterruptedException {
		// 休眠终止线程_A();
		卖票_共同任务_A();
		// 卖票_不同任务_A();
	}

	public static void 休眠终止线程_A() throws InterruptedException {
		for (int i = 1; i <= 5; i++) {
			Thread thread = new Thread(new 休眠终止线程_A(i), "线程名" + i);
			// 启动线程
			thread.start();
		}
	}

	public static void 卖票_共同任务_A() throws InterruptedException {
		卖票_任务_A mt = new 卖票_任务_A(JavaUtil.RandomUtil.getInt(5, 10));
		for (int i = 1; i <= 5; i++) {
			Thread t1 = new Thread(mt, i + "号窗口");
			t1.start();
		}
	}

	public static void 卖票_不同任务_A() throws InterruptedException {
		for (int i = 1; i <= 5; i++) {
			卖票_任务_A mt = new 卖票_任务_A(JavaUtil.RandomUtil.getInt(5, 10));
			Thread t1 = new Thread(mt, i + "号窗口");
			t1.start();
		}
	}
}

class 休眠终止线程_A implements Runnable {
	private int i;

	public 休眠终止线程_A(int i) {
		this.i = i;
	}

	@Override
	public void run() {
		if (i == 4 || i == 2) {
			System.out.println(Thread.currentThread().getName() + "进入休眠状态...3");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(Thread.currentThread().getName() + "进入工作状态...");
		}
	}
}

class 卖票_任务_A implements Runnable {
	private int ticket = 10;

	public 卖票_任务_A(int ticket) {
		this.ticket = ticket;
	}

	@Override
	public void run() {
		while (true) {
			if (this.ticket > 0) {
				System.out.println(Thread.currentThread().getName() + "卖票---->" + (this.ticket--));
			} else {
				System.out.println(Thread.currentThread().getName() + "卖票完毕!");
				break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}