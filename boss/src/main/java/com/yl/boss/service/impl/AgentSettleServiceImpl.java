package com.yl.boss.service.impl;

import java.util.Date;
import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.boss.dao.AgentSettleDao;
import com.yl.boss.dao.AgentSettleHistoryDao;
import com.yl.boss.entity.AgentSettle;
import com.yl.boss.entity.AgentSettleHistory;
import com.yl.boss.service.AgentSettleService;

/**
 * 服务商结算卡业务访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class AgentSettleServiceImpl implements AgentSettleService {
	
	private AgentSettleDao agentSettleDao;
	private AgentSettleHistoryDao agentSettleHistoryDao;

	@Override
	public void create(AgentSettle agentSettle, String oper) {
		agentSettle.setCreateTime(new Date());
		agentSettleDao.create(agentSettle);
		
		AgentSettleHistory agentSettleHistory = new AgentSettleHistory(agentSettle, oper);
		agentSettleHistoryDao.create(agentSettleHistory);
	}

	@Override
	public void update(AgentSettle agentSettle, String oper) {
		AgentSettle agSettle = agentSettleDao.findByAgentNo(agentSettle.getAgentNo());
		if(agSettle != null){
			agSettle.setAccountNo(agentSettle.getAccountNo());
			agSettle.setAccountName(agentSettle.getAccountName());
			agSettle.setBankCode(agentSettle.getBankCode());
			agSettle.setOpenBankName(agentSettle.getOpenBankName());
			agSettle.setAgentType(agentSettle.getAgentType());
			agentSettleDao.update(agSettle);
			
			AgentSettleHistory agentSettleHistory = new AgentSettleHistory(agSettle, oper);
			agentSettleHistoryDao.create(agentSettleHistory);
		}
	}

	@Override
	public AgentSettle findByAgentNo(String agentNo) {
		return agentSettleDao.findByAgentNo(agentNo);
	}
	
	public List<AgentSettle> findListByAgentNo(String agentNo) {
		return agentSettleDao.findListByAgentNo(agentNo);
	}

	@Override
	public List<AgentSettleHistory> findHistByAgentNo(String agentNo,Page page) {
		return agentSettleHistoryDao.findByAgentNo(agentNo,page);
	}

	public AgentSettleDao getAgentSettleDao() {
		return agentSettleDao;
	}

	public void setAgentSettleDao(AgentSettleDao agentSettleDao) {
		this.agentSettleDao = agentSettleDao;
	}

	public AgentSettleHistoryDao getAgentSettleHistoryDao() {
		return agentSettleHistoryDao;
	}

	public void setAgentSettleHistoryDao(AgentSettleHistoryDao agentSettleHistoryDao) {
		this.agentSettleHistoryDao = agentSettleHistoryDao;
	}

}
