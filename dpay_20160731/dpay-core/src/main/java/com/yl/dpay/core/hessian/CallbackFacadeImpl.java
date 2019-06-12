package com.yl.dpay.core.hessian;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
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

import com.yl.boss.api.bean.ShareProfitBean;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.dpay.core.Utils.CodeBuilder;
import com.yl.dpay.core.Utils.HolidayUtils;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.RequestStatus;
import com.yl.dpay.core.service.RequestService;
import com.yl.dpay.hessian.beans.CallbackBean;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.service.CallbackFacade;

/**
 * 付款回调远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
@Service("callbackFacade")
public class CallbackFacadeImpl implements CallbackFacade {
	
	private static Logger log = LoggerFactory.getLogger(CallbackFacadeImpl.class);

	@Resource
	private RequestService requestService;
	@Resource
	private ShareProfitInterface shareProfitInterface;
	@Resource
	private AccountInterface accountInterface;

	@Override
	public void callback(CallbackBean callbackBean) {
		log.info("付款通知 参数:{}", JsonUtils.toJsonString(callbackBean));
		Request request = requestService.findByFlowNo(callbackBean.getFlowNo());
		if(callbackBean.getAmount() != request.getAmount()){
			throw new DpayRuntimeException("300001", "订单:"+ request.getFlowNo() +" 金额不一致！");
		}
		
		if(request.getStatus() == RequestStatus.FAILED || request.getStatus() == RequestStatus.SUCCESS){
			throw new DpayRuntimeException("300002", "订单:"+ request.getFlowNo() +" 已处理！");
		}
		
		if(callbackBean.getStatus().equals("SUCCESS") || callbackBean.getStatus().equals("FAILED")){
			request.setStatus(RequestStatus.valueOf(callbackBean.getStatus()));
			request.setCompleteMsg(callbackBean.getResponseCode()+"-"+callbackBean.getResponseMsg());
			request.setCompleteDate(new Date());
			request.setCost(callbackBean.getFee());
			if(callbackBean.getStatus().equals("SUCCESS")){
				shareProfit(request, callbackBean.getInterfaceCode());
			}
			if(callbackBean.getStatus().equals("FAILED")){
				credit(request);
			}
			request = requestService.updateStatus(request);
		}
	}
	
	/**
	 * 代付失败退款
	 * @param request
	 * @return boolean
	 */
	private void credit(Request request){
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("DPAY_CREDIT");
		accountBussinessInterfaceBean.setOperator("DPAY");
		accountBussinessInterfaceBean.setRemark("代付退款");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("DPAY");
		accountBussinessInterfaceBean.setSystemFlowId(CodeBuilder.build("DC"));

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
			throw new DpayRuntimeException("200001", "订单:"+ request.getFlowNo() +"退款失败！");
		}
		
		if(accountCompositeTallyResponse.getResult() != HandlerResult.SUCCESS){
			throw new DpayRuntimeException("200002", "订单:"+ request.getFlowNo() +"退款失败！");
		}
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
			shareProfit.setChannelCost(request.getCost());
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

}
