package com.yl.customer.interfaces.impl;

import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.customer.api.bean.CustOperator;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.customer.service.OperatorService;

/**
 * 商户操作员接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public class CustOperInterfaceImpl implements CustOperInterface{
	
	private OperatorService operatorService;

	@Override
	public void create(CustOperator operator) {
		operatorService.add((com.yl.customer.entity.Operator)JsonUtils.toJsonToObject(operator, com.yl.customer.entity.Operator.class));
	}

	@Override
	public void update(CustOperator operator) {
		operatorService.update((com.yl.customer.entity.Operator)JsonUtils.toJsonToObject(operator, com.yl.customer.entity.Operator.class));
	}

	@Override
	public CustOperator findByCustNo(String customerNo) {
		com.yl.customer.entity.Operator operator = operatorService.findByCustNo(customerNo);
		if(operator != null){
			CustOperator custOperator=new  CustOperator();
			custOperator.setCustomerNo(operator.getCustomerNo());
			custOperator.setRealname(operator.getRealname());
			custOperator.setUsername(operator.getUsername());
			return custOperator;
		}
		return null;
	}

	@Override
	public CustOperator findByUserName(String userName) {
		com.yl.customer.entity.Operator operator = operatorService.findByUsername(userName);
		if(operator != null){
			CustOperator custOperator=new  CustOperator();
			custOperator.setCustomerNo(operator.getCustomerNo());
			custOperator.setRealname(operator.getRealname());
			custOperator.setUsername(operator.getUsername());
			custOperator.setPassword(operator.getPassword());
			return custOperator;
		}
		return null;
	}

	public OperatorService getOperatorService() {
		return operatorService;
	}

	public void setOperatorService(OperatorService operatorService) {
		this.operatorService = operatorService;
	}

	@Override
	public String resetPassWord(String customer) {
		return operatorService.resetPassWord(customer);
	}

	@Override
	public String appUpdatePassword(Map<String, Object> param) {
		return operatorService.updateAppPassword(param);
	}

	@Override
	public String resetPassWordByPhone(String customer, String phone) {
		return operatorService.resetPassWordByPhone(customer,phone);
	}
}