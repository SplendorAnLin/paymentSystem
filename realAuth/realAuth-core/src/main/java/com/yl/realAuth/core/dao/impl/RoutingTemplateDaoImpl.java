package com.yl.realAuth.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.realAuth.core.dao.RoutingTemplateDao;
import com.yl.realAuth.core.dao.mapper.RoutingTemplateMapper;
import com.yl.realAuth.model.RoutingRuleInfo;
import com.yl.realAuth.model.RoutingTemplate;

@Repository("routingTemplateDao")
public class RoutingTemplateDaoImpl implements RoutingTemplateDao {

	@Resource
	private RoutingTemplateMapper routingTemplateMapper;
	
	@Override
	public void insert(RoutingTemplate routingTemplate) {
		routingTemplateMapper.insert(routingTemplate);
	}

	@Override
	public RoutingTemplate findByCode(String code) {
		return routingTemplateMapper.findByCode(code);
	}

	@Override
	public void modifyRoutingTemplate(RoutingTemplate routingTemplate) {
		routingTemplateMapper.modifyRoutingTemplate(routingTemplate);
	}

	@Override
	public List<RoutingRuleInfo> findRoutingRuleByTemplateCode(String routingTemplateCode, String bankCode, String cardType) {
		return routingTemplateMapper.findRoutingRuleByTemplateCode(routingTemplateCode, bankCode, cardType);
	}
	
	public List<Map> findRoutingTemplate(){
		return routingTemplateMapper.findRoutingTemplate();
	}

}
