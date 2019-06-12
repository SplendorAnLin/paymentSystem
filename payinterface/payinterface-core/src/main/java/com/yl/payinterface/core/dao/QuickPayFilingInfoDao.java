package com.yl.payinterface.core.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.payinterface.core.model.QuickPayFilingInfo;

/** 
 * @ClassName QuickPayFilingInfoDao 
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @author Administrator 
 * @date 2017年11月20日 下午3:56:02  
 */
public interface QuickPayFilingInfoDao {

	/**
	 * 
	 * @Description TODO
	 * @param quickPayFilingInfo
	 * @date 2017年11月20日
	 */
	void save(QuickPayFilingInfo quickPayFilingInfo);
	
	void update(QuickPayFilingInfo quickPayFilingInfo);
	
	QuickPayFilingInfo find(@Param("bankCardNo")String bankCardNo, @Param("interfaceInfoCode")String interfaceInfoCode);
	
	QuickPayFilingInfo findByIdCardNo(@Param("idCardNo")String idCardNo, @Param("interfaceInfoCode")String interfaceInfoCode);

	QuickPayFilingInfo findByCustomerCode(@Param("customerCode")String customerCode);
}
