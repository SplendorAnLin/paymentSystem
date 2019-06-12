package com.yl.realAuth.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yl.realAuth.core.dao.AuthInfoManageDao;
import com.yl.realAuth.core.service.AuthInfoManageService;
import com.yl.realAuth.model.BindCardInfo;
import com.yl.realAuth.model.IdentityInfo;
import com.yl.realAuth.model.RealNameAuthOrder;

@Service("authInfoManageService")
public class AuthInfoManageServiceImpl implements AuthInfoManageService {

	@Resource
	private AuthInfoManageDao authInfoManageDao;

	@Override
	public boolean findAuthInfoBy(RealNameAuthOrder order) {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("certNo", order.getCertNoEncrypt());
		queryParams.put("payerName", order.getPayerName());
		if (StringUtils.isNotBlank(order.getPayerMobNo())) {
			queryParams.put("payerMobNo", order.getPayerMobNo());
		}
		queryParams.put("bankCardNo", order.getBankCardNoEncrypt());

		if (StringUtils.isNotBlank(order.getBankCardNo())) {
			BindCardInfo bindCardInfo = authInfoManageDao.queryBy(queryParams);
			if (bindCardInfo == null)
				return false;
			return true;
		} else {
			IdentityInfo identityInfo = authInfoManageDao.queryByCertNoEncrypt(order.getCertNoEncrypt());
			if (identityInfo == null)
				return false;
			if (identityInfo.getPayerName().equals(order.getPayerName()))
				return true;
		}
		return false;
	}

	@Override
	public void saveAuthInfo(RealNameAuthOrder order) {
		if (StringUtils.isNotBlank(order.getBankCardNo())) {
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("bankCardNo", order.getBankCardNo());

			BindCardInfo bindCardInfo = authInfoManageDao.queryBy(queryParams);
			if (bindCardInfo == null) {
				BindCardInfo cardInfo = new BindCardInfo();
				cardInfo.setBankCardNo(order.getBankCardNo());
				cardInfo.setBankCardNoEncrypt(order.getBankCardNoEncrypt());
				cardInfo.setCertNo(order.getCertNo());
				cardInfo.setCertNoEncrypt(order.getCertNoEncrypt());
				cardInfo.setPayerMobNo(order.getPayerMobNo());
				cardInfo.setPayerName(order.getPayerName());

				authInfoManageDao.insertAuthOrderByBind(cardInfo);
			} else {
				bindCardInfo.setCertNo(order.getCertNo());
				bindCardInfo.setCertNoEncrypt(order.getCertNoEncrypt());
				bindCardInfo.setPayerMobNo(order.getPayerMobNo());
				bindCardInfo.setPayerName(order.getPayerName());

				authInfoManageDao.updateBindCardInfo(bindCardInfo);
			}
			if (StringUtils.isNotBlank(order.getCertNo()) && StringUtils.isNotBlank(order.getPayerName())) {
				certNoManage(order);
			}
		} else {
			certNoManage(order);
		}
	}
	
	/**
	 * 身份认证信息管理
	 * @param order
	 */
	public void certNoManage(RealNameAuthOrder order) {
		IdentityInfo identityInfo = authInfoManageDao.queryByCertNoEncrypt(order.getCertNoEncrypt());
		if (identityInfo == null) {
			IdentityInfo info = new IdentityInfo();
			info.setCertNo(order.getCertNo());
			info.setCertNoEncrypt(order.getCertNoEncrypt());
			info.setPayerName(order.getPayerName());

			authInfoManageDao.insertAuthOrderByIdentity(info);
		} else {
			identityInfo.setPayerName(order.getPayerName());
			authInfoManageDao.updateIdentityInfo(identityInfo);
		}
	}

}
