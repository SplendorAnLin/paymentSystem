package com.yl.payinterface.core.hessian;

/**
 * @ClassName QuickPayFilingInfoHessianService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年12月15日 下午4:57:11
 */
public interface QuickPayFilingInfoHessianService {

	/**
	 * @Description 更新报备信息
	 * @date 2017年12月15日
	 */
	void updateQuickPayFilingInfo(String customerCode, String channelCustomerCode);
}
