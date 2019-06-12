package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.BankChannelRouteReturnBean;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.BankInterfaceSwitchConfig;
import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.entity.BankResponseDict;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.exception.trans.BankNeedReversalException;
import com.yl.pay.pos.interfaces.IBankTransaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Title: 交易处理  - 余额查询
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BalanceInquiry extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(BalanceInquiry.class);
	
	public ExtendPayBean execute(ExtendPayBean extendPayBean){
		log.info("######## BalanceInquiry ########");
			
		List<BankChannelRouteReturnBean> channelRoutes = getBankChannelRoutes(extendPayBean);	//获取可用银行通道列表		
		UnionPayBean posRequestBean = extendPayBean.getUnionPayBean().clone();
		UnionPayBean bankRequestBean = null;
		BankResponseDict bankResponseDict=null;
		String additionalAmount=null;

		for(int i=0;i<channelRoutes.size();i++){
			boolean isEnd = false;
			if(i == channelRoutes.size()-1){
				isEnd=true;
			}
			BankChannelRouteReturnBean channelRouteBean = channelRoutes.get(i);
			bankRequestBean = posRequestBean.clone();												//POS请求报文数据还原
			String interfaceCode = channelRouteBean.getBankChannel().getBankInterface().getCode();
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
			bankRequestBean.setCardAcceptorName(bankTerminal.getBankCustomerName());			//受卡方名称地址
			bankRequestBean.setMerchantType(bankTerminal.getMcc());									//银行商户类型MCC
			
			extendPayBean.setUnionPayBean(bankRequestBean);		
			
			extendPayBean = paymentService.create(extendPayBean);									//创建订单交易
			extendPayBean = bankRequestService.create(extendPayBean);								//创建银行交易请求	

			//银行接口调用
			UnionPayBean bankResponseBean = null;
			String beanName = StringUtil.replaceUnderline(extendPayBean.getTransType().name()) + interfaceCode;
			IBankTransaction bankTransaction = bankTransactionFactory.getBankTransaction(beanName);
			try {
				 bankResponseBean = bankTransaction.execute(extendPayBean);			
			} catch (BankNeedReversalException e) {
				log.info("BankNeedReversalException ",e);				
				try {
					internalReversalService.processReversal(extendPayBean);
				} catch (Exception e1) {
					log.info("",e);
					throw new TransRunTimeException(TransExceptionConstant.BANK_TRX_HANDLE_TIMEOUT);
				}
				bankInterfaceTerminalService.terminalRecover(bankTerminal);
				if(isEnd){
					throw new TransRunTimeException(TransExceptionConstant.BANK_TRX_HANDLE_TIMEOUT);
				}else{
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
			
			additionalAmount=bankResponseBean.getAdditionalAmount();								//卡余额信息
			extendPayBean.setUnionPayBean(bankResponseBean);
			extendPayBean = bankRequestService.complete(extendPayBean);								//完成银行请求						
			bankResponseDict= bankResponseDictDao.findByCode(interfaceCode, 
					bankResponseBean.getResponseCode());											//银行返回码处理
			extendPayBean.setResponseCode(bankResponseDict.getMappingCode());
			extendPayBean = paymentService.complete(extendPayBean);									//完成订单交易			
			
			if(Constant.SUCCESS_POS_RESPONSE.equals(bankResponseDict.getMappingCode())){
				break;
			}else{
				bankInterfaceTerminalService.terminalRecover(bankTerminal);
				List<BankInterfaceSwitchConfig> interfaceSwitchs=bankInterfaceSwitchConfigDao.findByInterfaceCodeAndRespCode(interfaceCode, bankResponseBean.getResponseCode());
				if(interfaceSwitchs==null||interfaceSwitchs.isEmpty()){
					break;
				}
			}			
		}
		
		posRequestBean.setResponseCode(bankResponseDict.getMappingCode());							//39
		posRequestBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));							//13
		posRequestBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));						//12
		posRequestBean.setDateSettlement(DateUtil.formatNow("MMdd"));								//14
		posRequestBean.setICSystemRelated(extendPayBean.getUnionPayBean().getICSystemRelated());	//55
		posRequestBean.setAdditionalAmount(additionalAmount);	
		posRequestBean.setRetrievalReferenceNumber(extendPayBean.getUnionPayBean().getRetrievalReferenceNumber());
		extendPayBean.setUnionPayBean(posRequestBean);		
		return extendPayBean;
	}
}
