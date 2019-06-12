package com.yl.boss.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.components.If;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.pay.common.util.Md5Util;
import com.pay.common.util.StringUtil;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.request.AccountModify;
import com.yl.account.api.bean.request.AccountOpen;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountModifyResponse;
import com.yl.account.api.bean.response.AccountOpenResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.AccountStatus;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.UserRole;
import com.yl.agent.api.enums.Status;
import com.yl.boss.Constant;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.dao.CustomerCertDao;
import com.yl.boss.dao.CustomerCertHistoryDao;
import com.yl.boss.dao.CustomerDao;
import com.yl.boss.dao.CustomerFeeDao;
import com.yl.boss.dao.CustomerFeeHistoryDao;
import com.yl.boss.dao.CustomerHistoryDao;
import com.yl.boss.dao.CustomerSettleDao;
import com.yl.boss.dao.CustomerSettleHistoryDao;
import com.yl.boss.dao.SyncInfoDao;
import com.yl.boss.entity.AgentFee;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.CustomerCert;
import com.yl.boss.entity.CustomerCertHistory;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.entity.CustomerFeeHistory;
import com.yl.boss.entity.CustomerHistory;
import com.yl.boss.entity.CustomerKey;
import com.yl.boss.entity.CustomerSettle;
import com.yl.boss.entity.CustomerSettleHistory;
import com.yl.boss.entity.Device;
import com.yl.boss.entity.License;
import com.yl.boss.entity.Shop;
import com.yl.boss.enums.CustomerSource;
import com.yl.boss.enums.CustomerStatus;
import com.yl.boss.enums.CustomerType;
import com.yl.boss.enums.FeeType;
import com.yl.boss.enums.ProductType;
import com.yl.boss.enums.SyncType;
import com.yl.boss.enums.YesNo;
import com.yl.boss.service.AgentFeeService;
import com.yl.boss.service.CustomerCertService;
import com.yl.boss.service.CustomerFeeService;
import com.yl.boss.service.CustomerKeyService;
import com.yl.boss.service.CustomerService;
import com.yl.boss.service.CustomerSettleService;
import com.yl.boss.service.DeviceService;
import com.yl.boss.service.OrganizationService;
import com.yl.boss.service.ShareProfitService;
import com.yl.boss.service.ShopService;
import com.yl.boss.utils.CodeBuilder;
import com.yl.boss.utils.GengeratePassword;
import com.yl.customer.api.bean.CustOperator;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.enums.OwnerRole;
import com.yl.dpay.hessian.service.RequestFacade;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;
import com.yl.pay.pos.api.interfaces.PosOrderHessianService;
import com.yl.realAuth.hessian.AuthConfigHessianService;
import com.yl.realAuth.hessian.bean.AuthConfigBean;
import com.yl.realAuth.hessian.enums.AuthBusiType;
import com.yl.realAuth.hessian.enums.AuthConfigStatus;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.receive.hessian.enums.ReceiveConfigStatus;
import com.yl.sms.SmsUtils;

import net.sf.json.JSONObject;

