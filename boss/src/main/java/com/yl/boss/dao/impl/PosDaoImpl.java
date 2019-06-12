package com.yl.boss.dao.impl;

import com.pay.common.util.SequenceUtils;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.PosDao;
import com.yl.boss.entity.Pos;
import com.yl.boss.entity.PosPurchase;
import com.yl.boss.enums.Status;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * POS终端数据访问接口实现
 *
 * @author 聚合支付有限公司
 * @since 2017年7月10日
 * @version V1.0.0
 */
public class PosDaoImpl implements PosDao {

	@Resource
	EntityDao entityDao;
	
	@Override
	public void posAdd(Pos pos) {
		entityDao.persist(pos);
		if(pos.getId() != null){
			pos.setPosCati(SequenceUtils.createSequence(pos.getId(), new int[] {6,0,8,5,9,3,8,0}, new int[] {4,5}, new int[] {6,3}, new int[] {2,3}));
			entityDao.update(pos);
		}
	}

	@Override
	public Pos posById(Long id) {
		String hql = "from Pos p where p.id = ?";
		List<Pos> posList = entityDao.find(hql, id);
		if(posList != null && posList.size() > 0){
			return posList.get(0);
		}
		return null;
	}

	@Override
	public void posUpdate(Pos pos) {
		entityDao.update(pos);
	}
	
	public List<Pos> findPos(String customerNo) {
		String hql = "from Pos where customerNo=?";
		List<Pos> list =entityDao.find(hql,customerNo);
		return list;
	}

	@Override
	public List<Pos> findSyncPos() {
		String hql = "from Pos where customerNo != null and shopNo != null and issync=?";
		List<Pos> list = entityDao.find(hql,Status.FALSE);
		return list;
	}

	@Override
	public boolean posCatiBool(String posCati) {
		String hql = "from Pos where posCati = ?";
		List list = entityDao.find(hql,posCati);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public void posPurchaseAdd(PosPurchase posPurchase) {
		entityDao.save(posPurchase);
	}

	@Override
	public String posCatiMax() {
		String hql = "select MAX(posCati) from Pos";
		List list = entityDao.find(hql);
		if(list != null && list.size() > 0){
			return (String) list.get(0);
		}
		return null;
	}

	@Override
	public Pos posByPosCati(String posCati) {
		String hql = "from Pos where posCati = ?";
		List<Pos> list = entityDao.find(hql,posCati);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<String> posPosSnExists(String posSn) {
		String hql = "select posSn from Pos where posSn in ("+ posSn +")";
		List<String> list = entityDao.find(hql);
		if(list != null){
			return list;
		}
		return null;
	}

	@Override
	public Pos findPosByPosSn(String posSn) {
		String hql = "from Pos where posSn = ?";
		List<Pos> list = entityDao.find(hql,posSn);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List findPosOemByCustomerNo(String customerNo) {
		String hql = "select posCati, posBrand, type, status, posSn, createTime, posType, customerNo from Pos where customerNo = ?";
		List list = entityDao.find(hql,customerNo);
		return list;
	}
}