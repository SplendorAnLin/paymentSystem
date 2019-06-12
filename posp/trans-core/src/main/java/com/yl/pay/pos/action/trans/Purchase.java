package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.BankChannelRouteReturnBean;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.entity.BankResponseDict;
import com.yl.pay.pos.entity.BankReversalTask;
import com.yl.pay.pos.entity.Customer;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.enums.CustomerType;
import com.yl.pay.pos.enums.ReversalType;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.exception.trans.BankNeedReversalException;
import com.yl.pay.pos.interfaces.IBankTransaction;
import com.yl.pay.pos.service.AccountBussinessService;
import com.yl.pay.pos.service.IOemOrderService;
import com.yl.pay.pos.service.ProxyPayInfoService;
import com.yl.pay.pos.service.ShareProfitService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * Title: 交易处理  - 消费
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun.yu
 */

public class Purchase extends BaseTransaction implements ITransaction {
	private static final Log log = LogFactory.getLog(Purchase.class);
	
	private AccountBussinessService accountBussinessService;
	
	private ShareProfitService shareProfitService;
	private ProxyPayInfoService proxyPayInfoService;
	
	private IOemOrderService oemOrderService;
	
	public AccountBussinessService getAccountBussinessService() {
		return accountBussinessService;
	}


	public void setAccountBussinessService(AccountBussinessService accountBussinessService) {
		this.accountBussinessService = accountBussinessService;
	}


	public ShareProfitService getShareProfitService() {
		return shareProfitService;
	}


	public void setShareProfitService(ShareProfitService shareProfitService) {
		this.shareProfitService = shareProfitService;
	}


	public ProxyPayInfoService getProxyPayInfoService() {
		return proxyPayInfoService;
	}


	public void setProxyPayInfoService(ProxyPayInfoService proxyPayInfoService) {
		this.proxyPayInfoService = proxyPayInfoService;
	}

	public IOemOrderService getOemOrderService() {
		return oemOrderService;
	}


	public void setOemOrderService(IOemOrderService oemOrderService) {
		this.oemOrderService = oemOrderService;
	}
	

