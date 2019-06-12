package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.SecretKeyDao;
import com.yl.pay.pos.entity.SecretKey;
import com.yl.pay.pos.service.SecretKeyService;

public class SecretKeyServiceImpl implements SecretKeyService {
	
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

	public SecretKeyDao getSecretKeyDao() {
		return secretKeyDao;
	}

	public void setSecretKeyDao(SecretKeyDao secretKeyDao) {
		this.secretKeyDao = secretKeyDao;
	}
	
}
