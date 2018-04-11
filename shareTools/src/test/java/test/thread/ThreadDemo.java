package test.thread;

public class ThreadDemo {
	public static void main(String[] args) {
		MyThread t = new MyThread();
		YourThread t2 = new YourThread();
		WhoAmIThread t3 = new WhoAmIThread();
		WhoAUThread t4 = new WhoAUThread();
		t4.setDaemon(true);
		t3.setPriority(Thread.MAX_PRIORITY - 1);
		t.start();
		// Thread.yield();//当前线程让出处理器, 使当前线程进入Runnable等待
		t2.start();
		t3.start();
		t4.start();
		System.out.println("Over!");
	}
	private static class WhoAUThread extends Thread {
		public void run() {
			for (int i = 0; i < 100; i++) {
				System.out.println("你是谁呀?");
				// Thread.yield();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Bye!");
		}
	}

	private static class WhoAmIThread extends Thread {
		public void run() {
			for (int i = 0; i < 100; i++) {
				System.out.println("我是谁呀?");
				Thread.yield();
			}
		}
	}

	private static class YourThread extends Thread {
		public void run() {
			for (int i = 0; i < 100; i++) {
				System.out.println("你干啥那?");
				Thread.yield();
			}
		}
	}

	private static class MyThread extends Thread {
		public void run() {
			for (int i = 0; i < 100; i++) {
				System.out.println("我抽出皮筋作弹弓子," + "打你们家玻璃!");
				if (i % 2 == 0) {
					Thread.yield();
				}
			}
		}
	}
}


