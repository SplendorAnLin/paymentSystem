package com.yl.boss.action;

import java.util.List;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.service.CustomerFeeService;
import com.yl.boss.service.CustomerService;

/**
 * 商户费率控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class CustomerFeeAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -8165485370103499149L;
	private CustomerFeeService customerFeeService;
	private CustomerFee customerFee;
	private String msg;
	private CustomerService customerService;
	private Customer customer;
	private String mcc;
	
	public String create(){
		if (mcc!=null||StringUtils.notEmpty(mcc)) {
			customer=customerService.findByCustNo(customerFee.getCustomerNo());
			customer.setMcc(mcc);
			customerService.update(customer);
		}
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		customerFeeService.create(customerFee, auth.getRealname());
		return SUCCESS;
	}
	
	public String update(){
		if (mcc!=null&&StringUtils.notEmpty(mcc)) {
			customer=customerService.findByCustNo(customerFee.getCustomerNo());
			customer.setMcc(mcc);
			customerService.update(customer);
		}
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		customerFeeService.update(customerFee, auth.getRealname());
		return SUCCESS;
	}
	
	public String findById(){
		customerFee = customerFeeService.findById(customerFee.getId());
		return SUCCESS;
	}
	
	public String toUpdateCustomerFee() {
		customerFee = customerFeeService.findById(customerFee.getId());
		customer = customerService.findByCustNo(customerFee.getCustomerNo());
		return SUCCESS;
	}
	
	public String checkCustomerFee(){
		String customerNo = customerFee.getCustomerNo();
		String productType = customerFee.getProductType().toString();
		customerFee = customerFeeService.findBy(customerFee.getCustomerNo(), customerFee.getProductType());
		if(customerFee == null){
			msg = "TRUE";
			if(productType.equals("HOLIDAY_REMIT")){
				List<CustomerFee> listCustomerFee = customerFeeService.findByCustNo(customerNo);
				for (CustomerFee c : listCustomerFee) {
					if(c.getProductType().toString().equals("REMIT")){
						msg = "REMIT";
					}
				}
				if(msg != "REMIT"){
					msg = "EXISTS_REMIT";
				}else{
					msg = "TRUE";
				}
			}
		}else{
			msg = "FALSE";
		}
		return SUCCESS;
	}

	public CustomerFeeService getCustomerFeeService() {
		return customerFeeService;
	}

	public void setCustomerFeeService(CustomerFeeService customerFeeService) {
		this.customerFeeService = customerFeeService;
	}

	public CustomerFee getCustomerFee() {
		return customerFee;
	}

	public void setCustomerFee(CustomerFee customerFee) {
		this.customerFee = customerFee;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
}
