/**
 * 
 */
package com.yl.account.core.service.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountFreezeDetailResponse;
import com.yl.account.api.bean.response.AccountFreezeQueryResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.core.dao.AccountFreezeDetailDao;
import com.yl.account.core.service.AccountFreezeDetailService;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.model.AccountFreezeDetail;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 账户冻结明细业务逻辑处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@Service
public class AccountFreezeDetailServiceImpl implements AccountFreezeDetailService {

	@Resource
	private AccountFreezeDetailDao accountFreezeDetailDao;
	
	@Override
	public AccountFreezeQueryResponse findAccountFzBy(Map<String, Object> accountHistoryQueryParams, Page<?> page) {
		List <AccountFreezeDetailResponse> accountFreezeDetailResponse = accountFreezeDetailDao.findAccountFzBy(accountHistoryQueryParams, page);
		AccountFreezeQueryResponse accountFreezeQueryResponse = new AccountFreezeQueryResponse();
		accountFreezeQueryResponse.setAccountFreezeDetailResponseBeans(JsonUtils.toObject(JsonUtils.toJsonString(accountFreezeDetailResponse),
				new TypeReference<List<AccountFreezeDetailResponse>>() {}));
		accountFreezeQueryResponse.setPage(page);
		accountFreezeQueryResponse.setResult(HandlerResult.SUCCESS);
		accountFreezeQueryResponse.setFinishTime(new Date());
		return accountFreezeQueryResponse;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeDetailService#create(com.yl.account.model.AccountFreezeDetail)
	 */
	@Override
	public AccountFreezeDetail createPreFreezeDetail(AccountBussinessInterfaceBean accountBussinessInterfaceBean, String accountNo, double preFreezeBalance,
			Date freezeLimit, double valueableBalance, FreezeStatus preFreezeComplete) {
		String freezeNo = UUID.randomUUID().toString().substring(20);
		AccountFreezeDetail accountFreezeDetail = new AccountFreezeDetail();
		accountFreezeDetail.setCode(CodeBuilder.getOrderIdByUUId());
		accountFreezeDetail.setAccountNo(accountNo);
		accountFreezeDetail.setBussinessCode(accountBussinessInterfaceBean.getBussinessCode());
		accountFreezeDetail.setFreezeNo(freezeNo);
		accountFreezeDetail.setPreFreezeBalance(preFreezeBalance);
		accountFreezeDetail.setFreezeBalance(valueableBalance);
		accountFreezeDetail.setFreezeLimit(freezeLimit);
		accountFreezeDetail.setPreFreezeDate(new Date());
		accountFreezeDetail.setStatus(preFreezeComplete);
		accountFreezeDetail.setSystemCode(accountBussinessInterfaceBean.getSystemCode());
		accountFreezeDetail.setTransFlow(accountBussinessInterfaceBean.getSystemFlowId());
		return accountFreezeDetailDao.create(accountFreezeDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeDetailService#findAccountFreezeDetailBy(java.lang.String)
	 */
	@Override
	public List<AccountFreezeDetail> findAccountFreezeDetailBy(String accountNo, FreezeStatus freezeStatus) {
		return accountFreezeDetailDao.findAccountFreezeDetailBy(accountNo, freezeStatus);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeDetailService#addFreezeBalance(com.yl.account.model.AccountFreezeDetail, double)
	 */
	@Override
	public void addFreezeBalance(AccountFreezeDetail accountFreezeDetail, double valueableBalance) {
		accountFreezeDetail.setFreezeBalance(AmountUtils.add(accountFreezeDetail.getFreezeBalance(), valueableBalance));
		accountFreezeDetailDao.addFreezeBalance(accountFreezeDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeDetailService#preFreezeComplete(com.yl.account.model.AccountFreezeDetail, double)
	 */
	@Override
	public void preFreezeComplete(AccountFreezeDetail accountFreezeDetail, double remainPreFreezeBalance) {
		accountFreezeDetail.setFreezeBalance(AmountUtils.add(accountFreezeDetail.getFreezeBalance(), remainPreFreezeBalance));
		accountFreezeDetail.setStatus(FreezeStatus.PRE_FREEZE_COMPLETE);
		accountFreezeDetailDao.preFreezeComplete(accountFreezeDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeDetailService#createFreezeDetail(com.yl.account.api.bean.AccountBussinessInterfaceBean, java.lang.String,
	 * java.util.Date, double)
	 */
	@Override
	public AccountFreezeDetail createFreezeDetail(AccountBussinessInterfaceBean accountBussinessInterfaceBean, String accountNo, double freezeAmount,
			Date freezeLimit) {
		String freezeNo = UUID.randomUUID().toString().substring(20);
		AccountFreezeDetail accountFreezeDetail = new AccountFreezeDetail();
		accountFreezeDetail.setCode(CodeBuilder.getOrderIdByUUId());
		accountFreezeDetail.setSystemCode(accountBussinessInterfaceBean.getSystemCode());
		accountFreezeDetail.setFreezeDate(new Date());
		accountFreezeDetail.setTransFlow(accountBussinessInterfaceBean.getSystemFlowId());
		accountFreezeDetail.setBussinessCode(accountBussinessInterfaceBean.getBussinessCode());
		accountFreezeDetail.setAccountNo(accountNo);
		accountFreezeDetail.setFreezeNo(freezeNo);
		accountFreezeDetail.setFreezeBalance(freezeAmount);
		accountFreezeDetail.setFreezeLimit(freezeLimit);
		accountFreezeDetail.setStatus(FreezeStatus.FREEZE);
		return accountFreezeDetailDao.create(accountFreezeDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeDetailService#findAccountFreezeDetailByFreezeNo(java.lang.String)
	 */
	@Override
	public AccountFreezeDetail findAccountFreezeDetailByFreezeNo(String freezeNo) {
		return accountFreezeDetailDao.findAccountFreezeDetailByFreezeNo(freezeNo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeDetailService#thawComplete(com.yl.account.model.AccountFreezeDetail)
	 */
	@Override
	public void thawComplete(AccountFreezeDetail accountFreezeDetail) {
		accountFreezeDetail.setStatus(FreezeStatus.UNFREEZE);
		accountFreezeDetailDao.thawComplete(accountFreezeDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeDetailService#addConsultBalance(com.yl.account.model.AccountFreezeDetail)
	 */
	@Override
	public void addConsultBalance(AccountFreezeDetail accountFreezeDetail) {
		accountFreezeDetailDao.addConsultBalance(accountFreezeDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeDetailService#findAccountFreezeDetailsBy(com.lefu.commons.utils.Page)
	 */
	@Override
	public List<AccountFreezeDetail> findAccountFreezeDetailsBy(Page<List<AccountFreezeDetail>> page) {
		return accountFreezeDetailDao.findAccountFreezeDetailsBy(page);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yl.account.query.service.AccountFreezeDetailService#findAccountDetailBy(java.util.Map)
	 */
	@Override
	public AccountFreezeDetailResponse findAccountDetailBy(Map<String, Object> queryParams) {
		AccountFreezeDetailResponse accountFreezeDetailResponse = null;

		AccountFreezeDetail accountFreezeDetail = accountFreezeDetailDao.findAccountDetailBy(queryParams);
		accountFreezeDetailResponse = JsonUtils.toJsonToObject(accountFreezeDetail, AccountFreezeDetailResponse.class);

		if (accountFreezeDetailResponse != null) accountFreezeDetailResponse.setResult(HandlerResult.SUCCESS);
		else {
			accountFreezeDetailResponse = new AccountFreezeDetailResponse();
			accountFreezeDetailResponse.setResult(HandlerResult.FAILED);
		}

		accountFreezeDetailResponse.setFinishTime(new Date());
		return accountFreezeDetailResponse;
	}


	@Override
	public boolean queryFreezeNoExists(String freezeNo) {
		return accountFreezeDetailDao.queryFreezeNoExists(freezeNo);
	}
}