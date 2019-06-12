package com.yl.online.trade.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lefu.commons.utils.lang.AmountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.account.api.enums.UserRole;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.enums.AccountFailStatus;
import com.yl.online.model.enums.OrderClearingStatus;
import com.yl.online.model.enums.PaymentStatus;
import com.yl.online.model.model.AccountFailInfo;
import com.yl.online.model.model.Order;
import com.yl.online.trade.exception.BusinessException;
import com.yl.online.trade.quartz.context.QuartzContext;
import com.yl.online.trade.quartz.enums.QuartzStrategyType;
import com.yl.online.trade.quartz.model.VectorConstant;
import com.yl.online.trade.service.AccountFailInfoService;
import com.yl.online.trade.service.PaymentService;
import com.yl.online.trade.service.TradeOrderService;

/**
 * 交易系统重新入账(交易成功但入账失败的订单)
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月5日
 * @version V1.0.0
 */
public class AutoRetryAccountFacadeJob{

	private static final Logger logger = LoggerFactory.getLogger(AutoRetryAccountFacadeJob.class);

	/** 最大入账次数 */
	private static final int MAX_COUNT = 5;
	/** 一批次入帳量 */
	private static final int BATCH_REVERSE_COUNT = 200;
	/** 入账服务 */
	@Resource
	private AccountFailInfoService accountService;
	/** 订单服务 */
	@Resource
	private TradeOrderService tradeOrderService;
	/** 定时策略上下文 */
	@Resource
	private QuartzContext quartzContext;
	/** 账务服务 */
	@Resource
	private AccountInterface accountInterface;
	/** 账务查询服务 */
	@Resource
	private AccountQueryInterface accountQueryInterface;
	/** 支付记录服务 **/
	@Resource
	private PaymentService paymentService;
	
	public void execute() {
		logger.info("交易系统重新入账(交易成功但入账失败的订单)");
		// 查询需更新状态的交易数据
		List<AccountFailInfo> accountFacadeInfos = accountService.queryAccountFacadeInfo(MAX_COUNT, BATCH_REVERSE_COUNT);
		if (accountFacadeInfos != null) {
			Iterator<AccountFailInfo> iterator = accountFacadeInfos.iterator();
			while (iterator.hasNext()) {
				AccountFailInfo accountFacadeInfo = iterator.next();
				logger.info("入账失败的数据 : {}", accountFacadeInfo);
				Order order = null;
				try {
					String orderCode = accountFacadeInfo.getOrderCode();
					order = tradeOrderService.queryByCode(orderCode);
					List<Payment> payments = paymentService.findByOrderCode(orderCode);
					for (Payment payment : payments) {
						// 交易成功入账
						if (PaymentStatus.SUCCESS.equals(payment.getStatus())) {

							if(AmountUtils.geq(payment.getReceiverFee(), payment.getAmount())){
								// 更新入账状态
								accountFacadeInfo.setAccountCount(100);
								accountFacadeInfo.setAccountFacadeTime(new Date());
								accountService.modifyAccountInfo(accountFacadeInfo);
								continue;
							}

							AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
							accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
							accountBussinessInterfaceBean.setOperator("ONLINE");
							accountBussinessInterfaceBean.setRemark("在线入账");
							accountBussinessInterfaceBean.setRequestTime(new Date());
							accountBussinessInterfaceBean.setSystemCode("ONLINE");
							accountBussinessInterfaceBean.setSystemFlowId(order.getCode());

							AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

							// 交易金额
							SpecialTallyVoucher amount = new SpecialTallyVoucher();
							amount.setAccountType(AccountType.CASH);
							amount.setUserNo(order.getReceiver());
							amount.setUserRole(UserRole.CUSTOMER);
							amount.setAmount(payment.getAmount());
							amount.setSymbol(FundSymbol.PLUS);
							amount.setCurrency(Currency.RMB);
							amount.setFee(payment.getReceiverFee());
							amount.setFeeSymbol(FundSymbol.SUBTRACT);
							amount.setTransDate(order.getSuccessPayTime());
							amount.setTransOrder(order.getCode());
							Map<String, Object> queryParams = new HashMap<>();
							queryParams.put("userNo", order.getReceiver());
							queryParams.put("userRole", UserRole.CUSTOMER.name());
							queryParams.put("productNo", "ONLINE");
							
							AccountQuery accountQuery = new AccountQuery();
							accountQuery.setUserNo(order.getReceiver());
							accountQuery.setUserRole(UserRole.CUSTOMER);
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

							AccountCompositeTallyResponse accountCompositeTallyResponse = accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
							Status status = accountCompositeTallyResponse.getStatus();
							HandlerResult handleResult = accountCompositeTallyResponse.getResult();
							if (!(Status.SUCCESS.equals(status) && HandlerResult.SUCCESS.equals(handleResult))) throw new BusinessException("在线支付调用新账务系统,入账失败!");

							// 入账成功
							order.setClearingStatus(OrderClearingStatus.CLEARING_SUCCESS);
							order.setClearingFinishTime(new Date());
							// 更新订单清算状态
							tradeOrderService.modifyOrderClearingStatus(order);

							// 更新入账状态
							accountFacadeInfo.setAccountStatus(AccountFailStatus.SUCCESS);
							accountFacadeInfo.setAccountFacadeTime(new Date());
							accountService.modifyAccountInfo(accountFacadeInfo);
						}
					}
				} catch (Exception e) {
					logger.error("failed accounting !... [tradeOrder:{}]", order==null?null:JsonUtils.toJsonString(order), e);

					// 更新入账状态
					VectorConstant vectorConstant = new VectorConstant();
					vectorConstant.setVector(accountFacadeInfo.getAccountCount());
					vectorConstant.setBaseLine(2);
					Date nextFireTime = quartzContext.getNextFireTime(QuartzStrategyType.QuartzTriggerIncrease, vectorConstant);
					accountFacadeInfo.setNextFireTime(nextFireTime); // 下次触发时间
					accountService.modifyAccountInfo(accountFacadeInfo);
				}
			}
		}
	}
}