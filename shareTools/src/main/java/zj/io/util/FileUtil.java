package zj.io.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import zj.check.util.CheckUtil;
import zj.io.model.JarClass;
import zj.io.model.JarParams;
import zj.io.service.FileFilterI;
import zj.io.service.ReadLinesBatchCallI;
import zj.io.service.ReadLinesCallI;

/**
 * 类名 ：FileUtil<br>
 * 概况 ：文件工具类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class FileUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 写文件换行标识 **/
	// System.getProperty("line.separator").equals(FileUtil.LINE_SEPARATOR)->true
	public static String LINE_SEPARATOR;// = System.getProperty("line.separator");
	/** 文件分割符:/,\\ **/
	public static final String SEPARATOR = File.separator;
	private transient static final Logger logger = Logger.getLogger(FileUtil.class);
	static {
		// avoid security issues
		StringBuilderWriter buf = new StringBuilderWriter(4);
		PrintWriter out = new PrintWriter(buf);
		out.println();
		LINE_SEPARATOR = buf.toString();
		out.close();
	}

	/**
	 * 取得文件的创建时间
	 * 
	 * @param file
	 * @return
	 */
	public static final String getCreateTime(File file) {
		String createTime = "";
		try {
			Process p = Runtime.getRuntime().exec("cmd /C dir " + file.getAbsolutePath() + " /tc");
			InputStream is = p.getInputStream();
			// ant下的zip工具默认压缩编码为UTF-8编码， 而winRAR软件压缩是用的windows默认的GBK或者GB2312编码 所以解压缩时要制定编码格式
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "gbk"));
			String line = null;
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (++i == 6) {
					createTime = line.substring(0, 17);
				}
			}
		} catch (Exception e) {
			createTime = "";
			logger.error(e.getMessage());
		}
		return createTime;
	}

	/**
	 * 根据系统改变路径分割符号
	 * 
	 * @param path
	 *            路径
	 * @param isSpeEnd
	 *            是否添加最后分割符
	 *            <p>
	 *            true:添加
	 *            </p>
	 *            <p>
	 *            false:默认
	 *            </p>
	 * @see #changePathSeparator(String, zj.io.util.ConstantForEnum.ChangePathLastSeparator)
	 * @return 改变后的系统路径
	 */
	@Deprecated
	public static final String changePathSeparator(String path, boolean isSpeEnd) {
		if (CheckUtil.isNull(path))
			return "";
		// logger.debug("改变路径分割符号前path:" + path);
		if (SEPARATOR.equals("/")) {
			// 非windows系统
			path = path.replaceAll("\\\\", "/");
			if (isSpeEnd) {
				if (!path.endsWith("/")) {
					path = path + "/";
				}
			}
		} else {
			// windows系统
			path = path.replaceAll("/", "\\\\");
			if (isSpeEnd) {
				if (!path.endsWith("\\")) {
					path = path + "\\";
				}
			}
		}
		return path;
	}

	/**
	 * 根据系统改变路径分割符号
	 * 
	 * @param path
	 *            路径
	 * @param sepEnum
	 *            常量枚举{@link zj.io.util.ConstantForEnum.ChangePathLastSeparator}
	 * @return 改变后的系统路径
	 */
	public static final String changePathSeparator(String path, ConstantForEnum.ChangePathLastSeparator sepEnum) {
		if (CheckUtil.isNull(path))
			return "";
		// logger.debug("改变路径分割符号前path:" + path);
		if (SEPARATOR.equals("/")) {
			path = linuxSeparator(path, sepEnum);
		} else {
			path = windowsSeparator(path, sepEnum);
		}
		return path;
	}

	/**
	 * 根据系统改变包路径分割符号
	 * 
	 * @param packagePath
	 *            包路径
	 * @return 包路径
	 */
	public static final String packageToPath(String packagePath) {
		if (CheckUtil.isNull(packagePath))
			return "";
		// logger.debug("改变路径分割符号前path:" + path);
		if (SEPARATOR.equals("/")) {
			packagePath = packagePath.replaceAll("\\.", "/");
		} else {
			packagePath = packagePath.replaceAll("\\.", "\\\\");
		}
		return packagePath;
	}

	/**
	 * window分割符
	 * 
	 * @param path
	 *            路径
	 * @return 改变后的系统路径
	 */
	public static final String windowsSeparator(String path) {
		return windowsSeparator(path, ConstantForEnum.ChangePathLastSeparator.NONE);
	}

	/**
	 * window分割符
	 * 
	 * @param path
	 *            路径
	 * @param sepEnum
	 *            常量枚举{@link zj.io.util.ConstantForEnum.ChangePathLastSeparator}
	 * @return 改变后的系统路径
	 */
	public static final String windowsSeparator(String path, ConstantForEnum.ChangePathLastSeparator sepEnum) {
		if (CheckUtil.isNull(path))
			return "";
		// windows系统
		path = path.replaceAll("/", "\\\\");
		if (ConstantForEnum.ChangePathLastSeparator.ADD_ALL == sepEnum || ConstantForEnum.ChangePathLastSeparator.ADD_BEFORE == sepEnum) {
			if (!path.startsWith("\\")) {
				path = "\\" + path;
			}
		}
		if (ConstantForEnum.ChangePathLastSeparator.ADD_ALL == sepEnum || ConstantForEnum.ChangePathLastSeparator.ADD_AFTER == sepEnum) {
			if (!path.endsWith("\\")) {
				path = path + "\\";
			}
		}
		if (ConstantForEnum.ChangePathLastSeparator.DEL_ALL == sepEnum || ConstantForEnum.ChangePathLastSeparator.DEL_BEFORE == sepEnum) {
			if (path.startsWith("\\")) {
				path = path.substring(1);
			}
		}
		if (ConstantForEnum.ChangePathLastSeparator.DEL_ALL == sepEnum || ConstantForEnum.ChangePathLastSeparator.DEL_AFTER == sepEnum) {
			if (path.endsWith("\\")) {
				path = path.substring(0, path.length() - 1);
			}
		}
		return path;
	}

	/**
	 * linux分割符
	 * 
	 * @param path
	 *            路径
	 * @return 改变后的系统路径
	 */
	public static final String linuxSeparator(String path) {
		return linuxSeparator(path, ConstantForEnum.ChangePathLastSeparator.NONE);
	}

	/**
	 * linux分割符
	 * 
	 * @param path
	 *            路径
	 * @param sepEnum
	 *            常量枚举{@link zj.io.util.ConstantForEnum.ChangePathLastSeparator}
	 * @return 改变后的系统路径
	 */
	public static final String linuxSeparator(String path, ConstantForEnum.ChangePathLastSeparator sepEnum) {
		if (CheckUtil.isNull(path))
			return "";
		// 非windows系统
		path = path.replaceAll("\\\\", "/");
		// switch (sepEnum) {
		// case ADD_ALL:
		// case ADD_BEFORE:
		// break;
		// case ADD_AFTER:
		// break;
		// default:
		// break;
		// }
		if (ConstantForEnum.ChangePathLastSeparator.ADD_ALL == sepEnum || ConstantForEnum.ChangePathLastSeparator.ADD_BEFORE == sepEnum) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
		}
		if (ConstantForEnum.ChangePathLastSeparator.ADD_ALL == sepEnum || ConstantForEnum.ChangePathLastSeparator.ADD_AFTER == sepEnum) {
			if (!path.endsWith("/")) {
				path = path + "/";
			}
		}
		if (ConstantForEnum.ChangePathLastSeparator.DEL_ALL == sepEnum || ConstantForEnum.ChangePathLastSeparator.DEL_BEFORE == sepEnum) {
			if (path.startsWith("/")) {
				path = path.substring(1);
			}
		}
		if (ConstantForEnum.ChangePathLastSeparator.DEL_ALL == sepEnum || ConstantForEnum.ChangePathLastSeparator.DEL_AFTER == sepEnum) {
			if (path.endsWith("/")) {
				path = path.substring(0, path.length() - 1);
			}
		}
		return path;
	}

	/**
	 * 根据系统改变路径分割符号
	 * 
	 * @param path
	 * @return
	 */
	public static final String changePathSeparator(String path) {
		return changePathSeparator(path, ConstantForEnum.ChangePathLastSeparator.NONE);
	}

	/**
	 * 获得文件扩展名及前面的字符串
	 * 
	 * @param fileName
	 *            E:\\xmls\\iqc_basic_user.xml [E:\xmls\,iqc_basic_user,.xml,false]
	 * @return
	 */
	public static final String[] getFileNameExtension(String filePath) {
		String[] rtnStrs = new String[4];
		rtnStrs[0] = "";
		rtnStrs[1] = "";
		rtnStrs[2] = "";
		rtnStrs[3] = "";
		String dirPath = "";
		String fileName = "";
		String fileExtension = "";
		String tempStr = "";
		int index = -1;
		filePath = changePathSeparator(filePath);
		if (CheckUtil.isNotNull(filePath)) {
			index = filePath.lastIndexOf(SEPARATOR);
			if (index >= 0) {
				dirPath = filePath.substring(0, index + 1);
				tempStr = filePath.substring(index + 1);
				index = tempStr.lastIndexOf(".");
				if (index >= 0) {
					fileName = tempStr.substring(0, index);
					fileExtension = tempStr.substring(index);
				}
			} else {
				index = filePath.lastIndexOf(".");
				if (index >= 0) {
					fileName = filePath.substring(0, index);
					dirPath = fileName;
					fileExtension = filePath.substring(index);
				} else {
					dirPath = filePath;
					fileName = filePath;
				}
			}
		}
		rtnStrs[0] = dirPath;
		rtnStrs[1] = fileName;
		rtnStrs[2] = fileExtension;
		String diskPath = "";
		try {
			diskPath = dirPath.substring(dirPath.indexOf("\\") + 1);
		} catch (Exception e) {
			diskPath = "";
		}
		if ("".equals(diskPath)) {
			rtnStrs[3] = "true";
		} else {
			rtnStrs[3] = "false";
		}
		return rtnStrs;
	}

	/**
	 * 获取图片流
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static final ImageReader getImageReader(File file) throws IOException {
		String fileExtension = FilenameUtils.getExtension(file.getName());
		ImageReader reader = null;
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(fileExtension);
		reader = readers.next();
		ImageInputStream iis = ImageIO.createImageInputStream(file);
		reader.setInput(iis, true);
		return reader;
	}

	/**
	 * 创建文件或目录
	 * 
	 * @param file
	 *            文件对象
	 * @param isFolder
	 *            是否是目录
	 * @return 创建是否成功
	 * @throws IOException
	 */
	public static final boolean createFolderOrFile(File file, boolean isFolder) throws IOException {
		if (file == null)
			return false;
		if (isFolder) {
			if (file.exists()) {
				if (file.isFile()) {
					logger.warn("创建文件夹失败:将要创建的文件【" + file.getAbsolutePath() + "】重名");
				} else if (file.isDirectory()) {
					logger.warn("创建文件夹失败:将要创建的文件夹【" + file.getAbsolutePath() + "】已经存在");
				}
				return false;
			} else {
				file.mkdirs();
				logger.debug("创建文件夹【" + file.getAbsolutePath() + "】成功!");
				return true;
			}
		} else {
			String path = file.getPath();
			String[] tempPaths = getFileNameExtension(path);
			File fileDir = new File(tempPaths[0]);
			if (fileDir.exists()) {
				logger.warn("创建文件夹失败:将要创建的文件夹【" + fileDir.getAbsolutePath() + "】已经存在");
				return false;
			} else {
				fileDir.mkdirs();
				logger.debug("创建文件夹【" + fileDir.getAbsolutePath() + "】成功!");
				return true;
			}
		}
	}

	/**
	 * 创建文件或目录
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static final boolean createFolderOrFile(String file) throws IOException {
		return createFolderOrFile(file, false);
	}

	/**
	 * 创建文件或目录
	 * 
	 * @param file
	 * @param isFolder
	 * @return
	 * @throws IOException
	 */
	public static final boolean createFolderOrFile(String file, boolean isFolder) throws IOException {
		if (CheckUtil.isNull(file)) {
			return false;
		}
		file = changePathSeparator(file);
		return createFolderOrFile(new File(file), isFolder);
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static final void forceMkdirFolderOrFile(String file) throws IOException {
		if (CheckUtil.isNull(file)) {
			return;
		}
		file = changePathSeparator(file);
		forceMkdirFolderOrFile(new File(file));
	}

	/**
	 * 创建文件或目录
	 * 
	 * @param file
	 *            文件对象
	 * @throws IOException
	 */
	public static final void forceMkdirFolderOrFile(File file) throws IOException {
		if (file == null)
			return;
		if (!file.exists()) {
			// 创建目录
			if (file.isFile()) {
				String path = file.getPath();
				String[] tempPaths = getFileNameExtension(path);
				File fileDir = new File(tempPaths[0]);
				if (fileDir.exists()) {
					logger.warn("创建文件夹失败:将要创建的文件夹【" + fileDir.getAbsolutePath() + "】已经存在");
				} else {
					fileDir.mkdirs();
					logger.debug("创建文件夹【" + fileDir.getAbsolutePath() + "】成功!");
				}
			} else {
				file.mkdirs();
				logger.debug("创建文件夹【" + file.getAbsolutePath() + "】成功!");
			}
		}
		if (file.isFile()) {
			file.createNewFile();
			logger.debug("创建文件【" + file.getAbsolutePath() + "】成功!");
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static final boolean createFolderOrFile(File file) throws IOException {
		return createFolderOrFile(file, false);
	}

	/**
	 * 将一个字符串转化为输入流
	 * 
	 * @param sInputString
	 * @return
	 */
	public static final InputStream getStringStream(String sInputString) {
		return getStringStream(sInputString, "utf-8");
	}

	/**
	 * 将一个字符串转化为输入流
	 * 
	 * @param sInputString
	 * @param charset
	 * @return
	 */
	public static final InputStream getStringStream(String sInputString, String charset) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes(charset));
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将一个输入流转化为字符串
	 * 
	 * @see #readString(File)
	 * @param file
	 *            文件对象
	 * @return
	 */
	@Deprecated
	public static final String getStreamString(File file) throws IOException {
		return getStreamString(new BufferedInputStream(new FileInputStream(file)));
	}

	/**
	 * 将一个输入流转化为字符串
	 * 
	 * @see #readString(InputStream, boolean)
	 * @param is
	 *            输入流
	 * @return 文件内容
	 */
	public static final String getStreamString(InputStream is) throws IOException {
		InputStreamReader isr = null;
		if (is != null) {
			StringBuffer sb = new StringBuffer();
			BufferedReader br = null;
			try {
				br = new BufferedReader((isr = new InputStreamReader(is)));
				String sLine = null;
				sLine = br.readLine();
				sLine = IOUtils.readFirstLine(sLine);
				if (sLine != null) {
					sb.append(sLine);
					while ((sLine = br.readLine()) != null) {
						sb.append(sLine);
					}
				}
				return sb.toString();
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (isr != null)
						isr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (is != null)
						is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return null;
	}

	/**
	 * 设置一个目录的所有文件至集合中
	 * 
	 * @param fileList
	 *            过虑后的文件集合
	 * @param dir
	 *            目录
	 * @param filter
	 *            自定义文件过虑器
	 * @param defaultFilter
	 *            默认过虑器
	 */
	public static final void setFilterFilesLevel(List<File> fileList, String dir, FileFilterI filter, FileFilter defaultFilter, int level) {
		File file = new File(dir);
		if (filter == null) {
			if (file.isDirectory()) {
				File[] files = null;
				if (defaultFilter == null) {
					files = file.listFiles();
				} else {
					files = file.listFiles(defaultFilter);
				}
				if (files == null) {
					logger.debug("文件:" + file.getAbsolutePath() + "不存在");
				} else {
					for (File info : files) {
						if (info.isDirectory()) {
							fileList.add(info);
							setFilterFilesLevel(fileList, info.getPath(), filter, defaultFilter, 0);
						} else {
							// 如果是文件
							fileList.add(info);
						}
					}
				}
			} else {
				fileList.add(file);
			}
		} else {
			int tempLevel = level + 1;
			if (file.isDirectory()) {
				if (filter.interrupt(file, fileList)) {
					logger.debug("待判断文件夹:" + file.getAbsolutePath() + "退出");
					return;
				}
				File[] files = null;
				if (defaultFilter == null) {
					files = file.listFiles();
				} else {
					files = file.listFiles(defaultFilter);
				}
				if (files == null) {
					logger.debug("文件:" + file.getAbsolutePath() + "不存在");
				} else {
					for (File info : files) {
						if (level == 0) {
							// 如果当前是开始目录,则设置为1
							tempLevel = 1;
						}
						filter.setLevel(tempLevel);
						if (filter.accept(info)) {
							if (info.isDirectory()) {
								if (filter.acceptDir(info)) {
									if (filter.interrupt(info, fileList)) {
										logger.debug("循环中待添加文件夹:" + info.getAbsolutePath() + "中断");
										break;
									}
									fileList.add(info);
								}
								if (filter.interrupt(info, fileList)) {
									logger.debug("待回调文件夹:" + info.getAbsolutePath() + "中断");
									break;
								}
								setFilterFilesLevel(fileList, info.getPath(), filter, defaultFilter, tempLevel);
							} else {
								// 如果是文件
								if (filter.acceptFile(info)) {
									if (filter.interrupt(info, fileList)) {
										logger.debug("循环中待添加文件:" + info.getAbsolutePath() + "中断");
										break;
									}
									fileList.add(info);
								}
							}
						}
					}
				}
			} else {
				// 设置当前文件级别
				filter.setLevel(tempLevel);
				if (filter.accept(file)) {
					if (filter.interrupt(file, fileList)) {
						logger.debug("待判断文件:" + file.getAbsolutePath() + "退出");
						return;
					}
					if (filter.acceptFile(file)) {
						if (filter.interrupt(file, fileList)) {
							logger.debug("待添加文件:" + file.getAbsolutePath() + "中断");
							return;
						}
						fileList.add(file);
					}
				}
			}
		}
	}

	/**
	 * 设置一个目录的所有文件至集合中
	 * 
	 * @param fileList
	 *            过虑后的文件集合
	 * @param dir
	 *            目录
	 * @param filter
	 *            自定义文件过虑器
	 * @param defaultFilter
	 *            默认过虑器
	 */
	public static final void setFilterFiles(List<File> fileList, String dir, FileFilterI filter, FileFilter defaultFilter) {
		setFilterFilesLevel(fileList, dir, filter, defaultFilter, 0);
	}

	/**
	 * 设置一个目录的所有文件至集合中
	 * 
	 * @param fileList
	 *            过虑后的文件集合
	 * @param dir
	 *            目录
	 * @param defaultFilter
	 *            默认过虑器
	 */
	public static final void setFilterFiles(List<File> fileList, String dir, FileFilter defaultFilter) {
		setFilterFiles(fileList, dir, null, defaultFilter);
	}

	/**
	 * 设置一个目录的所有文件至集合中
	 * 
	 * @param fileList
	 *            过虑后的文件集合
	 * @param dir
	 *            目录
	 * @param filter
	 *            自定义文件过虑器
	 */
	public static final void setFilterFiles(List<File> fileList, String dir, FileFilterI filter) {
		setFilterFiles(fileList, dir, filter, null);
	}

	/**
	 * 设置一个目录的所有文件至集合中
	 * 
	 * @param fileList
	 *            过虑后的文件集合
	 * @param dir
	 *            目录
	 */
	public static final void setFilterFiles(List<File> fileList, String dir) {
		setFilterFiles(fileList, dir, null, null);
	}

	/**
	 * 设置属性文件的值
	 * 
	 * @param path
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public static final void setProperty(String path, String key, String value) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		BufferedInputStream bis = new BufferedInputStream(fis);
		// 配置文件内容解析
		Properties prop = new Properties();
		prop.load(bis);
		bis.close();
		fis.close();
		FileOutputStream fos = new FileOutputStream(path);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		// Properties prop = getProperties(path);
		prop.setProperty(key, value);
		prop.store(bos, null);
		bos.flush();
		fos.flush();
		bos.close();
		fos.close();
	}

	/**
	 * 获取属性值
	 * 
	 * @param path
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static final String getProperty(String path, String key) throws Exception {
		return getProperties(path).getProperty(key);
	}

	/**
	 * 获取属性对象
	 * 
	 * @param path
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static final Properties getProperties(String path) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		BufferedInputStream bis = new BufferedInputStream(fis);
		// 配置文件内容解析
		Properties prop = new Properties();
		prop.load(bis);
		bis.close();
		fis.close();
		return prop;
	}

	/**
	 * 将对象序列化到磁盘文件中
	 * 
	 * @param t
	 * @param filePath
	 * @throws Exception
	 */
	public static final <T> void writeObjectToFile(T t, String filePath) throws Exception {
		writeObjectToFile(t, new File(filePath));
	}

	/**
	 * 将对象序列化到磁盘文件中
	 * 
	 * @param t
	 * @param file
	 * @throws Exception
	 */
	public static final <T> void writeObjectToFile(T t, File file) throws Exception {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			String[] fileExts = getFileNameExtension(file.getAbsolutePath());
			String newFilePath = "";
			if (fileExts.length > 0) {
				newFilePath += fileExts[0];
			}
			File extFileDir = new File(newFilePath);
			if (!extFileDir.exists()) {
				extFileDir.mkdirs();
			}
			if (fileExts.length > 1) {
				newFilePath += fileExts[1];
			}
			if (fileExts.length > 2) {
				newFilePath += fileExts[2];
			}
			file = new File(newFilePath);
			if (file.exists()) {
				file.delete();
			}
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);
			oos.writeObject(t);
			oos.flush();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 反序列化,将字符串转化为对象
	 * 
	 * @param serStr
	 * @return
	 * @throws Exception
	 */
	public static final <T> T readStrToObject(String serStr) throws Exception {
		return readStrToObject(serStr, "UTF-8");
	}

	/**
	 * 反序列化,将字符串转化为对象
	 * 
	 * @param serStr
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T readStrToObject(String serStr, String charsetName) throws Exception {
		if (CheckUtil.isNull(serStr))
			return null;
		T obj = null;
		ObjectInputStream ois = null;
		ByteArrayInputStream bais = null;
		try {
			String redStr = "";
			redStr = java.net.URLDecoder.decode(serStr, charsetName);
			bais = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
			ois = new ObjectInputStream(bais);
			obj = (T) ois.readObject();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (bais != null) {
					bais.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	// //序列化对象为String字符串，先对序列化后的结果进行BASE64编码，否则不能直接进行反序列化
	// public static final String writeObject(Object o) throws Exception {
	// ByteArrayOutputStream bos = new ByteArrayOutputStream();
	// ObjectOutputStream oos = new ObjectOutputStream(bos);
	// oos.writeObject(o);
	// oos.flush();
	// oos.close();
	// bos.close();
	// //return new BASE64Encoder().encode(bos.toByteArray());
	// return new String(bos.toByteArray(), "ISO-8859-1");
	// }
	//
	// //反序列化String字符串为对象
	//
	// public static final Object readObject(String object) throws Exception{
	// //ByteArrayInputStream bis = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(object));
	// ByteArrayInputStream bis = new ByteArrayInputStream(object.getBytes("ISO-8859-1"));
	// ObjectInputStream ois = new ObjectInputStream(bis);
	// Object o = null;
	// try {
	// o = ois.readObject();
	// } catch(EOFException e) {
	// System.err.print("read finished");
	// }
	// bis.close();
	// ois.close();
	// return o;
	// }
	/**
	 * 将对象序列化成字符串
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public static final <T> String writeObjectToStr(T t) throws Exception {
		if (t == null)
			return null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		String serStr = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(t);
			oos.flush();
			baos.flush();
			serStr = baos.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		} finally {
			oos.close();
			baos.close();
		}
		return serStr == null ? "" : serStr;
	}

	/**
	 * 反序列化,将磁盘文件转化为对象
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static final <T> T readFileToObject(String filePath) throws Exception {
		if (filePath == null || filePath.trim().equals(""))
			return null;
		File file = new File(filePath);
		return readFileToObject(file);

	}

	/**
	 * 反序列化,将磁盘文件转化为对象
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T readFileToObject(File file) throws Exception {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		T obj = null;
		try {
			if (!file.exists())
				return null;
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			obj = (T) ois.readObject();
		} finally {
			ois.close();
			bis.close();
			fis.close();
		}
		return obj;

	}

	// readLinesCount
	// -----------------------------------------------------------------------
	public static final long readLinesCount(File file) throws IOException {
		return readLinesCount(file, null);
	}

	public static final long readLinesCount(File file, String encoding) throws IOException {
		InputStream in = null;
		try {
			in = FileUtils.openInputStream(file);
			return readLinesCount(in, encoding);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static final long readLinesCount(InputStream input, String encoding) throws IOException {
		if (encoding == null) {
			return readLinesCount(input);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readLinesCount(reader);
		}
	}

	public static final long readLinesCount(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLinesCount(reader);
	}

	public static final long readLinesCount(Reader input) throws IOException {
		long lineNum = 0;
		BufferedReader reader = new BufferedReader(input);
		String line = reader.readLine();
		line = IOUtils.readFirstLine(line);
		while (line != null) {
			lineNum++;
			line = reader.readLine();
		}
		return lineNum;
	}

	// readString
	/**
	 * 读取文件内容
	 * 
	 * @param file
	 *            文件对象
	 * @param line
	 *            是否换行(true:换行,false:非换行),默认换行
	 * @return 文件内容
	 * @throws IOException
	 */
	public static final String readString(File file) throws IOException {
		return readString(file, true);
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 *            文件对象
	 * @param line
	 *            是否换行(true:换行,false:非换行)
	 * @return 文件内容
	 * @throws IOException
	 */
	public static final String readString(File file, boolean line) throws IOException {
		return readString(file, null, line);
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 *            文件对象
	 * @param encoding
	 *            编码
	 * @param line
	 *            是否换行(true:换行,false:非换行)
	 * @return 文件内容
	 * @throws IOException
	 */
	public static final String readString(File file, String encoding, boolean line) throws IOException {
		InputStream in = null;
		try {
			in = FileUtils.openInputStream(file);
			return readString(in, encoding, line);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param input
	 *            输入流
	 * @param encoding
	 *            编码
	 * @param line
	 *            是否换行(true:换行,false:非换行)
	 * @return 文件内容
	 * @throws IOException
	 */
	public static final String readString(InputStream input, String encoding, boolean line) throws IOException {
		if (encoding == null) {
			return readString(input, line);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readString(reader, line);
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param input
	 *            输入流
	 * @param line
	 *            是否换行(true:换行,false:非换行)
	 * @return 文件内容
	 * @throws IOException
	 */
	public static final String readString(InputStream input, boolean line) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readString(reader, line);
	}

	/**
	 * 读取文件内容
	 * 
	 * @param input
	 *            输入流
	 * @param line
	 *            是否换行(true:换行,false:非换行)
	 * @return 文件内容
	 * @throws IOException
	 */
	public static final String readString(Reader input, boolean line) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(input);
		String lineString = reader.readLine();
		lineString = IOUtils.readFirstLine(lineString);
		if (line) {
			while (lineString != null) {
				sb.append(lineString).append(IOUtils.LINE_SEPARATOR);
				lineString = reader.readLine();
			}
		} else {
			while (lineString != null) {
				sb.append(lineString);
				lineString = reader.readLine();
			}
		}
		return sb.toString();
	}

	// readLines
	// -----------------------------------------------------------------------

	public static final <T extends Collection<String>> void readLines(T t, File file) throws IOException {
		readLines(t, file, null);
	}

	public static final <T extends Collection<String>> void readLines(T t, File file, String encoding) throws IOException {
		InputStream in = null;
		try {
			in = FileUtils.openInputStream(file);
			readLines(t, in, encoding);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static final <T extends Collection<String>> void readLines(T t, InputStream input, String encoding) throws IOException {
		if (encoding == null) {
			readLines(t, input);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			readLines(t, reader);
		}
	}

	public static final <T extends Collection<String>> void readLines(T t, InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		readLines(t, reader);
	}

	public static final <T extends Collection<String>> void readLines(T t, Reader input) throws IOException {
		Collection<String> coll = getColl(t);
		BufferedReader reader = new BufferedReader(input);
		String line = reader.readLine();
		line = IOUtils.readFirstLine(line);
		while (line != null) {
			coll.add(line);
			line = reader.readLine();
		}
	}

	// readLinesCountI
	// -----------------------------------------------------------------------
	public static final <T extends Collection<String>> long readLinesCountI(File file, ReadLinesCallI<T> call) throws IOException {
		return readLinesCountI(file, null, call);
	}

	public static final <T extends Collection<String>> long readLinesCountI(File file, String encoding, ReadLinesCallI<T> call) throws IOException {
		InputStream in = null;
		try {
			in = FileUtils.openInputStream(file);
			return readLinesCountI(in, encoding, call);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static final <T extends Collection<String>> long readLinesCountI(InputStream input, String encoding, ReadLinesCallI<T> call) throws IOException {
		if (encoding == null) {
			return readLinesCountI(input, call);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readLinesCountI(reader, call);
		}
	}

	public static final <T extends Collection<String>> long readLinesCountI(InputStream input, ReadLinesCallI<T> call) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLinesCountI(reader, call);
	}

	public static final <T extends Collection<String>> long readLinesCountI(Reader input, ReadLinesCallI<T> call) throws IOException {
		long lineNum = 0;
		BufferedReader reader = new BufferedReader(input);
		String line = reader.readLine();
		line = IOUtils.readFirstLine(line);
		long startLineNum = call.getStartLineNum();
		long endLineNum = call.getEndLineNum();
		long totalLineNum = 0;
		while (line != null) {
			totalLineNum++;
			if (startLineNum <= totalLineNum && (endLineNum <= 0 || endLineNum >= totalLineNum)) {
				// 开始/结束行区间数据
				line = call.changeLine(line, totalLineNum);
				if (call.interrupt(line, totalLineNum)) {
					break;
				} else {
					if (!call.filter(line, totalLineNum)) {
						lineNum++;
					}
				}
			}
			// 读取下一行
			line = reader.readLine();
		}
		return lineNum;
	}

	// readLinesI
	// -----------------------------------------------------------------------
	public static final <T extends Collection<String>> T readLinesI(File file, ReadLinesCallI<T> call) throws IOException {
		return readLinesI(file, null, call);
	}

	public static final <T extends Collection<String>> T readLinesI(File file, String encoding, ReadLinesCallI<T> call) throws IOException {
		InputStream in = null;
		try {
			in = FileUtils.openInputStream(file);
			return readLinesI(in, encoding, call);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static final <T extends Collection<String>> T readLinesI(InputStream input, String encoding, ReadLinesCallI<T> call) throws IOException {
		if (encoding == null) {
			return readLinesI(input, call);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readLinesI(reader, call);
		}
	}

	public static final <T extends Collection<String>> T readLinesI(InputStream input, ReadLinesCallI<T> call) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLinesI(reader, call);
	}

	/**
	 * 把文件中每一行设置到集合中(never return null)
	 * 
	 * @param input
	 * @param call
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends Collection<String>> T readLinesI(Reader input, ReadLinesCallI<T> call) throws IOException {
		T t = call.getObj();
		Collection<String> coll = getColl(t);
		BufferedReader reader = new BufferedReader(input);
		String line = reader.readLine();
		line = IOUtils.readFirstLine(line);
		long startLineNum = call.getStartLineNum();
		long endLineNum = call.getEndLineNum();
		long totalLineNum = 0;
		while (line != null) {
			totalLineNum++;
			if (startLineNum <= totalLineNum && (endLineNum <= 0 || endLineNum >= totalLineNum)) {
				// 开始/结束行区间数据
				line = call.changeLine(line, totalLineNum);
				if (call.interrupt(line, totalLineNum)) {
					break;
				} else {
					if (!call.filter(line, totalLineNum)) {
						coll.add(line);
					}
				}
			}
			// 读取下一行
			line = reader.readLine();
		}
		return (T) coll;
	}

	// readLinesBatchI
	// -----------------------------------------------------------------------
	public static final <T extends Collection<String>> List<T> readLinesBatchI(File file, ReadLinesCallI<T> call, ReadLinesBatchCallI<T> batchI) throws IOException {
		return readLinesBatchI(file, null, call, batchI);
	}

	/**
	 * 把文件中每一行设置到批量集合中(never return null)
	 * 
	 * @param file
	 * @param encoding
	 * @param call
	 * @param batchI
	 * @return
	 * @throws IOException
	 */
	public static final <T extends Collection<String>> List<T> readLinesBatchI(File file, String encoding, ReadLinesCallI<T> call, ReadLinesBatchCallI<T> batchI) throws IOException {
		List<T> lists = new ArrayList<T>();
		// 获取总数
		long size = readLinesCountI(file, call);
		if (size > 0) {
			long start = System.currentTimeMillis();
			long batchSize = batchI.getBatchSize();
			if (batchSize > 1) {
				long preNum = size / batchSize;
				long modNum = size % batchSize;
				// 100/10=10
				for (long i = 1; i <= preNum; i++) {
					call.setStartLineNum((i - 1) * batchSize + 1);
					call.setEndLineNum(i * batchSize);
					setBatchCollI(file, encoding, call, batchI, lists);
				}
				if (modNum == 0) {
				} else {
					// 11/3=3...2
					// 设置剩余的量
					call.setStartLineNum(preNum * batchSize + 1);
					call.setEndLineNum(size);
					setBatchCollI(file, encoding, call, batchI, lists);
				}
			} else {
				setBatchCollI(file, encoding, call, batchI, lists);
			}
			long end = System.currentTimeMillis();
			long x = (end - start) / 1000;
			long y = (end - start) % 1000;
			logger.debug("全部读取完毕...,总执行时间:" + (end - start) + "毫秒," + x + "." + (y < 100 ? "0" + y : y) + "秒");
		}
		return lists;
	}

	/**
	 * 批量设置集合
	 * 
	 * @param file
	 * @param encoding
	 * @param call
	 * @param batchI
	 * @param lists
	 * @throws IOException
	 */
	private static <T extends Collection<String>> void setBatchCollI(File file, String encoding, ReadLinesCallI<T> call, ReadLinesBatchCallI<T> batchI, List<T> lists) throws IOException {
		logger.debug("正在读取..." + (call.getEndLineNum() <= 0 ? "全部数据" : "[" + call.getStartLineNum() + "-" + call.getEndLineNum() + "]行数据"));
		long start = System.currentTimeMillis();
		T t = readLinesI(file, encoding, call);
		if (batchI.isCallBatchColl()) {
			batchI.callBatchColl(t);
		} else {
			lists.add(t);
		}
		long end = System.currentTimeMillis();
		long x = (end - start) / 1000;
		long y = (end - start) % 1000;
		logger.debug("读取完毕..." + (call.getEndLineNum() <= 0 ? "全部数据" : "[" + call.getStartLineNum() + "-" + call.getEndLineNum() + "]行数据") + ",执行时间:" + (end - start) + "毫秒," + x + "." + (y < 100 ? "0" + y : y) + "秒");
	}

	/**
	 * 获取对应的集合类型
	 * 
	 * @param t
	 * @return
	 * @throws IOException
	 */
	private static <T extends Collection<String>> Collection<String> getColl(T t) throws IOException {
		Collection<String> coll = null;
		if (t instanceof List) {
			coll = (List<String>) t;
		} else if (t instanceof Set) {
			coll = (Set<String>) t;
		} else {
			throw new IOException("不支持的返回类型" + t);
		}
		return coll;
	}

	/**
	 * 查找冲突jar包
	 * 
	 * @param path
	 *            所要查找的JAR包的目录
	 * @param className
	 *            要查询的class,要带包名的类名
	 * @return
	 */
	public static final List<String> findClassConflictJar(String path, String className) {
		List<String> results = new ArrayList<String>();
		className = className.replace('.', '/') + ".class";
		findClassConflictJar(path, className, results);
		return results;
	}

	/**
	 * 查找冲突jar包
	 * 
	 * @param path
	 *            所要查找的JAR包的目录
	 * @param className
	 *            要查询的class,要带包名的类名
	 * @param results
	 *            冲突的jar文件路径集合
	 */
	private static final void findClassConflictJar(String path, String className, List<String> results) {
		path = changePathSeparator(path, ConstantForEnum.ChangePathLastSeparator.ADD_AFTER);
		File file = new File(path);
		if (!file.exists()) {
			logger.warn("文件[" + file.getAbsolutePath() + "]不存在");
			return;
		}
		if (file.isFile()) {
			logger.warn("文件[" + file.getAbsolutePath() + "]不是目录,强制返回结果");
			return;
		}
		String[] filelist = file.list();
		if (filelist == null) {
			logger.warn("文件[" + file.getAbsolutePath() + "]中没有任何文件,强制返回结果");
			return;
		}
		for (int i = 0; i < filelist.length; i++) {
			String filePath = filelist[i];
			File temp = new File(path + filePath);
			// if ((temp.isDirectory() && !temp.isHidden() && temp.exists())) {
			if (temp.isDirectory()) {
				findClassConflictJar(path + filePath, className, results);
			} else {
				if (filePath.toLowerCase().endsWith("jar")) {
					try {
						java.util.jar.JarFile jarfile = new java.util.jar.JarFile(path + filePath);
						for (Enumeration<JarEntry> e = jarfile.entries(); e.hasMoreElements();) {
							String name = e.nextElement().toString();
							if (name.equals(className) || name.indexOf(className) > -1) {
								// System.out.println(path + filePath);
								results.add(path + filePath);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// /**
	// * 查找冲突jar包
	// *
	// * @param objPath
	// * jar文件或目录对象
	// * @param jarClass
	// * 每个jar路径对应的class集合
	// * @param classResult
	// * 所有class(包路径)对应的jar文件路径
	// * @param conflictClass
	// * 冲突class(包路径)对应的jar文件路径
	// */
	// @SuppressWarnings("unchecked")
	// public static final void findJarConflictClass(Object objPath, Map<String, Set<JarClass>> jarClassResult, Map<String, Set<String>> classResult, Map<String, Set<String>> conflictResult) {
	@SuppressWarnings("unchecked")
	public static final void findJarConflictClass(JarParams params) {
		Collection<String> values = null;
		Object objPath = params.getObjPath();
		if (CheckUtil.isNull(objPath)) {
			// 如果没有设置objPath,则默认path为objPath值
			objPath = params.getPath();
		}
		if (objPath instanceof String) {
			// 此处不会被执行
			values = new HashSet<String>();
			values.add(String.valueOf(objPath));
		} else if (objPath instanceof Collection) {
			values = ((Collection<String>) objPath);
		} else if (objPath instanceof String[]) {
			values = Arrays.asList((String[]) objPath);
		}
		if (values == null || values.size() == 0) {
			logger.warn("jar文件或目录对象数据格式不正确");
			return;
		}
		// 循环写
		Iterator<String> it = values.iterator();
		while (it.hasNext()) {
			// doFindJarConflictClass(it.next(), jarClassResult, classResult, conflictResult);
			// 设置path值
			params.setPath(it.next());
			doFindJarConflictClass(params);
		}
	}

	// /**
	// * 查找冲突jar包
	// *
	// * @param path
	// * jar文件或目录路径
	// * @param jarClass
	// * 每个jar路径对应的class集合
	// * @param classResult
	// * 所有class(包路径)对应的jar文件路径
	// * @param conflictClass
	// * 冲突class(包路径)对应的jar文件路径
	// */
	// public static final void findJarConflictClass(String path, Map<String, Set<JarClass>> jarClassResult, Map<String, Set<String>> classResult, Map<String, Set<String>> conflictResult) {
	private static final void doFindJarConflictClass(final JarParams params) {
		final String path = changePathSeparator(params.getPath(), ConstantForEnum.ChangePathLastSeparator.ADD_AFTER);
		final Map<String, Set<JarClass>> jarClassResult = params.getJarClassResult();
		final Map<String, Set<String>> classResult = params.getClassResult();
		final Map<String, Set<String>> conflictResult = params.getConflictResult();
		File file = new File(path);
		if (!file.exists()) {
			logger.warn("文件[" + file.getAbsolutePath() + "]不存在");
			return;
		}
		if (file.isFile()) {
			logger.warn("文件[" + file.getAbsolutePath() + "]不是目录,强制返回结果");
			return;
		}
		final String[] filelist = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// System.out.println(dir + "," + name + "," + FilenameUtils.getExtension(name));
				String extension = FilenameUtils.getExtension(name);
				if (CheckUtil.isNotNull(extension)) {
					if ("jar".equals(extension.toLowerCase())) {
						return true;
					}
				}
				return false;
			}
		});
		if (filelist == null || filelist.length == 0) {
			logger.warn("文件[" + file.getAbsolutePath() + "]中没有任何文件,强制返回结果");
			return;
		}
		for (int i = 0; i < filelist.length; i++) {
			final String filePath = filelist[i];
			doJarClass(params, path, jarClassResult, classResult, conflictResult, filePath);
		}
	}

	/**
	 * 处理jar中的class
	 * 
	 * @param params
	 * @param path
	 * @param jarClassResult
	 * @param classResult
	 * @param conflictResult
	 * @param filePath
	 */
	private static final void doJarClass(final JarParams params, final String path, final Map<String, Set<JarClass>> jarClassResult, final Map<String, Set<String>> classResult, final Map<String, Set<String>> conflictResult, final String filePath) {
		File temp = new File(path + filePath);
		// if ((temp.isDirectory() && !temp.isHidden() && temp.exists())) {
		if (temp.isDirectory()) {
			// findJarConflictClass(path + filePath, jarClassResult, conflictResult, classResult);
			doFindJarConflictClass(params);
		} else {
			try {
				String jarPath = changePathSeparator(path + filePath);
				// System.out.println(jarPath);
				Set<JarClass> jarClasss = null;
				// 可以为null
				if (jarClassResult != null) {
					jarClasss = jarClassResult.get(jarPath);
					if (jarClasss == null) {
						// 不存在
						jarClasss = new HashSet<JarClass>();
						jarClassResult.put(jarPath, jarClasss);
					}
				}
				java.util.jar.JarFile jarfile = new java.util.jar.JarFile(path + filePath);
				for (Enumeration<JarEntry> e = jarfile.entries(); e.hasMoreElements();) {
					// 获取jar文件
					String classPath = e.nextElement().toString();
					String extension = FilenameUtils.getExtension(classPath);
					String fullPath = jarPath + "!" + classPath;
					// 是否是class文件
					if ("class".equals(extension.toLowerCase())) {
						// 转换为class包
						int index = classPath.lastIndexOf(".");
						String prefixClassName = classPath;
						if (index > -1) {
							prefixClassName = classPath.substring(0, index);
						}
						String className = prefixClassName.replaceAll("/", ".");
						// 添加class
						Set<String> classJars = classResult.get(className);
						if (classJars == null) {
							classJars = new HashSet<String>();
							classResult.put(className, classJars);
						} else {
							// 冲突了
							Set<String> classConflictJars = conflictResult.get(className);
							if (classConflictJars == null) {
								classConflictJars = new HashSet<String>();
								conflictResult.put(className, classConflictJars);
							}
							// 添加jar文件(会自动去重)
							classConflictJars.add(jarPath);
							// 添加冲突的class对应的所有jar文件路径
							classConflictJars.addAll(classJars);
						}
						classJars.add(jarPath);
						if (jarClasss != null) {
							JarClass jarClass = new JarClass();
							// 设置jar属性
							jarClass.setFileDir(path);
							jarClass.setClassPath(classPath);
							jarClass.setClassName(className);
							jarClass.setFullPath(fullPath);
							jarClasss.add(jarClass);
						}
					} else {
						logger.debug("文件[" + fullPath + "]不是class文件,继续查找");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            文件内容
	 * @throws IOException
	 */
	public static final void write(String filePath, String content) throws IOException {
		write(new File(filePath), content, "UTF-8");
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            文件内容
	 * @param encoding
	 *            编码
	 * @throws IOException
	 */
	public static final void write(String filePath, String content, String encoding) throws IOException {
		write(new File(filePath), content, encoding);
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 *            文件对象
	 * @param content
	 *            文件内容
	 * @throws IOException
	 */
	public static final void write(File file, String content) throws IOException {
		write(file, content, null);
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 *            文件对象
	 * @param content
	 *            文件内容
	 * @param encoding
	 *            编码
	 * @throws IOException
	 */
	public static final void write(File file, String content, String encoding) throws IOException {
		createFolderOrFile(file, false);
		if (file == null)
			return;
		if (CheckUtil.isNull(content)) {
			content = "";
		}
		if (CheckUtil.isNull(encoding)) {
			encoding = "UTF-8";
		}
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		OutputStreamWriter writer = new OutputStreamWriter(bos, encoding);
		writer.write(content);
		writer.flush();
		writer.close();
	}

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * 
	 * @author 张军
	 * @date 2015-11-03 21:59:00
	 * @modifiyNote
	 * @version 1.0
	 * @return 页面地址
	 */
	public static final String GetImageStr(String imgFilePath) {
		byte[] data = null;
		BufferedInputStream bis = null;
		// 读取图片字节数组
		try {
			bis = new BufferedInputStream(new FileInputStream(imgFilePath));
			data = new byte[bis.available()];
			bis.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回Base64编码过的字节数组字符串
		return encoder.encode(data);
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @author 张军
	 * @date 2015-11-03 21:59:00
	 * @modifiyNote
	 * @version 1.0
	 * @return 页面地址
	 */
	public static final boolean GenerateImage(String imgStr, String imgFilePath) {
		// 图像数据为空
		if (imgStr == null)
			return false;
		if (imgStr.indexOf("data:image/jpeg;base64,") != -1) {
			imgStr = imgStr.substring("data:image/jpeg;base64,".length());
		}
		BASE64Decoder decoder = new BASE64Decoder();
		BufferedOutputStream bos = null;
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			bos = new BufferedOutputStream(new FileOutputStream(imgFilePath));
			bos.write(bytes);
			bos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}