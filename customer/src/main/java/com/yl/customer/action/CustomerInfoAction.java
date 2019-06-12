package com.yl.customer.action;

import java.util.List;

import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.UserRole;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;

/**
 * 商户信息控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月4日
 * @version V1.0.0
 */
public class CustomerInfoAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 1850486561355042786L;
	private CustomerInterface customerInterface;
	private List<CustomerKey> customerKeys;
	private List<CustomerFee> customerFees;
	private CustomerSettle customerSettle;
	private Customer customer;
	private List<CustomerKey> customerKey;
	private String CusuFee;
	private ReceiveConfigInfoBean receiveConfigInfoBean;
	private ReceiveQueryFacade receiveQueryFacade;
	private ServiceConfigBean serviceConfigBean;
	private ServiceConfigFacade serviceConfigFacade;
	private AccountQueryInterface accountQueryInterface;
	private int Cycle;
	private String msg;

	/**
	 * 查询商户代付费率
	 */
	public String queryCustomerDfFee() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		CusuFee =customerInterface.getCustomerDfFee(auth.getCustomerno(), ProductType.REMIT);
		return SUCCESS;
	}

	/**
	 * 账户信息
	 * 
	 * @return
	 */
	public String queryCustomerBasicInfo() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		// 服务商信息
		customer = customerInterface.getCustomer(auth.getCustomerno());
		// 费率信息
		customerFees = customerInterface.getCustomerFeeList(auth.getCustomerno());
		// 结算卡信息
		customerSettle = customerInterface.getCustomerSettle(auth.getCustomerno());
		// 密钥信息
		customerKey = customerInterface.getCustomerKeyByCustomerNo(auth.getCustomerno());
		// 代收密钥
		receiveConfigInfoBean = receiveQueryFacade.queryReceiveConfigBy(auth.getCustomerno());
		//代付密钥
		serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
		if (serviceConfigBean!=null){
			serviceConfigBean.setPublicKey(serviceConfigBean.getPublicKey().replaceAll("\r\n", ""));
			serviceConfigBean.setPrivateKey(serviceConfigBean.getPrivateKey().replaceAll("\r\n", ""));
		}

		//入账周期
		AccountQuery accountQuery = new AccountQuery();
		accountQuery.setUserNo(auth.getCustomerno());
		accountQuery.setUserRole(UserRole.CUSTOMER);
		AccountQueryResponse accountQueryResponse = accountQueryInterface.findAccountBy(accountQuery);
		List<AccountBean> list = accountQueryResponse.getAccountBeans();
		Cycle = list.get(0).getCycle();
		return SUCCESS;
	}

	public List<CustomerKey> getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(List<CustomerKey> customerKey) {
		this.customerKey = customerKey;
	}

	/**
	 * 获取商户秘钥信息
	 * 
	 * @return
	 */
	public String queryCustomerKey() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		customerKeys = customerInterface.getCustomerKeyByCustomerNo(auth.getCustomerno());
		return "queryCustomerKey";
	}

	/**
	 * @Description:查询商户费率
	 * @return
	 * @date 2016-10-21 22:56:51
	 */
	public String queryCustomerFee() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		customerFees = customerInterface.getCustomerFeeList(auth.getCustomerno());
		return "queryCustomerFee";
	}

	/**
	 * @Title:CustomerInfoAction
	 * @Description: 查询结算卡信息
	 * @return
	 * @date 2016年10月21日
	 */
	public String queryCustomerSettle() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		customerSettle = customerInterface.getCustomerSettle(auth.getCustomerno());
		return "queryCustomerSettle";
	}
	
	/**
	 * 根据商户编号查询商户简称
	 * @return
	 */
	public String findShortNameByCustomerNo(){
		msg = customerInterface.findShortNameByCustomerNo(getHttpRequest().getParameter("customerNo"));
		return SUCCESS;
	}

	public CustomerInterface getCustomerInterface() {
		return customerInterface;
	}

	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}

	public List<CustomerKey> getCustomerKeys() {
		return customerKeys;
	}

	public void setCustomerKeys(List<CustomerKey> customerKeys) {
		this.customerKeys = customerKeys;
	}

	public List<CustomerFee> getCustomerFees() {
		return customerFees;
	}

	public void setCustomerFees(List<CustomerFee> customerFees) {
		this.customerFees = customerFees;
	}

	public CustomerSettle getCustomerSettle() {
		return customerSettle;
	}

	public void setCustomerSettle(CustomerSettle customerSettle) {
		this.customerSettle = customerSettle;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCusuFee() {
		return CusuFee;
	}

	public void setCusuFee(String cusuFee) {
		CusuFee = cusuFee;
	}

	public ReceiveConfigInfoBean getReceiveConfigInfoBean() {
		return receiveConfigInfoBean;
	}

	public void setReceiveConfigInfoBean(ReceiveConfigInfoBean receiveConfigInfoBean) {
		this.receiveConfigInfoBean = receiveConfigInfoBean;
	}

	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}

	public ServiceConfigBean getServiceConfigBean() {
		return serviceConfigBean;
	}

	public void setServiceConfigBean(ServiceConfigBean serviceConfigBean) {
		this.serviceConfigBean = serviceConfigBean;
	}

	public ServiceConfigFacade getServiceConfigFacade() {
		return serviceConfigFacade;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public int getCycle() {
		return Cycle;
	}

	public void setCycle(int cycle) {
		Cycle = cycle;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}