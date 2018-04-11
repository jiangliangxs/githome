package zj.io.model;

import java.io.Serializable;

/**
 * 每个jar路径对应的class集合属性
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class JarClass implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fileDir;
	private String fullPath;
	private String classPath;
	private String className;

	/**
	 * 
	 * @return 文件名中的class路径
	 */
	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	/**
	 * 
	 * @return 文件目录路径
	 */
	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	/**
	 * 
	 * @return 类对应的java包路径
	 */
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 
	 * @return 类对应的文件全路径
	 */
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

}