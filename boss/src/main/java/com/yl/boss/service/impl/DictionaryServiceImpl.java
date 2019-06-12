package com.yl.boss.service.impl;

import com.yl.boss.dao.DictionaryDao;
import com.yl.boss.service.DictionaryService;

/**
 * 字典操作业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
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
