package test.io;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

public class TestFileUtils extends TestCase {
	public String s_1 = null;
	public int i_1 = 0;
	public long l_1 = 0;
	public List<String> ls_1;

	public void test1() throws Exception {
		// FileUtils.copyDirectory(new File(TestFileConstant.SRC_FOLDER), new File(TestFileConstant.SRC_FOLDER2));
		// s_1 = FilenameUtils.getExtension(TestFileConstant.SRC_FILE);
		// System.out.println("1:" + s_1);
		// s_1 = FilenameUtils.getExtension(TestFileConstant.SRC_FILE2);
		// System.out.println("2:" + s_1);
		// s_1 = FilenameUtils.getExtension(TestFileConstant.SRC_FOLDER);
		// System.out.println("3:" + s_1);
		// s_1 = FilenameUtils.getBaseName(TestFileConstant.SRC_FILE);
		// System.out.println("4:" + s_1);
		// s_1 = FilenameUtils.removeExtension(TestFileConstant.SRC_FILE);
		// System.out.println("5:" + s_1);
		// i_1 = FilenameUtils.indexOfExtension(TestFileConstant.SRC_FILE);
		// System.out.println("6:" + i_1);
		// s_1 = FilenameUtils.getName(TestFileConstant.SRC_FILE);
		// System.out.println("7:" + s_1);
		// FileUtils.writeStringToFile(new File(TestFileConstant.SRC_FILE4), TestFileConstant.CONTENT_TEXT);
		// FileUtils.deleteDirectory(new File(TestFileConstant.SRC_FOLDER2));
		// s_1 = FileUtils.readFileToString(new File(TestFileConstant.SRC_FILE),"GBK");
		// System.out.println("8:" + s_1);
		// System.out.println("9:" + s_1.startsWith("嗯"));
		// l_1 = new File(TestFileConstant.SRC_FILE).length();
		// System.out.println("10:" + l_1);
		// s_1 = FileUtils.readFileToString(new File(TestFileConstant.ENCODING_FILE1), "UTF-8");
		// System.out.println(s_1 + "\n" + s_1.startsWith("#"));
		ls_1 = FileUtils.readLines(new File(TestFileConstant.ENCODING_FILE), "UTF-8");
		for (String ls : ls_1) {
			System.out.println(ls + "\t" + ls.startsWith("#"));
		}
	}

}
