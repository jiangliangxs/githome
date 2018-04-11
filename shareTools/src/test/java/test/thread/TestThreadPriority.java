package test.thread;

public class TestThreadPriority {
	public static void main(String[] args) {
		new TestThreadPriority_Thread("低级", Thread.MIN_PRIORITY).start();
		new TestThreadPriority_Thread("高级", Thread.MAX_PRIORITY).start();
		new TestThreadPriority_Thread("中级", Thread.NORM_PRIORITY).start();
	}

}

class TestThreadPriority_Thread extends Thread {
	public TestThreadPriority_Thread(String name, int pro) {
		super(name);// 设置线程的名称
		this.setPriority(pro);// 设置优先级
	}

	@Override
	public void run() {
		for (int i = 0; i < 30; i++) {
			System.out.println(this.getName() + "线程第" + i + "次执行！");
			if (i % 5 == 0)
				Thread.yield();
		}
	}
}