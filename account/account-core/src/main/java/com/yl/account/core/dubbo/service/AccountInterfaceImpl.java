package com.yl.account.core.dubbo.service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.*;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.core.C;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.HandlerResultCode;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.*;
import com.yl.account.core.service.AccountCreditReverseService;
import com.yl.account.core.service.AccountingVoucherService;
import com.yl.account.model.AccountCreditReverse;
import com.yl.account.model.AccountingVoucher;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 账务业务逻辑处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月26日
 * @version V1.0.0
 */
@Service("accountInterface")
public class AccountInterfaceImpl implements AccountInterface {

	private static Logger logger = LoggerFactory.getLogger(AccountInterfaceImpl.class);

	@Resource
	private AccountOpenFacade accountOpenFacade;
	@Resource
	private AccountModifyFacade accountModifyFacade;
	@Resource
	private AccountCompositeTallyFacade accountCompositeTallyFacade;
	@Resource
	private AccountPreFreezeFacade accountPreFreezeFacade;
	@Resource
	private AccountThawFacade accountThawFacade;
	@Resource
	private AccountDelayTallyFacade accountDelayTallyFacade;
	@Resource
	private AccountingVoucherService accountingVoucherService;
	@Resource
	private AccountFreezeFacade accountFreezeFacade;
	@Resource
	private AccountConsultFacade accountConsultFacade;
	@Resource
	private AccountBatchConsultFacade accountBatchConsultFacade;
	@Resource
	private AccountCreditReverseService accountCreditReverseService;
	@Autowired
	private Environment env;
	@Resource
	private Validator validator;
	@Resource
	private AccountAdjustFacade accountAdjustFacade;

	@Override
	public synchronized AccountCompositeTallyResponse standardCompositeTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountCompositeTallyResponse accountCompositeTallyResponse = null;
		try {
			if (accountBussinessInterfaceBean == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
				else if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Min.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_ERROR);
			}

