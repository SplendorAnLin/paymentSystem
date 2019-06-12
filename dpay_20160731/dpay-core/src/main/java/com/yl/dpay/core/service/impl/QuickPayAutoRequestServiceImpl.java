package com.yl.dpay.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountConsult;
import com.yl.account.api.bean.response.AccountConsultResponse;
import com.yl.account.api.bean.response.AccountFreezeDetailResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.FreezeStatus;
import com.yl.account.api.enums.HandlerResult;
import com.yl.boss.api.bean.ShareProfitBean;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.dpay.core.Utils.CodeBuilder;
import com.yl.dpay.core.Utils.HolidayUtils;
import com.yl.dpay.core.dao.QuickPayAutoRequestDao;
import com.yl.dpay.core.entity.QuickPayAutoRequest;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.AuditStatus;
import com.yl.dpay.core.enums.CardType;
import com.yl.dpay.core.enums.CerType;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.QuickPayAutoRequestStatus;
import com.yl.dpay.core.enums.QuickPayRemitType;
import com.yl.dpay.core.enums.RequestStatus;
import com.yl.dpay.core.enums.RequestType;
import com.yl.dpay.core.remit.handle.RequestRemitHandle;
import com.yl.dpay.core.service.QuickPayAutoRequestService;
import com.yl.dpay.core.service.RequestService;

@Service("quickPayAutoRequestService")
public class QuickPayAutoRequestServiceImpl implements QuickPayAutoRequestService {

	private static Logger logger = LoggerFactory.getLogger(QuickPayAutoRequestServiceImpl.class);

	@Resource
	private AccountInterface accountInterface;
	@Resource
	private QuickPayAutoRequestDao quickPayAutoRequestDao;
	@Resource
	private AccountQueryInterface accountQueryInterface;
	@Resource
	private RequestService requestService;
	@Resource
	private ShareProfitInterface shareProfitInterface;
	@Resource
	private RequestRemitHandle requestRemitHandle;

