package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.CustomerFee;

import java.util.List;


/**
 * Title: 商户费率配置DAO
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface CustomerFeeDao {
	//创建
	public CustomerFee create(CustomerFee customerFee);
	
	//根据ID查找
	public CustomerFee findById(Long id);
	
	//根据商户号和MCC查找
	public List<CustomerFee> findByCustomerNoAndMccCode(String customerNo, String mcc);
	
	//
	public CustomerFee findByCode(String code);
	//根据商户号查找费率
	public CustomerFee findBycustNo(String custNo);
	//删除
	public void delete(CustomerFee customerFee);
	
	public CustomerFee update(CustomerFee customerFee);
	
}
