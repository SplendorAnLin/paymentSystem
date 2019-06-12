package com.yl.boss.service;

import java.util.List;

import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.entity.CustomerKey;
import com.yl.boss.entity.CustomerKeyHistory;

/**
 * 商户密钥服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface CustomerKeyService {

	/**
	 * 创建商户密钥
	 * @param customerKey
	 * @param oper
	 */
	public void create(CustomerKey customerKey, String oper);
	
	/**
	 * 更新商户密钥
	 * @param customerKey
	 * @param oper
	 */
	public void update(CustomerKey customerKey, String oper);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public CustomerKey findById(long id);
	
	/**
	 * 根据商编查询
	 * @param custNo
	 * @return
	 */
	public List<CustomerKey> findByCustNo(String custNo);
	
	/**
	 * 根据商编、产品类型查询
	 * @param custNo
	 * @param productType
	 * @return
	 */
	public CustomerKey findBy(String custNo, ProductType productType);
	
	/**
	 * 根据商编、密钥类型查询
	 * @param custNo
	 * @param keyType
	 * @return
	 */
	public CustomerKey findBy(String custNo, KeyType keyType);
	
	/**
	 * 根据商编查询历史
	 * @param custNo
	 * @return List<CustomerKeyHistory>
	 */
	public List<CustomerKeyHistory> findHistByCustNo(String custNo);
	
	/**
	 * 通过密钥获取商户号
	 */
	public String findByKey(String key);
	
}
