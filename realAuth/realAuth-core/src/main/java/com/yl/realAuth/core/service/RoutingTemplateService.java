package com.yl.realAuth.core.service;

import java.util.List;
import java.util.Map;

import com.yl.realAuth.hessian.bean.RoutingTemplateBean;
import com.yl.realAuth.model.RoutingRuleInfo;

/**
 * 路有模板
 * @author Shark
 * @since 2015年12月16日
 */
public interface RoutingTemplateService {

	/**
	 * 查询模板集合
	 * @param routingTemplateCode
	 * @param bankCode
	 * @param cardType
	 * @author Shark
	 * @return
	 */
	public List<RoutingRuleInfo> findRoutingRuleByTemplateCode(String routingTemplateCode, String bankCode, String cardType);
	
	/**
	 * 查询所有路由模版
	 * @return
	 */
	public List<Map> findRoutingTemplate();
	/**
	 * 添加路由信息
	 * @param routingTemplateBean
	 */
	public void addRoutingTemplate(RoutingTemplateBean routingTemplateBean);

	/**
	 * 修路由道信息
	 * @param routingTemplateBean
	 */
	public void modifyRoutingTemplate(RoutingTemplateBean routingTemplateBean) ;
}
