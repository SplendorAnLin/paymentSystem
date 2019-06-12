package com.yl.payinterface.core.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yl.payinterface.core.dao.BindCardInfoDao;
import com.yl.payinterface.core.model.BindCardInfo;
import com.yl.payinterface.core.service.BindCardInfoService;

@Service("bindCardInfoService")
public class BindCardInfoServiceImpl implements BindCardInfoService {

	@Resource
	private BindCardInfoDao bindCardInfoDao;

	@Override
	public void updateFailed(String cardNo, String interfaceInfoCode) {
		if (StringUtils.isNotEmpty(cardNo) || StringUtils.isNotEmpty(interfaceInfoCode)) {
			bindCardInfoDao.updateFailed(cardNo, interfaceInfoCode);
		}
	}

	@Override
	public void save(BindCardInfo bindCardInfo) {
		bindCardInfoDao.save(bindCardInfo);
	}

	@Override
	public void update(BindCardInfo bindCardInfo) {
		BindCardInfo info = bindCardInfoDao.find(bindCardInfo.getCardNo(), bindCardInfo.getInterfaceInfoCode());
		info.setStatus(bindCardInfo.getStatus());
		info.setToken(bindCardInfo.getToken());
		info.setEffectiveDate(bindCardInfo.getEffectiveDate());
		info.setExpiryDate(bindCardInfo.getExpiryDate());
		info.setCvv(bindCardInfo.getCvv());
		info.setValidityYear(bindCardInfo.getValidityYear());
		info.setValidityMonth(bindCardInfo.getValidityMonth());
		bindCardInfoDao.update(bindCardInfo);
	}

	@Override
	public BindCardInfo find(String cardNo, String interfaceInfoCode) {
		if (StringUtils.isEmpty(cardNo) || StringUtils.isEmpty(interfaceInfoCode)) {
			return null;
		}
		return bindCardInfoDao.find(cardNo, interfaceInfoCode);
	}
	
	@Override
	public void saveOrUpdate(BindCardInfo bindCardInfo) {
		BindCardInfo info= bindCardInfoDao.find(bindCardInfo.getCardNo(), bindCardInfo.getInterfaceInfoCode());
		if (info != null) {
			info.setStatus(bindCardInfo.getStatus());
			info.setToken(bindCardInfo.getToken());
			info.setEffectiveDate(bindCardInfo.getEffectiveDate());
			info.setExpiryDate(bindCardInfo.getExpiryDate());
			info.setCvv(bindCardInfo.getCvv());
			info.setValidityYear(bindCardInfo.getValidityYear());
			info.setValidityMonth(bindCardInfo.getValidityMonth());
			bindCardInfoDao.update(info);
		}else {
			bindCardInfoDao.save(bindCardInfo);
		}
	}

	@Override
	public BindCardInfo findEffective(String cardNo, String interfaceInfoCode) {
		return bindCardInfoDao.findEffective(cardNo, interfaceInfoCode);
	}

}
