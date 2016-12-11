package com.materialize.jee.platform.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CacheUtils {
	public static final Log logger = LogFactory.getLog(CacheUtils.class);
	public static final String EHCACHE_DATADIC_CONF_NAME = "DATADIC_CACHE";
	public static final String EHCACHE_HBASE_CONF_NAME = "HBASE_CACHE";
	public static final String EHCACHE_SESSION_CONF_NAME = "SESSION_CACHE";
	public static final String EHCACHE_USER_LOGIN_NAME = "USER_LOGIN_SESSIONS_CACHE";
	public static final String CACHE_DATADIC_NAME_PIX = "DATADIC_";
	public static final String CACHE_HBASE_NAME_PIX = "HBASE_";
	
	private static EhCacheCache getCache(String name){
		EhCacheCacheManager manager = (EhCacheCacheManager)ContextUtils.getBean("cacheManager");
		return (EhCacheCache)manager.getCache(name);
	}
	
	/**
	 * 从缓存中获取
	 * 
	 */
	public static Object getCacheInfo(String cacheName, String cacheKey){
		Object result = null;
		try{
			Cache cache = getCache(cacheName);
			result = cache.get(cacheKey, Object.class);
		}catch(Exception e){
			logger.info("获取缓存["+cacheName+","+cacheKey+"]失败");
		}
		return result;
	}
	
	/**
	 * 从缓存中获取
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static List<Object> getAllCacheInfo(String cacheName,Boolean includeExpired){
		List<Object> result = new ArrayList<Object>();
		try{
			EhCacheCache cache = getCache(cacheName);
			List keys = null;
			if(includeExpired){
				keys = cache.getNativeCache().getKeys();
			}else{
				keys = cache.getNativeCache().getKeysWithExpiryCheck();
			}
			for(int i=0;keys!=null && i<keys.size();i++){
				result.add(cache.getNativeCache().get(keys.get(i)));
			}
		}catch(Exception e){
			logger.info("获取缓存["+cacheName+"]失败");
		}
		return result;
	}

	/**
	 * 放入缓存
	 * 
	 * @param cacheName
	 * @param cacheKey
	 * @param cacheObj
	 * @throws Exception
	 */
	public static void putCacheInfo(String cacheName, String cacheKey, Object cacheObj) {
		try{
			Cache cache = getCache(cacheName);
			cache.put(cacheKey, cacheObj);
		}catch(Exception e){
			logger.info("放入缓存["+cacheName+","+cacheKey+"]失败");
		}
	}

	/**
	 * 删除缓存
	 * 
	 * @param cacheName
	 * @param cacheKey
	 * @throws Exception
	 */
	public static void deleteCacheInfo(String cacheName, String cacheKey) {
		try{
			Cache cache = getCache(cacheName);
			Object obj = cache.get(cacheKey, Object.class);
			if(obj!=null){
				cache.evict(cacheKey);
			}
		}catch(Exception e){
			logger.info("删除缓存["+cacheName+","+cacheKey+"]失败");
		}
	}
	
	@Test
	public void test() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-context-ehcache.xml");
		ContextUtils.setContext(context);
		CacheUtils.putCacheInfo("DATADIC_CACHE", "123", 123);
		System.out.println(CacheUtils.getCacheInfo("DATADIC_CACHE", "123"));
		CacheUtils.putCacheInfo("DATADIC_CACHE", "1234", 1234);
		System.out.println(CacheUtils.getCacheInfo("DATADIC_CACHE", "1234"));
		CacheUtils.deleteCacheInfo("DATADIC_CACHE", "1234");
		List<Object> all = CacheUtils.getAllCacheInfo("DATADIC_CACHE", true); 
		for(Object o:all){
			System.out.println(o);
		}
	}
}
