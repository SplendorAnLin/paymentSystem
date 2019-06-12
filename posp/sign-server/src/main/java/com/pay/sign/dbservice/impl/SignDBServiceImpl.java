package com.pay.sign.dbservice.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pay.sign.dao.ISignDao;
import com.pay.sign.dbentity.SignPic;
import com.pay.sign.dbservice.ISignDBService;
@Component("signDBService")
public class SignDBServiceImpl implements ISignDBService{

	@Resource
	private ISignDao signDao;
	
	@Override
	public void createSignPic(SignPic signPic) {
		
		if(signPic.getOrderId() != null && ! signDao.isExist(signPic.getOrderId())){
			signDao.createSignPic(signPic);
		}
	}

}
