package com.yl.agent.service.impl;

import com.yl.agent.dao.DictionaryTypeDao;
import com.yl.agent.service.DictionaryTypeService;

/**
 * 字典类型service实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
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
