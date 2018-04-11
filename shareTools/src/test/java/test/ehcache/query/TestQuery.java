package test.ehcache.query;

import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.config.Searchable;
import net.sf.ehcache.search.Attribute;

import org.junit.Test;

import zj.cache.util.EhCacheUtil;

public class TestQuery {
	@Test
	public void m1() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a1", "va1");
		map.put("a2", "va2");
		EhCacheUtil.put("a", map);
		Map<String, Object> mapNew = EhCacheUtil.get("a");
		System.out.println(mapNew.keySet());
		User user = new User();
		user.setName("msth");
		EhCacheUtil.put("userCache", "abc", user);
		Cache cache = EhCacheUtil.getCache("userCache");
		System.out.println("userCache:" + cache);
		Attribute<User> userAttr = cache.getSearchAttribute("user");
		System.out.println("userAttr:" + userAttr.ge(user));
	}

	@Test
	public void test() {
		CacheManager cacheManager = CacheManager.create();
		CacheConfiguration cacheConfig = new CacheConfiguration();
		cacheConfig.name("cache1").maxBytesLocalHeap(100, MemoryUnit.MEGABYTES);
		Searchable searchable = new Searchable();
		// 指定Cache的Searchable对象。
		cacheConfig.searchable(searchable);
		// 如下指定也行
		// cacheConfig.addSearchable(searchable);
		Cache cache1 = new Cache(cacheConfig);
		cacheManager.addCache(cache1);
	}
}
