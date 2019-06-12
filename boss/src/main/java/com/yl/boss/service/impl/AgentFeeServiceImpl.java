package com.yl.boss.service.impl;

import java.util.Date;
import java.util.List;

import com.yl.agent.api.enums.Status;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.dao.AgentFeeDao;
import com.yl.boss.dao.AgentFeeHistoryDao;
import com.yl.boss.entity.AgentFee;
import com.yl.boss.entity.AgentFeeHistory;
import com.yl.boss.service.AgentFeeService;

/**
 * 服务商费率业务访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class AgentFeeServiceImpl implements AgentFeeService {
	
	private AgentFeeDao agentFeeDao;
	private AgentFeeHistoryDao agentFeeHistoryDao;

	@Override
	public void delect(AgentFee agentFee, String oper) {
		AgentFee fee = agentFeeDao.findById(agentFee.getId());
		if(fee != null){
			if (agentFee.getProductType() == null && agentFee.getFeeType() == null) {
				agentFeeDao.delect(fee);
			}
			AgentFeeHistory history = new AgentFeeHistory(fee, oper);
			agentFeeHistoryDao.create(history);
		}
	}
	
	@Override
	public void create(AgentFee agentFee, String oper) {
		agentFee.setCreateTime(new Date());
		agentFee.setStatus(Status.TRUE);
		agentFeeDao.create(agentFee);
		
		AgentFeeHistory agentFeeHistory = new AgentFeeHistory(agentFee, oper);
		agentFeeHistoryDao.create(agentFeeHistory);
	}

	@Override
	public void update(AgentFee agentFee, String oper) {
		AgentFee agFee = agentFeeDao.findById(agentFee.getId());
		if(agFee != null){
			agFee.setFee(agentFee.getFee());
			agFee.setFeeType(agentFee.getFeeType());
			agFee.setProfitRatio(agentFee.getProfitRatio());
			agFee.setProfitType(agentFee.getProfitType());
			agFee.setMaxFee(agentFee.getMaxFee());
			agFee.setMinFee(agentFee.getMinFee());
			agFee.setStatus(agentFee.getStatus());
			agentFeeDao.update(agFee);
			
			AgentFeeHistory agentFeeHistory = new AgentFeeHistory(agFee, oper);
			agentFeeHistoryDao.create(agentFeeHistory);
		}
	}

	@Override
	public AgentFee findBy(String agentNo, ProductType productType) {
		return agentFeeDao.findBy(agentNo, productType);
	}

	@Override
	public List<AgentFee> findByAgentNo(String agentNo) {
		return agentFeeDao.findByAgentNo(agentNo);
	}

	@Override
	public AgentFee findById(long id) {
		return agentFeeDao.findById(id);
	}

	@Override
	public List<AgentFeeHistory> findHistByAgentNo(String agentNo) {
		return agentFeeHistoryDao.findByAgentNo(agentNo);
	}

	public AgentFeeDao getAgentFeeDao() {
		return agentFeeDao;
	}

	public void setAgentFeeDao(AgentFeeDao agentFeeDao) {
		this.agentFeeDao = agentFeeDao;
	}

	public AgentFeeHistoryDao getAgentFeeHistoryDao() {
		return agentFeeHistoryDao;
	}

	public void setAgentFeeHistoryDao(AgentFeeHistoryDao agentFeeHistoryDao) {
		this.agentFeeHistoryDao = agentFeeHistoryDao;
	}
}