package com.yl.account.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.Md5Util;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.UserRole;

/**
 * 账户
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月21日
 * @version V1.0.0
 */
@Controller
public class AccountController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Resource
	private AccountQueryInterface accountQueryInterface;

	/**
	 * 查询账户余额客户端接口
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "accBalanceCliQuery")
	public void accBalanceCliQuery(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> reqParams = null;
		Map<String, String> resParams = null;
		try {
        	BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
			    sb.append(line);
			}
			reqParams = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>() {});
		} catch (IOException e1) {
			logger.info("客户端账户余额查询 接收参数异常：{}", e1);
		}
		
		if(reqParams == null){
			resParams = new HashMap<>();
			resParams.put("responseCode", "01");
			resParams.put("responseMsg", "参数错误");
			resParams.put("timestamp", String.valueOf(System.nanoTime()));
			resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JsonUtils.toJsonString(resParams));
				response.getWriter().close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return;
		}
		
		Map<String, String> resMap = new HashMap<>();
		resMap.put("customerNo", reqParams.get("customerNo"));
		resMap.put("timestamp", reqParams.get("timestamp"));
		if(!Md5Util.hmacSign(JsonUtils.toJsonString(resMap), resMap.get("timestamp").substring(2, 10)).equals(reqParams.get("sign"))){
			resParams = new HashMap<>();
			resParams.put("responseCode", "01");
			resParams.put("responseMsg", "签名错误");
			resParams.put("timestamp", String.valueOf(System.nanoTime()));
			resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JsonUtils.toJsonString(resParams));
				response.getWriter().close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return;
		}
		
		AccountBalanceQuery accountBalanceQuery = new AccountBalanceQuery();
		accountBalanceQuery.setUserNo(reqParams.get("customerNo"));
		accountBalanceQuery.setUserRole(UserRole.CUSTOMER);
		accountBalanceQuery.setAccountType(AccountType.CASH);
		AccountBalanceQueryResponse balanceQueryResponse = accountQueryInterface.findAccountBalance(accountBalanceQuery);
		if(balanceQueryResponse.getResult() == HandlerResult.SUCCESS){
			resParams = new HashMap<>();
			resParams.put("responseCode", "00");
			resParams.put("responseMsg", "成功");
			resParams.put("timestamp", String.valueOf(System.nanoTime()));
			
			resParams.put("status", balanceQueryResponse.getStatus().toString());
			resParams.put("balance", String.valueOf(balanceQueryResponse.getBalance()));
			resParams.put("availavleBalance", String.valueOf(balanceQueryResponse.getAvailavleBalance()));
			resParams.put("transitBalance", String.valueOf(balanceQueryResponse.getTransitBalance()));
			resParams.put("freezeBalance", String.valueOf(balanceQueryResponse.getFreezeBalance()));
			resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JsonUtils.toJsonString(resParams));
				response.getWriter().close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return;
		}else{
			resParams = new HashMap<>();
			resParams.put("responseCode", "01");
			resParams.put("responseMsg", balanceQueryResponse.getResultMsg());
			resParams.put("timestamp", String.valueOf(System.nanoTime()));
			resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JsonUtils.toJsonString(resParams));
				response.getWriter().close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return;
		}
	}

}
