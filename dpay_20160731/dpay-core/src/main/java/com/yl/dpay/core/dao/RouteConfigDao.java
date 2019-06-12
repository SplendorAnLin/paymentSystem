package com.yl.dpay.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.dpay.core.entity.RouteConfig;
import com.yl.dpay.core.enums.Status;

/**
 * 代付路由配置数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface RouteConfigDao extends BaseDao<RouteConfig>{
	
	/**
	 * @param code
	 * @return
	 */
	RouteConfig findByCode(@Param("code")String code);
	
	
	/**
	 * @param status
	 * @return
	 */
	List<RouteConfig> findBy(@Param("status")Status status);
	
	/**
	 * @return
	 */
	RouteConfig findRouteConfig();
	
	/**
	 * 根据条件查询所有代付路由
	 * @return
	 */
	public List<RouteConfig> findRouteConfigList(RouteConfig routeConfigBean);
}
