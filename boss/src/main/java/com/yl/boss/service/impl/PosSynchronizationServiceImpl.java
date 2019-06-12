package com.yl.boss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.yl.boss.dao.PosSynchronizationDao;
import com.yl.boss.entity.PosSynchronization;
import com.yl.boss.enums.Status;
import com.yl.boss.service.PosSynchronizationService;

public class PosSynchronizationServiceImpl implements PosSynchronizationService {
	
	@Resource
	PosSynchronizationDao posSynchronizationDao;

	@Override
	public List query(String customerNo) {
		return posSynchronizationDao.query(customerNo);
	}

	@Override
	public void updateStatusByPosCati(String customerNo, List<String> posCatis) {
		for (int i = 0; i < posCatis.size(); i++) {
			PosSynchronization p = posSynchronizationDao.queryByPosCati(posCatis.get(i));
			if(p != null && p.getCustomerNo().equals(customerNo)){
				p.setStatus(Status.TRUE);
				posSynchronizationDao.update(p);
			}
		}
	}


}
