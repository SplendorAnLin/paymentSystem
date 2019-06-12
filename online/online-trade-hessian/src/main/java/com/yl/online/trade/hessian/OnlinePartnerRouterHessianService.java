package com.yl.online.trade.hessian;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;

/**
 * 商户路由服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
public interface OnlinePartnerRouterHessianService {

	/**
	 * 创建商户路由信息
	 * @param partnerRouterBean 商户路由信息实体
	 */
	void createPartnerRouter(PartnerRouterBean partnerRouterBean);

	/**
	 * 分页查询商户路由信息
	 * @param page 分页实体
	 * @return page
	 */
	Page queryPartnerRouterByPage(Page page);

	/**
	 * 根据商户路由编码查询商户路由实体
	 * @param code 商户路由编码
	 * @return
	 */
	PartnerRouterBean queryPartnerRouterByCode(String code);

	/**
	 * 更新商户路由实体
	 * @param partnerRouterBean 商户路由实体
	 */
	void updatePartnerRouterByPage(PartnerRouterBean partnerRouterBean);

	/**
	 * 根据合作方角色和编码查询商户路由
	 * @param partnerRole 合作方角色
	 * @param partnerCode 合作方编码
	 * @return Map<String, List<InterfacePolicyProfileBean>>
	 */
	Map<String, List<InterfacePolicyProfileBean>> queryPartnerRouterBy(String partnerRole, String partnerCode);
	

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
