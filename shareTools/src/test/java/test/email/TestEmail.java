package test.email;

import org.junit.Test;

import zj.email.bean.EmailDto;
import zj.email.util.EmailUtil;
import zj.message.util.MessageConstantsUtil;

public class TestEmail {
	@Test
	public void sendEmail() throws Exception {
		EmailDto dto = new EmailDto();
		dto.setFromAddress("z360901061@126.com");
		//从配置文件中获取常量值
		//设置密码
		dto.setPassword(MessageConstantsUtil.getConstantValue("password"));
		//设置收件人邮箱，多个以;号隔开
		dto.setToAddresses("360901061@qq.com;z360901061@126.com");
		//设置抄送人邮箱，多个以;号隔开
		dto.setBccAddresses("z360901061@126.com;zhangjun@msthamc.com");
		//设置密送人邮箱，多个以;号隔开
		dto.setCcAddresses("360901061@qq.com");
		//设置邮件主题
		dto.setSubject("测试主题");
		//设置普通格式邮件内容
//		dto.setContentObj("测试内容");
		//设置附件地址，多个以;号隔开
		dto.setFileDataSources("E:/document/xls/附件1.rar;E:/document/xls/附件2.rar");
		//设置HTML格式邮件内容
		dto.setContentObj("<table border='1' bgColor='red' style='width:500px'><tr><td><a href='http://user.qzone.qq.com/360901061'>发送一个表格张军</a></td></tr></table>");
		//发送邮箱
		EmailUtil.sendEmail(dto);
		System.out.println("发送邮件成功");
	}
}
