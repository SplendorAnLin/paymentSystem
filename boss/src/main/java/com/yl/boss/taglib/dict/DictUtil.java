package com.yl.boss.taglib.dict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.*;
import com.yl.boss.Constant;
import com.yl.boss.cache.CacheService;
import com.yl.boss.dao.DictionaryDao;
import com.yl.boss.dao.DictionaryTypeDao;
import com.yl.boss.entity.Dictionary;
import com.yl.boss.entity.DictionaryType;

public class DictUtil {
	
	private static DictionaryDao dictionaryDao ;
	private static DictionaryTypeDao dictionaryTypeDao ;
	private static CacheService cacheService;
	private static Log log = LogFactory.getLog(DictUtil.class);
	public static List<String> filterSymbols = new ArrayList<String>();
	
	static{
		filterSymbols.add(">");
		filterSymbols.add("<");
		filterSymbols.add("=");
		filterSymbols.add(">=");
		filterSymbols.add(">=");
		filterSymbols.add("!=");
	}
	
	/**
	 * 根据字典类型标识，查询所有字典集合
	 * @param dictTypeId
	 * @return List()
	 */
	public static List<Dictionary> getAllDictionary(String dictTypeId) {
		return dictionaryDao.findByDictionaryTypeId(dictTypeId) ;
	}
	public static Map<String,List<Dictionary>> getAllDicts(){
		Map<String,List<Dictionary>> allDicts = (Map<String,List<Dictionary>>)cacheService.get(Constant.DICT_REGION,Constant.CACHE_DICTS);
		if(allDicts.size() == 0)
			return null;
		else
			return allDicts;
	}

	public static List<Dictionary> getDictsByDictTypeId(String dictTypeId){
		Map<String,List<Dictionary>> allDicts = getAllDicts();
		if(allDicts.containsKey(dictTypeId)){
			return allDicts.get(dictTypeId);
		}else{
			return null;
		}
	}
	
	public static List<Dictionary> getDictsByDictTypeIdAndFilterExpre(String dictTypeId,String filterExpr,String filterVal){
		Map<String,List<Dictionary>> allDicts = getAllDicts();
		List<Dictionary> tempRes ;
		List<Dictionary> res = new ArrayList<Dictionary>();
		if(allDicts.containsKey(dictTypeId)){
			tempRes = allDicts.get(dictTypeId);
		}else{
			log.info("map in cache doesn't contain "+dictTypeId);
			return null;
		}
		if(tempRes != null){
			for(Dictionary dict:tempRes){
				if(StringUtil.notNull(dict.getPrivilege()) && comparePrivilege(dict.getPrivilege(),filterVal,filterExpr)){
					res.add(dict);
				}
			}
		}
		return res;
	}
	
	public static boolean comparePrivilege(String source,String tar,String symbol){
		Integer sour = Integer.parseInt(source);
		Integer target = Integer.parseInt(tar);
		if(">".equals(symbol)){
			if(sour>target){
				return true;
			}else{
				return false;
			}
		}else if("<".equals(symbol)){
			if(sour<target){
				return true;
			}else{
				return false;
			}
		}else if("=".equals(symbol)){
			if(sour == target){
				return true;
			}else{
				return false;
			}
			
		}else if(">=".equals(symbol)){
			if(sour >= target){
				return true;
			}else{
				return false;
			}
		}else if("<=".equals(symbol)){
			if(sour <= target){
				return true;
			}else{
				return false;
			}
			
		}else if("!=".equals(symbol)){
			if(sour != target){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public static List<Dictionary> reverseByDisplayOrder(List<Dictionary> dicts){
		Comparator comp = new Comparator(){
	          public int compare(Object o1,Object o2) {
	              Dictionary d1 = (Dictionary) o1;
	              Dictionary d2 = (Dictionary) o2;
	              Long v1 = d1.getDisplayOrder();
	              Long v2 = d2.getDisplayOrder();
	              if(v1<v2)
	            	  return 1;
	              else
	            	  return 0;
	             }
	        };
	    Collections.sort(dicts,comp);
	    return dicts;
	}
	/**
	 * 根据字典类型标识，字典标识，查询所有字典
	 * @param dictTypeId
	 * @return List
	 */
	public static Dictionary getDictionary(String dictTypeId, String dictId) {
		return dictionaryDao.findByDictionaryTypeIdAndDictionaryId(dictTypeId, dictId);
	}
	
	/**
	 * 根据字典类型标识，字典标识，查询所有字典名称
	 * @param dictTypeId
	 * @param dictId
	 * @return String
	 */
	public static String getDictName(String dictTypeId, String dictId) {
		Dictionary dictionary = dictionaryDao.findByDictionaryTypeIdAndDictionaryId(dictTypeId, dictId);
		if(dictionary == null){
			return null;
		}
		return dictionary.getDictName() ;
	}	
	
	/**
	 * 根据字典类型标识，字典标识，查询所有字典名称
	 * @param dictTypeId
	 * @param dictId
	 * @param defaultValue
	 * @return String
	 */
	public static String getDictName(String dictTypeId, String dictId, String defaultValue) {
		String dictName = getDictName(dictTypeId,dictId) ;
		if( dictName==null ){
			dictName = defaultValue ;
		}
		return dictName;
	}
		
	/**
	 * 根据字典类型标识，字典名称，查询所有字典标识
	 * @param dictTypeId
	 * @param dictName 
	 * @return String
	 */
	public static String getDictId(String dictTypeId, String dictName){
		Dictionary dictionary = dictionaryDao.findByDictionaryTypeIdAndDictionaryName(dictTypeId, dictName);
		if(dictionary == null){
			return null;
		}
		return dictionary.getDictId();
	}
	
	/**
	 * 根据字典类型标识，字典名称，查询所有字典标识
	 * @param dictTypeId
	 * @param dictName 
	 * @param defaultValue
	 * @return String
	 */
	public static String getDictId(String dictTypeId, String dictName, String defaultValue) {
		String dictId=getDictId(dictTypeId,dictName) ;
		if(dictName==null){
			dictId=defaultValue ;
		}
		return dictId;
	}
	/**
	 * 根据字典类型标识，获取字典类型名称
	 * @param dictTypeId
	 * @return
	 */
	public static String getDictTypeName(String dictTypeId) {
		DictionaryType dictionaryType = dictionaryTypeDao.findById(dictTypeId);
		if( dictionaryType==null ){
			return "" ;
		}else{
			return dictionaryType.getDictTypeName() ;
		}
	}
	
	public static DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}
	public static void setDictionaryDao(DictionaryDao dictionaryDao) {
		DictUtil.dictionaryDao = dictionaryDao;
	}

	public static DictionaryTypeDao getDictionaryTypeDao() {
		return dictionaryTypeDao;
	}
	public static void setDictionaryTypeDao(DictionaryTypeDao dictionaryTypeDao) {
		DictUtil.dictionaryTypeDao = dictionaryTypeDao;
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
	
}
