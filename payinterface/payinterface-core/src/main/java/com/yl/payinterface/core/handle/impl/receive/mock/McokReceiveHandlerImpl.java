package com.yl.payinterface.core.handle.impl.receive.mock;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yl.payinterface.core.handler.ReceiveHandler;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 代收mock
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月18日
 * @version V1.0.0
 */
@Service("mcokReceiveHandler")
public class McokReceiveHandlerImpl implements ReceiveHandler {

	@Override
	public Map<String, String> trade(Map<String, String> completeParameters) {
		completeParameters.put("resCode", "0000");
		completeParameters.put("resMsg", "交易成功");
		completeParameters.put("tranStat", "SUCCESS");
		completeParameters.put("compType", "NORMAL");
		return completeParameters;
	}

	@Override
	public InterfaceRequest complete(Map<String, String> completeParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> query(Map<String, String> completeParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> whiteList(Map<String, String> completeParameters) {
		// TODO Auto-generated method stub
		return null;
	}

}
