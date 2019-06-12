package com.yl.online.trade.hessian;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfig;

/**
 * 商户交易配置操作接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
public interface OnlineCustomerConfigHessianService {
	
	/**
	 * 查询所有商户配置信息
	 * @param page
	 * @param params
	 * @return
	 */
	Object findAll(Page page, Map<String, Object> params);
	
	/**
	 * 新增商户交易配置信息
	 * @param customerConfig
	 */
	void create(Map<String, Object> customerConfig,String reason,String operator);
	
	/**
	 * 根据ID查询商户交易配置信息
	 * @param id
	 * @return
	 */
	CustomerConfig findById(String id);
	
	/**
	 * 修改商户交易配置信息
	 * @param customerConfig
	 */
	void modifyConfig(CustomerConfig customerConfig,String reason,String operator);
	
	/**
     * 根据商户编号和产品类型，判断当前商户是否存在该类型，存在(true)，不存在(false)
     * @param customerNo
     * @param productType
     * @return
     */
    boolean queryProductTypeExistsByCustNo(String customerNo,String productType);
    
    /**
     * 根据商户编号查询当前商户所有配置信息
     * @param customerNo
     * @return
     */
    List<CustomerConfig> queryAllByCustomerNo(String customerNo);
	
}