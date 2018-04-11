package zj.io.service;

import java.io.File;
import java.util.List;

/**
 * 文件/文件夹过虑接口
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public interface FileFilterI {
	int setLevel(int level);
	int getLevel();
	/**
	 * 是否接收文件夹或文件
	 * 
	 * @param file
	 *            当前文件
	 * @return true:过虑,false:不过虑(默认)
	 */
	boolean accept(File file);

	/**
	 * 是否接收文件夹
	 * 
	 * @param file
	 *            当前文件
	 * @return true:过虑,false:不过虑(默认)
	 */
	boolean acceptDir(File file);

	/**
	 * 是否接收文件
	 * 
	 * @param file
	 *            当前文件
	 * @return true:过虑,false:不过虑(默认)
	 */
	boolean acceptFile(File file);

	/**
	 * 是否中断
	 * 
	 * @param file
	 *            当前文件
	 * @param fileList
	 *            过虑的文件集合
	 * @return true:中断,false:不中断
	 */
	public boolean interrupt(File file, List<File> fileList);

}