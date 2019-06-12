package com.yl.boss.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.boss.Constant;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;
import com.yl.boss.api.utils.Page;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Bulletin;
import com.yl.boss.entity.LoginLog;
import com.yl.boss.service.BulletinService;
import com.yl.boss.service.OperatorLoginService;
import com.yl.boss.service.ShareProfitService;
import com.yl.dpay.hessian.service.RequestFacade;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import com.yl.pay.pos.api.interfaces.PosOrderHessianService;

import net.sf.json.JSONObject;

/**
 * 欢迎页
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class WelcomeAction extends Struts2ActionSupport {
	private static final long serialVersionUID = -5018497027344629043L;
	private BulletinService bulletinService;
	private List<Bulletin> bulletinBoss;
	Map<String, Object> ipInfo;
	private OperatorLoginService operatorLoginService;
	private LoginLog lastloginInfo;
	String countAccBalance = "";
	private AccountQueryInterface accountQueryInterface;
	private Page page;
	private OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	private String initSumFlag;
	private RequestFacade requestFacade;
	private ReceiveQueryFacade receiveQueryFacade;
	private ShareProfitService shareProfitService;
	private PosOrderHessianService posOrder;
	private int day = 1;
	
	
	/**
	 * 首页信息
	 * 
	 * @return
	 */
	public String bulletinList() {
		if (page == null) {
			page = new Page(0);
		}
		HttpServletRequest request = this.getHttpRequest();
		int currentPage = request.getParameter("currentPage") == null ? 1
				: Integer.parseInt(request.getParameter("currentPage"));
		if (currentPage > 1) {
			page.setCurrentPage(currentPage);
		}
		page.setTotalResult(bulletinService.findBulletinCount(BulletinType.TRUE, BulletinSysType.BOSS, new Date()));
		bulletinBoss = bulletinService.findBulletinBy(BulletinType.TRUE, BulletinSysType.BOSS, new Date(), page);
		return SUCCESS;
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
	 * 账户分类统计
	 * 
	 * @return
	 */
	public String countAccBalance() {
		List<Map<String, Object>> accInfo = accountQueryInterface.findAccTypeBalance();
		Map<String, Object> agent=new HashMap<>();
		Map<String, Object> customer=new HashMap<>();
		for (Map<String, Object> map : accInfo) {
			if (map.get("USER_ROLE").equals("AGENT")) {
				agent.putAll(map);
			}
			if (map.get("USER_ROLE").equals("CUSTOMER")) {
				customer.putAll(map);
			}
		}
		JSONObject accBalance = new JSONObject();
		accBalance.put("AGENT", agent);
		accBalance.put("CUSTOMER", customer);
		countAccBalance=JsonUtils.toJsonString(accBalance);
		return SUCCESS;
	}

	/**
	 * 今日数据
	 * 
	 * @throws ParseException
	 */
	public void initDate() throws ParseException {
		String resMap = shareProfitService.counts(new Date(), new Date(), "");
		List list = new ArrayList<>();
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
	 * 经营分析 日 - 周 - 月 - 所有
	 * 
	 * @throws ParseException
	 */
	public void initDayDate() throws ParseException {
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
		String jyfx = shareProfitService.orderAmountSumByPayType(startTime, endTime, "");
		write(jyfx);
	}

	/**
	 * 收支明细
	 * 
	 * @throws ParseException
	 */
	public void initRD() throws ParseException {
		Date startTime = DateUtils.addDays(new Date(), 0);
		Date endTime = DateUtils.addDays(new Date(), -7);
		String resMap = null;
		if ("WEEK".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -7);
			day = 7;
			resMap = shareProfitService.income(startTime, endTime, "", day);
		} else if ("MONTH".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -30);
			day = 30;
			resMap = shareProfitService.income(startTime, endTime, "", day);
		} else if("YEAR".equals(initSumFlag)) {
			resMap = shareProfitService.incomeYear("");
		}
		write(resMap);
	}
	
	/**
	 * 收支明细	年数据
	 */
	public void initYear(){
		String result = shareProfitService.incomeYear("");
		write(result);
	}

	/**
	 * 订单转换率
	 * 
	 * @throws ParseException
	 */
	
	public void initOrder() throws ParseException {
		List list = new ArrayList<>();
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
		String jyfxCounts = shareProfitService.orderSumNotRemit(startTime, endTime, ""); // 经营分析成功
		List<Map<String, String>> receiveMap = receiveQueryFacade.orderSumCount(startTime, endTime, "");// 代收总笔数
		String pos = posOrder.orderSum(startTime, endTime, ""); // POS总笔数
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

	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}

	public String getCountAccBalance() {
		return countAccBalance;
	}

	public void setCountAccBalance(String countAccBalance) {
		this.countAccBalance = countAccBalance;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Bulletin> getBulletinBoss() {
		return bulletinBoss;
	}

	public void setBulletinBoss(List<Bulletin> bulletinBoss) {
		this.bulletinBoss = bulletinBoss;
	}

	public BulletinService getBulletinService() {
		return bulletinService;
	}

	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
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

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public Map<String, Object> getIpInfo() {
		return ipInfo;
	}

	public void setIpInfo(Map<String, Object> ipInfo) {
		this.ipInfo = ipInfo;
	}

	public void setOnlineTradeQueryHessianService(OnlineTradeQueryHessianService onlineTradeQueryHessianService) {
		this.onlineTradeQueryHessianService = onlineTradeQueryHessianService;
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

	public ShareProfitService getShareProfitService() {
		return shareProfitService;
	}

	public void setShareProfitService(ShareProfitService shareProfitService) {
		this.shareProfitService = shareProfitService;
	}

	public PosOrderHessianService getPosOrder() {
		return posOrder;
	}

	public void setPosOrder(PosOrderHessianService posOrder) {
		this.posOrder = posOrder;
	}

	public OnlineTradeQueryHessianService getOnlineTradeQueryHessianService() {
		return onlineTradeQueryHessianService;
	}
}