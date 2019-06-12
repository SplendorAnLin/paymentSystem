package com.yl.boss.service.impl;

import com.yl.boss.dao.DictionaryTypeDao;
import com.yl.boss.service.DictionaryTypeService;

/**
 * 字典类型实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class DictionaryTypeServiceImpl implements DictionaryTypeService {
	
	private DictionaryTypeDao dictionaryTypeDao;

	public DictionaryTypeDao getDictionaryTypeDao() {
		return dictionaryTypeDao;
	}

	public void setDictionaryTypeDao(DictionaryTypeDao dictionaryTypeDao) {
		this.dictionaryTypeDao = dictionaryTypeDao;
	}		
}
