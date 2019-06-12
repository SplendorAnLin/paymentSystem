package com.yl.boss.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.Md5Util;
import com.pay.common.util.StringUtil;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.request.AccountModify;
import com.yl.account.api.bean.request.AccountOpen;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountModifyResponse;
import com.yl.account.api.bean.response.AccountOpenResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.AccountStatus;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.UserRole;
import com.yl.agent.api.bean.AgentOperator;
import com.yl.agent.api.enums.Status;
import com.yl.agent.api.interfaces.AgentFunctionInterface;
import com.yl.agent.api.interfaces.AgentOperInterface;
import com.yl.boss.Constant;
import com.yl.boss.action.CustomerAction;
import com.yl.boss.api.enums.AgentStatus;
import com.yl.boss.dao.AgentCertDao;
import com.yl.boss.dao.AgentCertHistoryDao;
import com.yl.boss.dao.AgentDao;
import com.yl.boss.dao.AgentFeeDao;
import com.yl.boss.dao.AgentFeeHistoryDao;
import com.yl.boss.dao.AgentHistoryDao;
import com.yl.boss.dao.AgentSettleDao;
import com.yl.boss.dao.AgentSettleHistoryDao;
import com.yl.boss.entity.Agent;
import com.yl.boss.entity.AgentCert;
import com.yl.boss.entity.AgentCertHistory;
import com.yl.boss.entity.AgentFee;
import com.yl.boss.entity.AgentFeeHistory;
import com.yl.boss.entity.AgentHistory;
import com.yl.boss.entity.AgentSettle;
import com.yl.boss.entity.AgentSettleHistory;
import com.yl.boss.service.AgentFeeService;
import com.yl.boss.service.AgentService;
import com.yl.boss.service.OrganizationService;
import com.yl.boss.utils.GengeratePassword;
import com.yl.boss.utils.RSAUtils;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.enums.OwnerRole;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.receive.hessian.enums.ReceiveConfigStatus;
import com.yl.sms.SmsUtils;

