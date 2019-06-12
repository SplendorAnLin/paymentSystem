package com.yl.account.core.facade.impl;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountPreFreeze;
import com.yl.account.api.bean.response.AccountAdjustResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.bean.AccountAdjust;
import com.yl.account.bean.TallyVoucher;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountAdjustFacade;
import com.yl.account.core.facade.AccountPreFreezeFacade;
import com.yl.account.core.facade.AccountTallyFacade;
import com.yl.account.core.service.AccountAdjustVoucherService;
import com.yl.account.core.service.AccountBehaviorService;
import com.yl.account.core.service.AccountService;
import com.yl.account.core.utils.AccountFundSymbolUtils;
import com.yl.account.enums.*;
import com.yl.account.model.Account;
import com.yl.account.model.AccountAdjustVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 调账facade实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component
public class AccountAdjustFacadeImpl implements AccountAdjustFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountAdjustFacadeImpl.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountBehaviorService accountBehaviorService;
	@Resource
	private AccountAdjustVoucherService accountAdjustVoucherService;
	@Resource
	private AccountPreFreezeFacade accountPreFreezeFacade;
	@Resource
	private AccountTallyFacade accountDebitFacade;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountAdjustResponse adjust(AccountBussinessInterfaceBean bussIntBean) {
		try {

			AccountAdjust accountAdjust = JsonUtils.toJsonToObject(bussIntBean.getTradeVoucher(), AccountAdjust.class);

			logger.info("账务系统接收业务系统[调账]指令：{}", accountAdjust);

			Account account = accountService.findAccountByCode(accountAdjust.getAccountNo());
			if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);
			if (account.getStatus().equals(AccountStatus.FREEZE)) throw new BussinessException(ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL);

			String bussinesssCode = bussIntBean.getBussinessCode();
			AccountAdjustVoucher accountAdjustVoucher = accountAdjustVoucherService.findBy(bussIntBean.getSystemCode(), bussIntBean.getSystemFlowId(),
					bussinesssCode, accountAdjust.getAccountNo());
			if (accountAdjustVoucher != null) returnResponse(accountAdjustVoucher.getStatus(), "已在系统中纯在！", null);

			// 账户可用金额
			double valueableBalance = AmountUtils.subtract(account.getBalance(), AmountUtils.add(account.getTransitBalance(), account.getFreezeBalance()));
			// 账户交易金额（调增为正数，调减为负数）
			double tradeBalance = AccountFundSymbolUtils.convertToRealAmount(accountAdjust.getFundSymbol(), accountAdjust.getAmount());
			// 预冻编号（只有调减时调减金额大于账户可用金额时才有值）
			String freezeNo = null;
			// 调账状态
			AdjustStatus adjustStatus = AdjustStatus.ADJUST_ING;
			// 处理状态
			HandleStatus handleStatus = null;

			// 调账
			if (accountAdjust.getFundSymbol().equals(FundSymbol.PLUS)) {
				if (AccountStatus.END_IN.equals(account.getStatus())) throw new BussinessException(ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL);
				handleStatus = HandleStatus.AUDIT_ING;
			} else {
				double chaEBalance = AmountUtils.add(valueableBalance, tradeBalance);
				if (chaEBalance < 0) {
					if (accountAdjust.getExpireTime() == null || accountAdjust.getExpireOperate() == null) throw new BussinessException(
							ExceptionMessages.PARAM_NOT_EXISTS);
					AccountPreFreeze accountPreFreeze = new AccountPreFreeze();
					accountPreFreeze.setAccountNo(account.getCode());
					accountPreFreeze.setFreezeLimit(DateUtils.addDays(new Date(), accountAdjust.getExpireTime()));
					accountPreFreeze.setPreFreezeAmount(accountAdjust.getAmount());
					bussIntBean.setTradeVoucher(accountPreFreeze);
					bussIntBean.setBussinessCode("PREFREEZE");
					freezeNo = accountPreFreezeFacade.preFreeze(bussIntBean).getFreezeNo();
					handleStatus = HandleStatus.UN_HANDLE;
				} else {
					TallyVoucher tallyVoucher = new TallyVoucher();
					tallyVoucher.setAccountNo(account.getCode());
					tallyVoucher.setAmount(accountAdjust.getAmount());
					tallyVoucher.setTransOrder(accountAdjust.getTransOrder());
					tallyVoucher.setTransDate(bussIntBean.getRequestTime());
					tallyVoucher.setWaitAccountDate(new Date());
					tallyVoucher.setCurrency(Currency.RMB);
					tallyVoucher.setSymbol(accountAdjust.getFundSymbol());
					bussIntBean.setBussinessCode("ADJUST");
					accountDebitFacade.tally(tallyVoucher, bussIntBean);
					adjustStatus = AdjustStatus.ADJUST_ED;
					handleStatus = HandleStatus.HANDLED;
				}
			}
			// 创建调账流水
			accountAdjustVoucherService.create(bussIntBean.getSystemCode(), bussinesssCode, account.getCode(), account.getType(), account.getUserNo(),
					account.getUserRole(), adjustStatus, accountAdjust.getFundSymbol(), accountAdjust.getAmount(), accountAdjust.getExpireTime(),
					accountAdjust.getExpireOperate(), bussIntBean.getSystemFlowId(), freezeNo, bussIntBean.getOperator(), accountAdjust.getReason(),
					bussIntBean.getRemark(), handleStatus, accountAdjust.getProcessInstanceId(), accountAdjust.getTransOrder(),
					FundSymbol.PLUS.name().equals(accountAdjust.getFundSymbol().name()) ? NoticeStatus.YES : NoticeStatus.NO);

			// 返回处理结果
			return returnResponse(adjustStatus, AdjustStatus.ADJUST_ED.equals(adjustStatus) ? "调账已完成" : "调账进行中", freezeNo);
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
	}

	private AccountAdjustResponse returnResponse(AdjustStatus adjustStatus, String msg, String freezeNo) {
		AccountAdjustResponse accountAdjustResponse = new AccountAdjustResponse();
		accountAdjustResponse.setResult(HandlerResult.SUCCESS);
		accountAdjustResponse.setResultMsg(msg);
		accountAdjustResponse.setFinishTime(new Date());
		accountAdjustResponse.setAdjustStatus(com.yl.account.api.enums.AdjustStatus.valueOf(adjustStatus.name()));
		return accountAdjustResponse;
	}

	@Override
	public void modifyHandleStatusAndAdjustStatus(HashMap<String, Object> paramMap) {
		AccountAdjustVoucher accountAdjustVoucher = accountAdjustVoucherService.findByCode((String) paramMap.get("code"));
		if (accountAdjustVoucher != null && !accountAdjustVoucher.getStatus().equals(AdjustStatus.ADJUST_ING)) throw new BussinessRuntimeException(
				ExceptionMessages.ACCOUNT_ADJUST_STATUS_NOT_MATCH);
		accountAdjustVoucher.setHandleStatus(HandleStatus.valueOf((String) paramMap.get("handleStatus")));
		accountAdjustVoucher.setStatus(AdjustStatus.valueOf((String) paramMap.get("adjustStatus")));
		if (paramMap.get("unFreezeAmount") != null) accountAdjustVoucher.setUnFreezeAmount((Double) paramMap.get("unFreezeAmount"));
		logger.info("调账流水凭证状态更改,HandleStatus {},Status {}, unFreezeAmount {}",accountAdjustVoucher.getHandleStatus(),accountAdjustVoucher.getStatus(),accountAdjustVoucher.getUnFreezeAmount());
		accountAdjustVoucherService.modifyHandleStatusAndAdjustStatus(accountAdjustVoucher);
	}

	@Override
	public void modifyVoucher(Map<String, Object> paramMap) {
		AccountAdjustVoucher accountAdjustVoucher = accountAdjustVoucherService.findByCode((String) paramMap.get("code"));
		if (accountAdjustVoucher != null && !accountAdjustVoucher.getStatus().equals(AdjustStatus.ADJUST_ING)) throw new BussinessRuntimeException(
				ExceptionMessages.ACCOUNT_ADJUST_STATUS_NOT_MATCH);
		Account account = accountService.findAccountByCode((String) paramMap.get("accountNo"));
		if (account == null) throw new BussinessRuntimeException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

		accountAdjustVoucher.setAccountNo(account.getCode());
		accountAdjustVoucher.setAccountType(account.getType());
		accountAdjustVoucher.setUserNo(account.getUserNo());
		accountAdjustVoucher.setUserRole(account.getUserRole());
		accountAdjustVoucher.setAmount((Double) paramMap.get("amount"));
		accountAdjustVoucher.setReason((String) paramMap.get("reason"));
		accountAdjustVoucher.setRemark((String) paramMap.get("remark"));

		logger.info("调账明细更改为{}", accountAdjustVoucher);

		accountAdjustVoucherService.modifyVoucher(accountAdjustVoucher);
	}
}
