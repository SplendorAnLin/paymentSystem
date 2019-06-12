package com.pay.sign.dbservice.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pay.sign.dao.SecretKeyDao;
import com.pay.sign.dbentity.SecretKey;
import com.pay.sign.dbservice.SecretKeyService;
@Component("secretKeyService")
public class SecretKeyServiceImpl implements SecretKeyService {
	@Resource
	private SecretKeyDao secretKeyDao;

	public SecretKey create(SecretKey secretKey) {
		return secretKeyDao.create(secretKey);
	}

	public SecretKey findByKey(String keyName) {
		return secretKeyDao.findByKey(keyName);
	}

	public SecretKey update(SecretKey secretKey) {
		return secretKeyDao.update(secretKey);
	}

	
}
