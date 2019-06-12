package com.yl.dpay.hessian.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yl.dpay.hessian.beans.RequestBean;

/**
 * 代付请求远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
public interface RequestFacade {
	
	/**
	 * 代付总笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String, String>> dfSuccessCount(Date orderTimeStart, Date orderTimeEnd, String owner);

	/**
	 * 总支出   
	 */
	public String orderSumByDay(Date orderTimeStart, Date orderTimeEnd,String owner, int day);

	
	/**
	 * 代付成功  笔数  金额
	 */
	public List<Map<String, String>> dfSuccess(Date orderTimeStart, Date orderTimeEnd, List owner);
	
	/**
	 * 代付对账单
	 */
	public List<RequestBean> customerRecon(Map<String, Object> params);
	
	/**
	 * 代付成功 笔数 金额 手续费
	 */
	public String customerReconHead(Map<String, Object> params);
}