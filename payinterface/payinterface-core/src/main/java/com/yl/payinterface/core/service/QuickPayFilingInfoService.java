package com.yl.payinterface.core.service;

import com.yl.payinterface.core.model.QuickPayFilingInfo;

/**
 * @ClassName QuickPayFilingInfoService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年11月20日 下午4:23:31
 */
public interface QuickPayFilingInfoService {

	void save(QuickPayFilingInfo quickPayFilingInfo);

	void update(QuickPayFilingInfo quickPayFilingInfo);

	QuickPayFilingInfo find(String bankCardNo, String interfaceInfoCode);
	
	public QuickPayFilingInfo findByIdCardNo(String idCardNo, String interfaceInfoCode);
	
	QuickPayFilingInfo findByCustomerCode(String customerCode);
}
