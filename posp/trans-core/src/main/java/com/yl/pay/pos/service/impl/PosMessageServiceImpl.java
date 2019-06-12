package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.PosMessageDao;
import com.yl.pay.pos.entity.PosMessage;
import com.yl.pay.pos.service.PosMessageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PosMessageServiceImpl implements PosMessageService{
	private static final Log log = LogFactory.getLog(CustomerFeeServiceImpl.class);
	private PosMessageDao posMessageDao;
	public PosMessageDao getPosMessageDao() {
		return posMessageDao;
	}

	public void setPosMessageDao(PosMessageDao posMessageDao) {
		this.posMessageDao = posMessageDao;
	}

	@Override
	public PosMessage findByPosCati(String posCati) {
		return posMessageDao.findByPosCati(posCati);
	}

	@Override
	public PosMessage create(PosMessage posMessage) {
		
		return posMessageDao.create(posMessage);
	}

	@Override
	public PosMessage update(PosMessage posMessage) {
		return posMessageDao.update(posMessage);
	}

	@Override
	public void delete(PosMessage posMessage) {
		posMessageDao.delete(posMessage);
		
	}

}
