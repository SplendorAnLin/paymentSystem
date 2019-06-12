package com.yl.online.trade.quartz.context;

import java.util.Date;

import com.yl.online.trade.quartz.model.VectorScale;

/**
 * 定时触发策略接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public interface QuartzTriggerStrategy {
	/**
	 * 获取下一次的触发时间
	 * @param VectorScale 向量
	 * @return Date
	 */
	public Date getNextFireTime(VectorScale vectorScale);
}
