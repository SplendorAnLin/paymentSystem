package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.Pos;

/**
 * Title: POS Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface PosDao {

	//根据ID查询POS
	public Pos findById(Long id);

	//创建POS
	public Pos create(Pos pos);
	
	public Pos createPos(Pos pos);
	
	//更新POS
	public Pos update(Pos pos);
	
	//根据序列号查找Pos
	public Pos findByPosSn(String posSn);
	

	//根据终端号查询
	public Pos findByPosCati(String posCati);
	/**
	 * 根据pos终端号和序列号查找终端
	 * @param posSn
	 * @param posCati
	 * @return
	 */
	public Pos findByPosCatiAndSn(String posSn, String posCati);
	/**
	 * 删除终端
	 * @param id
	 */
	public void delete(Pos pos) ;
	
}
