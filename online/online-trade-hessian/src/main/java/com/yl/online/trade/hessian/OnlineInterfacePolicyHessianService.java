package com.yl.online.trade.hessian;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.online.trade.hessian.bean.InterfacePolicyBean;

/**
 * 在线交易路由服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
public interface OnlineInterfacePolicyHessianService {

	/**
	 * 创建交易路由模板
	 * @param interfacePolicyBean 路由模板实体Bean
	 */
	void createInterfacePolicy(InterfacePolicyBean interfacePolicyBean);

	/**
	 * 查询交易路由模板
	 * @param page 分页实体Bean
	 * @return
	 */
	Page queryInterfacePolicyBy(Page page);

	/**
	 * 根据交易路由编码查询路由模板信息
	 * @param code 路由模板编码
	 * @return InterfacePolicyBean
	 */
	InterfacePolicyBean queryInterfacePolicyByCode(String code);

	/**
	 * 更新交易路由模板
	 * @param interfacePolicyBean 路由模板实体Bean
	 */
	void updateInterfacePolicy(InterfacePolicyBean interfacePolicyBean);

	/**
	 * 根据条件查询路由模板信息
	 * @param params 查询条件
	 * @return List<InterfacePolicyBean>
	 */
	List<InterfacePolicyBean> queryInterfacePolicysBy(Map<String, Object> params);
	/**
	 * 根据接口类型查询路由模板
	 * @param interfaceType
	 * @return
	 */
	List<InterfacePolicyBean> queryInterfacePolicyByInterfaceType(String interfaceType);

	/**
	 * 路由模板分页查询
	 * @param page
	 * @param params
	 * @return
	 * @author qiujian
	 * 2016年10月7日
	 */
	Object findAllInterfacePolicy(Page page, Map<String, Object> params);
}
