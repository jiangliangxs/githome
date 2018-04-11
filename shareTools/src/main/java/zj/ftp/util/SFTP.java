package zj.ftp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTP {

	/**
	 * 初使化SFTP自动连接
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 */
	public SFTP(String host, int port, String userName, String password) throws Exception {
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 连接sftp服务器
	 * 
	 * @return
	 */
	public void connect() throws Exception {
		if (this.sftp == null || !this.sftp.isClosed() || !this.sftp.isConnected()) {
			JSch jsch = new JSch();
			jsch.getSession(this.userName, host, port);
			Session sshSession = jsch.getSession(this.userName, host, port);
			logger.info("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			if (this.timeOut != 0) {
				sshSession.setTimeout(timeOut);
			}
			sshSession.connect();
			logger.info("Session connected.");
			logger.info("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			this.sftp = (ChannelSftp) channel;
		} else {
			logger.info("sftp isConnected");
		}
		logger.info("Connected to " + host + ".");
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 */
	public void upload(String directory, String uploadFile) throws Exception {
		FileInputStream in = null;
		BufferedInputStream bis = null;
		try {
			this.sftp.cd(directory);
			File file = new File(uploadFile);
			in = new FileInputStream(file);
			bis = new BufferedInputStream(in);
			this.sftp.put(bis, file.getName());
			logger.info("上传文件路径[" + directory + "/" + file.getName() + "]");
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 */
	public void download(String directory, String downloadFile, String saveFile) throws Exception {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			this.sftp.cd(directory);
			File file = new File(saveFile);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			this.sftp.get(downloadFile, bos);
			logger.info("下载文件路径[" + directory + "/" + file.getName() + "]");
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 */
	public void delete(String directory, String deleteFile) {
		try {
			this.sftp.cd(directory);
			this.sftp.rm(deleteFile);
		} catch (Exception e) {
			logger.error("删除ftp文件异常", e);
		}
	}

	/**
	 * 
	 * 创建文件夹
	 * 
	 * @param
	 * @return
	 */
	public void makeDirs(String directory) throws SftpException {
		String[] dirs = directory.split("/");
		if (dirs.length > 1) {
			int i = 0;
			String temp = "/" + dirs[i];
			boolean end = false;
			while (!end) {
				try {
					this.sftp.ls(temp);
				} catch (Exception e) {
					this.sftp.mkdir(temp);
				}
				i++;
				if (i >= dirs.length) {
					end = true;
				} else {
					temp += "/" + dirs[i];
				}
			}
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @return
	 * @throws SftpException
	 */
	@SuppressWarnings("unchecked")
	public Vector<LsEntry> listFiles(String directory) throws SftpException {
		return this.sftp.ls(directory);
	}

	/**
	 * 关闭连接
	 * 
	 * @param sftp
	 */
	public void disconnect() {
		try {
			if (this.sftp != null) {
				this.sftp.getSession().disconnect();
				this.sftp.disconnect();
				this.sftp.exit();
				this.sftp = null;
			}
		} catch (JSchException e) {
			logger.error("断开ftp异常", e);
		}
	}

	private transient static final Logger logger = Logger.getLogger(SFTP.class);
	private String host;
	private int port;
	private String userName;
	private String password;
	private ChannelSftp sftp;
	private int timeOut;

	public int getTimeOut() {
		return timeOut;
	}

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

	public ChannelSftp getSftp() {
		return sftp;
	}

	@Override
	public String toString() {
		return "SFTP [host=" + host + ", port=" + port + ", userName=" + userName + ", password=" + password + ", sftp=" + sftp + "]";
	}

}
