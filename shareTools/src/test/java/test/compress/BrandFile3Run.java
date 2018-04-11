package test.compress;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import zj.io.util.FileUtil;

/**
 * 归集至云存储部分<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class BrandFile3Run {
	public static void main(String[] args) {
		new BrandFile3Run().run(null);
	}

	/**
	 * 
	 * @param map
	 */
	public void run(Map<String, Object> map) {
		// 日志文件路径
		String logPath = "C:\\Users\\Administrator\\Desktop\\LoseLogs\\Cloud";
		logPath = FileUtil.changePathSeparator(logPath, true);
		try {
			FileUtil.createFolderOrFile(logPath, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 读取源路径
		String srcPath = "C:\\Users\\Administrator\\Desktop\\GuiJi";
		// 读取目标路径
		String descPath = "C:\\Users\\Administrator\\Desktop\\Cloud";
		// 在路径后添加/或\\
		srcPath = FileUtil.changePathSeparator(srcPath, true);
		descPath = FileUtil.changePathSeparator(descPath, true);
		File srcFile = new File(srcPath);
		File[] srcFiles = srcFile.listFiles();
		File descFile = new File(descPath);
		File[] descFiles = descFile.listFiles();
		try {
			// 文件名
			String timeTxt = "文件夹个数-2014-01-01.txt";
			String titleMsg = "原文件夹名\t说明";
			String msg = "";
			// 判断源文件个数与目标文件个数是否一致
			if (srcFiles.length != descFiles.length) {
				for (File sf : srcFiles) {
					String fileName = sf.getName();
					boolean exists = false;
					for (File df : descFiles) {
						if (fileName.equals(df.getName())) {
							exists = true;
							break;
						}
					}
					if (!exists) {
						msg += fileName + "\t云存储rar打包文件缺失" + FileUtil.LINE_SEPARATOR;
					}
				}
			}
			if (!"".equals(msg)) {
				FileUtils.writeStringToFile(new File(logPath + timeTxt), titleMsg + FileUtil.LINE_SEPARATOR + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 压缩文件大小比对
		try {
			// 文件名
			String timeTxt = "rar文件大小-2014-01-01.txt";
			String titleMsg = "rar名\t原大小\t目标大小";
			String msg = "";
			for (File sf : srcFiles) {
				for (File df : descFiles) {
					if (sf.getName().equals(df.getName())) {
						long slen = sf.length();
						long dlen = df.length();
						if (slen != dlen) {// 判断大小
							// System.out.println(strF.getAbsolutePath());
							msg += sf.getName() + "\t" + slen + "字节\t" + dlen + "字节" + FileUtil.LINE_SEPARATOR;
						}
					}
				}
			}
			if (!"".equals(msg)) {
				FileUtils.writeStringToFile(new File(logPath + timeTxt), titleMsg + FileUtil.LINE_SEPARATOR + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
