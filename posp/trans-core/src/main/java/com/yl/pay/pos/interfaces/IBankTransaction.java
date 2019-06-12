package com.yl.pay.pos.interfaces;

import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;

public interface IBankTransaction {

	public UnionPayBean execute(ExtendPayBean extendPayBean) throws Exception;	//交易处理

}
