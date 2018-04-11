package zj.excel.bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 概况 ：ExcelTemplate/xls/xlsx<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class ExcelTemplate extends Excel {
	private static final long serialVersionUID = 1L;
	// str = md5.getEncryptString("zhangjun201509060001");
	// System.out.println(str);
	// str = md5.getEncryptString("zhangjun201509060002");
	// System.out.println(str);
	/**
	 * 集合名设置引用
	 * <p>
	 * (集合引用名)(#c4326f60d6c14697d37df1f6913bdeff#)
	 * <p>
	 */
	private static final String SUFFIX_COLLECTION_REFERENCE = "#c4326f60d6c14697d37df1f6913bdeff#";
	/**
	 * 集合名设置引用
	 * <p>
	 * (集合引用名)(#61770731068b4f7e81ba83e3af757a40#)(集合属性名)
	 * <p>
	 */
	private static final String PROPERTY_SPLIT = "#61770731068b4f7e81ba83e3af757a40#";
	public static final String PROPERTY_AUTO_INSERT_ROWS = "auto.insert.rows";
	private Map<String, Object> nameValueMap = Collections.synchronizedMap(new HashMap<String, Object>());
	// 模板sheet名
	public Set<String> templateSheets = new HashSet<String>();
	// 添加的sheet名
	public Set<String> addedSheets = new HashSet<String>();
	// 删除模板sheet,针对重新命名的sheet
	private boolean removeTemplatesSheets = true;
	// 删除其它sheet
	private boolean removeOtherSheets = true;

	public boolean isRemoveTemplatesSheets() {
		return removeTemplatesSheets;
	}

	public void setRemoveTemplatesSheets(boolean removeTemplatesSheets) {
		this.removeTemplatesSheets = removeTemplatesSheets;
	}

	public boolean isRemoveOtherSheets() {
		return removeOtherSheets;
	}

	public void setRemoveOtherSheets(boolean removeOtherSheets) {
		this.removeOtherSheets = removeOtherSheets;
	}

	/**
	 * 设置普通键值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		nameValueMap.put(key, value);
	}

	/**
	 * 设置集合引用键值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void putCollection(String key, Object value) {
		nameValueMap.put(key + SUFFIX_COLLECTION_REFERENCE, value);
	}

	/**
	 * 获取集合引用键
	 * 
	 * @param key
	 *            前缀键
	 * @return 集合引用键
	 */
	public String getCollectionKey(String key) {
		return key + SUFFIX_COLLECTION_REFERENCE;
	}

	/**
	 * 设置集合引用属性
	 * 
	 * @param key
	 *            键
	 * @param propertyName
	 *            属性名:{@link zj.excel.bean.ExcelTemplate#PROPERTY_AUTO_INSERT_ROWS}
	 * 
	 * @param value
	 *            值
	 */
	public void putProperty(String key, String propertyName, Object value) {
		nameValueMap.put(key + PROPERTY_SPLIT + propertyName, value);
	}

	/**
	 * 获取集合引用属性键
	 * 
	 * @param key
	 *            集合键
	 * @param propertyName
	 *            属性名
	 * @return 集合引用属性键
	 */
	public String getPropertyKey(String key, String propertyName) {
		return key + PROPERTY_SPLIT + propertyName;
	}

	/**
	 * 键值
	 * 
	 * @return 键值
	 */
	public Map<String, Object> getNameValueMap() {
		return nameValueMap;
	}
}