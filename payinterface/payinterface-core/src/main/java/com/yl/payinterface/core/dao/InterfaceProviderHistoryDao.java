package com.yl.payinterface.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.model.InterfaceProviderHistory;

/**
 * 接口提供方历史数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
public interface InterfaceProviderHistoryDao {

	/**
	 * 接口提供方历史信息新增
	 * @param providerHistory
	 */
	public void create(InterfaceProviderHistory providerHistory);
	
	/**
	 * 根据提供方编号查询
	 * @param interfaceProviderCode
	 * @return
	 */
	public List<InterfaceProviderHistory> findByInterfaceProviderCode(@Param("interfaceProviderCode")String interfaceProviderCode);
	
	/**
	 * 分页查询历史
	 * @param page
	 * @param params
	 * @return
	 */
	public List<InterfaceProviderHistory> findAllHistoryPageAll(@Param("page")Page page, @Param("params") Map<String, Object> params);

}
