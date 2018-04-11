package zj.regex.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import zj.check.util.CheckUtil;
import zj.java.util.JavaUtil;

/**
 * 类名 ：RegexSqlUtil<br>
 * 概况 ：正则sql工具类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class RegexSqlUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(RegexSqlUtil.class);

	/**
	 * sql属性
	 * 
	 * @author zhangjun
	 * 
	 */
	public static class SqlConstant {
		/**
		 * sql语句
		 */
		public static final String KEY_SQL = "sql";
		/**
		 * where条件占位名
		 */
		public static final String KEY_PLACE_WHERE_NAME = "placeWhereName";
		/**
		 * where条件占位值
		 */
		public static final String KEY_PLACE_WHERE_VALUE = "placeWhereValue";
		/**
		 * 排序值
		 */
		public static final String KEY_PLACE_SORT_VALUES = "placeSortValues";
		/**
		 * 分页开始记录占位名
		 */
		public static final String KEY_PAGE_START = "page.start";
		/**
		 * 分页结束记录占位名
		 */
		public static final String KEY_PAGE_END = "page.end";

		/**
		 * 排序值占位值
		 */
		public static final String PREFIX_SORT_VALUES = "sort.values";
		/**
		 * 排序值为null的占位名
		 */
		public static final String PREFIX_SORT_VALUES_NULL = "sort.null";
		/**
		 * where条件值前缀占位值
		 */
		public static final String PREFIX_WHERE_VALUE = "where.value.";
		/**
		 * where条件值为空时前缀占位名
		 */
		public static final String PREFIX_WHERE_NULL = "where.null.";

	}

	/**
	 * 根据params参数替换sql
	 * 
	 * @return 替换后的新的sql语句
	 */
	public static synchronized String replaceSql(Map<String, String> params) {
		try {
			String sql = params.get(SqlConstant.KEY_SQL);
			String wherePlaceName = params.get(SqlConstant.KEY_PLACE_WHERE_NAME);
			String wherePlaceValue = params.get(SqlConstant.KEY_PLACE_WHERE_VALUE);
			String placeSortValues = params.get(SqlConstant.KEY_PLACE_SORT_VALUES);
			String pageStart = params.get(SqlConstant.KEY_PAGE_START);
			String pageEnd = params.get(SqlConstant.KEY_PAGE_END);
			// where条件占位名
			String wherePlaceValueName = SqlConstant.PREFIX_WHERE_VALUE + wherePlaceName;
			// where条件值为null的占位名
			String wherePlaceValueNameNull = SqlConstant.PREFIX_WHERE_NULL + wherePlaceName;
			// 待替换集合
			Map<String, String> nameValueMap = new HashMap<String, String>();
			// 添加where条件名值
			nameValueMap.put(wherePlaceValueName, wherePlaceValue);
			// 添加分页替换值
			if (params.containsKey(SqlConstant.KEY_PAGE_START)) {
				nameValueMap.put(SqlConstant.KEY_PAGE_START, pageStart);
			}
			if (params.containsKey(SqlConstant.KEY_PAGE_END)) {
				nameValueMap.put(SqlConstant.KEY_PAGE_END, pageEnd);
			}
			// 添加排序条件值
			if (params.containsKey(SqlConstant.KEY_PLACE_SORT_VALUES)) {
				nameValueMap.put(SqlConstant.PREFIX_SORT_VALUES, placeSortValues);
			}
			// 返回替换后的where条件及排序及分页条件对象
			Map<String, String> newMap = RegexUtil.fillString(sql, nameValueMap);
			// 获取新的sql语句
			sql = newMap.get(RegexUtil.FillString.KEY_NEW_VALUE);
			// 获取所有固定占位符名及上面替换后的默认值
			// newMap = RegexUtil.fillString(sql);
			Set<String> replaceOriginalValueKeys = new HashSet<String>();
			// 是否替换条件为空
			if (CheckUtil.isNull(wherePlaceValue)) {
				// 替换条件为空
				nameValueMap.put(wherePlaceValueNameNull, "");
			} else {
				// 替换真实条件
				replaceOriginalValueKeys.add(wherePlaceValueNameNull);
				// nameValueMap.put(wherePlaceValueNameNull, newMap.get(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE + wherePlaceValueNameNull));
			}
			// 是否替换排序为空
			if (params.containsKey(SqlConstant.KEY_PLACE_SORT_VALUES)) {
				if (CheckUtil.isNull(placeSortValues)) {
					// 替换排序为空
					nameValueMap.put(SqlConstant.PREFIX_SORT_VALUES_NULL, "");
				} else {
					// 替换真实排序
					replaceOriginalValueKeys.add(SqlConstant.PREFIX_SORT_VALUES_NULL);
					// nameValueMap.put(SqlConstant.PREFIX_SORT_VALUES_NULL, newMap.get(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE + SqlConstant.PREFIX_SORT_VALUES_NULL));
				}
			}
			// 开始执行替换
			newMap = RegexUtil.fillString(sql, nameValueMap, replaceOriginalValueKeys);
			// 获取最终的替换
			sql = newMap.get(RegexUtil.FillString.KEY_NEW_VALUE);
			log.debug("wherePlaceName:" + wherePlaceName + ",wherePlaceValue:" + wherePlaceValue + ",替换后的sql:" + sql);
			return sql;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 替换最终sql
	 * 
	 * @param sql
	 *            sql语句
	 * @return 替换后的新的sql语句
	 */
	public static synchronized String replaceSql(String sql) {
		try {
			// 待替换集合
			Map<String, String> nameValueMap = new HashMap<String, String>();
			// 获取所有固定占位符名及上面替换后的默认值
			Map<String, String> newMap = RegexUtil.fillString(sql);
			String placeNames = newMap.get(RegexUtil.FillString.KEY_PLACEHOLDER_NAMES);
			String[] placeNamesAry = JavaUtil.split(placeNames, RegexUtil.FillString.KEY_PLACEHOLDER_NAMES_SPLIT);
			for (String placeName : placeNamesAry) {
				// 替换占位符为空
				nameValueMap.put(placeName, "");
			}
			// 开始执行替换
			newMap = RegexUtil.fillString(sql, nameValueMap);
			// 获取最终的替换
			sql = newMap.get(RegexUtil.FillString.KEY_NEW_VALUE);
			log.debug("替换后的sql:" + sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql;
	}

}
