package zj.compress.bean;

import java.io.Serializable;

/**
 * 概况 ：压缩文件属性操作<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class CompressAttr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 压缩内文件路径 **/
	private String descPath;
	/** 文件大小 **/
	private long length;
	/** 是否是目录 **/
	private boolean directory;
	/** 文件名 **/
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isDirectory() {
		return directory;
	}

	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	public String getDescPath() {
		return descPath;
	}

	public void setDescPath(String descPath) {
		this.descPath = descPath;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

}
