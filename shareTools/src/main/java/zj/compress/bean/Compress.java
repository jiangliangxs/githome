package zj.compress.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 概况 ：压缩文件<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class Compress implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String srcPath;
	private String descPath;
	private List<CompressAttr> compressAttrs = new ArrayList<CompressAttr>();
	private String encoding = "UTF-8";
	private String overWrite;

	/**
	 * 编码
	 * 
	 * @return 编码
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 编码
	 * 
	 * @param encoding
	 *            编码
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 获取是否覆盖
	 * 
	 * @return 是否覆盖
	 * @see zj.compress.util.CompressUtil.Enums.OverWrite
	 */
	public String getOverWrite() {
		return overWrite;
	}

	/**
	 * 设置是否覆盖
	 * 
	 * @param overWrite
	 * @see zj.compress.util.CompressUtil.Enums.OverWrite
	 */
	public void setOverWrite(String overWrite) {
		this.overWrite = overWrite;
	}

	/**
	 * 获取源压缩文件路径
	 * 
	 * @return 源压缩文件路径
	 */
	public String getSrcPath() {
		return srcPath;
	}

	/**
	 * 设置源压缩文件路径
	 * 
	 * @param srcPath
	 *            源压缩文件路径
	 */
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	/**
	 * 获取目标压缩文件路径
	 * 
	 * @return 目标压缩文件路径
	 */
	public String getDescPath() {
		return descPath;
	}

	/**
	 * 设置目标压缩文件路径
	 * 
	 * @param descPath
	 *            目标压缩文件路径
	 */
	public void setDescPath(String descPath) {
		this.descPath = descPath;
	}

	/**
	 * 获取压缩文件中的属性对象集合
	 * 
	 * @return 压缩文件中的属性对象集合
	 */
	public List<CompressAttr> getCompressAttrs() {
		return compressAttrs;
	}

	/**
	 * 设置压缩文件中的属性对象集合
	 * 
	 * @param compressAttrs
	 *            压缩文件中的属性对象集合
	 */
	public void setCompressAttrs(List<CompressAttr> compressAttrs) {
		this.compressAttrs = compressAttrs;
	}

}
