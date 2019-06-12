package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.entity.CustomerKey;

/**
 * 商户密钥数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface CustomerKeyDao {

	
	/**
	 * 创建商户密钥
	 * @param customerKey
	 */
	public void create(CustomerKey customerKey);
	
	/**
	 * 更新商户密钥
	 * @param customerKey
	 */
	public void update(CustomerKey customerKey);
	
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
	 * 通过密钥获取商户号
	 */
	public String findByKey(String key);
}
