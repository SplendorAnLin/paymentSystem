package com.yl.dpay.core.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.dpay.core.Utils.CodeBuilder;
import com.yl.dpay.core.Utils.FeeUtils;
import com.yl.dpay.core.Utils.HolidayUtils;
import com.yl.dpay.core.dao.AutoRequestDao;
import com.yl.dpay.core.entity.AutoRequest;
import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.AutoRequestStatus;
import com.yl.dpay.core.enums.FeeType;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.RequestType;
import com.yl.dpay.core.service.AutoRequestService;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.UserRole;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.beans.RequestBean;
import com.yl.dpay.hessian.beans.ResponseBean;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.boss.api.bean.AgentFee;
import com.yl.boss.api.bean.AgentSettle;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.enums.Status;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;

/**
 * 自动发起service实现
 *
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Service("autoRequestService")
public class AutoRequestServiceImpl implements AutoRequestService {
	
	@Resource
	private AutoRequestDao autoRequestDao;
	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private AgentInterface agentInterface;
	@Resource
	private DpayFacade dpayFacade;
	@Resource
	private AccountQueryInterface accountQueryInterface;

	@Override
	public void insert(AutoRequest autoRequest) {
		autoRequest.setCode(CodeBuilder.build("AR", "yyyyMMdd"));
		autoRequest.setCreateDate(new Date());
		autoRequestDao.insert(autoRequest);
	}

	@Override
	public AutoRequest findById(Long id) {
		return autoRequestDao.findById(id);
	}

	@Override
	public void update(AutoRequest autoRequest) {
		autoRequestDao.update(autoRequest);
	}

	@Override
	public void handle(AutoRequest autoRequest) {
		autoRequest.setCode(CodeBuilder.build("AR", "yyyyMMdd"));
		autoRequest.setCreateDate(new Date());
		autoRequest.setAutoRequestStatus(AutoRequestStatus.WAIT);
		settleInfoHandle(autoRequest);
		autoRequestDao.insert(autoRequest);
	}

	@Override
	public void apply(List<AutoRequest> autoRequests) {
		RequestBean requestBean = new RequestBean();
		int listSize = autoRequests.size();
		if(autoRequests == null || listSize == 0){
			return;
		}
		BigDecimal amount = new BigDecimal(0);
		for(AutoRequest autoRequest : autoRequests){
			amount = amount.add(new BigDecimal(autoRequest.getAmount()).setScale(2, RoundingMode.HALF_UP));
		}
		requestBean.setAccountName(autoRequests.get(listSize-1).getAccountName());
		requestBean.setAccountNo(autoRequests.get(listSize-1).getAccountNo());
		requestBean.setAccountType(autoRequests.get(listSize-1).getAccountType().name());
		requestBean.setAmount(amount.doubleValue());
		requestBean.setBankCode(autoRequests.get(listSize-1).getBankCode());
		requestBean.setBankName(autoRequests.get(listSize-1).getBankName());
		if(autoRequests.get(listSize-1).getAccountType() == AccountType.INDIVIDUAL){
			requestBean.setCardType("DEBIT");
		}
		requestBean.setDescription("自动结算,"+"交易订单"+listSize+"笔,金额"+amount+"元");
		requestBean.setOperator("DPAY");
		requestBean.setOwnerId(autoRequests.get(listSize-1).getOwnerId());
		requestBean.setOwnerRole(autoRequests.get(listSize-1).getOwnerRole().name());
		requestBean.setRequestNo(autoRequests.get(listSize-1).getOwnerId() + "-" + DateFormatUtils.format(autoRequests.get(0).getAccountDate(), "yyyyMMdd"));
		requestBean.setRequestType(RequestType.AUTO_DRAWCASH.name());
		
		// 计算付款金额
		compRemitAmount(requestBean);
		// 预算手续费
		preCompFee(requestBean);

		// 发起付款
		ResponseBean bean = dpayFacade.interfaceSingleRequest(requestBean);
		if(bean.getResponseCode().equals("S3101") || bean.getResponseCode().equals("S3100") || bean.getResponseCode().equals("S3000")
				|| bean.getResponseCode().equals("S3001")){
			for(AutoRequest autoRequest : autoRequests){
				autoRequest.setFlowNo(bean.getFlowNo());
				autoRequest.setAutoRequestStatus(AutoRequestStatus.SUCCESS);
				autoRequest.setApplyDate(new Date());
				autoRequestDao.update(autoRequest);
			}
		}
	}
	
	/**
	 * 查询所有者结算信息
	 * @param autoRequest
	 */
	public void settleInfoHandle(AutoRequest autoRequest){
		if(autoRequest.getOwnerRole() == OwnerRole.AGENT){
			AgentSettle agentSettle = agentInterface.getAgentSettle(autoRequest.getOwnerId());
			autoRequest.setAccountName(agentSettle.getAccountName());
			autoRequest.setAccountNo(agentSettle.getAccountNo());
			autoRequest.setAccountType(AccountType.valueOf(agentSettle.getAgentType().name()));
			autoRequest.setBankCode(agentSettle.getBankCode());
			autoRequest.setBankName(agentSettle.getOpenBankName());
		}else if(autoRequest.getOwnerRole() == OwnerRole.CUSTOMER){
			CustomerSettle customerSettle = customerInterface.getCustomerSettle(autoRequest.getOwnerId());
			autoRequest.setAccountName(customerSettle.getAccountName());
			autoRequest.setAccountNo(customerSettle.getAccountNo());
			autoRequest.setAccountType(AccountType.valueOf(customerSettle.getCustomerType()));
			autoRequest.setBankCode(customerSettle.getBankCode());
			autoRequest.setBankName(customerSettle.getOpenBankName());
		}else {
			throw new RuntimeException("autoRequest ownerRole is error");
		}
	}
	
	/**
	 * 计算付款金额
	 */
	private void compRemitAmount(RequestBean requestBean){
		double remitAmount = requestBean.getAmount();
		AccountQuery accountQuery = new AccountQuery();
		accountQuery.setAccountType(com.yl.account.api.enums.AccountType.CASH);
		accountQuery.setUserNo(requestBean.getOwnerId());
		accountQuery.setUserRole(UserRole.valueOf(requestBean.getOwnerRole()));
		AccountQueryResponse accountQueryResponse = accountQueryInterface.findAccountBy(accountQuery);
		
		AccountBalanceQuery balanceQuery = new AccountBalanceQuery();
		balanceQuery.setAccountNo(accountQueryResponse.getAccountBeans().get(0).getCode());
		balanceQuery.setAccountType(com.yl.account.api.enums.AccountType.CASH);
		balanceQuery.setUserNo(requestBean.getOwnerId());
		balanceQuery.setUserRole(UserRole.valueOf(requestBean.getOwnerRole()));
		AccountBalanceQueryResponse accountBalance = accountQueryInterface.findAccountBalance(balanceQuery);
		
		// 账户可用余额 大于待结算金额
		double cmpAmount = AmountUtils.round(AmountUtils.subtract(accountBalance.getAvailavleBalance(), requestBean.getAmount()), 2, RoundingMode.HALF_UP);
		if(cmpAmount >= 0){
			// 结算账户可用余额
			if(cmpAmount > 0){
				requestBean.setAmount(AmountUtils.round(AmountUtils.add(requestBean.getAmount(), cmpAmount), 2, RoundingMode.HALF_UP));
				requestBean.setDescription(requestBean.getDescription() + ",账户余额"+cmpAmount+"元");
			}
		}else{
			throw new RuntimeException("用户:{"+requestBean.getOwnerId()+"},订单自动结算,账户余额不足 待结算金额:{"+remitAmount+"},"
					+ "账户可用余额:{"+accountBalance.getAvailavleBalance()+"}");
		}
		
	}
	
	/**
	 * 预算手续费
	 * 从付款金额中去掉 预算付款手续费 
	 */
	private void preCompFee(RequestBean requestBean){
		double fee = 0d;
		if(HolidayUtils.isHoliday()){
			if("CUSTOMER".equals(requestBean.getOwnerRole())){
				// 假日付 计算手续费
				CustomerFee customerFee = customerInterface.getCustomerFee(requestBean.getOwnerId(), "HOLIDAY_REMIT");
				if(customerFee == null){
					throw new DpayRuntimeException(DpayExceptionEnum.NOTOPEN.getCode(), DpayExceptionEnum.NOTOPEN.getMessage());
				}
				if(customerFee.getStatus() == Status.FALSE){
					throw new DpayRuntimeException(DpayExceptionEnum.FEE_DISABLE.getCode(), DpayExceptionEnum.FEE_DISABLE.getMessage());
				}
				fee = FeeUtils.computeFee(requestBean.getAmount(), FeeType.valueOf(customerFee.getFeeType().toString()), customerFee.getFee());
			}
			if("AGENT".equals(requestBean.getOwnerRole())){
				// 假日付 计算手续费
				AgentFee agentFee = agentInterface.getAgentFee(requestBean.getOwnerId(), ProductType.HOLIDAY_REMIT);
				if(agentFee == null){
					throw new DpayRuntimeException(DpayExceptionEnum.CUST_NOT_OPEN_HOLIDAY.getCode(), DpayExceptionEnum.CUST_NOT_OPEN_HOLIDAY.getMessage());
				}
				if(agentFee.getStatus() == Status.FALSE){
					throw new DpayRuntimeException(DpayExceptionEnum.FEE_DISABLE.getCode(), DpayExceptionEnum.FEE_DISABLE.getMessage());
				}
				fee = FeeUtils.computeFee(requestBean.getAmount(), FeeType.valueOf(agentFee.getFeeType().toString()), agentFee.getFee());
			}
		}else{
			if("CUSTOMER".equals(requestBean.getOwnerRole())){
				// 计算手续费
				CustomerFee customerFee = customerInterface.getCustomerFee(requestBean.getOwnerId(), "REMIT");
				if(customerFee == null){
					throw new DpayRuntimeException(DpayExceptionEnum.NOTOPEN.getCode(), DpayExceptionEnum.NOTOPEN.getMessage());
				}
				if(customerFee.getStatus() == Status.FALSE){
					throw new DpayRuntimeException(DpayExceptionEnum.FEE_DISABLE.getCode(), DpayExceptionEnum.FEE_DISABLE.getMessage());
				}
				fee = FeeUtils.computeFee(requestBean.getAmount(), FeeType.valueOf(customerFee.getFeeType().toString()), customerFee.getFee());
			}
			if("AGENT".equals(requestBean.getOwnerRole())){
				// 计算手续费
				AgentFee agentFee = agentInterface.getAgentFee(requestBean.getOwnerId(), ProductType.REMIT);
				if(agentFee == null){
					throw new DpayRuntimeException(DpayExceptionEnum.NOTOPEN.getCode(), DpayExceptionEnum.NOTOPEN.getMessage());
				}
				if(agentFee.getStatus() == Status.FALSE){
					throw new DpayRuntimeException(DpayExceptionEnum.FEE_DISABLE.getCode(), DpayExceptionEnum.FEE_DISABLE.getMessage());
				}
				fee = FeeUtils.computeFee(requestBean.getAmount(), FeeType.valueOf(agentFee.getFeeType().toString()), agentFee.getFee());
			}
		}
		// 原付款金额减去预算手续费
		requestBean.setAmount(AmountUtils.round(AmountUtils.subtract(requestBean.getAmount(), fee), 2, RoundingMode.HALF_UP));
		if(AmountUtils.leq(requestBean.getAmount(), 0d)){
			throw new RuntimeException("用户:{"+requestBean.getOwnerId()+"},订单自动结算,预算手续费后，账户余额不足 待结算金额:{"+requestBean.getAmount()+"}");
		}
	}

	@Override
	public List<Map<String, String>> findAllWait() {
		return autoRequestDao.findAllWait();
	}

	@Override
	public List<AutoRequest> findWaitByOwner(String ownerId, OwnerRole ownerRole) {
		return autoRequestDao.findWaitByOwner(ownerId, ownerRole);
	}

}
