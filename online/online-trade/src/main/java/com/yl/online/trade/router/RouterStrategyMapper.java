package com.yl.online.trade.router;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.yl.online.model.enums.InterfacePolicyType;
import com.yl.online.trade.router.template.InterfacePolicyTemplate;
import com.yl.online.trade.router.template.impl.PriorityInterfacePolicy;

/**
 * 映射具体的路由处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
@Component
public class RouterStrategyMapper implements ApplicationContextAware {

	public final Map<InterfacePolicyType, InterfacePolicyTemplate> routerHandlers = new HashMap<InterfacePolicyType, InterfacePolicyTemplate>();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		routerHandlers.put(InterfacePolicyType.COST, (CostInterfacePolicy) applicationContext.getBean("costInterfacePolicy"));
		routerHandlers.put(InterfacePolicyType.PRIORITY, (PriorityInterfacePolicy) applicationContext.getBean("priorityInterfacePolicy"));
//		routerHandlers.put(InterfacePolicyType.SCALE, (ScaleInterfacePolicy) applicationContext.getBean("scaleInterfacePolicy"));		
	}
	
	
}
