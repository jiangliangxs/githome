package zj.io.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import zj.io.util.ConstantForEnum;
import zj.io.util.FileUtil;

/**
 * jar工具类参数值
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class JarParams implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(this.getClass());
	private Object objPath;
	private String path;
	private Map<String, Set<JarClass>> jarClassResult = Collections.synchronizedMap(new HashMap<String,Set<JarClass>>());
	private Map<String, Set<String>> classResult = Collections.synchronizedMap(new HashMap<String,Set<String>>());
	private Map<String, Set<String>> conflictResult = Collections.synchronizedMap(new HashMap<String,Set<String>>());

	/**
	 * 
	 * @return jar文件或目录对象
	 */
	public Object getObjPath() {
		return objPath;
	}

	/**
	 * 可以设置string,Collection,String[]等封装的jar绝对路径path
	 * 
	 * @param objPath
	 *            jar文件或目录对象
	 */
	public void setObjPath(Object objPath) {
		this.objPath = objPath;
	}

	/**
	 * @return jar文件或目录绝对路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 
	 * @param path
	 *            jar文件或目录绝对路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 每个jar路径对应的class集合:
	 * <p>
	 * key:jar全路径
	 * </p>
	 * <p>
	 * value:class集合
	 * </p>
	 * 
	 * @return 每个jar路径对应的class集合
	 */
	public Map<String, Set<JarClass>> getJarClassResult() {
		return jarClassResult;
	}

	/**
	 * 
	 * @param jarClassResult
	 *            每个jar路径对应的class集合
	 */
	public void setJarClassResult(Map<String, Set<JarClass>> jarClassResult) {
		this.jarClassResult = jarClassResult;
	}

	/**
	 * 所有class(包路径)对应的jar文件路径集合:
	 * <p>
	 * key:包路径
	 * </p>
	 * <p>
	 * value:jar文件路径集合
	 * </p>
	 * <p>
	 * 此classResult属性不可能为null
	 * </p>
	 * 
	 * @return 所有class(包路径)对应的jar文件路径集合
	 */
	public Map<String, Set<String>> getClassResult() {
		return classResult;
	}

	/**
	 * 不设置,系统默认实例化
	 * 
	 * @param classResult
	 *            所有class(包路径)对应的jar文件路径
	 */
	public void setClassResult(Map<String, Set<String>> classResult) {
		this.classResult = classResult;
	}

	/**
	 * 冲突class(包路径)对应的jar文件路径集合:
	 * <p>
	 * key:包路径
	 * </p>
	 * <p>
	 * value:jar文件路径集合
	 * </p>
	 * <p>
	 * 此conflictResult属性不可能为null
	 * </p>
	 * 
	 * @return 冲突class(包路径)对应的jar文件路径集合
	 */
	public Map<String, Set<String>> getConflictResult() {
		return conflictResult;
	}

	/**
	 * 
	 * @param classResult
	 *            冲突class(包路径)对应的jar文件路径
	 */
	public void setConflictResult(Map<String, Set<String>> conflictResult) {
		this.conflictResult = conflictResult;
	}

	/**
	 * 写日志
	 * 
	 * @param fileDir
	 */
	public void writeLog(final String fileDir) {
		long execStartTime = System.currentTimeMillis();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				writeConflictResultLog(FileUtil.changePathSeparator(fileDir, true) + "conflictResultLog.log");
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				writeClassResultLog(FileUtil.changePathSeparator(fileDir, true) + "classResultLog.log");
//			}
//		}).start();
		writeConflictResultLog(FileUtil.changePathSeparator(fileDir, ConstantForEnum.ChangePathLastSeparator.ADD_AFTER) + "conflictResultLog.log");
		writeClassResultLog(FileUtil.changePathSeparator(fileDir, ConstantForEnum.ChangePathLastSeparator.ADD_AFTER) + "classResultLog.log");
		writeJarClassResultLog(FileUtil.changePathSeparator(fileDir, ConstantForEnum.ChangePathLastSeparator.ADD_AFTER) + "jarClassResult.log");
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		long execEndTime = System.currentTimeMillis();
		long duration = execEndTime - execStartTime;
		long x = duration / 1000;
		long y = duration % 1000;
		logger.info("执行时间:" + duration + "毫秒;" + x + "." + (y < 100 ? "0" + y : y) + "秒");
	}

	/**
	 * 写类冲突日志
	 * 
	 * @param fileDir
	 */
	public void writeConflictResultLog(String filePath) {
		BufferedOutputStream output = null;
		try {
			output = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			for (String className : conflictResult.keySet()) {
				String line = "";
				line += "class包路径:" + className + FileUtil.LINE_SEPARATOR;
				Set<String> jarPaths = conflictResult.get(className);
				line += "--------------------------------" + FileUtil.LINE_SEPARATOR;
				for (String jarPath : jarPaths) {
					line += "jar文件路径:" + jarPath + FileUtil.LINE_SEPARATOR;
				}
				line += "=================================" + FileUtil.LINE_SEPARATOR;
				IOUtils.write(line, output);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				IOUtils.closeQuietly(output);
			}
		}
	}
	/**
	 * 写jar包对应的类日志
	 * 
	 * @param fileDir
	 */
	public void writeJarClassResultLog(String filePath) {
		BufferedOutputStream output = null;
		try {
			output = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			for (String key : jarClassResult.keySet()) {
				String line = "";
				line += "jar全路径:" + key + FileUtil.LINE_SEPARATOR;
				Set<JarClass> values = jarClassResult.get(key);
				line += "--------------------------------" + FileUtil.LINE_SEPARATOR;
				for (JarClass value : values) {
					line += "文件目录路径:" + value.getFileDir() + FileUtil.LINE_SEPARATOR;
					line += "类对应的文件全路径:" + value.getFullPath() + FileUtil.LINE_SEPARATOR;
					line += "文件名中的class路径:" + value.getClassPath() + FileUtil.LINE_SEPARATOR;
					line += "类对应的java包路径:" + value.getClassName() + FileUtil.LINE_SEPARATOR;
					line += "..............................." + FileUtil.LINE_SEPARATOR;
				}
				line += "=================================" + FileUtil.LINE_SEPARATOR;
				IOUtils.write(line, output);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				IOUtils.closeQuietly(output);
			}
		}
	}
	/**
	 * 写类对应jar的日志
	 * 
	 * @param fileDir
	 */
	public void writeClassResultLog(String filePath) {
		BufferedOutputStream output = null;
		try {
			output = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			for (String className : this.classResult.keySet()) {
				String line = "";
				line += "class包路径:" + className + FileUtil.LINE_SEPARATOR;
				Set<String> jarPaths = classResult.get(className);
				line += "--------------------------------" + FileUtil.LINE_SEPARATOR;
				for (String jarPath : jarPaths) {
					line += "jar文件路径:" + jarPath + FileUtil.LINE_SEPARATOR;
				}
				line += "=================================" + FileUtil.LINE_SEPARATOR;
				IOUtils.write(line, output);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				IOUtils.closeQuietly(output);
			}
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param filePath
	 */
	@Override
	public String toString() {
		for (String className : conflictResult.keySet()) {
			String line = "";
			line += "包路径:" + className;
			Set<String> jarPaths = conflictResult.get(className);
			line += "--------------------------------";
			for (String jarPath : jarPaths) {
				line += "jar文件:" + jarPath;
			}
			line += "=================================";
			logger.debug(line);
		}
		return super.toString();
	}
}