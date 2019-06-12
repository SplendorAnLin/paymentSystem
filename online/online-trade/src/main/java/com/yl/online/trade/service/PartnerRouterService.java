package com.yl.online.trade.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.online.model.bean.InterfacePolicyProfile;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;

/**
 * 商戶路由服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface PartnerRouterService {

	/**
	 * 创建商户路由信息
	 * @param partnerRouterBean 商户路由实体Bean
	 */
	void createPartnerRouter(PartnerRouterBean partnerRouterBean);

	/**
	 * 分页查询商户路由信息
	 * @param page 分页实体Bean
	 * @return Page
	 */
	Page queryPartnerRouterByPage(Page page);

	/**
	 * 根据商户路由编码查询商户路由实体
	 * @param code 商户路由编码
	 * @return PartnerRouterBean
	 */
	PartnerRouterBean queryPartnerRouterByCode(String code);

	/**
	 * 更新商户路由信息
	 * @param partnerRouterBean 商户路由实体Bean
	 */
	void updatePartnerRouter(PartnerRouterBean partnerRouterBean);
	
	/**
	 * 根据合作方角色、编码、接口类型、卡种、接口提供方编码查询
	 * @param partnerRole 合作方角色
	 * @param partnerCode 合作方编码
	 * @param interfaceType 接口类型
	 * @param cardType 卡种
	 * @param interfaceProviderCode 接口提供方编码
	 * @return InterfacePolicyProfile
	 */
	InterfacePolicyProfile queryPartnerRouterBy(String partnerRole, String partnerCode, String interfaceType, String cardType, String interfaceProviderCode);

	/**
	 * 根据合作方角色、编码查询商户路由信息
	 * @param partnerRole 合作方角色
	 * @param partnerCode 合作方编码
	 * @return Map<String, List<InterfacePolicyProfileBean>>
	 */
	Map<String, List<InterfacePolicyProfileBean>> queryPartnerRouterByPartner(String partnerRole, String partnerCode);

	/**
	 * 商户路由分页查询
	 * @return
	 * @author qiujian
	 * 2016年10月4日
	 */
	Object findAllPartnerRouter(Page page, Map<String, Object> params);
	
	/**
	 * 根据商户编号查询商户路由信息
	 * @return
	 */
	List<Map<String,Object>> queryCustRouterByCustomerNo(String customerNo);


}
