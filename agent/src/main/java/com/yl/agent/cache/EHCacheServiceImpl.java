package com.yl.agent.cache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.agent.Constant;

/**
 * 服务实现 
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月1日
 * @version V1.0.0
 */
public class EHCacheServiceImpl implements CacheService {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(EHCacheServiceImpl.class);
	
	private CacheManager cacheManager;

    public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

    public  Object get(Object key) throws CacheException {
        return get(Constant.DEFAULT_REGION, key);
    }

    public Object get(String regionName, Object key) throws CacheException {
        return getCache(regionName).get(key).getValue();
    }
   
    public  void put(Object key, Object value) throws CacheException {
        put(Constant.DEFAULT_REGION, key, value);
    }

    public  void put(String regionName, Object key, Object value) throws
        CacheException {
    	Element element = new Element(key, (Serializable) value);
        getCache(regionName).put(element);
    }
    
    /**
     * getCache
     *
     * @param regionName String
     * @throws CacheException
     * @return Cache
     */
    protected  Cache getCache(String regionName) throws CacheException {
    	 Cache cache = cacheManager.getCache(regionName);
         if(cache == null){
             logger.warn("Could not find configuration for " + regionName + ". Configuring using the defaultCache settings.");
             cacheManager.addCache(regionName);
             cache = cacheManager.getCache(regionName);
         }
         return cache;
    }

	public void remove(Object key) throws CacheException {
		remove(Constant.DEFAULT_REGION,key);
	}

	public void remove(String regionName, Object key) throws CacheException {
		getCache(regionName).remove(key);
	}
}
