package zj.check.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zj.date.util.DateUtil;
import zj.java.util.JavaUtil;

/**
 * 验证工具类
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class CheckUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 判断字符串是否为空
	 * 
	 * @param value
	 *            校验字符串
	 * @return true:空或"",false:非空并且非""
	 */
	public static boolean isNull(String value) {
		return value == null || (value = value.trim()).equals("") || value.length() == 0 || value.matches("\\s*");
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param value
	 *            校验字符串
	 * @return true:非空并且非"",false:空或""
	 */
	public static boolean isNotNull(String value) {
		return !isNull(value);
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param value
	 *            校验对象
	 * @return true:空,false:非空
	 */
	public static boolean isNull(Object value) {
		return value == null;
	}

	/**
	 * 检查多个字符串是否为""或者null
	 * 
	 * @param values
	 *            可变数组
	 * @return true:全部不为null且不为"" false:其中有为null或者为""的字符串
	 */
	public static boolean isContainsNulls(String... values) {
		if (values == null)
			return true;
		for (String value : values) {
			if (value == null || value.trim().length() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是正数
	 * 
	 * @param value
	 *            校验字符串
	 * @return true:正数,false:非正数
	 */
	public static boolean isPlusNum(String value) {
		if (isNull(value))
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(value);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 银行卡号验证
	 * 
	 * @param value
	 *            校验字符串
	 * @return true:是银行卡号,false:非银行卡号
	 */
	public static boolean checkBankCard(String value) {
		char bit = getBankCardCheckCode(value.substring(0, value.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return value.charAt(value.length() - 1) == bit;

	}

	/**
	 * 银行卡号验证
	 * 
	 * @param value
	 *            校验字符串
	 * @return 非'N':是银行卡号,'N':非银行卡号
	 */
	private static char getBankCardCheckCode(String value) {
		if (value == null || value.trim().length() == 0 || !value.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = value.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	/**
	 * 字符对应的数据库长度,防止溢出,返回数据库字段最大长度
	 * 
	 * @param value
	 *            校验字符串
	 * @return 字符对应的数据库长度
	 */
	public static int getTextLengthByDB(String value) {
		if (CheckUtil.isNull(value)) {
			return 0;
		}
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为3 */
				valueLength += 3;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 截取的数据库长度字符串
	 * 
	 * @param value
	 *            校验字符串
	 * @param dbLength
	 *            数据库长度
	 * @param chineseConvertLength
	 *            中文转换长度
	 * @return 截取的数据库长度字符串
	 */
	public static String getTextLengthStopByDB(String value, int dbLength, int chineseConvertLength) {
		if (CheckUtil.isNull(value)) {
			return null;
		}
		int valueLength = 0;
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为chineseConvertLength，否则为1 */
		String newText = "";
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (isChinese(temp)) {
				/* 中文字符长度为chineseConvertLength */
				valueLength += chineseConvertLength;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
			if (valueLength > dbLength) {
				return newText;
			}
			newText += temp;
		}
		return newText;
	}

	/**
	 * 字符对应的数据库长度字符串,防止溢出
	 * 
	 * @param value
	 *            校验字符串
	 * @param dbLength
	 *            数据库长度
	 * @return 截取的数据库长度字符串
	 */
	public static String getTextLengthStopByDB(String value, int dbLength) {
		return getTextLengthStopByDB(value, dbLength, 3);
	}

	/**
	 * 验证是否是中文字符
	 * 
	 * @param value
	 *            校验字符串
	 * @return true:是中文,false:非中文
	 */
	public static boolean isChinese(String value) {
		String chinese = "[\u0391-\uFFE5]";
		if (value.matches(chinese)) {
			return true;
		}
		return false;
	}

	/**
	 * 手机号码验证:1、手机号码位数必须为11位阿拉伯数字；2、手机号码第一位必须为“13、14、15、17或18”开头。
	 * 
	 * @param value
	 *            校验字符串
	 * @return true:通过,false:不通过
	 */
	public static boolean isValidMobile(String value) {
		Pattern p = Pattern.compile("^(1[34578])\\d{9}$");
		Matcher match = p.matcher(value);
		return match.matches();
	}

	/**
	 * 验证身份证号码
	 * 
	 * @param value
	 *            校验字符串
	 * @return 返回校验结果map
	 *         <p>
	 *         key:sex,value:1:男,0:女
	 *         </p>
	 *         <p>
	 *         key:errorMsg,value:错误提示信息
	 *         </p>
	 *         <p>
	 *         key:birthday,value:生日
	 *         </p>
	 *         <p>
	 *         key:success,value:true:成功,false:失败
	 *         </p>
	 * 
	 */
	public static Map<String, Object> isValidIdNo(String value) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "false");
		Pattern p = Pattern.compile("^([\\d]{6})([\\d]{8})([\\d]{3})([\\d|x|X])$");
		Matcher match = p.matcher(value);
		if (match.matches()) {
			match.reset();
			// 取得生日
			while (match.find()) {
				String birthday = match.group(2);
				if (DateUtil.parseDate(birthday) == null) {
					result.put("errorMsg", "生日不正确");
					return result;
				}
				result.put("birthday", DateUtil.parseDate(birthday));
				// 取得性别
				String sex = match.group(3);
				result.put("sex", String.valueOf(Integer.parseInt(sex) % 2));
				// while (match.find()) {
				// for (int i = 0; i <= match.groupCount(); i++) {
				// System.out.println("索引(" + i + ")→" + match.group(i));
				// }
				// }
			}
			result.put("success", "true");
		} else {
			result.put("errorMsg", "身份证号码格式不正确");
		}
		return result;
	}

	/**
	 * 校验邮箱格式
	 * 
	 * @param obj
	 *            校验对象
	 * @return true:正确,false:非正确
	 */
	public static boolean isEmail(Object obj) {
		return isEmail(obj, ";");
	}

	/**
	 * 校验邮箱格式
	 * 
	 * @param obj
	 *            校验对象
	 * @param split
	 *            分割字符串
	 * @return true:正确,false:非正确
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmail(Object obj, String split) {
		Collection<String> values = null;
		if (obj instanceof String) {
			values = Arrays.asList(JavaUtil.split(String.valueOf(obj), split));
		} else if (obj instanceof Collection) {
			values = ((Collection<String>) obj);
		} else if (obj instanceof String[]) {
			values = Arrays.asList((String[]) obj);
		}
		if (values == null || values.size() == 0) {
			return false;
		}
		// str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile("^([a-zA-Z0-9_\\.-]+)@([\\da-zA-Z\\.-]+)\\.([a-zA-Z\\.]{2,6})$");
		Iterator<String> it = values.iterator();
		while (it.hasNext()) {
			Matcher match = p.matcher(it.next());
			if (!match.matches()) {
				return false;
			}
		}
		return true;
	}

}
