package com.yl.pay.pos.task;

import com.pay.acc.remote.beans.AccountFacadeBean;
import com.pay.acc.remote.beans.NotifyCreditStatusBean;
import com.pay.acc.remote.beans.TransCreditReturnBean;
import com.pay.acc.remote.beans.TransDebitReturnBean;
import com.pay.acc.remote.service.AccountServiceFacade;
import com.pay.common.util.AmountUtil;
import com.pay.common.util.DateUtil;
import com.pay.common.util.PropertyUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.dao.CardInfoDao;
import com.yl.pay.pos.dao.OrderDao;
import com.yl.pay.pos.dao.PaymentDao;
import com.yl.pay.pos.dao.ShopDao;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.enums.TransStatus;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.service.IOrderService;
import com.yl.sms.SmsUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Title: 订单日终管理
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */
public class OrderDailyManage {
	
	private static final Log log = LogFactory.getLog(OrderDailyManage.class);
	private OrderDao orderDao;
	private IOrderService orderService;
	private AccountServiceFacade accountServiceFacade;		//账户系统远程服务
	public PaymentDao paymentDao;
	public ShopDao shopDao;
	public CardInfoDao cardInfoDao;
	private static PropertyUtil propertyUtil =null;
	private static PropertyUtil propertyUtil2 =null;
	static{
		propertyUtil = PropertyUtil.getInstance("transConfig");
	}
	
	public void creditHandle(){
		log.info("--------------orderCreditHandle start on: " + DateUtil.formatDate(new Date()));
		
		Date start=DateUtil.getDate(new Date(), -15);
		start=DateUtil.parseToTodayDesignatedDate(start, "00:00:00");
		Date end=DateUtil.parseToTodayDesignatedDate(new Date(), "00:00:00");
		try {
			List<Order> orders=orderDao.findByCompleteTimeAndStatus(start, end);
			log.info("#+#+#+#+ orderCreditHandle allSuccess order size="+orders.size());
			if(orders!=null&&orders.size()!=0){
				//对订单进行分组，每組建立一個子線程
				Map<String,List<Order>> groups=new HashMap<String,List<Order>>(); 
				for(int i=0;i<orders.size();i++){
					Order order=orders.get(i);
					if(groups.containsKey(order.getCustomerNo())){
						groups.get(order.getCustomerNo()).add(order);
					}else{
						List<Order> orderList = new ArrayList<Order>();
						orderList.add(order);
						groups.put(order.getCustomerNo(), orderList);
					}
				}
				log.info("#+#+#+#+ executorService size"+groups.keySet().size()+" time="+DateUtil.formatDate(new Date()));
				ExecutorService executorService = Executors.newFixedThreadPool(10);
				List<Future<Integer>> futures = new ArrayList<Future<Integer>>(groups.keySet().size());
				
				for (String group : groups.keySet()) {
					Callable<Integer> callable = creditAccount(groups.get(group));
					futures.add(executorService.submit(callable));
				}
				executorService.shutdown();
				int failCount = 0;
				for(Future<Integer> future : futures){
					Integer res = future.get();
					if(res != null && res!=0){
						failCount=failCount+res.intValue();
					}
				}
				if(failCount > 0){
					log.info("------account credit fail order size="+failCount);
					SmsUtils.sendError("trans-core","", "POS交易系统定时入账异常，失败订单【" + failCount + "】，请处理！",propertyUtil.getProperty(Constant.BIZ_ALARM_PHONE_NO));
					accountServiceFacade.notifyCreditStatus(new NotifyCreditStatusBean("NO"));
				}else{
					accountServiceFacade.notifyCreditStatus(new NotifyCreditStatusBean("YES"));
				}
			}
		} catch (Exception e) {
			log.info("------account credit fail"+e);
			try {
				SmsUtils.sendError("trans-core","", "POS交易系统定时入账异常，紧急处理！",propertyUtil.getProperty(Constant.BIZ_ALARM_PHONE_NO));
			} catch (IOException e1) {
				log.info("短信发送失败", e1);
			}
		}
		log.info("--------------orderCreditHandle end on: " + DateUtil.formatDate(new Date()));
	}

