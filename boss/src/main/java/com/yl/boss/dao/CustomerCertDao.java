package com.yl.boss.dao;

import com.yl.boss.entity.CustomerCert;

/**
 * 商户证件数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface CustomerCertDao {
	
	/**
	 * 创建商户证件信息
	 * @param customerCert
	 */
	public void create(CustomerCert customerCert);
	/**
	 * 查询法人姓名
	 * @param customerNo
	 * @return
	 */
	public String findLegalPersonByNo(String customerNo);
	/**
	 * 更新商户证件信息
	 * @param customerCert
	 */
	public void update(CustomerCert customerCert);
	
	/**
	 * 根据商户编号查询
	 * @param CustomerNo
	 * @return
	 */
	public CustomerCert findByCustNo(String customerNo);

}
