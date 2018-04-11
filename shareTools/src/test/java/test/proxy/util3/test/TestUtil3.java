package test.proxy.util3.test;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;


public class TestUtil3 {
	public static void main(String[] args) throws Exception{
		String rt = "\r\t";
		String src = "package test.proxy.util3;" + rt +
		"public class TankTimeProxy implements Moveable {" + rt +
				"	public TankTimeProxy(Moveable t) {" + rt +
				"	super();" + rt +
				"	this.t = t;" + rt + 
				"}" + rt +
				"	Moveable t;" + rt +
				"	@Override" + rt + 
				"	public void move() {" + rt + 
				"		long start = System.currentTimeMillis();" + "t.move();" + rt + 
				"		long end = System.currentTimeMillis();" + rt + 
				"		System.out.println(\"time: \" + (end - start));" + rt + 
				"	}" + rt +
				"}";
		String fileName = System.getProperty("user.dir")
				+ "/src/main/java/utils/proxyUtils/util3/TankTimeProxy.java";
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write(src);
		fw.flush();
		fw.close();
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//		System.out.println(compiler);
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> units = fileManager.getJavaFileObjects(fileName);
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, units);
		task.call();
		fileManager.close();
		
		URL[] urls = new URL[]{new URL("file:/" + System.getProperty("user.dir") + "/src")};
		URLClassLoader ul = new URLClassLoader(urls);
		
		Class<?> c = ul.loadClass(".proxy.util.util3.TankTimeProxy");
		System.out.println(c);
		@SuppressWarnings("unchecked")
		Constructor ctr = c.getConstructor(Moveable.class);
		
		Moveable m = (Moveable)ctr.newInstance(new Tank());
		m.move();
		
		
	}

}
