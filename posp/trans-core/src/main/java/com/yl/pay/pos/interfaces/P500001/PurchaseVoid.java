package com.yl.pay.pos.interfaces.P500001;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.entity.Pos;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.trans.BankNeedReSignInException;
import com.yl.pay.pos.exception.trans.BankNeedReversalException;
import com.yl.pay.pos.interfaces.IBankTransaction;

/**
 * Title: 银行交易处理  - 消费撤销
 * Description:  
 * Copyright: Copyright (c)2014
 * Company: pay
 * @author zhongxiang.ren
 */
public class PurchaseVoid  implements IBankTransaction {
	
	private static final Log log = LogFactory.getLog(PurchaseVoid.class);

	private NCCBTransUtil cmbcTransUtil;
	private SignIn signInP500001;
	public UnionPayBean execute(ExtendPayBean extendPayBean) throws Exception{

		log.info(Constant.INTERFACE_CODE + " PurchaseVoid ");

		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		UnionPayBean responseBean = new UnionPayBean();
		Pos pos = extendPayBean.getPos();
		Payment sourcePayment = extendPayBean.getSourcePayment();
		CmbcISO8583 ccbRequest = new CmbcISO8583();
		ccbRequest.putItem(0, Constant.TRANS_TYPE_PURCHASE_VOID);					//交易类型
		ccbRequest.putItem(2, unionPayBean.getPan());									//卡号
		ccbRequest.putItem(3, Constant.PROC_PURCHASE_VOID);						//交易处理码
		ccbRequest.putItem(4, unionPayBean.getAmount());								//交易金额
		ccbRequest.putItem(11, unionPayBean.getSystemsTraceAuditNumber());				//系统流水号
		ccbRequest.putItem(22, unionPayBean.getPosEntryModeCode());
		if(unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_021)){												// pin,当22域末位为1时,此域存在,且存放pin密文
			ccbRequest.putItem(26, "12");
			String posCati=pos.getPosCati();
			String bankCati=unionPayBean.getCardAcceptorTerminalId();
			String pin=unionPayBean.getPin();
			String pan= unionPayBean.getPan();
			String pinData = NCCBTransUtil.translatePin(posCati,bankCati, pin, pan); 				//PIN转加密
			ccbRequest.putItem(52,pinData);	    												// pin,当22域末位为1时,此域存在,且存放pin密文
			ccbRequest.putItem(53, "2600000000000000");
		}else if(unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_051)||unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_071)){
			String posCati=pos.getPosCati();
			String bankCati=unionPayBean.getCardAcceptorTerminalId();
			String pin= unionPayBean.getPin();
			String pan= unionPayBean.getPan();
			String pinData = NCCBTransUtil.translatePin(posCati,bankCati, pin, pan); 				//PIN转加密
			ccbRequest.putItem(52, pinData);
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
		}else if(unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_052)||unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_072)){
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

		ccbRequest.putItem(25,"00");
		if(StringUtil.notNull(unionPayBean.getTrack2())){
			ccbRequest.putItem(35, unionPayBean.getTrack2());								//二磁
		}
		if(StringUtil.notNull(unionPayBean.getTrack3())){
			ccbRequest.putItem(36, unionPayBean.getTrack3());							    //三磁
		}
		ccbRequest.putItem(37, sourcePayment.getBankExternalId());
		if(StringUtil.notNull(unionPayBean.getAuthorizationCode())){
			ccbRequest.putItem(38, unionPayBean.getAuthorizationCode());					//原消费交易授权号
		}
		ccbRequest.putItem(41, unionPayBean.getCardAcceptorTerminalId());	 						// 银行终端号
		ccbRequest.putItem(42, unionPayBean.getCardAcceptorId());
		ccbRequest.putItem(49, Constant.CURRCY_CODE_TRANS);									//币种

		ccbRequest.putItem(601, Constant.PURCHASEVOID_MESSAGETYPECODE);
		ccbRequest.putItem(602, unionPayBean.getBatchNo());								//签到返回批次号
		ccbRequest.putItem(603, Constant.NETWORKMESSAGECODE);
		ccbRequest.putItem(604, "5");
		if(unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_071)
                || unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_072)){
			ccbRequest.putItem(604, "6");
        }
		ccbRequest.putItem(605,"0");

		ccbRequest.putItem(611, sourcePayment.getBankBatch());								//原交易批次号
		ccbRequest.putItem(612, sourcePayment.getBankRequestId());							//原交易流水号
		try {
			responseBean = cmbcTransUtil.send2Bank(ccbRequest);
		}catch(BankNeedReSignInException e1){
			log.info("QtoPayTransUtil send2Bank error!", e1);
			signInP500001.transReSignIn(unionPayBean.getCardAcceptorId(),unionPayBean.getCardAcceptorTerminalId());
			throw new BankNeedReversalException(TransExceptionConstant.INTERFACE_EXCEPTION);
		}catch(Exception e){
			log.info("PurchaseVoid send2Bank ..",e);
			throw new BankNeedReversalException(TransExceptionConstant.INTERFACE_EXCEPTION);
		}
		return responseBean;
	}


    public NCCBTransUtil getCmbcTransUtil() {
		return cmbcTransUtil;
	}

	public void setCmbcTransUtil(NCCBTransUtil cmbcTransUtil) {
		this.cmbcTransUtil = cmbcTransUtil;
	}


	public void test() {

		ExtendPayBean extendPayBean = new ExtendPayBean();
		UnionPayBean unionPayBean = new UnionPayBean();
		unionPayBean.setCardAcceptorId(Constant.TEST_BANK_CUSTOMERNO);
		unionPayBean.setCardAcceptorTerminalId(Constant.TEST_BANK_POSCATI);
		unionPayBean.setBatchNo("000002");
		//unionPayBean.setSystemsTraceAuditNumber(generateSequence(Constant.SEQ_SYSTEM_TRACKE_NUMBER));
		unionPayBean.setAmount("000000001000");
		unionPayBean.setPosEntryModeCode("021");
		unionPayBean.setPan("6225880160777257");
		unionPayBean.setTrack2("6225880160777257=49121200614700363431");
		extendPayBean.setUnionPayBean(unionPayBean);

        Payment sourcePayment = new Payment();
        sourcePayment.setBankRequestId("000025");//原始交易流水号
        sourcePayment.setBankExternalId("100000011807");   //原始交易响应37域的值，原始交易检索号
        sourcePayment.setBankBatch("000002");
        extendPayBean.setSourcePayment(sourcePayment);
        try {
            execute(extendPayBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
