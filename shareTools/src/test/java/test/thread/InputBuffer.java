package test.thread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 异步的磁盘写操作
 * 
 * @author Administrator
 * 
 */
// public class InputBuffer {
//
// }

public class InputBuffer {
	public List<String> lines = Collections.synchronizedList(new ArrayList<String>());
	public WriterThread wt;

	private boolean over = false;

	public synchronized void over() {
		over = true;
	}

	public synchronized boolean isOver() {
		return over;
	}

	public void go() throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("a");
		StringBuilder sb2 = new StringBuilder();
		sb2.append("a");
	}

	public class WriterThread extends Thread {
		PrintWriter out = null;

		public WriterThread() throws IOException {
			out = new PrintWriter(new FileWriter("lines.txt"));
		}

		public void run() {
			// os.flush()
			// lines.clear()
		}
	}

	public static void main(String[] args) throws Exception {
		InputBuffer input = new InputBuffer();
		input.go();
	}
}
