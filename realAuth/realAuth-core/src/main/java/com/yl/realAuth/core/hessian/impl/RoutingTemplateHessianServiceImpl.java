package com.yl.realAuth.core.hessian.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.realAuth.core.service.RoutingTemplateService;
import com.yl.realAuth.hessian.RoutingTemplateHessianService;
import com.yl.realAuth.hessian.bean.RoutingTemplateBean;

/**
 * 路由信息Hessian
 * @author Shark
 * @since 2016年3月14日
 */
@Service("routingTemplateHessianService")
public class RoutingTemplateHessianServiceImpl implements RoutingTemplateHessianService {

	@Resource
	private RoutingTemplateService routingTemplateService;

	@Override
	public void addRoutingTemplate(RoutingTemplateBean routingTemplateBean) {
		routingTemplateService.addRoutingTemplate(routingTemplateBean);
	}

	@Override
	public void modifyRoutingTemplate(RoutingTemplateBean routingTemplateBean) {
		routingTemplateService.modifyRoutingTemplate(routingTemplateBean);

	}

	@Override
	public List<Map> findAllTemplate() {
		return routingTemplateService.findRoutingTemplate();
	}
}