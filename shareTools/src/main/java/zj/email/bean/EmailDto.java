package zj.email.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import zj.check.util.CheckUtil;
import zj.java.util.JavaUtil;

/**
 * 
 * @author zhangjun
 * 
 */
public class EmailDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 发件人使用发邮件的电子信箱服务器,默认smtp.126.com
	 */
	private String mailSmtpHost;
	/**
	 * 是否调试
	 */
	private boolean debug;
	/**
	 * 发送人邮箱地址
	 */
	private String fromAddress;
	/**
	 * 收件人邮箱地址(多个以;号隔开)
	 */
	private String toAddresses;
	/**
	 * 密送人邮箱地址(多个以;号隔开)
	 */
	private String bccAddresses;
	/**
	 * 抄送人邮箱地址(多个以;号隔开)
	 */
	private String ccAddresses;
	/**
	 * 设置主题
	 */
	private String subject;
	/**
	 * 发送主体内容
	 */
	private Object contentObj;
	/**
	 * 编码,默认text/html;charset=UTF-8
	 */
	private String contentType = "text/html;charset=UTF-8";
	/**
	 * 附件地址
	 */
	private String fileDataSources;
	/**
	 * 附件地址名
	 */
	private String fileDataSourcesName;
	/**
	 * 协议,默认smtp
	 */
	private String protocol = "smtp";
	/**
	 * 发送邮件用户名
	 */
	private String user;
	/**
	 * 发送邮件密码
	 */
	private String password;

	private Map<String, Object> propertiesMap = new HashMap<String, Object>();

	/**
	 * 自动设置用户名
	 */
	public void autoSetUser() {
		if (CheckUtil.isNull(user)) {
			// 设置用户名
			String[] userAry = JavaUtil.split(this.fromAddress, "@");
			if (userAry.length > 0) {
				user = userAry[0];
			}
		}
	}

	/**
	 * 自动设置服务器
	 */
	public void autoSetMailSmtpHost() {
		if (CheckUtil.isNull(mailSmtpHost)) {
			String[] userAry = JavaUtil.split(this.fromAddress, "@");
			if (userAry.length > 1) {
				String hosts[] = JavaUtil.split(userAry[1], ".");
				if (hosts.length > 0) {
					String host = hosts[0];
					if ("qq".equalsIgnoreCase(host) || "allianity".equalsIgnoreCase(host)) {
						mailSmtpHost = "smtp.qq.com";
					} else if ("yahoo".equalsIgnoreCase(host)) {
						mailSmtpHost = "smtp.mail.yahoo.com.cn";
					} else if ("msthamc".equalsIgnoreCase(host)) {
						mailSmtpHost = "mail.msthamc.com";
						propertiesMap.put("mail.smtp.port", "465");
						propertiesMap.put("mail.smtp.starttls.enable", "true");
						propertiesMap.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
						propertiesMap.put("mail.smtp.socketFactory.fallback", "false");
					} else if ("163".equalsIgnoreCase(host)) {
						mailSmtpHost = "smtp.163.com";
					} else if ("126".equalsIgnoreCase(host)) {
						mailSmtpHost = "smtp.126.com";
					} else {
						mailSmtpHost = "smtp.126.com";
					}
				}
			}
		}
	}

	/**
	 * 发送邮件用户名
	 * 
	 * @return
	 */
	public String getUser() {
		autoSetUser();
		return user;
	}

	/**
	 * 发送邮件用户名
	 * 
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 发送邮件密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 发送邮件密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 协议,默认smtp
	 * 
	 * @return
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * 协议,默认smtp
	 * 
	 * @param protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * 附件地址
	 * 
	 * @return 附件地址
	 */
	public String getFileDataSources() {
		return fileDataSources;
	}

	/**
	 * 附件地址
	 * 
	 * @param fileDataSources
	 *            附件地址
	 */
	public void setFileDataSources(String fileDataSources) {
		this.fileDataSources = fileDataSources;
	}

	/**
	 * 附件地址名
	 * 
	 * @return 附件地址名
	 */
	public String getFileDataSourcesName() {
		return fileDataSourcesName;
	}

	/**
	 * 附件地址名
	 * 
	 * @param fileDataSourcesName
	 *            附件地址名
	 */
	public void setFileDataSourcesName(String fileDataSourcesName) {
		this.fileDataSourcesName = fileDataSourcesName;
	}

	/**
	 * 编码,默认text/html;charset=UTF-8
	 * 
	 * @return
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * 编码,默认text/html;charset=UTF-8
	 * 
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 发送主体内容
	 * 
	 * @return
	 */
	public Object getContentObj() {
		return contentObj;
	}

	/**
	 * 发送主体内容
	 * 
	 * @param obj
	 */
	public void setContentObj(Object contentObj) {
		this.contentObj = contentObj;
	}

	/**
	 * 设置主题
	 * 
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 设置主题
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 发件人使用发邮件的电子信箱服务器,默认smtp.126.com
	 * 
	 * @return
	 */
	public String getMailSmtpHost() {
		autoSetMailSmtpHost();
		return mailSmtpHost;
	}

	/**
	 * 发件人使用发邮件的电子信箱服务器,默认smtp.126.com
	 * 
	 * @param mailSmtpHost
	 */
	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	/**
	 * 是否调试
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * 是否调试
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * 发送人邮箱地址
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * 发送人邮箱地址
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * 收件人邮箱地址(多个以;号隔开)
	 * 
	 * @return
	 */
	public String getToAddresses() {
		return toAddresses;
	}

	/**
	 * 收件人邮箱地址(多个以;号隔开)
	 * 
	 * @param toAddresses
	 */
	public void setToAddresses(String toAddresses) {
		this.toAddresses = toAddresses;
	}

	/**
	 * 密送人邮箱地址(多个以;号隔开)
	 * 
	 * @return
	 */
	public String getBccAddresses() {
		return bccAddresses;
	}

	/**
	 * 密送人邮箱地址(多个以;号隔开)
	 * 
	 * @param bccAddresses
	 */
	public void setBccAddresses(String bccAddresses) {
		this.bccAddresses = bccAddresses;
	}

	/**
	 * 抄送人邮箱地址(多个以;号隔开)
	 * 
	 * @return
	 */
	public String getCcAddresses() {
		return ccAddresses;
	}

	/**
	 * 抄送人邮箱地址(多个以;号隔开)
	 * 
	 * @param ccAddresses
	 */
	public void setCcAddresses(String ccAddresses) {
		this.ccAddresses = ccAddresses;
	}

	/**
	 * 属性key-value
	 * 
	 * @return 属性key-value
	 */
	public Map<String, Object> getPropertiesMap() {
		return propertiesMap;
	}

	/**
	 * 属性key-value
	 * 
	 * @param propertiesMap
	 *            属性key-value
	 */
	public void setPropertiesMap(Map<String, Object> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}
}
