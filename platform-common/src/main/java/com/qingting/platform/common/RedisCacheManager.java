package com.qingting.platform.common;

import javax.annotation.Resource;

import com.qingting.platform.cache.RedisCache;

public class RedisCacheManager extends CacheManager {
	
	/**
	 * 是否需要扩展token过期时间
	 */
	private volatile boolean isNeedExtendExpired = false;

	@Resource
	private RedisCache<Object> redisCache;
	
	@Override
	public void verifyExpired() {
		isNeedExtendExpired = true;
	}

	@Override
	public void addCache(String key, Object value) {
		redisCache.set(key, value, cacheTimeout * 1000);
	}

	@Override
	public Object validate(String key) {
		Object obj = redisCache.get(key);
		if (obj != null && isNeedExtendExpired) {
			isNeedExtendExpired = false;
			addCache(key, obj);
		}
		return obj;
	}

	@Override
	public void remove(String key) {
		redisCache.delete(key);
	}

	@Override
	public void addCache(String key, Object value, int cacheTimeout) {
		redisCache.set(key, value, cacheTimeout * 1000);
	}

	@Override
	public Object get(String key) {
		return redisCache.get(key);
	}

}