	@Override
	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception{
		log.info("######## Purchase ########");
		
		extendPayBean = orderService.create(extendPayBean);											//创建订单
		log.info("create order posCati=" + extendPayBean.getPosRequest().getPosCati() + 
				" externalId=" + extendPayBean.getOrder().getExternalId());
		
		List<BankChannelRouteReturnBean> channelRoutes = getBankChannelRoutes(extendPayBean);		//获取可用银行通道列表		
		UnionPayBean posRequestBean = extendPayBean.getUnionPayBean().clone();
		UnionPayBean bankRequestBean = null;
		BankResponseDict bankResponseDict=null;
		
		for(int i=0;i<channelRoutes.size();i++){
			boolean isEnd = false;
			if(i == channelRoutes.size()-1){
				isEnd=true;
			}
			BankChannelRouteReturnBean channelRouteBean = channelRoutes.get(i);
			String interfaceCode = channelRouteBean.getBankChannel().getBankInterface().getCode();
			bankRequestBean = posRequestBean.clone();												//POS请求报文数据还原
			
			BankInterfaceTerminal bankTerminal = bankInterfaceTerminalService.loadUseableBankInterfaceTerminal(interfaceCode, channelRouteBean.getBankCustomerNo());
			if(bankTerminal == null){
				if(isEnd){
					throw new TransRunTimeException(TransExceptionConstant.NO_USEABLE_BANKTERMINAL);
				}
				continue;
			}			
			extendPayBean.getBankRequestTerminal().copy(bankTerminal);
			
			extendPayBean.setBankChannelRouteBean(channelRouteBean);
			String seqName = Constant.BANK_INTERFACE_SEQUENCE_HEAD + interfaceCode;
			bankRequestBean.setSystemsTraceAuditNumber(sequenceDao.generateSequence(seqName));		//银行流水号 11域 
			bankRequestBean.setBatchNo(bankTerminal.getBankBatch());								//批次号
			bankRequestBean.setCardAcceptorTerminalId(bankTerminal.getBankPosCati());				//银行终端号
			bankRequestBean.setCardAcceptorId(bankTerminal.getBankCustomerNo());					//银行商户号
			bankRequestBean.setCardAcceptorName(bankTerminal.getBankCustomerName());				//受卡方名称地址
			bankRequestBean.setMerchantType(bankTerminal.getMcc());									//银行商户类型MCC
			bankRequestBean.setRetrievalReferenceNumber(extendPayBean.getOrder().getExternalId());	//检索参考号
			extendPayBean.setUnionPayBean(bankRequestBean);		
			
			extendPayBean = paymentService.create(extendPayBean);									//创建订单交易
			extendPayBean = bankRequestService.create(extendPayBean);								//创建银行交易请求	

			//银行接口调用
			UnionPayBean bankResponseBean = null;
			String beanName = StringUtil.replaceUnderline(extendPayBean.getTransType().name()) + interfaceCode;
			IBankTransaction bankTransaction = bankTransactionFactory.getBankTransaction(beanName);
			try {
				 bankResponseBean = bankTransaction.execute(extendPayBean);	
				 bankResponseDict= bankResponseDictDao.findByCode(interfaceCode, bankResponseBean.getResponseCode()); //银行返回码处理
				
			} catch (BankNeedReversalException e) {
				log.info("BankNeedReversalException ",e);				
				try {
					internalReversalService.processReversal(extendPayBean);
				} catch (Exception e1) {
					log.info("InternalReversal fail ",e1);
					//创建银行冲正任务
					bankReversalTaskService.create(new BankReversalTask(extendPayBean.getPayment(),bankRequestBean.getAmount(),bankRequestBean.getPosEntryModeCode(),ReversalType.SYSTEM_REVERSAL));
					throw new TransRunTimeException(TransExceptionConstant.BANK_TRX_HANDLE_TIMEOUT);
				}
				bankInterfaceTerminalService.terminalRecover(bankTerminal);
				 if(isEnd){
					throw new TransRunTimeException(TransExceptionConstant.BANK_TRX_HANDLE_TIMEOUT);
				} else{
					continue;
				}
			} catch (Throwable e) {
				log.info("bankTransHandleFail ",e);
				bankInterfaceTerminalService.terminalRecover(bankTerminal);
				if(isEnd){
					throw new TransRunTimeException(TransExceptionConstant.BANK_TRX_HANDLE_FAIL);
				}else{
					continue;
				}
			}	
			
			extendPayBean.setUnionPayBean(bankResponseBean);
			extendPayBean = bankRequestService.complete(extendPayBean);								//完成银行请求						
			extendPayBean.setResponseCode(bankResponseDict.getMappingCode());
			extendPayBean.setExceptionCode(bankResponseDict.getExceptionCode());
			extendPayBean = paymentService.complete(extendPayBean);									//完成支付记录			

			if(Constant.SUCCESS_POS_RESPONSE.equals(bankResponseDict.getMappingCode())){
				break;
			}		
		}
		
		if(Constant.SUCCESS_POS_RESPONSE.equals(bankResponseDict.getMappingCode())){
			
			extendPayBean = orderService.complete(extendPayBean);									//完成订单	
			
			Order order = extendPayBean.getOrder();
			
			//入账
			accountBussinessService.specialCompositeTally(order);
			
			//判断是否为OME商户
			Customer customer = extendPayBean.getCustomer();
			if(CustomerType.OEM.equals(customer.getCustomerType())){
				Map<String,String> respMap = oemOrderService.complete(extendPayBean.getOrder());
				if (!"SUCCESS".equals(respMap.get("result"))){
					log.error(extendPayBean.getOrder().getExternalId()+"|"+respMap.get("respCode")+"|"+respMap.get("respMsg"));
				}
			}
			
			//修改订单入账状态
			orderService.update(order);
			
			//分润
			shareProfitService.createShareProfit(order);
			
//			if(RouteType.D0.equals(extendPayBean.getPos().getRouteType())){
//				
//				try {
//					ProxyPayInfo proxyPayInfo = proxyPayInfoService.generateProxyPayInfo(extendPayBean);
//					
//					//入账
//					accountBussinessService.specialCompositeTally(proxyPayInfo);
//					
//					//代付
//					proxyPayInfoService.proxyPay(proxyPayInfo);
//				} catch (Exception e) {
//					log.info("++++++++++++组装代付信息异常,orderNo:"+extendPayBean.getOrder().getPosRequestId(),e);
//				}
//			}
		}
		
		//信息转换
		posRequestBean.setResponseCode(bankResponseDict.getMappingCode());							//39
		posRequestBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));							//13
		posRequestBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));						//12
		posRequestBean.setDateSettlement(DateUtil.formatNow("MMdd"));								//14
		posRequestBean.setAdditionalResponseData(ISO8583Utile.putIssuerandAcquirer(
				extendPayBean.getBankIdNumber()==null?"":extendPayBean.getBankIdNumber().getBin(), "")); //44
		posRequestBean.setRetrievalReferenceNumber(extendPayBean.getOrder().getExternalId());		//37
		posRequestBean.setAuthorizationCode(extendPayBean.getUnionPayBean().getAuthorizationCode());//38
		posRequestBean.setICSystemRelated(extendPayBean.getUnionPayBean().getICSystemRelated());	//55
		posRequestBean.setOperator("CUP");															//63.1
		
		extendPayBean.setUnionPayBean(posRequestBean);		
		return extendPayBean;
	}
	
}
