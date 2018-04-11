package test.compress;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import zj.compress.bean.Compress;
import zj.compress.bean.CompressAttr;
import zj.compress.util.CompressUtil;
import zj.io.util.FileUtil;

/**
 * 概况 ：打包点监控部分<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class BrandFile1Run {
	public static void main(String[] args) {
		new BrandFile1Run().run(null);
	}

	/**
	 * 
	 * @param map
	 */
	public void run(Map<String, Object> map) {
		// 日志文件路径
		String logPath = "C:\\Users\\Administrator\\Desktop\\LoseLogs\\Rar";
		logPath = FileUtil.changePathSeparator(logPath, true);
		try {
			FileUtil.createFolderOrFile(logPath, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 读取源路径
		String srcPath = "C:\\Users\\Administrator\\Desktop\\Calls";
		// 读取目标路径
		String descPath = "C:\\Users\\Administrator\\Desktop\\Rar";
		// 在路径后添加/或\\
		srcPath = FileUtil.changePathSeparator(srcPath, true);
		descPath = FileUtil.changePathSeparator(descPath, true);
		File srcFile = new File(srcPath);
		File[] srcFiles = srcFile.listFiles();
		File descFile = new File(descPath);
		File[] descFiles = descFile.listFiles();
		try {
			// 文件名
			String timeTxt = "文件夹个数异常-2014-01-01.txt";
			String titleMsg = "原文件夹名　　　　　　　　　　说明";
			String msg = "";
			// 判断源文件个数与目标文件个数是否一致
			if (srcFiles.length != descFiles.length) {
				for (File sf : srcFiles) {
					String fileName = sf.getName();
					boolean exists = false;
					for (File df : descFiles) {
						// 取得文件名去掉扩展名
						String[] descFileNames = FileUtil.getFileNameExtension(df.getName());
						if (fileName.equals(descFileNames[1])) {
							exists = true;
							break;
						}
					}
					if (!exists) {
						msg += fileName + "　　　　　 本地rar打包文件缺失" + FileUtil.LINE_SEPARATOR;
					}
				}
			}
			if (!"".equals(msg)) {
				FileUtils.writeStringToFile(new File(logPath + timeTxt), titleMsg + FileUtil.LINE_SEPARATOR + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 文件夹中录音文件个数与压缩包中录音文件个数是否一致
		try {
			// 文件名
			String timeTxt = "录音文件个数异常-2014-01-01.txt";
			String titleMsg = "原文件夹名                 文件夹录音个数       打包文件录音个数　　丢失个数       丢失录音名";
			String msg = "";
			for (File sf : srcFiles) {
				// 判断是否是目录
				if (sf.isDirectory()) {
					// 源文件夹名
					String srcFileName = sf.getName();
					// System.out.println("src:"+srcFileName);
					// 源文件夹下的所有录音文件
					File[] sfs = sf.listFiles();
					// 源文件夹下的录音文件个数,压缩文件有个文件夹，所有加1
					int srcLen = sfs.length + 1;
					// System.out.println(srcLen);
					for (File df : descFiles) {
						// 目标rar文件名
						String descFileName = df.getName();
						// System.out.println("desc:"+descFileName);
						// 取得文件名去掉扩展名
						String[] descFileNames = FileUtil.getFileNameExtension(descFileName);
						if (srcFileName.equals(descFileNames[1])) {
							Compress c = new Compress();
							c.setSrcPath(df.getAbsolutePath());
							CompressUtil.setCompress(c);
							List<CompressAttr> attrs = c.getCompressAttrs();
							int descLen = attrs.size();
							if (srcLen != descLen) {// 如果录音个数不一致
								for (File strF : sfs) {// 循环源文件夹下的录音
									boolean exists = false;
									// 取得录音名
									String srcRecordName = strF.getName();
									for (CompressAttr attr : attrs) {
										String descRecordName = attr.getFileName();
										// System.out.println(srcRecordName+"\t"+descRecordName);
										if (srcRecordName.equals(descRecordName)) {
											exists = true;
											break;
										}
									}
									if (!exists) {
										// System.out.println(strF.getAbsolutePath());
										// Verint-001-20140804　　 101　　　　　　　　　89 12 600418223751500.wav
										msg += srcFileName + "　　    " + srcLen + "　　　　　　　　　" + descLen + "                  " + (srcLen - descLen) + "             " + srcRecordName + FileUtil.LINE_SEPARATOR;
									}
								}
							}
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
		// 录音文件大小比对
		try {
			// 文件名
			String timeTxt = "录音文件大小异常-2014-01-01.txt";
			String titleMsg = "原文件夹名             打包文件名	                异常录音名	            原大小	       目标大小";
			String msg = "";
			for (File sf : srcFiles) {
				// 判断是否是目录
				if (sf.isDirectory()) {
					// 源文件夹名
					String srcFileName = sf.getName();
					// System.out.println("src:"+srcFileNasme);
					// 源文件夹下的所有录音文件
					File[] sfs = sf.listFiles();
					for (File df : descFiles) {
						// 目标rar文件名
						String descFileName = df.getName();
						// System.out.println("desc:"+descFileName);
						// 取得文件名去掉扩展名
						String[] descFileNames = FileUtil.getFileNameExtension(descFileName);
						if (srcFileName.equals(descFileNames[1])) {
							Compress c = new Compress();
							c.setSrcPath(df.getAbsolutePath());
							CompressUtil.setCompress(c);
							List<CompressAttr> attrs = c.getCompressAttrs();
							for (File strF : sfs) {// 循环源文件夹下的录音
								boolean exists = false;
								// 取得录音名
								String srcRecordName = strF.getName();
								long srcFileLen = strF.length();
								long descFileLen = -1;
								for (CompressAttr attr : attrs) {
									String descRecordName = attr.getFileName();
									// System.out.println(srcRecordName+"\t"+descRecordName);
									if (srcRecordName.equals(descRecordName)) {
										descFileLen = attr.getLength();
										if (srcFileLen == descFileLen) {
											exists = true;
											break;
										}
									}
								}
								if (!exists && descFileLen != -1) {// 去除不存在的录音文件
									// System.out.println(strF.getAbsolutePath());
									// Aspect-010-20140804 Aspect-010-20140804.rar 600418223751300.wav 44226字节 73766字节
									msg += srcFileName + "	   " + df.getName() + "	  " + srcRecordName + "	    " + srcFileLen + "字节	     " + descFileLen + "字节" + FileUtil.LINE_SEPARATOR;
								}
							}
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
