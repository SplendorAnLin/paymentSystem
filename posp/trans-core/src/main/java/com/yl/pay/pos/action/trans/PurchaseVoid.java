package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
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
 * Title: 交易处理  - 消费撤销
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class PurchaseVoid extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(PurchaseVoid.class);

	public ExtendPayBean execute(ExtendPayBean extendPayBean){
		log.info("######## PurchaseVoid ########");
		PosRequest posRequest=extendPayBean.getPosRequest();				
		//原Order及原Payment加载及校验
		List<Order> orders=orderDao.findSourceOrder(posRequest.getPosCati(), posRequest.getSourceBatchNo(), posRequest.getSourcePosRequestId(), posRequest.getPan(), posRequest.getAmount());
		if(orders == null||orders.isEmpty()){
			throw new TransRunTimeException(TransExceptionConstant.SOURCE_ORDER_ISNULL);		
		}
		Order sourceOrder=orders.get(0);
		if(YesNo.Y.equals(sourceOrder.getCreditStatus())){
			throw new TransRunTimeException(TransExceptionConstant.SOURCE_ORDER_CREDITED);
		}
		Date tempTime=DateUtil.parseToTodayDesignatedDate(new Date(), Constant.SETTLE_END_TIME);
		if(tempTime.compareTo(sourceOrder.getCreateTime())>0){
			throw new TransRunTimeException(TransExceptionConstant.PURCHASE_VOID_TIMEERR);
		}
		extendPayBean.setOrder(sourceOrder);
		Payment sourcePayment = paymentDao.findSourcePayment(sourceOrder, TransStatus.SUCCESS, TransType.PURCHASE);
		if(sourcePayment==null){
			throw new TransRunTimeException(TransExceptionConstant.SOURCE_PAYMENT_IS_NULL);
		}
		
		//校验撤销黑名单商户
		List<TransCheckProfile> checkProfiles=transCheckProfileService.findByBizTypeAndProfileTypeAndProfileValue("PurchaseVoidBlacklist", "CUSTOMER", extendPayBean.getCustomer().getCustomerNo());
		if(checkProfiles!=null&&checkProfiles.size()>0){
			extendPayBean.getUnionPayBean().setResponseCode("96");
			extendPayBean.getUnionPayBean().setDateLocalTransaction(DateUtil.formatNow("MMdd"));
			extendPayBean.getUnionPayBean().setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));
			extendPayBean.getUnionPayBean().setDateSettlement(DateUtil.formatNow("MMdd"));
			return extendPayBean;
		}
		extendPayBean.setSourcePayment(sourcePayment);
		//判断订单入账情况，交易已入账需要出账才能撤销
