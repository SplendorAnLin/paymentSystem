package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.enums.ProductType;
import com.yl.boss.entity.CustomerFee;

/**
 * 商户费率数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface CustomerFeeDao {
	
	/**
	 * 创建商户费率
	 * @param customerFee
	 */
	public void create(CustomerFee customerFee);
	
	/**
	 * 更新商户费率
	 * @param customerFee
	 */
	public void update(CustomerFee customerFee);
	
	/**
	 * 根据商编、产品类型查询
	 * @param custNo
	 * @param productType
	 * @return
	 */
	public CustomerFee findBy(String custNo,ProductType productType);
	
	/**
	 * 根据商编查询
	 * @param custNo
	 * @return
	 */
	public List<CustomerFee> findByCustNo(String custNo);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public CustomerFee findById(long id);

	/**
	 * agent修改商户信息时候点删除按钮,物理删除该费率
	 * @param custFee
	 * @author qiujian
	 * 2016年11月7日
	 */
	public void delete(CustomerFee custFee);

}
