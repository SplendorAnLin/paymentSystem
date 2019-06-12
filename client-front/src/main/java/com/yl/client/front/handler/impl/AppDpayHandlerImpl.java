package com.yl.client.front.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.client.front.common.AppRuntimeException;
import com.yl.client.front.common.DictUtils;
import com.yl.client.front.enums.AppExceptionEnum;
import com.yl.client.front.handler.AppDpayHandler;
import com.yl.dpay.hessian.service.DpayFacade;

/**
 * App代付处理实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@Service("dpayHandLer")
public class AppDpayHandlerImpl implements AppDpayHandler {

	private static Logger log = LoggerFactory.getLogger(AppDpayHandler.class);

	@Resource
	DpayFacade dpayFacade;
	@Resource
	AppAccountHandlerImpl accountHandLer;
	@Resource
	CustomerInterface customerInterface;
	@Resource
	AccountQueryInterface accountQueryInterface;
	@Value("${orderComplete}")
	private String orderComplete;

	@Override
	public Map<String, Object> DrawCash(Map<String, Object> param) throws AppRuntimeException {
		try {
			return (Map<String, Object>) dpayFacade.appDrawCash(param.get("customerNo").toString(),
					Double.parseDouble(param.get("amount").toString()));
		} catch (Exception e) {
			log.error("提现请求失败！商户编号：{},错误原因：{}", param.get("customerNo"), e);
			throw new AppRuntimeException(AppExceptionEnum.DRAWCASH.getCode(), AppExceptionEnum.DRAWCASH.getMessage());
		}
	}

	@Override
	public Map<String, Object> selectRequest(Map<String, Object> params) throws AppRuntimeException {
		int pageSize = 10;
		int currentPage = 1;
		try {
			if (params.get("pageSize") != null) {
				pageSize = Integer.parseInt(params.get("pageSize").toString());
			}
			if (params.get("currentPage") != null) {
				currentPage = Integer.parseInt(params.get("currentPage").toString());
			}
			String startCompleteDate=params.get("startCompleteDate").toString()+" 00:00:00";
			String endCompleteDate=params.get("endCompleteDate").toString()+" 23:59:59";
			params.put("startCompleteDate",startCompleteDate);
			params.put("endCompleteDate",endCompleteDate);
			Map<String, Object> findBalance = new HashMap<String, Object>();
			findBalance.put("customerNo", params.get("customerNo"));
			params.put("pageSize", pageSize);
			params.put("currentPage", (currentPage - 1) * pageSize);
			List<Map<String, Object>> maps = dpayFacade.selectRequest(params);
			Map<String, Object> request = new HashMap<String, Object>();
			AccountBalanceQuery accountBalanceQuery= new AccountBalanceQuery();
			accountBalanceQuery.setUserNo(params.get("customerNo").toString());
			accountBalanceQuery.setUserRole(UserRole.CUSTOMER);
			AccountBalanceQueryResponse accountResponse=accountQueryInterface.findAccountBalance(accountBalanceQuery);
			Double balance =accountResponse.getAvailavleBalance();
			request.put("balance", balance);
			for (int i = 0; i < maps.size(); i++) {
				if (maps.get(i).get("AUDIT_STATUS").equals("WAIT")) {
					maps.get(i).put("AUDIT_STATUS_CN", "待审核");
				}
				if (maps.get(i).get("AUDIT_STATUS").equals("REFUSE")) {
					maps.get(i).put("AUDIT_STATUS_CN", "审核拒绝");
				}
				if (maps.get(i).get("AUDIT_STATUS").equals("REMIT_REFUSE")) {
					maps.get(i).put("AUDIT_STATUS_CN", "付款拒绝");
				}
				if (maps.get(i).get("AUDIT_STATUS").equals("REMIT_WAIT")) {
					maps.get(i).put("AUDIT_STATUS_CN", "付款审核");
				}
				if ((maps.get(i).get("AUDIT_STATUS").equals("PASS")
						|| (maps.get(i).get("AUDIT_STATUS").equals("REMIT_WAIT"))
								&& (maps.get(i).get("STATUS").equals("WAIT")||maps.get(i).get("STATUS").equals("HANDLEDING")))) {
					maps.get(i).put("AUDIT_STATUS_CN", "处理中");
				}
				if (maps.get(i).get("STATUS").equals("SUCCESS")) {
					maps.get(i).put("AUDIT_STATUS_CN", "成功");
				}
				if (maps.get(i).get("AUDIT_STATUS").equals("PASS") && maps.get(i).get("STATUS").equals("FAILED")) {
					maps.get(i).put("AUDIT_STATUS_CN", "付款失败");
				}
			}
			request.put("request", maps);
			
			return request;
		} catch (Exception e) {
			log.error("提现交易订单查询失败！错误原因：{}", e);
			throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),
					AppExceptionEnum.RETURNEMPTY.getMessage());
		}

	}

	@Override
	public Map<String, Object> selectRequestDetailed(Map<String, Object> params) throws AppRuntimeException {
		try {
			List<Map<String, Object>> maps = dpayFacade.selectRequestDetailed(params);
			Map<String, Object> request = new HashMap<String, Object>();
			for (int i = 0; i < maps.size(); i++) {
				if (maps.get(i).get("AUDIT_STATUS").equals("WAIT")) {
					maps.get(i).put("AUDIT_STATUS_CN", "待审核");
				}
				if (maps.get(i).get("AUDIT_STATUS").equals("REFUSE")) {
					maps.get(i).put("AUDIT_STATUS_CN", "审核拒绝");
				}
				if (maps.get(i).get("AUDIT_STATUS").equals("REMIT_REFUSE")) {
					maps.get(i).put("AUDIT_STATUS_CN", "付款拒绝");
				}
				if (maps.get(i).get("AUDIT_STATUS").equals("REMIT_WAIT")) {
					maps.get(i).put("AUDIT_STATUS","WAIT");
					maps.get(i).put("AUDIT_STATUS_CN", "付款审核");
				}
				if ((maps.get(i).get("AUDIT_STATUS").equals("PASS")
						|| (maps.get(i).get("AUDIT_STATUS").equals("REMIT_WAIT"))
								&& (maps.get(i).get("STATUS").equals("WAIT")||maps.get(i).get("STATUS").equals("HANDLEDING")))) {
					maps.get(i).put("AUDIT_STATUS","WAIT");
					maps.get(i).put("AUDIT_STATUS_CN", "处理中");
				}
				if (maps.get(i).get("STATUS").equals("SUCCESS")) {
					maps.get(i).put("AUDIT_STATUS_CN", "成功");
				}
				if (maps.get(i).get("AUDIT_STATUS").equals("PASS") && maps.get(i).get("STATUS").equals("FAILED")) {
					maps.get(i).put("AUDIT_STATUS_CN", "付款失败");
				}
			}
			request.putAll(maps.get(0));
			if (request != null) {
				Customer cust = customerInterface.getCustomer(params.get("customerNo").toString());
				request.put("FULL_NAME", cust.getFullName());
			}
			DictUtils.mapOfDict(request,"DF_TYPE", "REQUEST_TYPE");
			return request;
		} catch (Exception e) {
			log.error("提现交易订单详细查询失败！错误原因：{}", e);
			throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),
					AppExceptionEnum.RETURNEMPTY.getMessage());
		}
	}

	@Override
	public Map<String, Object> goDrawCash(Map<String, Object> param) throws AppRuntimeException {
		try {
			if (!param.get("customerNo").toString().equals("") && !param.get("amount").toString().equals("")) {
				return customerInterface.goDrawCash(param);
			} else {
				throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),
						AppExceptionEnum.PARAMSERR.getMessage());
			}
		} catch (Exception e) {
			log.error("提现复核发起失败！错误原因：{}", e);
			throw new AppRuntimeException(AppExceptionEnum.GODRAWCASH.getCode(),
					AppExceptionEnum.GODRAWCASH.getMessage());
		}
	}
}