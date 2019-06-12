package com.yl.dpay.core.remit.handle;

import java.util.Map;

import com.yl.dpay.core.entity.Request;

/**
 * 代付付款处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月16日
 * @version V1.0.0
 */
public interface RequestRemitHandle {
	
	Map<String,String> remit(Request request);
	
	Map<String,String> query(Request request);

}
