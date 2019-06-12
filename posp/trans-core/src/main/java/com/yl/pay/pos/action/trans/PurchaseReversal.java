package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.BankChannelRouteReturnBean;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.*;
import com.yl.pay.pos.enums.*;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.exception.trans.BankNeedReversalException;
import com.yl.pay.pos.interfaces.IBankTransaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * Title: 交易处理  - 消费冲正
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class PurchaseReversal extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(PurchaseReversal.class);

	public ExtendPayBean execute(ExtendPayBean extendPayBean){
		log.info("######## PurchaseReversal ########");
		
		//加载原交易，如无匹配的原交易，直接返回成功
		Order sourceOrder = orderDao.findSourceOrder(extendPayBean.getPosRequest().getPosCati(), extendPayBean.getPosRequest().getPosBatch(), extendPayBean.getPosRequest().getPosRequestId());
		if(sourceOrder==null||!OrderStatus.SUCCESS.equals(sourceOrder.getStatus())){
			extendPayBean.getUnionPayBean().setResponseCode(Constant.SUCCESS_POS_RESPONSE);
			extendPayBean.getUnionPayBean().setDateLocalTransaction(DateUtil.formatNow("MMdd"));
			extendPayBean.getUnionPayBean().setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));
			extendPayBean.getUnionPayBean().setDateSettlement(DateUtil.formatNow("MMdd"));
			return extendPayBean;
		}
		
		Date tempTime=DateUtil.parseToTodayDesignatedDate(new Date(), Constant.SETTLE_END_TIME);
		if(tempTime.compareTo(sourceOrder.getCreateTime())>0){
			throw new TransRunTimeException(TransExceptionConstant.REVERSAL_TIME_ERR);
		}
		if(YesNo.Y.equals(sourceOrder.getCreditStatus())){
			throw new TransRunTimeException(TransExceptionConstant.SOURCE_ORDER_CREDITED);
		}
		extendPayBean.setOrder(sourceOrder);
		
		
		extendPayBean.getUnionPayBean().setPan(sourceOrder.getPan());
		CardInfo cardInfo=cardInfoDao.findById(sourceOrder.getCardInfo().getId());
		extendPayBean.setCardInfo(cardInfo);
		
		List<BankChannelRouteReturnBean> channelRoutes = getBankChannelRoutes(extendPayBean);	//获取可用银行通道
		Payment sourcePayment=paymentDao.findSourcePayment(sourceOrder, TransStatus.SUCCESS, TransType.PURCHASE);
		if(sourcePayment==null){
			throw new TransRunTimeException(TransExceptionConstant.SOURCE_PAYMENT_IS_NULL);
		}
		extendPayBean.setSourcePayment(sourcePayment);
		
		//判断订单入账情况，交易已入账需要出账才能撤销
