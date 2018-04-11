package test.thread;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {
	public static void main(String[] args) {
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			int i = 10;
			public void run() {
				System.out.println("还有:" + i--);
				if (i == 0) {
					t.cancel();
					System.out.println("我会回来的! I will b back");
				}
			}
			// 2000毫秒以后每隔1000毫秒去执行run方法
		}, 2000, 1000);
	}

}
