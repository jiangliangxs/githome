package test.ehcache.ehcache2;

import java.io.InputStream;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.management.ManagementService;
import net.sf.ehcache.statistics.StatisticsGateway;

public class EHCacheTest {

	public static void main(String[] args) {
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 读入配置
		InputStream is = EHCacheTest.class.getResourceAsStream("/ehcache2.xml");
		CacheManager cacheManager = new CacheManager(is);
		// 打印初始缓存
		String[] cacheNames = cacheManager.getCacheNames();
		printNames(cacheNames);
		// 注册管理Bean
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ManagementService.registerMBeans(cacheManager, mBeanServer, true, true, true, true);

		// distributed
		Cache cache = cacheManager.getCache("sampleDistributedCache2");
		printCache(cache);
		// 添加值后另一个虚拟机的缓存通过RMI会同步缓存，并读到这个值
		cache.put(new Element("ehcache", "newaddvalue"));
	}

	private static void printNames(String[] names) {
		System.out.println("=======================");
		for (int i = 0; i < names.length; i++) {
			System.out.println(names[i]);
		}
	}

	private static void printCache(Cache cache) {
		int size = cache.getSize();
		long memSize = cache.getStatistics().getLocalHeapSize();
		long diskSize = 0;
		if(cache.isTerracottaClustered()){
			diskSize = cache.getStatistics().getRemoteSize();
		}
        else{
        	diskSize = cache.getStatistics().getLocalDiskSize();
        }
		StatisticsGateway stat = cache.getStatistics();
//		LiveCacheStatistics liveStat = cache.getLiveCacheStatistics();
		long hits = stat.cacheHitCount();
		long missed = stat.cacheMissCount();
		long hitsOnDisk = 0;//stat.getOnDiskHits();
		long liveHits = 0;//liveStat.getCacheHitCount();
		long liveMissed = 0;//liveStat.getCacheMissCount();

		StringBuilder sb = new StringBuilder();
		sb.append("size=" + size + ";memsize=" + memSize);
		sb.append(";diskSize=" + diskSize + ";hits=" + hits);
		sb.append(";missed=" + missed + ";liveHits=" + liveHits);
		sb.append(";liveMissed=" + liveMissed + ";hitsOnDisk=" + hitsOnDisk);
		System.out.println(sb.toString());
	}
}
