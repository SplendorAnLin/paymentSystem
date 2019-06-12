package com.yl.boss.service;

import com.yl.boss.entity.UserFeedback;

/**
 * 意见反馈业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface UserFeedBackService {
	/**
	 * 新增意见反馈
	 */
	public void save(UserFeedback feedBack);
}
