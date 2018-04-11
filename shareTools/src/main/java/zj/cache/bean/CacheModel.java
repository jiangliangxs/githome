package zj.cache.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Cache封装类
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class CacheModel<T> implements Serializable {

	private static final long serialVersionUID = 1l;
	private String name;
	private Map<String, T> cacheMap;
	private int cacheSize;
	private long memoryStoreSize;
	private long cacheHits;
	private long cacheMisses;

	/**
	 * 获取缓存键名
	 * 
	 * @return 缓存键名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置缓存名字
	 * 
	 * @param name
	 *            缓存名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取缓存中所有对象
	 * 
	 * @return 缓存中所有对象
	 */
	public Map<String, T> getCacheMap() {
		return cacheMap;
	}

	/**
	 * 设置缓存中所有对象
	 * 
	 * @param cacheMap
	 *            缓存中所有对象
	 */
	public void setCacheMap(Map<String, T> cacheMap) {
		this.cacheMap = cacheMap;
	}

	/**
	 * 获取缓存中对象数
	 * 
	 * @return 缓存中对象数
	 */
	public int getCacheSize() {
		return cacheSize;
	}

	/**
	 * 设置缓存中对象数
	 * 
	 * @param cacheSize
	 *            缓存对象数
	 */
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	/**
	 * 获取缓存读取的命中次数
	 * 
	 * @return 缓存读取的命中次数
	 */
	public long getCacheHits() {
		return cacheHits;
	}

	/**
	 * 设置缓存读取的命中次数
	 * 
	 * @param cacheHits
	 *            缓存读取的命中次数
	 */
	public void setCacheHits(long cacheHits) {
		this.cacheHits = cacheHits;
	}

	/**
	 * 获取缓存读取的错失次数
	 * 
	 * @return 缓存读取的错失次数
	 */
	public long getCacheMisses() {
		return cacheMisses;
	}

	/**
	 * 设置缓存读取的错失次数
	 * 
	 * @param cacheMisses
	 *            缓存读取的错失次数
	 */
	public void setCacheMisses(long cacheMisses) {
		this.cacheMisses = cacheMisses;
	}

	/**
	 * 获取缓存对象占用内存的大小
	 * 
	 * @return 缓存对象占用内存的大小
	 */
	public long getMemoryStoreSize() {
		return memoryStoreSize;
	}

	/**
	 * 设置缓存对象占用内存的大小
	 * 
	 * @param memoryStoreSize
	 *            缓存对象占用内存的大小
	 */
	public void setMemoryStoreSize(long memoryStoreSize) {
		this.memoryStoreSize = memoryStoreSize;
	}

}
