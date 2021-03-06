package com.yl.agent.dao.impl;

import com.yl.agent.dao.DAOException;
import com.yl.agent.dao.EntityDao;
import com.yl.agent.dao.OperateLogDao;
import com.yl.agent.entity.OperateLog;

/**
 * 操作日志DAO实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月5日
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
