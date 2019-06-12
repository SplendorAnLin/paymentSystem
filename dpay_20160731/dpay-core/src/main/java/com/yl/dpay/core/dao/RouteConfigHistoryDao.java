package com.yl.dpay.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.dpay.core.entity.RouteConfigHistory;

/**
 * 代付路由历史配置历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface RouteConfigHistoryDao {
	
	/**
	 * @param routeConfigHistory
	 */
	public void insert(RouteConfigHistory routeConfigHistory);
	
	/**
	 * @param code
	 * @return
	 */
	public List<RouteConfigHistory> findByCode(@Param("code")String code);

}
