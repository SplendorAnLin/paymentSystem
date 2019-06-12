package com.yl.customer.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yl.customer.dao.DAOException;
import com.yl.customer.dao.DictionaryTypeDao;
import com.yl.customer.dao.EntityDao;
import com.yl.customer.entity.DictionaryType;

/**
 * 字典类型实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class DictionaryTypeDaoImpl implements DictionaryTypeDao {
	
	private Logger log = Logger.getLogger(DictionaryTypeDaoImpl.class);
	private EntityDao entityDao;
	
	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	public DictionaryType create(DictionaryType dictionaryType) {
		if(dictionaryType == null){
			throw new DAOException("DictionaryType不能为空！");
		}
		entityDao.persist(dictionaryType) ;
		return dictionaryType ;
	}

	public void delete(DictionaryType dictionaryType) {
		if(dictionaryType == null){
			throw new DAOException("dictionaryType！");
		}
		entityDao.remove(dictionaryType) ;
	}

	public DictionaryType findById(String id) {
		return entityDao.findById(DictionaryType.class, id) ;
	}

	public DictionaryType update(DictionaryType dictionaryType) {
		if(dictionaryType == null){
			throw new DAOException("dictionaryType！");
		}
		entityDao.merge(dictionaryType) ;
		return dictionaryType ;
	}
	
	public List<DictionaryType> findAll() {
		return entityDao.find(" from DictionaryType ") ;
	}
	public DictionaryType findByDictTypeId(String dictTypeId){
		String hql = "from DictionaryType type where type.dictTypeId=?";
		DictionaryType dictType = (DictionaryType)entityDao.findUnique(DictionaryType.class, hql, dictTypeId);
		return dictType;
	}
}
