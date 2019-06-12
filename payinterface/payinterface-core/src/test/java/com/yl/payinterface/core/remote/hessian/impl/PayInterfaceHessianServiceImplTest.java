package com.yl.payinterface.core.remote.hessian.impl;

/**
 * 银联远程服务接口实现测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class PayInterfaceHessianServiceImplTest /* extends BaseTest */{

	// @Resource
	// private InternetbankHessianService internetbankHessianService;
	//
	// public void testB2CTradeHandle() throws Exception {
	//
	// InternetbankSalesTradeBean b2CTradeBean = new InternetbankSalesTradeBean();
	// b2CTradeBean.setAmount(0.01);
	// b2CTradeBean.setBusinessCode("10000000");
	// b2CTradeBean.setBusinessOrderID("10000000");
	// b2CTradeBean.setBusinessOrderTime(new Date());
	// b2CTradeBean.setCurrency("001");
	// b2CTradeBean.setInstallmentTimes("1");
	// b2CTradeBean.setProductName("IPHONE 5");
	// b2CTradeBean.setProductNumber("1");
	// b2CTradeBean.setProductType("1");
	// b2CTradeBean.setInterfaceCode("ICBC584001");
	//
	// AuthBean auth = new AuthBean();
	// auth.setInvokeSystem("test");
	// auth.setTimestamp(System.currentTimeMillis());
	// auth.setSign(HessianSignUtils.md5DigestAsHex(new String(auth.getInvokeSystem() + auth.getTimestamp() + b2CTradeBean.toString()).getBytes()));
	// internetbankHessianService.trade(auth, b2CTradeBean);
	// }
	//
	// /**
	// * 中金支付订单支付测试
	// * @throws Exception
	// */
	// @Test
	// public void testB2CTradeHandle_CFCA() throws Exception {
	//
	// InternetbankSalesTradeBean b2CTradeBean = new InternetbankSalesTradeBean();
	// b2CTradeBean.setAmount(0.01);
	// b2CTradeBean.setBusinessCode("12345678");
	// b2CTradeBean.setBusinessOrderID("0989282837");
	// b2CTradeBean.setBusinessOrderTime(new Date());
	// b2CTradeBean.setCurrency("001");
	// b2CTradeBean.setInstallmentTimes("1");
	// b2CTradeBean.setProductName("IPHONE 5");
	// b2CTradeBean.setProductNumber("1");
	// b2CTradeBean.setProductType("1");
	// b2CTradeBean.setInterfaceCode("B2B_CFCA_100001");
	// b2CTradeBean.setInterfaceProviderCode("CFCA");
	//
	// AuthBean auth = new AuthBean();
	// auth.setInvokeSystem("test");
	// auth.setOperator("test");
	// auth.setTimestamp(System.currentTimeMillis());
	// auth.setSign(HessianSignUtils.md5DigestAsHex(new String(auth.getInvokeSystem() + auth.getTimestamp() + b2CTradeBean.toString()).getBytes()));
	// internetbankHessianService.trade(auth, b2CTradeBean);
	// }
	//
	// /**
	// * 中金支付订单支付结果查询测试
	// * @throws Exception
	// */
	// @Test
	// public void testB2CTradeHandleQuery() throws Exception {
	//
	// InternetbankSalesTradeBean b2CTradeBean = new InternetbankSalesTradeBean();
	// b2CTradeBean.setAmount(0.01);
	// b2CTradeBean.setBusinessCode("12345678");
	// b2CTradeBean.setBusinessOrderID("1c312f1a-f884-4e9c-a8dd-e9dbaa");
	// b2CTradeBean.setInterfaceCode("131114642403198");
	// b2CTradeBean.setBusinessOrderTime(new Date());
	// b2CTradeBean.setCurrency("001");
	// b2CTradeBean.setInstallmentTimes("1");
	// b2CTradeBean.setProductName("IPHONE 5");
	// b2CTradeBean.setProductNumber("1");
	// b2CTradeBean.setProductType("1");
	// b2CTradeBean.setInterfaceCode("B2B_CFCA_100001");
	// b2CTradeBean.setInterfaceProviderCode("CFCA");
	//
	// AuthBean auth = new AuthBean();
	// auth.setInvokeSystem("test");
	// auth.setOperator("test");
	// auth.setTimestamp(System.currentTimeMillis());
	// auth.setSign(HessianSignUtils.md5DigestAsHex(StringUtils.concatToSB(auth.toString(), b2CTradeBean.toString(), "payinterface").toString().getBytes()));
	// internetbankHessianService.trade(auth, b2CTradeBean);
	// }

}
