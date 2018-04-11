package zj.io.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import zj.java.util.JavaUtil;

/**
 * 网络文件或字符串获取类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class NetWorkUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger logger = Logger.getLogger(NetWorkUtil.class);

	/**
	 * 保存网络文件
	 * 
	 * @param urlAddr
	 * @param localAddr
	 * @return
	 * @throws Exception
	 */
	public static void writeFileToLocalByURL(String urlAddr, String localAddr) throws Exception {
		BufferedOutputStream bos = null;
		BufferedInputStream fis = null;
		try {
			// 以流的形式下载文件。
			URL urlObj = new URL(urlAddr);
			URLConnection uc = urlObj.openConnection();
			fis = new BufferedInputStream(uc.getInputStream());
			// 取得目标文件路径
			if (localAddr == null || localAddr.equals("")) {
				String[] paths = FileUtil.getFileNameExtension(urlAddr);
				if (paths.length > 2) {
					localAddr = paths[1] + paths[2];
				} else {
					File file = new File(urlAddr);
					localAddr = file.getName();
				}

			}
			// byte[] buffer = new byte[fis.available()];
			// fis.read(buffer);
			File fileDesc = new File(localAddr);

			String[] extension = FileUtil.getFileNameExtension(localAddr);
			File extensionFile = new File(extension[0]);
			if (!extensionFile.exists()) {
				extensionFile.mkdirs();
			}
			bos = new BufferedOutputStream(new FileOutputStream(fileDesc));
			int blen = 1024 * 5;
			byte[] b = new byte[blen];
			int len = 0;
			while ((len = fis.read(b, 0, blen)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			logger.debug("成功:保存网络文件地址:" + localAddr);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// // 根据String形式创建一个URL对象，
		// URL url = new URL(urlAddr);
		// // 实列一个URLconnection对象，用来读取和写入此 URL 引用的资源
		// URLConnection con = url.openConnection();
		// // 获取一个输入流
		// InputStream is = con.getInputStream();
		// BufferedInputStream bis = new BufferedInputStream(is);
		// // 实列一个输出对象
		// FileOutputStream fos = new FileOutputStream(fileAddr);
		// BufferedOutputStream bos = new BufferedOutputStream(fos);
		// // 用来接收每次读取的字节个数
		// int length = -1;
		// // 一个byte[]数组，一次读取多个字节
		// byte[] buffer = new byte[7092];
		// // 循环判断，如果读取的个数b为空了，则is.read()方法返回-1，具体请参考InputStream的read();
		// while ((length = bis.read(buffer, 0, 7092)) != -1) {
		// // 将对象写入到对应的文件中
		// bos.write(buffer, 0, length);
		// }
		// // 刷新流
		// bos.flush();
		// fos.flush();
		// // 关闭流
		// bos.close();
		// fos.close();
		// bis.close();
		// is.close();
	}

	/**
	 * 获取网络字符串 编码默认GB2312
	 * 
	 * @param url
	 * @return
	 */
	public static String getStringByURL(String url) {
		return getStringByURL(url, "GB2312");
	}

	/**
	 * 获取网络字符串 编码默认GB2312
	 * 
	 * @author zhangjun
	 * @param url
	 *            网址
	 * @param encoding
	 *            网页的编码集
	 * @return
	 */
	public static String getStringByURL(String url, String encoding) {
		StringBuffer sb = new StringBuffer();
		// 建立文件写入流
		// 建立缓冲写入流
		BufferedReader br = null;
		try {
			// 建立网络连接
			URL urlObj = new URL(url);
			// 打开网络连接
			URLConnection uc = urlObj.openConnection();
			// uc.setRequestProperty("User-Agent", "java");
			// 建立文件写入流
			// 建立缓冲写入流
			br = new BufferedReader(new InputStreamReader(uc.getInputStream(), encoding));
			String sLine = null;
			sLine = br.readLine();
			sLine = JavaUtil.readFirstLine(sLine);
			if (sLine != null) {
				sb.append(sLine);
				while ((sLine = br.readLine()) != null) {
					// 一边读,一边写
					//sb.append(sLine + System.getProperty("line.separator"));
					sb.append(sLine);
				}
			}
		} catch (MalformedURLException e) {
			System.err.println("连接网络失败");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("打开网络连接失败");
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
