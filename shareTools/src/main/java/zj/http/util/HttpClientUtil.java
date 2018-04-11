package zj.http.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

/**
 * httpClient工具类
 * 
 * @author zhangjun
 * 
 */
public class HttpClientUtil {
	private static transient final Logger logger = Logger.getLogger(HttpClientUtil.class);

	/**
	 * get请求
	 * 
	 * @param url
	 *            请求地址
	 * @return 响应数据
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		String resultString = null;
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		try {
			int resultCode = httpClient.executeMethod(method);
			if (resultCode == 200) {
				resultString = method.getResponseBodyAsString();
				logger.debug("调用方法返回结果:" + resultString);
			}
			return resultString;
		} finally {
			try {
				method.releaseConnection();
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * XML.post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            请求内容
	 * @return 响应数据
	 * @throws Exception
	 */
	public static String postXML(String url, String content) throws Exception {
		return post(url, content, "application/xml", "UTF-8");
	}

	/**
	 * JSON.post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            请求内容
	 * @return 响应数据
	 * @throws Exception
	 */
	public static String postJSON(String url, String content) throws Exception {
		return post(url, content, "application/json", "UTF-8");
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            请求内容
	 * @param contentType
	 *            请求类型
	 * @param charset
	 *            请求编码
	 * @return 响应数据
	 * @throws Exception
	 */
	public static String post(String url, String content, String contentType, String charset) throws Exception {
		String resultString = null;
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		try {
			method.setRequestEntity(new StringRequestEntity(content, contentType, charset));
			int resultCode = httpClient.executeMethod(method);
			if (resultCode == 200) {
				resultString = method.getResponseBodyAsString();
				logger.debug("调用方法返回结果:" + resultString);
			}
			return resultString;
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
