package com.yl.customer.service;

import java.util.Date;

import com.yl.boss.api.bean.BulletinBean;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.customer.enums.BulletinType;

/**
 * 商户公告业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public interface BulletinService {
	public BulletinBean findById(Long id);
	
	/**
	 * 公告合计
	 * @param bulletinType
	 * @param bulletinSysType
	 * @param date
	 * @return
	 */
	public int findBulletinCount(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date);
}