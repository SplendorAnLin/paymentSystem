package com.yl.payinterface.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.payinterface.core.service.InterfaceRequestReverseService;
import com.yl.payinterface.core.dao.InterfaceRequestReverseDao;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.enums.ReverseStatus;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.model.InterfaceRequestReverse;

/**
 * 补单接口信息处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
@Service("interfaceRequestReverseService")
public class InterfaceRequestReverseServiceImpl implements InterfaceRequestReverseService {

	@Resource
	private InterfaceRequestReverseDao interfaceRequestReverseDao;
	
	@Override
	public InterfaceRequestReverse queryInterfaceRequestReverse(String interfaceRequestID) {
		return interfaceRequestReverseDao.queryInterfaceRequestReverse(interfaceRequestID);
	}

	@Override
	public void recordInterfaceRequestReverse(InterfaceRequest interfaceRequest, String payType) {
		InterfaceRequestReverse reverseInterfaceRequest = new InterfaceRequestReverse();
		reverseInterfaceRequest.setInterfaceInfoCode(interfaceRequest.getInterfaceInfoCode());
		reverseInterfaceRequest.setInterfaceOrderID(interfaceRequest.getInterfaceOrderID());
		reverseInterfaceRequest.setInterfaceProviderCode(interfaceRequest.getInterfaceProviderCode());
		reverseInterfaceRequest.setInterfaceRequestID(interfaceRequest.getInterfaceRequestID());
		reverseInterfaceRequest.setAmount(interfaceRequest.getAmount());
		reverseInterfaceRequest.setBussinessOrderID(interfaceRequest.getBussinessOrderID());
		reverseInterfaceRequest.setCardType(interfaceRequest.getCardType());
		reverseInterfaceRequest.setReverseCount(0);
		reverseInterfaceRequest.setReverseStatus(ReverseStatus.WAIT_REVERSE);
		reverseInterfaceRequest.setPayType(InterfaceType.valueOf(payType));
		interfaceRequestReverseDao.save(reverseInterfaceRequest);
	}

	@Override
	public List<InterfaceRequestReverse> queryNeedReverseOrderInterfaceRequest(int maxCount, int maxNum) {
		return interfaceRequestReverseDao.queryNeedReverseOrderInterfaceRequest(maxCount, maxNum);
	}

	@Override
	public void modifyInterfaceRequestReverse(InterfaceRequestReverse interfaceRequestReverse) {
		interfaceRequestReverseDao.modifyInterfaceRequestReverse(interfaceRequestReverse);
	}

	@Override
	public void modifyReverseCount(InterfaceRequestReverse originalInterfaceRequestReverse) {
		interfaceRequestReverseDao.modifyReverseCount(originalInterfaceRequestReverse);
	}

	@Override
	public List<InterfaceRequestReverse> queryMicropayReverseOrderInterfaceRequest(int maxCount, int maxNum) {
		return interfaceRequestReverseDao.queryMicropayReverseOrderInterfaceRequest(maxCount, maxNum);
	}
}
