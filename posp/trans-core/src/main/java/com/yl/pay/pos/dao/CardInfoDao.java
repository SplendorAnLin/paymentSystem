package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.CardInfo;

/**
 * Title: Order Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface CardInfoDao {

	//根据ID查询
	public CardInfo findById(Long id);
	
	//根据卡号查询
	public CardInfo findByPan(String pan);

	//创建
	public CardInfo create(CardInfo cardInfo);
	
	//更新
	public CardInfo update(CardInfo cardInfo);
	
}
