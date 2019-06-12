package com.yl.pay.pos.action.trans;

import com.yl.pay.pos.bean.ExtendPayBean;

/**
 * Title: 交易处理 接口
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface ITransaction {
		
	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception;	//交易处理
	
}
