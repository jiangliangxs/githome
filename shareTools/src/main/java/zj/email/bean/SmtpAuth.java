package zj.email.bean;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 自定义一个SMTP授权验证类
 * 
 * @author zhangjun
 * 
 */
public class SmtpAuth extends Authenticator {
	/**
	 * 发送邮件用户名
	 */
	private String user;
	/**
	 * 发送邮件密码
	 */
	private String password;

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