	private Callable<Integer> creditAccount(final List<Order> orders){
		return new Callable<Integer>() {
			@Override
			public Integer call() {
				
				int countFailOrder = 0;
				try {
					for (Order order : orders) {
						ExtendPayBean extendPayBean=new ExtendPayBean();
						TransType transType = order.getTransType();
						if(TransType.PRE_AUTH.equals(transType)){
							transType=TransType.PRE_AUTH_COMP;
						}else if(TransType.PURCHASE.equals(transType)){
							transType=TransType.PURCHASE;
						}else if(TransType.CASH_RECHARGE_QUNCUN.equals(transType)){
							transType=TransType.CASH_RECHARGE_QUNCUN;
						}else if(TransType.OFFLINE_PURCHASE.equals(transType)){
							transType=TransType.OFFLINE_PURCHASE;
						}else{
							continue;
						}
						Payment sucPayment= paymentDao.findLastSourcePayment(order, TransStatus.SUCCESS, transType);
						if (sucPayment == null) {
							log.info("-------no success payment externalId="+ order.getExternalId());
							countFailOrder++;
							continue;
						} else {
							YesNo settleable = YesNo.Y;
							// 判断是否为待审核结算商户
							if(!TransType.CASH_RECHARGE_QUNCUN.equals(transType)){
//								if (riskServiceFacade.isExistCustomerNO(new CheckSettleTypeBean(order.getCustomerNo(),Constant.USER_ROLE_CUSTOMER))) {
//									settleable = YesNo.N;
//								}
							}
							TransCreditReturnBean acountReturnBean = null;
							TransDebitReturnBean transDebitReturnBean=null;
							try {
								if(TransType.CASH_RECHARGE_QUNCUN.equals(transType)){
									double creditAmount =order.getAmount();
									AccountFacadeBean accountBean = new AccountFacadeBean(order.getCustomerNo(),Constant.USER_ROLE_CUSTOMER, creditAmount,Constant.ACCOUNT_BIZ_TYPE_RECEIPT,order.getExternalId(), order.getCompleteTime(),settleable.name());
									transDebitReturnBean=accountServiceFacade.transDebit(accountBean);// 调用账户系统出账
								}else{
									double creditAmount = AmountUtil.sub(order.getAmount(),sucPayment.getCustomerFee());
									AccountFacadeBean accountBean = new AccountFacadeBean(order.getCustomerNo(),Constant.USER_ROLE_CUSTOMER, creditAmount,Constant.ACCOUNT_BIZ_TYPE_RECEIPT,order.getExternalId(), order.getCompleteTime(),settleable.name());
									acountReturnBean = accountServiceFacade.transCredit(accountBean); // 调用账户系统入账
								}
							} catch (Throwable e) {
								log.info("--------account credit or debit fail! externalId="+ order.getExternalId(), e);
								countFailOrder++;
								continue;
							}
							if(TransType.CASH_RECHARGE_QUNCUN.equals(transType)){
								if (transDebitReturnBean.getResult().equals("SUCCESS")) {
									order.setCreditStatus(YesNo.Y);
									order.setCreditTime(transDebitReturnBean.getAccountOperateTime());
									order=orderService.update(order);
									extendPayBean.setOrder(order);
								//	jmsService.sendOrderMsg(extendPayBean, dataSource,propertyUtil2.getProperty("topic").split(",")[1]);
								} else if (transDebitReturnBean.getResult().equals("ACC0004")) {
									continue;
								} else {
									log.info("--------account credit fail! externalId="+ order.getExternalId() + "--result="+ acountReturnBean.getResult());
									countFailOrder++;
								}
							}else{
								if (acountReturnBean.getResult().equals("SUCCESS")) {
									order.setCreditStatus(YesNo.Y);
									order.setCreditTime(acountReturnBean.getAccountOperateTime());
									order=orderService.update(order);
									extendPayBean.setOrder(order);
									//jmsService.sendOrderMsg(extendPayBean, dataSource,propertyUtil2.getProperty("topic").split(",")[1]);
								} else if (acountReturnBean.getResult().equals("ACC0004")) {
									continue;
								} else {
									log.info("--------account credit fail! externalId="+ order.getExternalId() + "--result="+ acountReturnBean.getResult());
									countFailOrder++;
								}
							}
						}
					}
				} catch (Throwable e) {
					log.info("--------account handle fail! ",e);
				}
				
				return countFailOrder;
			}
			
		};
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	public AccountServiceFacade getAccountServiceFacade() {
		return accountServiceFacade;
	}

	public void setAccountServiceFacade(AccountServiceFacade accountServiceFacade) {
		this.accountServiceFacade = accountServiceFacade;
	}

	public PaymentDao getPaymentDao() {
		return paymentDao;
	}

	public void setPaymentDao(PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	public ShopDao getShopDao() {
		return shopDao;
	}

	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	public CardInfoDao getCardInfoDao() {
		return cardInfoDao;
	}

	public void setCardInfoDao(CardInfoDao cardInfoDao) {
		this.cardInfoDao = cardInfoDao;
	}

	public static PropertyUtil getPropertyUtil() {
		return propertyUtil;
	}

	public static void setPropertyUtil(PropertyUtil propertyUtil) {
		OrderDailyManage.propertyUtil = propertyUtil;
	}

	public static PropertyUtil getPropertyUtil2() {
		return propertyUtil2;
	}

	public static void setPropertyUtil2(PropertyUtil propertyUtil2) {
		OrderDailyManage.propertyUtil2 = propertyUtil2;
	}
	
	
	
}
