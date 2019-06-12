package com.yl.payinterface.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.bean.InterfaceProviderQueryBean;
import com.yl.payinterface.core.model.InterfaceProvider;

/**
 * 接口提供方数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
public interface InterfaceProviderDao {

	/**
	 * 分页查询所有
	 * @param interfaceProvider
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param page
	 * @return
	 */
	public List<InterfaceProvider> findAll(@Param("page")Page page, @Param("params") Map<String, Object> params);
	
	/**
	 * 接口提供方信息新增
	 * 
	 * @param interfaceProvider
	 */
	public void create(InterfaceProvider interfaceProvider);

	/**
	 * 接口提供方信息修改
	 * 
	 * @param interfaceProvider
	 * @param newVersion
	 */
	public void update(@Param("interfaceProvider") InterfaceProvider interfaceProvider, @Param("newVersion") Long newVersion);

	/**
	 * 根据接口提供方编号查询提供方信息
	 * 
	 * @param code
	 * @return
	 */
	public InterfaceProvider findByCode(@Param("code") String code);

	/**
	 * 查询所有接口提供方
	 * 
	 * @return
	 */
	public List<InterfaceProvider> queryAllInterfaceProvider();


}
