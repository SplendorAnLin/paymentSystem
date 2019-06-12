package com.yl.customer.api.interfaces;

import java.util.Map;

import com.yl.customer.api.bean.CustOperator;

/**
 * 商户操作员接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public interface CustOperInterface {
	
	/**
	 * 重置商户密码
	 */
	public String resetPassWord(String customer);
	public String resetPassWordByPhone(String customer,String phone);
	/**
	 * 创建操作员
	 * @param operator
	 */
	public void create(CustOperator operator);
	
	/**
	 * 修改操作员
	 * @param operator
	 */
	public void update(CustOperator operator);
	
	/**
	 * 根据商编查询
	 * @param customerNo
	 * @return
	 */
	public CustOperator findByCustNo(String customerNo);
	
	/**
	 * 根据登录账号查询
	 * @param userName
	 * @return
	 */
	public CustOperator findByUserName(String userName);
	
	/**
	 * 商户密码修改
	 */
	public String appUpdatePassword(Map<String, Object> param);
}