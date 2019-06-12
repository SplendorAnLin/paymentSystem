package com.yl.pay.pos.service;

import com.yl.pay.pos.bean.ExtendPayBean;

/**
 * Title: POS请求信息 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface IPosRequestService {
	
	public ExtendPayBean create(ExtendPayBean extendPayBean) ;
	
	public ExtendPayBean complete(ExtendPayBean extendPayBean) ;
}

