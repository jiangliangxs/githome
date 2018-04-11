package zj.encrypt.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 加密解密工具类
 * 
 * @author zhangjun
 * 
 */
public class EncryptUtil {
	private static String key = "AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746";

	public static String encrypt(String encrypt) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		DESKeySpec desKS = new DESKeySpec(hex2byte(key.getBytes()));
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.ENCRYPT_MODE, sk);
		return byte2hex(cip.doFinal(encrypt.getBytes()));
	}

	public static String decrypt(String decrypt) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		DESKeySpec desKS = new DESKeySpec(hex2byte(key.getBytes()));
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.DECRYPT_MODE, sk);
		return new String(cip.doFinal(hex2byte(decrypt.getBytes())));
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String tmp = "";
		for (int n = 0; n < b.length; n++) {
			tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (tmp.length() == 1) {
				hs = hs + "0" + tmp;
			} else {
				hs = hs + tmp;
			}
		}
		tmp = null;
		return hs.toUpperCase();

	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("长度不是偶数");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		b = null;
		return b2;
	}

	public static void main(String[] args) {
		String password = "zhangjun";
		try {
			System.out.println(password);
			String tt = encrypt(password);
			System.out.println(tt);
			System.out.println(decrypt(tt));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
