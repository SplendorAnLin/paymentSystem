package com.yl.online.trade.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfig;

/**
 * 商户交易配置处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface CustomerConfigService {
	
	Object findAll(Page page, Map<String, Object> params);
	
	void create(CustomerConfig customerConfig);
	
	CustomerConfig findById(String id);
	
	void modifyConfig(CustomerConfig customerConfig);
	
	CustomerConfig findByCustomerNo(String customerNo,String payType);
	
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