//		accountOpService.accountDebit(sourceOrder, extendPayBean);
		
		UnionPayBean posRequestBean = extendPayBean.getUnionPayBean().clone();
		UnionPayBean bankRequestBean = extendPayBean.getUnionPayBean();
		
		BankChannelRouteReturnBean channelRouteBean = channelRoutes.get(0);
		
		String interfaceCode = channelRouteBean.getBankChannel().getBankInterface().getCode();
		BankInterfaceTerminal bankTerminal = bankInterfaceTerminalService.loadUseableBankInterfaceTerminal(interfaceCode, channelRouteBean.getBankCustomerNo(),sourcePayment.getBankPosCati());			
		if(bankTerminal == null){
			throw new TransRunTimeException(TransExceptionConstant.NO_USEABLE_BANKTERMINAL);
		}			
		extendPayBean.getBankRequestTerminal().copy(bankTerminal);							
		
		extendPayBean.setBankChannelRouteBean(channelRouteBean);
		String seqName = Constant.BANK_INTERFACE_SEQUENCE_HEAD + interfaceCode;
		bankRequestBean.setSystemsTraceAuditNumber(sequenceDao.generateSequence(seqName));		//银行流水号 11域 
		bankRequestBean.setTimeLocalTransaction(DateUtil.formatDate(sourcePayment.getCreateTime(), "HHmmss"));		//时间 12域
		bankRequestBean.setDateLocalTransaction(DateUtil.formatDate(sourcePayment.getCreateTime(), "MMdd"));		//日期 13域
		bankRequestBean.setSourcePosRequestId(sourcePayment.getBankRequestId());				//原银行流水号
		bankRequestBean.setSourceTranDate(sourcePayment.getBankDate()+sourcePayment.getBankTime());//原银行交易日期+时间
		bankRequestBean.setBatchNo(bankTerminal.getBankBatch());								//批次号
		bankRequestBean.setCardAcceptorTerminalId(bankTerminal.getBankPosCati());				//银行终端号
		bankRequestBean.setCardAcceptorId(bankTerminal.getBankCustomerNo());					//银行商户号
		bankRequestBean.setCardAcceptorName(bankTerminal.getBankCustomerName());			//受卡方名称地址
		bankRequestBean.setMerchantType(bankTerminal.getMcc());									//银行商户类型MCC
		bankRequestBean.setRetrievalReferenceNumber(extendPayBean.getOrder().getExternalId());	//检索参考号
		extendPayBean.setUnionPayBean(bankRequestBean);		
		
		extendPayBean = paymentService.create(extendPayBean);									//创建Payment
		extendPayBean = bankRequestService.create(extendPayBean);								//创建BankRequest	
		sourceOrder.setStatus(OrderStatus.REVERSALED);
		sourceOrder=orderService.update(sourceOrder);
		extendPayBean.setOrder(sourceOrder);
		//银行接口调用
		UnionPayBean bankResponseBean = null;
		String beanName = StringUtil.replaceUnderline(extendPayBean.getTransType().name()) + interfaceCode;
		IBankTransaction bankTransaction = bankTransactionFactory.getBankTransaction(beanName);
		try {
			 bankResponseBean = bankTransaction.execute(extendPayBean);			
		} catch (BankNeedReversalException e) {
			log.info("BankNeedReversalException ",e);		
			bankReversalTaskService.create(new BankReversalTask(extendPayBean.getPayment(),bankRequestBean.getAmount(),bankRequestBean.getPosEntryModeCode(),ReversalType.POS_REVERSAL));
			throw new TransRunTimeException(TransExceptionConstant.BANK_TRX_HANDLE_TIMEOUT);
		} catch (Throwable e) {
			log.info("bankTransHandleFail ",e);
			bankReversalTaskService.create(new BankReversalTask(extendPayBean.getPayment(),bankRequestBean.getAmount(),bankRequestBean.getPosEntryModeCode(),ReversalType.POS_REVERSAL));
			throw new TransRunTimeException(TransExceptionConstant.BANK_TRX_HANDLE_FAIL);
		}	
		
		extendPayBean.setUnionPayBean(bankResponseBean);
		extendPayBean = bankRequestService.complete(extendPayBean);							//完成BankRequest
		
		BankResponseDict bankResponseDict = bankResponseDictDao.findByCode(interfaceCode, bankResponseBean.getResponseCode());	//银行返回码处理
		extendPayBean.setResponseCode(bankResponseDict.getMappingCode());
		extendPayBean = paymentService.complete(extendPayBean);								//完成Payment			
		
		//更改订单状态
		if(!Constant.SUCCESS_POS_RESPONSE.equals(bankResponseDict.getMappingCode())){
			sourceOrder.setStatus(OrderStatus.SUCCESS);
			sourceOrder=orderService.update(sourceOrder);
			extendPayBean.setOrder(sourceOrder);
		}
		
		//信息转换
		posRequestBean.setResponseCode(bankResponseDict.getMappingCode());
		posRequestBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));
		posRequestBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));
		posRequestBean.setDateSettlement(DateUtil.formatNow("MMdd"));
		extendPayBean.setUnionPayBean(posRequestBean);
		
		return extendPayBean;
	}	
}
