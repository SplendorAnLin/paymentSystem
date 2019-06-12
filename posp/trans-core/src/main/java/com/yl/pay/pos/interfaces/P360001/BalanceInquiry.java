package com.yl.pay.pos.interfaces.P360001;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.Pos;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.exception.trans.BankNeedReSignInException;
import com.yl.pay.pos.interfaces.BaseBankTransaction;
import com.yl.pay.pos.interfaces.IBankTransaction;

/**
 * Title: 银行交易处理  - 余额查询
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author xiaojun.yang
 */

public class BalanceInquiry extends BaseBankTransaction implements IBankTransaction {
	private static final Log log = LogFactory.getLog(BalanceInquiry.class);
	
	private NCCBTransUtil nccbTransUtil;
	private SignIn signInP360001;
	
	public UnionPayBean execute(ExtendPayBean extendPayBean) throws Exception{
		log.info("BalanceQuery : interfaceCode=" + Constant.INTERFACE_CODE);
		
		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		Pos pos = extendPayBean.getPos();
			
		CcbISO8583 ccbRequest = new CcbISO8583();
		ccbRequest.putItem(0,  Constant.TRANS_TYPE_BALANCE_QUERY); 			// 交易类型
		ccbRequest.putItem(2,  unionPayBean.getPan());						// 主账号 
		ccbRequest.putItem(3,  Constant.PROC_BALANCE_QUERY);				// 处理码
		ccbRequest.putItem(11, unionPayBean.getSystemsTraceAuditNumber()); 	// 系统跟踪号
		 
		ccbRequest.putItem(22, unionPayBean.getPosEntryModeCode());			// 输入方式,刷卡无密码【022】		
		ccbRequest.putItem(25, "00");			
		ccbRequest.putItem(35, unionPayBean.getTrack2());					// 2磁道
		
		ccbRequest.putItem(41, unionPayBean.getCardAcceptorTerminalId());	// 银行终端号
		ccbRequest.putItem(42, unionPayBean.getCardAcceptorId()); 			// 银行商户号
		ccbRequest.putItem(49, Constant.CURRCY_CODE_TRANS);					// 币种	,人民币【156】
		
		if(unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_021)){
			ccbRequest.putItem(26, "12");
			String posCati=pos.getPosCati();
			String bankCati=unionPayBean.getCardAcceptorTerminalId();
			String pin=unionPayBean.getPin();
			String pan= unionPayBean.getPan();
			String pinData = NCCBTransUtil.translatePin(posCati ,bankCati, pin, pan); 				//PIN转加密
			ccbRequest.putItem(52, pinData);	
			ccbRequest.putItem(53, "2600000000000000");
		}else if(unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_051)){
			String posCati=pos.getPosCati();
			String bankCati=unionPayBean.getCardAcceptorTerminalId();
			String pin= unionPayBean.getPin();
			String pan= unionPayBean.getPan();
			String pinData = NCCBTransUtil.translatePin(posCati ,bankCati, pin, pan); 					//PIN转加密
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
		}else if(unionPayBean.getPosEntryModeCode().equals(Constant.ENTRY_MODE_052)){
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
		ccbRequest.putItem(601, "01");
		ccbRequest.putItem(602, "000001");//签到批次号
		ccbRequest.putItem(603, Constant.NETWORKMESSAGECODE);
		UnionPayBean responseBean = new UnionPayBean();
		try {					
			responseBean =nccbTransUtil.send2Bank(ccbRequest);
		}catch(BankNeedReSignInException e1){
			log.info("BalanceInquiry nccbTransUtil send2Bank error ",e1);
			signInP360001.transReSignIn(unionPayBean.getCardAcceptorId(),unionPayBean.getCardAcceptorTerminalId());
			throw new TransRunTimeException(TransExceptionConstant.INTERFACE_EXCEPTION);
		}catch(Exception e){
			log.info("BalanceInquiry nccbTransUtil send2Bank error ",e);
			throw new TransRunTimeException(TransExceptionConstant.INTERFACE_EXCEPTION);
		}		
		String additionalAmount=responseBean.getAdditionalAmount();
		String posAdditionalAmount=additionalAmount.substring(2, 4)+additionalAmount.substring(0, 2)+additionalAmount.substring(4);
		responseBean.setAdditionalAmount(posAdditionalAmount);
		
		return responseBean;
	}	



	

	public NCCBTransUtil getNccbTransUtil() {
		return nccbTransUtil;
	}


	public void setNccbTransUtil(NCCBTransUtil nccbTransUtil) {
		this.nccbTransUtil = nccbTransUtil;
	}

	public SignIn getSignInP360001() {
		return signInP360001;
	}





	public void setSignInP360001(SignIn signInP360001) {
		this.signInP360001 = signInP360001;
	}

public void test(){
		
		ExtendPayBean extendPayBean = new ExtendPayBean();
		UnionPayBean unionPayBean = new UnionPayBean();
		unionPayBean.setCardAcceptorId(Constant.TEST_BANK_CUSTOMERNO);
		unionPayBean.setCardAcceptorTerminalId(Constant.TEST_BANK_POSCATI);
		unionPayBean.setSystemsTraceAuditNumber("000001");
		unionPayBean.setSystemsTraceAuditNumber(generateSequence(Constant.SEQ_SYSTEM_TRACKE_NUMBER));
		unionPayBean.setPosEntryModeCode("021");
		unionPayBean.setPan("6225880160777257");
		unionPayBean.setTrack2("6225880160777257=49121200614700363431");
		extendPayBean.setUnionPayBean(unionPayBean);
		
//		ccbRequest.putItem(2, unionPayBean.getPan()); 						// 主账号
//		ccbRequest.putItem(3, Constant.PROC_PURCHASE); 						// 处理码
//		ccbRequest.putItem(4, unionPayBean.getAmount()); 					// 交易金额
//		ccbRequest.putItem(11, unionPayBean.getSystemsTraceAuditNumber()); 	// 系统跟踪号
//
//		String enterMode=unionPayBean.getPosEntryModeCode();
//		ccbRequest.putItem(22, enterMode); 									// 输入方式,刷卡有密码【021】
//		ccbRequest.putItem(25, "00");										// 服务代码00
//		ccbRequest.putItem(35, unionPayBean.getTrack2()); 	
		try {
			execute(extendPayBean);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	
	
}
