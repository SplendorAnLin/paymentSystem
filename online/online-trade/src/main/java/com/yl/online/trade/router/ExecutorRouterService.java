package com.yl.online.trade.router;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yl.online.model.bean.InterfacePolicyProfile;
import com.yl.online.model.model.Order;
import com.yl.online.trade.ExceptionMessages;
import com.yl.online.trade.exception.BusinessRuntimeException;
import com.yl.online.trade.router.template.InterfacePolicyTemplate;
import com.yl.online.trade.service.PartnerRouterService;

/**
 * 路由业务处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
@Component
public class ExecutorRouterService {
	
	@Resource
	private RouterStrategyMapper strategyMapper;
	
	@Resource
	private PartnerRouterService partnerRouterService;

	/**
	 * 转向到具体的路由目的地
	 * @param order 交易订单实体
	 * @return String 具体接口编码
	 */
	public String routeSpecifiedDestination(Order order, String interfaceType, String cardType, String interfaceProvider) {
		InterfacePolicyProfile interfacePolicyProfile = partnerRouterService.queryPartnerRouterBy(
				order.getReceiverRole().name(), order.getReceiver(), interfaceType, cardType, interfaceProvider);
		if (interfacePolicyProfile == null) throw new BusinessRuntimeException(ExceptionMessages.INTERFACE_POLICY_PROFILE_NOT_EXISTS);
		InterfacePolicyTemplate interfacePolicyTemplate = strategyMapper.routerHandlers.get(interfacePolicyProfile.getPolicyType());
		if (interfacePolicyTemplate == null) throw new BusinessRuntimeException(ExceptionMessages.ROUTER_MAPPER_ERROR);
		
		return interfacePolicyTemplate.route(interfacePolicyProfile, order.getAmount());
	}
	
	
}