			accountingVoucherService.recordAccountingVoucher(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountingVoucher.class)); // 记录记账凭证信息
			accountCompositeTallyResponse = accountCompositeTallyFacade.standardCompositeTally(accountBussinessInterfaceBean);
		} catch (Exception e) {
			logger.error("{}", e);

			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			String errorMsg = StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString();

			this.snaphost(accountBussinessInterfaceBean, errorMsg);

			if (accountCompositeTallyResponse == null) accountCompositeTallyResponse = new AccountCompositeTallyResponse();
			accountCompositeTallyResponse.setResult(HandlerResult.FAILED);
			accountCompositeTallyResponse.setResultMsg(errorMsg);
		}
		return accountCompositeTallyResponse;
	}

	/**
	 * @Description 快照当前信息
	 * @param accountBussinessInterfaceBean 账务业务处理Bean
	 * @param errorMsg 错误信息
	 */
	private void snaphost(AccountBussinessInterfaceBean accountBussinessInterfaceBean, String errorMsg) {
		try {
			String phoneErrorMsg = StringUtils.concatToSB("账务系统处理异常! 错误码：[", errorMsg, "]，系统：[", accountBussinessInterfaceBean.getSystemCode(), "]，流水号：[",
					accountBussinessInterfaceBean.getSystemFlowId() + "]，业务类型：[", accountBussinessInterfaceBean.getBussinessCode(), "]").toString();

			try {
				logger.error("{}", phoneErrorMsg);
			} catch (Exception e) {
				logger.error("短信发送异常：{}", e);
			}

			if (accountBussinessInterfaceBean != null) {
				AccountCreditReverse accountCreditReverse = JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountCreditReverse.class);
				accountCreditReverse.setExceptionMessage(errorMsg);
				accountCreditReverseService.create(accountCreditReverse);
			}
		} catch (Exception ex) {
			logger.error("{}", ex);
		}
	}

	@Override
	public synchronized AccountCompositeTallyResponse specialCompositeTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountCompositeTallyResponse accountCompositeTallyResponse = null;
		try {
			if (accountBussinessInterfaceBean == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
				else if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Min.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_ERROR);
			}

			accountingVoucherService.recordAccountingVoucher(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountingVoucher.class)); // 记录记账凭证信息
			accountCompositeTallyResponse = accountCompositeTallyFacade.specialCompositeTally(accountBussinessInterfaceBean);
		} catch (Exception e) {
			logger.error("{}", e);

			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			String errorMsg = StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString();

			this.snaphost(accountBussinessInterfaceBean, errorMsg);

			if (accountCompositeTallyResponse == null) accountCompositeTallyResponse = new AccountCompositeTallyResponse();
			accountCompositeTallyResponse.setResult(HandlerResult.FAILED);
			accountCompositeTallyResponse.setResultMsg(errorMsg);
		}
		return accountCompositeTallyResponse;
	}

	@Override
	public synchronized AccountDelayTallyResponse delayTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountDelayTallyResponse accountDelayTallyResponse = null;
		try {
			if (accountBussinessInterfaceBean == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
			}

			accountingVoucherService.recordAccountingVoucher(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountingVoucher.class)); // 记录记账凭证信息
			accountDelayTallyResponse = accountDelayTallyFacade.delayTally(accountBussinessInterfaceBean);
		} catch (Exception e) {
			logger.error("{}", e);
			if (accountDelayTallyResponse == null) accountDelayTallyResponse = new AccountDelayTallyResponse();
			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			accountDelayTallyResponse.setResult(HandlerResult.FAILED);
			accountDelayTallyResponse.setResultMsg(StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString());
		}
		return accountDelayTallyResponse;
	}

	@Override
	public synchronized AccountFreezeResponse freeze(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountFreezeResponse accountFreezeResponse = null;
		try {
			if (accountBussinessInterfaceBean == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
				else if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Min.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Future.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_ERROR);
			}

			accountingVoucherService.recordAccountingVoucher(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountingVoucher.class)); // 记录记账凭证信息
			accountFreezeResponse = accountFreezeFacade.freeze(accountBussinessInterfaceBean);
		} catch (Exception e) {
			logger.error("{}", e);
			if (accountFreezeResponse == null) accountFreezeResponse = new AccountFreezeResponse();
			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			accountFreezeResponse.setResult(HandlerResult.FAILED);
			accountFreezeResponse.setResultMsg(StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString());
		}
		return accountFreezeResponse;
	}

	@Override
	public synchronized AccountPreFreezeResponse preFreeze(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountPreFreezeResponse accountPreFreezeResponse = null;
		try {
			if (accountBussinessInterfaceBean == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
				else if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Min.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Future.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_ERROR);
			}

			accountingVoucherService.recordAccountingVoucher(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountingVoucher.class)); // 记录记账凭证信息
			accountPreFreezeResponse = accountPreFreezeFacade.preFreeze(accountBussinessInterfaceBean); // 入账处理
		} catch (Exception e) {
			logger.error("{}", e);
			if (accountPreFreezeResponse == null) accountPreFreezeResponse = new AccountPreFreezeResponse();
			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			accountPreFreezeResponse.setResult(HandlerResult.FAILED);
			accountPreFreezeResponse.setResultMsg(StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString());
		}
		return accountPreFreezeResponse;
	}

	@Override
	public synchronized AccountThawResponse thaw(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountThawResponse accountThawResponse = null;
		try {
			if (accountBussinessInterfaceBean == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
			}

			accountingVoucherService.recordAccountingVoucher(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountingVoucher.class)); // 记录记账凭证信息
			accountThawResponse = accountThawFacade.thaw(accountBussinessInterfaceBean);
		} catch (Exception e) {
			logger.error("{}", e);
			if (accountThawResponse == null) accountThawResponse = new AccountThawResponse();
			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			accountThawResponse.setResult(HandlerResult.FAILED);
			accountThawResponse.setResultMsg(StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString());
		}
		return accountThawResponse;
	}

	@Override
	public AccountOpenResponse openAccount(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountOpenResponse accountOpenResponse = null;
		try {
			// 参数校验
			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean);
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
			}
			accountOpenResponse = accountOpenFacade.openAccount(accountBussinessInterfaceBean);
		} catch (Exception e) {
			logger.error("{}", e);
			if (accountOpenResponse == null) accountOpenResponse = new AccountOpenResponse();
			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			accountOpenResponse.setResult(HandlerResult.FAILED);
			accountOpenResponse.setResultMsg(StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString());
		}
		return accountOpenResponse;
	}

	@Override
	public AccountModifyResponse modifyAccount(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountModifyResponse accountModifyResponse = null;
		// 参数校验
		try {
			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean);
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
			}
			accountModifyResponse = accountModifyFacade.modifyAccountInfo(accountBussinessInterfaceBean);
		} catch (Exception e) {
			logger.error("{}", e);
			if (accountModifyResponse == null) accountModifyResponse = new AccountModifyResponse();
			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			accountModifyResponse.setResult(HandlerResult.FAILED);
			accountModifyResponse.setResultMsg(StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString());
		}
		return accountModifyResponse;
	}

	@Override
	public synchronized AccountConsultResponse consult(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountConsultResponse accountConsuleResponse = null;
		try {
			if (accountBussinessInterfaceBean == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
				else if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Min.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_ERROR);
			}

			accountingVoucherService.recordAccountingVoucher(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountingVoucher.class)); // 记录记账凭证信息
			accountConsuleResponse = accountConsultFacade.consult(accountBussinessInterfaceBean);
		} catch (Exception e) {
			logger.error("{}", e);
			if (accountConsuleResponse == null) accountConsuleResponse = new AccountConsultResponse();
			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			accountConsuleResponse.setResult(HandlerResult.FAILED);
			accountConsuleResponse.setResultMsg(StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString());
		}
		return accountConsuleResponse;
	}

	@Override
	public synchronized AccountBatchConsultResponse batchConsult(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountBatchConsultResponse accountBatchConsultResponse = null;
		try {
			if (accountBussinessInterfaceBean == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
				else if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Min.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_ERROR);
			}

			accountingVoucherService.recordAccountingVoucher(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountingVoucher.class)); // 记录记账凭证信息
			accountBatchConsultResponse = accountBatchConsultFacade.batchConsult(accountBussinessInterfaceBean);
		} catch (Exception e) {
			logger.error("{}", e);
			if (accountBatchConsultResponse == null) accountBatchConsultResponse = new AccountBatchConsultResponse();
			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			accountBatchConsultResponse.setResult(HandlerResult.FAILED);
			accountBatchConsultResponse.setResultMsg(StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString());
		}
		return accountBatchConsultResponse;
	}

	@Override
	public synchronized AccountAdjustResponse adjustAccount(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountAdjustResponse accountAdjustResponse = null;

		try {

			if (accountBussinessInterfaceBean == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

			Set<ConstraintViolation<AccountBussinessInterfaceBean>> violations = validator.validate(accountBussinessInterfaceBean); // 参数校验
			if (violations != null && !violations.isEmpty()) {
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)
						|| violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotBlank.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_NOT_EXISTS);
				else if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(Min.class)) throw new BussinessRuntimeException(
						ExceptionMessages.PARAM_ERROR);
			}

			accountingVoucherService.recordAccountingVoucher(JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountingVoucher.class)); // 记录记账凭证信息

			accountAdjustResponse = accountAdjustFacade.adjust(accountBussinessInterfaceBean);

		} catch (Exception e) {
			logger.error("{}", e);
			if (accountAdjustResponse == null) accountAdjustResponse = new AccountAdjustResponse();
			HandlerResultCode result = HandlerResultCode.getHandlerResultCode(e.getMessage());
			accountAdjustResponse.setResult(HandlerResult.FAILED);
			accountAdjustResponse.setResultMsg(StringUtils.concatToSB(result.getAccountCode(), C.SPLIT_STRING, result.getHandlerResultMsg()).toString());
		}

		return accountAdjustResponse;
	}

	@Override
	public String queryFreezeAccount(String freezeNo) {
		return accountThawFacade.queryFreezeAccount(freezeNo);
	}

}
