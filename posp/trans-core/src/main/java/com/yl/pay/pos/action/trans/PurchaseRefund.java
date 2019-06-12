package com.yl.pay.pos.action.trans;

import com.yl.pay.pos.bean.ExtendPayBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Title: 交易处理  - 退货
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class PurchaseRefund extends BaseTransaction implements ITransaction {
	
	private static final Log log = LogFactory.getLog(PurchaseRefund.class);

	public ExtendPayBean execute(ExtendPayBean extendPayBean){	
		return extendPayBean;
	}
}
