package com.yl.realAuth.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yl.realAuth.model.RoutingRuleInfo;
import com.yl.realAuth.model.RoutingTemplate;

public interface RoutingTemplateDao {

	
	/**
	 * 插入
	 * @param routingTemplate
	 */
	public void insert(RoutingTemplate routingTemplate);

	/**
	 * 根据主键查询路由模板
	 * @param code
	 * @return
	 */
	public RoutingTemplate findByCode(String code);

	/**
	 * 修路由道信息
	 * @param routingTemplate
	 */
	public void modifyRoutingTemplate(RoutingTemplate routingTemplate) ;
	
	/**
	 * 
	 * @Description 查询路由规则
	 * @param routingTemplateCode
	 * @param bankCode
	 * @param cardType
	 * @return
	 * @date 2017年7月8日
	 */
	public List<RoutingRuleInfo> findRoutingRuleByTemplateCode(@Param("routingTemplateCode") String routingTemplateCode, @Param("bankCode") String bankCode, @Param("cardType") String cardType);
	/**
	 * 查询所有路由模版
	 * @return
	 */
	public List<Map> findRoutingTemplate();
}
