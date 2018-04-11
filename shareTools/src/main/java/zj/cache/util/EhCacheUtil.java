package zj.cache.util;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import zj.cache.bean.CacheModel;
import zj.check.util.CheckUtil;

/**
 * 缓存工具类
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class EhCacheUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EhCacheUtil.class.getName());
	private static CacheManager cacheManager;
	private static final String DEFAULT_CACHE_FILE = "/ehcache.xml";
	// Md5Util.toMd5("DEFAULT_CACHE_KEY")
	private static final String DEFAULT_CACHE_KEY = "c4afb15a0b0b4ef2e38e2a28c2471119";

	/**
	 * 获取缓存管理器
	 * 
	 * @return 缓存管理器
	 */
	public static synchronized CacheManager getCacheManager() {
		return getCacheManager(DEFAULT_CACHE_FILE);
	}

	/**
	 * 根据ehcache文件路径获取缓存管理器对象
	 * 
	 * @param file
	 *            ehcache文件路径,默认classes下的/ehcache.xml文件
	 * @return 缓存管理器对象
	 */
	public static synchronized CacheManager getCacheManager(String file) {
		if (cacheManager == null) {
			if (file == null || "".equals(file.trim())) {
				file = DEFAULT_CACHE_FILE;
			}
			String configurationFileName = null;
			try {
				// 查找classpath下是否有文件
				URL url = EhCacheUtil.class.getResource(file);
				if (url == null) {
					// 没有找到
					configurationFileName = file;
				} else {
					configurationFileName = url.getPath();
				}
			} catch (Exception e) {
				logger.error("获取缓存配置文件路径失败", e);
			}
			if (configurationFileName == null || "".equals(configurationFileName)) {
				configurationFileName = file;
				logger.warn("获取缓存配置文件路径为空,则默认:" + file);
			}
			if (new File(configurationFileName).isDirectory()) {
				configurationFileName = file;
				logger.warn("获取缓存配置文件路径不能是路径,则默认:" + file);
			}
			logger.debug("缓存配置文件路径:" + configurationFileName);
			try {
				// ehcache_auto_created_1420510852968创建是因为ehcache.xml文件和二级缓存中的文件是同一个文件
				// 日志:Creating a new instance of CacheManager using the diskStorePath
				cacheManager = CacheManager.create(configurationFileName);
			} catch (Exception e) {
				logger.error("创建缓存对象失败,根据[" + file + "]进行创建", e);
				try {
					cacheManager = CacheManager.create(file);
				} catch (CacheException e1) {
					logger.error("根据[" + file + "]创建缓存对象失败", e);
				}
			} finally {
				if (cacheManager == null) {
					// 创建默认的cacheManager
					cacheManager = CacheManager.getInstance();
					logger.warn("创建默认的缓存管理器");
				}
			}
		}
		return cacheManager;
	}

	// Cache构造函数
	// Cache(java.lang.String name, int maxElementsInMemory, boolean
	// overflowToDisk, boolean eternal, long timeToLiveSeconds, long
	// timeToIdleSeconds, boolean diskPersistent, long
	// diskExpiryThreadIntervalSeconds);
	/**
	 * 根据缓存名获取缓存对象
	 * 
	 * @param cacheName
	 *            缓存名
	 * @return 缓存对象
	 */
	public static synchronized Cache getCache(String cacheName) {
		// 1.必须要有的属性：
		// name: cache的名字，用来识别不同的cache，必须惟一。
		// maxElementsInMemory: 内存管理的缓存元素数量最大限值。(内存中存储对象的最大值)
		// maxElementsOnDisk: 硬盘管理的缓存元素数量最大限值。默认值为0，就是没有限制。
		// eternal: 设定元素是否持久话。若设为true，则缓存元素不会过期。
		// overflowToDisk: 设定是否在内存填满的时候把数据转到磁盘上。
		// 2.下面是一些可选属性：
		// timeToIdleSeconds： 设置Element在失效前的允许闲置时间。仅当element不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
		// timeToLiveSeconds: 设置Element在失效前允许存活时间。最大时间介于创建时间和失效时间之间。仅当element不是永久有效时使用，默认是0.，也就是element存活时间无穷大。其他与timeToIdleSeconds类似。
		// diskPersistent: 设定在虚拟机重启时是否进行磁盘存储，默认为false.(我的直觉，对于安全小型应用，宜设为true)。
		// diskExpiryThreadIntervalSeconds: 访问磁盘线程活动时间。
		// diskSpoolBufferSizeMB: 存入磁盘时的缓冲区大小，默认30MB,每个缓存都有自己的缓冲区。
		// memoryStoreEvictionPolicy: 元素逐出缓存规则。共有三种，Recently Used (LRU)最近最少使用，为默认。 First In First Out (FIFO)，先进先出。Less Frequently Used(specified as LFU)最少使用。
		Cache cache = getCacheManager().getCache(cacheName);
		if (cache == null) {
			cache = new Cache(cacheName, 10000, true, true, 0, 0, false, 120);
			getCacheManager().addCache(cache);
		}
		return cache;
	}

	/**
	 * 设置缓存数据
	 * 
	 * @param cacheName
	 *            缓存名
	 * @param key
	 *            缓存键
	 * @param value
	 *            缓存值
	 */
	public static synchronized <T> void put(String cacheName, String key, T value) {
		Cache cache = getCache(cacheName);
		key = getKey(key);
		cache.put(new Element(key, value));
	}

	/**
	 * 设置缓存数据对象
	 * 
	 * @param cacheName
	 *            缓存名
	 * @param value
	 *            缓存值
	 */
	public static synchronized <T> void put(String cacheName, T value) {
		put(cacheName, null, value);
	}

	/**
	 * 设置缓存数据
	 * 
	 * @param cacheName
	 *            缓存名
	 * @param map
	 *            缓存值
	 */
	public static synchronized <T> void put(String cacheName, Map<String, T> map) {
		Cache cache = getCache(cacheName);
		for (String key : map.keySet()) {
			cache.put(new Element(key, map.get(key)));
		}
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param cacheName
	 *            缓存名
	 * @param key
	 *            缓存键
	 * @return 缓存数据
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <T> T get(String cacheName, String key) {
		Cache cache = getCache(cacheName);
		key = getKey(key);
		Element element = cache.get(key);
		if (element == null) {
			return null;
		}
		// 1.2.3版本
		// return (T) element.getValue();
		return (T) element.getObjectValue();
	}

	/**
	 * 获取key
	 * 
	 * @param key
	 * @return
	 */
	private static synchronized String getKey(String key) {
		if (CheckUtil.isNull(key)) {
			key = DEFAULT_CACHE_KEY;
		}
		return key;
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param cacheName
	 *            缓存名
	 * @return 缓存数据
	 */
	public static synchronized <T> T getT(String cacheName) {
		return get(cacheName, null);
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param cacheName
	 *            缓存名
	 * @return 缓存数据
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <T> Map<String, T> get(String cacheName) {
		Map<String, T> map = new HashMap<String, T>();
		Cache cache = getCache(cacheName);
		List<String> list = (List<String>) cache.getKeys();
		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			String key = it.next();
			// 1.2.3版本
			// map.put(key, (T) cache.get(key).getValue());
			map.put(key, (T) cache.get(key).getObjectValue());
		}
		return map;
	}

	/**
	 * 获取所有缓存数据
	 * 
	 * @return 所有缓存数据
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <T> List<CacheModel<T>> getAllCache() {
		List<CacheModel<T>> list = new ArrayList<CacheModel<T>>();
		String[] cacheNames = getCacheManager().getCacheNames();
		// 1.2.3版本
		// if (cacheNames != null) {
		// for (String cacheName : cacheNames) {
		// Cache cache = this.getCache(cacheName);
		// CacheModel<T> cacheModel = new CacheModel<T>();
		// cacheModel.setName(cacheName);
		// cacheModel.setCacheMap(this.get(cacheName));
		// cacheModel.setCacheSize(cache.getSize());
		// cacheModel.setMemoryStoreSize(cache.getMemoryStoreSize());
		// int cacheHits = cache.getStatistics().getCacheHits();
		// cacheModel.setCacheHits(cacheHits);
		// int misses = cache.getStatistics().getCacheMisses();
		// cacheModel.setCacheMisses(misses);
		// list.add(cacheModel);
		// }
		// }
		if (cacheNames != null) {
			for (String cacheName : cacheNames) {
				Cache cache = getCache(cacheName);
				CacheModel<T> cacheModel = new CacheModel<T>();
				cacheModel.setName(cacheName);
				cacheModel.setCacheMap((Map<String, T>) get(cacheName));
				cacheModel.setCacheSize(cache.getSize());
				cacheModel.setMemoryStoreSize(cache.getStatistics().getLocalHeapSize());
				cacheModel.setCacheHits(cache.getStatistics().cacheHitCount());
				cacheModel.setCacheMisses(cache.getStatistics().cacheMissCount());
				list.add(cacheModel);
			}
		}
		return list;
	}

	/**
	 * 移除缓存数据
	 * 
	 * @param cacheName
	 *            缓存名
	 */
	public static synchronized void removeCache(String cacheName) {
		getCacheManager().removeCache(cacheName);
	}

	/**
	 * 移除缓存数据
	 * 
	 * @param cacheName
	 *            缓存名
	 * @param key
	 *            缓存键
	 */
	public static synchronized void remove(String cacheName, String key) {
		Cache cache = getCache(cacheName);
		key = getKey(key);
		cache.remove(key);
	}

	/**
	 * 移除缓存数据
	 * 
	 * @param cacheName
	 *            缓存名
	 */
	public static synchronized void remove(String cacheName) {
		remove(cacheName, null);
	}

	/**
	 * 停止缓存
	 */
	public static synchronized void shutdown() {
		getCacheManager().shutdown();
	}

	/**
	 * 停止所有缓存
	 */
	public static synchronized void removalAll() {
		// 1.2.3版本
		// cacheManager.removalAll();
		getCacheManager().removeAllCaches();
	}

}
