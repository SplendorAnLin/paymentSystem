package com.yl.payinterface.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.payinterface.core.dao.QuickPayFilingInfoDao;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;

/**
 * @ClassName QuickPayFilingInfoServiceImpl
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年11月20日 下午4:27:31
 */
@Service("quickPayFilingInfoService")
public class QuickPayFilingInfoServiceImpl implements QuickPayFilingInfoService {

	@Resource
	private QuickPayFilingInfoDao quickPayFilingInfoDao;

	@Override
	public void save(QuickPayFilingInfo quickPayFilingInfo) {
		quickPayFilingInfoDao.save(quickPayFilingInfo);
	}

	@Override
	public void update(QuickPayFilingInfo quickPayFilingInfo) {
		quickPayFilingInfoDao.update(quickPayFilingInfo);

	}

	@Override
	public QuickPayFilingInfo find(String bankCardNo, String interfaceInfoCode) {
		return quickPayFilingInfoDao.find(bankCardNo, interfaceInfoCode);
	}
	
	@Override
	public QuickPayFilingInfo findByIdCardNo(String idCardNo, String interfaceInfoCode) {
		return quickPayFilingInfoDao.findByIdCardNo(idCardNo, interfaceInfoCode);
	}
	
	public QuickPayFilingInfo findByCustomerCode(String customerCode) {
		return quickPayFilingInfoDao.findByCustomerCode(customerCode);
	}

}
