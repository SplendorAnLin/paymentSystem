package com.yl.pay.pos.service;

import com.yl.pay.pos.bean.ExtendPayBean;


/**
 * Title: 交易金额限制服务
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface ITransAmountLimitService {
	
	public ExtendPayBean execute(ExtendPayBean extendPayBean) ;	
	
	public ExtendPayBean completeAccumulatedAmount(ExtendPayBean extendPayBean) ; 
	
}
