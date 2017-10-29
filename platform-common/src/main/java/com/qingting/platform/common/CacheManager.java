package com.qingting.platform.common;

import java.util.Timer;
import java.util.TimerTask;

public abstract class CacheManager {
	// 有效期，单位为秒，默认30分钟
	protected int cacheTimeout = 1800;

	private final Timer timer = new Timer(true);

	// 每分钟执行一次
	public CacheManager() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				verifyExpired();
			}
		}, 60 * 1000, 60 * 1000);
	}

	public void setCacheTimeout(int cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
	}

	/**
	 * 验证失效
	 */
	public abstract void verifyExpired();

	/**
	 * 存入
	 * @param key
	 * @param value
	 */
	public abstract void addCache(String key, Object value);
	/**
	 * 存入
	 * @param key
	 * @param value
	 * @param cacheTimeout
	 */
	public abstract void addCache(String key, Object value,int cacheTimeout);
	
	/**
	 * 验证有效性,有效则延长session生命周期
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object validate(String key);

	/**
	 * 移除缓存
	 * 
	 * @param key
	 */
	public abstract void remove(String key);
	/**
	 * 获取
	 * @param key
	 * @return Object
	 */
	public abstract Object get(String key);
}
