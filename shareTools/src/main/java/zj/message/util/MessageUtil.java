package zj.message.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import zj.java.util.JavaUtil;

/**
 * 资源文件工具类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 * @see zj.message.util.MessageConstantsUtil
 * @see zj.message.util.MessageI18nUtil
 */
@Deprecated
public class MessageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(MessageUtil.class);
	public static String AJAX_SUCCESS;
	public static String AJAX_FAIL;
	public static String AJAX_MSG;
	/** 是否重新获取配置文件值 **/
	public static boolean RELOAD_READ;
	/** 资源键值对 **/
	public static final Map<String, String> CONSTANT_KEY_VALUE = new ConcurrentHashMap<String, String>();
	/** 国际化资源文件键值对 **/
	public static final Map<String, String> I18N_RESOURCE_KEY_VALUE = new ConcurrentHashMap<String, String>();
	/** 资源键值对临时集合 **/
	public static final Map<String, String> TEMP_CONSTANT_KEY_VALUE = new ConcurrentHashMap<String, String>();
	/** 国际化资源文件临时集合 **/
	public static final Map<String, String> TEMP_I18N_RESOURCE_KEY_VALUE = new ConcurrentHashMap<String, String>();
	/** 国际化资源文件名以,隔开 */
	private static String I18N_RESOURCE;
	/** 国际化资源文件地址集合,无序 **/
	public static Set<String> I18N_RESOURCES = new HashSet<String>();
	/** 资源文件地址 */
	private static String CONFIG;
	/** 资源文件地址集合,无序 **/
	public static Set<String> CONFIGS = new HashSet<String>();
	/** 资源文件地址 */
	private static final String I18N_RESOURCE_KEY = "cons.global.i18n.resouces";
	/** 资源文件地址 */
	private static final String CONFIG_KEY = "cons.global.config";
	/** 国际化资源文件/资源文件内容初使化 **/
	static {
		AJAX_SUCCESS = "success";
		AJAX_FAIL = "fail";
		AJAX_MSG = "msg";
		// 默认注册资源文件地址
		CONFIGS.add("./constant.properties");
		CONFIGS.add("./systemConfig.properties");
		CONFIGS.add("./app.properties");
		I18N_RESOURCES.add("i18n.page");
		I18N_RESOURCES.add("i18n.info");
		I18N_RESOURCES.add("i18n.error");

		I18N_RESOURCES.add("i18n.app.page");
		I18N_RESOURCES.add("i18n.app.info");
		I18N_RESOURCES.add("i18n.app.error");

		// 添加新的国际化资源文件地址
		I18N_RESOURCE = getConstant(I18N_RESOURCE_KEY, true);
		String[] i18n_resources = JavaUtil.split(I18N_RESOURCE, ",");
		for (String s : i18n_resources) {
			if (s == null || s.equals("") || s.equals(I18N_RESOURCE_KEY))
				continue;
			I18N_RESOURCES.add(s);
		}
		// 添加新的资源文件地址
		CONFIG = getConstant(CONFIG_KEY, true);
		String[] configs = JavaUtil.split(CONFIG, ",");
		for (String s : configs) {
			if (s == null || s.equals("") || s.equals(CONFIG_KEY))
				continue;
			CONFIGS.add(s);
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

	// 资源文件
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
	public static String getConstant(String key, boolean notExistIsNull) {
		String value = null;
		if (RELOAD_READ || (value = TEMP_CONSTANT_KEY_VALUE.get(key)) == null) {
			boolean exists = false;
			for (String path : CONFIGS) {
				value = ConfigUtil.getConfig(path, key);
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
			if (value != null) {
				TEMP_CONSTANT_KEY_VALUE.put(key, value);
			}
		} else {

		}
		return value;
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @return 资源文件值
	 */
	public static String getConstant(String key) {
		return getConstant(key, false);
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
	public static String getConstantByParams(String key, boolean notExistIsNull, Object... arguments) {
		return getValueByParams(getConstant(key, notExistIsNull), arguments);
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
	public static String getConstantByParams(String key, Object... arguments) {
		return getConstantByParams(key, false, arguments);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param paths
	 *            资源文件路径集合
	 * @param notExistIsNull
	 *            <p>
	 *            true:如果资源文件键不存在,则返回null
	 *            </p>
	 *            <p>
	 *            false:如果资源文件键不存在,则返回资源文件key
	 *            </p>
	 * @return 资源文件值
	 */
	public static String getConstantValueByMemory(String key, Set<String> paths, boolean notExistIsNull) {
		String value = null;
		if (key == null || "".equals(key)) {
			// 先清除缓存
			CONSTANT_KEY_VALUE.clear();
			TEMP_CONSTANT_KEY_VALUE.clear();
			// 最好只调用一次
			CONSTANT_KEY_VALUE.putAll(ConfigUtil.getConstantKeyValues(paths));
		} else {
			if (RELOAD_READ || (value = CONSTANT_KEY_VALUE.get(key)) == null) {
				value = getConstant(key, notExistIsNull);
				CONSTANT_KEY_VALUE.put(key, value);
			}
		}
		return value;
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @param paths
	 *            资源文件路径集合
	 * @return 资源文件值
	 */
	public static String getConstantValueByMemory(String key, Set<String> paths) {
		return getConstantValueByMemory(key, paths, false);
	}

	/**
	 * 设置所有资源文件键值到内存中
	 * 
	 * @param config
	 */
	public static void setConstantKeyValueToMemory(Set<String> config) {
		getConstantValueByMemory(null, CONFIGS);
	}

	/**
	 * 设置所有资源键值
	 */
	public static void setConstantKeyValueToMemory() {
		setConstantKeyValueToMemory(CONFIGS);
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
	 * @return 资源文件值
	 */
	public static String getConstantValueByMemory(String key, boolean notExistIsNull) {
		return getConstantValueByMemory(key, CONFIGS, notExistIsNull);
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @return 资源文件值
	 */
	public static String getConstantValueByMemory(String key) {
		return getConstantValueByMemory(key, false);
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
	public static String getConstantValueByMemoryParams(String key, boolean notExistIsNull, Object... arguments) {
		return getValueByParams(getConstantValueByMemory(key, notExistIsNull), arguments);
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
	public static String getConstantValueByMemoryParams(String key, Object... arguments) {
		return getConstantValueByMemoryParams(key, false, arguments);
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
			if (RELOAD_READ || (value = I18N_RESOURCE_KEY_VALUE.get(key)) == null) {
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
		log.debug("资源文件列表如下:");
		for (String s : CONFIGS) {
			log.debug("CONFIGS:" + s);
		}
		log.debug("CONSTANT_KEY_VALUE:" + CONSTANT_KEY_VALUE.entrySet());
		log.debug("I18N_RESOURCE_KEY_VALUE:" + I18N_RESOURCE_KEY_VALUE.entrySet());
		log.debug("TEMP_CONSTANT_KEY_VALUE:" + TEMP_CONSTANT_KEY_VALUE.entrySet());
		log.debug("TEMP_I18N_RESOURCE_KEY_VALUE:" + TEMP_I18N_RESOURCE_KEY_VALUE.entrySet());
	}

	/**
	 * 通过resources设置所有资源文件键值到内存中
	 * 
	 * @param resources
	 */
	public static void setStringKeyValuesToMemory(Set<String> resources) {
		setStringKeyValuesToMemory(resources, Locale.getDefault());
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
	public static void setStringKeyValuesToMemory(Set<String> resources, Locale locale) {
		try {
			// 先清除缓存
			I18N_RESOURCE_KEY_VALUE.clear();
			TEMP_I18N_RESOURCE_KEY_VALUE.clear();
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
	public static String getString(String key, Set<String> baseNames, Locale locale, boolean notExistIsNull) {
		String value = null;
		key = JavaUtil.trim(key);
		if (RELOAD_READ || (value = TEMP_I18N_RESOURCE_KEY_VALUE.get(key)) == null) {
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
			TEMP_I18N_RESOURCE_KEY_VALUE.put(key, value);
		} else {
		}
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
		return getString(key, baseNames, Locale.getDefault(), false);
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
	 * @return 资源文件值
	 */
	public static String getString(String key, Set<String> baseNames, Locale locale) {
		return getString(key, baseNames, locale, false);
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
	 * @return 资源文件值
	 */
	public static String getString(String key, Set<String> baseNames, boolean notExistIsNull) {
		return getString(key, baseNames, Locale.getDefault(), notExistIsNull);
	}

}