	@Override
	public void handler(QuickPayAutoRequest quickPayAutoRequest) {
		String requestNo = quickPayAutoRequest.getOrderCode();
		
		QuickPayAutoRequest quickPayRequest= quickPayAutoRequestDao.query(requestNo);
		if (quickPayRequest!= null) {
			return;
		}
		logger.info("******requestNo:{},自动快捷-代付开始******", requestNo);
		// 判断欲冻是否成功
		Map<String, Object> map = new HashMap<>();
		map.put("freezeNo", quickPayAutoRequest.getFreezeNo());
		AccountFreezeDetailResponse accountFreezeDetailResponse = accountQueryInterface.findAccountFreezeDetailBy(map);
		logger.info("requestNo:{},查询欲冻状态返回：{}", requestNo, accountFreezeDetailResponse);
		// 查询冻结状态，如果冻结完成，则进行扣款动作，如果冻结没有完成
		if (FreezeStatus.PRE_FREEZE_COMPLETE == accountFreezeDetailResponse.getStatus()) {
			quickPayAutoRequest.setFreezeStatus(QuickPayAutoRequestStatus.SUCCESS);
		} else {
			quickPayAutoRequest.setFreezeStatus(QuickPayAutoRequestStatus.FAILED);
		}
		if (FreezeStatus.PRE_FREEZE_COMPLETE == accountFreezeDetailResponse.getStatus()) {
			try {
				// 请代付金额款
				AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
				accountBussinessInterfaceBean.setBussinessCode("QUICK_PAY");
				accountBussinessInterfaceBean.setOperator("SYSTEM");
				accountBussinessInterfaceBean.setRemark("QUICK");
				accountBussinessInterfaceBean.setRequestTime(new Date());
				accountBussinessInterfaceBean.setSystemCode("DPAY");
				accountBussinessInterfaceBean.setSystemFlowId(requestNo);
				
				AccountConsult accountConsult = new AccountConsult();
				accountConsult.setFreezeNo(quickPayAutoRequest.getFreezeNo());
				accountConsult.setConsultAmount(quickPayAutoRequest.getRemitAmount());
				accountBussinessInterfaceBean.setTradeVoucher(accountConsult);
				
				AccountConsultResponse acp = accountInterface.consult(accountBussinessInterfaceBean);
				logger.info("requestNo:{},发起请款代付金额返回：{}", requestNo, JsonUtils.toJsonString(acp));
				if (HandlerResult.SUCCESS == acp.getResult() && com.yl.account.api.enums.Status.SUCCESS == acp.getStatus()) {
					quickPayAutoRequest.setConsultRemitStatus(QuickPayAutoRequestStatus.SUCCESS);
				} else {
					quickPayAutoRequest.setConsultRemitStatus(QuickPayAutoRequestStatus.FAILED);
				}
			} catch (Exception e) {
				logger.info("requestNo:{},发起请款代付金额失败：{}", requestNo, e);
				quickPayAutoRequest.setConsultRemitStatus(QuickPayAutoRequestStatus.FAILED);
			}
			
			try {
				// 请代付手续费金额款
				AccountBussinessInterfaceBean accountFeeBussinessInterfaceBean = new AccountBussinessInterfaceBean();
				accountFeeBussinessInterfaceBean.setBussinessCode("Q_P_FEE");
				accountFeeBussinessInterfaceBean.setOperator("SYSTEM");
				accountFeeBussinessInterfaceBean.setRemark("QUICK");
				accountFeeBussinessInterfaceBean.setRequestTime(new Date());
				accountFeeBussinessInterfaceBean.setSystemCode("DPAY");
				accountFeeBussinessInterfaceBean.setSystemFlowId(requestNo);
				
				AccountConsult accountFeeConsult = new AccountConsult();
				accountFeeConsult.setFreezeNo(quickPayAutoRequest.getFreezeNo());
				accountFeeConsult.setConsultAmount(quickPayAutoRequest.getRemitFee());
				accountFeeBussinessInterfaceBean.setTradeVoucher(accountFeeConsult);
				
				AccountConsultResponse acpFee = accountInterface.consult(accountFeeBussinessInterfaceBean);
				logger.info("requestNo:{},发起请款代付手续费金额返回：{}", requestNo, JsonUtils.toJsonString(acpFee));
				if (HandlerResult.SUCCESS == acpFee.getResult() && com.yl.account.api.enums.Status.SUCCESS == acpFee.getStatus()) {
					quickPayAutoRequest.setConsultRemitFeeStatus(QuickPayAutoRequestStatus.SUCCESS);
				} else {
					quickPayAutoRequest.setConsultRemitFeeStatus(QuickPayAutoRequestStatus.FAILED);
				}
			} catch (Exception e) {
				logger.info("requestNo:{},发起请款代付手续费金额失败：{}", requestNo, e);
				quickPayAutoRequest.setConsultRemitFeeStatus(QuickPayAutoRequestStatus.FAILED);
			}
		}
		// 如果状态都为成功生成付款记录
		if (QuickPayAutoRequestStatus.SUCCESS == quickPayAutoRequest.getConsultRemitStatus() && 
				QuickPayAutoRequestStatus.SUCCESS == quickPayAutoRequest.getFreezeStatus() && 
				QuickPayAutoRequestStatus.SUCCESS == quickPayAutoRequest.getConsultRemitFeeStatus() ) {
			try {
				// 生成成功的代付记录
				Request request = null;
				// 订单代付
				if (quickPayAutoRequest.getQuickPayRemitType() != null && quickPayAutoRequest.getQuickPayRemitType() == QuickPayRemitType.ORDER_PAY) {
					request = saveOrderPayRequest(quickPayAutoRequest);
				} else {
					request = saveAutoRequest(quickPayAutoRequest);
					// 分润
					shareProfit(request, "YLZF_DF_100000");
				}
				quickPayAutoRequest.setRequestStatus(QuickPayAutoRequestStatus.SUCCESS);
				quickPayAutoRequest.setFlowNo(request.getFlowNo());
			} catch (Exception e) {
				quickPayAutoRequest.setRequestStatus(QuickPayAutoRequestStatus.FAILED);
				logger.info("requestNo:{},生成代付记录失败：{}", requestNo, e);
			}
		}
		if (QuickPayAutoRequestStatus.FAILED == quickPayAutoRequest.getRequestStatus() || 
				QuickPayAutoRequestStatus.FAILED == quickPayAutoRequest.getFreezeStatus() || 
				QuickPayAutoRequestStatus.FAILED == quickPayAutoRequest.getConsultRemitFeeStatus() ||
				QuickPayAutoRequestStatus.FAILED == quickPayAutoRequest.getConsultRemitStatus()) {
			
			quickPayAutoRequest.setAutoRequestStatus(QuickPayAutoRequestStatus.FAILED);
		} else {
			quickPayAutoRequest.setAutoRequestStatus(QuickPayAutoRequestStatus.SUCCESS);
		}
		quickPayAutoRequestDao.insert(quickPayAutoRequest);
		logger.info("******flowNo:{},自动快捷-代付结束******", requestNo);
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
			logger.error("shareProfit failed!... [dpay request:{}]", request.getFlowNo(), e);
		}
	}
	
	/**
	 * 
	 * @Description 报错自动代付记录
	 * @param quickPayAutoRequest
	 * @return
	 * @date 2017年11月3日
	 */
	private Request saveAutoRequest(QuickPayAutoRequest quickPayAutoRequest) {
		// 生成成功的代付记录
		Request request = new Request();
		request.setCreateDate(new Date());
		request.setCost(0D);
		request.setOptimistic(0);
		request.setFlowNo(CodeBuilder.build("DF", "yyMMdd", 6));
		request.setAccountName(quickPayAutoRequest.getAccountName());
		request.setAccountNo(quickPayAutoRequest.getAccountNo());
		request.setAccountType(quickPayAutoRequest.getAccountType());
		request.setAmount(quickPayAutoRequest.getRemitAmount());
		request.setFee(quickPayAutoRequest.getRemitFee());
		request.setCerType(CerType.NAME);
		request.setDescription("快捷自动结算,交易订单1笔,金额" + quickPayAutoRequest.getRemitAmount() + "元");
		request.setOwnerId(quickPayAutoRequest.getOwnerId());
		request.setOwnerRole(OwnerRole.CUSTOMER);
		request.setRequestNo(quickPayAutoRequest.getOrderCode());
		request.setRequestType(RequestType.AUTO_DRAWCASH);
		if (request.getAccountType() == AccountType.INDIVIDUAL) {
			request.setCardType(CardType.valueOf("DEBIT"));
		}
		request.setBankCode(quickPayAutoRequest.getBankCode());
		request.setBankName(quickPayAutoRequest.getBankName());
		request.setAuditStatus(AuditStatus.PASS);
		request.setStatus(RequestStatus.SUCCESS);
		request.setCompleteDate(new Date());
		request.setAuditDate(new Date());
		// 生成记录
		requestService.insert(request);
		return request;
	}
	/**
	 * 
	 * @Description TODO
	 * @param quickPayAutoRequest
	 * @return
	 * @date 2017年11月3日
	 */
	private Request saveOrderPayRequest(QuickPayAutoRequest quickPayAutoRequest) {
		// 生成成功的代付记录
		Request request = new Request();
		request.setCreateDate(new Date());
		request.setCost(0D);
		request.setOptimistic(0);
		request.setFlowNo(CodeBuilder.build("DF", "yyMMdd", 6));
		request.setAccountName(quickPayAutoRequest.getAccountName());
		request.setAccountNo(quickPayAutoRequest.getAccountNo());
		request.setAccountType(quickPayAutoRequest.getAccountType());
		request.setAmount(quickPayAutoRequest.getRemitAmount());
		request.setFee(quickPayAutoRequest.getRemitFee());
		request.setCerType(CerType.NAME);
		request.setDescription("快捷自动结算,交易订单1笔,金额" + quickPayAutoRequest.getRemitAmount() + "元");
		request.setOwnerId(quickPayAutoRequest.getOwnerId());
		request.setOwnerRole(OwnerRole.CUSTOMER);
		request.setRequestNo(quickPayAutoRequest.getOrderCode());
		request.setRequestType(RequestType.AUTO_DRAWCASH);
		if (request.getAccountType() == AccountType.INDIVIDUAL) {
			request.setCardType(CardType.valueOf("DEBIT"));
		}
		request.setBankCode(quickPayAutoRequest.getBankCode());
		request.setBankName(quickPayAutoRequest.getBankName());
		request.setAuditStatus(AuditStatus.PASS);
		request.setStatus(RequestStatus.HANDLEDING);
		request.setCompleteDate(new Date());
		request.setAuditDate(new Date());
		request.setInterfaceInfoCode(quickPayAutoRequest.getRemitInterfaceCode());
		// 生成记录
		requestService.insert(request);
		
		Map<String, String> params = requestRemitHandle.remit(request);
		if(params.get("tranStat").equals("SUCCESS") || params.get("tranStat").equals("FAILED")){
			request.setStatus(RequestStatus.valueOf(params.get("tranStat")));
			request.setCompleteDate(new Date());
			if(params.get("tranStat").equals("SUCCESS")){
				request.setCompleteMsg(params.get("resCode")+"-"+params.get("resMsg"));
				request.setCost(Double.valueOf(params.get("cost")));
				// 分润
				shareProfit(request, quickPayAutoRequest.getRemitInterfaceCode());
			}
			request = requestService.updateStatus(request);
		}
		return request;
	}

}
