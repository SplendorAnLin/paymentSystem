package com.yl.payinterface.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.enums.InterfaceTypeDubbo;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceProvider;

/**
 * 接口信息数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
public interface InterfaceInfoDao {
	/**
	 * 支付接口新增
	 * @param interfaceInfo
	 */
	void create(InterfaceInfo interfaceInfo);

	/**
	 * 根据支付接口编号查询接口信息
	 * @param code
	 * @return
	 */
	InterfaceInfo findByCode(@Param("code")String code);
	InterfaceInfo findByCodeToShow(@Param("code")String code);

	/**
	 * 支付接口信息修改
	 * @param interfaceInfo
	 * @param newVersion
	 */
	void update(@Param("interfaceInfo")InterfaceInfo interfaceInfo, @Param("newVersion")Long newVersion);

	/**
	 * 查询所有支付接口信息
	 * @return
	 */
	List<InterfaceInfo> findInterfaceInfo();

	/**
	 * 查询可用的支付接口
	 * @param codeList
	 * @return
	 */
	List<InterfaceInfo> findAllEnable(String codes);

	/**
	 * 根据接口提供方编码查询接口提供方信息
	 * @param interfaceType 提供类型
	 * @param cardType 卡类型
	 * @return List<InterfaceProvider>
	 */
	List<InterfaceProvider> findInterfaceProviderBy(@Param("interfaceType")String interfaceType, @Param("cardType")String cardType);

	/**
	 * 根据接口提供方编码和卡类型查询支付接口信息
	 * @param interfaceType 接口类型
	 * @param providerCode 接口提供方编码
	 * @param cardType 卡类型
	 * @return <InterfaceInfo>
	 */
	List<InterfaceInfo> findInterfaceInfoBySupProviderCode(@Param("interfaceType")String interfaceType, @Param("providerCode")String providerCode, @Param("cardType")String cardType);

	/**
	 * 根据查询参数获取接口提供方信息
	 * @param queryParams 查询参数
	 * @return List<InterfaceProvider>
	 */
	List<InterfaceProvider> findInterfaceProviderBy(Map<String, Object> queryParams);

	/**
	 * @Description 根据接口类型查询所有可用接口
	 * @param interfaceTypes 接口类型
	 * @return List<InterfaceInfo> 可用接口信息
	 * @see 需要参考的类或方法
	 */
	List<InterfaceInfo> queryAllEnableByInterfaceType(@Param("interfaceTypes")String interfaceTypes);

	/**
	 * 
	 * @param type
	 * @return
	 */
	List<InterfaceInfo> findValidateInterfaceInfo(InterfaceTypeDubbo type);
	
	List<InterfaceInfo> findAll(@Param("page")Page page, @Param("params")Map<String, Object> params);

	InterfaceInfo findByName(String name);

}
