package com.yl.boss.action;

import java.util.List;

import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.CustomerSettle;
import com.yl.boss.entity.CustomerSettleHistory;
import com.yl.boss.service.CustomerSettleService;

/**
 * 商户结算卡控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class CustomerSettleAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -6498967085589486060L;
	private CustomerSettleService customerSettleService;
	private CustomerSettle customerSettle;
	private List<CustomerSettleHistory> customerSettleHistoryList;
	
	public String update(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		customerSettleService.update(customerSettle, auth.getRealname());
		return SUCCESS;
	}
	
	public String findByCustNo(){
		customerSettle = customerSettleService.findByCustNo(customerSettle.getCustomerNo());
		return SUCCESS;
	}

	public CustomerSettleService getCustomerSettleService() {
		return customerSettleService;
	}

	public void setCustomerSettleService(CustomerSettleService customerSettleService) {
		this.customerSettleService = customerSettleService;
	}

	public CustomerSettle getCustomerSettle() {
		return customerSettle;
	}

	public void setCustomerSettle(CustomerSettle customerSettle) {
		this.customerSettle = customerSettle;
	}
	public List<CustomerSettleHistory> getCustomerSettleHistoryList() {
		return customerSettleHistoryList;
	}
	public void setCustomerSettleHistoryList(List<CustomerSettleHistory> customerSettleHistoryList) {
		this.customerSettleHistoryList = customerSettleHistoryList;
	}
	
}
