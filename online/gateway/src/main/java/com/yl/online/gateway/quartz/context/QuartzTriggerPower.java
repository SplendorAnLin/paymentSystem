package com.yl.online.gateway.quartz.context;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.online.gateway.quartz.model.VectorConstant;
import com.yl.online.gateway.quartz.model.VectorScale;

/**
 * 增量幂次策略
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
@Component("QuartzTriggerPower")
public class QuartzTriggerPower implements QuartzTriggerStrategy {

	@Override
	public Date getNextFireTime(VectorScale vectorScale) {
		VectorConstant vectorConstant = (VectorConstant) vectorScale;
		double mins = vectorConstant.getVector() * Math.pow(vectorConstant.getVector(), vectorConstant.getVector() - 1);
		return DateUtils.addMinutes(new Date(), (int) mins);
	}

}
