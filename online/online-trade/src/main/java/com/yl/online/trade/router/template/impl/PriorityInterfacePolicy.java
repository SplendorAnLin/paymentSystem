package com.yl.online.trade.router.template.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import com.yl.online.model.bean.InterfaceInfoForRouter;
import com.yl.online.model.bean.InterfacePolicyProfile;
import com.yl.online.trade.router.template.InterfacePolicyTemplate;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;

/**
 * 权重处理器（权重值越小优先级越高）
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
@Component("priorityInterfacePolicy")
public class PriorityInterfacePolicy extends InterfacePolicyTemplate {
	
	@Resource
	private PayInterfaceHessianService payInterfaceHessianService;
	
	@Override
	public String handleSpecifiedRouter(InterfacePolicyProfile interfacePolicyProfile, Double amount) {
		List<InterfaceInfoForRouter> interfaceInfoForRouters = interfacePolicyProfile.getInterfaceInfos();
		if (interfaceInfoForRouters.size() == 1) return interfaceInfoForRouters.get(0).getInterfaceCode();
		
		Iterator<InterfaceInfoForRouter> iterator = interfaceInfoForRouters.iterator();
		List<InterfaceInfoForRouter> finalInterfaceInfoForRouters = new ArrayList<InterfaceInfoForRouter>();
		List<Integer> prioritys = new ArrayList<Integer>();
		while (iterator.hasNext()) {
			InterfaceInfoForRouter interfaceInfoForRouter = iterator.next();
			// 判断接口是否被禁用
			InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(interfaceInfoForRouter.getInterfaceCode());
			if (interfaceInfoBean.getStatus().equals(InterfaceInfoStatus.FALSE)) continue;
			finalInterfaceInfoForRouters.add(interfaceInfoForRouter);
			prioritys.add(interfaceInfoForRouter.getScale());
		}
		// 第一个权重值
		Integer higerPriority = prioritys.get(0);
		// 权重值索引
		List<Integer> priorityIdxs = new ArrayList<Integer>();
		priorityIdxs.add(0);
		
		for (int cursorIdx = 1; cursorIdx < prioritys.size(); cursorIdx++) {
			if (higerPriority.compareTo(prioritys.get(cursorIdx)) > 0) {
				higerPriority = prioritys.get(cursorIdx);
				priorityIdxs.clear();
				priorityIdxs.add(cursorIdx);
			} else if (higerPriority.compareTo(prioritys.get(cursorIdx)) == 0) priorityIdxs.add(cursorIdx);
		}
		// 随机数小于priorityIdxs.size()
		return finalInterfaceInfoForRouters.get(priorityIdxs.get(RandomUtils.nextInt(priorityIdxs.size()))).getInterfaceCode();
	}


}
