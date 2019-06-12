package com.yl.pay.pos.dao.impl.helper;

/**
 * Title: EntityDao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EntityDao extends EntityFinder {
	
	@Transactional
	public Object doInSession(HibernateCallback callback);
	
	/**
	 * 把新建实体对象存储在数据库中
	 * @param entity - 新建的实体
	 */
	@Transactional
	public void persist(final Serializable entity);

	/**
	 * 更新实体对象或把当前新建实体存储在数据库中
	 * @param <T> - 实体对象
	 * @param entity - 新建或更新的实体
	 * @return
	 */
	@Transactional
	public <T> T merge(final T entity);

	/**
	 * 从数据库中删除指定的数据库实体
	 * @param entity - 待删除的数据库实体
	 */
	@Transactional
	public void remove(final Serializable entity);

	/**
	 * 批量执行数据库脚本
	 * @param sqls - 数据库对象
	 */
	@Transactional
	public void executeBatchUpdate(final List sqls);

	/**
	 * 保存新建的实体对象
	 * @param entity - POJO实体对象
	 */
	@Transactional
	public void save(final Serializable entity);

	/**
	 * 更新指定的实体对象
	 * @param entity - POJO实体对象
	 */
	@Transactional
	public void update(final Serializable entity);

	/**
	 * 根据实体对象的id属性，保存或者更新（id值为未保存时的缺省值时，采用保存策略）
	 * @param entity - POJO实体对象
	 */
	@Transactional
	public void saveOrUpdate(final Serializable entity);
	
	/**
	 * 把所有会话中未保存的数据，更新到数据库中
	 *
	 */
	public void sessionFlush();
	
	public Integer executeUpdate(final String hql, final Object... objects);
	
	/**
	 * 以PreparedStatement的方式执行SQL语句
	 * @param sql
	 * @param objects
	 * @return
	 */
	public Object executeSQLUpdate(final String sql, final Object... objects);
	
	/**
	 * 把实体从session中清除
	 */
	public void evict(Object entity);

	/**
	 * 读取sequence
	 * @param sequence
	 * @return
	 */
	public Object getSequence(final String sequence);
	
	/**
	 * 读取 Sql 查询结果
	 * @param sequence
	 * @return
	 */
	public List queryListBySqlQuery(String sql, Map<String, Object> params);
	
	public Session getMSession();
}
