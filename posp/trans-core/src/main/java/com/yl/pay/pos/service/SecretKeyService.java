package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.SecretKey;

public interface SecretKeyService {
	
	public SecretKey create(SecretKey secretKey);
	
	public SecretKey findByKey(String keyName);
	
	public SecretKey update(SecretKey secretKey);

}
