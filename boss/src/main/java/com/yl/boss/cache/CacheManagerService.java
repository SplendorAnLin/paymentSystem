package com.yl.boss.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.boss.Constant;
import com.yl.boss.dao.DictionaryDao;
import com.yl.boss.dao.DictionaryTypeDao;
import com.yl.boss.entity.Dictionary;
import com.yl.boss.entity.DictionaryType;

/**
 *  缓存管理业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月1日
 * @version V1.0.0
 */
public class CacheManagerService{	
	private static final Log log=LogFactory.getLog(CacheManagerService.class);
	private CacheService cacheService; 
	private DictionaryTypeDao dictionaryTypeDao;
	private DictionaryDao dictionaryDao;
	public CacheManagerService() {
		super();		
	}
	
	public void initCache(){
		List<DictionaryType> dictTypes = dictionaryTypeDao.findAll();
		Map<String,List<Dictionary>> allDicts = new HashMap<String,List<Dictionary>>();
		List<Dictionary> dicts ;
		for(DictionaryType dictType:dictTypes){
			dicts = dictionaryDao.findByDictionaryTypeId(dictType.getDictTypeId());
			allDicts.put(dictType.getDictTypeId(), dicts);
		}
		cacheService.put(Constant.DICT_REGION, Constant.CACHE_DICTS, allDicts);
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

	public DictionaryTypeDao getDictionaryTypeDao() {
		return dictionaryTypeDao;
	}

	public void setDictionaryTypeDao(DictionaryTypeDao dictionaryTypeDao) {
		this.dictionaryTypeDao = dictionaryTypeDao;
	}
	
}	
