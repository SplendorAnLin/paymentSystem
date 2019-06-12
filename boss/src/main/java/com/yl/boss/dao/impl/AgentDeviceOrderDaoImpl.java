package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.AgentDeviceOrderDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AgentDeviceOrder;

/**
 * 服务商设备采购订单接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class AgentDeviceOrderDaoImpl implements AgentDeviceOrderDao{
	private EntityDao entityDao;

	@Override
	public void create(AgentDeviceOrder agentDeviceOrder) {
		entityDao.persist(agentDeviceOrder);
	}

	@Override
	public void update(AgentDeviceOrder agentDeviceOrder) {
		entityDao.update(agentDeviceOrder);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AgentDeviceOrder findByOrderId(String orderId) {
		String hql = "from AgentDeviceOrder where outOrderId = ?";
		List<AgentDeviceOrder> list = entityDao.find(hql, orderId);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateDeviceOrder(AgentDeviceOrder agentDeviceOrderBean) {
		entityDao.update(agentDeviceOrderBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AgentDeviceOrder findByPsNo(String psNo) {
		String hql = "from AgentDeviceOrder where purchaseSerialNumber = ?";
		try {

			List<AgentDeviceOrder> list = entityDao.find(hql,psNo);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		} catch (Exception e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentDeviceOrder> findByAgentNo(String agentNo) {
		String hql = "from AgentDeviceOrder where ownerId = ?";
		return entityDao.find(hql,agentNo);
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public int yOrNoOrder(AgentDeviceOrder agentDeviceOrder) {
		String hql = "from AgentDeviceOrder where purchaseChannel = ? and user = ? and flowStatus = ?";
		List<AgentDeviceOrder> lists=entityDao.find(hql,agentDeviceOrder.getPurchaseChannel(),agentDeviceOrder.getUser(),agentDeviceOrder.getFlowStatus());
		if(lists.size() != 0 && lists != null){
			return lists.size();
		}else{
			return 0;
		}
	}
}
