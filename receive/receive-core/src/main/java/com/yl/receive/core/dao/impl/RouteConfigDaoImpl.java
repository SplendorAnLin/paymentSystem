package com.yl.receive.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.receive.core.dao.RouteConfigDao;
import com.yl.receive.core.entity.RouteConfig;
import com.yl.receive.core.enums.Status;
import com.yl.receive.core.mybatis.mapper.RouteConfigMapper;

/**
 * 代付路由配置数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
@Repository("routeConfigDao")
public class RouteConfigDaoImpl implements RouteConfigDao {
	
	@Resource
	private RouteConfigMapper routeConfigMapper;

	@Override
	public void insert(RouteConfig t) {
		routeConfigMapper.insert(t);	
	}

	@Override
	public RouteConfig findById(Long id) {
		return routeConfigMapper.findById(id);
	}

	@Override
	public void update(RouteConfig t) {
		routeConfigMapper.update(t);
	}

	@Override
	public RouteConfig findByCode(String code) {
		return routeConfigMapper.findByCode(code);
	}

	@Override
	public List<RouteConfig> findBy(Status status) {
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
