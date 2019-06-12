package com.yl.dpay.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.RouteConfigDao;
import com.yl.dpay.core.entity.RouteConfig;
import com.yl.dpay.core.enums.Status;
import com.yl.dpay.core.mybatis.mapper.RouteConfigMapper;

/**
 * 代付路由配置数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("routeConfigDao")
public class RouteConfigDaoImpl implements RouteConfigDao{

	@Resource
	private RouteConfigMapper routeConfigMapper;
	
	@Override
	public void insert(RouteConfig t) {
		routeConfigMapper.insert(t);	
	}

	@Override
	public RouteConfig findById(@Param("id")Long id) {
		return routeConfigMapper.findById(id);
	}

	@Override
	public void update(RouteConfig t) {
		routeConfigMapper.update(t);
	}

	@Override
	public RouteConfig findByCode(@Param("code")String code) {
		return routeConfigMapper.findByCode(code);
	}

	@Override
	public List<RouteConfig> findBy(@Param("status")Status status) {
		return routeConfigMapper.findBy(status);
	}

	@Override
	public RouteConfig findRouteConfig() {
		return routeConfigMapper.findRouteConfig();
	}

	@Override
	public List<RouteConfig> findRouteConfigList(RouteConfig routeConfig) {
		return routeConfigMapper.findRouteConfigList(routeConfig);
	}

}
