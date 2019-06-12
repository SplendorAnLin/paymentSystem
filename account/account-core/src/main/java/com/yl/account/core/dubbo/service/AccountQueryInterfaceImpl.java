/**
 * 
 */
package com.yl.account.core.dubbo.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Validator;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.request.AccountHistoryQuery;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.bean.response.AccountChangeRecordsResponse;
import com.yl.account.api.bean.response.AccountFreezeDetailResponse;
import com.yl.account.api.bean.response.AccountFreezeQueryResponse;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.bean.response.AccountSummaryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.core.service.AccountAdjustVoucherService;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.service.AccountFreezeDetailService;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.enums.CallType;

/**
 * 账户查询处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月26日
 * @version V1.0.0
 */
@Service("accountQueryInterface")
public class AccountQueryInterfaceImpl implements AccountQueryInterface {

	private final static Logger logger = LoggerFactory.getLogger(AccountQueryInterfaceImpl.class);

	@Resource
	private Validator validator;
	@Resource
	private AccountService accountService;
	@Resource
	private AccountRecordedDetailService accountRecordedDetailService;
	@Resource
	private AccountChangeDetailService accountChangeDetailService;
	@Resource
	private AccountFreezeDetailService accountFreezeDetailService;
	@Resource
	private AccountAdjustVoucherService accountAdjustVoucherService;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountServiceFacade#findAccountBalanceBy(com.lefu.account.api.bean.request.AccountBalanceQueryRequest)
	 */
	@Override
	public AccountBalanceQueryResponse _findAccountBalance(AccountBalanceQuery accountBalanceQuery) {
		AccountBalanceQueryResponse accountBalanceQueryResponse = null;
		try {
			accountBalanceQueryResponse = accountService.findAccountBalanceBy(CallType.REAL_TIME,
					JsonUtils.toObject(JsonUtils.toJsonString(accountBalanceQuery), new TypeReference<Map<String, Object>>() {}));
			logger.debug("账户余额查询信息返回：{}", accountBalanceQueryResponse);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountBalanceQueryResponse == null) accountBalanceQueryResponse = new AccountBalanceQueryResponse();
			accountBalanceQueryResponse.setResult(HandlerResult.FAILED);
		}
		return accountBalanceQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountServiceFacade#findAccountBalanceBy(com.lefu.account.api.bean.request.AccountBalanceQueryRequest)
	 */
	@Override
	public AccountBalanceQueryResponse findAccountBalance(AccountBalanceQuery accountBalanceQuery) {
		AccountBalanceQueryResponse accountBalanceQueryResponse = null;
		try {
			accountBalanceQueryResponse = accountService.findAccountBalanceBy(CallType.NON_REAL_TIME,
					JsonUtils.toObject(JsonUtils.toJsonString(accountBalanceQuery), new TypeReference<Map<String, Object>>() {}));
		} catch (Exception e) {
			logger.error("-", e);
			if (accountBalanceQueryResponse == null) accountBalanceQueryResponse = new AccountBalanceQueryResponse();
			accountBalanceQueryResponse.setResult(HandlerResult.FAILED);
			accountBalanceQueryResponse.setResultMsg("账户不存在");
		}
		return accountBalanceQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountServiceFacade#findAccontBy(com.lefu.account.api.bean.request.AccountQueryRequest)
	 */
	@Override
	public AccountQueryResponse findAccontByAccountNo(String accountNo) {
		AccountQueryResponse accountQueryResponse = null;
		try {
			logger.debug("业务系统根据账号查询账户信息请求：{}", accountNo);
			accountQueryResponse = accountService.findAccountByAccountNo(CallType.NON_REAL_TIME, accountNo);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountQueryResponse == null) accountQueryResponse = new AccountQueryResponse();
			accountQueryResponse.setResult(HandlerResult.FAILED);
		}
		return accountQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountQueryInterface#findAccountBy(com.lefu.account.api.bean.request.AccountQuery)
	 */
	@Override
	public AccountQueryResponse findAccountBy(AccountQuery accountQuery) {
		AccountQueryResponse accountQueryResponse = null;
		try {
			logger.debug("业务系统调用账户查询接口请求：{}", accountQuery);
			accountQueryResponse = accountService.findAccountBy(CallType.NON_REAL_TIME,
					JsonUtils.toObject(JsonUtils.toJsonString(accountQuery), new TypeReference<Map<String, Object>>() {}));
		} catch (Exception e) {
			logger.error("-", e);
			if (accountQueryResponse == null) accountQueryResponse = new AccountQueryResponse();
			accountQueryResponse.setResult(HandlerResult.FAILED);
		}
		return accountQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountQueryInterface#findAccountHistory(com.lefu.account.api.bean.request.AccountHistoryQuery)
	 */
	@Override
	public AccountHistoryQueryResponse findAccountHistory(Map<String, Object> queryParams, Page<?> page) {
		AccountHistoryQueryResponse accountHistoryQueryResponse = null;
		try {
			accountHistoryQueryResponse = accountRecordedDetailService.findAccountHistoryBy(queryParams, page);

			logger.debug("账务查询系统返回账户历史信息：{}", accountHistoryQueryResponse);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountHistoryQueryResponse == null) accountHistoryQueryResponse = new AccountHistoryQueryResponse();
			accountHistoryQueryResponse.setResult(HandlerResult.FAILED);
		}
		return accountHistoryQueryResponse;
	}
	
	@Override
	public Map<String, Object> queryAccountHistorySum(Map<String, Object> queryParams) {
		return JsonUtils.toJsonToObject(accountRecordedDetailService.findAccountSummaryBy(queryParams), Map.class);
	}
	
	@Override
	public List<Map<String, Object>> queryAccountHistory(Map<String, Object> queryParams) {
		List<Map<String, Object>> list=accountRecordedDetailService.queryAccountHistory(queryParams);
		if(list!=null){ 
			return  list;
		}
		return null;
	}
	
	@Override
	public AccountFreezeQueryResponse AccountFreezeList(Map<String, Object> queryParams, Page<?> page) {
		AccountFreezeQueryResponse accountFreezeDetailResponse = null;
		try {
			accountFreezeDetailResponse = accountFreezeDetailService.findAccountFzBy(queryParams, page);
			
			logger.debug("资金冻结查询系统返回账户资金冻结历史信息：{}", accountFreezeDetailResponse);
		} catch (Exception e) {
			logger.error("-", e);
			if(accountFreezeDetailResponse == null) accountFreezeDetailResponse = new AccountFreezeQueryResponse();
			accountFreezeDetailResponse.setResult(HandlerResult.FAILED);
		}
		return accountFreezeDetailResponse;
	}
	
	public AccountHistoryQueryResponse findAccountHistoryExportBy(Map<String, Object> queryParams) {
		AccountHistoryQueryResponse accountHistoryQueryResponse = null;
		try {
			accountHistoryQueryResponse = accountRecordedDetailService.findAccountHistoryExportBy(queryParams);

			logger.debug("账务查询系统返回账户历史信息：{}", accountHistoryQueryResponse);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountHistoryQueryResponse == null) accountHistoryQueryResponse = new AccountHistoryQueryResponse();
			accountHistoryQueryResponse.setResult(HandlerResult.FAILED);
		}
		return accountHistoryQueryResponse;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountQueryInterface#findAccountSummary(com.lefu.account.api.bean.request.AccountQuerySummary)
	 */
	@Override
	public AccountSummaryResponse findAccountSummary(AccountHistoryQuery accountHistoryQuery) {
		AccountSummaryResponse acountSummaryResponse = null;
		try {
			Map<String, Object> queryParams = JsonUtils.toObject(JsonUtils.toJsonString(accountHistoryQuery), new TypeReference<Map<String, Object>>() {});
			if (accountHistoryQuery.getStartCreateDate() != null) queryParams.put("startCreateDate", accountHistoryQuery.getStartCreateDate());
			if (accountHistoryQuery.getEndCreateDate() != null) queryParams.put("endCreateDate", accountHistoryQuery.getEndCreateDate());
			if (accountHistoryQuery.getStartTransTime() != null) queryParams.put("startTransTime", accountHistoryQuery.getStartTransTime());
			if (accountHistoryQuery.getEndTransTime() != null) queryParams.put("endTransTime", accountHistoryQuery.getEndTransTime());

			acountSummaryResponse = accountRecordedDetailService.findAccountSummaryBy(queryParams);
		} catch (Exception e) {
			logger.error("-", e);
			if (acountSummaryResponse == null) acountSummaryResponse = new AccountSummaryResponse();
			acountSummaryResponse.setResult(HandlerResult.FAILED);
		}
		return acountSummaryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountQueryInterface#findAccountByPage(java.util.Map, com.lefu.commons.utils.Page)
	 */
	@Override
	public AccountQueryResponse findAccountByPage(Map<String, Object> queryParams, Page<?> page) {
		AccountQueryResponse accountQueryResponse = null;
		try {
			accountQueryResponse = accountService.findAccountByPage(queryParams, page);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountQueryResponse == null) accountQueryResponse = new AccountQueryResponse();
			accountQueryResponse.setResult(HandlerResult.FAILED);
		}
		return accountQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountQueryInterface#findAccountBySystemFlowId(java.lang.String)
	 */
	@Override
	public AccountQueryResponse findAccountBySystemFlowId(String systemCode, String systemFlowId) {
		AccountQueryResponse accountQueryResponse = null;
		try {
			logger.debug("业务系统根据系统编号、系统流水查询账户信息请求：{}, {}", systemCode, systemFlowId);
			accountQueryResponse = accountChangeDetailService.findAccountBySystemFlowId(systemCode, systemFlowId);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountQueryResponse == null) accountQueryResponse = new AccountQueryResponse();
			accountQueryResponse.setResult(HandlerResult.FAILED);
		}
		return accountQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountQueryInterface#findAccountByTransOrder(java.lang.String)
	 */
	@Override
	public AccountQueryResponse findAccountByTransOrder(String systemCode, String transOrder) {
		AccountQueryResponse accountQueryResponse = null;
		try {
			logger.debug("业务系统根据系统编号、交易订单号查询账户信息请求：{}, {}", systemCode, transOrder);
			accountQueryResponse = accountRecordedDetailService.findAccountByTransOrder(systemCode, transOrder);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountQueryResponse == null) accountQueryResponse = new AccountQueryResponse();
			accountQueryResponse.setResult(HandlerResult.FAILED);
		}
		return accountQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountQueryInterface#findAccountChangeRecordsBy(java.util.Map, com.lefu.commons.utils.Page)
	 */
	@Override
	public AccountChangeRecordsResponse findAccountChangeRecordsBy(Map<String, Object> queryParams, Page<?> page) {
		AccountChangeRecordsResponse accountChangeRecordsResponse = null;
		try {
			accountChangeRecordsResponse = accountChangeDetailService.findAccountChangeRecordsBy(queryParams, page);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountChangeRecordsResponse == null) accountChangeRecordsResponse = new AccountChangeRecordsResponse();
			accountChangeRecordsResponse.setResult(HandlerResult.FAILED);
		}
		return accountChangeRecordsResponse;
	}
  
	@Override
	public List<Map<String, Object>> querycountChangeRecordsBy(Map<String, Object> queryParams) {
		List list=new ArrayList<>();
		list=accountChangeDetailService.querycountChangeRecordsBy(queryParams);
		return list;
	}
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountQueryInterface#findAccountFreezeVoucBy(java.util.Map)
	 */
	@Override
	public AccountFreezeDetailResponse findAccountFreezeDetailBy(Map<String, Object> queryParams) {
		AccountFreezeDetailResponse accountFreezeVoucResponse = null;
		try {
			accountFreezeVoucResponse = accountFreezeDetailService.findAccountDetailBy(queryParams);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountFreezeVoucResponse == null) accountFreezeVoucResponse = new AccountFreezeDetailResponse();
			accountFreezeVoucResponse.setResult(HandlerResult.FAILED);
		}
		return accountFreezeVoucResponse;
	}

