package test.ehcache.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;

import org.junit.Test;

import test.ehcache.bean.TestCacheBean;
import zj.cache.util.EhCacheUtil;

public class TestCacheUtil {
	@Test
	public void 测试缓存任何() {
		System.out.println(System.getProperty("java.io.tmpdir"));
		List<TestCacheBean> beans = new ArrayList<TestCacheBean>();
		TestCacheBean bean = new TestCacheBean();
		beans.add(bean);
		bean.i = 100;
		bean.s = "我是谁啊";
		EhCacheUtil.put("reportHold", beans);
		bean.i = 200;
		bean.s = "张军";
		List<TestCacheBean> beansNew = EhCacheUtil.getT("reportHold");
		System.out.println(beansNew.size());
		System.out.println(beansNew.get(0).s + "-" + beansNew.get(0).i);
		System.out.println("---------------");
		List<TestCacheBean> beansTemp = new ArrayList<TestCacheBean>();
		beansTemp.addAll(beansNew);
		beansTemp.remove(0);
		System.out.println("beansNew.size():" + beansNew.size());
		//下面这样移除会有问题
		beansNew.remove(0);
		try {
			beansNew = EhCacheUtil.getT("reportHold");
			System.out.println(beansNew.size());
			System.out.println(beansNew.get(0).s + "-" + beansNew.get(0).i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("---------------");
		// EhCacheUtil.removeCache("reportHold");
		// try {
		// beansNew = EhCacheUtil.getT("reportHold");
		// System.out.println(beansNew.size());
		// System.out.println(beansNew.get(0).s + "-" + beansNew.get(0).i);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Test
	public void 测试缓存对象() {
		TestCacheBean bean = new TestCacheBean();
		bean.i = 100;
		bean.s = "我是谁啊";
		EhCacheUtil.put("cacheBean", "bean", bean);
		bean.i = 200;
		bean.s = "张军";
		TestCacheBean beanNew = EhCacheUtil.get("cacheBean", "bean");
		System.out.println(beanNew.s + "-" + beanNew.i);
	}

	@Test
	public void cache3() {
		try {
			System.out.println("开始计时");
			EhCacheUtil.put("userCache", "a1", "A1");
			EhCacheUtil.put("userCache", "a2", "A2");
			EhCacheUtil.put("userCache", "a3", "A3");
			System.out.println("userCache1:" + EhCacheUtil.get("userCache", "a1"));
			System.out.println("userCache2:" + EhCacheUtil.get("userCache", "a2"));
			System.out.println("userCache3:" + EhCacheUtil.get("userCache", "a3"));
			Thread.sleep(8000);
			System.out.println("时间已到");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("userCache:" + EhCacheUtil.get("userCache", "a3"));
	}

	@Test
	public void cache2() {
		Map<String, Map<String, String>> mm = new HashMap<String, Map<String, String>>();
		Map<String, String> m = new HashMap<String, String>();
		mm.put("m", m);
		m.put("a", "a1");
		EhCacheUtil.put("aa", mm);
		System.out.println(EhCacheUtil.get("aa"));
		System.out.println(EhCacheUtil.get("aa", "m"));
		Map<String, String> m2 = new HashMap<String, String>();
		m2.put("b", "ddd");
		EhCacheUtil.put("aa", "m", m2);
		System.out.println(EhCacheUtil.get("aa"));

	}

	@Test
	public void cache() {
		// String xml = "/ehcache.xml";
		// String configurationFileName = EhCacheUtil.class.getResource(xml).getPath();
		// System.out.println("configurationFileName:" + configurationFileName);
		Map<String, String> m = new HashMap<String, String>();
		m.put("aa", "a11");
		EhCacheUtil.put("a", m);
		System.out.println(EhCacheUtil.get("a"));
		EhCacheUtil.put("a", "aa", "888");
		System.out.println(EhCacheUtil.get("a"));
		Map<String, Integer> m2 = new HashMap<String, Integer>();
		m2.put("aa", 22);
		EhCacheUtil.removeCache("a");
		Map<String, Object> ma = EhCacheUtil.get("a", "baa");
		System.out.println(ma);
		EhCacheUtil.put("a", m2);
		ma = EhCacheUtil.get("a", "ccc");
		System.out.println(ma);
		System.out.println(EhCacheUtil.get("a"));
	}

	@Test
	public void cacheUrl() {
		CacheManager c = EhCacheUtil.getCacheManager("E:/bigfile/services/java/cluster/cache-files/8990/preehcache.xml");
		System.out.println("CacheManager:" + c);
	}
}
