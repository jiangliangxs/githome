package test.encrypt;

import java.security.*;
import javax.crypto.*;

public class TestSingleKeyExample {
	
	public static void main(String[] args) {
		try {
			String algorithm = "DES"; // 定义加密算法,可用 DES,DESede,Blowfish
			String message = "Hello World. 这是待加密的信息";
			// 生成个DES密钥
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(56); // 选择DES算法,密钥长度必须为56位
			Key key = keyGenerator.generateKey(); // 生成密钥
			// 生成Cipher对象
			Cipher cipher = Cipher.getInstance(algorithm);
			// 用密钥加密明文(message),生成密文(cipherText)
			cipher.init(Cipher.ENCRYPT_MODE, key); // 操作模式为加密(Cipher.ENCRYPT_MODE),key为密钥
			byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8")); // 得到加密后的字节数组
			String scipherText = new String(cipherText,"UTF-8");
			System.out.println("加密后的信息: " + scipherText);
			// 用密钥加密明文(plainText),生成密文(cipherByte)
			cipher.init(Cipher.DECRYPT_MODE, key); // 操作模式为解密,key为密钥
			byte[] sourceText = cipher.doFinal(cipherText); // 获得解密后字节数组
			System.out.println("解密后的信息: " + new String(sourceText,"UTF-8"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
