package zj.java.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import zj.check.util.CheckUtil;
import zj.date.util.DateUtil;

/**
 * java工具类
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class JavaUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(JavaUtil.class);

	/**
	 * 替换字符串
	 * 
	 * @param template
	 *            模板字符串
	 * @param placeholder
	 * @param replacement
	 * @return
	 */
	public static String replaceOnce(String template, String placeholder, String replacement) {
		// System.out.println("varchar($2)".replaceAll("\\$2", "9")+replaceOnce("varchar($2)","$2","6"));
		int loc = template == null ? -1 : template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			return new StringBuffer(template.substring(0, loc)).append(replacement).append(template.substring(loc + placeholder.length())).toString();
		}
	}

	/**
	 * 去除text左右空格
	 * 
	 * @param text
	 * @return
	 */
	public static final String trim(String text) {
		return ltrim(rtrim(text));
	}

	/**
	 * 替换左字符 String d = "b  d a  e abcd f "; String rs = CommonUtil.ltrimText(d, "a", "b", " d");
	 * 
	 * @param text
	 * @param trimText
	 * @return
	 */
	public static final String ltrimText(final String text, String... trimText) {
		if (text == null) {
			return null;
		}
		if (trimText == null || trimText.length == 0) {
			return ltrim(text);
		}
		int pos = 0;
		for (; pos < text.length(); pos++) {
			boolean notTrimChar = true;
			for (int i = 0; i < trimText.length; i++) {
				notTrimChar &= (trimText[i].indexOf(text.charAt(pos)) < 0);
			}
			if (notTrimChar) {
				break;
			}
		}
		return text.substring(pos);
	}

	/**
	 * 替换右字符
	 * 
	 * @param text
	 * @param trimText
	 * @return
	 */
	public static final String rtrimText(final String text, String... trimText) {
		if (text == null) {
			return null;
		}
		if (trimText == null || trimText.length == 0) {
			return rtrim(text);
		}
		int pos = text.length() - 1;
		for (; pos >= 0; pos--) {
			boolean notTrimChar = true;
			for (int i = 0; i < trimText.length; i++) {
				notTrimChar &= (trimText[i].indexOf(text.charAt(pos)) < 0);
			}
			if (notTrimChar) {
				break;
			}
		}
		return text.substring(0, pos + 1);
	}

	/**
	 * 替换左字符串
	 * 
	 * @param text
	 * @return
	 */
	public static final String ltrim(String text) {
		return ltrim(text, null);
	}

	/**
	 * 替换左字符串
	 * 
	 * @param text
	 * @param trimText
	 * @return
	 */
	public static final String ltrim(String text, String trimText) {
		if (text == null)
			return null;
		if (trimText == null)
			trimText = " ";
		int pos;
		for (pos = 0; pos < text.length() && trimText.indexOf(text.charAt(pos)) >= 0; pos++)
			;
		return text.substring(pos);
	}

	/**
	 * 替换右字符串
	 * 
	 * @param text
	 * @return
	 */
	public static final String rtrim(String text) {
		return rtrim(text, null);
	}

	/**
	 * 替换右字符串
	 * 
	 * @param text
	 * @param trimText
	 * @return
	 */
	public static final String rtrim(String text, String trimText) {
		if (text == null)
			return null;
		if (trimText == null)
			trimText = " ";
		int pos;
		for (pos = text.length() - 1; pos >= 0 && trimText.indexOf(text.charAt(pos)) >= 0; pos--)
			;
		return text.substring(0, pos + 1);
	}

	/**
	 * 分割字符串类
	 * 
	 * @author zhangjun
	 * 
	 */
	public static class SplitConstants {
		public static final String TRIM = "all";
		public static final String NRIM = "none";
		public static final String LTRIM = "left";
		public static final String RTRIM = "right";

		public static final String NO_CASE = "";
		public static final String TO_UPPER_CASE = "0";
		public static final String TO_LOWER_CASE = "1";
	}

	/**
	 * 分割字符串
	 * 
	 * @param str
	 * @param delim
	 * @return
	 */
	public static final String[] split(String str, String delim) {
		return split(str, delim, SplitConstants.TRIM, SplitConstants.NO_CASE);
	}

	/**
	 * 分割字符串
	 * 
	 * @param str
	 * @param delim
	 * @param caseType
	 * @return
	 */
	public static final String[] split(String str, String delim, String caseType) {
		return split(str, delim, SplitConstants.TRIM, caseType);
	}

	/**
	 * 分割字符串
	 * 
	 * @param str
	 * @param delim
	 * @param trim
	 *            去除左右空格
	 * @param caseType
	 * @return
	 */
	public static final String[] split(String str, String delim, String trim, String caseType) {
		String[] arrayStrings;
		try {
			arrayStrings = mySplit(str, delim, trim, caseType);
		} catch (Throwable t) {
			arrayStrings = null;
		}
		if (arrayStrings == null) {
			arrayStrings = new String[0];
		}
		return arrayStrings;

	}

	/**
	 * 分割字符串
	 * 
	 * @param s
	 * @param delimiter
	 * @param trim
	 * @param caseType
	 * @return
	 */
	private static String[] mySplit(String str, String delimiter, String trim, String caseType) {
		int delimiterLength;
		int stringLength = str.length();
		if (delimiter == null || (delimiterLength = delimiter.length()) == 0) {
			return new String[] { str };
		}
		int count;
		int start;
		int end;
		count = 0;
		start = 0;
		while ((end = str.indexOf(delimiter, start)) != -1) {
			count++;
			start = end + delimiterLength;
		}
		count++;
		String[] result = new String[count];
		count = 0;
		start = 0;
		while ((end = str.indexOf(delimiter, start)) != -1) {
			result[count] = getTrimStr(str.substring(start, end), trim, caseType);
			count++;
			start = end + delimiterLength;
		}
		end = stringLength;
		result[count] = getTrimStr(str.substring(start, end), trim, caseType);
		return (result);
	}

	/**
	 * 合并字符串
	 * 
	 * @param str
	 * @param trim
	 * @param caseType
	 * @return
	 */
	private static String getTrimStr(String str, String trim, String caseType) {
		if (SplitConstants.TRIM.equals(trim)) {
			str = str.trim();
		} else if (SplitConstants.LTRIM.equals(trim)) {
			str = ltrim(str);
		} else if (SplitConstants.RTRIM.equals(trim)) {
			str = rtrim(str);
		}
		if (SplitConstants.TO_LOWER_CASE.equals(caseType)) {
			str = str.toLowerCase();
		} else if (SplitConstants.TO_UPPER_CASE.equals(caseType)) {
			str = str.toUpperCase();
		}
		return str;
	}

	/**
	 * 转strs数组为字符串用split分割
	 * 
	 * @param strs
	 * @param split
	 * @return
	 */
	public static String getAryStrs(String[] strs, String split) {
		return getAryStrs(strs, split, "");
	}

	/**
	 * 转split分割的str字符串,转新的分割newSplit,添加在值左右添加addStr字符串
	 * 
	 * @param str
	 * @param split
	 * @param newSplit
	 * @param addStr
	 * @return
	 */
	public static String getAryStrs(String str, String split, String newSplit, String addStr) {
		return getAryStrs(split(str, split), newSplit, addStr);
	}

	/**
	 * 转strs数组为字符串用split分割,添加在值左右添加addStr字符串
	 * 
	 * @param strs
	 * @param split
	 * @param addStr
	 * @return
	 */
	public static String getAryStrs(String[] strs, String split, String addStr) {
		String rtnStrs = "";
		try {
			if (strs.length == 1 && strs[0].equals(""))
				return "";
			for (int i = 0; i < strs.length; i++) {
				if (i != 0)
					rtnStrs += split;
				rtnStrs += addStr + strs[i] + addStr;
			}
		} catch (Exception e) {
			rtnStrs = "";
			log.error(e.getMessage());
		}
		return rtnStrs;
	}

	/**
	 * 返回String
	 * 
	 * @param o
	 * @return
	 */
	public static String objToStr(Object obj) {
		String str = "";
		if (obj == null) {
			return str;
		} else if (obj instanceof Date) {
			str = DateUtil.dateParse((Date) obj, "yyyy-MM-dd HH:mm:ss");
		} else {
			str = obj.toString();
		}
		return str;
	}

	/**
	 * 转换成大写
	 * 
	 * @param o
	 * @return
	 */
	public static String toUpperCase(String o) {
		return o == null ? null : o.toUpperCase();
	}

	/**
	 * 转换成小写
	 * 
	 * @param o
	 * @return
	 */
	public static String toLowerCase(String o) {
		return o == null ? null : o.toLowerCase();
	}

	/**
	 * LIST转数组
	 * 
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static <T> T[] listToArray(List<T> list, Class<T> clazz) {
		if (list == null || list.size() == 0)
			return null;
		@SuppressWarnings("unchecked")
		T[] array = (T[]) Array.newInstance(clazz, list.size());
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * 获取最大长度文字
	 * 
	 * @param text
	 * @param count
	 * @return
	 */
	public static String getMaxText(String text, int count) {
		// 设置文章标题最大值
		if (text == null || text.trim().equals(""))
			return "";
		int maxIndex = -1;
		maxIndex = text.length();
		if (maxIndex > count) {
			return text.substring(0, count) + "...";
		} else {
			return text;
		}
	}

	/**
	 * 转换重复的list集合
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<Object, List<Object>>> convertRepeatList(List<Map<Object, Object>> list) {
		List<Map<Object, List<Object>>> rtnList = new ArrayList<Map<Object, List<Object>>>();
		Map<Object, Object> newMap = new HashMap<Object, Object>();
		for (Map<Object, Object> map : list) {
			Object key = map.keySet().iterator().next();
			newMap.put(key, null);
		}
		Map<Object, List<Object>> newMaps = null;
		List<Object> newLists = null;
		for (Object key : newMap.keySet()) {
			newMaps = new HashMap<Object, List<Object>>();
			newLists = new ArrayList<Object>();
			for (Map<Object, Object> map : list) {
				Object value2 = map.get(key);
				if (map.containsKey(key)) {
					newLists.add(value2);
				}
			}
			newMaps.put(key, newLists);
			rtnList.add(newMaps);
		}
		return rtnList;
	}

	/**
	 * 获取UUID唯一标识
	 * 
	 * @return
	 */
	public static synchronized String getUUID() {
		// + "-" + System.currentTimeMillis()
		return getUUID(null);
	}

	/**
	 * 获取UUID唯一标识
	 * 
	 * @return
	 */
	public static synchronized String getUUID(Class<?> cla) {
		// + "-" + System.currentTimeMillis()
		return (cla == null ? "id" : cla.getSimpleName().toLowerCase()) + "-" + UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * Object对象换int类型
	 * 
	 * @param o
	 * @return
	 */
	public static int getIntValue(Object o) {
		if (o == null) {
			return 0;
		} else {
			try {
				if (o instanceof BigDecimal) {
					return ((BigDecimal) o).intValue();
				} else if (o instanceof Integer) {
					return ((Integer) o).intValue();
				} else if (o instanceof Long) {
					return ((Long) o).intValue();
				} else if (o instanceof String) {
					return Integer.parseInt(String.valueOf(o));
				} else {
					return Integer.parseInt(o + "");
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				return 0;
			}
		}
	}

	/**
	 * 去除数组中为null或""的值
	 * 
	 * @param values
	 * @return
	 */
	public static String[] aryTrimValue(String[] values) {
		if (values == null) {
			return null;
		} else {
			List<String> valuesLst = new ArrayList<String>();
			for (String value : values) {
				if (CheckUtil.isNotNull(value)) {
					valuesLst.add(value);
				}
			}
			return (String[]) valuesLst.toArray(new String[valuesLst.size()]);
		}
	}

	/**
	 * 提取字符串中的整数字
	 * 
	 * @param txt
	 * @return
	 */
	public static int conversionStringToNum(String txt) {
		try {
			return Integer.parseInt(conversionStringToString("[^0-9]", txt));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 提取字符串中的数字
	 * 
	 * @param txt
	 * @return
	 */
	public static String conversionStringToNumber(String txt) {
		return conversionStringToString("[^0-9\\.-]", txt);
	}

	/**
	 * 提取字符串中的数字
	 * 
	 * @param txt
	 * @return
	 */
	public static String conversionStringToString(String regEx, String txt) {
		if (CheckUtil.isNull(txt))
			return null;
		// String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(txt);
		// 替换非数字字符
		String str = m.replaceAll("").trim();
		return str;
	}

	/**
	 * 取得布尔值
	 * 
	 * @param txt
	 * @return
	 */
	@Deprecated
	public static boolean getBoolean(String txt) {
		try {
			return Boolean.parseBoolean(txt);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 取得Long值
	 * 
	 * @param txt
	 * @return
	 */
	@Deprecated
	public static long getLong(String txt) {
		try {
			return Long.parseLong(txt);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0l;
		}
	}

	/**
	 * 获取批量集合
	 * 
	 * @param batchList
	 * @param batchSize
	 * @return
	 */
	public static <T> List<List<T>> getBatchList(List<T> batchList, int batchSize) {
		List<List<T>> batchLists = new ArrayList<List<T>>();
		List<T> tempBatchList = null;
		int mod = batchList.size() % batchSize;
		int remainder = batchList.size() / batchSize;
		for (int i = 0; i < remainder; i++) {
			tempBatchList = new ArrayList<T>();
			for (int j = 1 + batchSize * i; j <= batchSize * (i + 1); j++) {
				tempBatchList.add(batchList.get(j - 1));
			}
			batchLists.add(tempBatchList);
		}
		if (mod != 0) {
			tempBatchList = new ArrayList<T>();
			for (int i = batchSize * remainder; i < batchList.size(); i++) {
				tempBatchList.add(batchList.get(i));
			}
			batchLists.add(tempBatchList);
		}
		return batchLists;
	}

	/**
	 * 获取最后分割符前面的字符串
	 * 
	 * @param text
	 * @param split
	 * @return
	 */
	public static String getBeforeTextLastDelimiter(String text, String split) {
		if (text == null)
			return null;
		if ("".equals(text))
			return "";
		int lastIndex = text.lastIndexOf(split);
		if (lastIndex != -1) {
			text = text.substring(0, lastIndex);
		}
		return text;
	}

	/**
	 * 去除小数点后的字符
	 * 
	 * @param text
	 * @param split
	 * @return
	 */
	public static Object getIsNumValue(Object value) {
		if (value == null)
			return null;
		if ("".equals(value))
			return "";
		String stext = value.toString();
		int lastIndex = stext.lastIndexOf(".");
		if (lastIndex == -1) {
			return value;
		}
		String beforeText = getBeforeTextLastDelimiter(stext, ".");
		String afterText = getAfterTextLastDelimiter(stext, ".");
		// System.out.println(beforeText);
		// System.out.println(afterText);
		if (CheckUtil.isPlusNum(beforeText) && CheckUtil.isPlusNum(afterText)) {
			if (BigDecimal.ZERO.compareTo(new BigDecimal(afterText)) == 0) {
				return beforeText;
			} else {
				return value;
			}
		} else {
			return value;
		}
	}

	/**
	 * 获取最后分割符后面的字符串
	 * 
	 * @param text
	 * @param split
	 * @return
	 */
	public static String getAfterTextLastDelimiter(String text, String split) {
		if (text == null)
			return null;
		if ("".equals(text))
			return "";
		int lastIndex = text.lastIndexOf(split);
		if (lastIndex != -1) {
			text = text.substring(lastIndex + 1);
		}
		return text;
	}

	/**
	 * 获取map中的值为map对象
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <K, V> Map<K, V> getValueForMap(Map<K, V> map, String key) {
		Map<K, V> valueMap = null;
		Object value = null;
		if (map == null) {
			valueMap = new HashMap<K, V>();
		} else {
			value = map.get(key);
			valueMap = value instanceof Map ? (Map<K, V>) value : new HashMap<K, V>();
		}
		return valueMap;
	}

	/**
	 * 获取map中的值为list.map对象
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <K, V> List<Map<K, V>> getValueForListMap(Map<K, V> map, String key) {
		List<Map<K, V>> valueListMap = null;
		Object value = null;
		if (map == null) {
			valueListMap = new ArrayList<Map<K, V>>();
		} else {
			value = map.get(key);
			valueListMap = value instanceof List ? (List<Map<K, V>>) value : new ArrayList<Map<K, V>>();
		}
		return valueListMap;
	}

	/**
	 * 判断两数组相同
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static boolean arrayEquals(Object[] a1, Object[] a2) {
		if (a1 == null) {
			return a2 == null || a2.length == 0;
		}

		if (a2 == null) {
			return a1.length == 0;
		}

		if (a1.length != a2.length) {
			return false;
		}

		for (int i = 0; i < a1.length; i++) {
			if (a1[i] != a2[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 首字母变小写
	 * 
	 * @param text
	 *            字符串
	 * @return 以第一个字母小写其他不变
	 */
	public static String firstLowerCase(String text) {
		return text.substring(0, 1).toLowerCase() + text.substring(1, text.length());
	}

	/**
	 * 首字母变大写
	 * 
	 * @param text
	 * @return
	 */
	public static String firstUpperCase(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1, text.length());
	}

	/**
	 * 以分割符split首字母变大写(默认第一个字符不改变大写)
	 * 
	 * @param text
	 *            校验字符串
	 * @param split
	 *            分割符
	 * @return 转换后的结果
	 */
	public static String upperCaseSplit(String text, String split) {
		return upperCaseSplit(text, split, false);
	}

	/**
	 * 以分割符split首字母变大写
	 * 
	 * @param text
	 *            校验字符串
	 * @param split
	 *            分割符
	 * @param first
	 *            是否首字母大写
	 * @return 转换后的结果
	 */
	public static String upperCaseSplit(String text, String split, boolean first) {
		String[] texts = split(text, split);
		String newText = "";
		for (int i = 0; i < texts.length; i++) {
			String str = texts[i];
			if (CheckUtil.isNull(str)) {
				continue;
			}
			if (i == 0) {
				if (first) {
					newText += firstUpperCase(str);
				} else {
					newText += str;
				}
			} else {
				newText += firstUpperCase(str);
			}
		}
		return newText;
	}

	/**
	 * 改变第一行bug BufferedReader.readLine()读取第一行会出现bug，首行第一个字符会是一个空字符  line = br.readLine(); line = readFirstLine(line); 文件保存为UTF-8格式,会出现此问题(例如:文件内容第一行以#号开头) stream/a.txt不正常,b.txt正常
	 * 
	 * @param line
	 * @return
	 * @author zhangjun
	 */
	public static String readFirstLine(String line) {
		if (line == null)
			return null;
		line = line.trim();
		if ("".equals(line)) {
			return line;
		}
		char s = line.charAt(0);
		int hc = String.valueOf(s).hashCode();
		if (hc == 65279) {
			if (line.length() > 1) {
				line = line.substring(1);
			} else {
				line = "";
			}
		}
		return line;
	}

	/**
	 * 随机数
	 * 
	 * @author zhangjun
	 * 
	 */
	public static class RandomUtil {
		private static Random RD = null;
		static {
			RD = new Random();
		}

		/**
		 * 获取[s-e]之间的随机数
		 * 
		 * @param s
		 *            开始数字
		 * @param e
		 *            结束数字
		 * @return 随机数
		 */
		public static int getInt(int s, int e) {
			return RD.nextInt(e - s + 1) + s;
		}
	}
}
