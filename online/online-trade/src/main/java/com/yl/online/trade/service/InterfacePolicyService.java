package com.yl.online.trade.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.online.trade.hessian.bean.InterfacePolicyBean;

/**
 * 路由模板服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface InterfacePolicyService {
	
	/**
	 * 新增交易路由模板
	 * @param interfacePolicyBean 交易路由模板
	 */
	void createInterfacePolicy(InterfacePolicyBean interfacePolicyBean);

	/**
	 * 分页查询路由模板
	 * @param page 分页实体Bean
	 * @return Page
	 */
	Page queryInterfacePolicyByPage(Page page);

	/**
	 * 根据路由模板编码查询路由信息
	 * @param code 路由模板编码
	 * @return InterfacePolicyBean
	 */
	InterfacePolicyBean queryInterfacePolicyByCode(String code);

	/**
	 * 更新交易路由模板
	 * @param interfacePolicyBean 交易路由模板
	 */
	void updateInterfacePolicy(InterfacePolicyBean interfacePolicyBean);

	/**
	 * 根据条件查询路由模板信息
	 * @param params 查询条件
	 * @return List<InterfacePolicyBean>
	 */
	List<InterfacePolicyBean> queryInterfacePolicyBy(Map<String, Object> params);
	
	/**
	 * 根据接口类型查询路由模板
	 * @param interfaceType
	 * @return
	 */
	List<InterfacePolicyBean> queryInterfacePolicyByInterfaceType(String interfaceType);

	/**
	 * 商户模板分页查询
	 * @return
	 * @author qiujian
	 * 2016年10月4日
	 */
	Object findAllInterfacePolicy(Page page, Map<String, Object> params);
}