package com.yl.pay.pos.action.trans;

import com.pay.common.util.DateUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.interfaces.IBankTransaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Title: 交易处理  - 余额查询
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BalanceInquiryNotice extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(BalanceInquiryNotice.class);
	
	public ExtendPayBean execute(ExtendPayBean extendPayBean){
		log.info("######## BalanceInquiryNotice ########");
		String beanName="balanceInquiryNoticeP310000";
		IBankTransaction bankTransaction = bankTransactionFactory.getBankTransaction(beanName);
		try {
			UnionPayBean responseBean = bankTransaction.execute(extendPayBean);		
			extendPayBean.getUnionPayBean().setResponseCode(Constant.SUCCESS_POS_RESPONSE);
			extendPayBean.getUnionPayBean().setDateLocalTransaction(DateUtil.formatNow("MMdd"));
			extendPayBean.getUnionPayBean().setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));
			extendPayBean.getUnionPayBean().setDateSettlement(DateUtil.formatNow("MMdd"));
		} catch (Exception e) {
			log.info("eee ",e);				
		} 
		return extendPayBean;
	}
}
