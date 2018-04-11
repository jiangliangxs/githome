package test.compress;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import zj.compress.bean.Compress;
import zj.compress.bean.CompressAttr;
import zj.compress.util.CompressUtil;
import zj.io.util.FileUtil;

public class TestUtil {
	public static void main(String[] args) throws Exception {
//		m7();
//		String path = "E:\\document\\zip\\logFile.rar";
//		File file = new File(path);
//		String s = FileUtil.getCreateTime(file);
//		System.out.println(s);
		String s = FilenameUtils.getExtension("E:\\document\\zip\\logFile.rar");
		System.out.println(s);
	}
	private static void m7(){
		try {
			List<String> txtLst = null;
			if (txtLst!=null){
				for (String s : txtLst){
					System.out.println(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void m6(){
//		System.out.println(FileUtil.changePathSeparator("C:/Users/Administrator/Desktop/Rar/",true));
		// FileUtil.LOG_INFO = true;
		// FileUtil.deleteFiles("E:\\document\\zip\\unzip\\a\\");
//		m5();
//		FileUtil.writeFile("C:/a/b/c.txt", "aa"+System.getProperty("line.separator")+"bb");
		String pa = "E://a/b/c/a.txt";
		System.out.println(Arrays.toString(FileUtil.getFileNameExtension(pa)));
		pa = "6.txt";
		System.out.println(Arrays.toString(FileUtil.getFileNameExtension(pa)));
		pa = "6";
		System.out.println(Arrays.toString(FileUtil.getFileNameExtension(pa)));
	}
	private static void m5(){
		   System.out.println("java_vendor:" + System.getProperty("java.vendor"));   
		    System.out.println("java_vendor_url:"   
		             + System.getProperty("java.vendor.url"));   
		    System.out.println("java_home:" + System.getProperty("java.home"));   
		    System.out.println("java_class_version:"   
		             + System.getProperty("java.class.version"));   
		    System.out.println("java_class_path:"   
		            + System.getProperty("java.class.path"));   
		    System.out.println("os_name:" + System.getProperty("os.name"));   
		    System.out.println("os_arch:" + System.getProperty("os.arch"));   
		    System.out.println("os_version:" + System.getProperty("os.version"));   
		    System.out.println("user_name:" + System.getProperty("user.name"));   
		    System.out.println("user_home:" + System.getProperty("user.home"));   
		    System.out.println("user_dir:" + System.getProperty("user.dir"));   
		    System.out.println("java_vm_specification_version:"   
		            + System.getProperty("java.vm.specification.version"));   
		    System.out.println("java_vm_specification_vendor:"   
		            + System.getProperty("java.vm.specification.vendor"));   
		    System.out.println("java_vm_specification_name:"   
		            + System.getProperty("java.vm.specification.name"));   
		    System.out.println("java_vm_version:"   
		            + System.getProperty("java.vm.version"));   
		    System.out.println("java_vm_vendor:"   
		            + System.getProperty("java.vm.vendor"));   
		    System.out   
		            .println("java_vm_name:" + System.getProperty("java.vm.name"));   
		    System.out.println("java_ext_dirs:"   
		            + System.getProperty("java.ext.dirs"));   
		    System.out.println("file_separator:"   
		            + System.getProperty("file.separator"));   
		    System.out.println("path_separator:"   
		            + System.getProperty("path.separator"));   
		    System.out.println("line_separator:"   
		            + System.getProperty("line.separator"));   
	}

	private static void m4() throws Exception {
		String srcPath = "C:\\Users\\Administrator\\Desktop\\Calls";
		String descPath = "C:\\Users\\Administrator\\Desktop\\Rar";
		File file = new File(srcPath);
		File[] files = file.listFiles();
		System.out.println(files.length);
		for (File f : files){
			System.out.println(f.getName());
		}
		Compress c = new Compress();
		c.setSrcPath("E:\\document\\zip\\a.rar");
//		c.setDescPath("E:\\document\\zip\\unzip\\a\\");
		c.setOverWrite("2");
		CompressUtil.setCompress(c);
		List<CompressAttr> attrs = c.getCompressAttrs();
		System.out.println("返回结果如下:"+attrs.size());
		for (CompressAttr attr : attrs) {
			System.out.println("是否是目录:" + attr.isDirectory() + ",文件地址:" + attr.getDescPath() + ",文件名:" + attr.getDescPath() + "," + attr.getFileName() + ",大小:" + attr.getLength());
		}
	}

	private static void m3() throws Exception {
		String src = "E:\\document\\zip\\a.rar";
		// src = "E:/a/b/c/d";
		// src = "E:\\a.txt";
		File file = new File(src);
		System.out.println(file.length());
		Compress c = new Compress();
		src = FileUtil.changePathSeparator(src);
		System.out.println("src:" + src);
		String[] s = FileUtil.getFileNameExtension(src);
		String ss = FilenameUtils.getExtension(src);
		System.out.println(ss + "==" + Arrays.toString(s));

	}

	private static void m2() throws Exception {
		String s = TestUtil.class.getClassLoader().getResource("").getPath() + "a";
		File file = new File(s);
	}

	private static void m1() throws Exception {
		String path = "./com/zj/file_util/v1/resources/jdbcConfig3e.properties";
		// String value = FileUtil.getProperty(path, "hibernate.hbm2ddl.auto");
		// FileUtil.setProperty(path, "hibernate.hbm2ddl.auto", "create");
		// value = FileUtil.getProperty(path, "hibernate.hbm2ddl.auto");
		// System.out.println(value);
		// new File(path).renameTo(new File("./com/zj/file_util/v1/resources/jdbcConfig3.properties"));
		FileInputStream in = new FileInputStream(path);
		// 配置文件内容解析
		Properties prop = new Properties();
		prop.load(in);
		in.close();
		System.out.println(prop.getProperty("hibernate.hbm2ddl.auto"));
	}
}
