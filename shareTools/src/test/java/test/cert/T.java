package test.cert;

import java.io.File;

public class T {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char SEP = File.separatorChar;
		System.out.println(System.getProperty("java.home") + SEP + "lib" + SEP + "security");
		File file = new File("jssecacerts");
		File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security");
		file = new File(dir, "jssecacerts");
		System.out.println(file.isFile());
	}

}