/**
 * 商户业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	/** 快速入网默认开通固定费率产品 */
	private static final String[] QUICK_OPEN_FIXED_PRODUCTS = { "REMIT", "HOLIDAY_REMIT", "BINDCARD_AUTH" };
	/** 快速入网默认开通比例费率产品 */
	private static final String[] QUICK_OPEN_RATIO_PRODUCTS = { "B2C", "B2B", "AUTHPAY", "WXJSAPI", "WXNATIVE", "ALIPAY", "ALIPAYMICROPAY", "POS", "MPOS", "QUICKPAY", "WXMICROPAY" };
	/** 默认固定费率 */
	private static final Double DEFAULT_FIXED_FEE = 2D;
	/** 默认固定费率 */
	private static final Double DEFAULT_BINDCARD_AUTH_FEE = 0.001D;
	/** 默认比例 费率 */
	private static final Double DEFAULT_RATIO_FEE = 0.006D;
	/** 默认比例 费率 */
	private static final Double DEFAULT_RATIO_MAX_FEE = 9999D;
	/** 默认比例 费率 */
	private static final Double DEFAULT_RATIO_MIN_FEE = 0.1D;
	/** 代付默认开始时间 */
	private static final String DAPY_START_TIME = "08:30";
	/** 代付默认结束时间 */
	private static final String DAPY_END_TIME = "19:00";
	/** 商户默认MCC */
	private static final String DEFAULT_MCC = "5012";
	/** 更新商户基本信息操作员 */
	private static final String UPDATE_BASE_OPER = "UPDATEBASEINFO";
	
	private CustomerDao customerDao;
	private CustomerHistoryDao customerHistoryDao;
	private CustomerCertDao customerCertDao;
	private CustomerCertHistoryDao customerCertHistoryDao;
	private CustomerFeeDao customerFeeDao;
	private CustomerFeeHistoryDao customerFeeHistoryDao;
	private CustomerSettleDao customerSettleDao;
	private CustomerSettleHistoryDao customerSettleHistoryDao;
	private CustOperInterface custOperInterface;
	private AccountInterface accountInterface;
	private ReceiveQueryFacade receiveQueryFacade;
	private AccountQueryInterface accountQueryInterface;							  
	private CustomerKeyService customerKeyService;
	private CustomerCertService customerCertService;
	private OnlinePartnerRouterHessianService onlinePartnerRouterHessianService;
	private ServiceConfigFacade serviceConfigFacade;
	private CustomerFeeService customerFeeService;
	private CustomerSettleService customerSettleService;
	private RequestFacade requestFacade;
	private OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	private ShareProfitService shareProfitService;
	private int day;
	private DeviceService deviceService;
	private ShopService shopService;
	private OrganizationService organizationService;
	private SyncInfoDao syncInfoDao;
	private AgentFeeService agentFeeService;
	private AuthConfigHessianService authConfigHessianService;
	private PosOrderHessianService posOrder;
	
	/**
	 * 获取商户编号
	 * @param owner
	 * @return
	 */
	public String getCustomerNo(String owner){
		StringBuffer sb = new StringBuffer();
		List<Customer> listCustomer = customerDao.findByAgentNo(owner);
		if(listCustomer != null && listCustomer.size() > 0){
			for (int i = 0; i < listCustomer.size(); i++) {
				sb.append("'"+listCustomer.get(i).getCustomerNo()+"'");
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		} else {
			return "0";
		}
		return sb.toString();
	}
	
	/**
	 * 获取商户编号
	 * @param owner
	 * @return
	 */
	public List getCustomerNoList(String owner){
		List list = new ArrayList<>();
		List<Customer> listCustomer = customerDao.findByAgentNo(owner);
		if(listCustomer != null && listCustomer.size() > 0){
			for (int i = 0; i < listCustomer.size(); i++) {
				list.add(listCustomer.get(i).getCustomerNo());
			}
		} else {
			list.add("0");
			return list;
		}
		return list;
	}

	/**
	 * 收支明细
	 * @throws ParseException 
	 */
	@Override
	public String initRD(String owner,String initSumFlag) throws ParseException {
		String customerNo = getCustomerNo(owner);
		Date startTime = DateUtils.addDays(new Date(), 0);
		Date endTime = DateUtils.addDays(new Date(), -7);
		if ("WEEK".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -7);
			day = 7;
		} else if ("MONTH".equals(initSumFlag)) {
			endTime = DateUtils.addDays(new Date(), -30);
			day = 30;
		}
		String resMap = shareProfitService.income(startTime, endTime, customerNo, day);
		return resMap;
	}
	
	@Override
	public String initYear(String owner) {
		String customerNo = getCustomerNo(owner);
		String resMap = shareProfitService.incomeYear(customerNo);
		return resMap;
	}

	/**
	 * 订单转换率
	 * @throws ParseException 
	 */
	@Override
	public String initOrder(String owner,String initSumFlag) throws ParseException {
		String customerNo = getCustomerNo(owner);
		List list = getCustomerNoList(owner);
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
		List<Map<String, String>> allCount = null;
		List<Map<String, String>> receiveMap = null;
		if(list != null && !list.get(0).equals("0")){
			allCount = onlineTradeQueryHessianService.orderSum(startTime, endTime, "", list);// 总笔数
			receiveMap = receiveQueryFacade.orderSumCount(startTime, endTime, customerNo);// 代收总笔数
		}
		String pos = posOrder.orderSum(startTime, endTime, customerNo); // POS总笔数
		String jyfxCounts = shareProfitService.orderSumNotRemit(startTime, endTime, customerNo); // 经营分析成功
		/*List<Map<String, String>> dfMap = requestFacade.dfSuccess(startTime, endTime, ""); // 代付成功笔数
		List<Map<String, String>> dfCount = requestFacade.dfSuccessCount(startTime, endTime, ""); // 代付总笔数*/
		// 总笔数
		double jyamount = 0;
		int jycount = 0;
		if(allCount != null){
			String onlineCount = JsonUtils.toJsonString(allCount.get(0));
			JSONObject jsonObject = JSONObject.fromObject(onlineCount);
			if (jsonObject.getInt("counts") != 0) {
				jyamount = jsonObject.getDouble("amount");
				jycount = jsonObject.getInt("counts");
			}
		}
		// POS
		int posCount = 0;
		double posAmount = 0;
		JSONObject posp = JSONObject.fromObject(pos);
		if (posp.getInt("counts") != 0 && posp.getDouble("amount") != 0) {
			posCount = posp.getInt("counts");
			posAmount = posp.getDouble("amount");
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
		if(receiveMap != null){
			String receive = JsonUtils.toJsonString(receiveMap.get(0));
			JSONObject receiveJson = JSONObject.fromObject(receive);
			if (receiveJson.getInt("COUNTS") != 0) {
				receiveCount = receiveJson.getInt("COUNTS");
				receiveAmount = receiveJson.getDouble("AMOUNT");
			}
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
			return result;
		} catch (Exception e) {
			System.out.println("异常: " + e);
		}
		return null;
	}

	/**
	 * 经营分析  日 - 周 - 月 - 所有
	 * @throws ParseException 
	 */
	@Override
	public String initDayDate(String owner,String initSumFlag) throws ParseException {
		String customerNo = getCustomerNo(owner);
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
		String jyfx = shareProfitService.orderAmountSumByPayType(startTime, endTime, customerNo);
		return jyfx;
	}

	/**
	 * 今日数据
	 */
	@Override
	public String initDate(String owner,String initSumFlag) {
		String customerNo = getCustomerNo(owner);
		List list = getCustomerNoList(owner);
		String resMap = shareProfitService.counts(new Date(), new Date(), customerNo);
		List<Map<String, String>> dfMap = null;
		if(list != null && list.get(0).equals("0")){
			dfMap = requestFacade.dfSuccess(new Date(), new Date(), list);// 成功结算金额
		}
		int jsCount = 0;// 结算笔数
		double jsAmount = 0; // 结算金额
		if(dfMap != null){
			String dfsuccess = JsonUtils.toJsonString(dfMap.get(0));
			JSONObject dfJson = JSONObject.fromObject(dfsuccess);
			if (dfJson.getInt("COUNTS") != 0) {
				jsCount = dfJson.getInt("COUNTS");
				jsAmount = dfJson.getDouble("AMOUNT");
			}
		}
		String dfmap = String.format("{\"counts\": %d,\"sum_amount\": %.2f}", jsCount, jsAmount);
		String result = String.format("{\"resMap\": %s,\"dfMap\": %s}", resMap, dfmap);
		return result;
	}
	
	
	
	/**
	 * 重置商户密码
	 */
	@Override
	public void resetPassWord(String customerNo) {
		Customer cust=customerDao.findByNo(customerNo);
		if(cust==null){
			throw new RuntimeException("resetCustomerPassWord Customer:[" + customerNo + "] is failed! exception:{}");
		}
			try {
				String pwd=custOperInterface.resetPassWord(customerNo);
				SmsUtils.sendCustom(String.format(Constant.AGENT_RESETPASSWORD,pwd), cust.getPhoneNo());
			} catch (IOException e) {
				throw new RuntimeException("resetCustomerPassWord SMS:[" + customerNo + "] is failed! exception:{}",e);
			}
	}
	
	public boolean resetPassWordByPhone(String customerNo,String phone){
		Customer cust=customerDao.findByNo(customerNo);
		if(cust==null){
			throw new RuntimeException("resetCustomerPassWord Customer:[" + customerNo + "] is failed! exception:{}");
		}
		try {
			String pwd=custOperInterface.resetPassWordByPhone(customerNo,phone);
			if (pwd!=null) {
				SmsUtils.sendCustom(String.format(Constant.AGENT_RESETPASSWORD,pwd), cust.getPhoneNo());
				return true;
			}else {
				return false;
			}
		} catch (IOException e) {
			throw new RuntimeException("resetCustomerPassWord SMS:[" + customerNo + "] is failed! exception:{}",e);
		}
	}
	
	@Override
	public void create(Customer customer, CustomerCert customerCert, List<CustomerFee> custFees, ServiceConfigBean serviceConfigBean, CustomerSettle customerSettle, String oper, PartnerRouterBean partnerRouterBean, int cycle,ReceiveConfigInfoBean receiveConfigInfoBean,List<Shop> shopList, String configStatus) {

		customer.setCreateTime(new Date());
		String customerNo = this.getNowCustomerNo();
		customer.setCustomerNo(customerNo);
		customer.setStatus(CustomerStatus.TRUE);
		customer.setOpenTime(new Date());
		customer.setUseCustomPermission(YesNo.N);
		customerDao.create(customer);
		logger.info("create customer customerNo:" + customer.getCustomerNo() + " info:" + JsonUtils.toJsonString(customer));

		CustomerHistory customerHistory = new CustomerHistory(customer, oper);
		customerHistoryDao.create(customerHistory);
		
		customerCert.setCreateTime(new Date());
		customerCert.setCustomerNo(customerNo);
		customerCertDao.create(customerCert);
		logger.info("create customerCert customerNo:" + customerCert.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerCert));

		CustomerCertHistory customerCertHistorty = new CustomerCertHistory(customerCert, oper);
		customerCertHistoryDao.create(customerCertHistorty);

		for (CustomerFee customerFee : custFees) {
			if (customerFee == null || customerFee.getFeeType() == null) {
				continue;
			}
			if (ProductType.POS==customerFee.getProductType()) {//POS有费率需要同步到posp系统
				syncInfoDao.syncInfoAddFormPosp(SyncType.CUSTOMER_SYNC, JsonUtils.toJsonString(customer), com.yl.boss.enums.Status.TRUE);
			}
			customerFee.setCreateTime(new Date());
			customerFee.setCustomerNo(customerNo);
			customerFee.setStatus(Status.TRUE);
			customerFeeDao.create(customerFee);
			logger.info("create customerFee customerNo:" + customerFee.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerFee));

			CustomerFeeHistory customerFeeHistorty = new CustomerFeeHistory(customerFee, oper);
			customerFeeHistoryDao.create(customerFeeHistorty);
		}
		// 生成MD5秘钥
		CustomerKey customerKey = new CustomerKey();
		customerKey.setCustomerNo(customerNo);
		customerKey.setKeyType(KeyType.MD5);
		customerKeyService.create(customerKey, oper);
		// 生成RSA秘钥
		CustomerKey customerKey2 = new CustomerKey();
		customerKey2.setKeyType(KeyType.RSA);
		customerKey2.setCustomerNo(customerNo);
		customerKeyService.create(customerKey2, oper);

		customerSettle.setCreateTime(new Date());
		customerSettle.setCustomerNo(customerNo);
		customerSettleDao.create(customerSettle);
		logger.info("create customerSettle customerNo:" + customerSettle.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerSettle));

		CustomerSettleHistory customerSettleHistory = new CustomerSettleHistory(customerSettle, oper);
		customerSettleHistoryDao.create(customerSettleHistory);
		String pwd=null;
		if(customer.getCustomerType()!=CustomerType.OEM){
			pwd=Long.toHexString(System.nanoTime());
			// 创建商户操作员
			CustOperator operator = new CustOperator();
			operator.setPassword(pwd);
			operator.setRealname(customer.getShortName());
			operator.setUsername(customer.getPhoneNo());
			operator.setStatus(JsonUtils.toJsonToObject(Status.TRUE, com.yl.customer.api.enums.Status.class));
			operator.setCustomerNo(customer.getCustomerNo());
			operator.setRoleId(Long.valueOf(1));
			custOperInterface.create(operator);
			logger.info("create operator customerNo:" + operator.getCustomerNo() + " info:" + JsonUtils.toJsonString(operator));
		}
		// 创建商户账户
		AccountOpen accountOpen = new AccountOpen();
		accountOpen.setOperator(oper);
		accountOpen.setAccountType(AccountType.CASH);
		accountOpen.setBussinessCode("OPEN_ACCOUNT");
		accountOpen.setCurrency(Currency.RMB);
		accountOpen.setStatus(AccountStatus.NORMAL);
		accountOpen.setSystemCode("boss");
		accountOpen.setSystemFlowId("OC" + customer.getCustomerNo());
		accountOpen.setRequestTime(new Date());
		accountOpen.setUserNo(customer.getCustomerNo());
		accountOpen.setUserRole(UserRole.CUSTOMER);
		accountOpen.setCycle(cycle);
		AccountOpenResponse accountOpenResponse = accountInterface.openAccount(accountOpen);
		if (accountOpenResponse.getResult() == HandlerResult.FAILED) {
			throw new RuntimeException("customer No:" + customer.getCustomerNo() + " open account failed:" + accountOpenResponse + "");
		}
		logger.info("create account customerNo:" + accountOpen.getUserNo() + " info:" + JsonUtils.toJsonString(accountOpen));

		if (partnerRouterBean.getProfiles() != null && partnerRouterBean.getProfiles().size() != 0) {
			// 创建商户路由
			partnerRouterBean.setPartnerCode(customer.getCustomerNo());
			onlinePartnerRouterHessianService.createPartnerRouter(partnerRouterBean);
			logger.info("create partnerRoute customerNo:" + accountOpen.getUserNo() + " info:" + JsonUtils.toJsonString(partnerRouterBean));
		}
		
		String dpayPass = "";
		// 创建代付信息
		if (serviceConfigBean != null) {
			try {
				serviceConfigBean.setOwnerId(customerNo);
				serviceConfigBean.setValid("TRUE");
				serviceConfigBean.setPhone(customer.getPhoneNo());
				// Win 格式化
				serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
				serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\r\n", ""));
				//Linux 格式化
				serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\n", ""));
				serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\n", ""));
				dpayPass = GengeratePassword.getExchangeCode().toString();
				serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
				serviceConfigBean.setOwnerRole(OwnerRole.CUSTOMER.toString());

				if (customer.getCustomerType() == CustomerType.OEM) {
					// 商户审核自动；
					serviceConfigBean.setManualAudit("TRUE");
					// 运营审核人工； 
					serviceConfigBean.setBossAudit("FALSE");
					// 短信校验关闭； 
					serviceConfigBean.setUsePhoneCheck("FALSE");
					//自动结算人工
					serviceConfigBean.setFireType("MAN");
				}
				
				serviceConfigFacade.openDF(serviceConfigBean,oper);
			} catch (Exception e) {
				logger.info("create openDF serviceConfigBean.getOwnerId():" + serviceConfigBean.getOwnerId());
			}
		}
		
		//创建代收信息
		if(receiveConfigInfoBean != null){
			try {
				receiveConfigInfoBean.setOwnerId(customerNo);
				// Win 格式化
				receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
				receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\r\n", ""));
				//Linux 格式化
				receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\n", ""));
				receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\n", ""));
				receiveConfigInfoBean.setOper(oper);
				receiveConfigInfoBean.setCreateTime(new Date());
				receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
			} catch (Exception e) {
				logger.info("create Receive serviceConfigBean.getOwnerId():" + receiveConfigInfoBean.getOwnerId());
			}
		}
		
		//创建网点信息
		if(shopList != null){
			try {
				for (int i = 0; i < shopList.size(); i++) {
					Shop shop = new Shop();
					shop.setCustomer(this.findByCustNo(customer.getCustomerNo()));
					shop.setCreateTime(new Date());
					shop.setStatus(com.yl.boss.enums.Status.TRUE);
					shop.setShopNo(CodeBuilder.buildNumber(15));
					shop.setShopName(shopList.get(i).getShopName());
					shop.setPrintName(shopList.get(i).getPrintName());
					shop.setBindPhoneNo(shopList.get(i).getBindPhoneNo());
					shopService.shopAdd(shop);
				}
			} catch (Exception e) {
				logger.info("create Shop failed! Error Messages:"+e.getMessage());
			}
		}
		
		// 实名认证路由
		if (null != configStatus) {
			String config = authConfigHessianService.queryAuthConfigByCustomerNo(customer.getCustomerNo());
			if (StringUtils.isBlank(config)) {
				AuthConfigBean authConfigBean = new AuthConfigBean();
				authConfigBean.setBusiType(AuthBusiType.BINDCARD_AUTH);
				authConfigBean.setCode(CodeBuilder.build("ACI", "yyyyMMdd"));
				authConfigBean.setCreateTime(new Date());
				authConfigBean.setCustomerName(customer.getShortName());
				authConfigBean.setCustomerNo(customer.getCustomerNo());
				authConfigBean.setLastUpdateTime(new Date());
				authConfigBean.setStatus(AuthConfigStatus.TRUE);
				authConfigBean.setRoutingTemplateCode(configStatus);
				authConfigHessianService.createAuthConfig(authConfigBean);
			}
		}
		
		if(customer.getCustomerType()!=CustomerType.OEM) {
			try {
				SmsUtils.sendCustom(String.format(Constant.SMS_CUST_OPEN, customer.getFullName(), customer.getPhoneNo(), pwd, dpayPass), customer.getPhoneNo());
			} catch (IOException e) {
				logger.error("", e);
			}
		}else {
			logger.info("OEM商户创建成功");
		}
	}
	
	/**
	 * 
	 * @Title:CustomerServiceImpl
	 * @Description:外部系统商户入网
	 * @param customer
	 * @param customerCert
	 * @param custFees
	 * @param serviceConfigBean
	 * @param customerSettle
	 * @param oper
	 * @date 2016年10月23日 下午8:25:08
	 */
	public void create(Customer customer, CustomerCert customerCert, List<CustomerFee> custFees, ServiceConfigBean serviceConfigBean,ReceiveConfigInfoBean receiveConfigInfoBean, CustomerSettle customerSettle, String oper, String systemCode, int cycle,List<Shop> shopList) {
		customer.setCreateTime(new Date());
		String customerNo = this.getNowCustomerNo();
		customer.setCustomerNo(customerNo);
		customer.setUseCustomPermission(YesNo.N);
		customerDao.create(customer);
		logger.info("create customer customerNo:" + customer.getCustomerNo() + " info:" + JsonUtils.toJsonString(customer));
		
		CustomerHistory customerHistory = new CustomerHistory(customer, oper);
		customerHistoryDao.create(customerHistory);
		
		customerCert.setCreateTime(new Date());
		customerCert.setCustomerNo(customerNo);
		customerCertDao.create(customerCert);
		logger.info("create customerCert customerNo:" + customerCert.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerCert));
		
		CustomerCertHistory customerCertHistorty = new CustomerCertHistory(customerCert, oper);
		customerCertHistoryDao.create(customerCertHistorty);
		
		for (CustomerFee customerFee : custFees) {
			if (customerFee == null || customerFee.getFeeType() == null) {
				continue;
			}
			customerFee.setCreateTime(new Date());
			customerFee.setCustomerNo(customerNo);
			customerFee.setStatus(Status.TRUE);
			customerFeeDao.create(customerFee);
			logger.info("create customerFee customerNo:" + customerFee.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerFee));
			
			CustomerFeeHistory customerFeeHistorty = new CustomerFeeHistory(customerFee, oper);
			customerFeeHistoryDao.create(customerFeeHistorty);
		}
		// 生成MD5秘钥
		CustomerKey customerKey = new CustomerKey();
		customerKey.setCustomerNo(customerNo);
		customerKey.setKeyType(KeyType.MD5);
		customerKeyService.create(customerKey, oper);
		// 生成RSA秘钥
		CustomerKey customerKey2 = new CustomerKey();
		customerKey2.setKeyType(KeyType.RSA);
		customerKey2.setCustomerNo(customerNo);
		customerKeyService.create(customerKey2, oper);
		
		customerSettle.setCreateTime(new Date());
		customerSettle.setCustomerNo(customerNo);
		customerSettleDao.create(customerSettle);
		logger.info("create customerSettle customerNo:" + customerSettle.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerSettle));
		
		CustomerSettleHistory customerSettleHistory = new CustomerSettleHistory(customerSettle, oper);
		customerSettleHistoryDao.create(customerSettleHistory);
		
		/*// 创建商户操作员
		CustOperator operator = new CustOperator();
		operator.setPassword(Long.toHexString(System.nanoTime()));
		operator.setRealname(customer.getShortName());
		operator.setUsername(customer.getPhoneNo());
		operator.setStatus(Status.TRUE);
		operator.setCustomerNo(customer.getCustomerNo());
		operator.setRoleId(Long.valueOf(1));
		custOperInterface.create(operator);
		logger.info("create operator customerNo:" + operator.getCustomerNo() + " info:" + JsonUtils.toJsonString(operator));*/
		
		// 创建商户账户
		AccountOpen accountOpen = new AccountOpen();
		accountOpen.setOperator(oper);
		accountOpen.setAccountType(AccountType.CASH);
		accountOpen.setBussinessCode("OPEN_ACCOUNT");
		accountOpen.setCurrency(Currency.RMB);
		accountOpen.setStatus(AccountStatus.NORMAL);
		accountOpen.setSystemCode(systemCode);
		accountOpen.setSystemFlowId("OC" + customer.getCustomerNo());
		accountOpen.setRequestTime(new Date());
		accountOpen.setUserNo(customer.getCustomerNo());
		accountOpen.setUserRole(UserRole.CUSTOMER);
		accountOpen.setCycle(cycle);
		AccountOpenResponse accountOpenResponse = accountInterface.openAccount(accountOpen);
		if (accountOpenResponse.getResult() == HandlerResult.FAILED) {
			throw new RuntimeException("customer No:" + customer.getCustomerNo() + " open account failed:" + accountOpenResponse + "");
		}
		logger.info("create account customerNo:" + accountOpen.getUserNo() + " info:" + JsonUtils.toJsonString(accountOpen));
		
		String dpayPass = "";
		// 创建代付信息
		if (serviceConfigBean != null) {
			try {
				serviceConfigBean.setOwnerId(customerNo);
				serviceConfigBean.setValid("TRUE");
				serviceConfigBean.setPhone(customer.getPhoneNo());
				// Win 格式化
				serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
				serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\r\n", ""));
				//Linux 格式化
				serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\n", ""));
				serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\n", ""));
				dpayPass = GengeratePassword.getExchangeCode().toString();
				serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
				serviceConfigBean.setOwnerRole(OwnerRole.CUSTOMER.toString());
				serviceConfigFacade.openDF(serviceConfigBean,oper);
			} catch (Exception e) {
				logger.info("create openDF serviceConfigBean.getOwnerId():" + serviceConfigBean.getOwnerId());
			}
		}
		
		
		//代收
		if (receiveConfigInfoBean!=null) {
			receiveConfigInfoBean.setOwnerId(customerNo);
			receiveConfigInfoBean.setStatus(ReceiveConfigStatus.TRUE);
			receiveConfigInfoBean.setCreateTime(new Date());
			// Win 格式化
			receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
			receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\r\n", ""));
			//Linux 格式化
			receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\n", ""));
			receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\n", ""));
			receiveConfigInfoBean.setOper(oper);
			receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
		}
		
		//创建网点信息
		if(shopList != null){
			try {
				for (int i = 0; i < shopList.size(); i++) {
					Shop shop = new Shop();
					shop.setCustomer(this.findByCustNo(customer.getCustomerNo()));
					shop.setCreateTime(new Date());
					shop.setStatus(com.yl.boss.enums.Status.TRUE);
					shop.setShopNo(customer.getCustomerNo() + "-" + (1001 + i));
					shop.setShopName(shopList.get(i).getShopName());
					shop.setPrintName(shopList.get(i).getPrintName());
					shop.setBindPhoneNo(shopList.get(i).getBindPhoneNo());
					shopService.shopAdd(shop);
				}
			} catch (Exception e) {
				logger.info("create Shop failed! Error Messages:"+e.getMessage());
			}
		}

	}
	
	/**
	 * 扫码入网
	 */
	@Override
	public String QrCreate(License license, Customer customer, CustomerCert customerCert, List<CustomerFee> custFees, ServiceConfigBean serviceConfigBean, ReceiveConfigInfoBean receiveConfigInfoBean, CustomerSettle customerSettle, String oper, String systemCode, int cycle) {
		customer.setCreateTime(new Date());
		String customerNo = this.getNowCustomerNo();
		customer.setCustomerNo(customerNo);
		customer.setSource(CustomerSource.QRCODE);
		customerDao.create(customer);
		logger.info("create customer customerNo:" + customer.getCustomerNo() + " info:" + JsonUtils.toJsonString(customer));
		
		CustomerHistory customerHistory = new CustomerHistory(customer, oper);
		customerHistoryDao.create(customerHistory);
		
		customerCert.setCreateTime(new Date());
		customerCert.setCustomerNo(customerNo);
		customerCertDao.create(customerCert);
		logger.info("create customerCert customerNo:" + customerCert.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerCert));
		
		CustomerCertHistory customerCertHistorty = new CustomerCertHistory(customerCert, oper);
		customerCertHistoryDao.create(customerCertHistorty);
		
		for (CustomerFee customerFee : custFees) {
			if (customerFee == null || customerFee.getFeeType() == null) {
				continue;
			}
			customerFee.setCreateTime(new Date());
			customerFee.setCustomerNo(customerNo);
			customerFee.setStatus(Status.TRUE);
			customerFeeDao.create(customerFee);
			logger.info("create customerFee customerNo:" + customerFee.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerFee));
			
			CustomerFeeHistory customerFeeHistorty = new CustomerFeeHistory(customerFee, oper);
			customerFeeHistoryDao.create(customerFeeHistorty);
		}
		// 生成MD5秘钥
		CustomerKey customerKey = new CustomerKey();
		customerKey.setCustomerNo(customerNo);
		customerKey.setKeyType(KeyType.MD5);
		customerKeyService.create(customerKey, oper);
		// 生成RSA秘钥
		CustomerKey customerKey2 = new CustomerKey();
		customerKey2.setKeyType(KeyType.RSA);
		customerKey2.setCustomerNo(customerNo);
		customerKeyService.create(customerKey2, oper);
		
		customerSettle.setCreateTime(new Date());
		customerSettle.setCustomerNo(customerNo);
		customerSettleDao.create(customerSettle);
		logger.info("create customerSettle customerNo:" + customerSettle.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerSettle));
		
		CustomerSettleHistory customerSettleHistory = new CustomerSettleHistory(customerSettle, oper);
		customerSettleHistoryDao.create(customerSettleHistory);
		
		
		// 创建商户账户
		AccountQuery accountQuery = new AccountQuery();
		accountQuery.setUserNo(customer.getCustomerNo());
		accountQuery.setUserRole(UserRole.CUSTOMER);
		AccountQueryResponse accountResponse = accountQueryInterface.findAccountBy(accountQuery);
		if(accountResponse!=null&&accountResponse.getAccountBeans()!=null&&accountResponse.getAccountBeans().size()==1){
			AccountBean accountBean =accountResponse.getAccountBeans().get(0);
			AccountModify accountModify = new AccountModify();
			accountModify.setAccountNo(accountBean.getCode());
			accountModify.setUserNo(accountBean.getUserNo());
			accountModify.setCycle(cycle);
			accountModify.setAccountStatus(accountBean.getStatus());//注释掉查询时候是什么状态就什么状态,不能因为修改入账周期改变状态
			accountModify.setSystemCode("QRCODE");
			accountModify.setSystemFlowId("OC" + customer.getCustomerNo());
			accountModify.setBussinessCode("UPDATE_ACCOUNT");
			accountModify.setRequestTime(new Date());
			accountModify.setOperator(oper);
			accountModify.setRemark("运营调整商户入账周期为:T"+cycle);
			AccountModifyResponse accountModifyResponse = accountInterface.modifyAccount(accountModify);
			if (accountModifyResponse.getResult() == HandlerResult.FAILED) {
				throw new RuntimeException("customer No:" + customer.getCustomerNo() + " update account failed:" + accountModifyResponse + "");
			}
			logger.info("update account customerNo:" + accountModify.getUserNo() + " info:" + JsonUtils.toJsonString(accountModify));
		} else {
			AccountOpen accountOpen = new AccountOpen();
			accountOpen.setOperator(oper);
			accountOpen.setAccountType(AccountType.CASH);
			accountOpen.setBussinessCode("OPEN_ACCOUNT");
			accountOpen.setCurrency(Currency.RMB);
			accountOpen.setStatus(AccountStatus.NORMAL);
			accountOpen.setSystemCode(systemCode);
			accountOpen.setSystemFlowId("OC" + customer.getCustomerNo());
			accountOpen.setRequestTime(new Date());
			accountOpen.setUserNo(customer.getCustomerNo());
			accountOpen.setUserRole(UserRole.CUSTOMER);
			accountOpen.setCycle(cycle);
			AccountOpenResponse accountOpenResponse = accountInterface.openAccount(accountOpen);
			if (accountOpenResponse.getResult() == HandlerResult.FAILED) {
				throw new RuntimeException("customer No:" + customer.getCustomerNo() + " open account failed:" + accountOpenResponse + "");
			}
			logger.info("create account customerNo:" + accountOpen.getUserNo() + " info:" + JsonUtils.toJsonString(accountOpen));
		}
		
		String dpayPass = "";
		// 创建代付信息
		if (serviceConfigBean != null) {
			try {
				serviceConfigBean.setOwnerId(customerNo);
				serviceConfigBean.setValid("TRUE");
				serviceConfigBean.setPhone(customer.getPhoneNo());
				// Win 格式化
				serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
				serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\r\n", ""));
				//Linux 格式化
				serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\n", ""));
				serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\n", ""));
				dpayPass = GengeratePassword.getExchangeCode().toString();
				serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
				serviceConfigBean.setOwnerRole(OwnerRole.CUSTOMER.toString());
				serviceConfigFacade.openDF(serviceConfigBean,oper);
			} catch (Exception e) {
				logger.info("create openDF serviceConfigBean.getOwnerId():" + serviceConfigBean.getOwnerId());
			}
		}
		
		
		//代收
		if (receiveConfigInfoBean!=null) {
			receiveConfigInfoBean.setOwnerId(customerNo);
			receiveConfigInfoBean.setStatus(ReceiveConfigStatus.TRUE);
			receiveConfigInfoBean.setCreateTime(new Date());
			// Win 格式化
			receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
			receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\r\n", ""));
			//Linux 格式化
			receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\n", ""));
			receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\n", ""));
			receiveConfigInfoBean.setOper(oper);
			receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
		}
		return customerNo;
	}
	
	

	@Override
	public void update(Customer customer,CustomerCert customerCert, String oper,int cycle ) {

		Customer cust = customerDao.findByNo(customer.getCustomerNo());
		if (cust != null) {
			cust.setAddr(customer.getAddr());
			// cust.setCautionMoney(customer.getCautionMoney());
			cust.setLinkMan(customer.getLinkMan());
			if (customer.getStatus()!=null) {
				cust.setStatus(customer.getStatus());
			}
			cust.setCustomerType(customer.getCustomerType());
			cust.setFullName(customer.getFullName());
			cust.setIdCard(customer.getIdCard());
			cust.setEMail(customer.getEMail());
			cust.setShortName(customer.getShortName());
			cust.setAgentNo(customer.getAgentNo());
			cust.setPhoneNo(customer.getPhoneNo());
			cust.setProvince(customer.getProvince());
			cust.setCity(customer.getCity());
			cust.setOrganization(customer.getOrganization());
			customerDao.update(cust);
			//更新商户证件信息
			CustomerCert custCert= customerCertService.findByCustNo(cust.getCustomerNo());
			custCert.setValidStartTime(customerCert.getValidStartTime());
//			custCert.setIdCard(customerCert.getIdCard());
			custCert.setValidEndTime(customerCert.getValidEndTime());
			custCert.setBusinessScope(customerCert.getBusinessScope());
			custCert.setBusinessAddress(customerCert.getBusinessAddress());
			custCert.setEnterpriseCode(customerCert.getEnterpriseCode());
			custCert.setEnterpriseUrl(customerCert.getEnterpriseUrl());
			custCert.setIcp(customerCert.getIcp());
			custCert.setConsumerPhone(customerCert.getConsumerPhone());
			custCert.setLegalPerson(customerCert.getLegalPerson());
			
			custCert.setAttachment(customerCert.getAttachment()!=null?customerCert.getAttachment():custCert.getAttachment());
			custCert.setBusiLiceCert(customerCert.getBusiLiceCert()!=null?customerCert.getBusiLiceCert():custCert.getBusiLiceCert());
			custCert.setIdCard(customerCert.getIdCard()!=null?customerCert.getIdCard():custCert.getIdCard());
			custCert.setOpenBankAccCert(customerCert.getOpenBankAccCert()!=null?customerCert.getOpenBankAccCert():custCert.getOpenBankAccCert());
			custCert.setOrganizationCert(customerCert.getOrganizationCert()!=null?customerCert.getOrganizationCert():custCert.getOrganizationCert());
			custCert.setTaxRegCert(customerCert.getTaxRegCert()!=null?customerCert.getTaxRegCert():custCert.getTaxRegCert());
			customerCertService.update(custCert, oper);
			//更新帐户信息
			AccountQuery accountQuery = new AccountQuery();
			accountQuery.setUserNo(customer.getCustomerNo());
			accountQuery.setUserRole(UserRole.CUSTOMER);
			AccountQueryResponse accountResponse = accountQueryInterface.findAccountBy(accountQuery);
			if(accountResponse!=null&&accountResponse.getAccountBeans()!=null&&accountResponse.getAccountBeans().size()==1){
				AccountBean accountBean =accountResponse.getAccountBeans().get(0);
				
				AccountModify accountModify = new AccountModify();
				accountModify.setAccountNo(accountBean.getCode());
				accountModify.setUserNo(accountBean.getUserNo());
				accountModify.setCycle(cycle);
				accountModify.setAccountStatus(accountBean.getStatus());//注释掉查询时候是什么状态就什么状态,不能因为修改入账周期改变状态
				accountModify.setSystemCode("boss");
				accountModify.setSystemFlowId("OC" + customer.getCustomerNo());
				accountModify.setBussinessCode("UPDATE_ACCOUNT");
				accountModify.setRequestTime(new Date());
				accountModify.setOperator(oper);
				accountModify.setRemark("运营调整商户入账周期为:T"+cycle);
				AccountModifyResponse accountModifyResponse = accountInterface.modifyAccount(accountModify);
				if (accountModifyResponse.getResult() == HandlerResult.FAILED) {
					throw new RuntimeException("customer No:" + customer.getCustomerNo() + " update account failed:" + accountModifyResponse + "");
				}
				logger.info("update account customerNo:" + accountModify.getUserNo() + " info:" + JsonUtils.toJsonString(accountModify));
			}else{
				throw new RuntimeException("没有查询到账户信息或账户信息存在多个");
			}
			
			CustomerCertHistory customerCertHistorty = new CustomerCertHistory(customerCert, oper);
			customerCertHistoryDao.create(customerCertHistorty);
			CustomerHistory customerHistory = new CustomerHistory(cust, oper);
			customerHistoryDao.create(customerHistory);
		}

	}
	
	@Override
	public void updateForAudit(Customer customer, List<CustomerFee> customerFees,ServiceConfigBean serviceConfigBean,ReceiveConfigInfoBean receiveConfigInfoBean,PartnerRouterBean partnerRouterBean, String oper,String msg,List<Shop> shopList) {
		Customer cust = customerDao.findByNo(customer.getCustomerNo());
		if (cust != null) {
			cust.setStatus(customer.getStatus());
			if(cust.getStatus().equals(CustomerStatus.TRUE)){
				cust.setOpenTime(new Date());
			}
			customerDao.update(cust);
			CustomerHistory customerHistory = new CustomerHistory(customer, oper,msg);
			customerHistoryDao.create(customerHistory);
		}
		if (customer.getStatus() == CustomerStatus.TRUE) {
			if (CustomerSource.AGENT_SHARE == cust.getSource() || CustomerSource.APP_QUICK == cust.getSource() ) {
				auditShareCustomer(cust, customerFees, serviceConfigBean, receiveConfigInfoBean, partnerRouterBean, oper, msg, shopList);
			} else {
				auditNormalCustomer(cust, customerFees, serviceConfigBean, receiveConfigInfoBean, partnerRouterBean, oper, msg, shopList);
			}
		} else if (StringUtils.isBlank(cust.getAgentNo())) {
			try {
				SmsUtils.sendCustom(String.format(Constant.AGENT_AGENT_REFUSE, customer.getCustomerNo(), msg), cust.getPhoneNo());
			} catch (IOException e) {
				logger.info("商户发送短信错误", e);
			}
		}
		
	}
	
	/**
	 * 
	 * @Description 审核分享注册的商户
	 * @param cust
	 * @param customerFees
	 * @param serviceConfigBean
	 * @param receiveConfigInfoBean
	 * @param partnerRouterBean
	 * @param oper
	 * @param msg
	 * @param shopList
	 * @date 2017年9月18日
	 */
	private void auditShareCustomer(Customer cust, List<CustomerFee> customerFees, ServiceConfigBean serviceConfigBean, ReceiveConfigInfoBean receiveConfigInfoBean, PartnerRouterBean partnerRouterBean, String oper, String msg, List<Shop> shopList) {
		for (CustomerFee customerFee : customerFees) {
			if (customerFee == null) {
				continue;
			}
			
			if(null != customerFee.getId()){//原有的就更新
				if(null==customerFee.getFeeType()){
					customerFeeService.delete(customerFee, oper);
				}else{
					customerFeeService.update(customerFee, oper);
				}
			}else{//新增的就创建
				if (customerFee.getFeeType() == null || "".equals(customerFee.getFeeType())) {
					continue;
				}
				customerFee.setCreateTime(new Date());
				customerFee.setCustomerNo(cust.getCustomerNo());
				customerFee.setStatus(Status.TRUE);
				customerFeeService.create(customerFee, oper);
			}
			
		}
		// 生成设备激活时间
		Device device = deviceService.findByCustomerNo(cust.getCustomerNo());
		if (device != null) {
			device.setActivateTime(new Date());
			device.setUpdateTime(new Date());
			deviceService.upDevice(device);
		}
		String dpayPass = "";
		if (serviceConfigBean != null) {
			ServiceConfigBean bean = serviceConfigFacade.query(cust.getCustomerNo());
			if (bean != null) {
				bean.setMaxAmount(serviceConfigBean.getMaxAmount());
				bean.setMinAmount(serviceConfigBean.getMinAmount());
				bean.setCreateDate(new Date());
				dpayPass = GengeratePassword.getExchangeCode().toString();
				bean.setComplexPassword(Md5Util.hmacSign(dpayPass, "DPAY"));
				bean.setDayMaxAmount(serviceConfigBean.getDayMaxAmount());
				bean.setStartTime(serviceConfigBean.getStartTime());
				bean.setEndTime(serviceConfigBean.getEndTime());
				serviceConfigFacade.update(bean);
			}
		}
		// 修改代收配置信息
		if (receiveConfigInfoBean != null) {
			ReceiveConfigInfoBean receiveConfigInfo = receiveQueryFacade.queryReceiveConfigBy(receiveConfigInfoBean.getOwnerId());
			if (receiveConfigInfo != null) {
				// 设置属性
				receiveConfigInfo.setDailyMaxAmount(receiveConfigInfoBean.getDailyMaxAmount());
				receiveConfigInfo.setSingleBatchMaxNum(receiveConfigInfoBean.getSingleBatchMaxNum());
				receiveConfigInfo.setSingleMaxAmount(receiveConfigInfoBean.getSingleMaxAmount());
				receiveConfigInfo.setStatus(receiveConfigInfoBean.getStatus());
				receiveQueryFacade.updateReceiveConfig(receiveConfigInfo);
			}
		}

		if (partnerRouterBean.getProfiles() != null && partnerRouterBean.getProfiles().size() != 0) {
			// 创建商户路由
			partnerRouterBean.setPartnerCode(cust.getCustomerNo());
			onlinePartnerRouterHessianService.createPartnerRouter(partnerRouterBean);
			logger.info("create partnerRoute customerNo:" + cust.getCustomerNo() + " info:" + JsonUtils.toJsonString(partnerRouterBean));
		}

		// 更新/新增网点信息
		if (shopList != null) {

			Shop shopResult = null;

			for (int i = 0; i < shopList.size(); i++) {

				shopResult = new Shop();

				// 判断是修改还是新增
				if (shopList.get(i).getShopNo() != null && !"".equals(shopList.get(i).getShopNo()) && !"undefined".equals(shopList.get(i).getShopNo())) {

					shopResult = shopService.queryByShopNo(shopList.get(i).getShopNo());

					shopResult.setShopName(shopList.get(i).getShopName());
					shopResult.setPrintName(shopList.get(i).getPrintName());
					shopResult.setBindPhoneNo(shopList.get(i).getBindPhoneNo());

					shopService.ShopUpdate(shopResult);

				} else {

					shopResult.setCustomer(this.findByCustNo(cust.getCustomerNo()));
					shopResult.setCreateTime(new Date());
					shopResult.setStatus(com.yl.boss.enums.Status.TRUE);

					// 获取该商户最高网点编号
					String no = shopService.queryMaxShopNo(cust.getCustomerNo());
					if (no != null && no != "") {
						shopResult.setShopNo(cust.getCustomerNo() + "-" + (Integer.parseInt(no.split("-")[1]) + 1));
					} else {
						shopResult.setShopNo(cust.getCustomerNo() + "-1001");
					}

					shopResult.setShopName(shopList.get(i).getShopName());
					shopResult.setPrintName(shopList.get(i).getPrintName());
					shopResult.setBindPhoneNo(shopList.get(i).getBindPhoneNo());
					shopService.shopAdd(shopResult);

				}

			}

		}
		CustOperator op = custOperInterface.findByCustNo(cust.getCustomerNo());
		try {
			SmsUtils.sendCustom(String.format(Constant.SMS_QUICK_AUDIT_PASS, cust.getFullName(), op.getUsername(), dpayPass), op.getUsername());
		} catch (IOException e) {
			logger.error("", e);
		}

	}
	
	/**
	 * 
	 * @Description 审核常规流程商户
	 * @param cust
	 * @param customerFees
	 * @param serviceConfigBean
	 * @param receiveConfigInfoBean
	 * @param partnerRouterBean
	 * @param oper
	 * @param msg
	 * @param shopList
	 * @date 2017年9月18日
	 */
	private void auditNormalCustomer(Customer cust, List<CustomerFee> customerFees, ServiceConfigBean serviceConfigBean, ReceiveConfigInfoBean receiveConfigInfoBean, PartnerRouterBean partnerRouterBean, String oper, String msg, List<Shop> shopList) {
		for (CustomerFee customerFee : customerFees) {
			if (customerFee == null || customerFee.getFeeType() == null) {
				continue;
			}
			customerFee.setCustomerNo(cust.getCustomerNo());
			customerFee.setStatus(Status.TRUE);
			customerFeeService.update(customerFee, oper);
		}
		// 生成设备激活时间
		Device device = deviceService.findByCustomerNo(cust.getCustomerNo());
		if (device != null) {
			device.setActivateTime(new Date());
			device.setUpdateTime(new Date());
			deviceService.upDevice(device);
		}
		String dpayPass = "";
		if (serviceConfigBean != null) {
			ServiceConfigBean bean = serviceConfigFacade.query(cust.getCustomerNo());
			if (bean != null) {
				bean.setMaxAmount(serviceConfigBean.getMaxAmount());
				bean.setMinAmount(serviceConfigBean.getMinAmount());
				bean.setCreateDate(new Date());
				dpayPass = GengeratePassword.getExchangeCode().toString();
				bean.setComplexPassword(Md5Util.hmacSign(dpayPass, "DPAY"));
				bean.setDayMaxAmount(serviceConfigBean.getDayMaxAmount());
				bean.setStartTime(serviceConfigBean.getStartTime());
				bean.setEndTime(serviceConfigBean.getEndTime());
				
				if (cust.getCustomerType() == CustomerType.OEM) {
					// 商户审核自动；
					bean.setManualAudit("TRUE");
					// 运营审核人工； 
					bean.setBossAudit("FALSE");
					// 短信校验关闭； 
					bean.setUsePhoneCheck("FALSE");
					//自动结算人工
					bean.setFireType("MAN");
				}
				
				serviceConfigFacade.update(bean);
			}
		}
		// 修改代收配置信息
		if (receiveConfigInfoBean != null) {
			ReceiveConfigInfoBean receiveConfigInfo = receiveQueryFacade.queryReceiveConfigBy(receiveConfigInfoBean.getOwnerId());
			if (receiveConfigInfo != null) {
				// 设置属性
				receiveConfigInfo.setDailyMaxAmount(receiveConfigInfoBean.getDailyMaxAmount());
				receiveConfigInfo.setSingleBatchMaxNum(receiveConfigInfoBean.getSingleBatchMaxNum());
				receiveConfigInfo.setSingleMaxAmount(receiveConfigInfoBean.getSingleMaxAmount());
				receiveConfigInfo.setStatus(receiveConfigInfoBean.getStatus());
				receiveQueryFacade.updateReceiveConfig(receiveConfigInfo);
			}
		}

		if (partnerRouterBean.getProfiles() != null && partnerRouterBean.getProfiles().size() != 0) {
			// 创建商户路由
			partnerRouterBean.setPartnerCode(cust.getCustomerNo());
			onlinePartnerRouterHessianService.createPartnerRouter(partnerRouterBean);
			logger.info("create partnerRoute customerNo:" + cust.getCustomerNo() + " info:" + JsonUtils.toJsonString(partnerRouterBean));
		}

		// 更新/新增网点信息
		if (shopList != null) {

			Shop shopResult = null;

			for (int i = 0; i < shopList.size(); i++) {

				shopResult = new Shop();

				// 判断是修改还是新增
				if (shopList.get(i).getShopNo() != null && !"".equals(shopList.get(i).getShopNo()) && !"undefined".equals(shopList.get(i).getShopNo())) {

					shopResult = shopService.queryByShopNo(shopList.get(i).getShopNo());

					shopResult.setShopName(shopList.get(i).getShopName());
					shopResult.setPrintName(shopList.get(i).getPrintName());
					shopResult.setBindPhoneNo(shopList.get(i).getBindPhoneNo());

					shopService.ShopUpdate(shopResult);

				} else {

					shopResult.setCustomer(this.findByCustNo(cust.getCustomerNo()));
					shopResult.setCreateTime(new Date());
					shopResult.setStatus(com.yl.boss.enums.Status.TRUE);

					// 获取该商户最高网点编号
					String no = shopService.queryMaxShopNo(cust.getCustomerNo());
					if (no != null && no != "") {
						shopResult.setShopNo(cust.getCustomerNo() + "-" + (Integer.parseInt(no.split("-")[1]) + 1));
					} else {
						shopResult.setShopNo(cust.getCustomerNo() + "-1001");
					}

					shopResult.setShopName(shopList.get(i).getShopName());
					shopResult.setPrintName(shopList.get(i).getPrintName());
					shopResult.setBindPhoneNo(shopList.get(i).getBindPhoneNo());
					shopService.shopAdd(shopResult);

				}

			}

		}

		CustOperator operator = new CustOperator();
		operator.setPassword(Long.toHexString(System.nanoTime()));
		operator.setRealname(cust.getShortName());
		operator.setUsername(cust.getPhoneNo());
		operator.setStatus(JsonUtils.toJsonToObject(Status.TRUE, com.yl.customer.api.enums.Status.class));
		operator.setCustomerNo(cust.getCustomerNo());
		operator.setRoleId(Long.valueOf(1));
		custOperInterface.create(operator);
		logger.info("create operator customerNo:" + operator.getCustomerNo() + " info:" + JsonUtils.toJsonString(operator));
		try {
			SmsUtils.sendCustom(String.format(Constant.SMS_CUST_OPEN, cust.getFullName(), operator.getUsername(), operator.getPassword(), dpayPass), operator.getUsername());
		} catch (IOException e) {
			logger.error("", e);
		}

	}
	
	@Override
	public ServiceConfigBean findServiceConfigBeanByCustNo(String customerNo) {
		ServiceConfigBean bean = serviceConfigFacade.query(customerNo);
		return bean;
	}
	

	@Override
	public Customer findByPhone(String phone) {
		return customerDao.findByPhone(phone);
	}
	
	@Override
	public void update(Customer customer) {
		customerDao.update(customer);
	}
	
	@Override
	public void updateCustomerOnlyForAgentSystem(Customer customer,ServiceConfigBean serviceConfigBean,
			CustomerCert customerCert, CustomerSettle customerSettle,
			List<CustomerFee> customerFees, String oper,ReceiveConfigInfoBean receiveConfigInfoBean, String systemCode,int cycle,List<Shop> shopList) {
		try {
			Customer cust = customerDao.findByNo(customer.getCustomerNo());
			if (cust != null) {
				//更新商户基本信息
				cust.setFullName(customer.getFullName()!=null?customer.getFullName():cust.getFullName());
				cust.setShortName(customer.getShortName()!=null?customer.getShortName():cust.getShortName());
				cust.setCustomerType(customer.getCustomerType()!=null?customer.getCustomerType():cust.getCustomerType());
				cust.setPhoneNo(customer.getPhoneNo()!=null?customer.getPhoneNo():cust.getPhoneNo());
				cust.setLinkMan(customer.getLinkMan()!=null?customer.getLinkMan():cust.getLinkMan());
				cust.setCautionMoney(customer.getCautionMoney()>0?customer.getCautionMoney():cust.getCautionMoney());
				cust.setAddr(customer.getAddr()!=null?customer.getAddr():cust.getAddr());
				cust.setIdCard(customer.getIdCard()!=null?customer.getIdCard():cust.getIdCard());
				cust.setEMail(customer.getEMail()!=null?customer.getEMail():cust.getEMail());
				cust.setStatus(CustomerStatus.AUDIT);
				cust.setProvince(customer.getProvince());
				cust.setCity(customer.getCity());
				cust.setOrganization(customer.getOrganization());
				customerDao.update(cust);
				CustomerHistory customerHistory = new CustomerHistory(cust, oper);
				customerHistoryDao.create(customerHistory);
				//更新商户证件信息
				CustomerCert custCert= customerCertService.findByCustNo(cust.getCustomerNo());
				if(customerCert != null){
					custCert.setValidStartTime(customerCert.getValidStartTime());
					custCert.setValidEndTime(customerCert.getValidEndTime());
					custCert.setBusinessScope(customerCert.getBusinessScope());
					custCert.setBusinessAddress(customerCert.getBusinessAddress());
					custCert.setEnterpriseCode(customerCert.getEnterpriseCode());
					custCert.setEnterpriseUrl(customerCert.getEnterpriseUrl());
					custCert.setIcp(customerCert.getIcp());
					custCert.setConsumerPhone(customerCert.getConsumerPhone());
					custCert.setLegalPerson(customerCert.getLegalPerson());
					
					custCert.setAttachment(customerCert.getAttachment()!=null?customerCert.getAttachment():custCert.getAttachment());
					custCert.setBusiLiceCert(customerCert.getBusiLiceCert()!=null?customerCert.getBusiLiceCert():custCert.getBusiLiceCert());
					custCert.setIdCard(customerCert.getIdCard()!=null?customerCert.getIdCard():custCert.getIdCard());
					custCert.setOpenBankAccCert(customerCert.getOpenBankAccCert()!=null?customerCert.getOpenBankAccCert():custCert.getOpenBankAccCert());
					custCert.setOrganizationCert(customerCert.getOrganizationCert()!=null?customerCert.getOrganizationCert():custCert.getOrganizationCert());
					custCert.setTaxRegCert(customerCert.getTaxRegCert()!=null?customerCert.getTaxRegCert():custCert.getTaxRegCert());
					customerCertService.update(custCert, oper);
				}
				
				//更新商户结算卡信息
				CustomerSettle custSettle = customerSettleService.findByCustNo(cust.getCustomerNo());
				custSettle.setCustomerType(customerSettle.getCustomerType()!=null?customerSettle.getCustomerType():customerSettle.getCustomerType());
				custSettle.setAccountNo(customerSettle.getAccountNo()!=null?customerSettle.getAccountNo():customerSettle.getAccountNo());
				custSettle.setAccountName(customerSettle.getAccountName()!=null?customerSettle.getAccountName():customerSettle.getAccountName());
				custSettle.setOpenBankName(customerSettle.getOpenBankName()!=null?customerSettle.getOpenBankName():customerSettle.getOpenBankName());
				customerSettleService.update(custSettle, oper);
				
				//代付信息更新或者创建
				if (serviceConfigBean != null) {
					try {
						if (null == serviceConfigBean.getOwnerId() || serviceConfigBean.getOwnerId().equals("")) {
							String dpayPass = "";
							//获取RSA秘钥信息
							CustomerKey customerKey2 = customerKeyService.findBy(customer.getCustomerNo(), KeyType.RSA);
							// 创建代付信息
							serviceConfigBean.setOwnerId(customer.getCustomerNo());
							serviceConfigBean.setValid("TRUE");
							serviceConfigBean.setPhone(customer.getPhoneNo());
							serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
							serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\r\n", ""));
							serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\n", ""));
							serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\n", ""));
							dpayPass = GengeratePassword.getExchangeCode().toString();
							serviceConfigBean.setOwnerRole(OwnerRole.CUSTOMER.toString());
							serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
							
							serviceConfigFacade.openDF(serviceConfigBean,oper);
						} else {// 更新代付信息
							ServiceConfigBean serviceConfig =serviceConfigFacade.query(serviceConfigBean.getOwnerId());
							//设置属性
							serviceConfig.setManualAudit(serviceConfigBean.getManualAudit()!=null?serviceConfigBean.getManualAudit():serviceConfig.getManualAudit());
							serviceConfig.setUsePhoneCheck(serviceConfigBean.getUsePhoneCheck()!=null?serviceConfigBean.getUsePhoneCheck():serviceConfig.getUsePhoneCheck());
							serviceConfig.setCustIp(serviceConfigBean.getCustIp()!=null?serviceConfigBean.getCustIp():serviceConfig.getCustIp());
							serviceConfig.setDomain(serviceConfigBean.getDomain()!=null?serviceConfigBean.getDomain():serviceConfig.getDomain());
							serviceConfig.setMaxAmount(serviceConfigBean.getMaxAmount()>0?serviceConfigBean.getMaxAmount():serviceConfig.getMaxAmount());
							serviceConfig.setMinAmount(serviceConfigBean.getMinAmount()>0?serviceConfigBean.getMinAmount():serviceConfig.getMinAmount());
							serviceConfig.setBossAudit(serviceConfigBean.getBossAudit());
							serviceConfig.setDayMaxAmount(serviceConfigBean.getDayMaxAmount());
							serviceConfig.setStartTime(serviceConfigBean.getStartTime());
							serviceConfig.setEndTime(serviceConfigBean.getEndTime());
							if(serviceConfigBean.getHolidayStatus() != null && !serviceConfigBean.getHolidayStatus().equals("")){
								serviceConfig.setHolidayStatus(serviceConfigBean.getHolidayStatus());
							}
							serviceConfigFacade.updateServiceConfigOnlyForAgentSystem(serviceConfig);
						}
					} catch (Exception e) {
						logger.info("createOrUpdate openDF serviceConfigBean.getOwnerId():" + customer.getCustomerNo());
					}
				}
				
				//代收信息更新或者创建
				if(receiveConfigInfoBean != null){
					try {
						if(null == receiveConfigInfoBean.getOwnerId()){
							//获取RSA秘钥信息
							CustomerKey customerKey2 = customerKeyService.findBy(customer.getCustomerNo(), KeyType.RSA);
							//创建代收信息
							receiveConfigInfoBean.setOwnerId(customer.getCustomerNo());
							receiveConfigInfoBean.setStatus(ReceiveConfigStatus.TRUE);
							receiveConfigInfoBean.setCreateTime(new Date());
							receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
							receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\r\n", ""));
							receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\n", ""));
							receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\n", ""));
							receiveConfigInfoBean.setOper(oper);
							receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
						}else {//修改代收信息
							ReceiveConfigInfoBean receiveConfigInfo = receiveQueryFacade.queryReceiveConfigBy(receiveConfigInfoBean.getOwnerId());
							//设置属性
							receiveConfigInfo.setCustIp(receiveConfigInfoBean.getCustIp());
							receiveConfigInfo.setDailyMaxAmount(receiveConfigInfoBean.getDailyMaxAmount());
							receiveConfigInfo.setDomain(receiveConfigInfoBean.getDomain());
							receiveConfigInfo.setSingleBatchMaxNum(receiveConfigInfoBean.getSingleBatchMaxNum());
							receiveConfigInfo.setSingleMaxAmount(receiveConfigInfoBean.getSingleMaxAmount());
							if(receiveConfigInfo.getStatus().equals("true")){
								receiveConfigInfo.setStatus(receiveConfigInfoBean.getStatus());
							}
							receiveQueryFacade.updateReceiveConfig(receiveConfigInfo);
						}
					} catch (Exception e) {
						logger.info("createOrUpdate openDF receiveConfigInfoBean.getOwnerId():" + customer.getCustomerNo());
					}
				}
				
				
				AccountQuery accountQuery = new AccountQuery();
				accountQuery.setUserNo(customer.getCustomerNo());
				accountQuery.setUserRole(UserRole.CUSTOMER);
				AccountQueryResponse accountResponse = accountQueryInterface.findAccountBy(accountQuery);
				if(accountResponse!=null&&accountResponse.getAccountBeans()!=null&&accountResponse.getAccountBeans().size()==1){
					AccountBean accountBean =accountResponse.getAccountBeans().get(0);
					
					AccountModify accountModify = new AccountModify();
					accountModify.setAccountNo(accountBean.getCode());
					accountModify.setUserNo(accountBean.getUserNo());
					accountModify.setCycle(cycle);
					accountModify.setAccountStatus(accountBean.getStatus());//注释掉查询时候是什么状态就什么状态,不能因为修改入账周期改变状态
					accountModify.setSystemCode(systemCode);
					accountModify.setSystemFlowId("OC" + customer.getCustomerNo());
					accountModify.setBussinessCode("UPDATE_ACCOUNT");
					accountModify.setRequestTime(new Date());
					accountModify.setOperator(oper);
					accountModify.setRemark("服务商系统商户修改入账周期");
					AccountModifyResponse accountModifyResponse = accountInterface.modifyAccount(accountModify);
					if (accountModifyResponse.getResult() == HandlerResult.FAILED) {
						throw new RuntimeException("customer No:" + customer.getCustomerNo() + " update account failed:" + accountModifyResponse + "");
					}
					logger.info("update account customerNo:" + accountModify.getUserNo() + " info:" + JsonUtils.toJsonString(accountModify));
				}else{
					throw new RuntimeException("没有查询到账户信息或账户信息存在多个");
				}
				
				//更新费率信息
				for (CustomerFee customerFee : customerFees) {
					if (customerFee!=null) {
						if(null!=customerFee.getId()){//原有的就更新
							if(null==customerFee.getFeeType()){
								customerFeeService.delete(customerFee, oper);
							}else{
								customerFeeService.update(customerFee, oper);
							}
						}else{//新增的就创建
							if (customerFee.getFeeType() == null || "".equals(customerFee.getFeeType())) {
								continue;
							}
							customerFee.setCreateTime(new Date());
							customerFee.setCustomerNo(customer.getCustomerNo());
							customerFee.setStatus(Status.TRUE);
							customerFeeService.create(customerFee, oper);
						}
					}
				}
				
				//更新/新增网点信息
				if(shopList != null){
					
					Shop shopResult = null;
					
					String[] shopNo = new String[shopList.size()];
					
					for (int i = 0; i < shopList.size(); i++) {
						
						shopResult = new Shop();
						
						//判断是修改还是新增
						if(shopList.get(i).getShopNo() != null && !"".equals(shopList.get(i).getShopNo()) && !"undefined".equals(shopList.get(i).getShopNo())){
							
							shopResult = shopService.queryByShopNo(shopList.get(i).getShopNo());
							
							shopResult.setShopName(shopList.get(i).getShopName());
							shopResult.setPrintName(shopList.get(i).getPrintName());
							shopResult.setBindPhoneNo(shopList.get(i).getBindPhoneNo());
							
							shopService.ShopUpdate(shopResult);
							
							shopNo[i] = shopList.get(i).getShopNo();
						}else {
							
							shopResult.setCustomer(this.findByCustNo(cust.getCustomerNo()));
							shopResult.setCreateTime(new Date());
							shopResult.setStatus(com.yl.boss.enums.Status.TRUE);
							
							//获取该商户最高网点编号
							String no = shopService.queryMaxShopNo(cust.getCustomerNo());
							if(no != null && no != ""){
								shopResult.setShopNo(cust.getCustomerNo() + "-" + (Integer.parseInt(no.split("-")[1]) + 1));
							}else {
								shopResult.setShopNo(cust.getCustomerNo() + "-1001");
							}
							
							shopResult.setShopName(shopList.get(i).getShopName());
							shopResult.setPrintName(shopList.get(i).getPrintName());
							shopResult.setBindPhoneNo(shopList.get(i).getBindPhoneNo());
							shopService.shopAdd(shopResult);
							
							shopNo[i] = shopResult.getShopNo();
						}
						
					}
					
					//查询当前商户旗下所有网点信息
					List<String> list = shopService.queryShopNoByShopCustNo(cust.getCustomerNo());
					
					List<String> delList = new ArrayList<>();
					
					//筛选出需要删除的网点
					for (int i = 0; i < list.size(); i++) {
						boolean exist = false;
						for (int j = 0; j < shopNo.length; j++) {
							if(list.get(i).equals(shopNo[j])){
								exist = true;
							}
						}
						if (exist == false) {
							delList.add(list.get(i));
						}
					}
					
					for (int i = 0; i < delList.size(); i++) {
						 shopService.shopDelByShopNo(delList.get(i));
					}
					
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}
	
	
	@Override
	public void qrUpdateCustomer(Customer customer,ServiceConfigBean serviceConfigBean,
			CustomerCert customerCert, CustomerSettle customerSettle,
			List<CustomerFee> customerFees, String oper,ReceiveConfigInfoBean receiveConfigInfoBean, String systemCode,int cycle) {
		try {
			Customer cust = customerDao.findByNo(customer.getCustomerNo());
			if (cust != null) {
				//更新商户基本信息
				cust.setFullName(customer.getFullName()!=null?customer.getFullName():cust.getFullName());
				cust.setShortName(customer.getShortName()!=null?customer.getShortName():cust.getShortName());
				cust.setCustomerType(customer.getCustomerType()!=null?customer.getCustomerType():cust.getCustomerType());
				cust.setPhoneNo(customer.getPhoneNo()!=null?customer.getPhoneNo():cust.getPhoneNo());
				cust.setLinkMan(customer.getLinkMan()!=null?customer.getLinkMan():cust.getLinkMan());
				cust.setCautionMoney(customer.getCautionMoney()>0?customer.getCautionMoney():cust.getCautionMoney());
				cust.setAddr(customer.getAddr()!=null?customer.getAddr():cust.getAddr());
				cust.setStatus(CustomerStatus.AGENT_AUDIT); //服务商审核
				cust.setIdCard(customer.getIdCard()!=null?customer.getIdCard():cust.getIdCard());
				cust.setEMail(customer.getEMail()!=null?customer.getEMail():cust.getEMail());
				cust.setProvince(customer.getProvince());
				cust.setCity(customer.getCity());
				customerDao.update(cust);
				CustomerHistory customerHistory = new CustomerHistory(cust, oper);
				customerHistoryDao.create(customerHistory);
				//更新商户证件信息
				CustomerCert custCert= customerCertService.findByCustNo(cust.getCustomerNo());
				custCert.setValidStartTime(customerCert.getValidStartTime());
				custCert.setValidEndTime(customerCert.getValidEndTime());
				custCert.setBusinessScope(customerCert.getBusinessScope());
				custCert.setBusinessAddress(customerCert.getBusinessAddress());
				custCert.setEnterpriseCode(customerCert.getEnterpriseCode());
				custCert.setEnterpriseUrl(customerCert.getEnterpriseUrl());
				custCert.setIcp(customerCert.getIcp());
				custCert.setConsumerPhone(customerCert.getConsumerPhone());
				custCert.setLegalPerson(customerCert.getLegalPerson());
				
				custCert.setAttachment(customerCert.getAttachment()!=null?customerCert.getAttachment():custCert.getAttachment());
				custCert.setBusiLiceCert(customerCert.getBusiLiceCert()!=null?customerCert.getBusiLiceCert():custCert.getBusiLiceCert());
				custCert.setIdCard(customerCert.getIdCard()!=null?customerCert.getIdCard():custCert.getIdCard());
				custCert.setOpenBankAccCert(customerCert.getOpenBankAccCert()!=null?customerCert.getOpenBankAccCert():custCert.getOpenBankAccCert());
				custCert.setOrganizationCert(customerCert.getOrganizationCert()!=null?customerCert.getOrganizationCert():custCert.getOrganizationCert());
				custCert.setTaxRegCert(customerCert.getTaxRegCert()!=null?customerCert.getTaxRegCert():custCert.getTaxRegCert());
				customerCertService.update(custCert, oper);
				//更新商户结算卡信息
				CustomerSettle custSettle = customerSettleService.findByCustNo(cust.getCustomerNo());
				custSettle.setCustomerType(customerSettle.getCustomerType()!=null?customerSettle.getCustomerType():customerSettle.getCustomerType());
				custSettle.setAccountNo(customerSettle.getAccountNo()!=null?customerSettle.getAccountNo():customerSettle.getAccountNo());
				custSettle.setAccountName(customerSettle.getAccountName()!=null?customerSettle.getAccountName():customerSettle.getAccountName());
				custSettle.setOpenBankName(customerSettle.getOpenBankName()!=null?customerSettle.getOpenBankName():customerSettle.getOpenBankName());
				customerSettleService.update(custSettle, oper);
				
					

				
				//代付信息更新或者创建
				if (serviceConfigBean != null) {
					try {
						if (null == serviceConfigBean.getOwnerId()) {
							CustomerKey customerKey2 = customerKeyService.findBy(customer.getCustomerNo(), KeyType.RSA);
							customerKey2.setKeyType(KeyType.RSA);
							customerKey2.setCustomerNo(customer.getCustomerNo());
							customerKeyService.create(customerKey2, oper);
							String dpayPass = "";
							// 创建代付信息
							serviceConfigBean.setOwnerId(customer.getCustomerNo());
							serviceConfigBean.setValid("TRUE");
							serviceConfigBean.setPhone(customer.getPhoneNo());
							serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
							serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\r\n", ""));
							serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\n", ""));
							serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\n", ""));
							dpayPass = GengeratePassword.getExchangeCode().toString();
							serviceConfigBean.setOwnerRole(OwnerRole.CUSTOMER.toString());
							serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
							
							serviceConfigFacade.openDF(serviceConfigBean,oper);
						} else {// 更新代付信息
							ServiceConfigBean serviceConfig =serviceConfigFacade.query(serviceConfigBean.getOwnerId());
							//设置属性
							serviceConfig.setManualAudit(serviceConfigBean.getManualAudit()!=null?serviceConfigBean.getManualAudit():serviceConfig.getManualAudit());
							serviceConfig.setUsePhoneCheck(serviceConfigBean.getUsePhoneCheck()!=null?serviceConfigBean.getUsePhoneCheck():serviceConfig.getUsePhoneCheck());
							serviceConfig.setCustIp(serviceConfigBean.getCustIp()!=null?serviceConfigBean.getCustIp():serviceConfig.getCustIp());
							serviceConfig.setDomain(serviceConfigBean.getDomain()!=null?serviceConfigBean.getDomain():serviceConfig.getDomain());
							serviceConfig.setMaxAmount(serviceConfigBean.getMaxAmount()>0?serviceConfigBean.getMaxAmount():serviceConfig.getMaxAmount());
							serviceConfig.setMinAmount(serviceConfigBean.getMinAmount()>0?serviceConfigBean.getMinAmount():serviceConfig.getMinAmount());
							serviceConfig.setBossAudit(serviceConfigBean.getBossAudit());
							serviceConfig.setDayMaxAmount(serviceConfigBean.getDayMaxAmount());
							serviceConfig.setStartTime(serviceConfigBean.getStartTime());
							serviceConfig.setEndTime(serviceConfigBean.getEndTime());
							if(serviceConfigBean.getHolidayStatus().equals("TRUE")){
								serviceConfig.setHolidayStatus(serviceConfigBean.getHolidayStatus());
							}
							serviceConfigFacade.updateServiceConfigOnlyForAgentSystem(serviceConfig);
						}
					} catch (Exception e) {
						logger.info("createOrUpdate openDF serviceConfigBean.getOwnerId():" + customer.getCustomerNo());
					}
				}
				
				//代收信息更新或者创建
				if(receiveConfigInfoBean != null){
					try {
						if(null == receiveConfigInfoBean.getOwnerId()){
							CustomerKey customerKey2 = customerKeyService.findBy(customer.getCustomerNo(), KeyType.RSA);
							customerKey2.setKeyType(KeyType.RSA);
							customerKey2.setCustomerNo(customer.getCustomerNo());
							customerKeyService.create(customerKey2, oper);
							//创建代收信息
							receiveConfigInfoBean.setOwnerId(customer.getCustomerNo());
							receiveConfigInfoBean.setStatus(ReceiveConfigStatus.TRUE);
							receiveConfigInfoBean.setCreateTime(new Date());
							receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
							receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\r\n", ""));
							receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\n", ""));
							receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\n", ""));
							receiveConfigInfoBean.setOper(oper);
							receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
						}else {//修改代收信息
							ReceiveConfigInfoBean receiveConfigInfo = receiveQueryFacade.queryReceiveConfigBy(receiveConfigInfoBean.getOwnerId());
							//设置属性
							receiveConfigInfo.setCustIp(receiveConfigInfoBean.getCustIp());
							receiveConfigInfo.setDailyMaxAmount(receiveConfigInfoBean.getDailyMaxAmount());
							receiveConfigInfo.setDomain(receiveConfigInfoBean.getDomain());
							receiveConfigInfo.setSingleBatchMaxNum(receiveConfigInfoBean.getSingleBatchMaxNum());
							receiveConfigInfo.setSingleMaxAmount(receiveConfigInfoBean.getSingleMaxAmount());
							if(receiveConfigInfo.getStatus().equals("true")){
								receiveConfigInfo.setStatus(receiveConfigInfoBean.getStatus());
							}
							receiveQueryFacade.updateReceiveConfig(receiveConfigInfo);
						}
					} catch (Exception e) {
						logger.info("createOrUpdate openDF receiveConfigInfoBean.getOwnerId():" + customer.getCustomerNo());
					}
				}
				
				
				AccountQuery accountQuery = new AccountQuery();
				accountQuery.setUserNo(customer.getCustomerNo());
				accountQuery.setUserRole(UserRole.CUSTOMER);
				AccountQueryResponse accountResponse = accountQueryInterface.findAccountBy(accountQuery);
				if(accountResponse!=null&&accountResponse.getAccountBeans()!=null&&accountResponse.getAccountBeans().size()==1){
					AccountBean accountBean =accountResponse.getAccountBeans().get(0);
					
					AccountModify accountModify = new AccountModify();
					accountModify.setAccountNo(accountBean.getCode());
					accountModify.setUserNo(accountBean.getUserNo());
					accountModify.setCycle(cycle);
					accountModify.setAccountStatus(accountBean.getStatus());//注释掉查询时候是什么状态就什么状态,不能因为修改入账周期改变状态
					accountModify.setSystemCode(systemCode);
					accountModify.setSystemFlowId("OC" + customer.getCustomerNo());
					accountModify.setBussinessCode("UPDATE_ACCOUNT");
					accountModify.setRequestTime(new Date());
					accountModify.setOperator(oper);
					accountModify.setRemark("服务商系统商户修改入账周期");
					AccountModifyResponse accountModifyResponse = accountInterface.modifyAccount(accountModify);
					if (accountModifyResponse.getResult() == HandlerResult.FAILED) {
						throw new RuntimeException("customer No:" + customer.getCustomerNo() + " update account failed:" + accountModifyResponse + "");
					}
					logger.info("update account customerNo:" + accountModify.getUserNo() + " info:" + JsonUtils.toJsonString(accountModify));
				}else{
					throw new RuntimeException("没有查询到账户信息或账户信息存在多个");
				}
				
				//更新费率信息
				for (CustomerFee customerFee : customerFees) {
					if (customerFee!=null) {
						if(null!=customerFee.getId()){//原有的就更新
							if(null==customerFee.getFeeType()){
								customerFeeService.delete(customerFee, oper);
							}else{
								customerFeeService.update(customerFee, oper);
							}
						}else{//新增的就创建
							if (customerFee.getFeeType() == null || "".equals(customerFee.getFeeType())) {
								continue;
							}
							customerFee.setCreateTime(new Date());
							customerFee.setCustomerNo(customer.getCustomerNo());
							customerFee.setStatus(Status.TRUE);
							customerFeeService.create(customerFee, oper);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}
	
	private String getNowCustomerNo() {
		String maxCustomer = customerDao.getMaxCustomerNo();
		if (StringUtil.isNull(maxCustomer)) {
			return "C100000";
		}
		String customerMaxNo = Integer.parseInt(maxCustomer.replaceAll("[a-zA-Z]", "")) > 10000 ? maxCustomer : "100001";
		String customer = "C" + String.valueOf(Integer.parseInt(customerMaxNo.replaceAll("[a-zA-Z]", "")) + 1);
		return customer;
	}

	@Override
	public Customer findByCustNo(String custNo) {
		return customerDao.findByNo(custNo);
	}
	
	@Override
	public Map<String, String> checkCustomerName(String fullName, String shortName) {
		return customerDao.checkCustomerName(fullName, shortName);
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setCustomerCertDao(CustomerCertDao customerCertDao) {
		this.customerCertDao = customerCertDao;
	}

	public void setCustomerFeeDao(CustomerFeeDao customerFeeDao) {
		this.customerFeeDao = customerFeeDao;
	}

	public void setCustomerSettleDao(CustomerSettleDao customerSettleDao) {
		this.customerSettleDao = customerSettleDao;
	}

	public void setCustomerHistoryDao(CustomerHistoryDao customerHistoryDao) {
		this.customerHistoryDao = customerHistoryDao;
	}

	public void setCustomerCertHistoryDao(CustomerCertHistoryDao customerCertHistoryDao) {
		this.customerCertHistoryDao = customerCertHistoryDao;
	}

	public void setCustomerFeeHistoryDao(CustomerFeeHistoryDao customerFeeHistoryDao) {
		this.customerFeeHistoryDao = customerFeeHistoryDao;
	}

	public void setCustomerSettleHistoryDao(CustomerSettleHistoryDao customerSettleHistoryDao) {
		this.customerSettleHistoryDao = customerSettleHistoryDao;
	}

	@Override
	public List<CustomerHistory> findHistByCustNo(String custNo) {
		return customerHistoryDao.findByCustNo(custNo);
	}

	@Override
	public Customer findByFullName(String fullName) {
		return customerDao.findByFullName(fullName);
	}

	@Override
	public Customer findByShortName(String shortName) {
		return customerDao.findByShortName(shortName);
	}

	@Override
	public List<Customer> findByAgentNo(String AgentNo) {
		return customerDao.findByAgentNo(AgentNo);
	}
	
	@Override
	public void auditCustomerAction(com.yl.boss.entity.Customer customer,
			ServiceConfigBean serviceConfigBean, CustomerCert customerCert, com.yl.boss.entity.CustomerSettle customerSettle,
			List<com.yl.boss.entity.CustomerFee> customerFees, String oper,ReceiveConfigInfoBean receiveConfigInfoBean, String systemCode, int cycle,String msg,List<Shop> shopList) {
		try {
			Customer cust = customerDao.findByNo(customer.getCustomerNo());
			if (cust != null) {
				//更新商户基本信息
				cust.setFullName(customer.getFullName()!=null?customer.getFullName():cust.getFullName());
				cust.setShortName(customer.getShortName()!=null?customer.getShortName():cust.getShortName());
				cust.setCustomerType(customer.getCustomerType()!=null?customer.getCustomerType():cust.getCustomerType());
				cust.setPhoneNo(customer.getPhoneNo()!=null?customer.getPhoneNo():cust.getPhoneNo());
				cust.setLinkMan(customer.getLinkMan()!=null?customer.getLinkMan():cust.getLinkMan());
//				cust.setIdCard(customer.getIdCard()!=null?customer.getIdCard():cust.getIdCard());
//				cust.setEMail(customer.getEMail()!=null?customer.getEMail():cust.getEMail());
				cust.setCautionMoney(customer.getCautionMoney()>0?customer.getCautionMoney():cust.getCautionMoney());
				cust.setAddr(customer.getAddr()!=null?customer.getAddr():cust.getAddr());
				cust.setStatus(customer.getStatus());
//				cust.setProvince(customer.getProvince());
//				cust.setCity(customer.getCity());
				cust.setOrganization(customer.getOrganization());
				customerDao.update(cust);
				CustomerHistory customerHistory = new CustomerHistory(cust, oper);
				customerHistory.setMsg(msg);
				customerHistoryDao.create(customerHistory);
				
				
				// 短信通知到服务商
				if(cust.getStatus().name().equals("AGENT_REFUSE") && cust.getAgentNo() != null){
					SmsUtils.sendCustom(String.format(Constant.AGENT_AGENT_REFUSE, customer.getCustomerNo(), msg), cust.getPhoneNo());
				}
				
				
				if(customer.getStatus() == CustomerStatus.AUDIT){
					//更新商户证件信息
					
					if(customerCert != null){
						CustomerCert custCert= customerCertService.findByCustNo(cust.getCustomerNo());
						custCert.setValidStartTime(customerCert.getValidStartTime()!=null?customerCert.getValidStartTime():custCert.getValidStartTime());
						custCert.setValidEndTime(customerCert.getValidEndTime()!=null?customerCert.getValidEndTime():custCert.getValidEndTime());
						custCert.setBusinessScope(customerCert.getBusinessScope()!=null?customerCert.getBusinessScope():custCert.getBusinessScope());
						custCert.setBusinessAddress(customerCert.getBusinessAddress()!=null?customerCert.getBusinessAddress():custCert.getBusinessAddress());
						custCert.setEnterpriseCode(customerCert.getEnterpriseCode()!=null?customerCert.getEnterpriseCode():custCert.getEnterpriseCode());
						custCert.setEnterpriseUrl(customerCert.getEnterpriseUrl()!=null?customerCert.getEnterpriseUrl():custCert.getEnterpriseUrl());
						custCert.setIcp(customerCert.getIcp()!=null?customerCert.getIcp():custCert.getIcp());
						custCert.setConsumerPhone(customerCert.getConsumerPhone()!=null?customerCert.getConsumerPhone():custCert.getConsumerPhone());
						custCert.setLegalPerson(customerCert.getLegalPerson()!=null?customerCert.getLegalPerson():custCert.getLegalPerson());
						
						custCert.setAttachment(customerCert.getAttachment()!=null?customerCert.getAttachment():custCert.getAttachment());
						custCert.setBusiLiceCert(customerCert.getBusiLiceCert()!=null?customerCert.getBusiLiceCert():custCert.getBusiLiceCert());
						custCert.setIdCard(customerCert.getIdCard()!=null?customerCert.getIdCard():custCert.getIdCard());
						custCert.setOpenBankAccCert(customerCert.getOpenBankAccCert()!=null?customerCert.getOpenBankAccCert():custCert.getOpenBankAccCert());
						custCert.setOrganizationCert(customerCert.getOrganizationCert()!=null?customerCert.getOrganizationCert():custCert.getOrganizationCert());
						custCert.setTaxRegCert(customerCert.getTaxRegCert()!=null?customerCert.getTaxRegCert():custCert.getTaxRegCert());
						customerCertService.update(custCert, oper);
					}
					
					//更新商户结算卡信息
					CustomerSettle custSettle = customerSettleService.findByCustNo(cust.getCustomerNo());
					custSettle.setCustomerType(customerSettle.getCustomerType()!=null?customerSettle.getCustomerType():customerSettle.getCustomerType());
					custSettle.setAccountNo(customerSettle.getAccountNo()!=null?customerSettle.getAccountNo():customerSettle.getAccountNo());
					custSettle.setAccountName(customerSettle.getAccountName()!=null?customerSettle.getAccountName():customerSettle.getAccountName());
					custSettle.setOpenBankName(customerSettle.getOpenBankName()!=null?customerSettle.getOpenBankName():customerSettle.getOpenBankName());
					customerSettleService.update(custSettle, oper);
					
					// 生成RSA秘钥
					CustomerKey customerKey2 = customerKeyService.findBy(customer.getCustomerNo(), KeyType.RSA);
					
					//代付信息更新或者创建
					if (serviceConfigBean != null && cust.getCustomerNo() != null) {
						try {
							ServiceConfigBean serviceConfig =serviceConfigFacade.query(cust.getCustomerNo());
							if (serviceConfig == null) {
								String dpayPass = "";
								// 创建代付信息
								serviceConfigBean.setOwnerId(customer.getCustomerNo());
								serviceConfigBean.setValid("TRUE");
								serviceConfigBean.setPhone(customer.getPhoneNo());
								serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
								serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\r\n", ""));
								serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\n", ""));
								serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\n", ""));
								dpayPass = GengeratePassword.getExchangeCode().toString();
								serviceConfigBean.setOwnerRole(OwnerRole.CUSTOMER.toString());
								serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
								
								serviceConfigFacade.openDF(serviceConfigBean,oper);
							} else {
								//设置属性
								serviceConfig.setManualAudit(serviceConfigBean.getManualAudit()!=null?serviceConfigBean.getManualAudit():serviceConfig.getManualAudit());
								serviceConfig.setUsePhoneCheck(serviceConfigBean.getUsePhoneCheck()!=null?serviceConfigBean.getUsePhoneCheck():serviceConfig.getUsePhoneCheck());
								serviceConfig.setCustIp(serviceConfigBean.getCustIp()!=null?serviceConfigBean.getCustIp():serviceConfig.getCustIp());
								serviceConfig.setDomain(serviceConfigBean.getDomain()!=null?serviceConfigBean.getDomain():serviceConfig.getDomain());
								serviceConfig.setMaxAmount(serviceConfigBean.getMaxAmount()>0?serviceConfigBean.getMaxAmount():serviceConfig.getMaxAmount());
								serviceConfig.setMinAmount(serviceConfigBean.getMinAmount()>0?serviceConfigBean.getMinAmount():serviceConfig.getMinAmount());
								serviceConfig.setBossAudit(serviceConfigBean.getBossAudit());
								serviceConfig.setDayMaxAmount(serviceConfigBean.getDayMaxAmount());
								serviceConfig.setStartTime(serviceConfigBean.getStartTime());
								serviceConfig.setEndTime(serviceConfigBean.getEndTime());
								if(serviceConfigBean.getHolidayStatus().equals("TRUE")){
									serviceConfig.setHolidayStatus(serviceConfigBean.getHolidayStatus());
								}
								serviceConfigFacade.updateServiceConfigOnlyForAgentSystem(serviceConfig);
							}
						} catch (Exception e) {
							logger.info("createOrUpdate openDF serviceConfigBean.getOwnerId():" + customer.getCustomerNo());
						}
					}
					
					//代收信息更新或者创建
					if(receiveConfigInfoBean != null && cust.getCustomerNo() != null){
						try {
							ReceiveConfigInfoBean receiveConfigInfo = receiveQueryFacade.queryReceiveConfigBy(cust.getCustomerNo());
							if(null == receiveConfigInfo){
								//创建代收信息
								receiveConfigInfoBean.setOwnerId(customer.getCustomerNo());
								receiveConfigInfoBean.setStatus(ReceiveConfigStatus.TRUE);
								receiveConfigInfoBean.setCreateTime(new Date());
								receiveConfigInfoBean.setPrivateCer(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
								receiveConfigInfoBean.setPublicCer(customerKey2.getKey().replaceAll("\r\n", ""));
								serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\n", ""));
								serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\n", ""));
								receiveConfigInfoBean.setOper(oper);
								receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
							}else {//修改代收信息
								//设置属性
								receiveConfigInfo.setCustIp(receiveConfigInfoBean.getCustIp());
								receiveConfigInfo.setDailyMaxAmount(receiveConfigInfoBean.getDailyMaxAmount());
								receiveConfigInfo.setDomain(receiveConfigInfoBean.getDomain());
								receiveConfigInfo.setSingleBatchMaxNum(receiveConfigInfoBean.getSingleBatchMaxNum());
								receiveConfigInfo.setSingleMaxAmount(receiveConfigInfoBean.getSingleMaxAmount());
								if(receiveConfigInfo.getStatus().equals("true")){
									receiveConfigInfo.setStatus(receiveConfigInfoBean.getStatus());
								}
								receiveQueryFacade.updateReceiveConfig(receiveConfigInfo);
							}
						} catch (Exception e) {
							logger.info("createOrUpdate openDF receiveConfigInfoBean.getOwnerId():" + customer.getCustomerNo());
						}
					}
					
					
					AccountQuery accountQuery = new AccountQuery();
					accountQuery.setUserNo(customer.getCustomerNo());
					accountQuery.setUserRole(UserRole.CUSTOMER);
					AccountQueryResponse accountResponse = accountQueryInterface.findAccountBy(accountQuery);
					if(accountResponse!=null&&accountResponse.getAccountBeans()!=null&&accountResponse.getAccountBeans().size()==1){
						AccountBean accountBean =accountResponse.getAccountBeans().get(0);
						
						AccountModify accountModify = new AccountModify();
						accountModify.setAccountNo(accountBean.getCode());
						accountModify.setUserNo(accountBean.getUserNo());
						accountModify.setCycle(cycle);
						accountModify.setAccountStatus(accountBean.getStatus());//注释掉查询时候是什么状态就什么状态,不能因为修改入账周期改变状态
						accountModify.setSystemCode(systemCode);
						accountModify.setSystemFlowId("OC" + customer.getCustomerNo());
						accountModify.setBussinessCode("UPDATE_ACCOUNT");
						accountModify.setRequestTime(new Date());
						accountModify.setOperator(oper);
						accountModify.setRemark("服务商系统商户修改入账周期");
						AccountModifyResponse accountModifyResponse = accountInterface.modifyAccount(accountModify);
						if (accountModifyResponse.getResult() == HandlerResult.FAILED) {
							throw new RuntimeException("customer No:" + customer.getCustomerNo() + " update account failed:" + accountModifyResponse + "");
						}
						logger.info("update account customerNo:" + accountModify.getUserNo() + " info:" + JsonUtils.toJsonString(accountModify));
					}else{
						throw new RuntimeException("没有查询到账户信息或账户信息存在多个");
					}
					
					//更新费率信息
					for (CustomerFee customerFee : customerFees) {
						if (customerFee!=null) {
							logger.info("代理商审核，商户费率：{}", JsonUtils.toJsonString(customerFee));
							if(null!=customerFee.getId()){//原有的就更新
								if(null==customerFee.getFeeType()){
									customerFeeService.delete(customerFee, oper);
								}else{
									customerFeeService.update(customerFee, oper);
								}
							}else{//新增的就创建
								if (customerFee.getFeeType() == null || "".equals(customerFee.getFeeType())) {
									continue;
								}
								customerFee.setCreateTime(new Date());
								customerFee.setCustomerNo(customer.getCustomerNo());
								customerFee.setStatus(Status.TRUE);
								customerFeeService.create(customerFee, oper);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		
	}
	
	@Override
	public String findShortNameByCustomerNo(String customerNo) {
		return customerDao.findShortNameByCustomerNo(customerNo);
	}
	
	@Override
    public String queryAgentNoByCustomerNo(String customerNo) {
        return customerDao.queryAgentNoByCustomerNo(customerNo);
    }
	
	/**
	 * 
	 * @Description 快速入网
	 * @param customer
	 * @param password
	 * @date 2017年9月15日
	 */
	public void createQuick(Customer customer, String password) {
		customer.setCreateTime(new Date());
		String customerNo = this.getNowCustomerNo();
		customer.setCustomerNo(customerNo);
		customer.setUseCustomPermission(YesNo.N);
		customer.setFullName(customer.getPhoneNo());
		customer.setShortName(customer.getPhoneNo());
		customer.setStatus(CustomerStatus.PRE_TRUE);
		customer.setSource(null == customer.getAgentNo()?CustomerSource.APP_QUICK:CustomerSource.AGENT_SHARE);
		customerDao.create(customer);
		logger.info("create customer customerNo:" + customer.getCustomerNo() + " info:" + JsonUtils.toJsonString(customer));

		CustomerHistory customerHistory = new CustomerHistory(customer, "SYSTEM");
		customerHistoryDao.create(customerHistory);

		// 创建商户操作员
		CustOperator operator = new CustOperator();
		operator.setPassword(password);
		operator.setRealname(customer.getFullName());
		operator.setUsername(customer.getPhoneNo());
		operator.setStatus(com.yl.customer.api.enums.Status.TRUE);
		operator.setCustomerNo(customer.getCustomerNo());
		operator.setRoleId(Long.valueOf(1));
		custOperInterface.create(operator);
		logger.info("create operator customerNo:" + operator.getCustomerNo() + " info:" + JsonUtils.toJsonString(operator));
		
		try {
			SmsUtils.sendCustom(String.format(Constant.SMS_QUICK_CUST_OPEN, customer.getFullName(),customer.getPhoneNo()), customer.getPhoneNo());
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 
	 * @Description 快捷入网提交结算信息
	 * @param customerNo
	 * @param customerCert
	 * @param customerSettle
	 * @param oper
	 * @param systemCode
	 * @date 2017年9月16日
	 */
	public void createSettleInfo(String customerNo, CustomerCert customerCert, CustomerSettle customerSettle, String idCard, String oper, String systemCode) {
		
		Customer customer = this.findByCustNo(customerNo);
		
		customer.setStatus(null == customer.getAgentNo() ? CustomerStatus.AUDIT : CustomerStatus.AGENT_AUDIT);
		customer.setIdCard(idCard);
		customer.setCustomerType(CustomerType.INDIVIDUAL);
		customer.setMcc(DEFAULT_MCC);
		
		
		
		customerCert.setCreateTime(new Date());
		customerCert.setCustomerNo(customerNo);
		customerCertDao.create(customerCert);
		logger.info("create customerCert customerNo:" + customerCert.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerCert));
		
		CustomerCertHistory customerCertHistorty = new CustomerCertHistory(customerCert, oper);
		customerCertHistoryDao.create(customerCertHistorty);
		
		
		customerSettle.setCreateTime(new Date());
		customerSettle.setCustomerNo(customerNo);
		customerSettleDao.create(customerSettle);
		logger.info("create customerSettle customerNo:" + customerSettle.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerSettle));
		
		CustomerSettleHistory customerSettleHistory = new CustomerSettleHistory(customerSettle, oper);
		customerSettleHistoryDao.create(customerSettleHistory);
		
		
		// 创建商户账户
		AccountOpen accountOpen = new AccountOpen();
		accountOpen.setOperator(oper);
		accountOpen.setAccountType(AccountType.CASH);
		accountOpen.setBussinessCode("OPEN_ACCOUNT");
		accountOpen.setCurrency(Currency.RMB);
		accountOpen.setStatus(AccountStatus.NORMAL);
		accountOpen.setSystemCode(systemCode);
		accountOpen.setSystemFlowId("OC" + customer.getCustomerNo());
		accountOpen.setRequestTime(new Date());
		accountOpen.setUserNo(customer.getCustomerNo());
		accountOpen.setUserRole(UserRole.CUSTOMER);
		accountOpen.setCycle(0);
		AccountOpenResponse accountOpenResponse = accountInterface.openAccount(accountOpen);
		if (accountOpenResponse.getResult() == HandlerResult.FAILED) {
			throw new RuntimeException("customer No:" + customer.getCustomerNo() + " open account failed:" + accountOpenResponse + "");
		}
		logger.info("create account customerNo:" + accountOpen.getUserNo() + " info:" + JsonUtils.toJsonString(accountOpen));
		// 生成费率信息、已经相关配置
		createFee(customer);
		// 更新商户相关信息
		this.update(customer);
		
		CustomerHistory customerHistory = new CustomerHistory(customer, "SYSTEM");
		customerHistoryDao.create(customerHistory);
	}
	/**
	 * 
	 * @Description 快捷入网，提交结算卡信息 生成费率信息、已经相关配置
	 * @param customer
	 * @date 2017年9月18日
	 */
	private void createFee(Customer customer) {
		String customerNo = customer.getCustomerNo();
		String oper = "SYSTEM";
		for (String product : QUICK_OPEN_FIXED_PRODUCTS) {
			if (org.apache.commons.lang3.StringUtils.isNotBlank(customer.getAgentNo())) {
				AgentFee agentfee = agentFeeService.findBy(customer.getAgentNo(), com.yl.boss.api.enums.ProductType.valueOf(product));
				if (agentfee == null) {
					continue;
				}
			}
			CustomerFee customerFee = new CustomerFee();
			customerFee.setCustomerNo(customerNo);
			customerFee.setProductType(ProductType.valueOf(product));
			customerFee.setFeeType(FeeType.FIXED);
			if (ProductType.valueOf(product) == ProductType.BINDCARD_AUTH) {
				customerFee.setFee(DEFAULT_BINDCARD_AUTH_FEE);
			} else {
				customerFee.setFee(DEFAULT_FIXED_FEE);
			}
			customerFee.setCreateTime(new Date());
			customerFee.setStatus(Status.TRUE);
			customerFeeDao.create(customerFee);
			logger.info("create customerFee customerNo:" + customerFee.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerFee));
			CustomerFeeHistory customerFeeHistorty = new CustomerFeeHistory(customerFee, oper);
			customerFeeHistoryDao.create(customerFeeHistorty);
			
		}
		for (String product : QUICK_OPEN_RATIO_PRODUCTS) {
			if (org.apache.commons.lang3.StringUtils.isNotBlank(customer.getAgentNo())) {
				AgentFee agentfee = agentFeeService.findBy(customer.getAgentNo(), com.yl.boss.api.enums.ProductType.valueOf(product));
				if (agentfee == null) {
					continue;
				}
			}
			CustomerFee customerFee = new CustomerFee();
			customerFee.setCustomerNo(customerNo);
			customerFee.setProductType(ProductType.valueOf(product));
			customerFee.setFeeType(FeeType.RATIO);
			customerFee.setFee(DEFAULT_RATIO_FEE);
			customerFee.setMinFee(DEFAULT_RATIO_MIN_FEE);
			customerFee.setMaxFee(DEFAULT_RATIO_MAX_FEE);
			customerFee.setCreateTime(new Date());
			customerFee.setStatus(Status.TRUE);
			customerFeeDao.create(customerFee);
			logger.info("create customerFee customerNo:" + customerFee.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerFee));
			
			CustomerFeeHistory customerFeeHistorty = new CustomerFeeHistory(customerFee, oper);
			customerFeeHistoryDao.create(customerFeeHistorty);
		}
		// 生成MD5秘钥
		CustomerKey customerKey = new CustomerKey();
		customerKey.setCustomerNo(customerNo);
		customerKey.setKeyType(KeyType.MD5);
		customerKeyService.create(customerKey, oper);
		// 生成RSA秘钥
		CustomerKey customerKey2 = new CustomerKey();
		customerKey2.setKeyType(KeyType.RSA);
		customerKey2.setCustomerNo(customerNo);
		customerKeyService.create(customerKey2, oper);

		ServiceConfigBean serviceConfigBean = new ServiceConfigBean();
		String dpayPass = "";
		// 创建代付信息
		if (serviceConfigBean != null) {
			try {
				serviceConfigBean.setOwnerId(customerNo);
				serviceConfigBean.setValid("TRUE");
				serviceConfigBean.setPhone(customer.getPhoneNo());
				serviceConfigBean.setManualAudit("TRUE");
				// Win 格式化
				serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\r\n", ""));
				serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\r\n", ""));
				//Linux 格式化
				serviceConfigBean.setPrivateKey(customerKey2.getPrivateKey().replaceAll("\n", ""));
				serviceConfigBean.setPublicKey(customerKey2.getKey().replaceAll("\n", ""));
				dpayPass = GengeratePassword.getExchangeCode().toString();
				serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
				serviceConfigBean.setOwnerRole(OwnerRole.CUSTOMER.toString());
				serviceConfigBean.setUsePhoneCheck("FALSE");
				serviceConfigBean.setMaxAmount(50000D);
				serviceConfigBean.setMinAmount(10D);
				serviceConfigBean.setHolidayStatus("TRUE");
				serviceConfigBean.setDayMaxAmount(500000D);
				serviceConfigBean.setFireType("MAN");
				serviceConfigBean.setStartTime(DAPY_START_TIME);
				serviceConfigBean.setEndTime(DAPY_END_TIME);
				serviceConfigFacade.openDF(serviceConfigBean,oper);
			} catch (Exception e) {
				logger.info("create openDF serviceConfigBean.getOwnerId():" + serviceConfigBean.getOwnerId());
			}
		}
		
	}
	
	@Override
	public void updateCreateSettle(String customerNo, CustomerCert customerCert, CustomerSettle customerSettle,
			String oper) {
		try {
			// 更改商户状态
			Customer customer = this.findByCustNo(customerNo);
			customer.setStatus(null == customer.getAgentNo()?CustomerStatus.AUDIT:CustomerStatus.AGENT_AUDIT);
			
			this.update(customer);
			
			CustomerHistory customerHistory = new CustomerHistory(customer, "SYSTEM");
			customerHistoryDao.create(customerHistory);
			// 更改证件信息
			CustomerCert cert = customerCertService.findByCustNo(customerNo);
			cert.setIdCard(customerCert.getIdCard());
			cert.setOrganizationCert(customerCert.getOrganizationCert());
			customerCertService.update(cert, oper);
			logger.info("update customerCert customerNo:" + customerCert.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerCert));
			// 更改结算卡信息
			CustomerSettle settle = customerSettleService.findByCustNo(customerNo);
			settle.setAccountName(customerSettle.getAccountName());
			settle.setAccountNo(customerSettle.getAccountNo());
			settle.setBankCode(customerSettle.getBankCode());
			settle.setCustomerType(customerSettle.getCustomerType());
			settle.setOpenBankName(customerSettle.getOpenBankName());
			customerSettleService.update(settle, oper);
			logger.info("update customerSettle customerNo:" + customerSettle.getCustomerNo() + " info:" + JsonUtils.toJsonString(customerSettle));
		} catch (Exception e) {
			logger.error("updateCreateSettle error! {}", e);
		}
	}
	
	@Override
	public String updateCustomerBaseInfo(Customer customer) {
		Customer cust = customerDao.findByNo(customer.getCustomerNo());
		if (cust != null) {
			cust.setFullName(customer.getFullName());
			cust.setShortName(customer.getShortName());
			cust.setAddr(customer.getAddr());
			cust.setProvince(customer.getProvince());
			cust.setCity(customer.getCity());
			cust.setEMail(customer.getEMail());
			customerDao.update(cust);
			
			CustomerHistory customerHistory = new CustomerHistory(customer, UPDATE_BASE_OPER);
			customerHistoryDao.create(customerHistory);
			return "SUCCESS";
		} else {
			return "ERROR";
		}
		
	}
	
	@Override
	public List<CustomerHistory> findUpdateBaseInfoByCustNo(String customerNo) {
		return customerHistoryDao.findByCustNoAndOper(customerNo, UPDATE_BASE_OPER);
	}

	public void setCustOperInterface(CustOperInterface custOperInterface) {
		this.custOperInterface = custOperInterface;
	}

	public void setAccountInterface(AccountInterface accountInterface) {
		this.accountInterface = accountInterface;
	}

	public void setOnlinePartnerRouterHessianService(OnlinePartnerRouterHessianService onlinePartnerRouterHessianService) {
		this.onlinePartnerRouterHessianService = onlinePartnerRouterHessianService;
	}

	public void setCustomerKeyService(CustomerKeyService customerKeyService) {
		this.customerKeyService = customerKeyService;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public void setCustomerCertService(CustomerCertService customerCertService) {
		this.customerCertService = customerCertService;
	}
	
	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}

	public void setCustomerFeeService(CustomerFeeService customerFeeService) {
		this.customerFeeService = customerFeeService;
	}

	public void setCustomerSettleService(CustomerSettleService customerSettleService) {
		this.customerSettleService = customerSettleService;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}
	public RequestFacade getRequestFacade() {
		return requestFacade;
	}
	public void setRequestFacade(RequestFacade requestFacade) {
		this.requestFacade = requestFacade;
	}
	public OnlineTradeQueryHessianService getOnlineTradeQueryHessianService() {
		return onlineTradeQueryHessianService;
	}
	public void setOnlineTradeQueryHessianService(OnlineTradeQueryHessianService onlineTradeQueryHessianService) {
		this.onlineTradeQueryHessianService = onlineTradeQueryHessianService;
	}

	public ShareProfitService getShareProfitService() {
		return shareProfitService;
	}
	
	public void setShareProfitService(ShareProfitService shareProfitService) {
		this.shareProfitService = shareProfitService;
	}

	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public ShopService getShopService() {
		return shopService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public SyncInfoDao getSyncInfoDao() {
		return syncInfoDao;
	}

	public void setSyncInfoDao(SyncInfoDao syncInfoDao) {
		this.syncInfoDao = syncInfoDao;
	}

	@Override
	public String findCustomerCardByNo(String customerNo) {
		return customerDao.findCustomerCardByNo(customerNo);
	}

	@Override
	public boolean customerNoBool(String customerNo) {
		return customerDao.customerNoBool(customerNo);
	}

	@Override
	public String fullNameByCustNo(String customerNo) {
		return customerDao.fullNameByCustNo(customerNo);
	}

	public AgentFeeService getAgentFeeService() {
		return agentFeeService;
	}

	public void setAgentFeeService(AgentFeeService agentFeeService) {
		this.agentFeeService = agentFeeService;
	}

	public AuthConfigHessianService getAuthConfigHessianService() {
		return authConfigHessianService;
	}

	public void setAuthConfigHessianService(AuthConfigHessianService authConfigHessianService) {
		this.authConfigHessianService = authConfigHessianService;
	}

	public PosOrderHessianService getPosOrder() {
		return posOrder;
	}

	public void setPosOrder(PosOrderHessianService posOrder) {
		this.posOrder = posOrder;
	}
}