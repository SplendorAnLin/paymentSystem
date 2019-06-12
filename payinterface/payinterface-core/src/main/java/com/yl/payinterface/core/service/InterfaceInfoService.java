package com.yl.payinterface.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceProvider;

/**
 * 接口信息服务
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public interface InterfaceInfoService {

	/**
	 * 接口信息新增
	 * @param interfaceInfo
	 * @param source
	 * @param operator
	 */
	public void save(InterfaceInfo interfaceInfo, String source, String operator);

	/**
	 * 接口信息修改
	 * @param interfaceInfo
	 * @param source
	 * @param operator
	 */
	public void modify(InterfaceInfo interfaceInfo, String source, String operator);

	/**
	 * 查询所有支付接口信息
	 * @return
	 */
	public List<InterfaceInfo> queryInterfaceInfo();

	/**
	 * 查询可用的支付接口
	 * @param codeList
	 * @return
	 */
	public List<InterfaceInfo> queryEnableByCode(List<String> codeList);

	/**
	 * 根据支付接口编号查询
	 * @param code
	 * @return
	 */
	public InterfaceInfo queryByCode(String code);
	public InterfaceInfo queryByCodeToShow(String code);
	

	/**
	 * 根据接口提供方编码查询接口提供方信息
	 * @param interfaceType 接口类型
	 * @param cardType 卡类型
	 * @return List<InterfaceProvider>
	 */
	public List<InterfaceProvider> queryInterfaceProviderInfoBy(String interfaceType, String cardType);

	/**
	 * 根据接口提供方编码和卡类型查询支付接口编码
	 * @param interfaceType 接口类型
	 * @param supportProviderCode 接口提供方编码
	 * @param cardType 卡类型
	 * @return List<InterfaceInfo>
	 */
	public List<InterfaceInfo> queryInterfaceInfoBySupProviderCode(String interfaceType, String supportProviderCode, String cardType);

	/**
	 * 根据查询参数查询接口提供方信息
	 * @param queryParams 查询参数
	 * @return List<InterfaceProvider>
	 */
	public List<InterfaceProvider> queryAllInterfaceProviderInfoBy(Map<String, Object> queryParams);

	/**
	 * @Description 根据接口类型查询所有可用接口
	 * @param interfaceTypes 接口类型
	 * @return
	 * @see 需要参考的类或方法
	 */
	List<InterfaceInfo> queryAllEnableByInterfaceType(List<String> interfaceTypes);
	
	Page findAll(Page page, Map<String, Object> map);

	InterfaceInfo queryByName(String name);
}
