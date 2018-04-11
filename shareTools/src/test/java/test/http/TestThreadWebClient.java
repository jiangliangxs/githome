package test.http;

import org.junit.Test;

import zj.io.util.NetWorkUtil;

public class TestThreadWebClient {
	@Test
	public void getStringByURL() throws Exception {
		String urlAddr = "";
		urlAddr = "http://www.huabian.com/uploadfile/2014/0716/20140716101246925.jpg";
		NetWorkUtil.writeFileToLocalByURL(urlAddr, "E:/document/network/" + System.currentTimeMillis() + "/zhangjun.jpg");
	}

}
