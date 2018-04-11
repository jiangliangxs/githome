package test.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

import zj.encrypt.util.Md5Util;

public class TestPostClient {
	@Test
	public void post001() throws Exception {
		String url = "http://127.0.0.1:8080/eservice/mobile/";
		String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Package><SendTime>2012-01-02 10:11:12</SendTime><Serial>1234567890</Serial><Asyn>1</Asyn><ReturnUrl>http://www.back.com</ReturnUrl><PageReturnUrl>http://www.baidu.com</PageReturnUrl><Request><Register><OpenId>123456</OpenId><Name>123456</Name><Password>123456</Password></Register></Request></Package>";
		Md5Util.kernalMD5("a", "UTF-8");
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		try {
			postMethod.setRequestEntity(new StringRequestEntity(data, "application/xml", "UTF-8"));
			int resultCode = httpClient.executeMethod(postMethod);
			if (resultCode == 200) {
				String resultString = postMethod.getResponseBodyAsString();
				System.out.println("resultString:" + resultString);
			}
		} finally {
			try {
				postMethod.releaseConnection();
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
