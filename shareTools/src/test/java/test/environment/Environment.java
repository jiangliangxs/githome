package test.environment;

import java.io.InputStream;
import java.net.URL;

import org.junit.Test;

public class Environment {
	@Test
	public void m3() {
		// getResourceAsStream：查找具有给定名称的资源。返回 inputstream
		// getResource：查找带有给定名称的资源路径。返回URL
		printResource(Class.class, "");
		printResource(Class.class, ".");
		printResource(Class.class, "..");
		printResource(Class.class, "../");
		printResource(Class.class, "./a.txt");
		printResource(Class.class, "/a.txt");
		printResource(Class.class, "./file/b.txt");
		printResource(Class.class, "/file/b.txt");
		printResource(Class.class, "./c.txt");
		//正确获取
		printResource(Class.class, "/c.txt");
	}
	@Test
	public void m2() {
		// getResourceAsStream：查找具有给定名称的资源。返回 inputstream
		// getResource：查找带有给定名称的资源路径。返回URL
		//正确获取
		printResource(Environment.class, "");
		//正确获取
		printResource(Environment.class, ".");
		//正确获取
		printResource(Environment.class, "..");
		//正确获取
		printResource(Environment.class, "../");
		printResource(Environment.class, "./a.txt");
		printResource(Environment.class, "/a.txt");
		printResource(Environment.class, "./file/b.txt");
		printResource(Environment.class, "/file/b.txt");
		printResource(Environment.class, "./c.txt");
		//正确获取
		printResource(Environment.class, "/c.txt");
	}
	private void printResource(Class<?> clazz, String name) {
		System.out.println("class:\t" + clazz + "\t,name:\t" + name);
		try {
			URL url = clazz.getResource(name);
			System.out.println("结果1:" + url.getFile());
		} catch (Exception e) {
			System.err.println("错误信息:" + e.getMessage());
		}
		System.out.println("--------------------------------");
		try {
			InputStream is = clazz.getResourceAsStream(name);
			System.out.println("结果2:" + is);
		} catch (Exception e) {
			System.err.println("错误信息:" + e.getMessage());
		}
	}

	@Test
	public void m1() {
		System.out.println("java版本号：" + System.getProperty("java.version")); // java版本号
		System.out.println("Java提供商名称：" + System.getProperty("java.vendor")); //  Java提供商名称
		System.out.println("Java提供商网站：" + System.getProperty("java.vendor.url")); // Java提供商网站
		System.out.println("jre目录：" + System.getProperty("java.home")); // Java，哦，应该是jre目录
		System.out.println("Java虚拟机规范版本号：" + System.getProperty("java.vm.specification.version")); // Java虚拟机规范版本号
		System.out.println("Java虚拟机规范提供商：" + System.getProperty("java.vm.specification.vendor")); // Java虚拟机规范提供商
		System.out.println("Java虚拟机规范名称：" + System.getProperty("java.vm.specification.name")); // Java虚拟机规范名称
		System.out.println("Java虚拟机版本号：" + System.getProperty("java.vm.version")); // Java虚拟机版本号
		System.out.println("Java虚拟机提供商：" + System.getProperty("java.vm.vendor")); // Java虚拟机提供商
		System.out.println("Java虚拟机名称：" + System.getProperty("java.vm.name")); // Java虚拟机名称
		System.out.println("Java规范版本号：" + System.getProperty("java.specification.version")); // Java规范版本号
		System.out.println("Java规范提供商：" + System.getProperty("java.specification.vendor")); // Java规范提供商
		System.out.println("Java规范名称：" + System.getProperty("java.specification.name")); // Java规范名称
		System.out.println("Java类版本号：" + System.getProperty("java.class.version")); // Java类版本号
		System.out.println("Java类路径：" + System.getProperty("java.class.path")); // Java类路径
		System.out.println("Java lib路径：" + System.getProperty("java.library.path")); // Java lib路径
		System.out.println("Java输入输出临时路径：" + System.getProperty("java.io.tmpdir")); // Java输入输出临时路径
		System.out.println("Java编译器：" + System.getProperty("java.compiler")); // Java编译器
		System.out.println("Java执行路径：" + System.getProperty("java.ext.dirs")); // Java执行路径
		System.out.println("操作系统名称：" + System.getProperty("os.name")); // 操作系统名称
		System.out.println("操作系统的架构：" + System.getProperty("os.arch")); // 操作系统的架构
		System.out.println("操作系统版本号：" + System.getProperty("os.version")); // 操作系统版本号
		System.out.println("文件分隔符：" + System.getProperty("file.separator")); // 文件分隔符
		System.out.println("路径分隔符：" + System.getProperty("path.separator")); // 路径分隔符
		System.out.println("直线分隔符：" + System.getProperty("line.separator")); // 直线分隔符
		System.out.println("操作系统用户名：" + System.getProperty("user.name")); // 用户名
		System.out.println("操作系统用户的主目录：" + System.getProperty("user.home")); // 用户的主目录
		System.out.println("当前程序所在目录：" + System.getProperty("user.dir")); // 当前程序所在目录
	}
}
