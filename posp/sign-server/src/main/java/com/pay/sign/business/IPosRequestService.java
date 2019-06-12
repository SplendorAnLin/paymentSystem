package com.pay.sign.business;

import com.pay.sign.bean.ExtendPayBean;

/**
 * Title: POS请求信息 服务 
 * Description:   
 * Copyright: 2015年6月12日下午2:48:10
 * Company: SDJ
 * @author zhongxiang.ren
 */
public interface IPosRequestService {
	
	public ExtendPayBean create(ExtendPayBean extendPayBean) ;
	
	public ExtendPayBean complete(ExtendPayBean extendPayBean) ;
}

