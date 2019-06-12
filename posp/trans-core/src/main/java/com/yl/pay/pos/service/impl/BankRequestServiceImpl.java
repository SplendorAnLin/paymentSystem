package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.bean.BankChannelRouteReturnBean;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.BankRequest;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.service.IBankRequestService;

import java.util.Date;

/**
 * Title: 银行请求信息 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class BankRequestServiceImpl extends BaseService implements IBankRequestService{

	public ExtendPayBean create(ExtendPayBean extendPayBean) {
		
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();		
		BankRequest bankRequest = new BankRequest();

		bankRequest.setPan(unionPayBean.getPan());
		bankRequest.setBankRequestId(unionPayBean.getSystemsTraceAuditNumber());
		bankRequest.setPosEntryModeCode(unionPayBean.getPosEntryModeCode());
		bankRequest.setAuthorizationCode(unionPayBean.getAuthorizationCode());
		bankRequest.setBankCustomerNo(unionPayBean.getCardAcceptorId());
		bankRequest.setBankPosCati(unionPayBean.getCardAcceptorTerminalId());
		bankRequest.setBatchNo(unionPayBean.getBatchNo());	
		
		BankChannelRouteReturnBean bankChannelRouteBean = extendPayBean.getBankChannelRouteBean();	
		if(bankChannelRouteBean!=null){
			bankRequest.setBankChannelCode(bankChannelRouteBean.getBankChannel().getCode());
			bankRequest.setBankInterfaceCode(bankChannelRouteBean.getBankChannel().getBankInterface().getCode());
		}else if(extendPayBean.getBankInterface()!=null){
			bankRequest.setBankInterfaceCode(extendPayBean.getBankInterface().getCode());
		}
				
		bankRequest.setSourceBankRequestId(unionPayBean.getSourcePosRequestId());
		bankRequest.setSourceTranDate(unionPayBean.getSourceTranDate());
		bankRequest.setCreateTime(new Date());
		bankRequest.setTransType(extendPayBean.getTransType());
		if(extendPayBean.getPos()!=null){
			bankRequest.setPosCati(extendPayBean.getPos().getPosCati());
		}
		bankRequest.setAmount(extendPayBean.getTransAmount());	
		bankRequest.setCardInfo(extendPayBean.getCardInfo());
		bankRequest.setOrder(extendPayBean.getOrder());
		bankRequest.setPayment(extendPayBean.getPayment());
		
		bankRequest = bankRequestDao.create(bankRequest);		
		extendPayBean.setBankRequest(bankRequest);

		return extendPayBean;
	}	
	
	@Override
	public ExtendPayBean create(ExtendPayBean extendPayBean, TransType transType) {
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();		
		BankRequest bankRequest = new BankRequest();

		bankRequest.setPan(unionPayBean.getPan());
		bankRequest.setBankRequestId(unionPayBean.getSystemsTraceAuditNumber());
		bankRequest.setPosEntryModeCode(unionPayBean.getPosEntryModeCode());
		bankRequest.setAuthorizationCode(unionPayBean.getAuthorizationCode());
		bankRequest.setBankCustomerNo(unionPayBean.getCardAcceptorId());
		bankRequest.setBankPosCati(unionPayBean.getCardAcceptorTerminalId());
		bankRequest.setBatchNo(unionPayBean.getBatchNo());	
		
		BankChannelRouteReturnBean bankChannelRouteBean = extendPayBean.getBankChannelRouteBean();		
		bankRequest.setBankChannelCode(bankChannelRouteBean.getBankChannel().getCode());
		bankRequest.setBankInterfaceCode(bankChannelRouteBean.getBankChannel().getBankInterface().getCode());
		
		bankRequest.setSourceBankRequestId(unionPayBean.getSourcePosRequestId());
		bankRequest.setSourceTranDate(unionPayBean.getSourceTranDate());
		bankRequest.setCreateTime(new Date());
		bankRequest.setTransType(transType);
		bankRequest.setPosCati(extendPayBean.getPos().getPosCati());
		bankRequest.setAmount(extendPayBean.getTransAmount());	
		bankRequest.setCardInfo(extendPayBean.getCardInfo());
		bankRequest.setOrder(extendPayBean.getOrder());
		bankRequest.setPayment(extendPayBean.getPayment());
		
		bankRequest = bankRequestDao.create(bankRequest);		
		extendPayBean.setBankRequest(bankRequest);
		
		return extendPayBean;
	}

	public ExtendPayBean complete(ExtendPayBean extendPayBean) {
		BankRequest bankRequest = extendPayBean.getBankRequest();
		UnionPayBean bankResponseBean = extendPayBean.getUnionPayBean();
				
		if(bankResponseBean!=null){		
			bankRequest.setLocalTransDate(bankResponseBean.getDateLocalTransaction());
			bankRequest.setLocalTransTime(bankResponseBean.getTimeLocalTransaction());
			bankRequest.setBankExternalId(bankResponseBean.getRetrievalReferenceNumber());
			bankRequest.setAuthorizationCode(bankResponseBean.getAuthorizationCode());
			bankRequest.setBankResponseCode(bankResponseBean.getResponseCode());
			bankRequest.setCompleteTime(new Date());		
			bankRequest = bankRequestDao.update(bankRequest);		
			extendPayBean.setBankRequest(bankRequest);
		}	
		
		return extendPayBean;
	}
}
