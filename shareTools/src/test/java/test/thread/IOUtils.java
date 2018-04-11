package test.thread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class IOUtils {
	public static byte[] read(String filename) throws IOException {
		return read(new FileInputStream(filename));
	}

	public static byte[] read(File file) throws IOException {
		return read(new FileInputStream(file));
	}

	public static byte[] read(InputStream in) throws IOException {
		byte[] buf = new byte[in.available()];
		in.read(buf);
		in.close();
		return buf;
	}

	public static void write(byte[] buf, String filename) throws IOException {
		write(buf, filename, false);
	}

	public static void append(byte[] buf, String filename) throws IOException {
		write(buf, filename, true);
	}

	private static void write(byte[] buf, String filename, boolean append) throws IOException {
		OutputStream out = new FileOutputStream(filename, append);
		out.write(buf);
		out.close();
	}

	public static String toHexString(byte[] buf) {
		StringBuilder s = new StringBuilder();
		for (byte b : buf) {
			String hex = Integer.toHexString(b & 0xff);
			hex = leftPad(hex, '0', 2);
			s.append(hex).append(" ");
		}
		return s.toString();
	}

	private static String leftPad(String hex, char c, int size) {
		char[] cs = new char[size];
		Arrays.fill(cs, c);
		System.arraycopy(hex.toCharArray(), 0, cs, cs.length - hex.length(), hex.length());
		return new String(cs);
	}

	public static String toBinString(byte[] buf) {
		StringBuilder s = new StringBuilder();
		for (byte b : buf) {
			String hex = Integer.toBinaryString(b & 0xff);
			hex = leftPad(hex, '0', 8);
			s.append(hex).append(" ");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		byte[] ary = { 1, 4, 0, 78 };
		System.out.println(toBinString(ary));
	}

	/**
	 * 
	 * @param filename
	 *            abc.tar -> abc.tar.0 ~ abc.tar.5
	 * @param size
	 *            n个Byte
	 * @throws IOException
	 */
	public static void split(String filename, int size) throws IOException {
		int index = 0;// 输出文件序号
		String file = filename + "." + index++; // 输出文件名
		InputStream in = new BufferedInputStream(new FileInputStream(filename));
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		int b;
		int i = 0;
		while ((b = in.read()) != -1) {
			out.write(b);
			i++;
			if (i % size == 0) {// 文件满了, 关闭当前out流, 打开下一个文件
				out.close();
				file = filename + "." + index++;
				out = new BufferedOutputStream(new FileOutputStream(file));
			}
		}
		in.close();
		out.close();
	}

	public static void join(String filename) throws IOException {
		File infile = new File(filename);
		if (!infile.exists()) {
			throw new IOException("文件不存在");
		}
		String file = filename.substring(0, filename.lastIndexOf('.'));
		int index = Integer.parseInt(filename.substring(filename.lastIndexOf('.') + 1));
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		while (true) {
			File f = new File(file + "." + index++);
			if (!f.exists()) {
				break;
			}
			InputStream in = new BufferedInputStream(new FileInputStream(f));
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
		out.close();
	}

	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(obj);
		oos.close();
		return out.toByteArray();
	}

	public static Object unserialize(byte[] ary) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(ary);
		ObjectInputStream ois = new ObjectInputStream(in);
		Object o = ois.readObject();
		ois.close();
		return o;
	}

}
