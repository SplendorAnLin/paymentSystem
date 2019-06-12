package com.pay.sign.dbservice;

import com.pay.sign.dbentity.SecretKey;

public interface SecretKeyService {
	
	public SecretKey create(SecretKey secretKey);
	
	public SecretKey findByKey(String keyName);
	
	public SecretKey update(SecretKey secretKey);

}
