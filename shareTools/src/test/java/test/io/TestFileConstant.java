package test.io;

import java.util.UUID;

import zj.io.util.FileUtil;

import junit.framework.TestCase;

public class TestFileConstant extends TestCase {
	public static final String SRC_FOLDER = "E:/document/file/dir1";
	public static final String SRC_FOLDER2 = "D:/dir1";
	public static final String SRC_FILE = "E:/document/file/dir1/66008012b0248cee78197_CCBHMKFREC1.doc.zip..txt";
	public static final String SRC_FILE2 = "D:/66008012b0248cee78197_CCBHMKFREC1.doc.zip..txt";
	public static final String SRC_FILE3 = "D:/"+UUID.randomUUID().toString()+".txt";
	public static final String SRC_FILE4 = "E:/document/file/dir1/"+UUID.randomUUID()+"/"+UUID.randomUUID().toString()+".txt";
	public static final String SRC_FILE5 = "E:/document/file/dir1/"+UUID.randomUUID().toString()+".txt";
	public static final String DESTINATION_FOLDER = "E:/document/file/dir2";
	public static final String DESTINATION_FOLDER2 = "D:/dir2";
	public static final String CONTENT_TEXT = "我是中国人"+FileUtil.LINE_SEPARATOR+"呵呵,您好啊";
	
	public static final String ENCODING_FILE = "E:/document/file/dir1/a.TXT";
	public static final String ENCODING_FILE1 = "E:/document/file/dir1/c.TXT";
}
