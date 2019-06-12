package com.yl.customer.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 实体探测
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月1日
 * @version V1.0.0
 */
public interface EntityFinder {
	/**
	 * 根据实体主键id查找实体对象
	 * @param <T> - 实体对象类名
	 * @param entityClass - 实体对象类名
	 * @param id - 实体主键id
	 * @return - 如果记录没有找到，返回null
	 */
    public <T> T findById(Class<T> entityClass, Serializable id);
    
    /**
	 * 根据实体主键id查找实体对象
	 * @param <T> - 实体对象类名
	 * @param entityClass - 实体对象类名
	 * @param id - 实体主键id
	 * @return - 如果记录没有找到，抛出异常（DataRetrievalFailureException？）
     */
    public <T> T getReference(Class<T> entityClass, Serializable id);
    
    /**
     * 根据HQL查询语句，获得一个结构集
     * @param queryString - HQL查询语句
     * @return - HQL语句查询出来的结果集，如果没有查到记录，返回对象数量为0的List对象
     */
    public List find(String queryString);
    
    /**
     * 根据HQL查询语句，获得一个结构集
     * @param queryString - HQL查询语句, 语句中的查询条件无命名参数格式":?"
     * @param values - 查询参数值，变参方式
     * @return - HQL语句查询出来的结果集，如果没有查到记录，返回对象数量为0的List对象
     */
    public List find(String queryString, Object... values);
    
    /**
     * 根据HQL查询语句，获得一个结构集
     * TODO: 目前实现有问题，需要修改
     * @param queryString - HQL查询语句, 语句中的查询条件有命名参数格式":name"
     * @param params - 查询参数值，key/value的Map对象形式，key对应参数名字，value对应参数值
     * @return - HQL语句查询出来的结果集，如果没有查到记录，返回对象数量为0的List对象
     */
    public List find(String queryString, Map<String,Object> params);
    
    
    /**
     * 根据定义在Hibernate Mapping文件中的HQL查询语句名字查询
     * @param queryName - 在Hibernate Mapping文件中的HQL查询语句名字
     * @return - HQL语句查询出来的结果集，如果没有查到记录，返回对象数量为0的List对象
     */
    public List findByNamedQuery(String queryName);
    
    /**
     * 
     * 根据定义在Hibernate Mapping文件中的HQL查询语句名字查询
     * @param queryName - 在Hibernate Mapping文件中的HQL查询语句名字
     * @param values - 查询语句中":?"的参数值，变参方式
     * @return
     */
    public List findByNamedQuery(String queryName, Object... values);

    /**
     * 根据HQL查询语句，获得一个结构集
     * TODO: 目前实现有问题，需要修改
     * @param queryString - HQL查询语句, 语句中的查询条件有命名参数格式":name"
     * @param params - 查询参数值，key/value的Map对象形式，key对应参数名字，value对应参数值
     * @return - HQL语句查询出来的结果集，如果没有查到记录，返回对象数量为0的List对象
     */
    public List findByNamedQuery(String queryName, Map<String, Object> params);

    /**
     * 根据hibernate的Criteria查询条件获得查询结果 
     * @param criteria - hibernate的Criteria查询条件
     * @param cacheable - 是否允许查询结果进行缓存
     * @param firstResult - 查询记录的起始位置，从0开始
     * @param maxResults - 允许查询返回的最大记录数
     * @return - 查询结果集
     */
    public List findByCriteria(DetachedCriteria criteria,boolean cacheable ,int firstResult, int maxResults);

    /**
     * @see
     * 根据hibernate的Criteria查询条件获得查询结果
     * @param criteria - hibernate的Criteria查询条件
     * @param cacheable - 是否允许查询结果进行缓存
     * @return - 查询结果集
     *  
     */
    public List findByCriteria(DetachedCriteria criteria,boolean cacheable);
    
    
    /**
     * @see
     * 根据hibernate的Criteria查询条件获得查询结果
     * @param criteria - hibernate的Criteria查询条件
     * @return - 查询结果集
     */
    public List findByCriteria(final DetachedCriteria criteria);
    
