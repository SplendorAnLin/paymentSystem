package com.yl.boss.dao;

import com.yl.boss.entity.AdviceFeedBack;

/**
 * 意见反馈数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
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
