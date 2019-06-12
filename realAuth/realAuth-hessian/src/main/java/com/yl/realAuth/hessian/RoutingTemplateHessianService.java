package com.yl.realAuth.hessian;

import java.util.List;
import java.util.Map;

import com.yl.realAuth.hessian.bean.RoutingTemplateBean;

/**
 * 路由信息
 * @author shark
 * @since 2016年3月14日
 */
public interface RoutingTemplateHessianService {
	/**
	 * 添加路由信息
	 * @param routingTemplateBean
	 */
	public void addRoutingTemplate(RoutingTemplateBean routingTemplateBean);

	/**
	 * 修改路由信息
	 * @param routingTemplateBean
	 */
	public void modifyRoutingTemplate(RoutingTemplateBean routingTemplateBean);
	
	/**
	 * 查询路由模板
	 */
	public List<Map> findAllTemplate();
}