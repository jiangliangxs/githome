package zj.message.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import zj.check.util.CheckUtil;
import zj.java.util.JavaUtil;

/**
 * I18n资源文件工具类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>} 设置国际化setLocale
 */
public class MessageI18nUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(MessageI18nUtil.class);
	public static String AJAX_SUCCESS;
	public static String AJAX_FAIL;
	public static String AJAX_MSG;
	private static Locale locale;
	/** 国际化资源文件键值对 **/
	public static final Map<String, String> I18N_RESOURCE_KEY_VALUE = Collections.synchronizedMap(new HashMap<String,String>());
	/** 国际化资源文件地址集合,无序 **/
	public static Set<String> I18N_RESOURCES = new HashSet<String>();
	/** 国际化资源文件/资源文件内容初使化 **/
	static {
		AJAX_SUCCESS = "success";
		AJAX_FAIL = "fail";
		AJAX_MSG = "msg";
		loadConfig(null);
	}

	/**
	 * 设置国际化
	 */
	public static void setLocale(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
	}

	/**
	 * 设置资源文件路径
	 * 
	 * @param configFile
	 *            <p>
	 *            i18n.page
	 *            </p>
	 *            <p>
	 *            i18n.info
	 *            </p>
	 *            <p>
	 *            i18n.error
	 *            </p>
	 *            <p>
	 *            i18n.app.page
	 *            </p>
	 *            <p>
	 *            i18n.app.info
	 *            </p>
	 *            <p>
	 *            i18n.app.error
	 *            </p>
	 * @return 资源文件值
	 */
	public static void loadConfig(String configFile) {
		I18N_RESOURCES.add("i18n.page");
		I18N_RESOURCES.add("i18n.info");
		I18N_RESOURCES.add("i18n.error");

		I18N_RESOURCES.add("i18n.app.page");
		I18N_RESOURCES.add("i18n.app.info");
		I18N_RESOURCES.add("i18n.app.error");
		// 添加新的国际化资源文件地址
		String[] i18n_resources = JavaUtil.split(configFile, ",");
		for (String s : i18n_resources) {
			if (CheckUtil.isNull(s))
				continue;
			I18N_RESOURCES.add(s);
		}

	}

	// 国际化资源文件
	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param notExistIsNull
	 *            <p>
	 *            true:如果资源文件键不存在,则返回null
	 *            </p>
	 *            <p>
	 *            false:如果资源文件键不存在,则返回资源文件key
	 *            </p>
	 * @return 资源文件值
	 */
	public static String getString(String key, boolean notExistIsNull) {
		return getString(key, I18N_RESOURCES, notExistIsNull);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @return 资源文件值
	 */
	public static String getString(String key) {
		return getString(key, false);
	}

	/**
	 * 通过key,action类获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param action
	 *            action对应的类
	 * @param notExistIsNull
	 *            <p>
	 *            true:如果资源文件键不存在,则返回null
	 *            </p>
	 *            <p>
	 *            false:如果资源文件键不存在,则返回资源文件key
	 *            </p>
	 * @return 资源文件值
	 */
	public static String getStringByClass(String key, Class<?> action, boolean notExistIsNull) {
		// 添加后会去重
		I18N_RESOURCES.add(action.getName());
		return getString(key, notExistIsNull);
	}

	/**
	 * 通过key,action类获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param action
	 *            action对应的类
	 * @return 资源文件值
	 */
	public static String getStringByClass(String key, Class<?> action) {
		return getStringByClass(key, action, false);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param notExistIsNull
	 *            <p>
	 *            true:如果资源文件键不存在,则返回null
	 *            </p>
	 *            <p>
	 *            false:如果资源文件键不存在,则返回资源文件key
	 *            </p>
	 * @param arguments
	 *            资源文件参数
	 * @return 资源文件值
	 */
	public static String getStringByMemoryParams(String key, boolean notExistIsNull, Object... arguments) {
		return getValueByParams(getStringByMemory(key), arguments);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param arguments
	 *            资源文件参数
	 * @return 资源文件值
	 */
	public static String getStringByMemoryParams(String key, Object... arguments) {
		return getStringByMemoryParams(key, false, arguments);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param baseNames
	 *            国际化资源文件地址集合,无序
	 * @param notExistIsNull
	 *            <p>
	 *            true:如果资源文件键不存在,则返回null
	 *            </p>
	 *            <p>
	 *            false:如果资源文件键不存在,则返回资源文件key
	 *            </p>
	 * @param arguments
	 *            资源文件参数
	 * @return 资源文件值
	 */
	public static String getStringBySetParams(String key, Set<String> baseNames, boolean notExistIsNull, Object... arguments) {
		try {
			return getValueByParams(getString(key, baseNames, notExistIsNull), arguments);
		} catch (Exception e) {
			e.printStackTrace();
			return key;
		}
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param baseNames
	 *            国际化资源文件地址集合,无序
	 * @param arguments
	 *            资源文件参数
	 * @return 资源文件值
	 */
	public static String getStringBySetParams(String key, Set<String> baseNames, Object... arguments) {
		return getStringBySetParams(key, baseNames, false, arguments);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param notExistIsNull
	 *            <p>
	 *            true:如果资源文件键不存在,则返回null
	 *            </p>
	 *            <p>
	 *            false:如果资源文件键不存在,则返回资源文件key
	 *            </p>
	 * @param arguments
	 *            资源文件参数
	 * @return 资源文件值
	 */
	public static String getStringByParams(String key, boolean notExistIsNull, Object... arguments) {
		return getStringBySetParams(key, I18N_RESOURCES, notExistIsNull, arguments);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param arguments
	 *            资源文件参数
	 * @return 资源文件值
	 */
	public static String getStringByParams(String key, Object... arguments) {
		return getStringByParams(key, false, arguments);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param action
	 *            action对应的类
	 * @param notExistIsNull
	 *            <p>
	 *            true:如果资源文件键不存在,则返回null
	 *            </p>
	 *            <p>
	 *            false:如果资源文件键不存在,则返回资源文件key
	 *            </p>
	 * @param arguments
	 *            资源文件参数
	 * @return 资源文件值
	 */
	public static String getStringByClassParams(String key, Class<?> action, boolean notExistIsNull, Object... arguments) {
		I18N_RESOURCES.add(action.getName());
		return getStringByParams(key, notExistIsNull, arguments);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param action
	 *            action对应的类
	 * @param arguments
	 *            资源文件参数
	 * @return 资源文件值
	 */
	public static String getStringByClassParams(String key, Class<?> action, Object... arguments) {
		return getStringByClassParams(key, action, false, arguments);
	}

	/**
	 * 配置文件内容取得带参数
	 * 
	 * @param value
	 * @param arguments
	 * @return
	 */
	public static String getValueByParams(String value, Object... arguments) {
		try {
			return MessageFormat.format(value, arguments);
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}

	/**
	 * 通过key获取资源文件值
	 * 
	 * @param key
	 * @return
	 */
	public static String getStringByMemory(String key) {
		String value = null;
		if (key == null || "".equals(key)) {
			// 最好只调用一次
			setStringKeyValuesToMemory();
		} else {
			if ((value = I18N_RESOURCE_KEY_VALUE.get(key)) == null) {
				value = getString(key);
				I18N_RESOURCE_KEY_VALUE.put(key, value);
			}
		}
		return value == null ? key : value;
	}

	/**
	 * 打印debug信息
	 * 
	 * @return
	 */
	public static void debugString() {
		log.debug("国际化资源文件列表如下:");
		for (String s : I18N_RESOURCES) {
			log.debug("I18N_RESOURCES:" + s);
		}
		log.debug("I18N_RESOURCE_KEY_VALUE:" + I18N_RESOURCE_KEY_VALUE.entrySet());
	}

	/**
	 * 设置所有资源文件键值到内存中
	 */
	public static void setStringKeyValuesToMemory() {
		setStringKeyValuesToMemory(I18N_RESOURCES);
	}

	/**
	 * 通过resources设置所有资源文件键值到内存中
	 * 
	 * @param resources
	 * @param locale
	 */
	public static void setStringKeyValuesToMemory(Set<String> resources) {
		try {
			// 先清除缓存
			I18N_RESOURCE_KEY_VALUE.clear();
			ResourceBundle.clearCache();
			if (locale == null) {
				locale = new Locale("zh_CN");
			}
			ResourceBundle rb = null;
			Iterator<String> it = resources.iterator();
			while (it.hasNext()) {
				String resource = it.next();
				try {
					rb = ResourceBundle.getBundle(resource, locale);
					Enumeration<String> keys = rb.getKeys();
					while (keys.hasMoreElements()) {
						String key = keys.nextElement();
						String value = rb.getString(key);
						I18N_RESOURCE_KEY_VALUE.put(key, value);
					}
				} catch (Exception e) {
					log.warn("设置资源文件警告:" + e.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("获取资源值失败:" + e.getMessage());
		}
	}

	/**
	 * 通过key,baseNames获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param baseNames
	 *            国际化资源文件地址集合,无序
	 * @param locale
	 *            特定的地理、政治和文化地区
	 * @param notExistIsNull
	 *            <p>
	 *            true:如果资源文件键不存在,则返回null
	 *            </p>
	 *            <p>
	 *            false:如果资源文件键不存在,则返回资源文件key
	 *            </p>
	 * @return 资源文件值
	 */
	public static String getString(String key, Set<String> baseNames, boolean notExistIsNull) {
		String value = null;
		key = JavaUtil.trim(key);
		try {
			// 清除缓存
			ResourceBundle.clearCache();
			if (locale == null) {
				locale = new Locale("zh_CN");
			}
			ResourceBundle rb = null;
			Iterator<String> it = baseNames.iterator();
			boolean exists = false;
			while (it.hasNext()) {
				String baseName = it.next();
				try {
					rb = ResourceBundle.getBundle(baseName, locale);
					value = rb.getString(key);
				} catch (Exception e) {
					if (notExistIsNull) {
						value = null;
					} else {
						value = key;
					}
				}
				if (value != null && !value.equals(key)) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				if (notExistIsNull) {
					value = null;
				} else {
					value = key;
				}
			}
		} catch (Exception e) {
			log.error("获取资源值失败:" + e.getMessage());
			if (notExistIsNull) {
				value = null;
			} else {
				value = key;
			}
		}
		I18N_RESOURCE_KEY_VALUE.put(key, value);
		return value;
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param baseNames
	 *            国际化资源文件地址集合,无序
	 * @return 资源文件值
	 */
	public static String getString(String key, Set<String> baseNames) {
		return getString(key, baseNames, false);
	}
}
