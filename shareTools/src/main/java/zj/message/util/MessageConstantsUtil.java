package zj.message.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import zj.check.util.CheckUtil;
import zj.java.util.JavaUtil;

/**
 * 常量资源文件工具类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class MessageConstantsUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(MessageConstantsUtil.class);
	public static String AJAX_SUCCESS;
	public static String AJAX_FAIL;
	public static String AJAX_MSG;
	/** 资源键值对 **/
	public static final Map<String, String> CONSTANT_KEY_VALUE = Collections.synchronizedMap(new HashMap<String,String>());
	/** 资源文件地址集合,无序 **/
	public static Set<String> CONFIGS = new HashSet<String>();
	/** 国际化资源文件/资源文件内容初使化 **/
	static {
		AJAX_SUCCESS = "success";
		AJAX_FAIL = "fail";
		AJAX_MSG = "msg";
		loadConfig(null);
		setConstantKeyValueToMemory();
	}

	// 资源文件
	/**
	 * 设置资源文件路径
	 * 
	 * @param configFile
	 *            <p>
	 *            ./constant.properties
	 *            </p>
	 *            <p>
	 *            ./systemConfig.properties
	 *            </p>
	 *            <p>
	 *            ./app.properties
	 *            </p>
	 * @return 资源文件值
	 */
	public static void loadConfig(String configFile) {
		// 默认注册资源文件地址
		CONFIGS.add("./constant.properties");
		CONFIGS.add("./systemConfig.properties");
		CONFIGS.add("./app.properties");
		// 添加新的资源文件地址
		String[] configs = JavaUtil.split(configFile, ",");
		for (String s : configs) {
			if (CheckUtil.isNull(s))
				continue;
			CONFIGS.add(s);
		}
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
	public static String getConstantValue(String key, boolean notExistIsNull) {
		String value = null;
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
		CONSTANT_KEY_VALUE.put(key, value);
		return value;
	}

	/**
	 * 获取资源文件值
	 * 
	 * @param key
	 *            资源文件key
	 * @return 资源文件值
	 */
	public static String getConstantValue(String key) {
		return getConstantValue(key, false);
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
	public static String getConstantValueByParams(String key, boolean notExistIsNull, Object... arguments) {
		return getValueByParams(getConstantValue(key, notExistIsNull), arguments);
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
	public static String getConstantValueByParams(String key, Object... arguments) {
		return getConstantValueByParams(key, false, arguments);
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
		if (CheckUtil.isNull(key)) {
			// 先清除缓存
			CONSTANT_KEY_VALUE.clear();
			// 最好只调用一次
			CONSTANT_KEY_VALUE.putAll(ConfigUtil.getConstantKeyValues(paths));
			log.debug("加载所有常量数据至缓存成功");
		} else {
			if ((value = CONSTANT_KEY_VALUE.get(key)) == null) {
				value = getConstantValue(key, notExistIsNull);
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
	 * 打印debug信息
	 * 
	 * @return
	 */
	public static void debugString() {
		log.debug("资源文件列表如下:");
		for (String s : CONFIGS) {
			log.debug("CONFIGS:" + s);
		}
		log.debug("CONSTANT_KEY_VALUE:" + CONSTANT_KEY_VALUE.entrySet());
	}

}
