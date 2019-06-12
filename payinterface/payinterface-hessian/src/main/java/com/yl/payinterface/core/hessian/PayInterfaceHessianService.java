package com.yl.payinterface.core.hessian;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.lefu.commons.utils.Page;
import com.lefu.hessian.bean.AuthBean;
import com.yl.payinterface.core.bean.*;

/**
 * 支付接口服务Hessian接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public interface PayInterfaceHessianService {

	/**
	 * 接口提供方新增
	 * @param auth 认证Bean
	 * @param interfaceProviderBean 接口提供方公用BEAN
	 */
	void interfaceProviderSave(AuthBean auth, InterfaceProviderBean interfaceProviderBean);

	/**
	 * 接口提供方修改
	 * @param auth 认证Bean
	 * @param interfaceProviderBean 接口提供方公用BEAN
	 */
	void interfaceProviderModify(AuthBean auth, InterfaceProviderBean interfaceProviderBean);

	/**
	 * 接口新增
	 * @param auth 认证Bean
	 * @param interfaceInfoBean 接口公用BEAN
	 */
	void interfaceInfoSave(AuthBean auth, InterfaceInfoBean interfaceInfoBean);

	/**
	 * 接口修改
	 * @param auth 认证Bean
	 * @param interfaceInfoBean 接口公用BEAN
	 */
	void interfaceInfoModify(AuthBean auth, InterfaceInfoBean interfaceInfoBean);

	/**
	 * 查询可用的接口信息
	 * @param codeList 接口编号List
	 */
	List<SimplifyInterfaceInfoBean> interfaceInfoQueryEnableByCode(List<String> codeList);

	/**
	 * 根据接口编号查询交易
	 * @param code 接口编号
	 * @return
	 */
	Properties interfaceInfoQueryTradeConfigByCode(String code);

	/**
	 * 根据提供方编码查询接口提供信息
	 * @param interfaceType 接口类型
	 * @param cardType 卡类型
	 * @return List<InterfaceProviderBean>
	 */
	List<InterfaceProviderBean> interfaceInfoQueryProvier(String interfaceType, String cardType);

	/**
	 * 根据接口提供方编码和卡类型查询支持的接口信息
	 * @param interfaceType 接口类型
	 * @param supportProviderCode 接口提供方编码
	 * @param cardType 卡类型
	 * @return List<InterfaceInfoBean>
	 */
	List<InterfaceInfoBean> interfaceInfoQueryBySupProviderCode(String interfaceType, String supportProviderCode, String cardType);

	/**
	 * 根据接口信息编码查询接口信息
	 * @param interfaceCode 接口信息编码
	 * @return InterfaceInfoBean
	 */
	InterfaceInfoBean interfaceInfoQueryByCode(String interfaceCode);

	/**
	 * 根据查询参数获取接口提供方信息
	 * @param queryParams 查询参数
	 * @return List<InterfaceProviderBean>
	 */
	List<InterfaceProviderBean> interfaceInfoQueryAllProvier(Map<String, Object> queryParams);

	/**
	 * 根据查询参数获取接口信息
	 * @param queryParams 查询参数
	 * @return List<InterfaceProviderBean>
	 */
	List<InterfaceInfoBean> interfaceInfoQueryAllBy(Map<String, Object> queryParams);

	/**
	 * @Description 根据接口类型查询支付接口信息
	 * @param interfaceTypes 接口类型
	 * @return List<String> 支付接口信息
	 */
	List<String> interfaceInfoCodeQueryBy(List<String> interfaceTypes);
	
	/**
	 * @Description 根据接口请求号查询接口请求信息
	 */
	String findByInterfaceRequestId(String interfaceRequestId);
	
	/**
	 * @Description 根据接口请求号查询持卡人信息
	 */
	AuthSaleBean findCardholderInfo(String interfaceRequestId);
	/**
	 * 分页查询支付接口信息
	 * @param page
	 * @param map
	 * @return
	 */
	public Page queryAll(Page page, Map<String, Object> map);
	
	/**
	 * 根据支付接口编号查询
	 * @param code
	 * @return
	 */
	public String queryByCode(String code);

	/**
	 * 查询所有支付接口信息
	 * @return
	 */
	public List<InterfaceInfoBean> queryInterfaceInfo();
	/**
	 * 查询可用的接口提供方
	 * @param providerCode
	 * @return
	 */
	public String queryByProviderCode(String providerCode);
	/**
	 * 根据编号名称查接口
	 * @param interfaceName
	 * @return
	 */
	public InterfaceInfoBean queryByName(String interfaceName);

    /**
     * 更新个人码信息
     * @param aliPayCollectionCodeBean
     */
	void updateAliPayCollection(AliPayCollectionCodeBean aliPayCollectionCodeBean);

    /**
     * 更新个人码状态
     * @param aliPayAcc
     * @param status
     */
	void updateAliPayStatus(String aliPayAcc, String status);
}