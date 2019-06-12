package com.yl.customer.service.impl;

import com.yl.customer.dao.DictionaryTypeDao;
import com.yl.customer.service.DictionaryTypeService;

/**
 * 字典类型service实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
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
