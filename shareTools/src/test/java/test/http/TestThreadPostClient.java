package test.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import zj.encrypt.util.Md5Util;

public class TestThreadPostClient {
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for (int i = 100; i < 200; i++) {
							String returnStr = TestThreadPostClient.callInterface((i + 100) + "->" + Thread.currentThread().getName());
							if (returnStr != null && !"".equals(returnStr)) {
							} else {
								System.out.println((i + 100) + "->" + Thread.currentThread().getName() + "中断．．．");
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	public static final String callInterface(String threadName) throws Exception {
		String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Package><Header><SendTime>2012-01-02 10:11:12</SendTime><ThirdSerial>1234567890</ThirdSerial><Asyn>1</Asyn><ReturnUrl>http://www.back.com</ReturnUrl><Version>1.0</Version></Header><Request><UserInfo><ThirdUserId>wx15656856396</ThirdUserId></UserInfo></Request></Package>";
		String url = "http://127.0.0.1:8080/eservice/mobile/third/receive/05/THIRD-MSTH-020/" + Md5Util.kernalMD5("b3bb957dd574b7573285bdfae8f52eee" + data, "UTF-8");
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		try {
			postMethod.setRequestEntity(new StringRequestEntity(data, "application/xml", "UTF-8"));
			int resultCode = httpClient.executeMethod(postMethod);
			String resultString = postMethod.getResponseBodyAsString();
			System.out.println(threadName + "->" + resultCode + "->" + resultString);
			return resultString;
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
