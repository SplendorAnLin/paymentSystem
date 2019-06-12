package com.pay.sign.dao.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings({"rawtypes","unchecked","deprecation"})
public class EntityDaoImpl extends HibernateDaoSupport implements EntityDao {
	
	private static final Log logger = LogFactory.getLog(EntityDaoImpl.class);

	public Object doInSession(HibernateCallback callback){
		 return getHibernateTemplate().execute(callback);
	}
	
	public void persist(final Serializable entity) {
		getHibernateTemplate().persist(entity);
	}

	public <T> T merge(final T entity) {
		return (T) getHibernateTemplate().merge(entity);
	}

	public void remove(final Serializable entity) {
		getHibernateTemplate().delete(entity);
	}

	public <T> T findById(Class<T> entityClass, Serializable id) {

		return (T) getHibernateTemplate().get(entityClass, id);
	}

	public <T> T getReference(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	public List find(String queryString) {
		return getHibernateTemplate().find(queryString);
	}

	public List find(String queryString, Object... values) {
		return getHibernateTemplate().find(queryString, values);
	}

	public List find(String queryString, Map<String, Object> params) {
		return getHibernateTemplate().find(queryString, params);
	}

	public List findByNamedQuery(String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}

	public List findByNamedQuery(String queryName, Object... values) {
		return getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	public List findByNamedQuery(String queryName, Map<String, Object> params) {
		return getHibernateTemplate().findByNamedQuery(queryName, params);
	}

	public List findByCriteria(DetachedCriteria criteria) {
		// return getHibernateTemplate().findByCriteria(criteria);
		return findByCriteria(criteria, false, null, 0, 0);
	}

	public List findByCriteria(DetachedCriteria criteria, boolean cacheable) {
		return findByCriteria(criteria, cacheable, null, 0, 0);
	}

	public List findByCriteria(DetachedCriteria criteria, int firstResult,
			int maxResults) {

		return findByCriteria(criteria, false, null, firstResult, maxResults);
	}

	public List findByCriteria(DetachedCriteria criteria, boolean cacheable,
			int firstResult, int maxResults) {

		return findByCriteria(criteria, cacheable, null, firstResult,
				maxResults);
	}

	public List findByCriteria(final DetachedCriteria criteria,
			final boolean cacheable, final String cacheRegion,
			final int startIndex, final int size) {
		Object result = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria executableCriteria = criteria
						.getExecutableCriteria(session);
				executableCriteria.setCacheable(cacheable);
				if (cacheRegion != null)
					executableCriteria.setCacheRegion(cacheRegion);

				if (startIndex > 0) {
					executableCriteria.setFirstResult(startIndex);
				}
				if (size > 0) {
					executableCriteria.setMaxResults(size);
				}
				SessionFactoryUtils.applyTransactionTimeout(executableCriteria,
						getSessionFactory());
				return executableCriteria.list();
			}
		}, true);
		return (List) result;
	}

	public List<Map<String, Object>> customFieldQuery(
			final DetachedCriteria criteria, String[] returnFields,
			int firstResult, int maxResults) {
		List entitys = findByCriteria(criteria, firstResult, maxResults);
		List<Map<String, Object>> rets = new ArrayList<Map<String, Object>>();
		for (Object entity : entitys) {
			Map<String, Object> kv = new HashMap<String, Object>();
			for (String key : returnFields) {
				Object value = null;
				try {
					value = Ognl.getValue(key, entity);
				} catch (OgnlException e) {
					logger.error("Ognl exper=" + key, e);
				}
				kv.put(key, value);
			}
			if (!kv.isEmpty())
				rets.add(kv);
		}
		return rets;
	}

	public void executeBatchUpdate(final List sqls) throws DataAccessException {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Statement st = null;
				try {
					Connection con = session.connection();
					st = con.createStatement();
					for (int i = 0; i < sqls.size(); i++) {
						st.addBatch((String) sqls.get(i));
					}
					if (sqls.size() > 0)
						st.executeBatch();
				} catch (SQLException ex) {
					throw new DataAccessResourceFailureException(ex
							.getMessage());
				}finally{
					close(st, null);
				}
				return null;
			}
		});
	}

	public List queryListInSession(final String query) {
		HibernateCallback action = new HibernateCallback() {
			public List doInHibernate(Session session)
					throws HibernateException {
				Statement stmt = null;
				ResultSet rs = null;
				try {
					stmt = session.connection().createStatement();
					rs = stmt.executeQuery(query);
					int size = rs.getMetaData().getColumnCount();
					List result = new ArrayList();
					while (rs.next()) {
						List row = new ArrayList();
						for (int i = 1; i <= size; i++) {
							if (rs.getMetaData().getColumnType(i) == Types.DATE){
								row.add(rs.getDate(i));
							}
							else if(rs.getMetaData().getColumnType(i) == Types.TIMESTAMP){
								row.add(rs.getTimestamp(i));
							}
							else
								row.add(rs.getString(i));
						}
						result.add(row);
					}
//					session.close();
					return result;
				} catch (SQLException ex) {
					throw new DataAccessResourceFailureException(ex
							.getMessage(),ex);
				} finally {
					close(stmt, rs);
				}
			}
		};

		return getHibernateTemplate().executeFind(action);
	}

	public List findInCache(final String hql, final boolean cacheable,
			final String cacheRegion, final Object... objects) {
		return queryInCache(hql, cacheable, cacheRegion, 0, 0, objects);
	}

	private List queryInCache(final String hql, final boolean cacheable,
			final String cacheRegion, final int startIndex, final int size,
			final Object... objects) {
		Object result = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (cacheable) {
					query.setCacheable(true);
					if (cacheRegion != null) {
						query.setCacheRegion(cacheRegion);
					}
				}
				if (startIndex > 0) {
					query.setFirstResult(startIndex);
				}
				if (size > 0) {
					query.setMaxResults(size);
				}
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						query.setParameter(i, objects[i]);
					}
				}
				return query.list();
			}
		});
		return (List) result;
	}
	
	public List findInCache(final String hql, final boolean cacheable,
			final String cacheRegion, final int startIndex, final int size,
			final Object... objects) {
		return queryInCache(hql, cacheable, cacheRegion, startIndex, size, objects);
	}
	
	public Object findUnique(final String hql, final boolean cacheable, final String cacheRegion, final Object... objects) {
		Object result = getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(cacheable){
					query.setCacheable(true);
					if (cacheRegion != null) {
						query.setCacheRegion(cacheRegion);
					}
				}
				if(objects!=null){
					for(int i=0; i<objects.length;i++){
						query.setParameter(i,objects[i]);
					}
				}
				return query.uniqueResult();
			}
		});
		return result;
	}
	
	public <T> T findUnique(Class<T> entityClass, final String hql, boolean cacheable, final String cacheRegion, final Object... objects) {
		Object result = findUnique(hql, cacheable,cacheRegion, objects);
		return (T)result;
	}
	
	public <T> T findUnique(Class<T> entityClass, final String hql, final Object... objects) {
		Object result = findUnique(hql, false, null, objects);
		return (T)result;
	}
	
	
	public void save(final Serializable entity) {
		getHibernateTemplate().save(entity);
	}

	public void update(final Serializable entity) {
		getHibernateTemplate().update(entity);
	}

	public void saveOrUpdate(final Serializable entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}
	
	public void sessionFlush() {
		getHibernateTemplate().flush();
	}
	
	public Integer doCount(final String hql, final Object... objects) {
		return findUnique(Integer.class, getCountingHql(hql), objects);
	}
	
	private String getCountingHql(String s1) {
		int position1 = s1.toUpperCase().indexOf("FROM");
		String s2 = s1.substring(position1, s1.length());
		int position2 = s2.toUpperCase().indexOf(" ORDER ");
		int position3 = s2.toUpperCase().indexOf(" GROUP ");

		if (position2 != -1 || position3 != -1) {
			if (position2 < position3) {
				if (position2 != -1) {
					s2 = s2.substring(0, position2);
				} else {
					s2 = s2.substring(0, position3);
				}
			} else if (position2 > position3) {
				if (position3 != -1) {
					s2 = s2.substring(0, position3);
				} else {
					s2 = s2.substring(0, position2);
				}
			}
		}
		return "select count(*) " + s2;
	}
	
	public Integer executeUpdate(final String hql, final Object... objects) {
		return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(objects!=null){
					for(int i=0; i<objects.length;i++){
						query.setParameter(i,objects[i]);
					}
				}
				return query.executeUpdate();
			}
		});
	}
	
	public Object executeSQLUpdate(final String sql, final Object... objects){
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				PreparedStatement st = null;
				try {
					Connection con = session.connection();
					st = con.prepareStatement(sql);
					if(objects!=null){
						for(int i=0; i<objects.length;i++){
							st.setObject(i+1, objects[i]);
						}
					}
					return st.execute();
				} catch (SQLException ex) {
					throw new DataAccessResourceFailureException(ex
							.getMessage(),ex);
				}finally{
					close(st, null);
				}
			}
		});
	}
	
	/**
	 * @see EntityDao#evict(Object)
	 */
	public void evict(Object entity){
		getHibernateTemplate().evict(entity);
	}
	
	private void close(Statement st, ResultSet rs){
		try {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new DataAccessResourceFailureException(e.getMessage(), e);
		}
	}
	
	private void initLazyFields(Object entity, String[] lazyFields){
		if(lazyFields==null){
			return;
		}
		for(String fieldName : lazyFields){
			try {
				Object lazyField = Ognl.getValue(fieldName, entity);
				Hibernate.initialize(lazyField);
			} catch (OgnlException e) {
				throw new IllegalArgumentException("no field found for entity:"+entity);
			}
		}
	}
	
	public <T> T findById(Class<T> entityClass, Serializable id, String[] lazyFields){
		T entity = findById(entityClass, id);
		if(entity!=null){
			initLazyFields(entity, lazyFields);
		}
		return entity;
	}
	
	public Object getSequence(final String sequence) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
			throws HibernateException {
				PreparedStatement st = null;
				ResultSet rs = null;
				try {
					Connection con = session.connection();
					st = con.prepareStatement("values nextval for " + sequence);
					rs = st.executeQuery();
					rs.next();
					Long seqValue = rs.getLong(1);
					return seqValue;
				} catch (SQLException ex) {
					throw new DataAccessResourceFailureException(ex
					.getMessage(), ex);
				} finally {
					close(st, null);
				}
			}
		});
	}
	
    /**
     * 创建查询对象
     * @param hsql sql语句
     * @param params 查询参数
     * @param isJdbcSql 是否是原生SQL
     * @param entitys 实体map 别名->实体class
     * @param columnAlias 列别名map 列别名->列类型
     * @return
     */
    private Query createQuery(String hsql, Map<String, Object> params, boolean isJdbcSql, 
    		LinkedHashMap<String, Object> entitys,
    		LinkedHashMap<String, Object> columnAlias) {
        Query q;
        if (isJdbcSql) {
            //如果是jdbc,则设置entitys和columnAlias
            SQLQuery sqlQuery = getSession().createSQLQuery(hsql);
            if(entitys!=null){
                for(String key:entitys.keySet()){
                    sqlQuery.addEntity(key,(Class)entitys.get(key));
                }
            }
            if(columnAlias!=null){
                for(String key:columnAlias.keySet()){
                    Object type = columnAlias.get(key);
                    if(type==null){
                        sqlQuery.addScalar(key);
                    }else{
                        sqlQuery.addScalar(key,(Type)type);
                    }
                }
            }
            q = sqlQuery;
        } else {
            q = getSession().createQuery(hsql);
        }
        String[] keys = q.getNamedParameters();
        if (keys != null && params != null) {
            for (String key : keys) {
                Object value = params.get(key);
                if (value == null) {
                    throw new RuntimeException("没有设置参数" + key + "的值");
                }
                //db2,jdbc的枚举支持不好,将它转成string
                if (isJdbcSql && value instanceof Enum) {
                    Enum e = (Enum) value;
                    value = e.toString();
                }
                if (value instanceof Collection) {
                    Collection c = (Collection) value;
                    Object[] array = c.toArray();
                    for(int i=0;i<array.length;i++){
                        Object v = array[i];
                        if (isJdbcSql && v instanceof Enum) {
                            Enum e = (Enum) v;
                            v = e.toString();
                            array[i] = v;
                        }
                    }
                    q.setParameterList(key, array);
                }else if (value instanceof java.sql.Date) {
                    q.setDate(key, (java.sql.Date)value);
                }else if(value instanceof java.util.Date){
                    q.setTimestamp(key, (java.util.Date)value);
                }else {
                    q.setParameter(key, value);
                }
            }
        }
        return q;
    }
    
    private List queryList(String hql, Map<String, Object> params, boolean isJdbc,LinkedHashMap<String, Object> entitys,
    		LinkedHashMap<String, Object> columnAlias) {
        Query q = createQuery(hql, params, isJdbc, entitys, columnAlias);
        return q.list();
    }

    public List queryListBySqlQuery(String sql, Map<String, Object> params) {
    	
        return queryList(sql, params, true, null, null);
    }	
	
	
}
