package com.yl.boss.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.AccountFreezeBean;
import com.yl.account.api.bean.request.AccountAdjust;
import com.yl.account.api.bean.request.AccountCompositeTally;
import com.yl.account.api.bean.request.AccountFreeze;
import com.yl.account.api.bean.request.AccountModify;
import com.yl.account.api.bean.request.AccountThaw;
import com.yl.account.api.bean.request.TallyVoucher;
import com.yl.account.api.bean.response.AccountChangeRecordsResponse;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.bean.response.AccountFreezeDetailQueryResponse;
import com.yl.account.api.bean.response.AccountFreezeQueryResponse;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.api.bean.response.AccountModifyResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.ExpireOperate;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.boss.bean.AccountHistoryQueryBean;
import com.yl.boss.bean.AccountQueryBean;
import com.yl.boss.service.AccountService;
import com.yl.boss.utils.CodeBuilder;

/**
 * 账户信息业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class AccountServiceImpl implements AccountService {

	private AccountQueryInterface accountQueryInterface;

	private AccountInterface accountInterface;
	
	
	
	@Override
	public Page queryAccount(Page page, AccountQueryBean accountBean) {
		Map<String, Object> queryParams = ObjectToMap(accountBean);
		AccountQueryResponse accountQueryResponse = accountQueryInterface.findAccountByPage(queryParams, page);
		page = accountQueryResponse.getPage();
		page.setObject(accountQueryResponse.getAccountBeans());
		return page;
	}

	private Map<String, Object> ObjectToMap(Object object) {
		Map<String, Object> map = new HashMap<>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(object);
					if (value != null && StringUtils.notBlank(value.toString())) {
						map.put(key, value);
					}
				}

			}
		} catch (Exception e) {
		}
		return map;
	}

	@Override
	public AccountBean queryAccountDetail(String accountNo) {
		AccountQueryResponse accountQueryResponse = accountQueryInterface.findAccontByAccountNo(accountNo);
		return accountQueryResponse.getAccountBeans().get(0);
	}

	@Override
	public Page queryAccountHistory(AccountHistoryQueryBean accountHistoryQueryBean, Page page) {
		Map<String, Object> map = ObjectToMap(accountHistoryQueryBean);
		AccountHistoryQueryResponse accountHistoryQueryResponse = accountQueryInterface.findAccountHistory(map, page);
		page = accountHistoryQueryResponse.getPage();
		page.setObject(accountHistoryQueryResponse.getAccountRecordedDetailBeans());
		return page;
	}
	
	@Override
	public Map<String, Object> queryAccountHistorySum(AccountHistoryQueryBean accountHistoryQueryBean) {
		Map<String, Object> map = ObjectToMap(accountHistoryQueryBean);
		return accountQueryInterface.queryAccountHistorySum(map);
	}
	
	@Override
	public Page queryAccountFz(AccountFreezeBean accountFZTWQueryBean,Page page) {
		Map<String, Object> map = ObjectToMap(accountFZTWQueryBean);
		AccountFreezeQueryResponse accountFreezeDetailQueryResponse = accountQueryInterface.AccountFreezeList(map, page);
		page = accountFreezeDetailQueryResponse.getPage();
		page.setObject(accountFreezeDetailQueryResponse.getAccountFreezeDetailResponseBeans());
		return page;
	}
	

	@Override
	public Page queryAccountChangeRecordsBy(AccountQueryBean accountBean, Page page) {
		Map<String, Object> queryParams = ObjectToMap(accountBean);
		AccountChangeRecordsResponse accountChangeRecordsResponse = accountQueryInterface.findAccountChangeRecordsBy(queryParams, page);
		page = accountChangeRecordsResponse.getPage();
		page.setObject(accountChangeRecordsResponse.getAccountChangeDetailBeans());
		return page;
	}
	
	/**
	 * 冻结资金
	 */
	public void accountFrozen(String accountNo, Date freezeLimit, String amount, String frozenReason, String operator){
		String transCode = CodeBuilder.build("FZ", "yyyyMMdd");
		
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setSystemCode("BOSS");
		accountBussinessInterfaceBean.setBussinessCode("FZ");
		accountBussinessInterfaceBean.setSystemFlowId(transCode);
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setOperator(operator);
		accountBussinessInterfaceBean.setRemark(frozenReason);
		
		AccountFreeze accountFreeze = new AccountFreeze();
		accountFreeze.setAccountNo(accountNo);
		accountFreeze.setFreezeAmount(Double.valueOf(amount));
		accountFreeze.setFreezeLimit(freezeLimit);
		accountBussinessInterfaceBean.setTradeVoucher(accountFreeze);
		
		accountInterface.freeze(accountBussinessInterfaceBean);
	}
	
	/**
	 * 根据冻结编号查询账户编号
	 */
	@Override
	public String queryFreezeAccount(String freezeNo) {
		return accountInterface.queryFreezeAccount(freezeNo);
	}
	
	/**
	 * 资金解冻
	 */
	public void thaw(String accountNo, String freezeNo, String frozenReason,String operator){
		String transCode = CodeBuilder.build("TW", "yyyyMMdd");
		
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setSystemCode("BOSS");
		accountBussinessInterfaceBean.setBussinessCode("TW");
		accountBussinessInterfaceBean.setSystemFlowId(transCode);
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setOperator(operator);
		accountBussinessInterfaceBean.setRemark(frozenReason);
		
		AccountThaw accountThaw = new AccountThaw();
		accountThaw.setAccountNo(accountNo);
		accountThaw.setFreezeNo(freezeNo);
		accountBussinessInterfaceBean.setTradeVoucher(accountThaw);
		
		accountInterface.thaw(accountBussinessInterfaceBean);
	}

	public void adjustmentAccount(String accountNo, String symbol, String amount, String adjustReason, String operator) {
		String transCode = CodeBuilder.build("ADJUST", "yyyyMMdd");

		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setSystemCode("BOSS");
		accountBussinessInterfaceBean.setBussinessCode("ADJUST");
		accountBussinessInterfaceBean.setSystemFlowId(transCode);
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setOperator(operator);
		accountBussinessInterfaceBean.setRemark(adjustReason);

		AccountAdjust accountAdjust = new AccountAdjust();
		accountAdjust.setAccountNo(accountNo);
		accountAdjust.setAmount(Double.valueOf(amount));
		accountAdjust.setExpireOperate(ExpireOperate.NORMAL);
		accountAdjust.setFundSymbol(FundSymbol.valueOf(symbol));
		accountAdjust.setTransOrder(transCode);
		accountAdjust.setReason(adjustReason);
		accountBussinessInterfaceBean.setTradeVoucher(accountAdjust);
		accountInterface.adjustAccount(accountBussinessInterfaceBean);
		
		TallyVoucher tallyVoucher = new TallyVoucher();
		tallyVoucher.setAccountNo(accountAdjust.getAccountNo());
		tallyVoucher.setAmount(accountAdjust.getAmount());
		tallyVoucher.setTransOrder(accountAdjust.getTransOrder());
		tallyVoucher.setTransDate(accountBussinessInterfaceBean.getRequestTime());
		tallyVoucher.setWaitAccountDate(accountBussinessInterfaceBean.getRequestTime());
		tallyVoucher.setCurrency(Currency.RMB);
		tallyVoucher.setSymbol(FundSymbol.valueOf(accountAdjust.getFundSymbol().name()));

		LinkedList<TallyVoucher> tallyVoucherList = new LinkedList<TallyVoucher>();
		tallyVoucherList.add(tallyVoucher);
		AccountCompositeTally accountCompositeTally = new AccountCompositeTally();
		accountCompositeTally.setTallyVouchers(tallyVoucherList);

		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCompositeTallyResponse accountCompositeTallyResponse = accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
		if(accountCompositeTallyResponse.getStatus() == Status.FAILED || accountCompositeTallyResponse.getResult() == HandlerResult.FAILED){
			throw new RuntimeException("account "+accountAdjust.getAccountNo()+" Adjust is failed!");
		}
	}
	
	@Override
	public boolean queryFreezeNoExists(String freezeNo) {
		return accountQueryInterface.queryFreezeNoExists(freezeNo);
	}

	public AccountModifyResponse modifyAccount(AccountModify accountModify) {
		return accountInterface.modifyAccount(accountModify);
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public void setAccountInterface(AccountInterface accountInterface) {
		this.accountInterface = accountInterface;
	}

}