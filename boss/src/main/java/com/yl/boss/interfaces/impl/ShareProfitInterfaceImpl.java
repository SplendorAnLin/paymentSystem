package com.yl.boss.interfaces.impl;


import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.request.AccountSpecialCompositeTally;
import com.yl.account.api.bean.request.SpecialTallyVoucher;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.*;
import com.yl.account.api.enums.Currency;
import com.yl.boss.api.bean.ShareProfitBean;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.enums.ProfitType;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.boss.entity.*;
import com.yl.boss.enums.ShareAccType;
import com.yl.boss.service.*;
import com.yl.boss.utils.PropertiesUtil;
import com.yl.boss.valuelist.ValueListRemoteAction;
import com.yl.receive.hessian.ReceiveFacade;
import net.mlw.vlh.ValueList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 分润远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public class ShareProfitInterfaceImpl implements ShareProfitInterface {
	
	private static final Logger logger = LoggerFactory.getLogger(ShareProfitInterfaceImpl.class);
	private ShareProfitService shareProfitService;
	private CustomerService customerService;
	private CustomerFeeService customerFeeService;
	private AgentFeeService agentFeeService;
	private AccountInterface accountInterface;
	private AccountQueryInterface accountQueryInterface;
	private ValueListRemoteAction valueListRemoteAction;	
	private AgentService agentService;

	@Override
	public void createShareProfit(ShareProfitBean shareProfitBean) {
		logger.info("商户交易 经营分析数据:{}", JsonUtils.toJsonString(shareProfitBean));
		ShareProfit shareProfit = new ShareProfit();
		double agentProfit = 0d;
		Customer customer = customerService.findByCustNo(shareProfitBean.getCustomerNo());
		CustomerFee customerFee = customerFeeService.findBy(shareProfitBean.getCustomerNo(), com.yl.boss.enums.ProductType.valueOf(shareProfitBean.getProductType().toString()));
		if ("QUICKPAY_UnionPay-120002".equals(shareProfit.getInterfaceCode())) {
			String[] feeInfo = getQuickPayFen(customerFee.getCustomerNo(), shareProfit.getInterfaceCode());
			customerFee.setFee(Double.valueOf(feeInfo[0]));
		}
		
		AgentFee agentFee = agentFeeService.findBy(customer.getAgentNo(), ProductType.valueOf(shareProfitBean.getProductType().toString()));
		if ("QUICKPAY_UnionPay-120002".equals(shareProfit.getInterfaceCode())) {
			String[] feeInfo = getQuickPayFen(agentFee.getAgentNo(), shareProfit.getInterfaceCode());
			agentFee.setFee(Double.valueOf(feeInfo[0]));
			agentFee.setMinFee(Double.valueOf(feeInfo[1]));
			agentFee.setMaxFee(Double.valueOf(feeInfo[2]));
		}
		
		Agent agent = agentService.findByNo(customer.getAgentNo());
		
		shareProfit.setAgentNo(customer.getAgentNo());
		shareProfit.setAmount(shareProfitBean.getAmount());
		shareProfit.setChannelCost(shareProfitBean.getChannelCost());
		shareProfit.setCustomerFee(customerFee.getFee());
		shareProfit.setCustomerNo(shareProfitBean.getCustomerNo());
		shareProfit.setFee(shareProfitBean.getFee());
		shareProfit.setInterfaceCode(shareProfitBean.getInterfaceCode());
		shareProfit.setOrderCode(shareProfitBean.getOrderCode());
		shareProfit.setOrderTime(shareProfitBean.getOrderTime());
		shareProfit.setSource(shareProfitBean.getSource());
		shareProfit.setProductType(shareProfitBean.getProductType());

		if(agentFee != null && agentFee.getStatus() != com.yl.agent.api.enums.Status.FALSE){
			shareProfit.setAgentFee(agentFee.getFee());
			shareProfit.setProfitRatio(agentFee.getProfitRatio());
			shareProfit.setProfitType(agentFee.getProfitType());
			shareProfit.setAgentLevel(agent.getAgentLevel());
			//所属代理商分润金额
			shareProfit.setAgentProfit(agentProfit(shareProfitBean.getFee(), shareProfitBean.getAmount(), agentFee.getFee(), 
					shareProfit.getProfitRatio(), shareProfit.getProfitType(), shareProfit.getOrderCode(),agentFee.getMinFee(),
					agentFee.getMaxFee()));
			agentProfit = shareProfit.getAgentProfit();
			
			// 所属代理商分润入账
			agentProfitAcc(shareProfit.getOrderCode(), customer.getAgentNo(), agentProfit, shareProfitBean.getOrderTime(), ShareAccType.SHATE_CREDIT);

			if(agent.getAgentLevel() == 2 || agent.getAgentLevel() == 3){
				// 直接代理商分润
				AgentFee directAgentFee = agentFeeService.findBy(agent.getParenId(), ProductType.valueOf(shareProfitBean.getProductType().toString()));
				if ("QUICKPAY_UnionPay-120002".equals(shareProfit.getInterfaceCode())) {
					String[] feeInfo = getQuickPayFen(directAgentFee.getAgentNo(), shareProfit.getInterfaceCode());
					directAgentFee.setFee(Double.valueOf(feeInfo[0]));
					directAgentFee.setMinFee(Double.valueOf(feeInfo[1]));
					directAgentFee.setMaxFee(Double.valueOf(feeInfo[2]));
				}
				
				logger.info("++++++直接代理商费率信息 : {}", JsonUtils.toJsonString(directAgentFee));
				Agent directAgent = agentService.findByNo(agent.getParenId());
				shareProfit.setDirectAgent(agent.getParenId());
				shareProfit.setDirectAgentFee(directAgentFee.getFee());
				shareProfit.setDirectProfitType(directAgentFee.getProfitType());
				shareProfit.setDirectProfitRatio(directAgentFee.getProfitRatio());
				double directAgentProfit = agentProfit(shareProfitBean.getFee(), shareProfitBean.getAmount(), directAgentFee.getFee(), 
						directAgentFee.getProfitRatio(), directAgentFee.getProfitType(), shareProfit.getOrderCode(),directAgentFee.getMinFee(),
						directAgentFee.getMaxFee());
				if(directAgentFee.getProfitType() == ProfitType.RATIO_PROFIT){
					directAgentProfit = AmountUtils.subtract(directAgentProfit, AmountUtils.round(AmountUtils.multiply(agentProfit, directAgentFee.getProfitRatio()), 2, RoundingMode.HALF_UP));
				}
				shareProfit.setDirectAgentProfit(directAgentProfit);
				// 直接代理商分润入账
				agentProfitAcc(shareProfit.getOrderCode(), agent.getParenId(), shareProfit.getDirectAgentProfit(), shareProfitBean.getOrderTime(), ShareAccType.DIRECT_SHATE_CREDIT);
				agentProfit = AmountUtils.add(agentProfit, shareProfit.getDirectAgentProfit());
				
				if(agent.getAgentLevel() == 3){
					// 间接代理商分润
					AgentFee indirectAgentFee = agentFeeService.findBy(directAgent.getParenId(), ProductType.valueOf(shareProfitBean.getProductType().toString()));
					if ("QUICKPAY_UnionPay-120002".equals(shareProfit.getInterfaceCode())) {
						String[] feeInfo = getQuickPayFen(indirectAgentFee.getAgentNo(), shareProfit.getInterfaceCode());
						indirectAgentFee.setFee(Double.valueOf(feeInfo[0]));
						indirectAgentFee.setMinFee(Double.valueOf(feeInfo[1]));
						indirectAgentFee.setMaxFee(Double.valueOf(feeInfo[2]));
					}
					
					logger.info("++++++间接代理商费率信息 : {}", JsonUtils.toJsonString(indirectAgentFee));
					shareProfit.setIndirectAgent(directAgent.getParenId());
					shareProfit.setIndirectAgentFee(indirectAgentFee.getFee());
					shareProfit.setIndirectProfitType(indirectAgentFee.getProfitType());
					shareProfit.setIndirectProfitRatio(indirectAgentFee.getProfitRatio());
					double indirectAgentProfit = agentProfit(shareProfitBean.getFee(), shareProfitBean.getAmount(), indirectAgentFee.getFee(), 
							indirectAgentFee.getProfitRatio(), indirectAgentFee.getProfitType(), shareProfit.getOrderCode(),indirectAgentFee.getMinFee(),
							indirectAgentFee.getMaxFee());
					if(indirectAgentFee.getProfitType() == ProfitType.RATIO_PROFIT){
						indirectAgentProfit = AmountUtils.subtract(indirectAgentProfit, AmountUtils.round(AmountUtils.multiply(AmountUtils.add(shareProfit.getAgentProfit(), shareProfit.getDirectAgentProfit()),indirectAgentFee.getProfitRatio()), 2, RoundingMode.HALF_UP));
					}
					shareProfit.setIndirectAgentProfit(indirectAgentProfit);
					// 间接代理商分润入账
					agentProfitAcc(shareProfit.getOrderCode(), directAgent.getParenId(), shareProfit.getIndirectAgentProfit(), shareProfitBean.getOrderTime(), ShareAccType.INDIRECT_SHATE_CREDIT);
					agentProfit = AmountUtils.add(agentProfit, shareProfit.getIndirectAgentProfit());
				}
			}
		}
		
		//平台毛利
		shareProfit.setPlatformProfit(platformProfit(shareProfitBean.getFee(), agentProfit, shareProfitBean.getChannelCost()));
		shareProfitService.create(shareProfit);
		
		logger.info("shareProfit : " + shareProfit);
	}
	
	/**
	 * 计算代理商分润
	 * @param fee 订单手续费
	 * @param amount 订单金额
	 * @param agentFee 代理商成本
	 * @param profitType 代理商分润类型	 固定值|比例值
	 * @param profitRatio 分润比例|分润金额
	 * @param orderCode 订单号
	 * @return
	 */
	private static double agentProfit(double fee, double amount, double agentFee, double profitRatio, ProfitType profitType, String orderCode,
			double agentMinFee, double agentMaxFee){
		if(profitType == ProfitType.FIXED_PROFIT){
			double agentProfit = profitRatio;
			if(profitRatio > fee){
				logger.warn("orderCode:"+orderCode+" agentProfit cost error！");
				return 0d;
			}
			if(profitRatio == fee){
				return 0d;
			}
			if(profitRatio < fee){
				return agentProfit;
			}
			
//			if(agentMinFee !=0 && agentProfit < agentMinFee){
//				return agentMinFee;
//			}
//			
//			if(agentMaxFee !=0 && agentProfit > agentMaxFee){
//				return agentMaxFee;
//			}
			
			return agentProfit;
		}
		
		if(profitType == ProfitType.RATIO_PROFIT){
			double agentCost = AmountUtils.round(AmountUtils.multiply(agentFee, amount), 2, RoundingMode.HALF_UP);
            if (agentMinFee != 0 && agentCost < agentMinFee) {
                agentCost = agentMinFee;
            }

            if (agentMaxFee != 0 && agentCost > agentMaxFee) {
                agentCost = agentMaxFee;
            }

			if(agentCost > fee){
				logger.warn("orderCode:"+orderCode+" agentProfit cost error！");
				return 0d;
			}
			if(agentCost == fee){
				return 0d;
			}
			if(agentCost < fee){
				return AmountUtils.round(AmountUtils.multiply(AmountUtils.subtract(fee, agentCost), profitRatio), 2, RoundingMode.HALF_UP);
			}
		}
		
		return 0d;
	}
	
	/**
	 * 计算平台毛利
	 * @param fee
	 * @param agentProfit
	 * @param channelCost
	 * @return
	 */
	private static double platformProfit(double fee, double agentProfit, double channelCost){
		double platformProfit = AmountUtils.round(AmountUtils.subtract(fee, agentProfit), 2, RoundingMode.HALF_UP);
		platformProfit = AmountUtils.round(AmountUtils.subtract(platformProfit, channelCost), 2, RoundingMode.HALF_UP);
		return platformProfit;
	}
	
	public Map<String, Object> query(String queryId, Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute(queryId, params).get(queryId);
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put(ReceiveFacade.VALUELIST_INFO, vl.getValueListInfo());
		map.put(ReceiveFacade.VALUELIST, vl.getList());
		return map;
	}
	
	@Override
	public Map<String, Object> weeklySales(String ownerId) throws ParseException {
		if (ownerId.subSequence(0, 1).equals("A")) {
			List<Customer> list = customerService.findByAgentNo(ownerId);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append("'" + list.get(i).getCustomerNo() + "',");
			}
			sb.deleteCharAt(sb.length() - 1);
			ownerId = sb.toString();
		} else {
			ownerId = "'" + ownerId + "'";
		}
		return shareProfitService.weeklySales(ownerId);
	}
	
	/**
	 * 查看合计
	 */
	@Override
	public String findByMapShareProfitInterfaces(Map<String, Object> params) {
		return shareProfitService.findByMapShareProfitInterfaces(params);
	}
	
	public ShareProfitService getShareProfitService() {
		return shareProfitService;
	}

	public void setShareProfitService(ShareProfitService shareProfitService) {
		this.shareProfitService = shareProfitService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CustomerFeeService getCustomerFeeService() {
		return customerFeeService;
	}

	public void setCustomerFeeService(CustomerFeeService customerFeeService) {
		this.customerFeeService = customerFeeService;
	}

	public AgentFeeService getAgentFeeService() {
		return agentFeeService;
	}

	public void setAgentFeeService(AgentFeeService agentFeeService) {
		this.agentFeeService = agentFeeService;
	}

	public AccountInterface getAccountInterface() {
		return accountInterface;
	}

	public void setAccountInterface(AccountInterface accountInterface) {
		this.accountInterface = accountInterface;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}

	@Override
	public String orderAmountSumByPayType(Date orderTimeStart, Date orderTimeEnd, String owner) {
		return shareProfitService.orderAmountSumByPayType(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public String counts(Date orderTimeStart, Date orderTimeEnd, String owner) {
		return shareProfitService.counts(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public String income(Date orderTimeStart, Date orderTimeEnd, String owner, int day) throws ParseException {
		return shareProfitService.income(orderTimeStart, orderTimeEnd, owner, day);
	}

	@Override
	public String orderSumNotRemit(Date startTime, Date endTime, String customerNo) {
		return shareProfitService.orderSumNotRemit(startTime,endTime,customerNo);
	}

	@Override
	public String initYear(String customerNo) {
		return shareProfitService.incomeYear(customerNo);
	}

	public AgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(AgentService agentService) {
		this.agentService = agentService;
	}

	@Override
	public void createShareProfitAgent(ShareProfitBean shareProfitBean) {
		logger.info("++++++代理商交易 经营分析数据:{}", JsonUtils.toJsonString(shareProfitBean));
		AgentFee agentFee = agentFeeService.findBy(shareProfitBean.getAgentNo(), ProductType.valueOf(shareProfitBean.getProductType().toString()));
		Agent agent = agentService.findByNo(shareProfitBean.getAgentNo());
		
		ShareProfit shareProfit = new ShareProfit();
		double agentProfit = 0d;
		
		shareProfit.setAgentNo(shareProfitBean.getAgentNo());
		shareProfit.setAmount(shareProfitBean.getAmount());
		shareProfit.setChannelCost(shareProfitBean.getChannelCost());
		shareProfit.setFee(shareProfitBean.getFee());
		shareProfit.setInterfaceCode(shareProfitBean.getInterfaceCode());
		shareProfit.setOrderCode(shareProfitBean.getOrderCode());
		shareProfit.setOrderTime(shareProfitBean.getOrderTime());
		shareProfit.setSource(shareProfitBean.getSource());
		shareProfit.setProductType(shareProfitBean.getProductType());
		shareProfit.setAgentFee(agentFee.getFee());
		
		if(agent.getAgentLevel() == 2 || agent.getAgentLevel() == 3){
			// 直接代理商分润
			AgentFee directAgentFee = agentFeeService.findBy(agent.getParenId(), ProductType.valueOf(shareProfitBean.getProductType().toString()));
			logger.info("++++++直接代理商费率信息 : {}", JsonUtils.toJsonString(directAgentFee));
			Agent directAgent = agentService.findByNo(agent.getParenId());
			shareProfit.setDirectAgent(agent.getParenId());
			shareProfit.setDirectAgentFee(directAgentFee.getFee());
			shareProfit.setDirectProfitType(directAgentFee.getProfitType());
			shareProfit.setDirectProfitRatio(directAgentFee.getProfitRatio());
			double directAgentProfit = agentProfit(shareProfitBean.getFee(), shareProfitBean.getAmount(), directAgentFee.getFee(), 
					directAgentFee.getProfitRatio(), directAgentFee.getProfitType(), shareProfit.getOrderCode(),directAgentFee.getMinFee(),
					directAgentFee.getMaxFee());
			shareProfit.setDirectAgentProfit(directAgentProfit);
			// 直接代理商分润入账
			agentProfitAcc(shareProfit.getOrderCode(), agent.getParenId(), shareProfit.getDirectAgentProfit(), shareProfitBean.getOrderTime(), ShareAccType.DIRECT_SHATE_CREDIT);
			agentProfit = AmountUtils.add(agentProfit, shareProfit.getDirectAgentProfit());
			
			if(agent.getAgentLevel() == 3){
				// 间接代理商分润
				AgentFee indirectAgentFee = agentFeeService.findBy(directAgent.getParenId(), ProductType.valueOf(shareProfitBean.getProductType().toString()));
				logger.info("++++++间接代理商费率信息 : {}", JsonUtils.toJsonString(indirectAgentFee));
				shareProfit.setIndirectAgent(directAgent.getParenId());
				shareProfit.setIndirectAgentFee(indirectAgentFee.getFee());
				shareProfit.setIndirectProfitType(indirectAgentFee.getProfitType());
				shareProfit.setIndirectProfitRatio(indirectAgentFee.getProfitRatio());
				double indirectAgentProfit = agentProfit(shareProfitBean.getFee(), shareProfitBean.getAmount(), indirectAgentFee.getFee(), 
						indirectAgentFee.getProfitRatio(), indirectAgentFee.getProfitType(), shareProfit.getOrderCode(),indirectAgentFee.getMinFee(),
						indirectAgentFee.getMaxFee());
				if(indirectAgentFee.getProfitType() == ProfitType.RATIO_PROFIT){
					indirectAgentProfit = AmountUtils.subtract(indirectAgentProfit, AmountUtils.round(AmountUtils.multiply(AmountUtils.add(shareProfit.getAgentProfit(), shareProfit.getDirectAgentProfit()),indirectAgentFee.getProfitRatio()), 2, RoundingMode.HALF_UP));
				}
				shareProfit.setIndirectAgentProfit(indirectAgentProfit);
				// 间接代理商分润入账
				agentProfitAcc(shareProfit.getOrderCode(), directAgent.getParenId(), shareProfit.getIndirectAgentProfit(), shareProfitBean.getOrderTime(), ShareAccType.INDIRECT_SHATE_CREDIT);
				agentProfit = AmountUtils.add(agentProfit, shareProfit.getIndirectAgentProfit());
			}
		}

		//平台毛利
		shareProfit.setPlatformProfit(platformProfit(shareProfitBean.getFee(), agentProfit, shareProfitBean.getChannelCost()));
		shareProfitService.create(shareProfit);
		
		logger.info("shareProfit : " + shareProfit);
	}
	
	/**
	 * 代理商分润入账
	 * @param orderCode
	 * @param userNo
	 * @param agentProfit
	 * @param orderTime
	 */
	private void agentProfitAcc(String orderCode, String userNo, double agentProfit, Date orderTime, ShareAccType shareAccType){
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode(shareAccType.name());
		accountBussinessInterfaceBean.setOperator("BOSS");
		accountBussinessInterfaceBean.setRemark("代理商分润入账");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("BOSS");
		accountBussinessInterfaceBean.setSystemFlowId(orderCode);

		AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

		// 交易金额
		SpecialTallyVoucher amount = new SpecialTallyVoucher();
		amount.setAccountType(AccountType.CASH);
		amount.setUserNo(userNo);
		amount.setUserRole(UserRole.AGENT);
		amount.setAmount(agentProfit);
		amount.setSymbol(FundSymbol.PLUS);
		amount.setCurrency(Currency.RMB);
		amount.setTransDate(orderTime);
		amount.setTransOrder(orderCode);
		
		AccountQuery accountQuery = new AccountQuery();
		accountQuery.setUserNo(userNo);
		accountQuery.setUserRole(UserRole.AGENT);
		accountQuery.setAccountType(AccountType.CASH);
		AccountQueryResponse accountQueryResponse = accountQueryInterface.findAccountBy(accountQuery);
		
		if (accountQueryResponse.getAccountBeans().get(0).getCycle() == 0) {
			amount.setWaitAccountDate(new Date());
		} else {
			amount.setWaitAccountDate(DateUtils.addDays(new Date(), accountQueryResponse.getAccountBeans().get(0).getCycle()));
		}

		List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		try {
			AccountCompositeTallyResponse accountCompositeTallyResponse = accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
			Status status = accountCompositeTallyResponse.getStatus();
			HandlerResult handleResult = accountCompositeTallyResponse.getResult();
			if (!(Status.SUCCESS.equals(status) && HandlerResult.SUCCESS.equals(handleResult))) throw new RuntimeException("代理商分润,入账失败!");
		} catch (Exception e) {
			//XXX 补入账处理
			logger.error("交易订单:"+orderCode+" 代理商分润,入账失败 ", e);
		}
	}
	
	public static void main(String[] args) {
		/**
		 * c1 0.04
		 * a3 0.005 80%
		 * a2 0.004 80%
		 * a1 0.003 80%
		 */
		double a1 = agentProfit(550d, 10000d, 0.04d, 0.5d, ProfitType.RATIO_PROFIT, "21312312323", 0d, 0d);
		System.out.println(a1);
		double a2 = (agentProfit(550d, 10000d, 0.006d, 0.5d, ProfitType.RATIO_PROFIT, "21312312323", 0d, 0d)-(a1*0.5d));
		System.out.println(a2);
		double a3 = agentProfit(550d, 10000d, 0.003d, 0.8d, ProfitType.RATIO_PROFIT, "21312312323", 0d, 0d);
		System.out.println(a3-((a2+a1)*0.8d));
	}

	@Override
	public Map<String, Object> findByOrderCode(String code) {
      
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
        	ShareProfit share=shareProfitService.findByOrderCode(code);
            BeanInfo beanInfo = Introspector.getBeanInfo(share.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                if (!key.equals("class")) {  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(share);  
                    map.put(key, value);  
                }  
            }  
        } catch (Exception e) {  
        	logger.error("Map转换出错：{}",e);
        }  
        return map; 
	}

	public String sumOnlineSales(String ownerId,Date startTime, Date endTime){
		return JsonUtils.toJsonString(shareProfitService.sumOnlineSales(ownerId,startTime,endTime));
	}

	@Override
	public String findOnlineSalesBy(String ownerId, int current, int showCount, String productType,
													 Date startTime, Date endTime) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> resList=new ArrayList<>();
		List<Map<String, Object>> sales=shareProfitService.findOnlineSales(ownerId, current, showCount, productType, startTime, endTime);
		if (sales!=null) {
			for (Map<String, Object> shareProfit : sales) {
				Map<String, Object> temp=new HashMap<>();
				temp.put("CODE", shareProfit.get("orderCode"));
				temp.put("TotalAmount", shareProfit.get("amount"));
				temp.put("successPayTime", time.format(new Date(Long.valueOf(shareProfit.get("orderTime").toString()))));
				temp.put("PayType", shareProfit.get("productType").toString());
				resList.add(temp);
			}
			if (resList.size()>0) {
				return JsonUtils.toJsonString(resList);
			}
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findOnlineSales(String ownerId, int current, int showCount, String productType,
			Date startTime, Date endTime) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> resList=new ArrayList<>();
		List<Map<String, Object>> sales=shareProfitService.findOnlineSales(ownerId, current, showCount, productType, startTime, endTime);
		if (sales!=null) {
			for (Map<String, Object> shareProfit : sales) {
				Map<String, Object> temp=new HashMap<>();
				temp.put("CODE", shareProfit.get("orderCode"));
				temp.put("TotalAmount", shareProfit.get("amount"));
				temp.put("successPayTime", time.format(new Date(Long.valueOf(shareProfit.get("orderTime").toString()))));
				temp.put("PayType", shareProfit.get("productType").toString());
				resList.add(temp);
			}
			logger.info("APP经营分析前4条交易:{}",JsonUtils.toJsonString(resList));
			if (resList.size()>0) {
				return resList;
			}
		}
		return null;
	}
	
	/**
	 * @Description 获取快捷有积分通道费率（临时方案）
	 * @param ownerId
	 * @param interfaceInfoCode
	 * @return
	 * @date 2018年1月24日
	 */
	private String[] getQuickPayFen(String ownerId, String interfaceInfoCode){
		/** 快捷积分费率*/
		Properties prop = PropertiesUtil.getProperties("quickPayFee.properties");
		Object info = prop.get(ownerId + "_" + interfaceInfoCode);
		info = prop.get(ownerId + "_" + interfaceInfoCode);
		if (info == null) {
			throw new RuntimeException("ownerId " + ownerId + "customerFee error");
		}
		String[] feeInfo = info.toString().split(",");
		return feeInfo;
	}
}