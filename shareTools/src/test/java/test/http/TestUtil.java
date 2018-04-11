package test.http;

import org.junit.Test;

import zj.http.util.HttpClientUtil;

public class TestUtil {
	@Test
	public void testGet() throws Exception {
		String r = HttpClientUtil.get("http://127.0.0.1:8080/eservice/ebiz/cache/init/msth/ebizInterfaceMsgRelation");
		System.out.println(r);
	}
	@Test
	public void testPost() throws Exception {
		String r = HttpClientUtil.post("https://paimai2.alltobid.com/webwcf/BidCmd.svc/WebCmd", "{\"method\":\"login\",\"cmd\":\"%7B%22version%22%3A%221.0%22%2C%22timestamp%22%3A%221458366873447%22%2C%22bidnumber%22%3A%2253210563%22%2C%22requestid%22%3A%221458366873448%22%2C%22checkcode%22%3A%221952d6a9871721f0938941a3fade1fff%22%2C%22request%22%3A%7B%22info%22%3A%22Win7%3Bie%3A8%3B19%22%2C%22uniqueid%22%3A%22ec532ec0-6b14-47b4-b52f-8056a9bb2854%22%2C%22bidnumber%22%3A%2253210563%22%2C%22bidpassword%22%3A%22f19b586fab9ffa712a63e23d604cec72%22%2C%22machinecode%22%3A%22%22%2C%22imagenumber%22%3A%22129037%22%2C%22idcard%22%3A%22310110197406104620%22%2C%22clientId%22%3A%22%22%2C%22idtype%22%3A%220%22%7D%7D\"}", "application/json", "UTF-8");
		System.out.println(r);
	}
}
