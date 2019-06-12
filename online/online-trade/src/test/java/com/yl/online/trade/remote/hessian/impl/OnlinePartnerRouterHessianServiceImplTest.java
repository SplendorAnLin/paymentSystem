package com.yl.online.trade.remote.hessian.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.online.trade.BaseTest;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;
import com.yl.online.trade.hessian.bean.PartnerRouterProfileBean;

/**
 * 商户路由测试类
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class OnlinePartnerRouterHessianServiceImplTest extends BaseTest{
	
	@Resource
	private OnlinePartnerRouterHessianService onlinePartnerRouterHessianService;
	
	@Test
	public void create(){
		PartnerRouterBean partnerRouterBean = new PartnerRouterBean();
		partnerRouterBean.setPartnerCode("100001");
		partnerRouterBean.setPartnerRole("CUSTOMER");
		partnerRouterBean.setStatus("TRUE");
		
		List<PartnerRouterProfileBean> profiles = new ArrayList<>();
		PartnerRouterProfileBean routerProfileBean = new PartnerRouterProfileBean();
		routerProfileBean.setInterfaceType("WXJSAPI");
		routerProfileBean.setPolicySelectType("TEMPLATE");
		routerProfileBean.setTemplateInterfacePolicy("WX_JSAPI-TMP_0001");
		profiles.add(routerProfileBean);
		
		PartnerRouterProfileBean routerProfileBean1 = new PartnerRouterProfileBean();
		routerProfileBean1.setInterfaceType("WXNATIVE");
		routerProfileBean1.setPolicySelectType("TEMPLATE");
		routerProfileBean1.setTemplateInterfacePolicy("WX_NATIVE-TMP_0001");
		profiles.add(routerProfileBean1);
		
		PartnerRouterProfileBean routerProfileBean2 = new PartnerRouterProfileBean();
		routerProfileBean2.setInterfaceType("AUTHPAY");
		routerProfileBean2.setPolicySelectType("TEMPLATE");
		routerProfileBean2.setTemplateInterfacePolicy("AUTH_PAY-TMP_0001");
		profiles.add(routerProfileBean2);
		
		partnerRouterBean.setProfiles(profiles);
		try {
			onlinePartnerRouterHessianService.createPartnerRouter(partnerRouterBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
