package com.yl.dpay.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.RouteConfigHistoryDao;
import com.yl.dpay.core.entity.RouteConfigHistory;
import com.yl.dpay.core.mybatis.mapper.RouteConfigHistoryMapper;

/**
 * 代付路由历史配置历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("routeConfigHistoryDao")
public class RouteConfigHistoryDaoImpl implements RouteConfigHistoryDao {
	
	@Resource
	private RouteConfigHistoryMapper routeConfigHistoryMapper;

	@Override
	public void insert(RouteConfigHistory routeConfigHistory) {
		routeConfigHistoryMapper.insert(routeConfigHistory);
	}

	@Override
	public List<RouteConfigHistory> findByCode(String code) {
		return routeConfigHistoryMapper.findByCode(code);
	}

}
