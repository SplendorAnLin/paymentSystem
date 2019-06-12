package com.yl.boss.service.impl;


import java.util.Date;

import com.yl.boss.dao.QRCodeDao;
import com.yl.boss.entity.License;
import com.yl.boss.service.QRCodeService;

/**
 * QRCode业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class QRCodeServiceImpl implements QRCodeService {

	private QRCodeDao qRCodeDao;
	
	@Override
	public void sweepTheNetwork(License license) {
		license.setCreateTime(new Date());
		license.setUpdateTime(new Date());
		qRCodeDao.sweepTheNetwork(license);
	}

	public QRCodeDao getqRCodeDao() {
		return qRCodeDao;
	}

	public void setqRCodeDao(QRCodeDao qRCodeDao) {
		this.qRCodeDao = qRCodeDao;
	}

	@Override
	public License getInfo(int Id) {
		return qRCodeDao.getInfo(Id);
	}

	@Override
	public void updateNetwork(License license) {
		license.setUpdateTime(new Date());
		qRCodeDao.updateNetwork(license);
	}
}