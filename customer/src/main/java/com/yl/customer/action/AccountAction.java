package com.yl.customer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.AccountRecordedDetailBean;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.UserRole;
import com.yl.customer.Constant;
import com.yl.customer.bean.AccountHistoryQueryBean;
import com.yl.customer.entity.Authorization;
import com.yl.customer.utils.DateUtils;
import com.yl.customer.utils.ToolUtils;

/**
 * 账户控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月22日
 * @version V1.0.0
 */
public class AccountAction extends Struts2ActionSupport {
	private static final long serialVersionUID = 5633409001921833258L;

	private AccountQueryInterface accountQueryInterface;
	private AccountBean account;
	private Page page;
	private String accountInfo;
	private Double balance;
	private AccountHistoryQueryBean accountHistoryQueryBean;
	private List<AccountRecordedDetailBean> accountRecordedDetails;
	private String resultMsg;

	/**
	 * @Title:AccountAction
	 * @Description:查询账户信息
	 * @return
	 * @date 2016年10月22日
	 */
	public String queryAccount() {
		AccountQuery accountQuery = new AccountQuery();
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		accountQuery.setUserNo(auth.getCustomerno());
		AccountQueryResponse accountResponse = accountQueryInterface.findAccountBy(accountQuery);
		account = accountResponse.getAccountBeans().get(0);
		AccountBalanceQuery accountBalanceQuery= new AccountBalanceQuery();
		accountBalanceQuery.setUserNo(auth.getCustomerno());
		accountBalanceQuery.setUserRole(UserRole.CUSTOMER);
		AccountBalanceQueryResponse accountResponseYe = accountQueryInterface.findAccountBalance(accountBalanceQuery);
		balance = accountResponseYe.getAvailavleBalance();
		Map<String,Object> accountMap = new HashMap<String, Object>();
		accountMap.put("amount", balance);
		accountMap.put("userNo", account.getUserNo());
		accountMap.put("status", account.getStatus().toString());
		accountMap.put("balance", String.valueOf(account.getBalance()));
		accountMap.put("transitBalance", String.valueOf(account.getTransitBalance()));
		accountMap.put("freezeBalance", String.valueOf(account.getFreezeBalance()));
		accountMap.put("openDate", account.getOpenDate());
		accountInfo = JsonUtils.toJsonString(accountMap);
		return "queryAccount";
	}
	
	public String getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(String accountInfo) {
		this.accountInfo = accountInfo;
	}

	/**
	 * 余额查询
	 */
	public String balanceInfo(){
		AccountBalanceQuery accountBalanceQuery= new AccountBalanceQuery();
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		accountBalanceQuery.setUserNo(auth.getCustomerno());
		accountBalanceQuery.setUserRole(UserRole.CUSTOMER);
		AccountBalanceQueryResponse accountResponse = accountQueryInterface.findAccountBalance(accountBalanceQuery);
		if(accountResponse.getResultMsg() != "" || accountResponse.getResultMsg() != null){
			resultMsg = accountResponse.getResultMsg();
		}
		balance = accountResponse.getAvailavleBalance();
		return SUCCESS;
	}


	/**
	 * @Title:AccountAction
	 * @Description:查询账户详细信息
	 * @return
	 * @date 2016年10月22日
	 */
	public String queryAccountPage() {
		if (page == null) {
			page = new Page<>();
		}
		HttpServletRequest request = this.getHttpRequest();
		int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		if (currentPage > 1) {
			page.setCurrentPage(currentPage);
		}
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		if (accountHistoryQueryBean == null) {
			accountHistoryQueryBean = new AccountHistoryQueryBean();
		}
		if (accountHistoryQueryBean.getTransStartTime() != null) {
			accountHistoryQueryBean.setTransStartTime(DateUtils.getMinTime(accountHistoryQueryBean.getTransStartTime()));
		}
		if (accountHistoryQueryBean.getTransEndTime() != null) {
			accountHistoryQueryBean.setTransEndTime(DateUtils.getMaxTime(accountHistoryQueryBean.getTransEndTime()));
		}
		if(accountHistoryQueryBean.getTransFlow() != null){
			accountHistoryQueryBean.setTransFlow(accountHistoryQueryBean.getTransFlow());
		}
		accountHistoryQueryBean.setUserNo(auth.getCustomerno());
		Map<String, Object> queryParams = ToolUtils.ObjectToMap(accountHistoryQueryBean);
		AccountHistoryQueryResponse accountHistoryQueryResponse = accountQueryInterface.findAccountHistory(queryParams, page);
		page = accountHistoryQueryResponse.getPage();
		accountRecordedDetails = accountHistoryQueryResponse.getAccountRecordedDetailBeans();
		return "queryAccountPage";
	}
	
	/**
	 * @Title:AccountAction
	 * @Description:导出
	 * @return
	 * @date 2016年10月22日 下午10:03:00
	 */
	public String findAccountHistoryExportBy() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		if (accountHistoryQueryBean == null) {
			accountHistoryQueryBean = new AccountHistoryQueryBean();
		}
		if (accountHistoryQueryBean.getTransStartTime() != null) {
			accountHistoryQueryBean.setTransStartTime(DateUtils.getMinTime(accountHistoryQueryBean.getTransStartTime()));
		}
		if (accountHistoryQueryBean.getTransEndTime() != null) {
			accountHistoryQueryBean.setTransEndTime(DateUtils.getMaxTime(accountHistoryQueryBean.getTransEndTime()));
		}
		accountHistoryQueryBean.setUserNo(auth.getCustomerno());
		Map<String, Object> queryParams = ToolUtils.ObjectToMap(accountHistoryQueryBean);
		AccountHistoryQueryResponse accountHistoryQueryResponse = accountQueryInterface.findAccountHistoryExportBy(queryParams);
		accountRecordedDetails = accountHistoryQueryResponse.getAccountRecordedDetailBeans();
		return "findAccountHistoryExportBy";
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public AccountBean getAccount() {
		return account;
	}

	public void setAccount(AccountBean account) {
		this.account = account;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public AccountHistoryQueryBean getAccountHistoryQueryBean() {
		return accountHistoryQueryBean;
	}

	public void setAccountHistoryQueryBean(AccountHistoryQueryBean accountHistoryQueryBean) {
		this.accountHistoryQueryBean = accountHistoryQueryBean;
	}

	public List<AccountRecordedDetailBean> getAccountRecordedDetails() {
		return accountRecordedDetails;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setAccountRecordedDetails(List<AccountRecordedDetailBean> accountRecordedDetails) {
		this.accountRecordedDetails = accountRecordedDetails;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
