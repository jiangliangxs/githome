package zj.cxf.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;

/**
 * 
 * @author zhangjun
 * 
 */
public class CxfUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Map<String, Client> MAP_CLIENTS = Collections.synchronizedMap(new HashMap<String, Client>());
	private static DynamicClientFactory DYNAMIC_CLIENT_FACTORY = null;
	private static final Logger logger = Logger.getLogger(CxfUtil.class);
	static {
		initInvoke();
	}

	/**
	 * 初使化调用
	 */
	private static void initInvoke() {
		if (DYNAMIC_CLIENT_FACTORY == null) {
			// DYNAMIC_CLIENT_FACTORY = JaxWsDynamicClientFactory.newInstance();
			DYNAMIC_CLIENT_FACTORY = DynamicClientFactory.newInstance();
		}
	}

	/**
	 * 创建webservice代理并且调用webservice
	 * 
	 * @param wsdlAddress
	 *            调用webservice地址
	 * @param method
	 *            调用远程的方法名
	 * @param params
	 *            传递的参数
	 * @return
	 */
	public static Object[] invoke(String wsdlAddress, String method, Object[] params) throws Exception {
		Client client = (Client) MAP_CLIENTS.get(wsdlAddress);
		if (client == null) {
			client = DYNAMIC_CLIENT_FACTORY.createClient(wsdlAddress);
			// add 时间设置防止webservice在访问时候再次超时
			HTTPConduit conduit = (HTTPConduit) client.getConduit();
			HTTPClientPolicy policy = new HTTPClientPolicy();
			policy.setConnectionTimeout(1800000);
			policy.setReceiveTimeout(1800000);
			conduit.setClient(policy);
			MAP_CLIENTS.put(wsdlAddress, client);
		}
		Object[] results = client.invoke(method, params);
		return results;
	}

	/**
	 * 
	 * @param wsdlAddress
	 *            配置文件路径
	 * @param method
	 * @param arg0
	 * @return
	 */
	public static String invoke(String wsdlAddress, String method, String params) throws Exception {
		Object[] results = invoke(wsdlAddress, method, new Object[] { params });
		if (results != null && results.length > 0 && results[0] != null) {
			return String.valueOf(results[0]);
		} else {
			return null;
		}

	}

	/**
	 * 创建客户端地址集合
	 * 
	 * @param wsdlAddressList
	 * @return
	 */
	public static boolean addClientMap(List<String> wsdlAddressList) throws Exception {
		logger.debug("创建WebService开始");
		for (String wsdlAddress : wsdlAddressList) {
			try {
				Client client = DYNAMIC_CLIENT_FACTORY.createClient(wsdlAddress);
				// 设置超时时间
				HTTPConduit conduit = (HTTPConduit) client.getConduit();
				HTTPClientPolicy policy = new HTTPClientPolicy();
				policy.setConnectionTimeout(1800000);
				policy.setReceiveTimeout(1800000);
				conduit.setClient(policy);
				MAP_CLIENTS.put(wsdlAddress, client);
				logger.debug(wsdlAddress + "创建成功");
			} catch (Exception e) {
				logger.error(wsdlAddress + "创建失败:" + e.getMessage());
				e.printStackTrace();
			}
		}
		logger.debug("WebService创建成功MAP_CLIENTS集合:" + MAP_CLIENTS);
		return true;
	}
}
