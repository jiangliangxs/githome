package zj.email.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import zj.check.util.CheckUtil;
import zj.email.bean.EmailDto;
import zj.java.util.JavaUtil;

/**
 * 概况 ：EmailUtil工具类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>} <br>
 */
public class EmailUtil {
	private static Logger logger = Logger.getLogger(EmailUtil.class);

	/**
	 * 发送邮件
	 * 
	 * @param dto
	 */
	public static void sendEmail(EmailDto dto) throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", dto.getMailSmtpHost());
		props.put("mail.smtp.auth", "true");
		// 使用 STARTTLS安全连接:
		// props.setProperty("mail.smtp.port", "465");
		// props.setProperty("mail.smtp.starttls.enable", "true");
		// props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// props.setProperty("mail.smtp.socketFactory.fallback", "false");
		if (dto.getPropertiesMap() != null) {
			props.putAll(dto.getPropertiesMap());
		}
		// 邮件会话类Session，跟httpSession类似。传入属性设置properties和授权验证对象
		Session session = null;
		// 无授权验证
		session = Session.getInstance(props);
		// 授权验证
		// SmtpAuth auth = new SmtpAuth();
		// auth.setUser(dto.getUser());
		// auth.setPassword(dto.getPassword());
		// session = Session.getInstance(props, auth);

		session.setDebug(dto.isDebug());
		// 由邮件会话新建一个消息对象
		MimeMessage message = new MimeMessage(session);
		InternetAddress from = new InternetAddress(dto.getFromAddress());
		// 设置发信人
		message.setFrom(from);
		// 设置收信人
		if (CheckUtil.isNotNull(dto.getToAddresses())) {
			String[] sary = JavaUtil.split(dto.getToAddresses(), ";");
			List<String> emails = new ArrayList<String>();
			for (int i = 0; i < sary.length; i++) {
				if (CheckUtil.isEmail(sary[i])) {
					emails.add(sary[i]);
				} else {
					logger.warn("无效的收件人邮件地址:" + sary[i]);
				}
			}
			if (emails.size() > 0) {
				Address[] addressAry = new InternetAddress[emails.size()];
				for (int i = 0; i < emails.size(); i++) {
					String email = emails.get(i);
					addressAry[i] = new InternetAddress(email);
				}
				message.setRecipients(Message.RecipientType.TO, addressAry);
			} else {
				logger.warn("验证所有收件人失败,收件人至少有一个人");
				return;
			}
		} else {
			logger.warn("收件人至少有一个人");
			return;
		}
		// 设置抄送人
		if (CheckUtil.isNotNull(dto.getCcAddresses())) {
			String[] sary = JavaUtil.split(dto.getCcAddresses(), ";");
			List<String> emails = new ArrayList<String>();
			for (int i = 0; i < sary.length; i++) {
				if (CheckUtil.isEmail(sary[i])) {
					emails.add(sary[i]);
				} else {
					logger.warn("无效的抄送人邮件地址:" + sary[i]);
				}
			}
			if (emails.size() > 0) {
				Address[] addressAry = new InternetAddress[emails.size()];
				for (int i = 0; i < emails.size(); i++) {
					String email = emails.get(i);
					addressAry[i] = new InternetAddress(email);
				}
				message.setRecipients(Message.RecipientType.CC, addressAry);
			}
		}
		// 设置密送人
		if (CheckUtil.isNotNull(dto.getBccAddresses())) {
			String[] sary = JavaUtil.split(dto.getBccAddresses(), ";");
			List<String> emails = new ArrayList<String>();
			for (int i = 0; i < sary.length; i++) {
				if (CheckUtil.isEmail(sary[i])) {
					emails.add(sary[i]);
				} else {
					logger.warn("无效的密送人邮件地址:" + sary[i]);
				}
			}
			if (emails.size() > 0) {
				Address[] addressAry = new InternetAddress[emails.size()];
				for (int i = 0; i < emails.size(); i++) {
					String email = emails.get(i);
					addressAry[i] = new InternetAddress(email);
				}
				message.setRecipients(Message.RecipientType.BCC, addressAry);
			}
		}
		// 设置主题
		message.setSubject(dto.getSubject());
		// 设置发信时间
		message.setSentDate(new Date());
		// 发送邮件
		Multipart mm = new MimeMultipart();
		if (dto.getContentObj() == null) {
			dto.setContentObj("");
		}
		BodyPart mdpContent = new MimeBodyPart();
		// 添加内容
		mdpContent.setContent(dto.getContentObj(), dto.getContentType());
		mm.addBodyPart(mdpContent);
		// 添加附件
		if (CheckUtil.isNotNull(dto.getFileDataSources())) {
			String[] sary = JavaUtil.split(dto.getFileDataSources(), ";");
			String[] saryName = JavaUtil.split(dto.getFileDataSourcesName(), ";");
			for (int i = 0; i < sary.length; i++) {
				FileDataSource fdsFile = new FileDataSource(sary[i]);
				DataHandler dh = new DataHandler(fdsFile);
				BodyPart mdpAttachment = new MimeBodyPart();
				String fileName = null;
				if (saryName.length > i) {
					fileName = saryName[i];
				}
				if (CheckUtil.isNull(fileName)) {
					fileName = fdsFile.getName();
				}
				mdpAttachment.setFileName(MimeUtility.encodeWord(fileName));
				mdpAttachment.setDataHandler(dh);
				mm.addBodyPart(mdpAttachment);
			}
		}
		message.setContent(mm);
		// 保存改变
		message.saveChanges();
		// ****************************************************//
		Transport transport = session.getTransport(dto.getProtocol());
		// 以smtp方式登录邮箱，第一个参数是发送邮件用的邮件服务器SMTP地址，第二个参数为用户名，第三个参数密码
		transport.connect(dto.getMailSmtpHost(), dto.getUser(), dto.getPassword());
		// 发送邮件，其中第二个参数是所有已设好的收件人地址
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		logger.info("发送邮件成功");
	}
}
