package com.yl.payinterface.core.remote.hessian.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.hessian.QuickPayFilingInfoHessianService;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;

/** 
 * @ClassName QuickPayFilingInfoHessianServiceImpl 
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @author Administrator 
 * @date 2017年12月15日 下午5:05:41  
 */
@Service("quickPayFilingInfoHessianService")
public class QuickPayFilingInfoHessianServiceImpl implements QuickPayFilingInfoHessianService{

	@Resource
	private QuickPayFilingInfoService quickPayFilingInfoService;
	
	@Override
	public void updateQuickPayFilingInfo(String customerCode, String channelCustomerCode) {
		QuickPayFilingInfo quickPayFilingInfo = quickPayFilingInfoService.findByCustomerCode(customerCode);
		if (quickPayFilingInfo.getStatus() == InterfaceInfoStatus.FALSE) {
			quickPayFilingInfo.setChannelCustomerCode(channelCustomerCode);
			quickPayFilingInfo.setStatus(InterfaceInfoStatus.TRUE);
			quickPayFilingInfoService.update(quickPayFilingInfo);
		}
		
	}

}
