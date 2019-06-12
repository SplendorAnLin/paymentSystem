package com.yl.agent.action;

import java.text.ParseException;
import java.util.Map;

import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.agent.entity.LoginLog;
import com.yl.agent.service.OperatorLoginService;
import com.yl.boss.api.interfaces.CustomerInterface;

/**
 * 代理商欢迎页控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月28日
 * @version V1.0.0
 */
public class WelcomeAction extends Struts2ActionSupport {
	private static final long serialVersionUID = 8678824982306993968L;
	private String initSumFlag;
	private CustomerInterface customerInterface;
	private AccountQueryInterface accountQueryInterface;
	private AccountBalanceQueryResponse accountBalanceQueryResponse;
	private double balance;
	private LoginLog lastloginInfo;
	private OperatorLoginService operatorLoginService;
	Map<String, Object> ipInfo;
	
	public String getAgentNo(){
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		return auth.getAgentNo();
	}
	
	/**
	 * 上次登录信息加载
	 * 
	 * @return
	 */
	public String lastloginInfo() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		lastloginInfo = operatorLoginService.lastLogiLog(auth.getUsername());
		lastloginInfo.setLoginIp(lastloginInfo.getRemarks());
		return SUCCESS;
	}
	
	/**
	 * 今日数据
	 */
	public void initDate(){
		String agentNo = getAgentNo();
		String result = customerInterface.initDate(agentNo,initSumFlag);
		write(result);
	}
	
	/**
	 * 收支明细
	 * @throws ParseException 
	 */
	public void initRD() throws ParseException{
		String agentNo = getAgentNo();
		String result = null;
		if("YEAR".equals(initSumFlag)){
			result = customerInterface.initYear(agentNo);
		}else{
			result= customerInterface.initRD(agentNo, initSumFlag);
		}
		write(result);
	}
	
	/**
	 * 收支明细   年
	 */
	public void initYear(){
		String agentNo = getAgentNo();
		String result = customerInterface.initYear(agentNo);
		write(result);
	}
	
	/**
	 * 订单转换率
	 * @throws ParseException 
	 */
	public void initOrder() throws ParseException{
		String agentNo = getAgentNo();
		String result=customerInterface.initOrder(agentNo, initSumFlag);
		write(result);
	}
	
	/**
	 * 经营分析  日 - 周 - 月 - 所有
	 * @throws ParseException 
	 */
	public void initDayDate() throws ParseException{
		String agentNo = getAgentNo();
		String result=customerInterface.initDayDate(agentNo, initSumFlag);
		write(result);
	}
	
	public CustomerInterface getCustomerInterface() {
		return customerInterface;
	}
	
	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}

	public String getInitSumFlag() {
		return initSumFlag;
	}
	
	public void setInitSumFlag(String initSumFlag) {
		this.initSumFlag = initSumFlag;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public AccountBalanceQueryResponse getAccountBalanceQueryResponse() {
		return accountBalanceQueryResponse;
	}

	public void setAccountBalanceQueryResponse(AccountBalanceQueryResponse accountBalanceQueryResponse) {
		this.accountBalanceQueryResponse = accountBalanceQueryResponse;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public OperatorLoginService getOperatorLoginService() {
		return operatorLoginService;
	}

	public void setOperatorLoginService(OperatorLoginService operatorLoginService) {
		this.operatorLoginService = operatorLoginService;
	}

	public LoginLog getLastloginInfo() {
		return lastloginInfo;
	}

	public void setLastloginInfo(LoginLog lastloginInfo) {
		this.lastloginInfo = lastloginInfo;
	}

	public Map<String, Object> getIpInfo() {
		return ipInfo;
	}

	public void setIpInfo(Map<String, Object> ipInfo) {
		this.ipInfo = ipInfo;
	}
}