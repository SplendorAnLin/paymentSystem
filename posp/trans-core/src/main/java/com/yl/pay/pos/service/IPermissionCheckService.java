package com.yl.pay.pos.service;

import com.yl.pay.pos.bean.ExtendPayBean;

/**
 * Title: 交易权限检查
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface IPermissionCheckService {	
	/**
	 * 通用权限校验
	 * @param extendPayBean
	 * @return
	 */
	public ExtendPayBean execute(ExtendPayBean extendPayBean) ;	
	
	/**
	 * 特殊校验服务，暂针对交易卡种限制
	 * @param extendPayBean
	 * @return
	 */
	
}