    /**
     * @see
     * 根据hibernate的Criteria查询条件获得查询结果
     * @param criteria - hibernate的Criteria查询条件
     * @param firstResult - 查询记录的起始位置，从0开始
     * @param maxResults - 允许查询返回的最大记录数
     * @return - 查询结果集
     */
    public List findByCriteria(final DetachedCriteria criteria, int firstResult, int maxResults);
    
    
    /**
     * 返回指定域查找，主要用于页面显示,并解决lazy_load问题。
     *
     * 如：订单查询 指定返回域如下
     * {productName,receiver.shortname,payer.shortname}
     *
     * @param criteria
     * @param returnFields
     * @param firstResult : -1  表示从第一条记录开始
     * @param maxResults : -1  表示所有记录
     * @return
     */
    public List<Map<String,Object>> customFieldQuery(final DetachedCriteria criteria,String[] returnFields ,int firstResult, int maxResults);
    
    /**
     * 根据HQL查询语句，在会话中查询
     * @param query - HQL查询语句
     * @return - 查询结果集
     */
    public List queryListInSession(final String query);
    
    /**
     * 根据HQL查询语句，在会话中查询（使用缓存策略）
     * @param hql - HQL查询语句
     * @param cacheable - 是否使用缓存策略
     * @param cacheRegion - 缓存区名字
     * @param objects - HQL语句中的查询参数（一个或多个）
     * @return - 查询结果集
     */
    public List findInCache(final String hql, final boolean cacheable, final String cacheRegion,  final Object... objects);

    /**
     * 根据HQL查询语句，在会话中查询（使用缓存策略）
     * @param hql - HQL查询语句
     * @param cacheable - 是否允许查询结果进行缓存
     * @param cacheRegion - 缓存区名字
     * @param startIndex - 查询记录的起始位置，从0开始
     * @param size - 允许查询返回的最大记录数
     * @param objects - HQL语句中的查询参数（一个或多个）
     * @return - 查询结果集
     */
    public List findInCache(final String hql, final boolean cacheable, final String cacheRegion, final int startIndex, final int size, final Object... objects);

    /**
     * 根据HQL查询语句，在会话中查询唯一记录（使用缓存策略）
     * @param hql - HQL查询语句
     * @param cacheable - 是否允许查询结果进行缓存
     * @param cacheRegion - 缓存区名字
     * @param objects - HQL语句中的查询参数（一个或多个）
     * @return - 0个或一个查询结果，如果有多条记录，抛出HibernateException异常
     */
    public Object findUnique(final String hql, final boolean cacheable, final String cacheRegion, final Object... objects);
    
    /**
     * 根据HQL查询语句，在会话中查询唯一记录（使用缓存策略）
     * @param <T> - 返回实体类名称
     * @param entityClass - 实体类
     * @param hql - HQL查询语句
     * @param cacheable - 是否允许查询结果进行缓存
     * @param cacheRegion - 缓存区名字
     * @param objects - HQL语句中的查询参数（一个或多个）
     * @return - 0个或一个查询结果，如果有多条记录，抛出HibernateException异常
     */
    public <T> T findUnique(Class<T> entityClass, final String hql, boolean cacheable, final String cacheRegion, final Object... objects);
    
    /**
     * 根据HQL查询语句，在会话中查询唯一记录（不使用缓存策略）
     * @param <T> - 返回实体类名称
     * @param entityClass - 实体类
     * @param hql - HQL查询语句
     * @param objects - HQL语句中的查询参数（一个或多个）
     * @return - 0个或一个查询结果，如果有多条记录，抛出HibernateException异常
     */
    public <T> T findUnique(Class<T> entityClass, final String hql, final Object... objects);
    
    /**
     * 计算HQL查询语句返回的记录数
     * TODO: 在HQL查询语句中不能有子查询（把group、order语句截掉）
     * @param hql - HQL语句
     * @param objects - HQL语句中的查询参数（一个或多个）
     * @return - 记录数
     */
    public Integer doCount(final String hql, final Object... objects);
    
    /**
     * 查询
     * @param <T>
     * @param entityClass
     * @param id
     * @return
     */
    public <T> T findById(Class<T> entityClass, Serializable id, String[] lazyFields);
}
