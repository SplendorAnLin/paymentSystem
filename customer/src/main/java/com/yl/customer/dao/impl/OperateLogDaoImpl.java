package com.yl.customer.dao.impl;

import com.yl.customer.dao.DAOException;
import com.yl.customer.dao.EntityDao;
import com.yl.customer.dao.OperateLogDao;
import com.yl.customer.entity.OperateLog;

/**
 * 操作日志DAO实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class OperateLogDaoImpl implements OperateLogDao {

	private EntityDao entityDao;
	
	public OperateLog create(OperateLog operateLog) {
		if(operateLog == null){
			throw new DAOException("OperateLog信息不能为空！");
		}
		String info = operateLog.getOperateDesc();
		if(info != null && !"".equals(info)){
			if(info.length() > 1000){
				String descTemp1 = info.substring(0, 999);
				String descTemp2 = info.substring(999);
				operateLog.setOperateDesc(descTemp1);
				operateLog.setOperateDescAppend(descTemp2);
			}
		}
		entityDao.persist(operateLog);
		return operateLog;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
