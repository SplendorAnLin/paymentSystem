package com.yl.realAuth.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.realAuth.core.dao.RoutingTemplateDao;
import com.yl.realAuth.core.service.RoutingTemplateService;
import com.yl.realAuth.hessian.bean.RoutingTemplateBean;
import com.yl.realAuth.model.RoutingRuleInfo;

@Service("routingTemplateService")
public class RoutingTemplateServiceImpl implements RoutingTemplateService {

	@Resource
	private RoutingTemplateDao routingTemplateDao;
	
	@Override
	public List<RoutingRuleInfo> findRoutingRuleByTemplateCode(String routingTemplateCode, String bankCode, String cardType) {
		List<RoutingRuleInfo> list = routingTemplateDao.findRoutingRuleByTemplateCode(routingTemplateCode, bankCode, cardType);
		if (list == null || list.size() == 0) return new ArrayList<RoutingRuleInfo>();
		return list;
	}
	
	

	@Override
	public void addRoutingTemplate(RoutingTemplateBean routingTemplateBean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyRoutingTemplate(RoutingTemplateBean routingTemplateBean) {
		// TODO Auto-generated method stub

	}



	@Override
	public List<Map> findRoutingTemplate() {
		return routingTemplateDao.findRoutingTemplate();
	}

}
