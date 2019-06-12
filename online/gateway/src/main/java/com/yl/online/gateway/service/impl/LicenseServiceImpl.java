package com.yl.online.gateway.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.online.gateway.bean.License;
import com.yl.online.gateway.dao.LicenseDao;
import com.yl.online.gateway.service.LicenseService;

/**
 * 扫码入网服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Service("licenseService")
public class LicenseServiceImpl implements LicenseService {

	@Resource
	private LicenseDao licenseDao;
	
	@Override
	public void licenseLoginInfo(License license) {
		licenseDao.licenseLoginInfo(license);
	}

	@Override
	public void licenseBaseInfo(License license) {
		licenseDao.licenseBaseInfo(license);
	}

	@Override
	public void licenseBankCard(License license) {
		licenseDao.licenseBankCard(license);
	}

	@Override
	public License checkPhone(String phone) {
		License license = licenseDao.checkPhone(phone);
		if (license != null) {
			return license;
		}
		return null;
	}

	@Override
	public License checkFullName(String fullName) {
		License license = licenseDao.checkFullName(fullName);
		if (license != null) {
			return license;
		}
		return null;
	}

	@Override
	public License getInfo(String cardNo, String checkCode) {
		License license = licenseDao.getInfo(cardNo, checkCode);
		if (license != null) {
			return license;
		}
		return null;
	}

	@Override
	public License findById(int id) {
		License license = licenseDao.findById(id);
		if (license != null) {
			return license;
		}
		return null;
	}

	@Override
	public void again(License license) {
		licenseDao.again(license);
	}
}