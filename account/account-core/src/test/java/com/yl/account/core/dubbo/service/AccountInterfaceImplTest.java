package com.yl.account.core.dubbo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountBatchConsult;
import com.yl.account.api.bean.request.AccountCompositeTally;
import com.yl.account.api.bean.request.AccountConsult;
import com.yl.account.api.bean.request.AccountDelayTally;
import com.yl.account.api.bean.request.AccountFreeze;
import com.yl.account.api.bean.request.AccountModify;
import com.yl.account.api.bean.request.AccountOpen;
import com.yl.account.api.bean.request.AccountPreFreeze;
import com.yl.account.api.bean.request.AccountSpecialCompositeTally;
import com.yl.account.api.bean.request.AccountSpecialTransitVoucher;
import com.yl.account.api.bean.request.AccountThaw;
import com.yl.account.api.bean.request.AccountTransitVoucher;
import com.yl.account.api.bean.request.SpecialTallyVoucher;
import com.yl.account.api.bean.request.TallyVoucher;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.bean.response.AccountConsultResponse;
import com.yl.account.api.bean.response.AccountFreezeResponse;
import com.yl.account.api.bean.response.AccountModifyResponse;
import com.yl.account.api.bean.response.AccountOpenResponse;
import com.yl.account.api.bean.response.AccountPreFreezeResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.enums.AccountStatus;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.DelayType;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.TransitDebitSeq;
import com.yl.account.api.enums.UserRole;
import com.yl.account.core.BaseTest;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.service.AccountCreditReverseService;
import com.yl.account.model.AccountCreditReverse;

