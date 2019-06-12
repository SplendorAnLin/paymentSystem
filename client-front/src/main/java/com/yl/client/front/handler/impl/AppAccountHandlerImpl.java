package com.yl.client.front.handler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.client.front.common.AppRuntimeException;
import com.yl.client.front.common.DictUtils;
import com.yl.client.front.enums.AppExceptionEnum;
import com.yl.client.front.handler.AppAccountHandler;
import com.yl.client.front.service.FeeInfoService;
import com.yl.client.front.common.FeeUtils;

/**
 * App账户处理实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@Service("accountHandLer")
public class AppAccountHandlerImpl implements AppAccountHandler {

	private static Logger log = LoggerFactory.getLogger(AppAccountHandler.class);

	@Resource
	AccountQueryInterface accountQueryInterface;
	@Resource
	FeeInfoService feeInfoService;

	public Map<String, Object> findBalance(Map<String, Object> param) throws AppRuntimeException {
		AccountBalanceQuery accountBalanceQuery= new AccountBalanceQuery();
		try {
			accountBalanceQuery.setUserNo(param.get("customerNo").toString());
		} catch (Exception e) {
			log.error("账户余额查询失败！错误原因{}", e);
			throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),
					AppExceptionEnum.PARAMSERR.getMessage());
		}
		accountBalanceQuery.setUserRole(UserRole.CUSTOMER);
		try{
			AccountBalanceQueryResponse accountResponse = accountQueryInterface.findAccountBalance(accountBalanceQuery);
			Map<String, Object> balance=new HashMap<>();
			balance.put("balance",accountResponse.getAvailavleBalance());
			//代付费率计算
			List<Map<String, Object>> fee=feeInfoService.getCustomerFeeOut(param.get("customerNo").toString());
			if (fee!=null&&fee.size()>0){//计算可提现余额
				//balance.put("availableSettleAmount",20);
				double feeAmount=FeeUtils.compFee(fee,accountResponse.getAvailavleBalance());
				balance.put("availableSettleAmount",feeAmount);
			}else {
				balance.put("availableSettleAmount",0);
			}
			return balance;
		}catch (Exception e){
			log.error("帐户接口异常,{}",e.getMessage());
			throw new AppRuntimeException(AppExceptionEnum.SYSERR.getCode(),
					AppExceptionEnum.SYSERR.getMessage());
		}

	}

	@Override
	public Map<String, Object> findAccountChange(Map<String, Object> queryParams) throws AppRuntimeException {
		Map<String, Object> result = new HashMap<>();
		try {
			if (queryParams.get("pageCode") != null || queryParams.get("showCount") != null) {
				queryParams.put("createStartTime", queryParams.get("createStartTime") + " 00:00:00");
				queryParams.put("createEndTime", queryParams.get("createEndTime") + " 23:59:59");
				int pageCode = Integer.parseInt(queryParams.get("pageCode").toString());
				int showCount = Integer.parseInt(queryParams.get("showCount").toString());
				queryParams.put("pageCode", (pageCode - 1) * showCount);
				queryParams.put("showCount", showCount);
			} else {
				queryParams.put("pageCode", 1);
				queryParams.put("showCount", 10);
			}
			List<Map<String, Object>> list = accountQueryInterface.queryAccountHistory(queryParams);
			List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : list) {
				if (map.get("FUND_SYMBOL").equals("PLUS")) {
					map.put("result_AMT", Double.parseDouble(map.get("REMAIN_AMT").toString())
							- Double.parseDouble(map.get("TRANS_AMT").toString()));
				} else {
					map.put("result_AMT", Double.parseDouble(map.get("REMAIN_AMT").toString())
							+ Double.parseDouble(map.get("TRANS_AMT").toString()));
				}
				maps.add(map);
			}
			result.put("resmap", DictUtils.listOfDict(maps, "BUSINESS_TYPE", "BUSI_CODE"));
		} catch (Exception e) {
			log.error("账户明细查询失败！错误原因{}", e);
			throw new AppRuntimeException(AppExceptionEnum.ACCOUNT.getCode(), AppExceptionEnum.ACCOUNT.getMessage());
		}
		return result;
	}
}
