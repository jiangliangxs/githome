package zj.ftp.util;

import it.sauronsoftware.ftp4j.FTPClient;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

/**
 * FTP工具类
 * 
 * @author zhangjun
 * 
 */
public class FTP {
	/**
	 * FTP连接
	 * 
	 * @param host
	 *            主机地址
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @throws Exception
	 */
	public FTP(String host, String userName, String password) throws Exception {
		this.host = host;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * FTPS/FTPES连接
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @throws Exception
	 */
	public FTP(String host, int port, String userName, String password) throws Exception {
		this(host, userName, password);
		this.password = password;
	}

	/**
	 * 连接ftp服务器
	 * 
	 * @throws Exception
	 */
	public void connect() throws Exception {
		if (client == null || !client.isConnected()) {
			client = new FTPClient();
			if (factory == null) {
				createSSLSocketFactory();
			}
			client.setSSLSocketFactory(factory);
			if (this.security != 0) {
				client.setSecurity(security);
			}
			client.connect(host, port);
			client.login(userName, password);
			client.setType(FTPClient.TYPE_BINARY);
			if (client.isCompressionSupported()) {
				client.setCompressionEnabled(true);
			}
		} else {
			logger.info("client isConnected");
		}
		logger.info("Connected to " + host + ".");
	}

	/**
	 * 创建目录
	 * 
	 * @param directoryName
	 *            目录
	 * @throws Exception
	 */
	public void createDirectory(String directoryName) throws Exception {
		client.createDirectory(directoryName);
	}

	/**
	 * 删除目录
	 * 
	 * @param path
	 *            路径
	 * @throws Exception
	 */
	public void deleteDirectory(String path) throws Exception {
		client.deleteDirectory(path);
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 *            文件路径
	 * @throws Exception
	 */
	public void deleteFile(String file) throws Exception {
		client.deleteFile(file);
	}

	/**
	 * 重命名，可用于移动文件
	 * 
	 * @param oldname
	 *            源文件名
	 * @param newname
	 *            目标文件名
	 * @throws Exception
	 */
	public void rename(String oldname, String newname) throws Exception {
		client.rename(oldname, newname);
	}

	/**
	 * 下载文件
	 * 
	 * @param remoteFile
	 *            远程文件路径
	 * @param localFile
	 *            本地文件路径
	 * @throws Exception
	 */
	public void download(String remoteFile, String localFile) throws Exception {
		this.download(remoteFile, new File(localFile));
	}

	/**
	 * 下载文件
	 * 
	 * @param remoteFile
	 *            远程文件路径
	 * @param localFile
	 *            本地文件对象
	 * @throws Exception
	 */
	public void download(String remoteFile, File localFile) throws Exception {
		client.download(remoteFile, localFile);
	}

	/**
	 * 下载文件
	 * 
	 * @param remoteFile
	 * @param localFile
	 * @param restartAt
	 * @throws Exception
	 */
	public void download(String remoteFile, String localFile, long restartAt) throws Exception {
		client.download(remoteFile, new File(localFile), restartAt);
	}

	/**
	 * 下载文件
	 * 
	 * @param remoteFile
	 * @param localFile
	 * @param restartAt
	 * @throws Exception
	 */
	public void download(String remoteFile, File localFile, long restartAt) throws Exception {
		client.download(remoteFile, localFile, restartAt);
	}

	/**
	 * 
	 * 上传文件
	 * 
	 * @param localFile
	 * @return
	 */
	public void upload(String localFile) throws Exception {
		this.upload(new File(localFile));
	}

	/**
	 * 
	 * 上传文件
	 * 
	 * @param loacalFile
	 * @author zhangjun
	 * @return
	 */
	public void upload(File loacalFile) throws Exception {
		client.upload(loacalFile);
	}

	/**
	 * 追加文件内容到已存在的同名文件中
	 * 
	 * @param localFile
	 *            本地文件
	 * @throws Exception
	 */
	public void append(String localFile) throws Exception {
		this.append(new File(localFile));
	}

	/**
	 * 追加文件内容到已存在的同名文件中
	 * 
	 * @param localFile
	 *            本地文件
	 * @throws Exception
	 */
	public void append(File localFile) throws Exception {
		client.append(localFile);
	}

	/**
	 * 切换目录
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void changeDirectory(String path) throws Exception {
		client.changeDirectory(path);
	}

	/**
	 * 
	 * 切换目录到根目录
	 * 
	 * @return
	 */
	public void changeDirectoryUP() throws Exception {
		client.changeDirectoryUp();
	}

	/**
	 * 
	 * 设置FTP连接类型
	 * 
	 * @param status
	 * @return
	 */
	public void setSecurity(int status) {
		client.setSecurity(status);
	}

	/**
	 * 
	 * 返回当前所在目录路径
	 * 
	 */
	public String currentDirectory() throws Exception {
		return client.currentDirectory();
	}

	/**
	 * 
	 * 创建SSLSocketFactory
	 * 
	 * @return
	 */
	public void createSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] tm = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}
		} };
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, tm, new SecureRandom());
		this.factory = context.getSocketFactory();
	}

	/**
	 * 
	 * 断开连接
	 * 
	 * @param flag
	 * @return
	 */
	public void disconnect(boolean flag) throws Exception {
		if (client != null) {
			client.disconnect(flag);
			client = null;
		}
	}

	private transient static final Logger logger = Logger.getLogger(SFTP.class);
	private String host;
	private int port;
	private String userName;
	private String password;
	private FTPClient client;
	private SSLSocketFactory factory;
	private int security;

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public FTPClient getClient() {
		return client;
	}

	public static Logger getLogger() {
		return logger;
	}

	public SSLSocketFactory getFactory() {
		return factory;
	}

	public int getSecurity() {
		return security;
	}

}
