package com.yl.realAuth.front.dao.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.realAuth.front.dao.RealNameAuthRequestDao;
import com.yl.realAuth.front.dao.mapper.RealNameAuthRequestMapper;
import com.yl.realAuth.model.RealNameAuthRequest;

@Repository("realNameAuthRequestDao")
public class RealNameAuthRequestDaoImpl implements RealNameAuthRequestDao {

	@Resource
	private RealNameAuthRequestMapper realNameAuthRequestMapper;

	@Override
	public void insertAuthRequest(RealNameAuthRequest reaNameAuthRequest) {
		reaNameAuthRequest.setCode(UUID.randomUUID().toString());
		reaNameAuthRequest.setCreateTime(new Date());
		reaNameAuthRequest.setVersion(System.currentTimeMillis());
		realNameAuthRequestMapper.insertAuthRequest(reaNameAuthRequest);
	}

	@Override
	public RealNameAuthRequest queryPartnerRequestByRequestCode(String requestCode, String receiver) {
		return realNameAuthRequestMapper.queryPartnerRequestByRequestCode(requestCode, receiver);
	}

}