	@Override
	public List<Map<String, Object>> findAccTypeBalance() {
		return accountService.findAccTypeBalance();
	}

	@Override
	public Map<String, Object> queryAccountBalance(Map<String, Object> m) {
		AccountBalanceQueryResponse accountBalanceQueryResponse = null;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("userNo", m.get("customerNo"));
			accountBalanceQueryResponse = accountService.findAccountBalanceBy(CallType.REAL_TIME,map);
			logger.debug("账户余额查询信息返回：{}", accountBalanceQueryResponse);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountBalanceQueryResponse == null) accountBalanceQueryResponse = new AccountBalanceQueryResponse();
			accountBalanceQueryResponse.setResult(HandlerResult.FAILED);
		}
		return transBeanMap(accountBalanceQueryResponse);
	}
	
	public static Map<String, Object> transBeanMap(Object obj) {  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                if (!key.equals("class")) {  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    map.put(key, value);  
                }  
            }  
        } catch (Exception e) {  
        	logger.error("Map转换出错：{}",e);
        }  
        return map;  
    }

	@Override
	public boolean queryFreezeNoExists(String freezeNo) {
		return accountFreezeDetailService.queryFreezeNoExists(freezeNo);
	}

	@Override
	public Map<String, Object> findAllAdjHistory(String accountNo, Page<?> page) {
		Map<String, Object> info=new HashMap<>();
		info.put("findResult", accountAdjustVoucherService.findAllAdjHistory(accountNo,page));
		info.put("page", page);
		return info;
	}

}