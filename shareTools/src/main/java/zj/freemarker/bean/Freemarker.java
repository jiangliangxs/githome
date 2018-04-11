package zj.freemarker.bean;

import java.io.File;
import java.util.Map;

/**
 * FreemarkerUtil工具类实体参数
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class Freemarker {
	/**
	 * 模板名
	 */
	private String name;
	/**
	 * 多个模板路径
	 */
	private String[] ftlPaths;
	/**
	 * 顶层变量数据
	 */
	private Map<String, Object> rootMap;
	/**
	 * 输出文件
	 */
	private File outFile;
	/**
	 * 编码,默认UTF-8
	 */
	private String charsetName = "UTF-8";
	/**
	 * 是否追加
	 */
	private boolean append;

	public String[] getFtlPaths() {
		return ftlPaths;
	}

	public void setFtlPaths(String... ftlPaths) {
		this.ftlPaths = ftlPaths;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getRootMap() {
		return rootMap;
	}

	public void setRootMap(Map<String, Object> rootMap) {
		this.rootMap = rootMap;
	}

	public File getOutFile() {
		return outFile;
	}

	public void setOutFile(File outFile) {
		this.outFile = outFile;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

}
