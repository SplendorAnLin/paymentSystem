package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.DictionaryType;

/**
 * 字典类型操作接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface DictionaryTypeDao {
	
	/**
	 * Dictionary创建
	 * @param Dictionary
	 * @return
	 */
	public DictionaryType create(DictionaryType dictionaryType);
	/**
	 * Dictionary更改
	 * @param dictionary
	 * @return
	 */
	public DictionaryType update(DictionaryType dictionaryType);
	/**
	 * 
	 * @param dictionary
	 * @return
	 */
	public void delete(DictionaryType dictionaryType);
	/**
	 * Dictionary根据Id查找
	 * @param id
	 * @return
	 */
	public DictionaryType findById(String id);
	/**
	 * Dictionary查找所有
	 * @param id
	 * @return
	 */
	public List<DictionaryType> findAll();
	
	public DictionaryType findByDictTypeId(String dictTypeId);
}
