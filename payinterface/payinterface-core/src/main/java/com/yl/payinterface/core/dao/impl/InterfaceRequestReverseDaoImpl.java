package com.yl.payinterface.core.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.payinterface.core.dao.InterfaceRequestReverseDao;
import com.yl.payinterface.core.dao.mapper.InterfaceRequestReverseMapper;
import com.yl.payinterface.core.model.InterfaceRequestReverse;
import com.yl.payinterface.core.utils.CodeBuilder;

/**
 * 支付接口补单初始化数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
@Repository("interfaceRequestReverseDao")
public class InterfaceRequestReverseDaoImpl implements InterfaceRequestReverseDao {
	
	@Resource
	private InterfaceRequestReverseMapper interfaceRequestReverseMapper;
	
	@Override
	public InterfaceRequestReverse queryInterfaceRequestReverse(String interfaceRequestID) {
		return interfaceRequestReverseMapper.queryInterfaceRequestReverse(interfaceRequestID);
	}

	@Override
	public void save(InterfaceRequestReverse reverseInterfaceRequest) {
		reverseInterfaceRequest.setCode(CodeBuilder.build("PRIR", "yyyyMMdd", 6));
		reverseInterfaceRequest.setCreateTime(new Date());
		reverseInterfaceRequest.setVersion(System.currentTimeMillis());	
		interfaceRequestReverseMapper.save(reverseInterfaceRequest);
	}

	@Override
	public List<InterfaceRequestReverse> queryNeedReverseOrderInterfaceRequest(int maxCount, int maxNum) {
		return interfaceRequestReverseMapper.queryNeedReverseOrderInterfaceRequest(maxCount, maxNum);
	}

	@Override
	public void modifyInterfaceRequestReverse(InterfaceRequestReverse interfaceRequestReverse) {
		interfaceRequestReverseMapper.modifyInterfaceRequestReverse(interfaceRequestReverse);
	}

	@Override
	public void modifyReverseCount(InterfaceRequestReverse originalInterfaceRequestReverse) {
		interfaceRequestReverseMapper.modifyReverseCount(originalInterfaceRequestReverse);
	}

	@Override
	public List<InterfaceRequestReverse> queryMicropayReverseOrderInterfaceRequest(int maxCount, int maxNum) {
		return interfaceRequestReverseMapper.queryMicropayReverseOrderInterfaceRequest(maxCount, maxNum);
	}
}
