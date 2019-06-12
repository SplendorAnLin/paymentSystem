package com.yl.dpay.hessian.service;

import java.util.List;

import com.yl.dpay.hessian.beans.DpayConfigBean;
import com.yl.dpay.hessian.beans.RouteConfigBean;

/**
 * 代付配置远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
public interface DpayConfigFacade {
	
	/**
	 * 出款配置
	 * @param dpayConfigBean
	 */
	public void config(DpayConfigBean dpayConfigBean);
	
	/**
	 * 出款配置查询
	 * @return
	 */
	public DpayConfigBean findDfConfig();
	
	/**
	 * 创建代付路由
	 * @param routeConfigBean
	 * @param oper
	 */
	public void createRoute(RouteConfigBean routeConfigBean,String oper);
	
	/**
	 * 更新代付路由
	 * @param routeConfigBean
	 * @param oper
	 */
	public void updateRoute(RouteConfigBean routeConfigBean,String oper);
	
	/**
	 * 查询代付路由
	 * @param code
	 * @return
	 */
	public RouteConfigBean findByCode(String code);
	
	/**
	 * 查询代付默认路由
	 * @return
	 */
	public RouteConfigBean findRouteConfig();
	
	/**
	 * 根据条件查询所有代付路由
	 * @return
	 */
	public List<RouteConfigBean> findRouteConfigList(RouteConfigBean routeConfigBean);
	
}
