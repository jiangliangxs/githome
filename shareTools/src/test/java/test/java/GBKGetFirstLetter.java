package test.java;


public class GBKGetFirstLetter {
	private static final int GB_SP_DIFF = 160;

	private static final int[] secPosvalueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	private static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z' };

	public static String getFirstLetter(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) { // ���δ���str��ÿ���ַ�
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) { // �Ǻ���
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	private static char convert(byte[] bytes) {

		char result = '-';
		int secPosvalue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosvalue = bytes[0] * 100 + bytes[1];
		System.out.println(secPosvalue);
		for (i = 0; i < 23; i++) {
			if (secPosvalue >= secPosvalueList[i] && secPosvalue < secPosvalueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	public static void main(String args[]) {
		System.out.println(UTF8GetFirstLetter.getFirstLetter("�й���"));
		System.out.println(UTF8GetFirstLetter.getFirstLetter("������������"));
		System.out.println(UTF8GetFirstLetter.getFirstLetter("��ͨ��"));
	}
}