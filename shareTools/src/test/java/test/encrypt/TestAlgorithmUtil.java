package test.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.Security;

import zj.encrypt.util.AlgorithmUtil;

public class TestAlgorithmUtil {
	public static void main(String[] args) throws UnsupportedEncodingException {
		//  Auto-generated method stub
		// 添加新安全算法,如果用JCE就要把它添加进去
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 }; // 24字节的密钥
		String szSrc = "This is a 3DES test. 测试";
		System.out.println("加密前的字符串:" + szSrc);
		byte[] encoded = AlgorithmUtil.encryptMode(keyBytes, szSrc.getBytes());
		System.out.println("encoded1:" + encoded);
		String strEncoded = ByteOrStringHelper.ByteToString(encoded);
		System.out.println("加密后的字符串:" + strEncoded);
		// 取得加密串
		encoded = ByteOrStringHelper.StringToByte(strEncoded);
		System.out.println("encoded2:" + encoded);
		byte[] bytes = AlgorithmUtil.decryptMode(keyBytes, encoded);
		String strBytes = new String(bytes, "UTF-8");
		System.out.println("解密后的字符串:" + strBytes);

		// String sendString = new String(encoded, "ISO-8859-1");
		// System.out.println("encoded:" + encoded + ",sendString:" + sendString);
		// byte[] myBytes = sendString.getBytes("ISO-8859-1");
		// System.out.println("myBytes:" + myBytes);

		// bytes = new byte[] { 50, 0, -1, 28, -24 };
		System.out.println("bytes1:"+bytes);
		String sendString = new String(bytes, "UTF-8");
		bytes = sendString.getBytes("UTF8");
		System.out.println("bytes2:"+bytes);
		String recString = new String(bytes, "UTF-8");
		System.out.println("sendString:" + sendString + ",recString:" + recString + ",flag:" + sendString.equals(recString));
	}
}
