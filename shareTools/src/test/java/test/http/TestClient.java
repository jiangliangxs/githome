package test.http;

import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

import zj.encrypt.util.Md5Util;
import zj.java.util.JavaUtil;

import com.alibaba.fastjson.JSON;

public class TestClient {
	@Test
	public void testGet() throws Exception {
		String url = "";
		url = "http://money.163.com/special/baidu_netease_frame_page/?bdzt?date=20150115";
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		try {
			int resultCode = httpClient.executeMethod(method);
			if (resultCode == 200) {
				String resultString = method.getResponseBodyAsString();
				System.out.println("testGet:" + resultString);
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
	@SuppressWarnings("unchecked")
	@Test
	public void m1() {
		try {
			String json = "";
			String md5sign = Md5Util.kernalMD5UTF8("aaaaaaaaaaaaaaaaaaaa" + json);
			String url = "http://127.0.0.1:8080/eservice/" + md5sign;
			HttpClient httpClient = new HttpClient();
			PostMethod postMethod = new PostMethod(url);
			try {
				postMethod.setRequestEntity(new StringRequestEntity(json, "text/json", "UTF-8"));
				int resultCode = httpClient.executeMethod(postMethod);
				if (resultCode == 200) {
					String resultString = postMethod.getResponseBodyAsString();
					Map<String, Object> resultMap = JSON.parseObject(resultString, Map.class);
					String records = JavaUtil.objToStr(resultMap.get("Records"));
					System.out.println(records);
					List<Object> entities = JSON.parseObject(records, List.class);
					System.out.println("调用方法返回结果:" + entities);
					//for (Object obj : entities) {
						//MsgManagerPage entity = JSON.parseObject(JavaUtil.objToStr(obj), MsgManagerPage.class);
						//System.out.println(entity.getChannelSource());
					//}
				}
			} finally {
				try {
					postMethod.releaseConnection();
					((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
	}
}
