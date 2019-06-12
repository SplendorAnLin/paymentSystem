package com.yl.boss.service.impl;

import java.util.Date;
import java.util.List;

import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;
import com.yl.boss.api.utils.Page;
import com.yl.boss.dao.BulletinDao;
import com.yl.boss.entity.Bulletin;
import com.yl.boss.service.BulletinService;

/**
 * 公告信息业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class BulletinServiceImpl implements BulletinService {
	
	private BulletinDao bulletinDao;

	public Bulletin create(Bulletin bulletin) {
		return bulletinDao.create(bulletin);
	}

	public void update(Bulletin bulletin) {
		Bulletin bulletinOri = bulletinDao.findById(bulletin.getId());
		bulletinOri.setTitle(bulletin.getTitle());
		bulletinOri.setOperator(bulletin.getOperator());
		bulletinOri.setStatus(bulletin.getStatus());
		bulletinOri.setSysCode(bulletin.getSysCode());
		bulletinOri.setEffTime(bulletin.getEffTime());
		bulletinOri.setExtTime(bulletin.getExtTime());
		bulletinOri.setContent(bulletin.getContent());
		bulletinDao.update(bulletinOri);
	}

	public Bulletin findById(Long id) {
		return bulletinDao.findById(id);
	}

	public int findBulletinCount(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date) {
		return bulletinDao.findBulletinCount(bulletinType, bulletinSysType, date);
	}

	public BulletinDao getBulletinDao() {
		return bulletinDao;
	}

	public void setBulletinDao(BulletinDao bulletinDao) {
		this.bulletinDao = bulletinDao;
	}

	public List<Bulletin> findBulletinBy(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date,Page page) {
		return bulletinDao.findBulletinBy(bulletinType, bulletinSysType, date,page);
	}

}
