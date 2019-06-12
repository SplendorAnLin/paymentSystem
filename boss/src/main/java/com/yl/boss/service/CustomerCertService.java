package com.yl.boss.service;

import java.util.List;

import com.yl.boss.entity.CustomerCert;
import com.yl.boss.entity.CustomerCertHistory;

/**
 * 商户证件信息服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface CustomerCertService {
	
	/**
	 * 更新商户证件信息
	 * @param customerCert
	 * @param oper
	 */
	public void update(CustomerCert customerCert, String oper);
	
	/**
	 * 根据商户编号查询
	 * @param CustomerNo
	 * @return CustomerCert
	 */
	public CustomerCert findByCustNo(String customerNo);
	
	/**
	 * 根据商编查询历史
	 * @param customerNo
	 * @return List<CustomerCertHistorty>
	 */
	public List<CustomerCertHistory> findHistByCustNo(String customerNo);
	/**
	 * 查询法人姓名
	 * @param customerNo
	 * @return
	 */
	public String findLegalPersonByNo(String customerNo);

}
