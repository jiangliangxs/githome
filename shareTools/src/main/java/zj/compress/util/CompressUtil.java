package zj.compress.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import zj.check.util.CheckUtil;
import zj.compress.bean.Compress;
import zj.compress.bean.CompressAttr;
import zj.io.util.FileUtil;
import zj.java.util.JavaUtil;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

/**
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class CompressUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 枚举
	 * 
	 * @author zhangjun
	 * 
	 */
	public static class Enums {
		/**
		 * 压缩文件扩展名
		 * 
		 * @author zhangjun
		 * 
		 */
		public static enum FileExtension {
			ZIP("zip", "zip"), RAR("rar", "rar");
			private final String value;
			private final String name;

			/**
			 * 获取值
			 * 
			 * @return 值
			 */
			public String getValue() {
				return value;
			}

			/**
			 * 获取名称
			 * 
			 * @return 名称
			 */
			public String getName() {
				return name;
			}

			/**
			 * 构造值-名称
			 * 
			 * @param value
			 *            值
			 * @param name
			 *            名称
			 */
			FileExtension(String value, String name) {
				this.value = value;
				this.name = name;
			}
		}

		/**
		 * 是否覆盖
		 * 
		 * @author zhangjun
		 * 
		 */
		public static enum OverWrite {
			DEFAULT("", "重复时重命名"), OVER("1", "覆盖"), NO_OVER("2", "不覆盖");
			private final String value;
			private final String name;

			/**
			 * 获取值
			 * 
			 * @return 值
			 */
			public String getValue() {
				return value;
			}

			/**
			 * 获取名称
			 * 
			 * @return 名称
			 */
			public String getName() {
				return name;
			}

			/**
			 * 构造值-名称
			 * 
			 * @param value
			 *            值
			 * @param name
			 *            名称
			 */
			OverWrite(String value, String name) {
				this.value = value;
				this.name = name;
			}
		}

		/**
		 * ant下的zip工具默认压缩编码为UTF-8编码， 而winRAR软件压缩是用的windows默认的GBK或者GB2312编码 所以解压缩时要制定编码格式
		 * 
		 * @author zhangjun
		 * 
		 */
		public static enum Encoding {
			/**
			 * ASCII
			 */
			ASCII("ASCII", "ASCII"),
			/**
			 * MBCS
			 */
			MBCS("MBCS", "MBCS"),
			/**
			 * GB2312
			 */
			GB2312("GB2312", "GB2312"),
			/**
			 * GBK
			 */
			GBK("GBK", "GBK"),
			/**
			 * UNICODE
			 */
			UNICODE("UNICODE", "UNICODE"),
			/**
			 * UTF8
			 */
			UTF8("UTF-8", "UTF-8");
			private final String value;
			private final String name;

			/**
			 * 获取值
			 * 
			 * @return 值
			 */
			public String getValue() {
				return value;
			}

			/**
			 * 获取名称
			 * 
			 * @return 名称
			 */
			public String getName() {
				return name;
			}

			/**
			 * 构造值-名称
			 * 
			 * @param value
			 *            值
			 * @param name
			 *            名称
			 */
			Encoding(String value, String name) {
				this.value = value;
				this.name = name;
			}
		}
	}

	/**
	 * 解压zip格式压缩包 对应的是ant.jar
	 * 
	 * @param compress
	 *            压缩对象
	 * @throws Exception
	 */
	private static void unzip(Compress compress) throws Exception {
		Project p = new Project();
		Expand e = new Expand();
		e.setProject(p);
		e.setSrc(new File(compress.getSrcPath()));
		String overFlg = compress.getOverWrite();
		e.setOverwrite(CompressUtil.Enums.OverWrite.OVER.getValue().equals(overFlg) ? true : false);
		e.setDest(new File(compress.getDescPath()));
		/*
		 * ant下的zip工具默认压缩编码为UTF-8编码， 而winRAR软件压缩是用的windows默认的GBK或者GB2312编码 所以解压缩时要制定编码格式
		 */
		e.setEncoding(compress.getEncoding());
		e.execute();
	}

	/**
	 * 解压rar格式压缩包。 对应的是java-unrar-0.3.jar，但是java-unrar-0.3.jar又会用到commons-logging-1.1.1.jar
	 * 
	 * @param compress
	 *            压缩对象
	 * @throws Exception
	 */
	private static void unrar(Compress compress) throws Exception {
		Archive arc = null;
		BufferedOutputStream bos = null;
		try {
			arc = new Archive(new File(compress.getSrcPath()));
			FileHeader fh = arc.nextFileHeader();
			// 设置压缩文件里内容具体信息
			List<CompressAttr> attrs = compress.getCompressAttrs();
			while (fh != null) {
				// 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
				String compressFileName = JavaUtil.trim(fh.getFileNameString());
				// 设置目标文件/文件夹路径
				String descPath = compress.getDescPath() + FileUtil.changePathSeparator(compressFileName);
				long length = fh.getFullUnpackSize();
				// //("目标文件路径:【" + descPath + "】压缩文件路径:【" + compressFileName + "】压缩文件大小:【" + length + "】");
				// 设置压缩文件里内容具体信息
				CompressAttr attr = new CompressAttr();
				attrs.add(attr);
				attr.setDescPath(compressFileName);
				attr.setLength(length);
				attr.setDirectory(fh.isDirectory());
				// 取得文件名
				String[] compressFileNames = FileUtil.getFileNameExtension(compressFileName);
				// 文件名
				attr.setFileName(compressFileNames[1] + compressFileNames[2]);
				if (fh.isDirectory()) {
				} else {
					if (CheckUtil.isNotNull(compress.getDescPath())) {
						String[] descPaths = FileUtil.getFileNameExtension(descPath);
						File descFile = new File(descPath);
						if (CompressUtil.Enums.OverWrite.OVER.getValue().equals(compress.getOverWrite())) {
							// 覆盖
							// //("【" + descPath + "】文件重名,系统自动覆盖此文件");
						} else {
							if (descFile.exists()) {
								if (CompressUtil.Enums.OverWrite.NO_OVER.getValue().equals(compress.getOverWrite())) {
									// //("【" + descPath + "】文件重名,系统自动过虑此文件");
									fh = arc.nextFileHeader();
									continue;
								} else {
									String newPath = descPaths[0] + descPaths[1] + JavaUtil.getUUID() + descPaths[2];
									// //("【" + descPath + "】文件重名,系统自动重新命名:【" + newPath + "】");
									descFile = new File(newPath);
								}
							}
						}
						// 2创建文件夹
						File dir = new File(descPaths[0]);
						if (!dir.exists() || !dir.isDirectory()) {
							dir.mkdirs();
						}
						// 3解压缩文件
						FileOutputStream fos = new FileOutputStream(descFile);
						bos = new BufferedOutputStream(fos);
						arc.extractFile(fh, fos);
					}
				}
				fh = arc.nextFileHeader();
			}
		} finally {
			if (arc != null) {
				try {
					arc.close();
					arc = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
					bos = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解压缩rar,zip
	 * 
	 * @param compress
	 *            压缩对象
	 * @throws Exception
	 */
	public static void setCompress(Compress compress) throws Exception {
		/** 支持的文件扩展名 **/
		String srcType = FilenameUtils.getExtension(compress.getSrcPath());
		// 保证文件夹路径最后是"/"或者"\"
		if (CheckUtil.isNull(compress.getDescPath())) {
			compress.setDescPath("");
			// //("压缩文件内容没有设置解压目录,因此系统将压缩文件内容读取至内存中");
		} else {
			compress.setDescPath(FileUtil.changePathSeparator(compress.getDescPath()));
			if (!compress.getDescPath().endsWith(File.separator)) {
				compress.setDescPath(compress.getDescPath() + File.separator);
			}
		}
		// 根据类型，进行相应的解压缩
		if (CompressUtil.Enums.FileExtension.ZIP.getValue().equalsIgnoreCase(srcType)) {
			unzip(compress);
		} else if (CompressUtil.Enums.FileExtension.RAR.getValue().equalsIgnoreCase(srcType)) {
			unrar(compress);
		} else {
			// //("只支持zip和rar格式的压缩包！");
			List<String> fes = new ArrayList<String>();
			for (CompressUtil.Enums.FileExtension fe : CompressUtil.Enums.FileExtension.values()) {
				fes.add(fe.getValue());
			}
			throw new Exception("只支持[" + Arrays.toString(fes.toArray()) + "]格式的压缩包！");
		}
	}
}
