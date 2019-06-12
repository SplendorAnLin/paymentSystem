package com.yl.online.trade.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.InterfacePolicy;

/**
 * 路由模板数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface InterfacePolicyDao {

	/**
	 * 创建路由模板
	 * @param interfacePolicy 路由模板实体Bean
	 */
	void createInterfacePolicy(InterfacePolicy interfacePolicy);

	/**
	 * 分页查询路由模板
	 * @param page 分页实体Bean
	 * @return Page
	 */
	Page queryInterfacePolicyByPage(@Param("page")Page page);

	/**
	 * 根据路由模板编码查询路由信息
	 * @param code 路由模板编码
	 * @return InterfacePolicyBean
	 */
	InterfacePolicy queryInterfacePolicyByCode(@Param("code")String code);

	/**
	 * 更新路由模板
	 * @param interfacePolicy 路由模板实体Bean
	 */
	void updateInterfacePolicy(@Param("interfacePolicy")InterfacePolicy interfacePolicy);

	/**
	 * 根据查询条件查询路由模板信息
	 * @param params 查询条件
	 * @return List<InterfacePolicy>
	 */
	List<InterfacePolicy> queryInterfacePolicyBy(@Param("params")Map<String, Object> params);
	
	/**
	 * 
	 * @param interfaceType
	 * @return
	 */
	List<InterfacePolicy> queryInterfacePolicyByInterfaceType(@Param("interfaceType")String interfaceType);

	/**
	 * 商户模板分页查询
	 * @param page
	 * @param params
	 * @return
	 * @author qiujian
	 * 2016年10月7日
	 */
	List<InterfacePolicy> findAllInterfacePolicy(@Param("page")Page page, @Param("params")Map<String, Object> params);

    /**
     * 根据路由模板编码查询路由信息
     * @param codes 路由模板编码
     * @return List<InterfacePolicy>
     */
    List<InterfacePolicy> queryInterfacePolicyByCodes(@Param("codes")List<String> codes);
 
}
