package com.yl.boss.dao.impl;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.PosSynchronizationDao;
import com.yl.boss.entity.PosSynchronization;

public class PosSynchronizationDaoImpl implements PosSynchronizationDao {

	@Resource
	private EntityDao entityDao;
	
	@Override
	public void add(PosSynchronization posSynchronization) {
		entityDao.save(posSynchronization);
	}

	@Override
	public List query(String customerNo) {
		String hql = "select p.posCati, p.posBrand, p.type, p.status, p.posSn, p.createTime, p.posType, p.customerNo from PosSynchronization ps, Pos p where ps.posCati = p.posCati and ps.customerNo = ? AND ps.status = 'FALSE'";
		List list = entityDao.find(hql, customerNo);
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	@Override
	public PosSynchronization queryByPosCati(String posCati) {
		String hql = "from PosSynchronization where posCati = ?";
		List<PosSynchronization> list = entityDao.find(hql, posCati);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void update(PosSynchronization posSynchronization) {
		entityDao.update(posSynchronization);
	}


}
