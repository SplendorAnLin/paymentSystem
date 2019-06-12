package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.Constant;
import com.yl.pay.pos.action.trans.BaseTransaction;
import com.yl.pay.pos.bean.CustomerFeeBean;
import com.yl.pay.pos.bean.CustomerFeeReturnBean;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.entity.PaymentFee;
import com.yl.pay.pos.enums.TransStatus;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.service.IPaymentService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * Title: 订单交易  服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class PaymentServiceImpl extends BaseTransaction implements IPaymentService{
	private static final Log log = LogFactory.getLog(PaymentServiceImpl.class);
	
	public ExtendPayBean create(ExtendPayBean extendPayBean) {
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
		Payment payment = new Payment();
		payment.setTransType(extendPayBean.getTransType());											//交易类型
		payment.setCustomerNo(extendPayBean.getCustomer().getCustomerNo());							//POS商户号
		payment.setPosCati(extendPayBean.getPos().getPosCati());									//POS终端号
		payment.setPosBatch(extendPayBean.getPosRequest().getPosBatch());							//交易批次号
		payment.setPosRequestId(extendPayBean.getPosRequest().getPosRequestId());					//POS流水号
		payment.setPan(unionPayBean.getPan());														//卡号	
		payment.setAmount(extendPayBean.getTransAmount());											//交易金额
		payment.setCurrencyType(extendPayBean.getPosRequest().getCurrencyType());					//币种
		payment.setBankRequestId(unionPayBean.getSystemsTraceAuditNumber());						//银行请求号11域
		payment.setBankBatch(unionPayBean.getBatchNo());											//银行交易批次号
		payment.setBankPosCati(unionPayBean.getCardAcceptorTerminalId());							//银行终端号
		payment.setBankCustomerNo(unionPayBean.getCardAcceptorId());								//银行商户号		
		payment.setBankChannel(extendPayBean.getBankChannelRouteBean().getBankChannel());			//银行通道
		payment.setBankInterface(extendPayBean.getBankChannelRouteBean().getBankChannel().getBankInterface());//银行接口							
		payment.setBankCost(extendPayBean.getBankChannelRouteBean().getBankChannelCost());			//银行成本 
//		if(extendPayBean.getBankChannelRouteBean().getBankChannelCustomerConfig()!=null){
//			payment.setBankCustomerLevel(extendPayBean.getBankChannelRouteBean().
//					getBankChannelCustomerConfig().getCustomerLevel());								//银行商户级别
//		}
		payment.setStatus(TransStatus.INIT);														//状态
		payment.setCreateTime(new Date());															//交易时间
		payment.setIssuer(extendPayBean.getCardInfo().getIssuer());									//发卡银行
		payment.setOrder(extendPayBean.getOrder());													//订单
		payment.setCardInfo(extendPayBean.getCardInfo());											//卡信息		
		payment.setSourcePayment(extendPayBean.getSourcePayment());									//原交易信息
		if(TransType.NOT_SPECIFY_QUANCUN.name().equals(extendPayBean.getTransType().name())||
				TransType.NOT_SPECIFY_QUANCUN_REVERSAL.name().equals(extendPayBean.getTransType().name())){
		payment.setInnerPan(unionPayBean.getSwitchingData());//转入卡号
		}
		
		//计算交易手续费
		if(TransType.PURCHASE.equals(extendPayBean.getTransType())
				||TransType.PRE_AUTH.equals(extendPayBean.getTransType())
				||TransType.PRE_AUTH_COMP.equals(extendPayBean.getTransType())
				||TransType.OFFLINE_PURCHASE.equals(extendPayBean.getTransType())
				){
			
			CustomerFeeBean feeParam = new CustomerFeeBean(extendPayBean.getCustomer().getCustomerNo(), 
					extendPayBean.getCardInfo().getIssuer().getCode(), extendPayBean.getCardInfo().getCardType(), 
					extendPayBean.getTransAmount(), extendPayBean.getCustomer().getMcc());
			
			CustomerFeeReturnBean customerFee = customerFeeService.getCustomerFee(feeParam);
			payment.setCustomerFee(customerFee.getFee());
			
			PaymentFee paymentFee=new PaymentFee(extendPayBean.getBankChannelRouteBean().getBankChannelFeeCode(), extendPayBean.getBankChannelRouteBean().getBankChannelRate(), 
					customerFee.getCustomerFeeCode(), customerFee.getRate());
			paymentFee=paymentFeeDao.create(paymentFee);
			payment.setPaymentFee(paymentFee);
		}
						
		payment = paymentDao.create(payment);		
		extendPayBean.setPayment(payment);
		extendPayBean.getPayments().add(payment);
		
		return extendPayBean;
	}
	

	@Override
	public ExtendPayBean create(ExtendPayBean extendPayBean, TransType transType) {
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
		Payment payment = new Payment();
		payment.setTransType(transType);															//交易类型
		payment.setCustomerNo(extendPayBean.getCustomer().getCustomerNo());							//POS商户号
		payment.setPosCati(extendPayBean.getPos().getPosCati());									//POS终端号
		payment.setPan(extendPayBean.getPosRequest().getPan());										//卡号
		payment.setPosBatch(extendPayBean.getPosRequest().getPosBatch());							//交易批次号
		payment.setPosRequestId(extendPayBean.getPosRequest().getPosRequestId());					//POS流水号
		payment.setAmount(extendPayBean.getTransAmount());											//交易金额
		payment.setCurrencyType(extendPayBean.getPosRequest().getCurrencyType());					//币种
		payment.setBankRequestId(unionPayBean.getSystemsTraceAuditNumber());						//银行请求号11域
		payment.setBankBatch(unionPayBean.getBatchNo());											//银行交易批次号
		payment.setBankPosCati(unionPayBean.getCardAcceptorTerminalId());							//银行终端号
		payment.setBankCustomerNo(unionPayBean.getCardAcceptorId());								//银行商户号		
		payment.setBankChannel(extendPayBean.getBankChannelRouteBean().getBankChannel());			//银行通道
		payment.setBankInterface(extendPayBean.getBankChannelRouteBean().getBankChannel().getBankInterface());		//银行接口							
		payment.setBankCost(extendPayBean.getBankChannelRouteBean().getBankChannelCost());			//银行成本 
//		if(extendPayBean.getBankChannelRouteBean().getBankChannelCustomerConfig()!=null){
//			payment.setBankCustomerLevel(extendPayBean.getBankChannelRouteBean().
//					getBankChannelCustomerConfig().getCustomerLevel());								//银行商户级别
//		}
		
		payment.setStatus(TransStatus.INIT);														//状态
		payment.setCreateTime(new Date());															//交易时间
		payment.setIssuer(extendPayBean.getCardInfo().getIssuer());									//发卡银行
		payment.setOrder(extendPayBean.getOrder());													//订单
		payment.setCardInfo(extendPayBean.getCardInfo());											//卡信息	
		payment.setSourcePayment(extendPayBean.getSourcePayment());									//原交易信息
						
		payment = paymentDao.create(payment);		
		extendPayBean.setPayment(payment);
		extendPayBean.getPayments().add(payment);
		
		return extendPayBean;
	}



	public ExtendPayBean complete(ExtendPayBean extendPayBean) {
		Payment payment = extendPayBean.getPayment();
		if(Constant.SUCCESS_POS_RESPONSE.equals(extendPayBean.getResponseCode())){
			payment.setStatus(TransStatus.SUCCESS);
		}else {
			payment.setStatus(TransStatus.FAIL);
		}
		payment.setCompleteTime(new Date());	
		payment.setBankDate(extendPayBean.getUnionPayBean().getDateLocalTransaction());
		payment.setBankTime(extendPayBean.getUnionPayBean().getTimeLocalTransaction());
		payment.setBankExternalId(extendPayBean.getUnionPayBean().getRetrievalReferenceNumber());
		payment.setAuthorizationCode(extendPayBean.getUnionPayBean().getAuthorizationCode());
		String additionalResponseData = extendPayBean.getUnionPayBean().getAdditionalResponseData();
		if(StringUtils.isNotBlank(additionalResponseData) && additionalResponseData.length()<100){
			payment.setAdditionalRespData(additionalResponseData);
		}
		if(YesNo.Y.equals(extendPayBean.getIsForeignCard())){//境外卡需重新计算银行成本
			PaymentFee paymentFee  =payment.getPaymentFee();
			paymentFee.setBankChannelRate(extendPayBean.getBankChannelRouteBean().getBankChannelRate());
			paymentFee=paymentFeeDao.update(paymentFee);
			payment.setBankCost(extendPayBean.getBankChannelRouteBean().getBankChannelCost());
		}
		if(extendPayBean.getServiceInfo()!=null){
			payment.setServiceInfo(extendPayBean.getServiceInfo());
		}
		if(extendPayBean.getAquiringInstitutionId()!=null){
			payment.setAquiringInstitutionId(extendPayBean.getAquiringInstitutionId());
		}
		payment = paymentDao.update(payment);		
		extendPayBean.setPayment(payment);
		extendPayBean.getPayments().add(payment);
		
		return extendPayBean;
	}
	
	public List<Payment> findByOrder(Order order,String currentPage) {
		return paymentDao.findByOrder(order,currentPage);
	}

}
