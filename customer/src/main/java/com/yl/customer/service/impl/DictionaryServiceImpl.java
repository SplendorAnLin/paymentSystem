package com.yl.customer.service.impl;

import com.yl.customer.dao.DictionaryDao;
import com.yl.customer.service.DictionaryService;

/**
 * 字典service实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public class DictionaryServiceImpl implements DictionaryService {

	private DictionaryDao dictionaryDao;
	

	public DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

}
