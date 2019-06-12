package com.yl.receive.core.dao;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.receive.core.entity.ReceiveConfigInfo;

/**
 * 代收配置数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public interface ReceiveConfigInfoDao extends BaseDao<ReceiveConfigInfo>{

	/**
	 * 
	 * @Description 查询代收配置
	 * @param ownerId
	 * @return
	 * @date 2017年1月4日
	 */
	public ReceiveConfigInfo queryBy(String ownerId);
	
	/**
	 * 分页查询代收配置
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return	ReceiveConfigInfo 代收配置信息
	 */
	public List<ReceiveConfigInfo> findAllRecfByPage(Map<String, Object> queryParams, Page<?> page);
}
