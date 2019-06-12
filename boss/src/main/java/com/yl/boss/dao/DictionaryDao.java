package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.Dictionary;
import com.yl.boss.entity.DictionaryType;

/**
 * 字典操作
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface DictionaryDao {
	
	/**
	 * Dictionary创建
	 * @param Dictionary
	 * @return
	 */
	public Dictionary create(Dictionary Dictionary);
	/**
	 * Dictionary更改
	 * @param dictionary
	 * @return
	 */
	public Dictionary update(Dictionary dictionary);
	/**
	 * Dictionary删除
	 * @param dictionary
	 * @return
	 */
	public void delete(Dictionary dictionary);
	/**
	 * Dictionary根据Id查找
	 * @param id
	 * @return
	 */
	public Dictionary findById(long id);
	/**
	 * Dictionary查找所有
	 * @param id
	 * @return
	 */
	public List<Dictionary> findAll();
	/**
	 * Dictionary删除
	 * @param dictionary
	 * @return
	 */
	public List<Dictionary> findByDictionaryTypeId(String dictionaryTypeId);
	/**
	 * Dictionary删除
	 * @param dictionary
	 * @return
	 */
	public List<Dictionary> findByDictionaryType(DictionaryType dictionaryType);
	/**
	 * Dictionary的根据DictionaryType的名称进行查询
	 * @param dictionary
	 * @return
	 */
	public List<Dictionary> findByDictionaryTypeName(String dictionaryTypeName);
	/**
	 * Dictionary的根据DictionaryType的dictTypeId与Dictionary的dictId进行查询
	 * @param dictTypeId
	 * @param dictId
	 * @return
	 */
	public Dictionary findByDictionaryTypeIdAndDictionaryId(String dictTypeId, String dictId);
	/**
	 * Dictionary的根据DictionaryType的dictTypeId与Dictionary的dictName进行查询
	 * @param dictTypeId
	 * @param dictId
	 * @return
	 */
	public Dictionary findByDictionaryTypeIdAndDictionaryName(String dictTypeId, String dictName);
	
}
