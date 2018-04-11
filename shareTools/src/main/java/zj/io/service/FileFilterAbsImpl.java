package zj.io.service;

import java.io.File;
import java.util.List;

/**
 * 类名 ：FileFilterAbsImpl<br>
 * 概况 ：文件过虑实现类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public abstract class FileFilterAbsImpl implements FileFilterI {
	private int level;

	@Override
	public boolean accept(File file) {
		return true;
	}

	@Override
	public boolean acceptDir(File file) {
		return true;
	}

	@Override
	public boolean acceptFile(File file) {
		return true;
	}

	@Override
	public boolean interrupt(File file, List<File> fileList) {
		return false;
	}

	@Override
	public int setLevel(int level) {
		return this.level = level;
	}

	@Override
	public int getLevel() {
		return level;
	}

}