//		accountOpService.accountDebit(sourceOrder, extendPayBean);
		//银行路由
		List<BankChannelRouteReturnBean> channelRoutes = getBankChannelRoutes(extendPayBean);	
		BankChannelRouteReturnBean channelRouteBean = channelRoutes.get(0);		
		UnionPayBean posRequestBean = extendPayBean.getUnionPayBean().clone();	
		UnionPayBean bankRequestBean = extendPayBean.getUnionPayBean();
		
		String interfaceCode = channelRouteBean.getBankChannel().getBankInterface().getCode();
		BankInterfaceTerminal bankTerminal = bankInterfaceTerminalService.loadUseableBankInterfaceTerminal(interfaceCode, channelRouteBean.getBankCustomerNo(),sourcePayment.getBankPosCati());			
		if(bankTerminal == null){
			throw new TransRunTimeException(TransExceptionConstant.NO_USEABLE_BANKTERMINAL);
		}
		extendPayBean.getBankRequestTerminal().copy(bankTerminal);								//复制银行终端属性,在总控层进行终端回收		
		extendPayBean.setBankChannelRouteBean(channelRouteBean);
		
		//准备银行请求Bean
		String seqName = Constant.BANK_INTERFACE_SEQUENCE_HEAD + interfaceCode;
		bankRequestBean.setSystemsTraceAuditNumber(sequenceDao.generateSequence(seqName));			//银行流水号 11域 
		bankRequestBean.setSourcePosRequestId(sourcePayment.getBankRequestId());					//原银行流水号
		bankRequestBean.setSourceTranDate(sourcePayment.getBankDate()+sourcePayment.getBankTime());	//原银行交易日期+时间
		bankRequestBean.setBatchNo(bankTerminal.getBankBatch());									//批次号
		bankRequestBean.setCardAcceptorTerminalId(bankTerminal.getBankPosCati());					//银行终端号
		bankRequestBean.setCardAcceptorId(bankTerminal.getBankCustomerNo());						//银行商户号
		bankRequestBean.setCardAcceptorName(bankTerminal.getBankCustomerName());					//受卡方名称地址
		bankRequestBean.setMerchantType(bankTerminal.getMcc());										//银行商户类型MCC
		bankRequestBean.setRetrievalReferenceNumber(extendPayBean.getOrder().getExternalId());		//检索参考号
		bankRequestBean.setAuthorizationCode(sourcePayment.getAuthorizationCode());					//授权码
		extendPayBean.setUnionPayBean(bankRequestBean);		
		
		extendPayBean = paymentService.create(extendPayBean);										//创建Payment
		extendPayBean = bankRequestService.create(extendPayBean);									//创建银行交易请求
		//订单状态改为已撤销
		sourceOrder.setStatus(OrderStatus.REPEAL);
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
			bankReversalTaskService.create(new BankReversalTask(extendPayBean.getPayment(),bankRequestBean.getAmount(),"022",ReversalType.PURCHASE_VOID));
//			throw new TransRunTimeException(TransExceptionConstant.BANK_TRX_HANDLE_TIMEOUT);
		} catch (Throwable e2) {
			log.info("bankTransHandleFail ",e2);
			bankReversalTaskService.create(new BankReversalTask(extendPayBean.getPayment(),bankRequestBean.getAmount(),"022",ReversalType.PURCHASE_VOID));
//			throw new TransRunTimeException(TransExceptionConstant.BANK_TRX_HANDLE_FAIL);
		}	
		
		String posResponseCode=Constant.SUCCESS_POS_RESPONSE;
		String authorizationCode="";
		if(bankResponseBean!=null){
			extendPayBean.setUnionPayBean(bankResponseBean);
			extendPayBean = bankRequestService.complete(extendPayBean);								//完成银行请求
			BankResponseDict bankResponseDict = bankResponseDictDao.findByCode(interfaceCode, bankResponseBean.getResponseCode());	//银行返回码处理
			posResponseCode=bankResponseDict.getMappingCode();
			extendPayBean.setResponseCode(posResponseCode);
			extendPayBean = paymentService.complete(extendPayBean);									//完成Payment		
			
			//更改订单状态
			if(!Constant.SUCCESS_POS_RESPONSE.equals(bankResponseDict.getMappingCode())){
				sourceOrder.setStatus(OrderStatus.SUCCESS);
				sourceOrder=orderService.update(sourceOrder);
				extendPayBean.setOrder(sourceOrder);
			}
			authorizationCode=bankResponseBean.getAuthorizationCode();
		}
		
		extendPayBean.setResponseCode(posResponseCode);
		//信息转换					
		posRequestBean.setResponseCode(posResponseCode);
		posRequestBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));
		posRequestBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));
		posRequestBean.setDateSettlement(DateUtil.formatNow("MMdd"));
		posRequestBean.setAquiringInstitutionId("000001");
		posRequestBean.setAuthorizationCode(authorizationCode);
		posRequestBean.setAdditionalResponseData(ISO8583Utile.putIssuerandAcquirer(
				extendPayBean.getBankIdNumber()==null?"":extendPayBean.getBankIdNumber().getBin(), "")); //44
		posRequestBean.setOperator("CUP");		
		extendPayBean.setUnionPayBean(posRequestBean);
		
		return extendPayBean;
	}
	
	
}
