package zj.regex.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 类名 ：RegexUtil<br>
 * 概况 ：正则工具类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class RegexUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Map<String, String> SPECIALS;
	private transient static final Logger log = Logger.getLogger(RegexUtil.class);
	static {
		SPECIALS = new HashMap<String, String>();
		SPECIALS.put("[", "\\[");
		SPECIALS.put("$", "\\$");
		SPECIALS.put("(", "\\(");
		SPECIALS.put(")", "\\)");
		SPECIALS.put("*", "\\*");
		SPECIALS.put(".", "\\.");
		// SPECIALS.put("[", "\\[");
		// SPECIALS.put("]", "\\]");
		SPECIALS.put("?", "\\?");
		SPECIALS.put("\\", "\\\\");
		SPECIALS.put("|", "\\|");
		SPECIALS.put("{", "\\{");
		SPECIALS.put("}", "\\}");
		// **********************
		SPECIALS.put("'", "\\'");
	}

	public static class FillString {
		// String str = md5.getEncryptString("zhangjun201509010000");
		// System.out.println(str);
		// str = md5.getEncryptString("zhangjun201509010001");
		// System.out.println(str);
		// str = md5.getEncryptString("zhangjun201509010002");
		// System.out.println(str);
		// str = md5.getEncryptString("zhangjun201509010003");
		// System.out.println(str);
		// str = md5.getEncryptString("zhangjun201509010004");
		// System.out.println(str);
		// str = md5.getEncryptString("zhangjun201509010005");
		// System.out.println(str);
		/**
		 * 获取新字符串占位符对应的值:get(KEY_NEW_PLACEHOLDER_VALUE+占位符名)
		 */
		public static final String KEY_NEW_PLACEHOLDER_VALUE = "b1bb30d4bab4d8cce00d78283098dabe";
		/**
		 * 获取原字符串占位符对应的值:get(KEY_ORIGINAL_PLACEHOLDER_VALUE+占位符名)
		 */
		public static final String KEY_ORIGINAL_PLACEHOLDER_VALUE = "024110d8c905229ba6b01667cd2fe316";
		/**
		 * 获取新字符串值的key:get(KEY_NEW_VALUE)
		 */
		public static final String KEY_NEW_VALUE = "f8e4e87efbb70a29018eb9a106a8c22d";
		/**
		 * 获取原字符串值的key:get(KEY_ORIGINAL_VALUE)
		 */
		public static final String KEY_ORIGINAL_VALUE = "ad8fea661188b29cf2ffe3c8fe5121cd";
		/**
		 * 所有占位符名:get(KEY_PLACEHOLDER_NAMES),以KEY_PLACEHOLDER_NAMES_SPLIT分割
		 */
		public static final String KEY_PLACEHOLDER_NAMES = "824ff4fec41a74aa8da8873c403d816d";
		/**
		 * 占位符名分割符
		 */
		public static final String KEY_PLACEHOLDER_NAMES_SPLIT = "052b9e211243c0937871d8b296616d53";
		/**
		 * 所有占位符:get(KEY_PLACEHOLDER),以KEY_PLACEHOLDER_SPLIT分割
		 */
		public static final String KEY_PLACEHOLDER = "d72a3113b65fc8984ac1a346490427dd";
		/**
		 * 占位符分割符
		 */
		public static final String KEY_PLACEHOLDER_SPLIT = "052b9e211243c0937871d8b296616d53";
	}

	/**
	 * 占位符=值
	 * 
	 * @param keyOriginalValue
	 *            原始值
	 * @param nameValueMap
	 *            键值替换
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> fillString(String keyOriginalValue, Map<String, String> nameValueMap) throws Exception {
		return fillString(keyOriginalValue, nameValueMap, null);
	}

	/**
	 * 占位符=值
	 * 
	 * @param keyOriginalValue
	 *            原始值
	 * @param nameValueMap
	 *            键值替换
	 * @param replaceOriginalValueKeys
	 *            替换原来值的键
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> fillString(String keyOriginalValue, Map<String, String> nameValueMap, Set<String> replaceOriginalValueKeys) throws Exception {
		Map<String, String> rtnMap = new HashMap<String, String>();
		if (keyOriginalValue != null && !"".equals(keyOriginalValue)) {
			String places = "";
			String placeNames = "";
			String keyNewValue = keyOriginalValue;
			Matcher m = null;
			m = getMatcher(keyOriginalValue);
			while (m.find()) {
				String nameValueGroup = m.group(0);
				// name = m.group(1);
				if (nameValueGroup == null || nameValueGroup.equals("")) {
					continue;
				}
				// 替换掉所有匹配任何空白字符，包括空格、制表符、换页符
				// nameValueGroup = nameValueGroup.replaceAll("\\s*", "");
				String nameValue = nameValueGroup;
				if (!"".equals(places)) {
					places += RegexUtil.FillString.KEY_PLACEHOLDER_SPLIT;
				}
				places += nameValueGroup;
				if (nameValueGroup.length() > 1) {
					nameValue = nameValueGroup.substring(1, nameValueGroup.length() - 1);
				}
				Map<String, String> map = getPlaceHodlder(nameValue);
				if (!"".equals(placeNames)) {
					placeNames += RegexUtil.FillString.KEY_PLACEHOLDER_NAMES_SPLIT;
				}
				String fieldName = map.get("fieldName");
				// 替换开始和结束的空格
				// fieldName = fieldName.replaceAll("^ *", "").replaceAll(" *$", "");
				fieldName = fieldName.trim();
				placeNames += fieldName;
				String fieldValue = map.get("fieldValue");
				// 替换开始和结束的空格
				fieldValue = fieldValue.trim();
				String value = null;
				boolean isReplace = false;
				if (replaceOriginalValueKeys != null && replaceOriginalValueKeys.contains(fieldName)) {
					// 替换原值
					value = fieldValue;
					isReplace = true;
				} else {
					if (nameValueMap != null && nameValueMap.size() > 0 && nameValueMap.containsKey(fieldName)) {
						value = nameValueMap.get(fieldName);
						isReplace = true;
					}
				}
				value = value == null ? "" : value;
				if (isReplace) {
					keyNewValue = keyNewValue.replaceFirst(formatSpecial("{" + nameValue + "}"), value);
					rtnMap.put(RegexUtil.FillString.KEY_NEW_PLACEHOLDER_VALUE + fieldName, value);
				}
				rtnMap.put(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE + fieldName, fieldValue);
				log.debug("占位符名值组=" + nameValueGroup + ",占位符名值=" + nameValue + ",占位符名值=" + nameValue + ",占位符名=" + fieldName + ",占位符原值=" + fieldValue + ",占位符新值=" + value);
			}
			rtnMap.put(RegexUtil.FillString.KEY_ORIGINAL_VALUE, keyOriginalValue);
			rtnMap.put(RegexUtil.FillString.KEY_NEW_VALUE, keyNewValue);
			log.debug("原字符串值:" + keyOriginalValue + ",新字符串值:" + keyNewValue);
			log.debug("取原占位符值=返回值.get(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE +占位符名),取新占位符值=返回值.get(RegexUtil.FillString.KEY_NEW_PLACEHOLDER_VALUE +占位符名)");
			log.debug("取原字符串=返回值.get(RegexUtil.FillString.KEY_ORIGINAL_VALUE),取新字符串=返回值.get(RegexUtil.FillString.KEY_NEW_VALUE)");
			rtnMap.put(RegexUtil.FillString.KEY_PLACEHOLDER, places);
			rtnMap.put(RegexUtil.FillString.KEY_PLACEHOLDER_NAMES, placeNames);
		}
		return rtnMap;
	}

	/**
	 * 占位符=值
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> fillString(String keyOriginalValue) throws Exception {
		return fillString(keyOriginalValue, null);
	}

	/**
	 * 获取{占位符=值}名值
	 * 
	 * @param nameValue
	 * @return
	 */
	private static Map<String, String> getPlaceHodlder(String nameValue) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		String fieldName = "";
		String fieldValue = "";
		int index = nameValue.indexOf("=");
		if (index == -1) {
			fieldName = nameValue;
		} else {
			fieldName = nameValue.substring(0, index);
			fieldValue = nameValue.substring(index + 1);
		}
		rtnMap.put("fieldName", fieldName);
		rtnMap.put("fieldValue", fieldValue);
		return rtnMap;
	}

	/**
	 * 转换java属性为数据库字段
	 * 
	 * @param text
	 * @return
	 */
	public static String convertJavaToDbField(String text) {
		Matcher m = getMatcher("([A-Z])", text);
		Set<String> filter = new HashSet<String>();
		while (m.find()) {
			for (int i = 0; i < m.groupCount(); i++) {
				String s = m.group(i);
				// 首字母大写无需转换
				if (text.indexOf(s) == 0) {
					continue;
				}
				if (filter.contains(s)) {
					continue;
				}
				filter.add(s);
				text = text.replaceAll(s, "_" + s);
			}
		}
		return text;
	}

	/**
	 * 获取匹配结果对象
	 * 
	 * @param regex
	 * @param text
	 * @return
	 */
	public static Matcher getMatcher(String regex, String text) {
		Pattern pattern = Pattern.compile(regex);
		Matcher result = pattern.matcher(text);
		return result;
	}

	/**
	 * 获取匹配结果(全部)
	 * 
	 * @param regex
	 * @param text
	 * @return
	 */
	public static boolean getMatcherResult(String regex, String text) {
		Matcher result = getMatcher(regex, text);
		return result.matches();
	}

	/**
	 * 获取匹配
	 * 
	 * @param str
	 * @return
	 */
	public static Matcher getMatcher(String str) {
		Matcher m = null;
		String pile = null;
		// pile = "\\{((([\\w]*)|([\u4E00-\u9FA5]*))*)\\}";
		// pile = "\\{([^\\{|^\\}*]*=[^\\{|^\\}*]*)*\\}";
		// pile = "\\{([^\\{*]*=[^\\}*]*)\\}";
		// pile = "\\{([^{*]*=[^}*]*)\\}";
		// pile = "\\{([^\\{|^\\}*]*=[^\\{|^\\}*]*)\\}";
		// 第一次版本
		// pile = "\\{([^\\{|^\\}]*=[^\\{|^\\}]*)\\}";
		// 第二次版本
		// pile = "\\{([^\\{]*=[^\\}]*)\\}";
		// 添加匹配任何空白字符，包括空格、制表符、换页符等等。等价于 [ \f\n\r\t\v]。
		// // 第三次版本
		// pile = "\\{(\\s*[^\\{\\s]*\\s*=\\s*[^\\}\\s]*\\s*)\\}";
		// 第四次版本
		// pile = "\\{( *[^\\{\\s]* *= *[^\\}\\s]* *)\\}";
		// 第五次版本
		// pile = "\\{(?: *[^\\{\\s]* *= *[^\\}\\s]* *)\\}";
		// 第六次版本
		pile = "\\{([^\\{\\}]*)\\}";
		// str = "and INUM>''{INUM最大值=本地系统最大值}''  and INUM like ''{INUM前缀=604766,600418}'' and userField1=''1'' and CONVERT(varchar(10) ,StartedAt,120 )>=''{录音开始时间=2014-03-01}'' and CONVERT(varchar(10) ,StartedAt,120 )<={录音结束时间=CONVERT(varchar(10) ,DATEADD(day, -1, getDate() ),120 )}";
		m = Pattern.compile(pile).matcher(str);
		return m;
	}

	/***
	 * 格式化特殊字符
	 * 
	 * @param special
	 * @return
	 */
	public static String formatSpecial(String special) {
		if (special == null || special.equals(""))
			return "";
		String newSpecial = "";
		for (int i = 0; i < special.length(); i++) {
			char at = special.charAt(i);
			String skey = String.valueOf(at);
			String rspecial = SPECIALS.get(skey);
			if (rspecial == null) {
				newSpecial += skey;
			} else {
				newSpecial += rspecial;
			}
			// switch (at) {
			// case '{'=
			// newSpecial += "\\{";
			// break;
			// case '}'=
			// newSpecial += "\\}";
			// break;
			// case '('=
			// newSpecial += "\\(";
			// break;
			// case ')'=
			// newSpecial += "\\)";
			// break;
			// case '['=
			// newSpecial += "\\[";
			// break;
			// default=
			// newSpecial += at;
			// }
		}
		return newSpecial;
	}
}
