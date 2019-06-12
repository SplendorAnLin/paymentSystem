package com.yl.boss.dao;

import com.yl.boss.entity.UserFeedback;

/**
 * 意见反馈数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface UserFeedBackDao {
	/**
	 * 新增意见反馈
	 */
	public void save(UserFeedback feedBack);
}
