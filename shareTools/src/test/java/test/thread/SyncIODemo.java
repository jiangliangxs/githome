package test.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class SyncIODemo {
	// 同步读取控制台写到一个文本文件
	public static void main(String[] args) throws Exception {
		m2();
	}

	public static void m2() throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in, "UTF-8");
		BufferedReader console = new BufferedReader(isr);
		// PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("syncio.txt"),"UTF-8"), true);
		PrintWriter out = new PrintWriter(new FileWriter("syncio.txt", true), true);
		// PrintWriter out = new PrintWriter(System.out, true);
		String str;
		while ((str = console.readLine()) != null) {
			System.out.println("str:" + str);
			out.println(str);
			if ("exit".equalsIgnoreCase(str)) {
				break;
			}
		}
		out.close();
	}

	public static void m1() throws IOException {
		String str = " 中文测试，这是内部硬编码的串" + " test english character";
		String strin = "";
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "utf-8")); // 设置输入接口按中文编码
		BufferedWriter stdout = new BufferedWriter(new OutputStreamWriter(System.out, "utf-8")); // 设置输出接口按中文编码
		stdout.write("请输入:");
		stdout.flush();
		strin = stdin.readLine();
		stdout.write("这是从用户输入的串：" + strin);
		stdout.write(str);
		stdout.flush();
	}

}
