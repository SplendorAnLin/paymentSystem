package com.pay.sign.dao;

import com.pay.sign.dbentity.SignPic;

public interface ISignDao {

	public void createSignPic(SignPic signPic);
	
	public boolean isExist(Long orderId);
	
}
