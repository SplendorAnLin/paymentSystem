package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.CardAmountLimit;

public interface CardAmountLimitDao {

	public CardAmountLimit create(CardAmountLimit cardAmountLimit);
	
	public CardAmountLimit findByPan(String pan);
	
	public CardAmountLimit update(CardAmountLimit cardAmountLimit);
}
