package com.yl.realAuth.core.dao.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.realAuth.core.dao.AuthInfoManageDao;
import com.yl.realAuth.core.dao.mapper.AuthInfoManageMapper;
import com.yl.realAuth.core.utils.CodeBuilder;
import com.yl.realAuth.model.BindCardInfo;
import com.yl.realAuth.model.IdentityInfo;

@Repository("authInfoManageDao")
public class AuthInfoManageDaoImpl implements AuthInfoManageDao {

	@Resource
	private AuthInfoManageMapper authInfoManageMapper;

	@Override
	public IdentityInfo queryByCertNoEncrypt(String certNo) {
		return authInfoManageMapper.queryByCertNoEncrypt(certNo);
	}

	@Override
	public void insertAuthOrderByIdentity(IdentityInfo identityInfo) {
		identityInfo.setCode(CodeBuilder.build("AUTH", "yyyyMMdd", 6));
		identityInfo.setCreateTime(new Date());
		identityInfo.setVersion(System.currentTimeMillis());
		authInfoManageMapper.insertAuthOrderByIdentity(identityInfo);

	}

	@Override
	public void insertAuthOrderByBind(BindCardInfo bindCardInfo) {
		bindCardInfo.setCode(CodeBuilder.build("AUTH", "yyyyMMdd", 6));
		bindCardInfo.setCreateTime(new Date());
		bindCardInfo.setVersion(System.currentTimeMillis());
		authInfoManageMapper.insertAuthOrderByBind(bindCardInfo);
	}

	@Override
	public void updateBindCardInfo(BindCardInfo bindCardInfo) {
		bindCardInfo.setVersion(System.currentTimeMillis());
		bindCardInfo.setLastUpdateTime(new Date());
		authInfoManageMapper.updateBindCardInfo(bindCardInfo);

	}

	@Override
	public void updateIdentityInfo(IdentityInfo identityInfo) {
		identityInfo.setVersion(System.currentTimeMillis());
		identityInfo.setLastUpdateTime(new Date());
		authInfoManageMapper.updateIdentityInfo(identityInfo);

	}

	@Override
	public BindCardInfo queryBy(Map<String, String> queryParams) {
		return authInfoManageMapper.queryBy(queryParams);
	}

}
