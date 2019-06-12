package com.yl.payinterface.core.hessian;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.bean.InterfaceProviderHistory;
import com.yl.payinterface.core.bean.InterfaceProviderQueryBean;

/**
 * 接口提供方Bean远程接口服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public interface InterfaceProviderHessianService {
	
	/**
	 * 查询历史
	 * @param code
	 * @return
	 */
	public List<InterfaceProviderHistory> queryHistoryAll(String code);
	
	/**
	 * 分页查询历史
	 * @param page
	 * @param map
	 * @return
	 */
	public Page queryHistoryPageAll(Page<?> page, Map<String, Object> params);

	/**
	 * 分页查询接口提供方信息
	 * 
	 * @param interfaceProviderStr
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param page
	 * @return
	 */
	public Page queryAll(Page page, Map<String, Object> map);

	/**
	 * 查询接口提供方简称
	 * 
	 * @return
	 */
	public List<InterfaceProviderQueryBean> findAllShortName();

	/**
	 * 根据接口提供方编号查询提供方信息
	 * 
	 * @param code
	 * @return
	 */
	public InterfaceProviderQueryBean queryByCode(String code);
	
	/**
	 * 查询所有接口提供方
	 * @return
	 */
	public List<InterfaceProviderQueryBean> findAllProvider();
}
