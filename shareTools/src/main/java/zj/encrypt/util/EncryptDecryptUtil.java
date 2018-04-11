package zj.encrypt.util;

/**
 * 加密解密
 * 
 * @author zhangjun
 * 
 */
public class EncryptDecryptUtil {

	// //******************************加密*********************************////

	// 字符Ascii码值移位运算
	public static String[] stringMoveOperation(String target, int key) {
		int offset = key % 8;
		int length = target.length();
		String[] strMove = new String[2 * length];
		int j = 0;
		for (int i = 0; i < length; i++) {
			int ch = (int) target.charAt(i);
			strMove[2 * j] = Integer.toString(ch >> offset);
			strMove[2 * j + 1] = Integer.toString((ch << offset) & 255);
			j++;
		}
		return strMove;

	}

	// 转换成十六进制
	public static String[] toHexData(String target, int key) {
		String[] str = stringMoveOperation(target, key);
		String[] strHex = new String[str.length];
		for (int i = 0; i < str.length; i++) {
			strHex[i] = Integer.toHexString(Integer.parseInt(str[i]));
			while (strHex[i].length() < 2) {
				strHex[i] = "0" + strHex[i];
			}
		}
		return strHex;
	}

	// 加密后的字符串
	public static String encryptData(String target, int key) {
		String[] strdata = toHexData(target, key);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strdata.length; i++) {
			sb.append(strdata[i]);
		}
		return sb.toString();

	}

	// //******************************解密*********************************////

	// 将加密后的字符串转换成字符数组
	public static String[] toEncryptDataStringArray(String target) {
		String[] str = new String[target.length() / 4];

		for (int j = 0; j < str.length; j++) {
			str[j] = target.substring(4 * j, 4 * j + 4);
		}
		return str;
	}

	// 将字符数组(十六进制)转换成十进制数组
	public static String[] toEncryptDataAlgorismArray(String target) {
		String[] tarArr = toEncryptDataStringArray(target);
		String[] strAl = new String[tarArr.length];
		for (int i = 0; i < tarArr.length; i++) {
			strAl[i] = Integer.valueOf(tarArr[i], 16).toString();

		}
		return strAl;
	}

	// 十进制反转回移位之前
	public static String[] algorismMoveOperation(String target, int key) {
		int offset = key % 8;
		String[] alArr = toEncryptDataAlgorismArray(target);
		// System.out.println("十进制反转回移位之前" + alArr.length);
		String[] chArr = new String[alArr.length];
		for (int i = 0; i < alArr.length; i++) {
			chArr[i] = Integer.toString(Integer.parseInt(alArr[i]) >> offset);
		}
		return chArr;
	}

	// 解密后的字符串
	public static String decryptData(String target, int key) {
		String[] strdata = algorismMoveOperation(target, key);
		char[] strascii = new char[strdata.length];
		for (int i = 0; i < strdata.length; i++) {
			strascii[i] = (char) Integer.parseInt(strdata[i]);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strascii.length; i++) {
			sb.append(strascii[i]);
		}

		// try {
		// return new String(sb.toString().getBytes(), "gb2312").toString();
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }

		return sb.toString();

	}

	public static void main(String[] args) {
		String text = "helloWorld";
		System.out.println("----------------加密测试---------------");
		String encryptText = EncryptDecryptUtil.encryptData(text, 100);
		System.out.println("加密前:" + text + "->加密:" + encryptText);
		/*
		 * for(int i=0;i<EncryptDecryptUtil.toEncryptDataStringArray("96f70").length;i++) { System.out.println(EncryptDecryptUtil.toEncryptDataStringArray("96f70")[i]); }
		 * 
		 * System.out.println("-------------------------------");
		 * 
		 * for(int i=0;i<EncryptDecryptUtil.toEncryptDataAlgorismArray("96f70").length;i++) { System.out.println(EncryptDecryptUtil.toEncryptDataAlgorismArray("96f70")[i]); }
		 * 
		 * System.out.println("-------------------------------");
		 * 
		 * String[] ss=EncryptDecryptUtil.algorismMoveOperation("96f70",100); for(int i=0;i<ss.length;i++) { System.out.println(ss[i]); }
		 */
		System.out.println("----------------解密测试---------------");
		text = decryptData("0680065006c006c006f0057006f0072006c00640", 100);
		System.out.println("解密:" + text);
	}
}
