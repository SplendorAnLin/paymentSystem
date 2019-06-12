package com.yl.pay.pos.service;

import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.enums.TransType;

/**
 * Title: 银行请求信息 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface IBankRequestService {
	
	public ExtendPayBean create(ExtendPayBean extendPayBean) ;	
	
	public ExtendPayBean create(ExtendPayBean extendPayBean, TransType transType) ;
	
	public ExtendPayBean complete(ExtendPayBean extendPayBean) ;
}

