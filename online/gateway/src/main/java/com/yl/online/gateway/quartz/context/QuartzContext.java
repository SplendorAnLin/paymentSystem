package com.yl.online.gateway.quartz.context;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.lefu.commons.web.spring.ApplicationContextUtils;
import com.yl.online.gateway.quartz.enums.QuartzStrategyType;
import com.yl.online.gateway.quartz.model.VectorScale;

/**
 * 定时策略上下文
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
@Component("quartzContext")
public final class QuartzContext {
	
	/**
	 * 获取下一次的触发时间
	 * @param quartzStrategyType 定时策略类型
	 * @param vectorScale 向量
	 * @return Date
	 */
	public Date getNextFireTime(QuartzStrategyType quartzStrategyType, VectorScale vector) {
		return ((QuartzTriggerStrategy) ApplicationContextUtils.getApplicationContext().getBean(quartzStrategyType.name())).getNextFireTime(vector);
	}
}
