package test.thread;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//选项理解
public class WaitNotifyIODemo {
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
								System.out.println("等待...");
								this.wait();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					PrintWriter out = new PrintWriter(new FileWriter("nusyncio.txt", true));
					out.println(buf);
					System.out.println("Writing...");
					buf.clear();
					out.close();
				}
			} catch (IOException e) {
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
						System.out.println("通知写入...");
						w.notify();
					}
					if ("exit".equalsIgnoreCase(str)) {
						break;
					}
				}
			} catch (IOException e) {
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
		new WaitNotifyIODemo().go();
	}

}
