package com.yl.customer.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.opensymphony.xwork2.ActionContext;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.UserRole;
import com.yl.boss.api.bean.Agent;
import com.yl.boss.api.bean.AgentDeviceOrderBean;
import com.yl.boss.api.bean.BulletinBean;
import com.yl.boss.api.bean.DeviceTypeBean;
import com.yl.boss.api.enums.AgentType;
import com.yl.boss.api.enums.OrderStatus;
import com.yl.boss.api.enums.ProcessStatus;
import com.yl.boss.api.enums.PurchaseType;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.DeviceInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;
import com.yl.customer.entity.LoginLog;
import com.yl.customer.service.BulletinService;
import com.yl.customer.service.OperatorLoginService;
import com.yl.dpay.hessian.service.RequestFacade;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import com.yl.pay.pos.api.interfaces.PosOrderHessianService;
import com.yl.receive.hessian.ReceiveQueryFacade;

import net.sf.json.JSONObject;

/**
 * 首页信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class WelcomeAction extends Struts2ActionSupport{
	private static final long serialVersionUID = 1L;
	private CustomerInterface customerInterface;
	private DeviceInterface deviceInterface;
	private LoginLog lastloginInfo;
	private AgentInterface agentInterface;
	private OperatorLoginService operatorLoginService;
	private List<BulletinBean> bulletinBoss;
	private BulletinService bulletinService;
	private String initSumFlag;
	private RequestFacade requestFacade;
	private AccountQueryInterface accountQueryInterface;
	private ReceiveQueryFacade receiveQueryFacade;
	private OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	private ShareProfitInterface shareProfitInterface;
	private int day;
	private double balance;
	private AgentDeviceOrderBean devicePurch;
	String msg;
	private List<DeviceTypeBean> deviceTypeList=new ArrayList<DeviceTypeBean>();
	Map<String, Object> ipInfo;
	private PosOrderHessianService posOrder;
	
	public String getQrCode(){
		try {
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			msg=customerInterface.getDevice(auth.getCustomerno());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String accountBalanceNo(){
		AccountBalanceQuery accountBalanceQuery= new AccountBalanceQuery();
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		accountBalanceQuery.setUserNo(auth.getCustomerno());
		accountBalanceQuery.setUserRole(UserRole.CUSTOMER);
		AccountBalanceQueryResponse accountResponse = accountQueryInterface.findAccountBalance(accountBalanceQuery);
		balance = accountResponse.getAvailavleBalance();
		return SUCCESS;
	}
	
	/**
	 * 上次登录信息
	 * @return
	 */
	public String lastloginInfo() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		lastloginInfo=operatorLoginService.lastLogiLog(auth.getUsername());
		lastloginInfo.setLoginIp(lastloginInfo.getRemarks());
		return SUCCESS;
	}
	
	/**
	 * 商户编号加单引号
	 * @return
	 */
	public String getCustomerNoAdd(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String CustomerNo = "'"+auth.getCustomerno()+"'";
		return CustomerNo;
	}
	
	public List getCustomerList(){
		List list = new ArrayList<>();
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		list.add(auth.getCustomerno());
		return list;
	}
	
	/**
	 * 今日数据
	 */
	public void initDate(){
		String customerNo = getCustomerNoAdd();
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		List list = new ArrayList<>();
		list.add(auth.getCustomerno());
		String resMap = shareProfitInterface.counts(new Date(), new Date(), customerNo);
		List<Map<String, String>> dfMap = requestFacade.dfSuccess(new Date(), new Date(), list);// 成功结算金额
		String dfsuccess = JsonUtils.toJsonString(dfMap.get(0));
		JSONObject dfJson = JSONObject.fromObject(dfsuccess);
		int jsCount = 0;// 结算笔数
		double jsAmount = 0; // 结算金额
		if (dfJson.getInt("COUNTS") != 0) {
			jsCount = dfJson.getInt("COUNTS");
			jsAmount = dfJson.getDouble("AMOUNT");
		}
		String dfmap = String.format("{\"counts\": %d,\"sum_amount\": %.2f}", jsCount, jsAmount);
		String result = String.format("{\"resMap\": %s,\"dfMap\": %s}", resMap, dfmap);
		write(result);
	}
	
	/**
	 * 经营分析  日 - 周 - 月 - 所有
	 * @throws ParseException 
	 */
	public void initDayDate() throws ParseException{
		String customerNo = getCustomerNoAdd();
		Date startTime = DateUtils.addDays(new Date(), 0);
		Date endTime = DateUtils.addDays(new Date(), 0);
		if ("WEEK".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -7);
		} else if ("DAY".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), 0);
		} else if ("MONTH".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -30);
		} else if ("ALL".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -9999);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			endTime = sdf.parse(initSumFlag);
			startTime = sdf.parse(initSumFlag);
		}
		String jyfx = shareProfitInterface.orderAmountSumByPayType(startTime, endTime, customerNo);
		write(jyfx);
	}	
	
	/**
	 * 收支明细
	 * @throws ParseException 
	 */
	public void initRD() throws ParseException{
		String customerNo = getCustomerNoAdd();
		Date startTime = DateUtils.addDays(new Date(), 0);
		Date endTime = DateUtils.addDays(new Date(), -7);
		String resMap = null;
		if ("WEEK".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -7);
			day = 7;
			resMap = shareProfitInterface.income(startTime, endTime, customerNo, day);
		} else if ("MONTH".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -30);
			day = 30;
			resMap = shareProfitInterface.income(startTime, endTime, customerNo, day);
		} else if("YEAR".equals(initSumFlag)) {
			resMap = shareProfitInterface.initYear(customerNo);
		}
		write(resMap);
	}
	
	public void initYear(){
		String customerNo = getCustomerNoAdd();
		String resMap = shareProfitInterface.initYear(customerNo);
		write(resMap);
	}
	
	/**
	 * 订单转换率
	 * @throws ParseException 
	 */
	public void initOrder() throws ParseException{
		String customerNo = getCustomerNoAdd();
		List list = getCustomerList();
		Date startTime = DateUtils.addDays(new Date(), 0);
		Date endTime = DateUtils.addDays(new Date(), 0);
		if ("MONTH".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -30);
		} else if ("DAY".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), 0);
		} else if ("ALL".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -9999);
		} else if ("WEEK".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -7);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			endTime = sdf.parse(initSumFlag);
			startTime = sdf.parse(initSumFlag);
		}
		List<Map<String, String>> allCount = onlineTradeQueryHessianService.orderSum(startTime, endTime, "", list);// 总笔数
		String jyfxCounts = shareProfitInterface.orderSumNotRemit(startTime, endTime, customerNo); // 经营分析成功
		List<Map<String, String>> receiveMap = receiveQueryFacade.orderSumCount(startTime, endTime, customerNo);// 代收总笔数
		String pos = posOrder.orderSum(startTime, endTime, customerNo); // POS总笔数
		/*List<Map<String, String>> dfMap = requestFacade.dfSuccess(startTime, endTime, ""); // 代付成功笔数
		List<Map<String, String>> dfCount = requestFacade.dfSuccessCount(startTime, endTime, ""); // 代付总笔数
*/		// 总笔数
		double jyamount = 0;
		int jycount = 0;
		String onlineCount = JsonUtils.toJsonString(allCount.get(0));
		JSONObject jsonObject = JSONObject.fromObject(onlineCount);
		if (jsonObject.getInt("counts") != 0) {
			jyamount = jsonObject.getDouble("amount");
			jycount = jsonObject.getInt("counts");
		}
		// 经营分析成功笔数
		int allcount = 0;
		double sum_amount = 0;
		JSONObject jyfx = JSONObject.fromObject(jyfxCounts);
		if (jyfx.getInt("counts") != 0 && jyfx.getDouble("amount") != 0) {
			allcount = jyfx.getInt("counts");
			sum_amount = jyfx.getDouble("amount");
		}
		// 代收总笔数
		int receiveCount = 0;
		double receiveAmount = 0;
		String receive = JsonUtils.toJsonString(receiveMap.get(0));
		JSONObject receiveJson = JSONObject.fromObject(receive);
		if (receiveJson.getInt("COUNTS") != 0) {
			receiveCount = receiveJson.getInt("COUNTS");
			receiveAmount = receiveJson.getDouble("AMOUNT");
		}
		// POS
		int posCount = 0;
		double posAmount = 0;
		JSONObject posp = JSONObject.fromObject(pos);
		if (posp.getInt("counts") != 0 && posp.getDouble("amount") != 0) {
			posCount = posp.getInt("counts");
			posAmount = posp.getDouble("amount");
		}
		/*// 代付成功笔数
		int dfJsonCount = 0;
		double dfJsonAmount = 0;
		String df = JsonUtils.toJsonString(dfMap.get(0));
		JSONObject dfJson = JSONObject.fromObject(df);
		if (dfJson.getInt("COUNTS") != 0) {
			dfJsonCount = dfJson.getInt("COUNTS");
			dfJsonAmount = dfJson.getDouble("AMOUNT");
		}
		// 代付总笔数
		int dfCountJsonCount = 0;
		double dfCountJsonAmount = 0;
		String dfCounts = JsonUtils.toJsonString(dfCount.get(0));
		JSONObject dfc = JSONObject.fromObject(dfCounts);
		if (dfc.getInt("COUNT(AMOUNT)") != 0) {
			dfCountJsonCount = dfc.getInt("COUNT(AMOUNT)");
			dfCountJsonAmount = dfc.getDouble("AMOUNT");
		}*/
		// 合计
		try {
			double success = sum_amount; // 成功金额
			double err = posAmount + jyamount + receiveAmount - sum_amount;// 失败金额
			int successCount = allcount; // 成功笔数
			int errCount = posCount + receiveCount + jycount - allcount; // 失败笔数
			String result = String.format(
					"{\"success\": {\"count\":%d,\"amount\":%.2f}, \"error\":{\"count\": %d, \"amount\":%.2f}}",
					successCount, success, errCount, err);
			write(result);
		} catch (Exception e) {
			System.out.println("异常: " + e);
		}
	}
	
	//设备查询
	public String QueryDeviceResult(){
		try {
			devicePurch=new AgentDeviceOrderBean();
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			devicePurch.setUser(auth.getUsername());
			devicePurch.setFlowStatus(ProcessStatus.ORDER_WAIT);
			devicePurch.setPurchaseChannel(PurchaseType.CUSTOMER);
			int count=agentInterface.yOrNoOrder(devicePurch);
			if(count != 0){
				msg ="false";
			}else{
			deviceTypeList=deviceInterface.getDeviceType();
			msg ="true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg ="false";
		}
		ActionContext.getContext().getSession().put("msg", msg);
		return SUCCESS;
	}
	
	//没有水牌的用户申请水牌
	public String addOrder(){
		try { 
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			devicePurch.setUser(auth.getUsername());
			devicePurch.setQuantity(1);
			devicePurch.setOwnerId(auth.getCustomerno());
			devicePurch.setPurchaseChannel(PurchaseType.CUSTOMER);
			devicePurch.setOrderStatus(OrderStatus.WAIT_PAY);
			agentInterface.addAgentDeviceOrder(devicePurch,auth.getUsername());
			msg ="true";
		} catch (Exception e) {
			e.printStackTrace();
			 msg ="false";
		}
		return SUCCESS;
	}
	
	
	public LoginLog getLastloginInfo() {
		return lastloginInfo;
	}
	public void setLastloginInfo(LoginLog lastloginInfo) {
		this.lastloginInfo = lastloginInfo;
	}
	public OperatorLoginService getOperatorLoginService() {
		return operatorLoginService;
	}
	public void setOperatorLoginService(OperatorLoginService operatorLoginService) {
		this.operatorLoginService = operatorLoginService;
	}
	public List<BulletinBean> getBulletinBoss() {
		return bulletinBoss;
	}
	public void setBulletinBoss(List<BulletinBean> bulletinBoss) {
		this.bulletinBoss = bulletinBoss;
	}
	public BulletinService getBulletinService() {
		return bulletinService;
	}
	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getInitSumFlag() {
		return initSumFlag;
	}
	public void setInitSumFlag(String initSumFlag) {
		this.initSumFlag = initSumFlag;
	}

	public RequestFacade getRequestFacade() {
		return requestFacade;
	}
	public void setRequestFacade(RequestFacade requestFacade) {
		this.requestFacade = requestFacade;
	}
	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}
	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}
	public OnlineTradeQueryHessianService getOnlineTradeQueryHessianService() {
		return onlineTradeQueryHessianService;
	}
	public void setOnlineTradeQueryHessianService(OnlineTradeQueryHessianService onlineTradeQueryHessianService) {
		this.onlineTradeQueryHessianService = onlineTradeQueryHessianService;
	}

	public ShareProfitInterface getShareProfitInterface() {
		return shareProfitInterface;
	}
	
	public void setShareProfitInterface(ShareProfitInterface shareProfitInterface) {
		this.shareProfitInterface = shareProfitInterface;
	}

	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Map<String, Object> getIpInfo() {
		return ipInfo;
	}

	public void setIpInfo(Map<String, Object> ipInfo) {
		this.ipInfo = ipInfo;
	}
	public CustomerInterface getCustomerInterface() {
		return customerInterface;
	}
	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<DeviceTypeBean> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<DeviceTypeBean> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

	public DeviceInterface getDeviceInterface() {
		return deviceInterface;
	}

	public void setDeviceInterface(DeviceInterface deviceInterface) {
		this.deviceInterface = deviceInterface;
	}

	public AgentInterface getAgentInterface() {
		return agentInterface;
	}

	public void setAgentInterface(AgentInterface agentInterface) {
		this.agentInterface = agentInterface;
	}

	public AgentDeviceOrderBean getDevicePurch() {
		return devicePurch;
	}

	public void setDevicePurch(AgentDeviceOrderBean devicePurch) {
		this.devicePurch = devicePurch;
	}

	public PosOrderHessianService getPosOrder() {
		return posOrder;
	}

	public void setPosOrder(PosOrderHessianService posOrder) {
		this.posOrder = posOrder;
	}
}