package com.yl.online.gateway.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.online.gateway.bean.License;
import com.yl.online.gateway.dao.LicenseDao;
import com.yl.online.gateway.mapper.LicenseMapper;

/**
 * 扫码入网数据实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Repository("licenseDao")
public class LicenseDaoImpl implements LicenseDao {

	@Resource
	private LicenseMapper licenseMapper;

	@Override
	public void licenseLoginInfo(License license) {
		licenseMapper.licenseLoginInfo(license);
	}

	@Override
	public void licenseBaseInfo(License license) {
		licenseMapper.licenseBaseInfo(license);
	}

	@Override
	public void licenseBankCard(License license) {
		licenseMapper.licenseBankCard(license);
	}

	@Override
	public License checkPhone(String phone) {
		return licenseMapper.checkPhone(phone);
	}

	@Override
	public License checkFullName(String fullName) {
		return licenseMapper.checkFullName(fullName);
	}

	@Override
	public License getInfo(String cardNo, String checkCode) {
		return licenseMapper.getInfo(cardNo, checkCode);
	}

	@Override
	public License findById(int id) {
		return licenseMapper.findById(id);
	}

	@Override
	public void again(License license) {
		licenseMapper.again(license);
	}
}