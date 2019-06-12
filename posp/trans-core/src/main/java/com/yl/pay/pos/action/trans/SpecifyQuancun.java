package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.BankChannelRouteReturnBean;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.BankInterfaceSwitchConfig;
import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.entity.BankResponseDict;
import com.yl.pay.pos.entity.BankReversalTask;
import com.yl.pay.pos.enums.ReversalType;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.exception.trans.BankNeedReversalException;
import com.yl.pay.pos.interfaces.IBankTransaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * 指定账户圈存
 * @author haitao.liu
 *
 */
public class SpecifyQuancun extends BaseTransaction implements ITransaction{
	private static final Log log = LogFactory.getLog(SpecifyQuancun.class);
	
	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception{
		log.info("######## SpecifyQuancun ########");
		
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
			channelRouteBean.setBankChannelCost(0.40);
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
			
			extendPayBean.setUnionPayBean(bankResponseBean);
			extendPayBean = bankRequestService.complete(extendPayBean);								//完成银行请求						
			bankResponseDict= bankResponseDictDao.findByCode(interfaceCode, 
					bankResponseBean.getResponseCode());											//银行返回码处理
			extendPayBean.setResponseCode(bankResponseDict.getMappingCode());
			extendPayBean = paymentService.complete(extendPayBean);									//完成支付记录			

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
		
		if(Constant.SUCCESS_POS_RESPONSE.equals(bankResponseDict.getMappingCode())){
			extendPayBean = orderService.complete(extendPayBean);									//完成订单				
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
