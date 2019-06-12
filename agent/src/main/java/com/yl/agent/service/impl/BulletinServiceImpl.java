package com.yl.agent.service.impl;

import java.util.Date;
import java.util.List;

import com.yl.agent.service.BulletinService;
import com.yl.boss.api.bean.BulletinBean;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;
import com.yl.boss.api.interfaces.BulletinInterface;
import com.yl.boss.api.utils.Page;

/**
 * 公告业务接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class BulletinServiceImpl implements BulletinService {
	BulletinInterface bulletinInterface;
	
	@Override
	public List<BulletinBean> bulletinBySysCode(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date,Page page) {
//		return bulletinInterface.bulletinBySysCode(bulletinType, bulletinSysType,date, page );
		return null;
	}
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
//		return bulletinInterface.findBulletinCount(bulletinType, bulletinSysType, date);
		return 0;
	}


}
