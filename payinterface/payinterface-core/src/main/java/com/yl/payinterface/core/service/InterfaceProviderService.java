package com.yl.payinterface.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.bean.InterfaceProviderHistory;
import com.yl.payinterface.core.model.InterfaceProvider;

/**
 * 接口提供方服务
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public interface InterfaceProviderService {

	/**
	 * 分页查询历史
	 * @param page
	 * @param map
	 * @return
	 */
	public List<InterfaceProviderHistory> queryHistoryAll(String code);
	
	/**
	 * 分页查询所有
	 * @param interfaceProvider
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param page
	 * @return
	 */
	public Page queryAll(Page page , Map<String, Object> map);
	
	/**
	 * 接口提供方信息新增
	 * @param interfaceProvider
	 * @param source
	 * @param operator
	 */
	public void save(InterfaceProvider interfaceProvider, String source, String operator);

	/**
	 * 接口提供方信息修改
	 * @param interfaceProvider
	 * @param source
	 * @param operator
	 */
	public void modify(InterfaceProvider interfaceProvider, String source, String operator);

	/**
	 * 根据编号查询接口提供方信息
	 * @param interfaceProvider
	 * @return
	 */
	public InterfaceProvider queryByCode(String code);
	
	/**
	 * 查询所有接口提供方
	 * 
	 * @return
	 */
	public List<InterfaceProvider> queryAllInterfaceProvider();
	
	/**
	 * 分页查询历史
	 * @param page
	 * @param map
	 * @return
	 */
	public Page queryHistoryPageAll(Page page, Map<String,Object> params);

}
