package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.BankResponseDict;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.interfaces.IBankTransaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Title: 系统内部冲正服务
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class InternalReversalService extends BaseTransaction{
	private static final Log log = LogFactory.getLog(InternalReversalService.class);
	
	public ExtendPayBean processReversal(ExtendPayBean extendPayBean) throws Exception{
		log.info("########## InternalReversalService start #########");
		UnionPayBean bankRequestBean=extendPayBean.getUnionPayBean();
		Payment sourcePayment=extendPayBean.getPayment();
		
		TransType transType=null;
		if(TransType.PURCHASE.equals(extendPayBean.getTransType())){
			transType=TransType.PURCHASE_REVERSAL;
		}else if(TransType.PURCHASE_VOID.equals(extendPayBean.getTransType())){
			transType=TransType.PURCHASE_VOID_REVERSAL;
		}else if(TransType.PRE_AUTH.equals(extendPayBean.getTransType())){
			transType=TransType.PRE_AUTH_REVERSAL;
		}else if(TransType.PRE_AUTH_VOID.equals(extendPayBean.getTransType())){
			transType=TransType.PRE_AUTH_VOID_REVERSAL;
		}else if(TransType.PRE_AUTH_COMP.equals(extendPayBean.getTransType())){
			transType=TransType.PRE_AUTH_COMP_REVERSAL;
		}else if(TransType.PRE_AUTH_COMP_VOID.equals(extendPayBean.getTransType())){
			transType=TransType.PRE_AUTH_COMP_VOID_REVERSAL;
		}else if(TransType.SPECIFY_QUANCUN.equals(extendPayBean.getTransType())){
			transType=TransType.SPECIFY_QUANCUN_REVERSAL;
		}else if(TransType.NOT_SPECIFY_QUANCUN.equals(extendPayBean.getTransType())){
			transType=TransType.NOT_SPECIFY_QUANCUN_REVERSAL;
		}else if(TransType.CASH_RECHARGE_QUNCUN.equals(extendPayBean.getTransType())){
			transType=TransType.CASH_RECHARGE_QUNCUN_REVERSAL;
		}
		
		String interfaceCode = extendPayBean.getBankChannelRouteBean().getBankChannel().getBankInterface().getCode();
		String seqName = Constant.BANK_INTERFACE_SEQUENCE_HEAD + interfaceCode;
		bankRequestBean.setSystemsTraceAuditNumber(sequenceDao.generateSequence(seqName));		//银行流水号 11域 
		bankRequestBean.setTimeLocalTransaction(DateUtil.formatDate(sourcePayment.getCreateTime(), "HHmmss"));		//时间 12域
		bankRequestBean.setDateLocalTransaction(DateUtil.formatDate(sourcePayment.getCreateTime(), "MMdd"));		//日期 13域
		bankRequestBean.setSourcePosRequestId(sourcePayment.getBankRequestId());				//原银行流水号
		bankRequestBean.setSourceTranDate(sourcePayment.getBankDate()+sourcePayment.getBankTime());//原银行交易日期+时间
		bankRequestBean.setBatchNo(sourcePayment.getBankBatch());								//批次号
		bankRequestBean.setCardAcceptorTerminalId(sourcePayment.getBankPosCati());				//银行终端号
		bankRequestBean.setCardAcceptorId(sourcePayment.getBankCustomerNo());					//银行商户号
		extendPayBean.setUnionPayBean(bankRequestBean);		
		extendPayBean.setSourcePayment(sourcePayment);
		
		extendPayBean = paymentService.create(extendPayBean,transType);							//创建Payment
		extendPayBean = bankRequestService.create(extendPayBean,transType);						//创建BankRequest	
		
		UnionPayBean bankResponseBean = null;
		String beanName = StringUtil.replaceUnderline(transType.name()) + interfaceCode;
		IBankTransaction bankTransaction = bankTransactionFactory.getBankTransaction(beanName);
		//银行接口调用
		bankResponseBean = bankTransaction.execute(extendPayBean);
		
		extendPayBean.setUnionPayBean(bankResponseBean);
		extendPayBean = bankRequestService.complete(extendPayBean);							//完成BankRequest
		
		BankResponseDict bankResponseDict = bankResponseDictDao.findByCode(interfaceCode, bankResponseBean.getResponseCode());	//银行返回码处理
		extendPayBean.setResponseCode(bankResponseDict.getMappingCode());
		extendPayBean = paymentService.complete(extendPayBean);								//完成Payment		
		
		log.info("########## InternalReversalService end #########");
		return extendPayBean;
	}
	
}


