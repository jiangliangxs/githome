package test.check;

import java.util.Map;

import org.junit.Test;

import zj.check.util.CheckUtil;

public class TestCheck {
	@Test
	public void m2() {
		// System.out.println(CheckUtil.checkBankCard("6228485880571233350"));
		System.out.println(CheckUtil.isEmail("285627758@qq.com"));
		String split = "|";
		System.out.println(CheckUtil.isEmail("285627758@qq.com" + split + "chengdicong@sina.com" + split + "chengdicong@ahic.com.cn" + split + "guyang0329@126.com" + split + "guyang@ahic.com.cnflyspring2006@163.com" + split + "279114719@qq.com" + split + "wanglei3@ahic.com.cn" + split + "xuxun2.sh@ccb.com" + split + "yaoqing@ahic.com.cn" + split + "wangxl@msthamc.com"));
		System.out.println(CheckUtil.isEmail("285627758@qq.com" + split + "chengdicong@sina.com" + split + "chengdicong@ahic.com.cn" + split + "guyang0329@126.com" + split + "guyang@ahic.com.cnflyspring2006@163.com" + split + "279114719@qq.com" + split + "wanglei3@ahic.com.cn" + split + "xuxun2.sh@ccb.com" + split + "yaoqing@ahic.com.cn" + split + "wangxl@msthamc.com",split));
		System.out.println(CheckUtil.isEmail("285627758@qq.com" + split + "chengdicong@sina.com" + split + "chengdicong@ahic.com.cn" + split + "guyang0329@126.com" + split + "guyang@ahic.com.cn" + split + "flyspring2006@163.com" + split + "279114719@qq.com" + split + "wanglei3@ahic.com.cn" + split + "xuxun2.sh@ccb.com" + split + "yaoqing@ahic.com.cn" + split + "wangxl@msthamc.com"));
		System.out.println(CheckUtil.isEmail("285627758@qq.com" + split + "chengdicong@sina.com" + split + "chengdicong@ahic.com.cn" + split + "guyang0329@126.com" + split + "guyang@ahic.com.cn" + split + "flyspring2006@163.com" + split + "279114719@qq.com" + split + "wanglei3@ahic.com.cn" + split + "xuxun2.sh@ccb.com" + split + "yaoqing@ahic.com.cn" + split + "wangxl@msthamc.com",split));
		System.out.println("aaaaa");
	}

	@Test
	public void m1() {
		Map<String, Object> ids = CheckUtil.isValidIdNo("34221119290730051X");
		System.out.println(ids);
	}
}
