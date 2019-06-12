package com.yl.receive.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.receive.core.entity.RouteConfigHistory;

/**
 * 代付路由配置历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
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
