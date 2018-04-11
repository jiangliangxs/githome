package test.cert;

import zj.cert.util.InstallCertUtil;

/**
 * 概况 ：jdk的https证书生成<br>
 * 生成的jssecacerts文件放入到当前jdk下的jre\lib\security这个目录下
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>} <br>
 */
public class InstallCertMain {
	public static void main(String[] args) {
		String host = "cps.gnete.com";
		int port = 8093;
		try {
			InstallCertUtil.createCert(host, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
