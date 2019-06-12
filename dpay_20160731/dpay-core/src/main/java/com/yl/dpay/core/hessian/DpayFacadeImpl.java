package com.yl.dpay.core.hessian;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lefu.commons.utils.lang.AmountUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountSpecialCompositeTally;
import com.yl.account.api.bean.request.SpecialTallyVoucher;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.account.api.enums.UserRole;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.dpay.core.Utils.HolidayUtils;
import com.yl.dpay.core.entity.DpayConfig;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.entity.ServiceConfig;
import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.AuditStatus;
import com.yl.dpay.core.enums.CardType;
import com.yl.dpay.core.enums.CerType;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.RemitAdutiReason;
import com.yl.dpay.core.enums.RemitType;
import com.yl.dpay.core.enums.RequestStatus;
import com.yl.dpay.core.enums.RequestType;
import com.yl.dpay.core.remit.handle.RequestRemitHandle;
import com.yl.dpay.core.service.DpayConfigService;
import com.yl.dpay.core.service.RequestService;
import com.yl.dpay.core.service.ServiceConfigService;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.beans.RequestBean;
import com.yl.dpay.hessian.beans.ResponseBean;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;
import com.yl.dpay.hessian.service.DpayFacade;

/**
 * 代付远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
@Service("dpayFacade")
public class DpayFacadeImpl implements DpayFacade {

	private static Logger log = LoggerFactory.getLogger(DpayFacadeImpl.class);

	@Autowired
	private RequestService requestService;
	@Autowired
	private ServiceConfigService serviceConfigService;
	@Resource
	private RequestRemitHandle requestRemitHandle;
	@Resource
	private AccountInterface accountInterface;
	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private AgentInterface agentInterface;
	@Resource
	private DpayConfigService dpayConfigService;
	@Resource
	private ShareProfitInterface shareProfitInterface;

	@Override
	public ResponseBean singleRequest(RequestBean requestBean) {
		ResponseBean responseBean = new ResponseBean();
		try {
			log.info("singleRequest requestInfo:"+requestBean.toString());
			
			if(requestService.findByRequestNo(requestBean.getRequestNo(),requestBean.getOwnerId()) != null){
				throw new DpayRuntimeException(DpayExceptionEnum.CUSTORDEREXIST.getCode(), DpayExceptionEnum.getMessage(DpayExceptionEnum.CUSTORDEREXIST.getCode()));
			}
			
			Request request = new Request();
			request.setAccountName(requestBean.getAccountName());
			request.setAccountNo(requestBean.getAccountNo());
			request.setAccountType(AccountType.valueOf(requestBean.getAccountType()));
			request.setAmount(requestBean.getAmount());
			request.setCerType(CerType.valueOf(requestBean.getCerType()));
			if(request.getCerType() != CerType.NAME){
				request.setCerNo(requestBean.getCerNo());
			}
			request.setDescription(requestBean.getDescription());
			request.setOwnerId(requestBean.getOwnerId());
			if(requestBean.getOwnerRole() == null){
				request.setOwnerRole(OwnerRole.CUSTOMER);
			}else{
				request.setOwnerRole(OwnerRole.valueOf(requestBean.getOwnerRole()));
			}
			request.setRequestNo(requestBean.getRequestNo());
			request.setRequestType(RequestType.valueOf(requestBean.getRequestType()));
			if(request.getAccountType() == AccountType.INDIVIDUAL){
				request.setCardType(CardType.valueOf(requestBean.getCardType()));
				if(request.getCardType() == CardType.CREDIT){
					request.setCvv(requestBean.getCvv());
					request.setValidity(requestBean.getValidity());
				}
			}
			request.setBankCode(requestBean.getBankCode());
			request.setBankName(requestBean.getBankName());
			
			// 校验代付发起配置
			dpayConfigService.checkStart(request);
			
			requestService.create(request);
			ServiceConfig serviceConfig = serviceConfigService.find(requestBean.getOwnerId());
			DpayConfig dpayConfig = dpayConfigService.findDpayConfig();
			if(serviceConfig!=null && (serviceConfig.getManualAudit().equals("TRUE") || request.getRequestType() == RequestType.AUTO_DRAWCASH)){
				if (serviceConfig.getBossAudit().equals("FALSE")||dpayConfig.getRemitType()==RemitType.MAN||
						AmountUtils.leq(dpayConfig.getAuditAmount(), request.getAmount()) && dpayConfig.getAuditAmount() > 0) {//以单人配置为准
					return audit(request.getFlowNo(),AuditStatus.REMIT_WAIT.toString(), "SYSTEM");
				}else {
					return audit(request.getFlowNo(), AuditStatus.PASS.toString(), "SYSTEM");
				}
			}
			
			responseBean.setAmount(request.getAmount());
			responseBean.setDescription(request.getDescription());
			responseBean.setFlowNo(request.getFlowNo());
			responseBean.setRequestNo(request.getRequestNo());
			responseBean.setRequestStatus(request.getAuditStatus().toString());
			if(request.getCompleteDate() != null){
				responseBean.setHandleTime(new SimpleDateFormat("yyyyMMddhhmmss").format(request.getCompleteDate()));
			}else{
				responseBean.setHandleTime(new SimpleDateFormat("yyyyMMddhhmmss").format(request.getCreateDate()));
			}
			responseBean.setResponseCode(DpayExceptionEnum.WAITAUDIT.getCode());
			responseBean.setResponseMsg(DpayExceptionEnum.WAITAUDIT.getMessage());
			log.info("singleRequest responseInfo:"+responseBean.toString());
		} catch (DpayRuntimeException e) {
			responseBean.setResponseCode(e.getCode());
			responseBean.setResponseMsg(e.getMessage());
			log.error("发起失败！{}", e.getMessage());
		}
		return responseBean;
	}

	@Override
	public ResponseBean interfaceSingleRequest(RequestBean requestBean) {
		log.info("request requestInfo:"+requestBean.toString());
		
		if(requestService.findByRequestNo(requestBean.getRequestNo(),requestBean.getOwnerId()) != null){
			throw new DpayRuntimeException(DpayExceptionEnum.CUSTORDEREXIST.getCode(), DpayExceptionEnum.getMessage(DpayExceptionEnum.CUSTORDEREXIST.getMessage()));
		}
		
		Request request = new Request();
		request.setAccountName(requestBean.getAccountName());
		request.setAccountNo(requestBean.getAccountNo());
		request.setAccountType(AccountType.valueOf(requestBean.getAccountType()));
		request.setAmount(requestBean.getAmount());
		if(requestBean.getCerType() == null){
			request.setCerType(CerType.NAME);
		}else{
			request.setCerType(CerType.valueOf(requestBean.getCerType()));
		}
		if(request.getCerType() != CerType.NAME){
			request.setCerNo(requestBean.getCerNo());
		}
		request.setDescription(requestBean.getDescription());
		request.setOwnerId(requestBean.getOwnerId());
		request.setOwnerRole(OwnerRole.CUSTOMER);
		request.setRequestNo(requestBean.getRequestNo());
		request.setRequestType(RequestType.valueOf(requestBean.getRequestType()));
		if(request.getAccountType() == AccountType.INDIVIDUAL){
			request.setCardType(CardType.valueOf(requestBean.getCardType()));
			if(request.getCardType() == CardType.CREDIT){
				request.setCvv(requestBean.getCvv());
				request.setValidity(requestBean.getValidity());
			}
		}
		request.setBankCode(requestBean.getBankCode());
		request.setBankName(requestBean.getBankName());
		
		// 校验代付发起配置
		dpayConfigService.checkStart(request);
		
		requestService.create(request);
		ServiceConfig serviceConfig = serviceConfigService.find(requestBean.getOwnerId());
		DpayConfig dpayConfig = dpayConfigService.findDpayConfig();
		if(serviceConfig!=null && serviceConfig.getManualAudit().equals("TRUE")){
			if (serviceConfig!=null&&serviceConfig.getBossAudit().equals("FALSE")||dpayConfig.getRemitType()==RemitType.MAN||
					AmountUtils.leq(dpayConfig.getAuditAmount(), request.getAmount()) && dpayConfig.getAuditAmount() > 0) {//以单人配置为准
				return audit(request.getFlowNo(),AuditStatus.REMIT_WAIT.toString(), "SYSTEM");
				//request.setAuditStatus(AuditStatus.valueOf(auditStatus));
			}else {
				return audit(request.getFlowNo(), AuditStatus.PASS.toString(), "SYSTEM");
			}
		}
		
		ResponseBean responseBean = new ResponseBean();
		responseBean.setAmount(request.getAmount());
		responseBean.setDescription(request.getDescription());
		responseBean.setFlowNo(request.getFlowNo());
		responseBean.setRequestNo(request.getRequestNo());
		responseBean.setRequestStatus(request.getAuditStatus().toString());
		if(request.getCompleteDate() != null){
			responseBean.setHandleTime(new SimpleDateFormat("yyyyMMddhhmmss").format(request.getCompleteDate()));
		}else{
			responseBean.setHandleTime(new SimpleDateFormat("yyyyMMddhhmmss").format(request.getCreateDate()));
		}
		responseBean.setResponseCode(DpayExceptionEnum.WAITAUDIT.getCode());
		responseBean.setResponseMsg(DpayExceptionEnum.WAITAUDIT.getMessage());
		log.info("request responseInfo:"+responseBean.toString());
		return responseBean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseBean audit(String flowNo, String auditStatus, String operator) {
		ResponseBean bean;
		boolean debitflag = false;
		Request request = requestService.findByFlowNo(flowNo);
		
		if(request.getAuditStatus() != AuditStatus.WAIT){
			bean = new ResponseBean();
			bean.setFlowNo(flowNo);
			bean.setResponseCode(DpayExceptionEnum.HANDLE.getCode());
			bean.setResponseMsg(DpayExceptionEnum.HANDLE.getMessage());
			return bean;
		}
		
		if("REMIT_WAIT".equals(auditStatus)){
			request.setRemitAdutiReason(RemitAdutiReason.OWNER_AUDIT_BOSS.getMessage());
		}
		
		//运营自动审核检测
		ServiceConfig serviceConfig = serviceConfigService.find(request.getOwnerId());
		AuditStatus custAudit = request.getAuditStatus();
		request.setAuditStatus(AuditStatus.valueOf(auditStatus));
		//request.setOperator(operator);
		request.setAuditDate(new Date());
		log.info("audit requestInfo:"+request.toString());
		
		if(AuditStatus.valueOf(auditStatus) == AuditStatus.REFUSE){
			request.setStatus(RequestStatus.FAILED);
			request.setCompleteDate(new Date());
			requestService.updateAuditStatus(request);
			bean = new ResponseBean();
			bean.setFlowNo(flowNo);
			bean.setResponseCode("AUDIT_REFUSE");
			bean.setResponseMsg("审核拒绝成功");
			return bean;
		}
		// 校验代付审核配置
		DpayConfig dpayConfig = dpayConfigService.findDpayConfig();
		
			if(dpayConfig != null){
				dpayConfigService.checkAudit(request);
				if((AmountUtils.leq(dpayConfig.getAuditAmount(), request.getAmount()) && dpayConfig.getAuditAmount() > 0) ||
						dpayConfig.getRemitType() == RemitType.MAN){
					request.setAuditStatus(AuditStatus.REMIT_WAIT);
					auditStatus = AuditStatus.REMIT_WAIT.name();
					if(dpayConfig.getRemitType() == RemitType.MAN){
						request.setRemitAdutiReason(RemitAdutiReason.REMIT_AUDIT_MAN.getMessage());
					}else{
						request.setRemitAdutiReason(RemitAdutiReason.AUDIT_AMOUNT.getMessage() + dpayConfig.getAuditAmount() + "元");
					}
				}
			}else{
				request.setAuditStatus(AuditStatus.valueOf(auditStatus));
			}
		
		// 商户审核扣款
        log.info("custAudit:{} ---- 代付信息:{}", custAudit, JsonUtils.toJsonString(request));
		if(custAudit == AuditStatus.WAIT && 
				(request.getAuditStatus() == AuditStatus.PASS || request.getAuditStatus() == AuditStatus.REMIT_WAIT)){
			// 扣款
			debitflag = debit(request);
			if(!debitflag){
//				bean = new ResponseBean();
//				bean.setFlowNo(flowNo);
//				bean.setResponseCode(DpayExceptionEnum.CUST_BAL_ERR.getCode());
//				bean.setResponseMsg(DpayExceptionEnum.CUST_BAL_ERR.getMessage());
//				return bean;
				throw new DpayRuntimeException(DpayExceptionEnum.CUST_BAL_ERR.getCode(),DpayExceptionEnum.CUST_BAL_ERR.getMessage());
			}
			if (AuditStatus.valueOf(auditStatus)== AuditStatus.REMIT_WAIT) {
				requestService.updateAuditStatus(request);
				bean = new ResponseBean();
				bean.setFlowNo(flowNo);
				bean.setResponseCode(DpayExceptionEnum.HANDLE.getCode());
				bean.setResponseMsg(DpayExceptionEnum.HANDLE.getMessage());
				return bean;
			}
			if(request.getAuditStatus() == AuditStatus.REMIT_WAIT){
				request = requestService.updateAuditStatus(request);
				bean = new ResponseBean();
				bean.setResponseCode(DpayExceptionEnum.HANDLE.getCode());
				bean.setResponseMsg(DpayExceptionEnum.HANDLE.getMessage());
				if (serviceConfig!=null&&serviceConfig.getBossAudit().equals("TRUE")) {
					remitAudit(request.getFlowNo(), "PASS", "SYSTEM");
				}
				return bean;
			}
		}
		request.setStatus(RequestStatus.HANDLEDING);
		request = requestService.updateAuditStatus(request);
		try {
			bean = interfaceRequest(request);
			if(bean.getRequestStatus().equals(RequestStatus.FAILED.toString())){
				boolean falg = credit(request);
				if(!falg){
					//XXX 发短信通知
					
				}
			}
			
			return bean;
		} catch (Exception e) {
			log.error("flowNo:{} audit failed request failed error:{}",request.getFlowNo(),e);
//			if(debitflag){
//				boolean falg = credit(request);
//				if(!falg){
//					//XXX 发短信通知
//					
//				}
//			}
			throw new DpayRuntimeException(DpayExceptionEnum.SYSERR.getCode(), DpayExceptionEnum.getMessage(DpayExceptionEnum.SYSERR.getCode()));
		}
	}

	private ResponseBean interfaceRequest(Request request){
		ResponseBean responseBean;
		try {
			responseBean = new ResponseBean();
			
			Map<String, String> params = requestRemitHandle.remit(request);
			if(params.get("tranStat").equals("SUCCESS") || params.get("tranStat").equals("FAILED")){
				request.setStatus(RequestStatus.valueOf(params.get("tranStat")));
				request.setCompleteDate(new Date());
				if(params.get("tranStat").equals("SUCCESS")){
					request.setCompleteMsg(params.get("resCode")+"-"+params.get("resMsg"));
					request.setCost(Double.valueOf(params.get("cost")));
					request.setInterfaceInfoCode(params.get("interfaceCode"));
					responseBean.setResponseCode(DpayExceptionEnum.REMITSUCCESS.getCode());
					responseBean.setResponseMsg(DpayExceptionEnum.REMITSUCCESS.getMessage());
					shareProfit(request, params.get("interfaceCode"));
				}
				if(params.get("tranStat").equals("FAILED")){
					responseBean.setResponseCode(DpayExceptionEnum.REMITFAILED.getCode());
					responseBean.setResponseMsg(DpayExceptionEnum.REMITFAILED.getMessage());
					request.setCompleteMsg(params.get("resCode")+"-"+params.get("resMsg"));
					credit(request);
				}
				if(params.get("tranStat").equals("UNKNOW")){
					responseBean.setResponseCode(DpayExceptionEnum.REMIT_UNKNOW.getCode());
					responseBean.setResponseMsg(DpayExceptionEnum.REMIT_UNKNOW.getMessage());
				}
				request = requestService.updateStatus(request);
			}else{
				responseBean.setResponseCode(DpayExceptionEnum.HANDLE.getCode());
				responseBean.setResponseMsg(DpayExceptionEnum.HANDLE.getMessage());
			}
			
			responseBean.setAmount(request.getAmount());
			responseBean.setDescription(request.getDescription());
			responseBean.setFlowNo(request.getFlowNo());
			if(request.getCompleteDate() != null){
				responseBean.setHandleTime(new SimpleDateFormat("yyyyMMddHHmmss").format(request.getCompleteDate()));
			}
			responseBean.setRequestNo(request.getRequestNo());
			responseBean.setRequestStatus(request.getStatus().toString());
			log.info("audit responseInfo:"+responseBean.toString());
			return responseBean;
		} catch (Exception e) {
			log.error("flowNo:{} audit error:{}",request.getFlowNo(),e);
			throw new DpayRuntimeException(DpayExceptionEnum.SYSERR.getCode(), DpayExceptionEnum.getMessage(DpayExceptionEnum.SYSERR.getCode()));
		}
	}
	
	
	/**
	 * 代付失败退款
	 * @param request
	 * @return boolean
	 */
	private boolean credit(Request request){
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("DPAY_CREDIT");
		accountBussinessInterfaceBean.setOperator("DPAY");
		accountBussinessInterfaceBean.setRemark("代付退款");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("DPAY");
		accountBussinessInterfaceBean.setSystemFlowId(request.getFlowNo());

		AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

		// 交易金额
		SpecialTallyVoucher amount = new SpecialTallyVoucher();
		amount.setAccountType(com.yl.account.api.enums.AccountType.CASH);
		amount.setUserNo(request.getOwnerId());
		amount.setUserRole(UserRole.valueOf(request.getOwnerRole().toString()));
		amount.setAmount(request.getAmount());
		amount.setSymbol(FundSymbol.PLUS);
		amount.setCurrency(Currency.RMB);
		amount.setFee(request.getFee());
		amount.setFeeSymbol(FundSymbol.PLUS);
		amount.setTransDate(request.getAuditDate());
		amount.setTransOrder(request.getFlowNo());
		amount.setWaitAccountDate(new Date());

		List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCompositeTallyResponse accountCompositeTallyResponse = accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
		
		log.info("代付订单号:{}, 退款返回信息：{}", request.getFlowNo(), accountCompositeTallyResponse);
		if(accountCompositeTallyResponse.getStatus() != Status.SUCCESS){
			return false;
		}
		
		if(accountCompositeTallyResponse.getResult() != HandlerResult.SUCCESS){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 代付扣款
	 * @param request
	 * @return boolean
	 */
	private boolean debit(Request request){
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("DPAY_DEBIT");
		accountBussinessInterfaceBean.setOperator("DPAY");
		accountBussinessInterfaceBean.setRemark("代付");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("DPAY");
		accountBussinessInterfaceBean.setSystemFlowId(request.getFlowNo());

		AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

		// 交易金额
		SpecialTallyVoucher amount = new SpecialTallyVoucher();
		amount.setAccountType(com.yl.account.api.enums.AccountType.CASH);
		amount.setUserNo(request.getOwnerId());
		amount.setUserRole(UserRole.valueOf(request.getOwnerRole().toString()));
		amount.setAmount(request.getAmount());
		amount.setSymbol(FundSymbol.SUBTRACT);
		amount.setCurrency(Currency.RMB);
		amount.setFee(request.getFee());
		amount.setFeeSymbol(FundSymbol.SUBTRACT);
		amount.setTransDate(request.getAuditDate());
		amount.setTransOrder(request.getFlowNo());
		amount.setWaitAccountDate(new Date());

		List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCompositeTallyResponse accountDebitResponse = accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
		log.info("代付订单号:{}, 扣款返回信息：{}", request.getFlowNo(), accountDebitResponse);
		if(accountDebitResponse.getStatus() != Status.SUCCESS){
			return false;
		}
		
		if(accountDebitResponse.getResult() != HandlerResult.SUCCESS){
			return false;
		}
		
		return true;
	}

	@Override
	public ResponseBean drawCash(String customerNo, double amount) {
		CustomerSettle customerSettle = customerInterface.getCustomerSettle(customerNo);
		Customer customer = customerInterface.getCustomer(customerNo);
		RequestBean requestBean = new RequestBean();
		requestBean.setAccountName(customerSettle.getAccountName());
		requestBean.setAccountNo(customerSettle.getAccountNo());
		requestBean.setAccountType(customerSettle.getCustomerType().toString());
		if(!"OPEN".equals(requestBean.getAccountType())){
			requestBean.setCardType(CardType.DEBIT.toString());
		}
		requestBean.setAmount(amount);
		requestBean.setCerType(CerType.ID.toString());
		requestBean.setCerNo(customer.getIdCard());
		requestBean.setRequestType(RequestType.DRAWCASH.toString());
		requestBean.setOwnerId(customerNo);
		requestBean.setOwnerRole("CUSTOMER");
		requestBean.setDescription("提现");
		requestBean.setBankName(customerSettle.getOpenBankName());
		requestBean.setBankCode(customerSettle.getBankCode());
		return singleRequest(requestBean);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseBean remitAudit(String flowNo, String auditStatus,
			String operator) {
		ResponseBean bean;
		Request request = requestService.findByFlowNo(flowNo);
		
		if(request.getAuditStatus() != AuditStatus.REMIT_WAIT){
			bean = new ResponseBean();
			bean.setFlowNo(flowNo);
			bean.setResponseCode(DpayExceptionEnum.HANDLE.getCode());
			bean.setResponseMsg(DpayExceptionEnum.HANDLE.getMessage());
			return bean;
		}
		
		request.setAuditStatus(AuditStatus.valueOf(auditStatus));
		request.setOperator(operator);
		request.setStatus(RequestStatus.HANDLEDING);
		request.setAuditDate(new Date());
		
		// 校验代付审核配置
		DpayConfig dpayConfig = dpayConfigService.findDpayConfig();
		if(dpayConfig != null){
			dpayConfigService.checkAudit(request);
		}else{
			request.setAuditStatus(AuditStatus.valueOf(auditStatus));
		}
		
		log.info("audit requestInfo:"+request.toString());
		if(request.getAuditStatus() == AuditStatus.REMIT_REFUSE){
			request.setStatus(RequestStatus.FAILED);
			request.setCompleteDate(new Date());
			requestService.updateAuditStatus(request);
			if(credit(request)){
				//XXX 发短信通知
				log.info("remitAuditRefuse flow_no:"+request.getFlowNo()+" credit failed!");
			}
			bean = new ResponseBean();
			bean.setFlowNo(flowNo);
			bean.setResponseCode("AUDIT_REFUSE");
			bean.setResponseMsg("审核拒绝成功");
			return bean;
		}
		
		request = requestService.updateAuditStatus(request);
		
		try {
			bean = interfaceRequest(request);
			if(bean.getRequestStatus().equals(RequestStatus.FAILED.toString())){
				if(credit(request)){
					//XXX 发短信通知
					log.info("remitAudit remitFailed flow_no:"+request.getFlowNo()+" credit failed!");
				}
			}
			return bean;
		} catch (Exception e) {
			log.error("flowNo:{} remitAudit failed failed error:{}",request.getFlowNo(),e);
			throw new DpayRuntimeException(DpayExceptionEnum.SYSERR.getCode(), DpayExceptionEnum.getMessage(DpayExceptionEnum.SYSERR.getCode()));
		}
	}

	@Override
	public ResponseBean drawCashShareProfit(String agentNo, double amount) {
		AgentSettle agentSettle = agentInterface.getAgentSettle(agentNo);
		Agent agent = agentInterface.getAgent(agentNo);
		RequestBean requestBean = new RequestBean();
		requestBean.setAccountName(agentSettle.getAccountName());
		requestBean.setAccountNo(agentSettle.getAccountNo());
		requestBean.setAccountType(agentSettle.getAgentType().toString());
		if(!"OPEN".equals(requestBean.getAccountType())){
			requestBean.setCardType(CardType.DEBIT.toString());
		}
		requestBean.setAmount(amount);
		requestBean.setCerType(CerType.ID.toString());
		requestBean.setCerNo(agent.getIdCard());
		requestBean.setRequestType(RequestType.DRAWCASH.toString());
		requestBean.setOwnerId(agentNo);
		requestBean.setOwnerRole("AGENT");
		requestBean.setDescription("提现");
		requestBean.setBankName(agentSettle.getOpenBankName());
		requestBean.setBankCode(agentSettle.getBankCode());
		return singleRequest(requestBean);
	}
	
	private void shareProfit(Request request, String interfaceCode){
		// 分润
		try {
			ShareProfitBean shareProfit = new ShareProfitBean();
			if(request.getOwnerRole() == OwnerRole.AGENT){
				shareProfit.setAgentNo(request.getOwnerId());
			}else{
				shareProfit.setCustomerNo(request.getOwnerId());
			}
			shareProfit.setFee(request.getFee());
			shareProfit.setChannelCost(request.getCost());
			shareProfit.setAmount(request.getAmount());
			shareProfit.setInterfaceCode(interfaceCode);
			shareProfit.setOrderCode(request.getFlowNo());
			shareProfit.setOrderTime(request.getCompleteDate());
			if(HolidayUtils.isHoliday(request.getAuditDate())){
				shareProfit.setProductType(ProductType.HOLIDAY_REMIT);
			}else{
				shareProfit.setProductType(ProductType.REMIT);
			}
			shareProfit.setSource("DPAY");
			
			if(request.getOwnerRole() == OwnerRole.AGENT){
				shareProfitInterface.createShareProfitAgent(shareProfit);
			}else{
				shareProfitInterface.createShareProfit(shareProfit);
			}
			
		} catch (Exception e) {
			log.error("shareProfit failed!... [dpay request:{}]", request.getFlowNo(), e);
		}
	}

	@Override
	public Map<String, Object> appDrawCash(String customerNo, double amount) {
		CustomerSettle customerSettle = customerInterface.getCustomerSettle(customerNo);
		RequestBean requestBean = new RequestBean();
		requestBean.setAccountName(customerSettle.getAccountName());
		requestBean.setAccountNo(customerSettle.getAccountNo());
		requestBean.setAccountType(customerSettle.getCustomerType().toString());
		if(!"OPEN".equals(requestBean.getAccountType())){
			requestBean.setCardType(CardType.DEBIT.toString());
		}
		requestBean.setAmount(amount);
		requestBean.setCerType(CerType.NAME.toString());
		requestBean.setRequestType(RequestType.DRAWCASH.toString());
		requestBean.setOwnerId(customerNo);
		requestBean.setOwnerRole("CUSTOMER");
		requestBean.setDescription("APP提现");
		requestBean.setBankName(customerSettle.getOpenBankName());
		requestBean.setBankCode(customerSettle.getBankCode());
		Map<String, Object> map = transBeanMap(singleRequest(requestBean));
		return map;
	}
	
	public static Map<String, Object> transBeanMap(Object obj) {  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                if (!key.equals("class")) {  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    map.put(key, value);  
                }  
            }  
        } catch (Exception e) {  
        	log.error("Map转换出错：{}",e);
        }  
        return map;  
    }

	@Override
	public List<Map<String, Object>> selectRequest(Map<String, Object> params) {
		try {
			return requestService.selectRequest(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> selectRequestDetailed(Map<String, Object> params) {
		try {
			return requestService.selectRequestDetailed(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> selectRequestsum(Map<String, Object> params) {
		
		return requestService.selectRequestsum(params);
	}

	@Override
	public Page findrequest(Map<String, Object> params, Page page) {
		page.setObject(requestService.findrequest(params,page));
	  	return page;
	}

	@Override
	public Map<String, Object> findrequestDetail(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return requestService.findrequestDetail(params);
	}

	
   
	
	

	
	
}