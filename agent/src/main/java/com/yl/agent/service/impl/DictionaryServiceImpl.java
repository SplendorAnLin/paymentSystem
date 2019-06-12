package com.yl.agent.service.impl;

import com.yl.agent.dao.DictionaryDao;
import com.yl.agent.service.DictionaryService;

/**
 * 字典service实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
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
