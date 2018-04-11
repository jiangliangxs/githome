package test.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.TestCase;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import zj.io.model.JarClass;
import zj.io.model.JarParams;
import zj.io.service.FileFilterAbsImpl;
import zj.io.service.ReadLinesBatchCallAbsImpl;
import zj.io.service.ReadLinesCallAbsImpl;
import zj.io.util.ConstantForEnum;
import zj.io.util.FileUtil;
import zj.java.util.JavaUtil;

public class TestFileUtil extends TestCase {
	public String[] s_ary_1 = null;
	public String s_1 = null;
	public int i_1 = 0;
	public long l_1 = 0;

	public void testGetWorth() {
		String worth = getWorthByFundNo("E:/versionManager/sources/java/zj-model/integration/zj-utils-jdk1.6/src/test/java/test/io/FundInfo_20150702.txt","000008");
		System.out.println(worth);
	}

	// 根据净值文件的基金编号，查净值
	public String getWorthByFundNo(String worthPath, String fundNo) {
		String worth = "";
		File fundInfo = null;
		fundInfo = new File(worthPath);
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(fundInfo), "GBK");
			BufferedReader bufferedReader = new BufferedReader(isr);
			String lineTxt = null;
			String[] lineStr = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				if (lineTxt.length() > 10) {
					lineStr = lineTxt.split("\\s+");
					if (fundNo.equals(lineStr[0].substring(0, 6))) {
						worth = new DecimalFormat("0.000").format(Double.parseDouble(lineStr[1].substring(1, 5)) / 1000);
					}
				}
			}
			isr.close();
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return worth;
	}

	public void testm4() {
		System.out.println(new File("log.txt").getAbsolutePath());
	}

	public void testm3() {
		List<File> fileList = new ArrayList<File>();
		String dir = "E:\\versionManager\\sources\\java";
		FileUtil.setFilterFiles(fileList, dir, new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isAbsolute()) {
					return true;
				}
				if (pathname.getName().contains("Menu")) {
					return true;
				}
				System.out.println(pathname.getAbsolutePath());
				return false;
			}

		});
		for (File file : fileList) {
			System.out.println(file.getAbsolutePath());
		}
	}

	public void testm2() {
		// System.out.println(new File("C:/backup/mybatis/" + DateUtil.dateParse(new Date(), "yyyyMMddHHmmSSS")+"/aa.txt").getParent());
		System.out.println(FilenameUtils.getBaseName("持有人份额查询_2015-10-31.xls"));
		System.out.println(JavaUtil.split("持有人份额查询--2015-10-31.xls", "--").length);
		System.out.println(JavaUtil.split("持有人份额查询--2015-10-31.xls", "_").length);
		System.out.println(JavaUtil.split("持有人份额查询_2015-10-31.xls", "_").length);
	}

	public void testM1() {
		// data:image/jpeg;base64,
		String strImg = "data:image/jpeg;base64,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";
		FileUtil.GenerateImage(strImg, "D:\\wangyc.jpg");
		// 测试从图片文件转换为Base64编码
		System.out.println(FileUtil.GetImageStr("d:\\wangyc.jpg"));
	}

	@Test
	public void other() {
		System.out.println("-------------ADD_ALL-----------------");
		System.out.println(FileUtil.changePathSeparator("A\\B\\C", ConstantForEnum.ChangePathLastSeparator.ADD_ALL));
		System.out.println(FileUtil.changePathSeparator("\\A\\B\\C", ConstantForEnum.ChangePathLastSeparator.ADD_ALL));
		System.out.println(FileUtil.changePathSeparator("A\\B\\C\\", ConstantForEnum.ChangePathLastSeparator.ADD_ALL));
		System.out.println("-------------ADD_BEFORE-----------------");
		System.out.println(FileUtil.changePathSeparator("A\\B\\C", ConstantForEnum.ChangePathLastSeparator.ADD_BEFORE));
		System.out.println(FileUtil.changePathSeparator("\\A\\B\\C", ConstantForEnum.ChangePathLastSeparator.ADD_BEFORE));
		System.out.println(FileUtil.changePathSeparator("A\\B\\C\\", ConstantForEnum.ChangePathLastSeparator.ADD_BEFORE));
		System.out.println("-------------ADD_AFTER-----------------");
		System.out.println(FileUtil.changePathSeparator("A\\B\\C", ConstantForEnum.ChangePathLastSeparator.ADD_AFTER));
		System.out.println(FileUtil.changePathSeparator("\\A\\B\\C", ConstantForEnum.ChangePathLastSeparator.ADD_AFTER));
		System.out.println(FileUtil.changePathSeparator("A\\B\\C\\", ConstantForEnum.ChangePathLastSeparator.ADD_AFTER));
		System.out.println("-------------DEL_ALL-----------------");
		System.out.println(FileUtil.changePathSeparator("A\\B\\C", ConstantForEnum.ChangePathLastSeparator.DEL_ALL));
		System.out.println(FileUtil.changePathSeparator("\\A\\B\\C", ConstantForEnum.ChangePathLastSeparator.DEL_ALL));
		System.out.println(FileUtil.changePathSeparator("A\\B\\C\\", ConstantForEnum.ChangePathLastSeparator.DEL_ALL));
		System.out.println("-------------DEL_BEFORE-----------------");
		System.out.println(FileUtil.changePathSeparator("A\\B\\C", ConstantForEnum.ChangePathLastSeparator.DEL_BEFORE));
		System.out.println(FileUtil.changePathSeparator("\\A\\B\\C", ConstantForEnum.ChangePathLastSeparator.DEL_BEFORE));
		System.out.println(FileUtil.changePathSeparator("A\\B\\C\\", ConstantForEnum.ChangePathLastSeparator.DEL_BEFORE));
		System.out.println("-------------DEL_AFTER-----------------");
		System.out.println(FileUtil.changePathSeparator("A\\B\\C", ConstantForEnum.ChangePathLastSeparator.DEL_AFTER));
		System.out.println(FileUtil.changePathSeparator("\\A\\B\\C", ConstantForEnum.ChangePathLastSeparator.DEL_AFTER));
		System.out.println(FileUtil.changePathSeparator("A\\B\\C\\", ConstantForEnum.ChangePathLastSeparator.DEL_AFTER));
		System.out.println("-------------NONE-----------------");
		System.out.println(FileUtil.changePathSeparator("A\\B\\C", ConstantForEnum.ChangePathLastSeparator.NONE));
		System.out.println(FileUtil.changePathSeparator("\\A\\B\\C", ConstantForEnum.ChangePathLastSeparator.NONE));
		System.out.println(FileUtil.changePathSeparator("A\\B\\C\\", ConstantForEnum.ChangePathLastSeparator.NONE));

	}

	@Test
	public void test11() throws Exception {
		// _11(ConstantForEnum.ChangePathLastSeparator.DEL);
		// _11(null);
		String window = null;
		String linux = null;
		window = "D:\\a\\b\\c\\temp";
		linux = "D:/a/b/c/temp";
		// window = "D:\\a\\b\\c\\temp\\";
		// linux = "D:/a/b/c/temp/";
		String f = null;
		f = FileUtil.changePathSeparator(window);
		System.out.println(f);
		f = FileUtil.changePathSeparator(window, null);
		System.out.println(f);
		f = FileUtil.changePathSeparator(window, ConstantForEnum.ChangePathLastSeparator.NONE);
		System.out.println(f);
		f = FileUtil.changePathSeparator(window, ConstantForEnum.ChangePathLastSeparator.ADD_AFTER);
		System.out.println(f);
		f = FileUtil.changePathSeparator(window, ConstantForEnum.ChangePathLastSeparator.DEL_AFTER);
		System.out.println(f);
		System.out.println("----------------------------");
		f = FileUtil.changePathSeparator(linux);
		System.out.println(f);
		f = FileUtil.changePathSeparator(linux, null);
		System.out.println(f);
		f = FileUtil.changePathSeparator(linux, ConstantForEnum.ChangePathLastSeparator.NONE);
		System.out.println(f);
		f = FileUtil.changePathSeparator(linux, ConstantForEnum.ChangePathLastSeparator.ADD_AFTER);
		System.out.println(f);
		f = FileUtil.changePathSeparator(linux, ConstantForEnum.ChangePathLastSeparator.DEL_AFTER);
		System.out.println(f);
	}

	public void _11(ConstantForEnum.ChangePathLastSeparator sepEnum) {
		System.out.println(ConstantForEnum.ChangePathLastSeparator.ADD_AFTER == sepEnum);
		if (sepEnum != null)
			switch (sepEnum) {
			case ADD_AFTER:
				System.out.println("add");
				break;
			case DEL_AFTER:
				System.out.println("del");
				break;
			case NONE:
				System.out.println("none");
				break;
			default:
				System.out.println("...");
				break;
			}
	}

	@Test
	public void test10_1() throws Exception {
		String dir = "E:/success/OK/excel/jxls-core";
		List<String> f = FileUtil.findClassConflictJar(dir, "net.sf.jxls.util.ConditionalFormatting");
		System.out.println(f);
	}

	@Test
	public void test10() throws Exception {
		String dir = "E:/document/zj-utils/file";
		dir = "E:/bigfile/services/java/tomcat6.0.37-8";
		List<File> fileList = new ArrayList<File>();
		FileUtil.setFilterFiles(fileList, dir, new FileFilterAbsImpl() {
			@Override
			public boolean accept(File file) {
				// System.out.println(this.getLevel() + "," + file.getAbsolutePath());
				if (file.isFile()) {
					if (file.getAbsolutePath().indexOf("webapps") > -1 && file.getAbsolutePath().indexOf("WEB-INF\\lib") > -1) {
						// String extension = FilenameUtils.getBaseName(file.getAbsolutePath());
						// if (CheckUtil.isNotNull(extension)) {
						// if ("jar".equals(extension)) {
						// return true;
						// }
						// }
						if ("jsp-api.jar".equals(file.getName()) || "servlet-api.jar".equals(file.getName())) {
							return true;
						}
					}
					return false;
				} else {
					return true;
				}
			}

			@Override
			public boolean acceptDir(File file) {
				return false;
			}
		});
		for (File file : fileList) {
			System.out.println(file.getAbsolutePath());
			file.delete();
		}
		System.out.println(fileList.size());
		// File[] files = new File("E:/bigfile/services/java/tomcat6.0.37-8").listFiles(new FileFilter(){
		// @Override
		// public boolean accept(File pathname) {
		// System.out.println(pathname);
		// return true;
		// }
		// });
		// System.out.println(files.length);

	}

	@Test
	public void test9() throws Exception {
		System.out.println(FileUtil.readStrToObject(FileUtil.writeObjectToStr(new FileUtil())));
	}

	@Test
	public void test8() throws Exception {
		Map<String, Object> xmlMap = new HashMap<String, Object>();
		Map<String, Object> xmlMap2 = new HashMap<String, Object>();
		xmlMap2.put("c", "d");
		xmlMap.put("a", xmlMap2);
		System.out.println(FileUtil.readStrToObject(FileUtil.writeObjectToStr(xmlMap)));
		// System.out.println(readObject(writeObject(xmlMap)));
	}

	@Test
	public void test7() throws Exception {
		// FileUtil.writeObjectToFile("abc", "D:/a.txt");
		// System.out.println(FileUtil.readFileToObject("D:/a.txt"));
		System.out.println(FileUtil.readStrToObject(FileUtil.writeObjectToStr("a中国bc")));
	}

	@Test
	public void test6() throws IOException {
		String delFolder = "E:/document/file/dir1/del/zip/unzip";
		List<File> fileList = new ArrayList<File>();
		FileUtil.setFilterFiles(fileList, delFolder, new FileFilterAbsImpl() {
			@Override
			public boolean acceptFile(File file) {
				boolean flag = false;
				flag = file.getName().equals("600418223751300.wav");
				return flag;
			}

			@Override
			public boolean acceptDir(File file) {
				// return file.getName().equals("a");
				return false;
			}

			@Override
			public boolean interrupt(File file, List<File> fileList) {
				if (fileList.size() == 1) {
					return true;
				}
				return false;
			}
		});
		for (File file : fileList) {
			System.out.println(file.getAbsolutePath());
		}
		System.out.println(fileList.size());
	}

	public void mobj1(Object o) {
		System.out.println("o:" + o);
	}

	public void mobj1(String s) {
		System.out.println("s:" + s);
	}

	@Test
	public void test5_2() throws IOException {
		mobj1(123);
	}

	public static void main(String[] args) {

		// String libPath = "E:/document/zj-utils/jar/1"; //
		// String libPath = "E:/must-backup/lib/jar-manager/zj-util";
		String libPath = "E:/versionManager/sources/java/zj-web/songzhi/ebizdb-mysql-20150623/WebRoot/WEB-INF/lib";
		JarParams params = new JarParams();
		params.setPath(libPath);
		FileUtil.findJarConflictClass(params);
		params.writeLog("E:/document/zj-utils/jar/1");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$");
		params.toString();
		// Map<String, Set<JarClass>> jarClassResult = params.getJarClassResult();
		// Map<String, Set<String>> classResult = params.getClassResult();
		// Map<String, Set<String>> conflictResult = params.getConflictResult();
		// System.out.println("---------------------运行结果如下---------------------");
		// // for (String jarPath : jarClassResult.keySet()) {
		// // System.out.println("文件名:" + jarPath);
		// // Set<JarClass> jarClasss = jarClassResult.get(jarPath);
		// // for (JarClass jarClass : jarClasss) {
		// // System.out.println("    类对应的文件全路径:" + jarClass.getFullPath());
		// // System.out.println("    文件目录路径:" + jarClass.getFileDir());
		// // System.out.println("    文件名中的class路径:" + jarClass.getClassPath());
		// // System.out.println("    类对应的java包路径:" + jarClass.getClassName());
		// // }
		// // }
		// System.out.println("---------------------------------");
		// System.out.println("冲突文件jar如下:");
		// System.out.println("---------------------------------");
		// for (String className : conflictResult.keySet()) {
		// System.out.println("包路径:" + className);
		// Set<String> jarPaths = conflictResult.get(className);
		// System.out.println("=================================");
		// for (String jarPath : jarPaths) {
		// System.out.println("jar文件:" + jarPath);
		// }
		// System.out.println("**********************************");
		// }

	}

	@Test
	public void test5_3() throws IOException {
		// String libPath = "E:/document/zj-utils/jar/1"; //
		// String libPath = "E:/must-backup/lib/jar-manager/zj-util";
		String libPath = "E:/versionManager/sources/java/zj-web/songzhi/ebizdb-mysql-20150623/WebRoot/WEB-INF/lib";
		JarParams params = new JarParams();
		params.setPath(libPath);
		FileUtil.findJarConflictClass(params);
		params.writeLog("E:/must-backup/lib/jar-manager/zj-util");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$");
		params.toString();
		// Map<String, Set<JarClass>> jarClassResult = params.getJarClassResult();
		// Map<String, Set<String>> classResult = params.getClassResult();
		// Map<String, Set<String>> conflictResult = params.getConflictResult();
		// System.out.println("---------------------运行结果如下---------------------");
		// // for (String jarPath : jarClassResult.keySet()) {
		// // System.out.println("文件名:" + jarPath);
		// // Set<JarClass> jarClasss = jarClassResult.get(jarPath);
		// // for (JarClass jarClass : jarClasss) {
		// // System.out.println("    类对应的文件全路径:" + jarClass.getFullPath());
		// // System.out.println("    文件目录路径:" + jarClass.getFileDir());
		// // System.out.println("    文件名中的class路径:" + jarClass.getClassPath());
		// // System.out.println("    类对应的java包路径:" + jarClass.getClassName());
		// // }
		// // }
		// System.out.println("---------------------------------");
		// System.out.println("冲突文件jar如下:");
		// System.out.println("---------------------------------");
		// for (String className : conflictResult.keySet()) {
		// System.out.println("包路径:" + className);
		// Set<String> jarPaths = conflictResult.get(className);
		// System.out.println("=================================");
		// for (String jarPath : jarPaths) {
		// System.out.println("jar文件:" + jarPath);
		// }
		// System.out.println("**********************************");
		// }
	}

	@Test
	public void test5_1_2() throws IOException {
		String isJar = "E:/must-backup/lib/proguard/jar/DownloadUtil.class";
		Set<String> isJarFiles = new HashSet<String>();
		FileUtil.readLines(isJarFiles, new File(isJar), "UTF-8");
		System.out.println(isJarFiles.size());
		for (String s : isJarFiles) {
			System.out.println(s);
		}
	}

	@Test
	public void test5_1_1() throws IOException {
		String isJar = "E:/must-backup/lib/proguard/jar/a.txt";
		Set<String> isJarFiles = new HashSet<String>();
		FileUtil.readLines(isJarFiles, new File(isJar), "UTF-8");
		System.out.println(isJarFiles.size());
		Set<String> isJarFilesName = new HashSet<String>();
		for (String s : isJarFiles) {
			isJarFilesName.add(new File(s).getName());
			System.out.println(new File(s).getName());
		}
		String isJarDir = "E:/must-backup/lib/proguard/jar/toolsweb";
		for (File f : new File(isJarDir).listFiles()) {
			if (!isJarFilesName.contains(f.getName())) {
				f.delete();
			}
		}
	}

	@Test
	public void test5_1() throws IOException {
		Map<String, Set<JarClass>> jarClassResult = new ConcurrentHashMap<String, Set<JarClass>>();
		Map<String, Set<String>> classResult = new ConcurrentHashMap<String, Set<String>>();
		Map<String, Set<String>> conflictResult = new ConcurrentHashMap<String, Set<String>>();
		// String libPath = "E:/document/zj-utils/jar/1"; //
		// String libPath = "E:/must-backup/lib/jar-manager/zj-util";

		String libPath = "E:/must-backup/lib/proguard/jar/toolsweb";
		libPath = "E:/bigfile/services/java/tomcat6.0.37-8/tomcat6.0.37-8120/webapps/msth-finance/WEB-INF/lib";
		libPath = "E:/versionManager/sources/java/zj-web/ebiz/splider/WebRoot/WEB-INF/lib";
		JarParams params = new JarParams();
		params.setPath(libPath);
		params.setJarClassResult(jarClassResult);
		params.setClassResult(classResult);
		params.setConflictResult(conflictResult);
		// FileUtil.findJarConflictClass(libPath, jarClassResult, classResult, conflictResult);
		FileUtil.findJarConflictClass(params);
		System.out.println("---------------------运行结果如下---------------------");
		// for (String jarPath : jarClassResult.keySet()) {
		// System.out.println("文件名:" + jarPath);
		// Set<JarClass> jarClasss = jarClassResult.get(jarPath);
		// for (JarClass jarClass : jarClasss) {
		// System.out.println("    类对应的文件全路径:" + jarClass.getFullPath());
		// System.out.println("    文件目录路径:" + jarClass.getFileDir());
		// System.out.println("    文件名中的class路径:" + jarClass.getClassPath());
		// System.out.println("    类对应的java包路径:" + jarClass.getClassName());
		// }
		// }
		// System.out.println("jarClassResult:" + jarClassResult);
		// System.out.println("classResult:" + classResult);
		// System.out.println("conflictResult:" + conflictResult);
		System.out.println("---------------------------------");
		System.out.println("冲突文件jar如下:");
		System.out.println("---------------------------------");
		for (String className : conflictResult.keySet()) {
			System.out.println("包路径:" + className);
			Set<String> jarPaths = conflictResult.get(className);
			System.out.println("=================================");
			for (String jarPath : jarPaths) {
				System.out.println("jar文件:" + jarPath);
			}
			System.out.println("**********************************");
		}
	}

	@Test
	public void test5() throws IOException {
		String className = "org.apache.log4j.Appender"; //
		String libPath = "E:/document/zj-utils/jar"; //
		List<String> list = FileUtil.findClassConflictJar(libPath, className);
		for (String l : list) {
			System.out.println(l);
		}
	}

	public void test2() throws IOException {
		test3(new ArrayList<List<String>>());
		test3(new HashSet<Set<String>>());
	}

	@SuppressWarnings("unchecked")
	public <T> void test3(Collection<T> coll) throws IOException {
		T t = ((T) new ArrayList<String>());
		coll.add(t);
	}

	public void test() throws IOException {
		BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(TestFileConstant.SRC_FILE), "GBK"));
		String str = is.readLine();
		char c = str.charAt(0);
		System.out.println(c);
	}

	public void testreadLinesCountI() throws Exception {
		ReadLinesCallAbsImpl<List<String>> imp = new ReadLinesCallAbsImpl<List<String>>() {
			public List<String> getObj() {
				// return new ArrayList<String>();
				return new LinkedList<String>();
			}
		};
		// imp.setStartLineNum(10);
		// imp.setEndLineNum(15);
		long count = FileUtil.readLinesCountI(new File(TestFileConstant.ENCODING_FILE), imp);
		System.out.println("count:" + count);
	}

	public void testreadLinesList() throws Exception {
		List<String> list = new ArrayList<String>();
		FileUtil.readLines(list, new File(TestFileConstant.ENCODING_FILE));
		System.out.println(list.size());
	}

	public void testreadLinesSet() throws Exception {
		Set<String> list = new HashSet<String>();
		FileUtil.readLines(list, new File(TestFileConstant.ENCODING_FILE));
		System.out.println(list.size());
	}

	public void testreadLinesIList() throws Exception {
		ReadLinesCallAbsImpl<List<String>> imp = new ReadLinesCallAbsImpl<List<String>>() {
			public List<String> getObj() {
				// return new ArrayList<String>();
				return new LinkedList<String>();
			}
		};
		// imp.setStartLineNum(10);
		// imp.setEndLineNum(15);
		List<String> sets = FileUtil.readLinesI(new File(TestFileConstant.ENCODING_FILE), imp);
		for (String set : sets) {
			System.out.println("大小 :" + set);
		}
		System.out.println("总大小 :" + sets.size());
	}

	public void testreadLinesISet() throws Exception {
		ReadLinesCallAbsImpl<Set<String>> imp = new ReadLinesCallAbsImpl<Set<String>>() {
			public Set<String> getObj() {
				return new HashSet<String>();
			}
		};
		// imp.setStartLineNum(10);
		// imp.setEndLineNum(15);
		Set<String> sets = FileUtil.readLinesI(new File(TestFileConstant.ENCODING_FILE), imp);
		for (String set : sets) {
			System.out.println("大小 :" + set);
		}
		System.out.println("总大小 :" + sets.size());
	}

	public void testreadLinesBatchISet() throws Exception {
		ReadLinesCallAbsImpl<Set<String>> imp = new ReadLinesCallAbsImpl<Set<String>>() {
			public Set<String> getObj() {
				return new HashSet<String>();
			}
		};
		ReadLinesBatchCallAbsImpl<Set<String>> imp2 = new ReadLinesBatchCallAbsImpl<Set<String>>() {
			public boolean isCallBatchColl() {
				return false;
			}

			public void callBatchColl(Set<String> batchColl) {
				System.out.println("->" + batchColl.size());
			}

			public Set<String> getObj() {
				return new HashSet<String>();
			}
		};
		// imp.setStartLineNum(1);
		// imp.setEndLineNum(15);
		imp2.setBatchSize(10000);
		long count = FileUtil.readLinesCountI(new File(TestFileConstant.ENCODING_FILE), imp);
		System.out.println("count:" + count);
		List<Set<String>> sets = FileUtil.readLinesBatchI(new File(TestFileConstant.ENCODING_FILE), imp, imp2);
		for (Set<String> set : sets) {
			System.out.println("大小 :" + set.size());
		}
		System.out.println("总大小 :" + sets.size());
	}

	public void testreadLinesBatchIList() throws Exception {
		ReadLinesCallAbsImpl<List<String>> imp = new ReadLinesCallAbsImpl<List<String>>() {
			public List<String> getObj() {
				return new ArrayList<String>();
			}
		};
		ReadLinesBatchCallAbsImpl<List<String>> imp2 = new ReadLinesBatchCallAbsImpl<List<String>>() {
			public boolean isCallBatchColl() {
				return false;
			}

			public void callBatchColl(List<String> batchColl) {
				System.out.println("->" + batchColl.size());
			}

			public List<String> getObj() {
				return new ArrayList<String>();
			}
		};
		// imp.setStartLineNum(1);
		// imp.setEndLineNum(15);
		imp2.setBatchSize(10000);
		long count = FileUtil.readLinesCountI(new File(TestFileConstant.ENCODING_FILE), imp);
		System.out.println("count:" + count);
		List<List<String>> sets = FileUtil.readLinesBatchI(new File(TestFileConstant.ENCODING_FILE), imp, imp2);
		for (List<String> set : sets) {
			System.out.println("大小 :" + set.size());
		}
		System.out.println("总大小 :" + sets.size());
	}

	// 序列化对象为String字符串，先对序列化后的结果进行BASE64编码，否则不能直接进行反序列化
	public static String writeObject(Object o) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(o);
		oos.flush();
		oos.close();
		bos.close();
		// return new BASE64Encoder().encode(bos.toByteArray());
		return new String(bos.toByteArray(), "ISO-8859-1");
	}

	// 反序列化String字符串为对象

	public static Object readObject(String object) throws Exception {
		// ByteArrayInputStream bis = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(object));
		ByteArrayInputStream bis = new ByteArrayInputStream(object.getBytes("ISO-8859-1"));
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object o = null;
		try {
			o = ois.readObject();
		} catch (EOFException e) {
			System.err.print("read finished");
		}
		bis.close();
		ois.close();
		return o;
	}

}
