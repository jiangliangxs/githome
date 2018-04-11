package test.http;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

import zj.io.util.NetWorkUtil;

import com.alibaba.fastjson.JSON;

public class TestWebClient {
	private String appid = "wx541bf7be8683ae1b";
	private String secret = "08e112b3b8ecb310d5d7649922f2176f";

	@SuppressWarnings("unchecked")
	@Test
	public void getTwoDimensioCode() throws Exception {
		String url = "";
		String accessToken = "";
		accessToken = getAccessToken();
		url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
		String data = "";
		// data = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"test100000000000000001\"}}}";
		data = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
		// 以下生成永久性只能是1-100000
		// data = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": \"test100000000000000001\"}}}";
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		try {
			postMethod.setRequestEntity(new StringRequestEntity(data, "text/json", "UTF-8"));
			int resultCode = httpClient.executeMethod(postMethod);
			if (resultCode == 200) {
				String resultString = postMethod.getResponseBodyAsString();
				System.out.println("getTwoDimensioCode:" + resultString);
				Map<String, Object> m = JSON.parseObject(resultString, Map.class);
				System.out.println("getTwoDimensioCode对象:" + m);
				NetWorkUtil.writeFileToLocalByURL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + m.get("ticket"), "E:/document/network/" + System.currentTimeMillis() + "/zhangjun.jpg");
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

	@Test
	public void testAccessToken() throws Exception {
		System.out.println(getAccessToken());
	}

	@SuppressWarnings("unchecked")
	public String getAccessToken() throws Exception {
		String access_token = "";
		String url = "";
		url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		try {
			int resultCode = httpClient.executeMethod(method);
			if (resultCode == 200) {
				String resultString = method.getResponseBodyAsString();
				System.out.println("getAccessToken:" + resultString);
				Map<String, Object> m = JSON.parseObject(resultString, Map.class);
				System.out.println("getAccessToken对象:" + m);
				access_token = "" + m.get("access_token");
			}
		} finally {
			try {
				method.releaseConnection();
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return access_token;
	}

	@Test
	public void getStringByURL() throws Exception {
		String url = "";
		url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
		String content = NetWorkUtil.getStringByURL(url);
		System.out.println(content);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void getticket() throws Exception {
		String url = "";
		url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + getAccessToken() + "&type=wx_card";
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		try {
			int resultCode = httpClient.executeMethod(method);
			if (resultCode == 200) {
				String resultString = method.getResponseBodyAsString();
				System.out.println("getticket:" + resultString);
				// Map<String, Object> m = JSON.parseObject(resultString, Map.class);
				// System.out.println("getAccessToken对象:" + m);
			}
		} finally {
			try {
				method.releaseConnection();
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
