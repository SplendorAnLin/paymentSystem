package com.yl.client.front.handler;

import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.boss.api.bean.BankCustomerBean;

/**
 * App商户处理
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public interface AppOneCustomerMultiCodeHandler extends AppHandler{
	
	/**
	 * 查询银行商户
	 * @param owner
	 * @return
	 */
	public Map<String,Object> findBankCustomerByPage(Integer currentPage,Integer showCount,String organization, String mcc);
	
	/**
	 * 查询MCC
	 * @return
	 */
	public Map<String,Object> findMcc();
	
	/**
	 * 查询省
	 * @return
	 */
	public Map<String,Object> findProvince();
	
	/**
	 * 查询市
	 * @param parentCode
	 * @return
	 */
	public Map<String,Object> findCityByProvince(String parentCode);
	
	/**
	 * 用户关注商家
	 */
	public Map<String,Object> followBankCustomer(String customerNo,String bankCustomerNo);

}