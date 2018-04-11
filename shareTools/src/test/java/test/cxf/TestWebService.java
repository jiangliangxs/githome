package test.cxf;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import zj.cxf.util.CxfUtil;

public class TestWebService {
	@Test
	public void m2() throws Exception {
		Map<String,Object> wsParams = new HashMap<String,Object>();
		wsParams.put("id", "forumuser-55d0190d5b05437cb93581ea6dba8789");
		wsParams.put("audit", "1");
		String json = JSON.toJSONString(wsParams);
		String jsonResult = CxfUtil.invoke("http://127.0.0.1:8110/msthwebwapfront/ws95272ba73cbd09bf4505ce68b8b6d6c0/webService/forumUser?wsdl", "audit", json);
		System.out.println("webService返回结果:" + jsonResult);
	}
	@Test
	public void m1() throws Exception {
		String xml = "";
		String jsonResult = CxfUtil.invoke("http://127.0.0.1:8210/emailServerDB/ws/basic/testBasicWebService?wsdl", "findConstant", xml);
		System.out.println("webService返回结果:" + jsonResult);
	}
}