/**
 * 服务商信息业务访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class AgentServiceImpl implements AgentService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerAction.class);

	private AgentDao agentDao;
	private AgentHistoryDao agentHistoryDao;
	private AgentCertDao agentCertDao;
	private AgentCertHistoryDao agentCertHistoryDao;
	private AgentSettleDao agentSettleDao;
	private AgentSettleHistoryDao agentSettleHistoryDao;
	private AgentFeeDao agentFeeDao;
	private AccountInterface accountInterface;
	private AgentFeeHistoryDao agentFeeHistoryDao;
	private AgentOperInterface agentOperInterface;
	private ServiceConfigFacade serviceConfigFacade;
	private AgentFunctionInterface agentFunctionInterface;
	private ReceiveQueryFacade receiveQueryFacade;
	private AccountQueryInterface accountQueryInterface;
	private AgentFeeService agentFeeService;
	private OrganizationService organizationService;

	@Override
	public List<String> findAgentByParen(String paren) {
		return agentDao.findAgentByParen(paren);
	}
	
	@Override
	public void create(ReceiveConfigInfoBean receiveConfigInfoBean,Agent agent, AgentCert agentCert, AgentSettle agentSettle, List<AgentFee> agentFees, ServiceConfigBean serviceConfigBean, String oper, int cycle) {

		agent.setCreateTime(new Date());
		String agentNo = getNowAgentNo();
		agent.setAgentNo(agentNo);
		agent.setStatus(AgentStatus.TRUE);
		agent.setAgentLevel(1);
		agent.setOpenTime(new Date());
		agentDao.create(agent);

		//代收
		if(receiveConfigInfoBean != null){
			try {
				receiveConfigInfoBean.setCreateTime(new Date());
				receiveConfigInfoBean.setOper(oper);
				receiveConfigInfoBean.setOwnerId(agentNo);
				receiveConfigInfoBean.setStatus(ReceiveConfigStatus.TRUE);
				Map<String, Object> receivekeyMap = RSAUtils.genKeyPair();
				receiveConfigInfoBean.setPrivateCer(RSAUtils.getPrivateKey(receivekeyMap));
				receiveConfigInfoBean.setPublicCer(RSAUtils.getPublicKey(receivekeyMap));
				receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
			} catch (Exception e) {
				logger.info("服务商：{}创建代收信息失败，异常信息{}", agentNo, e);
			}
		}
		
		AgentHistory agentHistory = new AgentHistory(agent, oper);
		agentHistoryDao.create(agentHistory);

		agentCert.setAgentNo(agentNo);
		agentCert.setCreateTime(new Date());
		agentCertDao.create(agentCert);

		AgentCertHistory agentCertHistory = new AgentCertHistory(agentCert, oper);
		agentCertHistoryDao.create(agentCertHistory);

		agentSettle.setAgentNo(agentNo);
		agentSettle.setCreateTime(new Date());
		agentSettleDao.create(agentSettle);

		AgentSettleHistory agentSettleHistory = new AgentSettleHistory(agentSettle, oper);
		agentSettleHistoryDao.create(agentSettleHistory);

		// 创建服务商账户
		AccountOpen accountOpen = new AccountOpen();
		accountOpen.setOperator(oper);
		accountOpen.setAccountType(AccountType.CASH);
		accountOpen.setBussinessCode("OPEN_ACCOUNT");
		accountOpen.setCurrency(Currency.RMB);
		accountOpen.setStatus(AccountStatus.NORMAL);
		accountOpen.setOperator("oper");
		accountOpen.setSystemCode("boss");
		accountOpen.setSystemFlowId("OC" + agentNo);
		accountOpen.setRequestTime(new Date());
		accountOpen.setUserNo(agentNo);
		accountOpen.setUserRole(UserRole.AGENT);
		accountOpen.setCycle(cycle);
		AccountOpenResponse accountOpenResponse = accountInterface.openAccount(accountOpen);
		if (accountOpenResponse.getResult() == HandlerResult.FAILED) {
			throw new RuntimeException("agent No:" + agentNo + " open account failed:" + accountOpenResponse + "");
		}
		logger.info("create account agentNo:" + accountOpen.getUserNo() + " info:" + JsonUtils.toJsonString(accountOpen));

		for (AgentFee agentFee : agentFees) {
			if (agentFee == null) {
				continue;
			}
			agentFee.setAgentNo(agentNo);
			agentFee.setCreateTime(new Date());
			agentFee.setStatus(Status.TRUE);
			agentFeeDao.create(agentFee);

			AgentFeeHistory agentFeeHistory = new AgentFeeHistory(agentFee, oper);
			agentFeeHistoryDao.create(agentFeeHistory);
		}

		// 创建服务商系统操作员
		AgentOperator operator = new AgentOperator();
		operator.setPassword(Long.toHexString(System.nanoTime()));
		operator.setRealname(agent.getShortName());
		operator.setUsername(agent.getPhoneNo());
		operator.setStatus(Status.TRUE);
		operator.setAgentNo(agent.getAgentNo());
		operator.setRoleId(Long.valueOf(1));
		agentOperInterface.create(operator);

		String dpayPass = null;
		// 创建代付信息
		if (serviceConfigBean != null) {
			try {
				serviceConfigBean.setOwnerId(agentNo);
				serviceConfigBean.setValid("TRUE");
				serviceConfigBean.setPhone(agent.getPhoneNo());
				Map<String, Object> keyMap = RSAUtils.genKeyPair();
				serviceConfigBean.setPrivateKey(RSAUtils.getPrivateKey(keyMap).replaceAll("\r\n", ""));
				serviceConfigBean.setPublicKey(RSAUtils.getPublicKey(keyMap).replaceAll("\r\n", ""));
				dpayPass = GengeratePassword.getExchangeCode().toString();
				serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
				serviceConfigBean.setOwnerRole(OwnerRole.AGENT.toString());
				serviceConfigFacade.openDF(serviceConfigBean,oper);
			} catch (Exception e) {
				logger.info("服务商：{}创建代付信息失败，异常信息{}", agentNo, e);
			}
		}
		try {
			SmsUtils.sendCustom(String.format(Constant.SMS_AGENT_OPEN, agent.getShortName(),agent.getPhoneNo(),operator.getPassword(),dpayPass), agent.getPhoneNo());
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 密码重置
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void resetPassword(String agentNo) {
		Agent agent = agentDao.findByNo(agentNo);
		if(agent == null){
			throw new RuntimeException("resetAgentPassword agentNo:[" + agentNo + "] is failed! exception:{}");
		}
		String pwd = agentOperInterface.resetPassword(agentNo);
		try {
			SmsUtils.sendCustom(String.format(Constant.AGENT_RESETPASSWORD, pwd), agent.getPhoneNo());
		} catch (IOException e) {
			throw new RuntimeException("resetAgentPassword SMS:[" + agentNo + "] is failed! exception:{}",e);
		}
	}

	@Override
	public Agent findByNo(String agentNo) {
		return agentDao.findByNo(agentNo);
	}

	@Override
	public Agent findByFullName(String fullName) {
		return agentDao.findByFullName(fullName);
	}

	@Override
	public Agent findByShortName(String shortName) {
		return agentDao.findByShortName(shortName);
	}

	@Override
	public void update(Agent agent, String oper) {
		Agent ag = agentDao.findByNo(agent.getAgentNo());
		if (ag != null) {
			ag.setAddr(agent.getAddr());
			ag.setFullName(agent.getFullName());
			ag.setShortName(agent.getShortName());
			ag.setAgentType(agent.getAgentType());
			ag.setCautionMoney(agent.getCautionMoney());
			ag.setIdCard(agent.getIdCard());
			ag.setEMail(agent.getEMail());
			ag.setPhoneNo(agent.getPhoneNo());
			ag.setLinkMan(agent.getLinkMan());
			ag.setStatus(agent.getStatus());
			ag.setProvince(agent.getProvince());
			ag.setCity(agent.getCity());
			ag.setOrganization(agent.getOrganization());
			agentDao.update(ag);

			AgentHistory agentHistory = new AgentHistory(agent, oper);
			agentHistoryDao.create(agentHistory);
		}
	}

	@Override
	public List<AgentHistory> findHistByAgentNo(String agentNo) {
		return agentHistoryDao.findByAgentNo(agentNo);
	}

	private String getNowAgentNo() {
		String maxAgent = agentDao.getMaxAgentNo();
		if (StringUtil.isNull(maxAgent)) {
			return "A100000";
		}
		String agentMaxNo = Integer.parseInt(maxAgent.replaceAll("[a-zA-Z]", "")) > 10000 ? maxAgent : "100001";
		String agent = "A" + String.valueOf(Integer.parseInt(agentMaxNo.replaceAll("[a-zA-Z]", "")) + 1);
		return agent;
	}


	@Override
	public Agent findByPhone(String phone) {
		return agentDao.findByPhone(phone);
	}
	
	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}

	public AgentDao getAgentDao() {
		return agentDao;
	}

	public void setAgentDao(AgentDao agentDao) {
		this.agentDao = agentDao;
	}

	public AgentHistoryDao getAgentHistoryDao() {
		return agentHistoryDao;
	}

	public void setAgentHistoryDao(AgentHistoryDao agentHistoryDao) {
		this.agentHistoryDao = agentHistoryDao;
	}

	public AgentCertDao getAgentCertDao() {
		return agentCertDao;
	}

	public void setAgentCertDao(AgentCertDao agentCertDao) {
		this.agentCertDao = agentCertDao;
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

	public AgentCertHistoryDao getAgentCertHistoryDao() {
		return agentCertHistoryDao;
	}

	public void setAgentCertHistoryDao(AgentCertHistoryDao agentCertHistoryDao) {
		this.agentCertHistoryDao = agentCertHistoryDao;
	}

	public void setAgentOperInterface(AgentOperInterface agentOperInterface) {
		this.agentOperInterface = agentOperInterface;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public void setAccountInterface(AccountInterface accountInterface) {
		this.accountInterface = accountInterface;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public AgentFeeService getAgentFeeService() {
		return agentFeeService;
	}

	public void setAgentFeeService(AgentFeeService agentFeeService) {
		this.agentFeeService = agentFeeService;
	}

	@Override
	public String findAll(Map<String, Object> param) {
		agentFunctionInterface.findAll(param);
		return null;
	}

	@Override
	public String addFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modify() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String agentAddChildMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String agentModifyMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toagentMenuEdit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String agentMenuQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 新增服务商(外部入网)
	 */
	@Override
	public void createAgent(ReceiveConfigInfoBean receiveConfigInfoBean, Agent agent, AgentCert agentCert,
			AgentSettle agentSettle, List<AgentFee> agentFees, ServiceConfigBean serviceConfigBean, String oper, int cycle) {
		agent.setCreateTime(new Date());
		String agentNo = getNowAgentNo();
		agent.setAgentNo(agentNo);
		agent.setStatus(AgentStatus.AUDIT);
		agentDao.create(agent);
		AgentHistory agentHistory = new AgentHistory(agent, oper);
		agentHistoryDao.create(agentHistory);

		agentCert.setAgentNo(agentNo);
		agentCert.setCreateTime(new Date());
		agentCertDao.create(agentCert);

		AgentCertHistory agentCertHistory = new AgentCertHistory(agentCert, oper);
		agentCertHistoryDao.create(agentCertHistory);

		agentSettle.setAgentNo(agentNo);
		agentSettle.setCreateTime(new Date());
		agentSettleDao.create(agentSettle);

		AgentSettleHistory agentSettleHistory = new AgentSettleHistory(agentSettle, oper);
		agentSettleHistoryDao.create(agentSettleHistory);

		// 创建服务商账户
		AccountOpen accountOpen = new AccountOpen();
		accountOpen.setOperator(oper);
		accountOpen.setAccountType(AccountType.CASH);
		accountOpen.setBussinessCode("OPEN_ACCOUNT");
		accountOpen.setCurrency(Currency.RMB);
		accountOpen.setStatus(AccountStatus.NORMAL);
		accountOpen.setOperator("oper");
		accountOpen.setSystemCode("agent");
		accountOpen.setSystemFlowId("OC" + agentNo);
		accountOpen.setRequestTime(new Date());
		accountOpen.setUserNo(agentNo);
		accountOpen.setUserRole(UserRole.AGENT);
		accountOpen.setCycle(cycle);
		AccountOpenResponse accountOpenResponse = accountInterface.openAccount(accountOpen);
		if (accountOpenResponse.getResult() == HandlerResult.FAILED) {
			throw new RuntimeException("agent No:" + agentNo + " open account failed:" + accountOpenResponse + "");
		}
		logger.info("create account agentNo:" + accountOpen.getUserNo() + " info:" + JsonUtils.toJsonString(accountOpen));

		try {
			for (AgentFee agentFee : agentFees) {
				if (agentFee == null) {
					continue;
				}
				agentFee.setAgentNo(agentNo);
				agentFee.setCreateTime(new Date());
				agentFee.setStatus(Status.TRUE);
				agentFeeDao.create(agentFee);

				AgentFeeHistory agentFeeHistory = new AgentFeeHistory(agentFee, oper);
				agentFeeHistoryDao.create(agentFeeHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		// 创建服务商系统操作员
//		AgentOperator operator = new AgentOperator();
//		operator.setPassword(Long.toHexString(System.nanoTime()));
//		operator.setRealname(agent.getShortName());
//		operator.setUsername(agent.getPhoneNo());
//		operator.setStatus(Status.TRUE);
//		operator.setAgentNo(agent.getAgentNo());
//		
//		if(agent.getAgentLevel() == 3){
//			operator.setRoleId(Long.valueOf(3));
//		}else {
//			operator.setRoleId(Long.valueOf(1));
//		}
//		
//		agentOperInterface.create(operator);

		String dpayPass = null;
		// 创建代付信息
		if (serviceConfigBean != null) {
			try {
				serviceConfigBean.setOwnerId(agentNo);
				serviceConfigBean.setValid("TRUE");
				serviceConfigBean.setPhone(agent.getPhoneNo());
				Map<String, Object> keyMap = RSAUtils.genKeyPair();
				serviceConfigBean.setPrivateKey(RSAUtils.getPrivateKey(keyMap).replaceAll("\r\n", ""));
				serviceConfigBean.setPublicKey(RSAUtils.getPublicKey(keyMap).replaceAll("\r\n", ""));
				dpayPass = GengeratePassword.getExchangeCode().toString();
				serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
				serviceConfigBean.setOwnerRole(OwnerRole.AGENT.toString());
				serviceConfigFacade.openDF(serviceConfigBean,oper);
			} catch (Exception e) {
				logger.info("服务商：{}创建代付信息失败，异常信息{}", agentNo, e);
			}
		}
		//代收
		if(receiveConfigInfoBean != null){
			try {
				receiveConfigInfoBean.setCreateTime(new Date());
				receiveConfigInfoBean.setOper(oper);
				receiveConfigInfoBean.setOwnerId(agentNo);
				receiveConfigInfoBean.setStatus(ReceiveConfigStatus.TRUE);
				Map<String, Object> receivekeyMap = RSAUtils.genKeyPair();
				receiveConfigInfoBean.setPrivateCer(RSAUtils.getPrivateKey(receivekeyMap));
				receiveConfigInfoBean.setPublicCer(RSAUtils.getPublicKey(receivekeyMap));
				receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
			} catch (Exception e) {
				logger.info("服务商：{}创建代收信息失败，异常信息{}", agentNo, e);
			}
		}
	}

	@Override
	public void auditAgent(Agent agent, List<AgentFee> feeList, String oper) {
		try {
			Agent at = agentDao.findByNo(agent.getAgentNo());
			at.setStatus(agent.getStatus());
			if(at.getStatus().equals(AgentStatus.TRUE)){
				at.setOpenTime(new Date());
			}
			AgentHistory agentHistory = new AgentHistory(at, oper);
			agentHistoryDao.create(agentHistory);
			
			if(agent.getStatus().equals(AgentStatus.TRUE)){
				
				for (AgentFee agentFee : feeList) {
					if(agentFee != null){
						
						if(agentFee.getId() != null){
							AgentFee agFee = agentFeeDao.findBy(at.getAgentNo(),agentFee.getProductType());

							if(agFee != null){
								agFee.setFee(agentFee.getFee());
								agFee.setFeeType(agentFee.getFeeType());
								agFee.setProfitRatio(agentFee.getProfitRatio());
								agFee.setProfitType(agentFee.getProfitType());
								agFee.setMaxFee(agentFee.getMaxFee());
								agFee.setMinFee(agentFee.getMinFee());
								
								agentFeeDao.update(agFee);
							}
						}else {
							agentFeeDao.create(agentFee);
						}
						
						AgentFeeHistory agentFeeHistory = new AgentFeeHistory(agentFee, oper);
						agentFeeHistoryDao.create(agentFeeHistory);
					}
				}
				
				//代付
				String dpayPass = "";
				ServiceConfigBean bean = serviceConfigFacade.query(at.getAgentNo());
				if(bean != null){
					dpayPass = GengeratePassword.getExchangeCode().toString();
					bean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
					serviceConfigFacade.dfUpdateComplexPwd(bean);
				}
				
				// 创建服务商系统操作员
				AgentOperator operator = new AgentOperator();
				operator.setPassword(Long.toHexString(System.nanoTime()));
				operator.setRealname(at.getShortName());
				operator.setUsername(at.getPhoneNo());
				operator.setStatus(Status.TRUE);
				operator.setAgentNo(at.getAgentNo());
				
//				if(at.getAgentLevel() == 3){
//					operator.setRoleId(Long.valueOf(3));
//				}else {
//					operator.setRoleId(Long.valueOf(1));
//				}

                if(at.getAgentLevel() == 1){
                    operator.setRoleId(Long.valueOf(1));
                } else if (at.getAgentLevel() == 2) {
                    operator.setRoleId(Long.valueOf(2));
                } else {
                    throw new RuntimeException("不允许三级代理商审核");
                }
				
				agentOperInterface.create(operator);
				
				try {
					SmsUtils.sendCustom(String.format(Constant.SMS_AGENT_OPEN, at.getShortName(),at.getPhoneNo(),operator.getPassword(),dpayPass), at.getPhoneNo());
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		} catch (Exception e) {
			logger.info("服务商审核失败！"+e.getMessage()+"");
		}
 	}

	@Override
	public void updateAgent(Agent agent, List<AgentFee> feeList, ReceiveConfigInfoBean receiveConfigInfoBean,
			AgentCert agentCert, AgentSettle agentSettle, ServiceConfigBean serviceConfigBean, String oper, int cycle) {
		try {
			Agent at = agentDao.findByNo(agent.getAgentNo());
			if(at != null){
				//更新服务商信息
				at.setFullName(agent.getFullName()!=null?agent.getFullName():at.getFullName());
				at.setShortName(agent.getShortName()!=null?agent.getShortName():at.getShortName());
				at.setAgentType(agent.getAgentType()!=null?agent.getAgentType():at.getAgentType());
				at.setPhoneNo(agent.getPhoneNo()!=null?agent.getPhoneNo():at.getPhoneNo());
				at.setLinkMan(agent.getLinkMan()!=null?agent.getLinkMan():at.getLinkMan());
				at.setIdCard(agent.getIdCard()!=null?agent.getIdCard():at.getIdCard());
				at.setEMail(agent.getEMail()!=null?agent.getEMail():at.getEMail());
				at.setCautionMoney(agent.getCautionMoney()>0?agent.getCautionMoney():at.getCautionMoney());
				at.setAddr(agent.getAddr()!=null?agent.getAddr():at.getAddr());
				at.setStatus(AgentStatus.AUDIT);
				at.setProvince(agent.getProvince());
				at.setCity(agent.getCity());
				at.setOrganization(agent.getOrganization());
				agentDao.update(at);
				AgentHistory agentHistory = new AgentHistory(at,oper);
				agentHistoryDao.create(agentHistory);
				
				//更新服务商证件信息
				AgentCert atCert  = agentCertDao.findByAgentNo(at.getAgentNo());
				atCert.setValidStartTime(agentCert.getValidStartTime());
//				atCert.setIdCard(agentCert.getIdCard());
				atCert.setValidEndTime(agentCert.getValidEndTime());
				atCert.setBusinessScope(agentCert.getBusinessScope());
				atCert.setBusinessAddress(agentCert.getBusinessAddress());
				atCert.setEnterpriseCode(agentCert.getEnterpriseCode());
				atCert.setEnterpriseUrl(agentCert.getEnterpriseUrl());
				atCert.setIcp(agentCert.getIcp());
				atCert.setConsumerPhone(agentCert.getConsumerPhone());
				atCert.setLegalPerson(agentCert.getLegalPerson());
				
				atCert.setAttachment(agentCert.getAttachment()!=null?agentCert.getAttachment():atCert.getAttachment());
				atCert.setBusiLiceCert(agentCert.getBusiLiceCert()!=null?agentCert.getBusiLiceCert():atCert.getBusiLiceCert());
				atCert.setIdCard(agentCert.getIdCard()!=null?agentCert.getIdCard():atCert.getIdCard());
				atCert.setOpenBankAccCert(agentCert.getOpenBankAccCert()!=null?agentCert.getOpenBankAccCert():atCert.getOpenBankAccCert());
				atCert.setOrganizationCert(agentCert.getOrganizationCert()!=null?agentCert.getOrganizationCert():atCert.getOrganizationCert());
				atCert.setTaxRegCert(agentCert.getTaxRegCert()!=null?agentCert.getTaxRegCert():atCert.getTaxRegCert());
				agentCertDao.update(atCert);
				AgentCertHistory atCertHistory = new AgentCertHistory(atCert, oper);
				agentCertHistoryDao.create(atCertHistory);
				
				//更新服务商结算信息
				AgentSettle atSettle = agentSettleDao.findByAgentNo(at.getAgentNo());
				atSettle.setAgentType(agentSettle.getAgentType()!=null?agentSettle.getAgentType():agentSettle.getAgentType());
				atSettle.setAccountNo(agentSettle.getAccountNo()!=null?agentSettle.getAccountNo():agentSettle.getAccountNo());
				atSettle.setAccountName(agentSettle.getAccountName()!=null?agentSettle.getAccountName():agentSettle.getAccountName());
				atSettle.setOpenBankName(agentSettle.getOpenBankName()!=null?agentSettle.getOpenBankName():agentSettle.getOpenBankName());
				agentSettleDao.update(atSettle);
				AgentSettleHistory atSettleHistory = new AgentSettleHistory(atSettle,oper);
				agentSettleHistoryDao.create(atSettleHistory);
				
				
				
				
				if (serviceConfigBean != null) {
					try {
						if(null == serviceConfigBean.getOwnerId() || serviceConfigBean.getOwnerId().equals("")){
							String dpayPass = null;
							// 创建代付信息
							serviceConfigBean.setOwnerId(at.getAgentNo());
							serviceConfigBean.setValid("TRUE");
							serviceConfigBean.setPhone(agent.getPhoneNo());
							Map<String, Object> keyMap = RSAUtils.genKeyPair();
							serviceConfigBean.setPrivateKey(RSAUtils.getPrivateKey(keyMap).replaceAll("\r\n", ""));
							serviceConfigBean.setPublicKey(RSAUtils.getPublicKey(keyMap).replaceAll("\r\n", ""));
							dpayPass = GengeratePassword.getExchangeCode().toString();
							serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
							serviceConfigBean.setOwnerRole(OwnerRole.AGENT.toString());
							serviceConfigFacade.openDF(serviceConfigBean,oper);
						}else {
							//更新代付信息
							ServiceConfigBean serviceConfig =serviceConfigFacade.query(serviceConfigBean.getOwnerId());
							
							serviceConfig.setManualAudit(serviceConfigBean.getManualAudit()!=null?serviceConfigBean.getManualAudit():serviceConfig.getManualAudit());
							serviceConfig.setUsePhoneCheck(serviceConfigBean.getUsePhoneCheck()!=null?serviceConfigBean.getUsePhoneCheck():serviceConfig.getUsePhoneCheck());
							serviceConfig.setCustIp(serviceConfigBean.getCustIp()!=null?serviceConfigBean.getCustIp():serviceConfig.getCustIp());
							serviceConfig.setDomain(serviceConfigBean.getDomain()!=null?serviceConfigBean.getDomain():serviceConfig.getDomain());
							serviceConfig.setMaxAmount(serviceConfigBean.getMaxAmount()>0?serviceConfigBean.getMaxAmount():serviceConfig.getMaxAmount());
							serviceConfig.setMinAmount(serviceConfigBean.getMinAmount()>0?serviceConfigBean.getMinAmount():serviceConfig.getMinAmount());
							serviceConfig.setBossAudit(serviceConfigBean.getBossAudit());
							serviceConfig.setDayMaxAmount(serviceConfigBean.getDayMaxAmount());
							serviceConfig.setStartTime(serviceConfigBean.getStartTime());
							serviceConfig.setEndTime(serviceConfigBean.getEndTime());
							if(serviceConfigBean.getHolidayStatus() != null && !serviceConfigBean.getHolidayStatus().equals("")){
								serviceConfig.setHolidayStatus(serviceConfigBean.getHolidayStatus());
							}
							serviceConfigFacade.updateServiceConfigOnlyForAgentSystem(serviceConfig);
						}
						
					} catch (Exception e) {
						logger.info("createOrUpdate openDF serviceConfigBean.getOwnerId():" + agent.getAgentNo());
					}
				}
				
				//代收信息更新或者创建
				if(receiveConfigInfoBean != null){
					try {
						if(null == receiveConfigInfoBean.getOwnerId()){
							//创建代收信息
							receiveConfigInfoBean.setCreateTime(new Date());
							receiveConfigInfoBean.setOper(oper);
							receiveConfigInfoBean.setOwnerId(at.getAgentNo());
							receiveConfigInfoBean.setStatus(ReceiveConfigStatus.TRUE);
							Map<String, Object> receivekeyMap = RSAUtils.genKeyPair();
							receiveConfigInfoBean.setPrivateCer(RSAUtils.getPrivateKey(receivekeyMap));
							receiveConfigInfoBean.setPublicCer(RSAUtils.getPublicKey(receivekeyMap));
							receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
						}else {//修改代收信息
							ReceiveConfigInfoBean receiveConfigInfo = receiveQueryFacade.queryReceiveConfigBy(receiveConfigInfoBean.getOwnerId());
							//设置属性
							receiveConfigInfo.setCustIp(receiveConfigInfoBean.getCustIp());
							receiveConfigInfo.setDailyMaxAmount(receiveConfigInfoBean.getDailyMaxAmount());
							receiveConfigInfo.setDomain(receiveConfigInfoBean.getDomain());
							receiveConfigInfo.setSingleBatchMaxNum(receiveConfigInfoBean.getSingleBatchMaxNum());
							receiveConfigInfo.setSingleMaxAmount(receiveConfigInfoBean.getSingleMaxAmount());
							if(receiveConfigInfo.getStatus().equals("true")){
								receiveConfigInfo.setStatus(receiveConfigInfoBean.getStatus());
							}
							receiveQueryFacade.updateReceiveConfig(receiveConfigInfo);
						}
					} catch (Exception e) {
						logger.info("createOrUpdate openDF receiveConfigInfoBean.getOwnerId():" + agent.getAgentNo());
					}
				}
				
				AccountQuery accountQuery = new AccountQuery();
				accountQuery.setUserNo(at.getAgentNo());
				accountQuery.setUserRole(UserRole.AGENT);
				AccountQueryResponse accountResponse = accountQueryInterface.findAccountBy(accountQuery);
				if(accountResponse!=null&&accountResponse.getAccountBeans()!=null&&accountResponse.getAccountBeans().size()==1){
					AccountBean accountBean =accountResponse.getAccountBeans().get(0);
					
					AccountModify accountModify = new AccountModify();
					accountModify.setAccountNo(accountBean.getCode());
					accountModify.setUserNo(accountBean.getUserNo());
					accountModify.setCycle(cycle);
					accountModify.setAccountStatus(accountBean.getStatus());//注释掉查询时候是什么状态就什么状态,不能因为修改入账周期改变状态
					accountModify.setSystemCode("AGENT");
					accountModify.setSystemFlowId("OC" + at.getAgentNo());
					accountModify.setBussinessCode("UPDATE_ACCOUNT");
					accountModify.setRequestTime(new Date());
					accountModify.setOperator(oper);
					accountModify.setRemark("服务商系统服务商修改入账周期");
					AccountModifyResponse accountModifyResponse = accountInterface.modifyAccount(accountModify);
					if (accountModifyResponse.getResult() == HandlerResult.FAILED) {
						throw new RuntimeException("customer No:" + agent.getAgentNo() + " update account failed:" + accountModifyResponse + "");
					}
					logger.info("update account agentNo:" + accountModify.getUserNo() + " info:" + JsonUtils.toJsonString(accountModify));
				}else{
					throw new RuntimeException("没有查询到账户信息或账户信息存在多个");
				}
				
				//更新费率信息
				for (AgentFee agentFee : feeList) {
					if (agentFee!=null) {
						if(null!=agentFee.getId()){//原有的就更新
							if (null==agentFee.getProductType()) {
								agentFeeService.delect(agentFee, oper);
							} else {
								agentFeeService.update(agentFee, oper);
							}
						}else{//新增的就创建
							agentFee.setCreateTime(new Date());
							agentFee.setAgentNo(at.getAgentNo());
							agentFee.setStatus(Status.TRUE);
							agentFeeService.create(agentFee, oper);
						}
					}
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, String> checkAgentName(String fullName, String shortName) {
		return agentDao.checkAgentName(fullName, shortName);
	}

	@Override
	public int queryAgentLevelByAgentNo(String agentNo) {
		return agentDao.queryAgentLevelByAgentNo(agentNo);
	}
	
	@Override
	public List<String> findAllCustomerNoByAgentNo(String agentNo) {
		return agentDao.findAllCustomerNoByAgentNo(agentNo);
	}
	
	@Override
    public String queryAgentFullNameByAgentNo(String agentNo) {
        return agentDao.queryAgentFullNameByAgentNo(agentNo);
    }
    
    @Override
    public String queryParenIdByAgentNo(String agentNo) {
        return agentDao.queryParenIdByAgentNo(agentNo);
    }
    
	@Override
	public String queryAgentShortNameByAgentNo(String agentNo) {
		return agentDao.queryAgentShortNameByAgentNo(agentNo);
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

}
