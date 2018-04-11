package test.proxy.util3;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;



public class Proxy {
	private static String proxyName = "$Proxy1_zhangjun";
	public static Object newProxyInstance(Class<?> infac,InvocationHandler h) throws Exception{//java compiler , cglig , asm
		String rt = "\r\t";

		String methodStr = "";
		
		Method[] methods = infac.getDeclaredMethods();
		/*for(Method m : methods){
			methodStr += "@Override" + rt + 
			"public void " + m.getName() + "(){" + rt + 
			"	long start = System.currentTimeMillis();" + rt + 
			"	t." + m.getName() + "();" + rt + 
			"	long end = System.currentTimeMillis();" + rt + 
			"	System.out.println(\"time-proxy: \" + (end - start));" + rt + 
			"	}";
		}*/
		for(Method m : methods){
			methodStr += "@Override" + rt + 
			"public void " + m.getName() + "(){" + rt + 
			"try{" + rt +
			"Method md = " + infac.getName() + ".class.getMethod(\"" + m.getName() + "\");" + rt +  
			"h.invoke(this,md);" + rt +
			"}catch(Exception e){" + rt + 
			"e.printStackTrace();" + rt + 
			"	}" + rt +
			"}";
		}
		
		String src = "package test.proxy.util3;" + rt +
		"import java.lang.reflect.Method;" + rt +
		"public class " + proxyName + " implements " + infac.getName() + " {" + rt +
//				"	public " + proxyName + "(Moveable t) {" + rt +
//				"	super();" + rt +
//				"	this.t = t;" + rt + 
//				"}" + rt +
//				"	Moveable t;" + rt +
//				methodStr + rt +
//				"}";
		"	public " + proxyName + "(InvocationHandler h) {" + rt +
		"	this.h = h;" + rt + 
		"}" + rt +
		"	.proxy.util.util3.InvocationHandler h;" + rt +
		methodStr + rt +
		"}";
		
		
		new File("D:"
				+ "/src/main/java/utils/proxyUtils/util3/").mkdirs();
		String fileName = /*System.getProperty("user.dir")*/"D:"
				+ "/src/main/java/utils/proxyUtils/util3/" + proxyName + ".java";
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
		
		URL[] urls = new URL[]{new URL("file:/" + /*System.getProperty("user.dir")*/"D:" + "/src/main/java/")};
		URLClassLoader ul = new URLClassLoader(urls);
		
		Class<?> c = ul.loadClass(".proxy.util.util3." + proxyName);
//		Class<?> c = utils.fileUtils.util1.FileUtils.compilerJavaFile("D:/src/main/java/", ".proxy.util.util3", proxyName + ".java", "/", src,null);
//		System.out.println(c);
		@SuppressWarnings("unchecked")
		Constructor ctr = c.getConstructor(InvocationHandler.class);
		
		Object obj = ctr.newInstance(h);
//		m.move();

		
		return obj;
	}

}
