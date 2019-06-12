package com.yl.boss.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.MccDao;
import com.yl.boss.entity.Agent;
import com.yl.boss.entity.MccInfo;
import com.yl.boss.entity.Pos;
import com.yl.boss.enums.CustomerStatus;
import com.yl.boss.enums.Status;

/**
 * Mcc数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月13日
 * @version V1.0.0
 */
public class MccDaoImpl implements MccDao {
	
	@Resource
	private EntityDao entityDao;

	@Override
	public void mccAdd(MccInfo mccInfo) {
		entityDao.persist(mccInfo);
	}
	
	@Override
	public MccInfo mccById(Long id) {
		String hql = "from MccInfo where id = ?";
		List<MccInfo> list = entityDao.find(hql,id);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public boolean mccByMcc(String mcc) {
		String hql = "from MccInfo where mcc = ?";
		List<MccInfo> list = entityDao.find(hql,mcc);
		if(list != null && list.size() > 0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void mccUpdate(MccInfo mccInfo) {
		entityDao.update(mccInfo);
	}

	@Override
	public List<MccInfo> findSyncMcc() {
		String hql = "from MccInfo where issync=?";
		List<MccInfo> list = entityDao.find(hql,Status.FALSE);
		return list;
	}

	@Override
	public List<MccInfo> findAll() {
		String hql = "from MccInfo";
		List<MccInfo> list = entityDao.find(hql);
		return list;
	}

}
