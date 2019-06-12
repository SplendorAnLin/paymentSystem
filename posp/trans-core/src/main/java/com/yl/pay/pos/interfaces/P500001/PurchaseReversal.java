package com.yl.pay.pos.interfaces.P500001;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.interfaces.BaseBankTransaction;
import com.yl.pay.pos.interfaces.IBankTransaction;

/**
 * Title: 银行交易处理  - 消费冲正
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author xiaojun.yang
 */

public class PurchaseReversal extends BaseBankTransaction implements IBankTransaction {
		
	private static final Log log = LogFactory.getLog(PurchaseReversal.class);

	private NCCBTransUtil cmbcTransUtil;
	
	public UnionPayBean execute(ExtendPayBean extendPayBean) throws Exception{
		
		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		
		log.info("重庆民生Purchase : [interfaceCode=" + Constant.INTERFACE_CODE
				+ "],[BankCustomer=" + unionPayBean.getCardAcceptorId()
				+ "],[BankCati=" + unionPayBean.getCardAcceptorTerminalId()
				+ "]");	
		Payment sourcePayment = extendPayBean.getSourcePayment();
		CmbcISO8583 ccbRequest = new CmbcISO8583();
		ccbRequest.putItem(0, Constant.TRANS_TYPE_REVESAL); 				// 交易类型
		ccbRequest.putItem(2, unionPayBean.getPan()); 						// 主账号
		ccbRequest.putItem(3, Constant.PROC_PURCHASE); 						// 处理码
		ccbRequest.putItem(4, unionPayBean.getAmount()); 					// 交易金额
		ccbRequest.putItem(11,sourcePayment.getBankRequestId()); 	// 系统跟踪号

		String enterMode=unionPayBean.getPosEntryModeCode();
		ccbRequest.putItem(22, enterMode); 									// 输入方式,刷卡有密码【021】
		if(enterMode.equals(Constant.ENTRY_MODE_051) || enterMode.equals(Constant.ENTRY_MODE_052)||enterMode.equals(Constant.ENTRY_MODE_071) || enterMode.equals(Constant.ENTRY_MODE_072)){
			if(StringUtil.notNull(unionPayBean.getDateExpiration())){
				ccbRequest.putItem(14, unionPayBean.getDateExpiration());
			}
			if(unionPayBean.getCardSequenceNumber()!=null){
				ccbRequest.putItem(23, unionPayBean.getCardSequenceNumber()); // 卡序列号
			}
			ccbRequest.putItem(26, "12");
			ccbRequest.putItem(53, "2600000000000000");
			if(StringUtil.notNull(unionPayBean.getICSystemRelated())){
				ccbRequest.putItem(55, unionPayBean.getICSystemRelated());
			}
		}
		ccbRequest.putItem(25, "00");										// 服务代码00
		if(unionPayBean.getTrack2()!=null){
		ccbRequest.putItem(35, unionPayBean.getTrack2()); 					// 2磁道
		}
		ccbRequest.putItem(39, Constant.BANK_RESP_CODE_98); 
		ccbRequest.putItem(41, unionPayBean.getCardAcceptorTerminalId());	 						// 银行终端号
		ccbRequest.putItem(42, unionPayBean.getCardAcceptorId());									// 银行商户号
		ccbRequest.putItem(49, Constant.CURRCY_CODE_TRANS); 										// 币种 ,人民币【156】
		ccbRequest.putItem(601, "22");
		ccbRequest.putItem(602, unionPayBean.getBatchNo());//签到返回批次号
		ccbRequest.putItem(603, Constant.NETWORKMESSAGECODE);
		ccbRequest.putItem(604, "5");
		if(unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_071)
                || unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_072)){
			ccbRequest.putItem(604, "6");
        }
		ccbRequest.putItem(605,"0");
		
		UnionPayBean responseBean = new UnionPayBean();
		try {					
			responseBean =cmbcTransUtil.send2Bank(ccbRequest);	
		}catch(Exception e){
			log.info("nccbTransService send2Bank error",e);
			throw new TransRunTimeException(TransExceptionConstant.INTERFACE_EXCEPTION);
		}		
			
		return responseBean;
	}

	public NCCBTransUtil getCmbcTransUtil() {
		return cmbcTransUtil;
	}

	public void setCmbcTransUtil(NCCBTransUtil cmbcTransUtil) {
		this.cmbcTransUtil = cmbcTransUtil;
	}
public void test(){
		
		ExtendPayBean extendPayBean = new ExtendPayBean();
		UnionPayBean unionPayBean = new UnionPayBean();
		unionPayBean.setCardAcceptorId(Constant.TEST_BANK_CUSTOMERNO);
		unionPayBean.setCardAcceptorTerminalId(Constant.TEST_BANK_POSCATI);
		unionPayBean.setSystemsTraceAuditNumber("000020");
		unionPayBean.setAmount("000000001000");
		unionPayBean.setPosEntryModeCode("021");
		unionPayBean.setPan("6225880160777257");
		unionPayBean.setTrack2("6225880160777257=49121200614700363431");
		unionPayBean.setBatchNo("000002");
		extendPayBean.setUnionPayBean(unionPayBean);
		 Payment sourcePayment = new Payment();
	        sourcePayment.setBankRequestId("000020");//原始交易流水号
//	        sourcePayment.setBankExternalId("978990080039");   //原始交易响应37域的值，原始交易检索号
	        extendPayBean.setSourcePayment(sourcePayment);
		try {
			execute(extendPayBean);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	

}
