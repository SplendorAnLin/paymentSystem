package com.yl.payinterface.core.remote.hessian.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.payinterface.core.BaseTest;
import com.yl.payinterface.core.bean.ReceiveQueryBean;
import com.yl.payinterface.core.bean.ReceiveResponseBean;
import com.yl.payinterface.core.bean.ReceiveTradeBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.ReceiveHessianService;

/**
 * 代收远程服务接口实现测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class TestReceiveHessianServiceImplTest extends BaseTest {

	@Resource
	private ReceiveHessianService receiveHessianService;

	@Test
	public void testQuery() throws Exception {
		ReceiveQueryBean receiveTradeBean = new ReceiveQueryBean();
//		receiveTradeBean.setBusinessOrderID("TOSA20141112000023");
		receiveTradeBean.setBusinessOrderID("TOSA20141112000024");
//		receiveTradeBean.setOriginalInterfaceRequestID("TD-20170110-100585753138");/*("CHINA_PAY_100001_RECEIVE");*/
		
		receiveHessianService.query(receiveTradeBean);
	}
	
//	@Test
	public void testTrade() throws Exception {
		ReceiveTradeBean receiveTradeBean = new ReceiveTradeBean();
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
		receiveTradeBean.setAccountName("邱健");
		receiveTradeBean.setAccountNo("6228480011011981518");
		receiveTradeBean.setAccountNoType("CARD");
		receiveTradeBean.setAccountType("INDIVIDUAL");
		receiveTradeBean.setAmount(0.02);
		receiveTradeBean.setBusinessCode("TOSA20141112000022");
		receiveTradeBean.setBusinessOrderID("TOSA20141112000022");
		receiveTradeBean.setBusinessOrderTime(new Date());
		receiveTradeBean.setCertCode("522401198810180057");
		receiveTradeBean.setCertType("ID");
		receiveTradeBean.setCity("北京");
		receiveTradeBean.setBankCode("ABC");
		receiveTradeBean.setInterfaceCode("CHINA_PAY_100001_RECEIVE");
		receiveTradeBean.setInterfaceProviderCode("ICBC");
		receiveTradeBean.setBankName("中国农业银行股份有限公司北京中关村园区支行");
		receiveTradeBean.setProvince("北京");
		receiveTradeBean.setRemark("系统集成");
		receiveTradeBean.setTradeType("PAY");
		receiveTradeBean.setUsage("系统集成");
		
		
		receiveTradeBean.setCardType("DEBIT_CARD");
		receiveHessianService.trade(receiveTradeBean);
	}

//	@Test
	public void testComplete() {
		String params = "00001395631999999999999NCPS10020344zG+1MR69b8w2PYKVEDr+t051w7r7WiONaVT34iMgZZWMpKFffK2FqtPR5DviO/OJI4gOmmsQ9W0TAqVZv4AJ/xwyUkshxwh8ASQXQojbZJx18cJJnEjjolf8MV29+pgpMH/p8roV3vSQETAo/Pji+hNvxz6mPfyC0JUym/pRyxoYj77suA7BTKLZYdXLgWkhPMvVa04SOhiLnc0dHPPWFXMoJz+ttuXJ4agRkjKEZLwu7o9q2BHRNo33jyhGyAKgb10ybNRwtB7hjcKukzY94I8HHQoL0EAxZ/zpM3hUa+3HiwvsBetSibvOyut0W7b0rjTFIyWjsZFGtEdbnY2mzQ==J58dgYp6eE1FtsiIClo/qRFQQHdDkMepXFl/OtwUa2O2l3VCjX2QSBUB8weuR+JsrmGXmlMseDvBpbJaMAm92C3huuDFE2I7WrkuAIQfwr08PHjCGZgCPoWr4ki/JRfdAqfB0iCSOdYylWz6avjQnJvbGV5D0mUx6fQGVxN4ecSWjajA5QQ9OFCsYMqxJPhbNHi41QXXhqS+6al/xLizk4oLn3ELWp8jpAGIdsDHXsbtgj897i3TqMABSvd2MGc6Xu32IvZTjuMyjR10jQj1mnbuhViVh5Vsz+2tj+L9IwJ5/zgzfzaxOM8nXLK4caAr4oAcCLdeoWpxfncRS1Q7e6JEjnxEDgzHbBBIxdOKrxl+/eiGmkket4lPpiM3isMEuAYyRCwT4lMdsFblUIti6bZxovvz//ZsUXeJd+r84D4DKot7KHPWlBVv7WzOyBq2dWyK3ofckMSYhy5qjYQX1zrzWujx5cLGs7kyxzE/JVIiqNsqabIlT+0BT4xl7RPuhdvM8hpefQoBnkSSj2AD1Y8An9fcVugnDbcr+6jEO9nm0EZ78q3JJHnC+59Wav2JdQzqnQCGTbKHbuD90jlr9GfP5IWyo72wvpej/hz+nbu1ARxw84N2fqrmLEYHF+dcF5ssAJyD+kdWk1gh/SG6GpS35/VcAQicNUH/tS83ROYrqS65y0AqLbwXdmluYzxEafh29DKErVrmQV1GYxcaMkO7uCOW+4K9NgUnDIvpqfpQIA0MPQjVZR1lGv98Yw3RwrI2KnyBk8e+xIwcYGcec7imm1m8qfkhq64/HhLndoIU9OlmhE7mTOQsQKvWvewB/dx1OfmL1nvCdboEaeXSxk1zpBPrEnlyir0J6oduJV7ktVKijxeFHjlx8mVWp2QIYSuNd7pGd+1EFe2E0QdCmlWgaKtqAXPlX35++Q/8owI+MHa+8P879J9GvhBp0nLjEzlYfT78NFZ7DnlxSN6SA4R/jJ/NJgMtd6X0g1v+mT42a1Yr2DLCKMotSLNZQTcS";

		Map<String, Object> receiveResponseMsg = new LinkedHashMap<String, Object>();
		receiveResponseMsg.put("responseData", params);
		receiveResponseMsg.put("businessCompleteType", BusinessCompleteType.NORMAL);

		ReceiveResponseBean receiveResponseBean = new ReceiveResponseBean();
		receiveResponseBean.setInterfaceCode("RECEIVE_NOAH_290001");
		receiveResponseBean.setReceiveResponseMsg(receiveResponseMsg);
		receiveHessianService.complete(receiveResponseBean);
	}

}
