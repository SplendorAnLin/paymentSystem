package com.yl.online.trade.remote.hessian.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.online.trade.BaseTest;
import com.yl.online.trade.hessian.OnlineInterfacePolicyHessianService;
import com.yl.online.trade.hessian.bean.InterfaceInfoForRouterBean;
import com.yl.online.trade.hessian.bean.InterfacePolicyBean;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;

/**
 * 路由模版测试类
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class OnlineInterfacePolicyHessianServiceImplTest extends BaseTest{
	
	@Resource
	private OnlineInterfacePolicyHessianService onlineInterfacePolicyHessianService;
	
	@Test
	public void create(){
		InterfacePolicyBean interfacePolicyBean = new InterfacePolicyBean();
		interfacePolicyBean.setCode("AUTH_PAY-TMP_0001");
		interfacePolicyBean.setInterfaceType("AUTHPAY");
		interfacePolicyBean.setName("认证支付模版");
		
		List<InterfacePolicyProfileBean> profiles = new ArrayList<>();
		InterfacePolicyProfileBean interfacePolicyProfileBean = new InterfacePolicyProfileBean();
		interfacePolicyProfileBean.setCardType("DEBIT_CARD");
		interfacePolicyProfileBean.setEffectTime(DateUtils.addDays(new Date(), -1));
		interfacePolicyProfileBean.setExpireTime(DateUtils.addDays(new Date(), 1000));
		List<InterfaceInfoForRouterBean> interfaceInfos = new ArrayList<>();
		InterfaceInfoForRouterBean interfaceInfoForRouterBean = new InterfaceInfoForRouterBean();
		interfaceInfoForRouterBean.setInterfaceCode("RXT100000-AUTHPAY");
		interfaceInfoForRouterBean.setScale(1);
		interfaceInfos.add(interfaceInfoForRouterBean);
		interfacePolicyProfileBean.setInterfaceInfos(interfaceInfos);
		interfacePolicyProfileBean.setInterfaceProviderCode("RXT");
		interfacePolicyProfileBean.setPolicyType("PRIORITY");
		profiles.add(interfacePolicyProfileBean);
		
		interfacePolicyBean.setProfiles(profiles);
		interfacePolicyBean.setStatus("TRUE");
		onlineInterfacePolicyHessianService.createInterfacePolicy(interfacePolicyBean);
	}

}