/**
 * 账务业务逻辑处理接口单元测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountInterfaceImplTest extends BaseTest {

	private static Logger logger = LoggerFactory.getLogger(AccountInterfaceImplTest.class);

	@Resource
	private AccountInterface accountInterface;
	@Resource
	private AccountCreditReverseService accountCreditReverseService;

	/**
	 * Test method for {@link com.yl.account.core.dubbo.service.AccountInterfaceImpl#credit(com.lefu.account.api.bean.request.AccountCreditRequest)}.
	 */
	@Test
	public void testCredit() {
		logger.info("调用账务入账接口【入可用余额】");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountCompositeTally accountCompositeTally = new AccountCompositeTally();

		// 交易金额
		TallyVoucher amount = new TallyVoucher();
		amount.setAccountNo("1037478391549");
		amount.setAmount(140);
		amount.setSymbol(FundSymbol.PLUS);
		amount.setCurrency(Currency.RMB);
		amount.setFee(10d);
		amount.setFeeSymbol(FundSymbol.SUBTRACT);
		amount.setTransDate(new Date());
		// String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransOrder("9e-81ea-6a55fb34bfad");
		// logger.info("业务系统提交的交易订单号：{}", 9e-81ea-6a55fb34bfad);
		amount.setWaitAccountDate(DateUtils.addDays(new Date(), 2));

		List<TallyVoucher> tallyVouchers = new ArrayList<TallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCompositeTallyResponse accountCompositeTallyResponse = accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
		logger.info("【入账返回接口信息】：{}", StringUtils.concatToSB(accountCompositeTallyResponse.getResult().name(), "-", accountCompositeTallyResponse.getStatus().name())
				.toString());
	}

	@Test
	public void testTransfer() {
		logger.info("转帐接口");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("TRANSFER");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountCompositeTally accountCompositeTally = new AccountCompositeTally();

		// 交易金额
		TallyVoucher in = new TallyVoucher(); // 入账
		in.setAccountNo("203631388317");
		in.setAmount(30);
		in.setSymbol(FundSymbol.PLUS);
		in.setCurrency(Currency.RMB);
		in.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		in.setTransOrder(transOrder);
		in.setWaitAccountDate(new Date());

		TallyVoucher out = new TallyVoucher(); // 出账
		out.setAccountNo("234-5fb5e3e6e21c");
		out.setAmount(30);
		out.setSymbol(FundSymbol.SUBTRACT);
		out.setCurrency(Currency.RMB);
		out.setTransDate(new Date());
		out.setTransOrder(transOrder);
		out.setWaitAccountDate(new Date());

		List<TallyVoucher> tallyVouchers = new ArrayList<TallyVoucher>();
		tallyVouchers.add(out);
		tallyVouchers.add(in);

		accountCompositeTally.setTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
	}

	@Test
	public void testTransitCredit() {
		logger.info("调用账务入账接口【入在途余额】");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1002");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountCompositeTally accountCompositeTally = new AccountCompositeTally();

		// 交易金额
		TallyVoucher amount = new TallyVoucher();
		amount.setAccountNo("1069963996648");
		amount.setAmount(10);
		amount.setSymbol(FundSymbol.PLUS);
		amount.setCurrency(Currency.RMB);
		amount.setFee(5d);
		amount.setFeeSymbol(FundSymbol.SUBTRACT);
		amount.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransOrder(transOrder);
		amount.setWaitAccountDate(DateUtils.addDays(new Date(), 1));

		List<TallyVoucher> tallyVouchers = new ArrayList<TallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
	}

	@Test
	public void testTransitCreditWaitDateIsNull() {
		logger.info("调用账务入账接口【入在途余额】，待入账日期为空，用来验证【延迟入账接口】");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountCompositeTally accountCompositeTally = new AccountCompositeTally();

		// 交易金额
		TallyVoucher amount = new TallyVoucher();
		amount.setAccountNo("234-5fb5e3e6e21c");
		amount.setAmount(80);
		amount.setSymbol(FundSymbol.PLUS);
		amount.setCurrency(Currency.RMB);
		amount.setFee(4d);
		amount.setFeeSymbol(FundSymbol.SUBTRACT);
		amount.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransOrder(transOrder);

		List<TallyVoucher> tallyVouchers = new ArrayList<TallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
	}

	/**
	 * Test method for {@link com.yl.account.core.dubbo.service.AccountInterfaceImpl#debit(com.lefu.account.api.bean.request.AccountDebitRequest)}.
	 */
	@Test
	public void testDebitBalanceExcludeFee() {
		logger.info("1.【出账操作】   出可用余额(不包含手续费)");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ADJUST");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		String transOrder = UUID.randomUUID().toString().substring(16);
		accountBussinessInterfaceBean.setSystemFlowId(transOrder);

		AccountCompositeTally accountCompositeTally = new AccountCompositeTally();

		// 交易金额
		TallyVoucher amount = new TallyVoucher();
		amount.setAccountNo("203631388357");
		amount.setAmount(3);
		amount.setSymbol(FundSymbol.SUBTRACT);
		amount.setCurrency(Currency.RMB);
		amount.setTransDate(new Date());
		amount.setTransOrder(transOrder);
		// amount.setWaitAccountDate(DateUtils.getMinTime(new Date()));

		List<TallyVoucher> tallyVouchers = new ArrayList<TallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCompositeTallyResponse accountDebitResponse = accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
		logger.info(" 1.出可用余额 返回信息：{},(不包含书续费)", accountDebitResponse);

	}

	@Test
	public void testDebitBalanceIncludeFee() {
		logger.info("2.【出账操作】   出可用余额(包含书续费)");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountCompositeTally accountCompositeTally = new AccountCompositeTally();

		// 交易金额
		TallyVoucher amount = new TallyVoucher();
		amount.setAccountNo("234-5fb5e3e6e21c");
		amount.setAmount(2);
		amount.setSymbol(FundSymbol.SUBTRACT);
		amount.setFee(1d);
		amount.setFeeSymbol(FundSymbol.PLUS);
		amount.setCurrency(Currency.RMB);
		amount.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransOrder(transOrder);
		amount.setWaitAccountDate(DateUtils.getMinTime(new Date()));

		List<TallyVoucher> tallyVouchers = new ArrayList<TallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCompositeTallyResponse accountDebitResponse = accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
		logger.info(" 2.出可用余额 返回信息：{},(包含书续费)", accountDebitResponse);
	}

	@Test
	public void testDebitTransitBalanceNotRevoke() {
		logger.info("3.【出账操作】   出在途（非撤销）,包含在手续费");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountCompositeTally accountCompositeTally = new AccountCompositeTally();

		// 交易金额
		AccountTransitVoucher amount = new AccountTransitVoucher();
		amount.setAccountNo("234-5fb5e3e6e21c");
		amount.setTransitBalance(15d);
		amount.setTransitFeeBalance(5d);
		amount.setTransitFeeFundSymbol(FundSymbol.PLUS);
		amount.setTransitDebitSeq(TransitDebitSeq.ASC);
		amount.setCurrency(Currency.RMB);
		amount.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransOrder(transOrder);
		// amount.setWaitAccountDate(DateUtils.formatterDate(new Date(), "yyyy-MM-dd"));

		List<TallyVoucher> tallyVouchers = new ArrayList<TallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCompositeTallyResponse accountDebitResponse = accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
		logger.info(" 3.出在途余额(非撤销) 返回信息：{}", accountDebitResponse);
	}

	@Test
	public void testDebitTransitBalanceIsRevoke() {
		logger.info("4.【出账操作】  ********************** 出在途（撤销）");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountCompositeTally accountCompositeTally = new AccountCompositeTally();

		// 交易金额
		AccountTransitVoucher amount = new AccountTransitVoucher();
		amount.setAccountNo("234-5fb5e3e6e21c");
		amount.setTransitBalance(20d);
		amount.setCurrency(Currency.RMB);
		amount.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransOrder(transOrder);
		amount.setWaitAccountDate(DateUtils.addDays(DateUtils.getMinTime(new Date()), 1));

		List<TallyVoucher> tallyVouchers = new ArrayList<TallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCompositeTallyResponse accountDebitResponse = accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
		logger.info("********************** 4.出在途余额(撤销) 返回信息：{}", accountDebitResponse);
	}

	/**
	 * Test method for {@link com.yl.account.core.dubbo.service.AccountInterfaceImpl#delayTally(com.lefu.account.api.bean.request.AccountDelayDebitRequest)}.
	 */
	@Test
	public void testDelayTally() {
		logger.info("【延迟入账】标记待入账时间后，在途中的交易额直接入到可用余额中");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("DELAY_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountDelayTally accountDelayCredit = new AccountDelayTally();
		accountDelayCredit.setAccountNo("234-5fb5e3e6e21c");
		accountDelayCredit.setOrigTransOrder("05-a5ab-d959fce6a739");
		accountDelayCredit.setOrigBussinessCode("ONLINE_CREDIT");
		accountDelayCredit.setDelayType(DelayType.CREDIT);
		accountDelayCredit.setWaitAccountDate(new Date());

		accountBussinessInterfaceBean.setTradeVoucher(accountDelayCredit);

		accountInterface.delayTally(accountBussinessInterfaceBean);
	}

	@Test
	public void testDelayTallyWaitDate() {
		logger.info("【延迟入账】标记待入账时间后，在途中的交易额直接入到在途金额中");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("DELAY_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountDelayTally accountDelayCredit = new AccountDelayTally();
		accountDelayCredit.setAccountNo("234-5fb5e3e6e21c");
		accountDelayCredit.setOrigTransOrder("67-85c8-653c03a42ec5");
		accountDelayCredit.setWaitAccountDate(DateUtils.addDays(DateUtils.getMinTime(new Date()), 1));

		accountBussinessInterfaceBean.setTradeVoucher(accountDelayCredit);

		accountInterface.delayTally(accountBussinessInterfaceBean);
	}

	@Test
	public void testDelayTallyDebitWaitDate() {
		logger.info("【延迟出账账】标记待入账时间后，再出账");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("DELAY_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountDelayTally accountDelayTally = new AccountDelayTally();
		accountDelayTally.setAccountNo("234-5fb5e3e6e21c");
		accountDelayTally.setDelayType(DelayType.DEBIT);
		accountDelayTally.setOrigTransOrder("81-9eae-94537e9e708f");
		accountDelayTally.setWaitAccountDate(new Date());

		accountBussinessInterfaceBean.setTradeVoucher(accountDelayTally);

		accountInterface.delayTally(accountBussinessInterfaceBean);
	}

	/**
	 * Test method for {@link com.yl.account.core.dubbo.service.AccountInterfaceImpl#freeze(com.lefu.account.api.bean.request.AccountFreezeRequest)}.
	 */
	@Test
	public void testFreeze() {
		logger.info("************** 发起冻结操作 ************** ");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("FREEZE");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountFreeze accountFreeze = new AccountFreeze();
		accountFreeze.setAccountNo("234-5fb5e3e6e21c");
		accountFreeze.setFreezeAmount(50);
		accountFreeze.setFreezeLimit(DateUtils.addMinutes(new Date(), 10));
		accountBussinessInterfaceBean.setTradeVoucher(accountFreeze);

		AccountFreezeResponse accountFreezeResponse = accountInterface.freeze(accountBussinessInterfaceBean);
		logger.info("冻结返回信息：{}", accountFreezeResponse);
	}

	@Test
	public void testPreFreeze() {
		logger.info("1.【预冻处理】************");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("PREFREEZE");
		accountBussinessInterfaceBean.setOperator("TESTER");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("ACCOUNT-CORE");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));
		accountBussinessInterfaceBean.setRemark("直清");
		AccountPreFreeze accountPreFreeze = new AccountPreFreeze();
		accountPreFreeze.setAccountNo("1578175946144");
		accountPreFreeze.setFreezeLimit(DateUtils.addHours(new Date(), 1));
		accountPreFreeze.setPreFreezeAmount(200);
		accountBussinessInterfaceBean.setTradeVoucher(accountPreFreeze);

		AccountPreFreezeResponse accountPreFreezeResponse = accountInterface.preFreeze(accountBussinessInterfaceBean);
		logger.info("预冻返回数据：{}", accountPreFreezeResponse);
	}

	@Test
	public void testThaw() {
		logger.info("发起解冻操作");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("FREEZE_THAW");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountThaw accountThaw = new AccountThaw();
		accountThaw.setAccountNo("234-5fb5e3e6e21c");
		accountThaw.setFreezeNo("b56-e7d1cf26b323");
		accountBussinessInterfaceBean.setTradeVoucher(accountThaw);

		accountInterface.thaw(accountBussinessInterfaceBean);
	}

	/**
	 * Test method for {@link com.yl.account.core.dubbo.service.AccountInterfaceImpl#openAccount(com.lefu.account.api.bean.request.AccountOpenRequest)}.
	 */
	@Test
	public void testOpenAccount() {
		logger.info("*****************[调用账务开户接口]*****************");
		AccountOpen accountOpen = new AccountOpen();
		accountOpen.setAccountType(AccountType.CASH);
		accountOpen.setBussinessCode("OPEN_ACCOUNT");
		accountOpen.setCurrency(Currency.RMB);
		accountOpen.setStatus(AccountStatus.NORMAL);
		accountOpen.setOperator("jay");
		accountOpen.setSystemCode("BOSS");
		accountOpen.setSystemFlowId("1234324324");
		accountOpen.setRequestTime(new Date());
		accountOpen.setUserNo("100100");
		accountOpen.setUserRole(UserRole.AGENT);
		accountOpen.setCycle(1);
		AccountOpenResponse accountOpenResponse = accountInterface.openAccount(accountOpen);
		logger.info("账户测试用例-开户操作返回数据：{}", accountOpenResponse);
	}

	/**
	 * Test method for {@link com.yl.account.core.dubbo.service.AccountInterfaceImpl#modifyAccount(com.lefu.account.api.bean.request.AccountModifyRequest)}.
	 */
	@Test
	public void testModifyAccount() {
		AccountModify accountModify = new AccountModify();
		accountModify.setAccountNo("1069963996648");
		accountModify.setUserNo("89757");
		accountModify.setBussinessCode("MODIFY_ACCOUNT");
		accountModify.setAccountStatus(AccountStatus.NORMAL);
		accountModify.setOperator("123");
		accountModify.setSystemCode("PAYIT");
		accountModify.setSystemFlowId(UUID.randomUUID().toString().substring(16));
		accountModify.setRequestTime(new Date());
		accountModify.setRemark("修改账户状态");
		AccountModifyResponse accountModifyResponse = accountInterface.modifyAccount(accountModify);
		logger.info("账户测试用例-修改操作返回数据：{}", accountModifyResponse);
	}

	@Test
	public void testConsult() {
		logger.info("发起请款操作");
		String uuid = UUID.randomUUID().toString().substring(18);
		System.out.println(uuid);
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("SETTLE_AA");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId("-aa2a-5f01a18ff9de");

		AccountConsult accountConsult = new AccountConsult();
		accountConsult.setFreezeNo("ff9-e8dc9ce02b65");
		accountConsult.setConsultAmount(50);
		accountBussinessInterfaceBean.setTradeVoucher(accountConsult);

		AccountConsultResponse acp = accountInterface.consult(accountBussinessInterfaceBean);
		logger.info("请款返回结果：{}", JsonUtils.toJsonString(acp));

	}

	@Test
	public void testBatchConsult() {
		logger.info("发起批量请款操作");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("CONSULT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountBatchConsult accountBatchConsult = new AccountBatchConsult();

		Map<String, AccountConsult> accountConsults = new LinkedHashMap<String, AccountConsult>();
		AccountConsult accountConsult = new AccountConsult();
		accountConsult.setConsultAmount(50);
		accountConsult.setFreezeNo("658-551aa30a9a31");
		accountConsults.put("SETTLE_AA", accountConsult);

		AccountConsult accountConsultB = new AccountConsult();
		accountConsultB.setConsultAmount(50);
		accountConsultB.setFreezeNo("658-551aa30a9a31");
		accountConsults.put("SETTLE_BB", accountConsultB);

		accountBatchConsult.setAccountConsults(accountConsults);
		accountBussinessInterfaceBean.setTradeVoucher(accountBatchConsult);

		logger.info("批量请款响应：{}", accountInterface.batchConsult(accountBussinessInterfaceBean));

	}

	@Test
	public void testSpecialCompositeTally() {
		logger.info("临时入账接口");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

		// 交易金额
		SpecialTallyVoucher amount = new SpecialTallyVoucher();
		// amount.setAccountNo("234-5fb5e3e6e21c");
		amount.setAccountType(AccountType.CASH);
		amount.setUserNo("89757");
		amount.setUserRole(UserRole.CUSTOMER);
		amount.setAmount(30);
		amount.setSymbol(FundSymbol.PLUS);
		amount.setCurrency(Currency.RMB);
		amount.setFee(10d);
		amount.setFeeSymbol(FundSymbol.SUBTRACT);
		amount.setTransDate(DateUtils.addDays(new Date(),3));
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransOrder(transOrder);
		amount.setWaitAccountDate(DateUtils.addDays(new Date(),3));

		List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
	}

	@Test
	public void testSpecialDebitCompositeTally() {
		logger.info("临时出账接口");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

		// 交易金额
		SpecialTallyVoucher amount = new SpecialTallyVoucher();
		// amount.setAccountNo("234-5fb5e3e6e21c");
		amount.setAccountType(AccountType.CASH);
		amount.setUserNo("89757");
		amount.setUserRole(UserRole.CUSTOMER);
		amount.setAmount(230);
		amount.setSymbol(FundSymbol.SUBTRACT);
		amount.setCurrency(Currency.RMB);
		amount.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransOrder(transOrder);
		amount.setWaitAccountDate(new Date());

		List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
	}

	@Test
	public void testSpecialTransitDebitCompositeTally() {
		logger.info("临时出账接口-在途【撤销】");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("SETTLE_DEBIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

		// 交易金额
		AccountSpecialTransitVoucher amount = new AccountSpecialTransitVoucher();
		amount.setAccountType(AccountType.CASH);
		amount.setUserNo("8975723");
		amount.setUserRole(UserRole.CUSTOMER);
		amount.setCurrency(Currency.RMB);
		amount.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransitBalance(90d);
		amount.setTransitFeeBalance(3d);
		amount.setTransitFeeFundSymbol(FundSymbol.PLUS);
		amount.setTransOrder(transOrder);
		amount.setWaitAccountDate(DateUtils.addDays(new Date(), 1));

		List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
	}

	@Test
	public void testSpecialUnTransitDebitCompositeTally() {
		logger.info("临时出账接口-在途【非撤销】");
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("SETTLE_DEBIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

		// 交易金额
		AccountSpecialTransitVoucher amount = new AccountSpecialTransitVoucher();
		amount.setAccountType(AccountType.CASH);
		amount.setUserNo("8975723");
		amount.setUserRole(UserRole.CUSTOMER);
		amount.setCurrency(Currency.RMB);
		amount.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransitBalance(90d);
		amount.setTransitDebitSeq(TransitDebitSeq.DESC);
		amount.setTransitFeeBalance(3d);
		amount.setTransitFeeFundSymbol(FundSymbol.PLUS);
		amount.setTransOrder(transOrder);

		List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
	}

	@Resource
	private Validator validator;

	@Test
	public void testValidator() {
		try {
			TallyVoucher tallyVoucher = new TallyVoucher();
			tallyVoucher.setAccountNo("123");
			tallyVoucher.setCurrency(Currency.RMB);
			tallyVoucher.setTransOrder("123");
			tallyVoucher.setAmount(-1);
			Set<ConstraintViolation<TallyVoucher>> violations = validator.validate(tallyVoucher); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
				else if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Min.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_ERROR);

			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Test
	public void testPersistMongo() {
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
		accountBussinessInterfaceBean.setOperator("JOSE");
		accountBussinessInterfaceBean.setRemark("");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("1001");
		accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

		AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

		// 交易金额
		SpecialTallyVoucher amount = new SpecialTallyVoucher();
		// amount.setAccountNo("234-5fb5e3e6e21c");
		amount.setAccountType(AccountType.CASH);
		amount.setUserNo("8975723");
		amount.setUserRole(UserRole.CUSTOMER);
		amount.setAmount(30);
		amount.setSymbol(FundSymbol.PLUS);
		amount.setCurrency(Currency.RMB);
		amount.setFee(10d);
		amount.setFeeSymbol(FundSymbol.SUBTRACT);
		amount.setTransDate(new Date());
		String transOrder = UUID.randomUUID().toString().substring(16);
		amount.setTransOrder(transOrder);
		amount.setWaitAccountDate(new Date());

		List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCreditReverse accountCreditReverse = accountCreditReverseService.queryBy("1001", "-abd4-6165bdc89d53", "CREDI");
		if (accountCreditReverse == null) accountCreditReverseService.create(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountCreditReverse.class));
	}
}
