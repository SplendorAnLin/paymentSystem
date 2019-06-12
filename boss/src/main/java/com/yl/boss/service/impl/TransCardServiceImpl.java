package com.yl.boss.service.impl;

import java.util.List;
import com.yl.boss.dao.TransCardDao;
import com.yl.boss.entity.TransCard;
import com.yl.boss.enums.CardAttr;
import com.yl.boss.service.TransCardService;

/**
 * 交易卡服务接口实现
 * @author AnLin
 *
 */
public class TransCardServiceImpl implements TransCardService {

	private TransCardDao transCardDao;
	
	@Override
	public void addTransCard(TransCard transCard) {
		transCardDao.addTransCard(transCard);
	}

	@Override
	public void updateTransCard(TransCard transCard) {
		transCardDao.updateTransCard(transCard);
	}

	@Override
	public TransCard findByCode(String code) {
		return transCardDao.findByCode(code);
	}

	@Override
	public TransCard findByCustNoAndAccNo(String custNo, String accNo, CardAttr cardAttr) {
		return transCardDao.findByCustNoAndAccNo(custNo, accNo, cardAttr);
	}
	
	@Override
	public List<TransCard> findByCustNo(String custNo) {
		return transCardDao.findByCustNo(custNo);
	}

	public TransCardDao getTransCardDao() {
		return transCardDao;
	}

	public void setTransCardDao(TransCardDao transCardDao) {
		this.transCardDao = transCardDao;
	}

	@Override
	public List<TransCard> findAllByCustNo(String custNo) {
		return transCardDao.findAllByCustNo(custNo);
	}

	@Override
	public List<TransCard> findByCustAttr(String custNo, CardAttr cardAttr) {
		return transCardDao.findByCustAttr(custNo, cardAttr);
	}

	@Override
	public TransCard findByCustAndAcc(String custNo, String accNo, CardAttr cardAttr) {
		return transCardDao.findByCustAndAcc(custNo, accNo, cardAttr);
	}
}