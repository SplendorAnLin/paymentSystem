package com.yl.payinterface.core.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.payinterface.core.dao.QuickPayFilingInfoDao;
import com.yl.payinterface.core.dao.mapper.QuickPayFilingInfoMapper;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.utils.CodeBuilder;

/** 
 * @ClassName QuickPayFilingInfoImpl 
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @author Administrator 
 * @date 2017年11月20日 下午3:56:25  
 */
@Repository("quickPayFilingInfoDao")
public class QuickPayFilingInfoImpl implements QuickPayFilingInfoDao {
	
	@Resource
	private QuickPayFilingInfoMapper quickPayFilingInfoMapper;
	
	public void save(QuickPayFilingInfo quickPayFilingInfo) {
		quickPayFilingInfo.setCode(CodeBuilder.build("QPFI", "yyyymmdd"));
		quickPayFilingInfo.setCreateTime(new Date());
		quickPayFilingInfo.setVersion(System.currentTimeMillis());
		quickPayFilingInfoMapper.save(quickPayFilingInfo);
	}

	@Override
	public void update(QuickPayFilingInfo quickPayFilingInfo) {
		quickPayFilingInfoMapper.update(quickPayFilingInfo);
		
	}

	@Override
	public QuickPayFilingInfo find(String bankCardNo, String interfaceInfoCode) {
		return quickPayFilingInfoMapper.find(bankCardNo, interfaceInfoCode);
	}
	
	@Override
	public QuickPayFilingInfo findByCustomerCode(String customerCode) {
		return quickPayFilingInfoMapper.findByCustomerCode(customerCode);
	}
	
	@Override
	public QuickPayFilingInfo findByIdCardNo(String idCardNo, String interfaceInfoCode) {
		return quickPayFilingInfoMapper.findByIdCardNo(idCardNo, interfaceInfoCode);
	}
}
