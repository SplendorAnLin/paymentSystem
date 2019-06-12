package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.SecretKey;

public interface SecretKeyDao {

	
	public SecretKey create(SecretKey secretKey);
	
	public SecretKey findByKey(String keyName);
	
	public SecretKey update(SecretKey secretKey);
}
