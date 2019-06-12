package com.yl.boss.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.TransCardDao;
import com.yl.boss.entity.TransCard;
import com.yl.boss.enums.CardAttr;
import com.yl.boss.enums.CardStatus;

/**
 * 卡片服务实现
 * @author AnLin
 * 
 */
public class TransCardDaoImpl implements TransCardDao {

	@Resource
	private EntityDao entityDao;
	
	@Override
	public void addTransCard(TransCard transCard) {
		entityDao.save(transCard);
	}

	@Override
	public void updateTransCard(TransCard transCard) {
		entityDao.update(transCard);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransCard findByCode(String code) {
		StringBuffer hql = new StringBuffer(" from TransCard where code = ?");
		List<TransCard> list = entityDao.find(hql.toString(), code);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransCard findByCustNoAndAccNo(String custNo, String accNo, CardAttr cardAttr) {
		StringBuffer hql = new StringBuffer(" from TransCard where customerNo = ? AND accountNo = ? AND cardAttr = ? AND cardStatus = ?");
		List<TransCard> list = entityDao.find(hql.toString(), custNo, accNo, cardAttr, CardStatus.NORAML);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransCard> findByCustNo(String custNo) {
		StringBuffer hql = new StringBuffer(" from TransCard where customerNo = ? AND cardAttr = ?");
		List<TransCard> list = entityDao.find(hql.toString(), custNo, CardAttr.TRANS_CARD);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransCard> findAllByCustNo(String custNo) {
		StringBuffer hql = new StringBuffer(" from TransCard where customerNo = ?");
		List<TransCard> list = entityDao.find(hql.toString(), custNo);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransCard> findByCustAttr(String custNo, CardAttr cardAttr) {
		StringBuffer hql = new StringBuffer(" from TransCard where customerNo = ? AND cardAttr = ? AND cardStatus = ?");
		List<TransCard> list = entityDao.find(hql.toString(), custNo, cardAttr, CardStatus.NORAML);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransCard findByCustAndAcc(String custNo, String accNo, CardAttr cardAttr) {
		StringBuffer hql = new StringBuffer(" from TransCard where customerNo = ? AND accountNo = ? AND cardAttr = ?");
		List<TransCard> list = entityDao.find(hql.toString(), custNo, accNo, cardAttr);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}