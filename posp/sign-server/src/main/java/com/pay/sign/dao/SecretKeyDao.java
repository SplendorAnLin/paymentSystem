package com.pay.sign.dao;

import com.pay.sign.dbentity.SecretKey;

public interface SecretKeyDao {

	
	public SecretKey create(SecretKey secretKey);
	
	public SecretKey findByKey(String keyName);
	
	public SecretKey update(SecretKey secretKey);
}
