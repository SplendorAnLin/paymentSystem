package com.pay.sign.business;

import com.pay.sign.bean.ExtendPayBean;

/**
 * Title: 终端业务处理 接口
 * Description:终端业务处理 接口   
 * Copyright: 2015年6月12日下午2:48:28
 * Company: SDJ
 * @author zhongxiang.ren
 */
public interface ITerminalBusiness {
		
	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception;	//交易处理
	
}
