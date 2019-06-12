package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.agent.api.enums.Status;
import com.yl.boss.dao.DAOException;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.ReconBillDao;
import com.yl.boss.entity.InterfaceReconBillConfig;

/**
 * 对账单配置信息数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class ReconBillDaoImpl implements ReconBillDao {
	private EntityDao entityDao;
	
	@Override
	public InterfaceReconBillConfig reconBillById(long id) {
		return entityDao.findById(InterfaceReconBillConfig.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InterfaceReconBillConfig> reconBill() {
		String sql="from InterfaceReconBillConfig where status = ?";
		return entityDao.find(sql,Status.TRUE);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public void reconBillUpdate(InterfaceReconBillConfig interfaceReconBillConfig) throws DAOException {
		entityDao.saveOrUpdate(interfaceReconBillConfig);
	}

	@Override
	public void save(InterfaceReconBillConfig interfaceReconBillConfig) {
		entityDao.save(interfaceReconBillConfig);
	}
}