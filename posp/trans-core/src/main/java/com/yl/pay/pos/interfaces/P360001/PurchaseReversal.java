package com.yl.pay.pos.interfaces.P360001;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
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

	private NCCBTransUtil nccbTransUtil;
	
	public UnionPayBean execute(ExtendPayBean extendPayBean) throws Exception{
		
		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		
		log.info("南昌建行Purchase : [interfaceCode=" + Constant.INTERFACE_CODE
				+ "],[BankCustomer=" + unionPayBean.getCardAcceptorId()
				+ "],[BankCati=" + unionPayBean.getCardAcceptorTerminalId()
				+ "]");	
		
		CcbISO8583 ccbRequest = new CcbISO8583();
		ccbRequest.putItem(0, Constant.TRANS_TYPE_REVESAL); 				// 交易类型
		ccbRequest.putItem(2, unionPayBean.getPan()); 						// 主账号
		ccbRequest.putItem(3, Constant.PROC_PURCHASE); 						// 处理码
		ccbRequest.putItem(4, unionPayBean.getAmount()); 					// 交易金额
		ccbRequest.putItem(11,unionPayBean.getSourcePosRequestId()); 	// 系统跟踪号

		String enterMode=unionPayBean.getPosEntryModeCode();
		ccbRequest.putItem(22, enterMode); 									// 输入方式,刷卡有密码【021】
		if(enterMode.equals(Constant.ENTRY_MODE_051) || enterMode.equals(Constant.ENTRY_MODE_052)){
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
		ccbRequest.putItem(605, "0");
		
		UnionPayBean responseBean = new UnionPayBean();
		try {					
			responseBean =nccbTransUtil.send2Bank(ccbRequest);	
		}catch(Exception e){
			log.info("nccbTransService send2Bank error",e);
			throw new TransRunTimeException(TransExceptionConstant.INTERFACE_EXCEPTION);
		}		
			
		return responseBean;
	}
	
	
	public NCCBTransUtil getNccbTransUtil() {
		return nccbTransUtil;
	}

	public void setNccbTransUtil(NCCBTransUtil nccbTransUtil) {
		this.nccbTransUtil = nccbTransUtil;
	}

}
