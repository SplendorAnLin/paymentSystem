package com.yl.payinterface.core.remote.hessian.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.payinterface.core.BaseTest;
import com.yl.payinterface.core.bean.InterfaceRemitBillHessian;
import com.yl.payinterface.core.hessian.RemitHessianService;

/**
 * 付款远程服务接口实现测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class TestRemitHessianServiceImplTest extends BaseTest {

	@Resource
	private RemitHessianService remitHessianService;

	@Test
	public void testTrade() throws Exception {
		InterfaceRemitBillHessian interfaceRemitBillHessian = new InterfaceRemitBillHessian();
		interfaceRemitBillHessian.setInterfaceCode("CHINA_PAY_100001_REMIT");
		interfaceRemitBillHessian.setAccountName("邱健");
		interfaceRemitBillHessian.setAccountNo("6228480011011981518");
		interfaceRemitBillHessian.setBillCode("DF-170112-101633828316");
		interfaceRemitBillHessian.setAccountType("INDIVIDUAL");
		interfaceRemitBillHessian.setAmount(0.05);
//		interfaceRemitBillHessian.setCardType(cardType);
		interfaceRemitBillHessian.setCerNo("522401198810180057");
		interfaceRemitBillHessian.setBankCode("ABC");
		interfaceRemitBillHessian.setBankName("农业银行");//中国农业银行股份有限公司北京中关村园区支行
		interfaceRemitBillHessian.setRemark("系统集成");
		remitHessianService.remit(interfaceRemitBillHessian);
	}
	@Test
	public void testQuery() throws Exception {
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("merId","CHINA_PAY_100001_REMIT");
		reqMap.put("merId","808080211304517");
		reqMap.put("merDate","20170114");//DateTimeUtils.getDateTimeToString(interfaceRequest.getRequestTime(), DateTimeUtils.DATE_FORMAT_YYYYMMDD);
		reqMap.put("merSeqId","1004055630950000");
		reqMap.put("version","20160530");
		reqMap.put("businessOrderID","TD-20170114-101820070531");
		remitHessianService.query(reqMap);
	}
//	@Test
//	public void testTrade() throws Exception {
//		ReceiveTradeBean receiveTradeBean = new ReceiveTradeBean();
//		receiveTradeBean.setAccountName("白聪祥");
//		receiveTradeBean.setAccountNo("6212260200029905938");
//		receiveTradeBean.setAccountNoType("CARD");
//		receiveTradeBean.setAccountType("OPEN");
//		receiveTradeBean.setAmount(1D);
//		receiveTradeBean.setBusinessCode("TOSA20141112000022");
//		receiveTradeBean.setBusinessOrderID("TOSA20141112000022");
//		receiveTradeBean.setBusinessOrderTime(new Date());
//		receiveTradeBean.setCertCode("410622199101286019");
//		receiveTradeBean.setCertType("ID");
//		receiveTradeBean.setCity("北京");
//		receiveTradeBean.setInterfaceCode("RECEIVE_NOAH_290001");
//		receiveTradeBean.setInterfaceProviderCode("ICBC");
//		receiveTradeBean.setBankName("中国工商银行股份有限公司北京通州支行新华分理处");
//		receiveTradeBean.setProvince("北京");
//		receiveTradeBean.setRemark("测试");
//		receiveTradeBean.setTradeType("PAY");
//		receiveTradeBean.setUsage("测试");
//		receiveTradeBean.setAccountName("邱健");
//		receiveTradeBean.setAccountNo("6228480011011981518");
//		receiveTradeBean.setAccountNoType("CARD");
//		receiveTradeBean.setAccountType("INDIVIDUAL");
//		receiveTradeBean.setAmount(0.02);
//		receiveTradeBean.setBusinessCode("TOSA20141112000022");
//		receiveTradeBean.setBusinessOrderID("TOSA20141112000022");
//		receiveTradeBean.setBusinessOrderTime(new Date());
//		receiveTradeBean.setCertCode("522401198810180057");
//		receiveTradeBean.setCertType("ID");
//		receiveTradeBean.setCity("北京");
//		receiveTradeBean.setBankCode("ABC");
//		receiveTradeBean.setInterfaceCode("CHINA_PAY_100001_RECEIVE");
//		receiveTradeBean.setInterfaceProviderCode("ICBC");
//		receiveTradeBean.setBankName("中国农业银行股份有限公司北京中关村园区支行");
//		receiveTradeBean.setProvince("北京");
//		receiveTradeBean.setRemark("系统集成");
//		receiveTradeBean.setTradeType("PAY");
//		receiveTradeBean.setUsage("系统集成");
//		
//		
//		receiveTradeBean.setCardType("DEBIT_CARD");
//		receiveHessianService.trade(receiveTradeBean);
//	}

	
	

}
