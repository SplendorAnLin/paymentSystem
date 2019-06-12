package com.yl.online.trade.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfig;

/**
 * 商户交易配置数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface CustomerConfigDao {
	
	List<CustomerConfig> findAll(@Param("page")Page page, @Param("params")Map<String, Object> params);
	
	void create(CustomerConfig customerConfig);
	
	CustomerConfig findById(@Param("id")String id);
	
	void modifyConfig(@Param("params")Map<String, Object> params);
	
	CustomerConfig findByCustomerNo(@Param("customerNo")String customerNo,@Param("payType")String payType);
	
	/**
     * 根据商户编号和产品类型，判断当前商户是否存在该类型，存在(true)，不存在(false)
     * @param customerNo
     * @param productType
     * @return
     */
    int queryProductTypeExistsByCustNo(@Param("customerNo")String customerNo,@Param("productType")String productType);
    
    /**
     * 根据商户编号查询当前商户所有配置信息
     * @param customerNo
     * @return
     */
    List<CustomerConfig> queryAllByCustomerNo(@Param("customerNo")String customerNo);
}