package com.yl.boss.service;

import java.util.List;

import com.yl.boss.enums.ProductType;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.entity.CustomerFeeHistory;

/**
 * 商户费率服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface CustomerFeeService {

	/**
	 * 创建商户费率
	 * @param customerFee
	 * @param oper
	 */
	public void create(CustomerFee customerFee, String oper);
	
	/**
	 * 更新商户费率
	 * @param customerFee
	 * @param oper
	 */
	public void update(CustomerFee customerFee, String oper);
	
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
	 * 根据商编查询历史
	 * @param custNo
	 * @return List<CustomerFeeHistorty>
	 */
	public List<CustomerFeeHistory> findHistByCustNo(String custNo);

	/**
	 * 
	 * @param customerFee
	 * @param oper
	 * @author qiujian
	 * 2016年11月7日
	 */
	public void delete(CustomerFee customerFee, String oper);
	
}
