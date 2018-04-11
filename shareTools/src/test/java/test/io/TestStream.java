package test.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.junit.Test;

public class TestStream {
	@Test
	public void testM1() throws Exception{
		FileInputStream fis = new FileInputStream(new File("E:/document/file/dir1/b.TXT"));
		BufferedReader br = new BufferedReader(new InputStreamReader(fis,"GBK"));
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line+"\t"+String.valueOf(line.trim().charAt(0)).hashCode()+"\t"+line.startsWith("#"));
		}
		br.close();
		fis.close();
	}
}
