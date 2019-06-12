package com.yl.cachecenter.service;

import java.util.List;

import com.yl.cachecenter.model.Station;

/**
 * 公交车站信息服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
public interface StationService {

	/**
	 * 批量新增站点信息
	 * @param stations 站点列表
	 */
	void add(List<Station> stations);
	
	/**
	 * 模糊搜索站点信息
	 * @param name 站点名称关键字
	 * @param limit 查询条数（<=0时为所有）
	 * @param fields 返回参数列表（不包含code）
	 * @param routeNo 路线编号
	 * @param flag 上下行标识
	 * @return 站点信息列表
	 */
	List<Station> suggestSearch(String name, int limit, String[] fields , String routeNo , String flag);

}
