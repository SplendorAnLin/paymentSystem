package com.yl.customer.dao;

import com.yl.customer.entity.AdviceFeedBack;

/**
 * 意见反馈实现接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月1日
 * @version V1.0.0
 */
public interface AdviceFeedBackDao {
	/**
	 * 新增反馈意见
	 * @param adviceFeedBack
	 * @return
	 */
	public AdviceFeedBack create(AdviceFeedBack adviceFeedBack);
}
