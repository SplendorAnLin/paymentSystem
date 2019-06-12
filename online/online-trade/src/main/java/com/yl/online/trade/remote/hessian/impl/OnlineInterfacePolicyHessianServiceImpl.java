package com.yl.online.trade.remote.hessian.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.online.trade.hessian.OnlineInterfacePolicyHessianService;
import com.yl.online.trade.hessian.bean.InterfacePolicyBean;
import com.yl.online.trade.service.InterfacePolicyService;

/**
 * 在线交易路由服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
@Service("onlineInterfacePolicyHessianService")
public class OnlineInterfacePolicyHessianServiceImpl implements OnlineInterfacePolicyHessianService {
	@Resource
	private InterfacePolicyService routerService;

	public void createInterfacePolicy(InterfacePolicyBean interfacePolicyBean) {
		routerService.createInterfacePolicy(interfacePolicyBean);
	}

	public Page queryInterfacePolicyBy(Page page) {
		return routerService.queryInterfacePolicyByPage(page);
	}

	public InterfacePolicyBean queryInterfacePolicyByCode(String code) {
		return routerService.queryInterfacePolicyByCode(code);
	}

	public void updateInterfacePolicy(InterfacePolicyBean interfacePolicyBean) {
		routerService.updateInterfacePolicy(interfacePolicyBean);
	}

	public List<InterfacePolicyBean> queryInterfacePolicysBy(Map<String, Object> params) {
		return routerService.queryInterfacePolicyBy(params);
	}

	public List<InterfacePolicyBean> queryInterfacePolicyByInterfaceType(String interfaceType) {
		return routerService.queryInterfacePolicyByInterfaceType(interfaceType);
	}

	@Override
	public Object findAllInterfacePolicy(Page page, Map<String, Object> params) {
		if(params!=null&&params.size()>1){
			if(null!=params.get("createStartTime")&&!"".equals(params.get("createStartTime"))){
				params.put("createStartTime", ((Date) params.get("createStartTime")).getTime()/1000);
			}
			if(null!=params.get("createEndTime")&&!"".equals(params.get("createEndTime"))){
				params.put("createEndTime", ((Date) params.get("createEndTime")).getTime()/1000);
			}
		}
		return routerService.findAllInterfacePolicy(page,params);
	}
}
