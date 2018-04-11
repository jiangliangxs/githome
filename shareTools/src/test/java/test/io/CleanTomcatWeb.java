package test.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import zj.check.util.CheckUtil;
import zj.io.service.FileFilterAbsImpl;
import zj.io.util.ConstantForEnum;
import zj.io.util.FileUtil;
import zj.java.util.JavaUtil;

public class CleanTomcatWeb {
	private static Logger logger = Logger.getLogger(CleanTomcatWeb.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			logger.info("请传入删除的tomcat所在目录");
			return;
		}
		logger.info("传入数组参数值:" + Arrays.toString(args));
		// System.out.println(FilenameUtils.getBaseName(FileUtil.changePathSeparator("D:\\a\\", ConstantForEnum.ChangePathLastSeparator.DEL)));
		final Set<String> lastDirNames = new HashSet<String>();
		List<File> fileList = new ArrayList<File>();
		lastDirNames.add("temp");
		lastDirNames.add("logs");
		lastDirNames.add("work");
		// args = new String[] {};
		for (String dir : args) {
			// dir = "E:/document/zj-utils/file";
			dir = JavaUtil.trim(dir);// "E:/bigfile/services/java/tomcat6.0.37-8";
			if (CheckUtil.isNull(dir)) {
				logger.info("tomcat所在目录为空");
				continue;
			} else {
				File file = new File(dir);
				if (!file.exists()) {
					logger.info("tomcat[" + dir + "]所在目录不存在");
					continue;
				}
				if (file.isFile()) {
					logger.info("tomcat[" + dir + "]不是所在目录");
					continue;
				}
				logger.info("正在清除tomcat所在目录:" + dir);
				FileUtil.setFilterFiles(fileList, dir, new FileFilterAbsImpl() {
					@Override
					public boolean accept(File file) {
						// logger.info(this.getLevel() + "," + file.getAbsolutePath());
						if (file.isFile()) {
							if (file.getAbsolutePath().indexOf("webapps") > -1 && file.getAbsolutePath().indexOf("WEB-INF\\lib") > -1) {
								// String extension = FilenameUtils.getBaseName(file.getAbsolutePath());
								// if (CheckUtil.isNotNull(extension)) {
								// if ("jar".equals(extension)) {
								// return true;
								// }
								// }
								if ("jsp-api.jar".equals(file.getName()) || "servlet-api.jar".equals(file.getName())) {
									return true;
								}
							}
							return false;
						} else {
							return true;
						}
					}

					@Override
					public boolean acceptDir(File file) {
						String lastDirName = FilenameUtils.getBaseName(FileUtil.changePathSeparator(file.getAbsolutePath(), ConstantForEnum.ChangePathLastSeparator.DEL_AFTER));
						if (CheckUtil.isNotNull(lastDirName)) {
							lastDirName = lastDirName.toLowerCase();
							if (lastDirNames.contains(lastDirName)) {
								return true;
							}
						}
						return false;
					}
				});
			}
		}
		// 写入日志文件
		File rfile = writePendingFile(fileList);
		logger.info("可以修改生成的日志文件:[" + rfile.getAbsolutePath() + "]");
		Scanner can = new Scanner(System.in);
		String command = "";
		command = "输入命令:\nr-读取文件[" + rfile.getAbsolutePath() + "]内容\nq-退出";
		while (true) {
			logger.info(command);
			String next = can.next();
			if ("r".equalsIgnoreCase(next)) {
				break;
			} else if ("q".equalsIgnoreCase(next)) {
				return;
			} else {
				logger.info("输入的命令不正确,请重新输入");
			}
		}
		List<String> paths = new ArrayList<String>();
		try {
			FileUtil.readLines(paths, rfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("读取的文件内容如下");
		for (String path : paths) {
			System.out.println(path);
		}
		command = "输入命令:\nyall-全删除\nnall-全不删除\ny-删除\nn或回车-不删除\nq-退出";
		boolean input = true;
		logger.info("开始输入命令执行以下程序,删除文件总数:" + paths.size());
		q: for (String path : paths) {
			File file = new File(path);
			if (!file.exists()) {
				logger.info("文件[" + file.getAbsolutePath() + "]不存在");
				continue;
			}
			// 默认不删除
			boolean delete = true;
			if (input) {
				logger.info("即将执行删除文件或文件夹:[" + file.getAbsolutePath() + "]");
				while (true) {
					logger.info(command);
					// String nextline = can.nextLine();
					// nextline = JavaUtil.trim(nextline);
					// System.out.println("->"+nextline+","+"\r\n".equalsIgnoreCase(nextline)+","+"\n".equalsIgnoreCase(nextline));
					String next = can.next();
					logger.info("您输入的命令是:" + next);
					boolean q = "q".equalsIgnoreCase(next);
					if (q) {
						break q;
					}
					boolean yall = "yall".equalsIgnoreCase(next);
					boolean nall = "nall".equalsIgnoreCase(next);
					boolean y = "y".equalsIgnoreCase(next);
					boolean n = "n".equalsIgnoreCase(next);
					if (yall) {
						input = false;
					} else if (nall) {
						delete = false;
						break q;
					} else if (y) {
					} else if (n) {
						delete = false;
					} else {
						logger.info("输入的命令不正确,请重新输入");
						continue;
					}
					break;
				}
			}
			if (delete) {
				if (file.isDirectory()) {
					for (File df : file.listFiles()) {
						// if (df.isDirectory()) {
						// try {
						// FileUtils.deleteDirectory(df);
						// } catch (IOException e) {
						// e.printStackTrace();
						// }
						// } else {
						// FileUtils.deleteQuietly(df);
						// }
						FileUtils.deleteQuietly(df);
					}
				} else {
					file.delete();
				}
				logger.info("删除文件或文件夹:[" + file.getAbsolutePath() + "]成功");
			}
		}
		// logger.info("即将删除[" + fileList.size() + "]个文件");
		// for (File file : fileList) {
		// file.delete();
		// logger.info("删除:" + file.getAbsolutePath() + "文件成功");
		// }
		logger.info("操作成功");

		// List<File> fileList = new ArrayList<File>();
		// FileUtil.setFilterFiles(fileList, dir, new FileFilterAbsImpl() {
		// @Override
		// public boolean accept(File file) {
		// // logger.info(this.getLevel() + "," + file.getAbsolutePath());
		// if (file.isFile()) {
		// if (file.getAbsolutePath().indexOf("webapps") > -1 && file.getAbsolutePath().indexOf("WEB-INF\\lib") > -1) {
		// // String extension = FilenameUtils.getBaseName(file.getAbsolutePath());
		// // if (CheckUtil.isNotNull(extension)) {
		// // if ("jar".equals(extension)) {
		// // return true;
		// // }
		// // }
		// if ("jsp-api.jar".equals(file.getName()) || "servlet-api.jar".equals(file.getName())) {
		// return true;
		// }
		// }
		// return false;
		// } else {
		// return true;
		// }
		// }
		//
		// @Override
		// public boolean acceptDir(File file) {
		// return false;
		// }
		// });
		// logger.info("即将删除[" + fileList.size() + "]个文件");
		// for (File file : fileList) {
		// file.delete();
		// logger.info("删除:" + file.getAbsolutePath() + "文件成功");
		// }
		// logger.info("操作成功");
	}

	/**
	 * 写类日志文件
	 * 
	 * @param fileDir
	 */
	private static File writePendingFile(List<File> files) {
		File rfile = new File(FileUtil.changePathSeparator(System.getProperty("user.dir"), ConstantForEnum.ChangePathLastSeparator.ADD_AFTER) + "pendingFiles.txt");
		BufferedOutputStream output = null;
		try {
			output = new BufferedOutputStream(new FileOutputStream(rfile));
			for (File file : files) {
				IOUtils.write(file.getAbsolutePath(), output);
				IOUtils.write(FileUtil.LINE_SEPARATOR, output);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				IOUtils.closeQuietly(output);
			}
		}
		return rfile;
	}
}
