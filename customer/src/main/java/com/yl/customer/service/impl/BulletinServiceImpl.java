package com.yl.customer.service.impl;

import java.util.Date;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.BulletinBean;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.interfaces.BulletinInterface;
import com.yl.customer.enums.BulletinType;
import com.yl.customer.service.BulletinService;

/**
 * 商户公告业务接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public class BulletinServiceImpl implements BulletinService {
	private BulletinType bulletinType;
	private BulletinSysType bulletinSysType;
	//创建一个BOSS的bulletinInterface接口对象
	private BulletinInterface bulletinInterface;

	@Override
	public BulletinBean findById(Long id) {
		return bulletinInterface.findById(id);
	}
	
	
	public BulletinInterface getBulletinInterface() {
		return bulletinInterface;
	}

	public void setBulletinInterface(BulletinInterface bulletinInterface) {
		this.bulletinInterface = bulletinInterface;
	}

	@Override
	public int findBulletinCount(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date) {
		return bulletinInterface.findBulletinCount(JsonUtils.toJsonToObject(bulletinType, com.yl.boss.api.enums.BulletinType.class), bulletinSysType, date);
	}


	public BulletinType getBulletinType() {
		return bulletinType;
	}


	public void setBulletinType(BulletinType bulletinType) {
		this.bulletinType = bulletinType;
	}


	public BulletinSysType getBulletinSysType() {
		return bulletinSysType;
	}


	public void setBulletinSysType(BulletinSysType bulletinSysType) {
		this.bulletinSysType = bulletinSysType;
	}
}