package com.yl.boss.cache;

/**
 * Cache 服务接口 
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public interface CacheService {

	/**
	 * 从default缓存中取得对象
	 *
	 * @param key Object
	 * @throws CacheException
	 * @return Object
	 */
	public  Object get(Object key) throws CacheException;

	/**
	 * 从指定的缓存区中取得对象
	 *
	 * @param regionName String
	 * @param key Object
	 * @throws CacheException
	 * @return Object
	 */
	public  Object get(String regionName, Object key)
			throws CacheException;

	/**
	 * 向default缓存区存放对象
	 *
	 * @param key Object
	 * @param value Object
	 * @throws CacheException
	 */
	public  void put(Object key, Object value) throws CacheException;

	/**
	 * 向指定的缓存区（regionName）中存放对象
	 *
	 * @param regionName String
	 * @param key Object
	 * @param value Object
	 */
	public  void put(String regionName, Object key, Object value)
			throws CacheException;
	/**
	 * 从default缓存区移除对象
	 * @param regionName
	 * @param key
	 */
	public void remove(Object key) throws CacheException;
	
	/**
	 * 从指定的缓存区移除对象
	 * @param regionName
	 * @param key
	 * @throws CacheException
	 */
	public void remove(String regionName,Object key) throws CacheException;
	
	

}
