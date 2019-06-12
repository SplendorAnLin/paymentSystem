package com.yl.account.core;

import java.io.IOException;
import java.util.Properties;

/**
 * 账户核心常量
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public final class C {

	/** 账户核心应用名称 */
	public static final String APP_NAME = "account-core";
	/** 账户运营应用名称 */
	public static final String ACCOUNT_PORTAL_APP_NAME = "account-portal";
	/** 账户核心分隔符 */
	public static final String SPLIT_STRING = "-";
	/** 账户表主键KEY */
	public static final String ACCOUNT_CODE = "account.Account.code";
	/** 账户属性表主键KEY */
	public static final String ACCOUNT_BEHAVIOR_CODE = "account.AccountBehavior.code";
	/** 账户变更表主键KEY */
	public static final String ACCOUNT_CHANGE_DETAIL_CODE = "account.AccountChangeDetail.code";
	/** 账户冻结资金明细表主键KEY */
	public static final String ACCOUNT_FREEZE_BALANCE_DETAIL_CODE = "account.AccountFreezeBalanceDetail.code";
	/** 账户冻结明细表主键KEY */
	public static final String ACCOUNT_FREEZE_DETAIL_CODE = "account.AccountFreezeDetail.code";
	/** 账户冻结明细表冻结NO */
	public static final String ACCOUNT_FREEZE_DETAIL_FREEZENO = "account.AccountFreezeDetail.freezeNo";
	/** 账户外账记账凭证表主键KEY */
	public static final String ACCOUNT_RECORDED_DETAIL_CODE = "account.AccountRecordedDetail.code";
	/** 账户非入账周期汇总表主键KEY */
	public static final String ACCOUNT_TRANSIT_SUMMARY_CODE = "account.AccountTransitSummary.code";
	/** 账户记账凭证表主键KEY */
	public static final String ACCOUNTING_VOUCHER_CODE = "account.AccountingVoucher.code";
	/** 账户入账补单主键KEY */
	public static final String ACCOUNTING_CREDIT_REVERSE_CODE = "account.AccountCreditReverse.code";
	/** 账户在途资金明细表主键KEY */
	public static final String ACCOUNT_TRANSIT_BALANCE_DETAIL_CODE = "account.AccountTransitBalanceDetail.code";
	/** 账务系统自主生成主键KEY */
	public static final String SYSTEM_TRANS_ORDER = "account.SystemTransOrder.code";
	/** 账户入账订阅主题名称 */
	public static final String ACCOUNT_TALLY_SUBSCRIBE_THEME = "account-tally-theme";
	/** 账户日收支余额表主键KEY */
	public static final String ACCOUNT_DAY_CODE = "account.AccountDay.code";
	/** 账户日业务类型汇总表主键KEY */
	public static final String ACCOUNT_DAY_DETAIL_CODE = "account.AccountDayDetail.code";
	/** 账户冻结资金明细表系统流水号 */
	public static final String ACCOUNT_FREEZE_BALANCE_DETAIL_SYSFLOWID = "account.AccountFreezeBalanceDetail.systemFlowId";
	/** 账户调账明细code */
	public static final String ACCOUNT_ADJUST_CODE = "account.AccountAdjust.code";
	/** 账务资金归集交易流水号 */
	public static final String ACCOUNT_CAPITAL_ACCUMULATION_TRANS_ORDER = "account.AccountCapitalAccumulation.transOrder";
	/** 账务转账流水code */
	public static final String ACCOUNT_TRANSFER_CODE = "account.AccountTransfer.code";
	/** 账户预冻结凭证流水code*/
	public static final String ACCOUNT_FREEZE_VOUCHER_CODE ="account.AccountFreezeVoucher.code";
	/** 账户资金归集主键code*/
	public static final String ACCOUNT_CAPITAL_ACCUMULATION_CODE = "account.CapitalAccumulation.code";
	/** MQ 帐户主题 **/
	public static String NOTIFY_TOPIC = "yl-notify-topic";
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(C.class.getClassLoader().getResourceAsStream("producer-client.properties"));
			NOTIFY_TOPIC = prop.getProperty("NOTIFY_TOPIC");
		} catch (IOException e) {}
	}

	public static final String[] SETTLP_RODUCTS = new String[] { "CREDIT_JSFW_T1YXJS_FEE", "CREDIT_LFB_SSF_FEE", "CREDIT_LFB_T0JRJS_FEE", "CREDIT_LFB_T1JRF_FEE",
			"CREDIT_LFB_TTF_FEE", "CREDIT_P_SETTLE_JSFW_T1YXJS", "CREDIT_P_SETTLE_LFB_SSF", "CREDIT_P_SETTLE_LFB_T1JRF", "CREDIT_P_SETTLE_LFB_TTF",
			"DEBIT_JSFW_T1YXJS_FEE", "DEBIT_LFB_SSF_FEE", "DEBIT_LFB_T0JRJS_FEE", "DEBIT_LFB_T1JRF_FEE", "DEBIT_LFB_TTF_FEE", "DEBIT_P_SETTLE_JSFW_T1YXJS",
			"DEBIT_P_SETTLE_LFB_SSF", "DEBIT_P_SETTLE_LFB_T1JRF", "DEBIT_P_SETTLE_LFB_TTF", "DPAY_CREDIT", "DPAY_CREDIT_FEE", "DPAY_DEBIT", "DPAY_DEBIT_FEE",
			"SETTLE_CREDIT", "SETTLE_CREDIT_FEE", "SETTLE_DEBIT", "SETTLE_DEBIT_FEE" };

	public static final String[] ORDERP_RODUCTS = new String[] { "PRE_AUTH_COMP_REVERSAL", "PRE_AUTH_COMP_REVERSAL_FEE", "PRE_AUTH_COMP_VOID",
			"PRE_AUTH_COMP_VOID_FEE", "PURCHASE", "PURCHASE_FEE", "PURCHASE_REVERSAL", "PURCHASE_REVERSAL_FEE", "PURCHASE_VOID", "PURCHASE_VOID_FEE",
			"ONLINE_CREDIT", "ONLINE_CREDIT_FEE" };

}
