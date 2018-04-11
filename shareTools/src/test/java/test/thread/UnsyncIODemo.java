package test.thread;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UnsyncIODemo {
	List<String> buf = new ArrayList<String>();

	public class WriterThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					if (buf.isEmpty()) {
						try {
							 Thread.sleep(5000);
							// 挂起当前线程,停止线程
//							this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					PrintWriter out = new PrintWriter(new FileWriter("a.txt", true));
					for (String s : buf){
						out.println(s);
					}
					buf.clear();
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class ReadThread extends Thread {
		@Override
		public void run() {
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			String str = null;
			try {
				while ((str = console.readLine()) != null) {
					System.out.println("str:" + str);
					buf.add(str);
				}
				console.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void go() throws IOException {
		WriterThread t1 = new WriterThread();
		ReadThread t2 = new ReadThread();
		t1.start();
		t2.start();
	}

	public static void main(String[] args) {
		try {
			new UnsyncIODemo().go();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
