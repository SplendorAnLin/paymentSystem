package com.yl.receive.front.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yl.receive.front.common.ReceiveCodeEnum;
import com.yl.receive.front.model.QueryResponse;
import com.yl.receive.front.model.ReceiveRequest;
import com.yl.receive.front.model.TradeResponse;
import com.yl.receive.front.service.ReceiveRequestService;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.ReceiveTradeHessian;
import com.yl.receive.hessian.bean.ReceiveRequestBean;
import com.yl.receive.hessian.bean.ResponseBean;
import com.yl.receive.hessian.enums.AccNoType;
import com.yl.receive.hessian.enums.AccType;
import com.yl.receive.hessian.enums.OwnerRole;
import com.yl.receive.hessian.enums.ReceiveStatus;
import com.yl.receive.hessian.enums.SourceType;

/**
 * 代收请求业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
@Service("receiveRequestService")
public class ReceiveRequestServiceImpl implements ReceiveRequestService {

	private static final Logger logger = LoggerFactory.getLogger(ReceiveRequestServiceImpl.class);
	
	@Resource
	private ReceiveTradeHessian receiveTradeHessian;
	
	@Resource
	private ReceiveQueryFacade receiveQueryFacade;

	@Override
	public TradeResponse singleRequest(ReceiveRequest receiveRequest) {
		ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();
		receiveRequestBean.setAccNoType(AccNoType.valueOf(receiveRequest.getAccNoType()));
		receiveRequestBean.setAccountName(receiveRequest.getAccountName());
		receiveRequestBean.setAccountNo(receiveRequest.getAccountNo());
		receiveRequestBean.setAccType(AccType.valueOf(receiveRequest.getAccType()));
		receiveRequestBean.setAmount(receiveRequest.getAmount());
		receiveRequestBean.setCertificatesCode(receiveRequest.getCerNo());
		receiveRequestBean.setCertificatesType(receiveRequest.getCerType());
		receiveRequestBean.setCity(receiveRequest.getCity());
		receiveRequestBean.setCustomerNo(receiveRequest.getOwnerId());
		receiveRequestBean.setCustomerOrderCode(receiveRequest.getCustomerOrderCode());
		receiveRequestBean.setNotifyUrl(receiveRequest.getNotifyUrl());
		receiveRequestBean.setOpenBank(receiveRequest.getOpenBank());
		
		receiveRequestBean.setPayerBankNo(receiveRequest.getBankCode());
		receiveRequestBean.setPhone(receiveRequest.getPhone());
		receiveRequestBean.setProvince(receiveRequest.getProvince());
		receiveRequestBean.setRemark(receiveRequest.getRemark());
		receiveRequestBean.setSourceType(SourceType.INTERFACE);
		receiveRequestBean.setOwnerRole(OwnerRole.CUSTOMER);
		
		logger.info("receive-front trade request ", receiveRequestBean);
		ResponseBean responseBean = receiveTradeHessian.singleTrade(receiveRequestBean);
		logger.info("receive-front trade response ", receiveRequestBean);
		
		TradeResponse tradeResponse = new TradeResponse();
		tradeResponse.setCustomerOrderCode(responseBean.getCustomerOrderCode());
		tradeResponse.setOrderCode(responseBean.getReceiveId());
		tradeResponse.setOrderTime(responseBean.getHandleTime());
		
		tradeResponse.setResponseCode(responseBean.getResponseCode());
		tradeResponse.setResponseMsg(responseBean.getResponseMsg());
		return tradeResponse;
	}

	@Override
	public QueryResponse singleQuery(String customerNo, String customerOrderCode) {
		logger.info("receive-front query request customerNo:{},customerOrderCode:{}", customerNo,customerOrderCode);
		ReceiveRequestBean receiveRequestBean = receiveQueryFacade.findBy(customerNo, customerOrderCode);
		logger.info("receive-front query response ", receiveRequestBean);
		
		QueryResponse queryResponse = new QueryResponse();
		queryResponse.setCustomerNo(customerNo);
		queryResponse.setAmount(receiveRequestBean.getAmount());
		queryResponse.setCustomerOrderCode(receiveRequestBean.getCustomerOrderCode());
		queryResponse.setOrderCode(receiveRequestBean.getReceiveId());
		if(receiveRequestBean.getReceiveStatus() == ReceiveStatus.INIT || receiveRequestBean.getReceiveStatus() == ReceiveStatus.UNKNOWN ||
				receiveRequestBean.getReceiveStatus() == ReceiveStatus.WAIT){
			queryResponse.setResponseCode(ReceiveCodeEnum.PROCESS.getCode());
			queryResponse.setResponseMsg(ReceiveCodeEnum.PROCESS.getMessage());
		}
		
		if(receiveRequestBean.getReceiveStatus() == ReceiveStatus.SUCCESS){
			queryResponse.setResponseCode(ReceiveCodeEnum.RECEIVE_SUCCESS.getCode());
			queryResponse.setResponseMsg(ReceiveCodeEnum.RECEIVE_SUCCESS.getMessage());
		}
		
		if(receiveRequestBean.getReceiveStatus() == ReceiveStatus.FAIL){
			queryResponse.setResponseCode(ReceiveCodeEnum.RECEIVE_FAILED.getCode());
			queryResponse.setResponseMsg(ReceiveCodeEnum.RECEIVE_FAILED.getMessage());
		}
		return queryResponse;
	}
	
}
