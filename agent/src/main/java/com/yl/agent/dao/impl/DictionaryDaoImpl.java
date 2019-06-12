package com.yl.agent.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.agent.dao.DAOException;
import com.yl.agent.dao.DictionaryDao;
import com.yl.agent.dao.EntityDao;
import com.yl.agent.entity.Dictionary;
import com.yl.agent.entity.DictionaryType;
import com.yl.agent.enums.Status;

/**
 * 字典实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月5日
 * @version V1.0.0
 */
public class DictionaryDaoImpl implements DictionaryDao {
	private Log log = LogFactory.getLog(DictionaryDaoImpl.class);
	private EntityDao entityDao;
	
	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	public Dictionary create(Dictionary dictionary) {
		exceptionManage(dictionary) ;
		
		entityDao.persist(dictionary) ;
		return dictionary ;
	}

	public void delete(Dictionary dictionary) {
		entityDao.remove(dictionary);
	}

	public Dictionary update(Dictionary dictionary) {
		exceptionManage(dictionary) ;
		
		entityDao.merge(dictionary) ;
		return dictionary;
	}
	
	public Dictionary findById(long id) {
		return entityDao.findById(Dictionary.class, id);
	}
	
	public List<Dictionary> findAll() {
		return entityDao.find(" from Dictionary ") ;
	}
	
	public List<Dictionary> findByDictionaryTypeId(String dictionaryTypeId) {
		String hql="from Dictionary as d where d.dictType.dictTypeId = ? and d.status = ? order by d.displayOrder asc" ;
		return entityDao.find(hql,dictionaryTypeId, "TRUE") ;
	}
	
	public List<Dictionary> findByDictionaryType(DictionaryType dictionaryType) {
		String hql="from Dictionary as d where d.dictType=?" ;
		return entityDao.find(hql,dictionaryType) ;
	}
	
	public List<Dictionary> findByDictionaryTypeName(String dictionaryTypeName) {
		String hql="from Dictionary as d where d.dictType.dictTypeName=?" ;
		return entityDao.find(hql,dictionaryTypeName) ;
	}
	
	public Dictionary findByDictionaryTypeIdAndDictionaryId(String dictTypeId, String dictId){
		String hql=" from Dictionary as d where d.dictId=? and d.dictType.dictTypeId=? " ;
		return entityDao.findUnique(Dictionary.class,hql,dictId,dictTypeId);
	}
	
	public Dictionary findByDictionaryTypeIdAndDictionaryName(String dictTypeId, String dictName) {
		String hql=" from Dictionary as d where d.dictName=? and d.dictType.dictTypeId=? " ;
		return entityDao.findUnique(Dictionary.class,hql,dictName,dictTypeId);
	}
	
	/**
	 * 用于异常处理
	 * @param dictionary
	 */
	private void exceptionManage( Dictionary dictionary){
		if(dictionary == null){
			throw new DAOException("Dictionary不能为空！");
		}
		if(  dictionary.getDictId() == null  ){
			throw new DAOException("Dictionary标识不能为空！");
		}
		if( dictionary.getDictName() == null ){
			throw new DAOException("Dictionary名称不能为空！");
		}
		if( dictionary.getDictType() == null ){
			throw new DAOException("Dictionary类型不能为空！");
		}
	}
	
}
