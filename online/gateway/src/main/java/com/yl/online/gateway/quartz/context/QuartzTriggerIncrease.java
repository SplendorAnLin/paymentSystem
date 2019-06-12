package com.yl.online.gateway.quartz.context;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.online.gateway.quartz.model.VectorConstant;
import com.yl.online.gateway.quartz.model.VectorScale;

/**
 * 基数递增策略
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
@Component("QuartzTriggerIncrease")
public class QuartzTriggerIncrease implements QuartzTriggerStrategy {

	@Override
	public Date getNextFireTime(VectorScale vectorScale) {
		VectorConstant vectorConstant = (VectorConstant) vectorScale;
		double mins = vectorConstant.getVector() * vectorConstant.getBaseLine() * 10;
		return DateUtils.addMinutes(new Date(), (int) mins);
	}

}
