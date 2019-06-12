package com.yl.pay.pos.dao.impl;

import com.pay.common.util.SequenceUtils;
import com.yl.pay.pos.dao.PosDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.Pos;

/**
 * Title: PosDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class PosDaoImpl implements PosDao{

	public EntityDao entityDao;

	public Pos findById(Long id){
		return entityDao.findById(Pos.class, id);
	}

	public Pos create(Pos pos){
		//随机生成8位pos终端号
		Long poscatiSeq = (Long)entityDao.getSequence("SEQ_POS_CATI");
		String posCati= createPosCati(poscatiSeq);
		pos.setPosCati(posCati);
		entityDao.persist(pos);
		return pos;
	}
	
	public Pos createPos(Pos pos) {
		entityDao.persist(pos);
		return pos;
	}

	public Pos findByPosCati(String posCati){
		String hql = "from Pos p where p.posCati = ?";
		return entityDao.findUnique(Pos.class, hql, posCati);
	}
	
	//根据序列号查找Pos
	public Pos findByPosSn(String posSn){
		String hql = "from Pos p where p.posSn = ?";
		return entityDao.findUnique(Pos.class, hql, posSn);
	}
	@Override
	public Pos findByPosCatiAndSn(String posSn, String posCati) {
		String hql = "from Pos p where p.posSn = ? and p.posCati = ?";
		return entityDao.findUnique(Pos.class, hql, posSn,posCati);
	}	
	@Override
	public void delete(Pos pos){
		entityDao.remove(pos);
		return;
	}
	public Pos update(Pos pos){
		return entityDao.merge(pos);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	/**
     * 生成pos终端号
     * @return
     * @author HuangNanJian   2015年5月8日 下午4:52:51
     */
	public String createPosCati(long poscatiSeq){
		return  SequenceUtils.createSequence(poscatiSeq , new int[] {8,0,8,5,9,3,8,0}, new int[] {4,5}, new int[] {6,8}, new int[] {7,8});
	}
	
}
