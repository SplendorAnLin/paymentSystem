package com.yl.boss.action;

import java.util.Date;

import com.yl.boss.Constant;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Bulletin;
import com.yl.boss.service.BulletinService;

/**
 * 公告控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class BulletinAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -3201238991638228922L;
	private BulletinService bulletinService;
	private Bulletin bulletin;
	
	
	public String bulletinDetail() {
		bulletin = bulletinService.findById(bulletin.getId());
		return SUCCESS;
	}

	public String bulletinFindById() {
		bulletin = bulletinService.findById(bulletin.getId());
		return SUCCESS;
	}

	public String bulletinUpdate() {
		try {
			bulletinService.update(bulletin);
		} catch (Exception e) {
			logger.info("", e);
			throw new RuntimeException("bulletinUpdate is failed!");
		}
		return SUCCESS;
	}

	public String bulletinAdd() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		bulletin.setOperator(auth.getRealname() + "_" + auth.getUsername());
		bulletin.setCreateTime(new Date());
		try {
			bulletinService.create(bulletin);
			return SUCCESS;
		} catch (Exception e) {
			logger.info("", e);
			throw new RuntimeException("bulletinAdd is failed!");
		}

	}

	public void getBulletinCount() {
		this.write(
				String.valueOf(bulletinService.findBulletinCount(BulletinType.TRUE, BulletinSysType.BOSS, new Date())));
	}

	public BulletinService getBulletinService() {
		return bulletinService;
	}

	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}

	public Bulletin getBulletin() {
		return bulletin;
	}

	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}
}
