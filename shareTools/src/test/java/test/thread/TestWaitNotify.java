package test.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//选项理解
public class TestWaitNotify {
	WriteThread w = new WriteThread();
	ReadThread r = new ReadThread();
	List<String> buf = new ArrayList<String>();

	class WriteThread extends Thread {
		public void run() {
			try {
				while (true) {
					if (buf.isEmpty()) {
						try {
							synchronized (this) {
								System.out.println("调用wait()方法等待输入...");
								this.wait();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					System.out.println("写入内容:" + buf);
					System.out.println("Writing...");
					buf.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class ReadThread extends Thread {
		public void run() {
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			String str;
			try {
				while ((str = console.readLine()) != null) {
					buf.add(str);
					synchronized (w) {
						System.out.println("调用notify通知写入...");
						w.notify();
					}
					if ("exit".equalsIgnoreCase(str)) {
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void go() {
		w.setDaemon(true);
		w.start();
		r.start();
	}

	public static void main(String[] args) {
		new TestWaitNotify().go();
	}

}
