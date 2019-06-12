package com.yl.online.trade.quartz.context;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.online.trade.quartz.model.VectorConstant;
import com.yl.online.trade.quartz.model.VectorScale;

/**
 * 基数递增策略
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
@Component("QuartzTriggerIncrease")
public class QuartzTriggerIncrease implements QuartzTriggerStrategy {

	@Override
	public Date getNextFireTime(VectorScale vectorScale) {
		VectorConstant vectorConstant = (VectorConstant) vectorScale;
		double mins = vectorConstant.getVector() * vectorConstant.getBaseLine() * 2;
		return DateUtils.addMinutes(new Date(), (int) mins);
	}

}
