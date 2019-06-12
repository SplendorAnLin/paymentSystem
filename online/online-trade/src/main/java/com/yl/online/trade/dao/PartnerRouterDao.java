package com.yl.online.trade.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.PartnerRouter;

/**
 * 商户路由服务数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface PartnerRouterDao {

	/**
	 * 商户路由新增
	 * @param partnerRouter 商户路由实体
	 */
	void createPartnerRouter(PartnerRouter partnerRouter);

	/**
	 * 分页查询商户路由信息
	 * @param page 分页实体Bean
	 * @return Page
	 */
	Page queryPartnerRouterByPage(@Param("page")Page page);

	/**
	 * 根据商户路由编码查询商户路由实体
	 * @param code 商户路由编码
	 * @return PartnerRouter
	 */
	PartnerRouter queryPartnerRouterByCode(@Param("code")String code);

	/**
	 * 更新商户路由
	 * @param partnerRouter 新商户路由实体Bean
	 */
	void updatePartnerRouter(@Param("partnerRouter")PartnerRouter partnerRouter);

	/**
	 * 根据合作方角色和编码查询商户路由
	 * @param partnerRole 合作方角色
	 * @param partnerCode 合作方编码
	 * @return PartnerRouter
	 */
	PartnerRouter queryPartnerRouterBy(@Param("partnerRole")String partnerRole, @Param("partnerCode")String partnerCode);

	/**
	 * 商户路由分页查询
	 * @param page
	 * @return
	 * @author qiujian
	 * 2016年10月4日
	 * @param params 
	 */
	List<PartnerRouter> findAllPartnerRouter(@Param("page")Page page, @Param("params")Map<String, Object> params);

	/**
	 * 根据商户编号查询商户路由信息
	 * @return
	 */
	List<Map<String,Object>> queryCustRouterByCustomerNo(@Param("customerNo")String customerNo);
}
