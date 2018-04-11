package test.encrypt;

import org.junit.Test;

import zj.encrypt.util.Md5Util;

public class TestMD5 {
	@Test
	public void m1(){
		String str = null;
		str = Md5Util.toMd5("zhangjun1");
		System.out.println(str);
	}
}
