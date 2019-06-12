package com.yl.pay.pos.service;

import com.yl.pay.pos.bean.CustomerFeeBean;
import com.yl.pay.pos.bean.CustomerFeeReturnBean;
import com.yl.pay.pos.entity.CustomerFee;


/**
 * Title: 商户手续费服务
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface ICustomerFeeService {
	
	//计算商户手续费
	public CustomerFeeReturnBean getCustomerFee(CustomerFeeBean param);
	/**
	 * 新增商户费率
	 * @param customerFee
	 * @return
	 */
	public boolean addCustomerFee(CustomerFee customerFee);
	/**
	 * 更新商户费率
	 * @param customerFee
	 * @return
	 */
	public boolean updateCustomerFee(CustomerFee customerFee);
	/**
	 * 根据商户号查询收单费率
	 * @param custNo
	 * @return
	 */
	public CustomerFee findByCustNo(String custNo);
}
