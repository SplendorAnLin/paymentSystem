package com.yl.boss.service.impl;

import java.util.Date;
import java.util.List;

import com.yl.boss.dao.AgentCertDao;
import com.yl.boss.dao.AgentCertHistoryDao;
import com.yl.boss.entity.AgentCert;
import com.yl.boss.entity.AgentCertHistory;
import com.yl.boss.service.AgentCertService;

/**
 * 服务商证件业务访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class AgentCertServiceImpl implements AgentCertService {
	
	private AgentCertDao agentCertDao;
	private AgentCertHistoryDao agentCertHistoryDao;

	@Override
	public void create(AgentCert agentCert, String oper) {
		agentCert.setCreateTime(new Date());
		agentCertDao.create(agentCert);
		
		AgentCertHistory agentCertHistory = new AgentCertHistory(agentCert, oper);
		agentCertHistoryDao.create(agentCertHistory);
	}

	@Override
	public void update(AgentCert agentCert, String oper) {
		AgentCert agCert = agentCertDao.findByAgentNo(agentCert.getAgentNo());
		if(agCert != null){
			agCert.setValidStartTime(agentCert.getValidStartTime());
			agCert.setIdCard(agentCert.getIdCard());
			agCert.setValidEndTime(agentCert.getValidEndTime());
			agCert.setBusinessScope(agentCert.getBusinessScope());
			agCert.setBusinessAddress(agentCert.getBusinessAddress());
			agCert.setEnterpriseCode(agentCert.getEnterpriseCode());
			agCert.setEnterpriseUrl(agentCert.getEnterpriseUrl());
			agCert.setIcp(agentCert.getIcp());
			agCert.setConsumerPhone(agentCert.getConsumerPhone());
			agCert.setLegalPerson(agentCert.getLegalPerson());
			
			agCert.setAttachment(null!=agentCert.getAttachment()&&!agentCert.getAttachment().equals("")?agentCert.getAttachment():agCert.getAttachment());
			agCert.setBusiLiceCert(null!=agentCert.getBusiLiceCert()&&!agentCert.getBusiLiceCert().equals("")?agentCert.getBusiLiceCert():agCert.getBusiLiceCert());
			agCert.setIdCard(null!=agentCert.getIdCard()&&!agentCert.getIdCard().equals("")?agentCert.getIdCard():agCert.getIdCard());
			agCert.setOpenBankAccCert(null!=agentCert.getOpenBankAccCert()&&!agentCert.getOpenBankAccCert().equals("")?agentCert.getOpenBankAccCert():agCert.getOpenBankAccCert());
			agCert.setOrganizationCert(null!=agentCert.getOrganizationCert()&&!agentCert.getOrganizationCert().equals("")?agentCert.getOrganizationCert():agCert.getOrganizationCert());
			agCert.setTaxRegCert(null!=agentCert.getTaxRegCert()&&!agentCert.getTaxRegCert().equals("")?agentCert.getTaxRegCert():agCert.getTaxRegCert());
			agentCertDao.update(agCert);

			AgentCertHistory agentCertHistory = new AgentCertHistory(agCert, oper);
			agentCertHistoryDao.create(agentCertHistory);
		}
	}

	@Override
	public AgentCert findByAgentNo(String agentNo) {
		return agentCertDao.findByAgentNo(agentNo);
	}

	@Override
	public List<AgentCertHistory> findHistByAgentNo(String agentNo) {
		return agentCertHistoryDao.findByAgentNo(agentNo);
	}

	public AgentCertDao getAgentCertDao() {
		return agentCertDao;
	}

	public void setAgentCertDao(AgentCertDao agentCertDao) {
		this.agentCertDao = agentCertDao;
	}

	public AgentCertHistoryDao getAgentCertHistoryDao() {
		return agentCertHistoryDao;
	}

	public void setAgentCertHistoryDao(AgentCertHistoryDao agentCertHistoryDao) {
		this.agentCertHistoryDao = agentCertHistoryDao;
	}

}
