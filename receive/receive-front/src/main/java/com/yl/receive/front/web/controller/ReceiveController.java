package com.yl.receive.front.web.controller;

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
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.InetUtils;
import com.yl.receive.front.common.ReceiveCodeEnum;
import com.yl.receive.front.common.ReceiveException;
import com.yl.receive.front.model.CustomerReqInfo;
import com.yl.receive.front.model.QueryRequest;
import com.yl.receive.front.model.QueryResponse;
import com.yl.receive.front.model.ReceiveRequest;
import com.yl.receive.front.model.TradeRequest;
import com.yl.receive.front.model.TradeResponse;
import com.yl.receive.front.service.CustomerReqInfoService;
import com.yl.receive.front.service.DataValidateService;
import com.yl.receive.front.service.ReceiveRequestService;

/**
 * 代收接口控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
@Controller
public class ReceiveController {

	private static Logger logger = LoggerFactory.getLogger(ReceiveController.class);

	@Resource
	private DataValidateService dataValidateService;
	@Resource
	private ReceiveRequestService receiveRequestService;
	@Resource
	private CustomerReqInfoService customerReqInfoService;

	@RequestMapping("/receiveTrade")
	public void trade(HttpServletRequest request, HttpServletResponse response) {
		TradeRequest tradeRequest = null;
		TradeResponse tradeResponse = null;
		ReceiveRequest receiveRequest = null;
		String ownerId = null;
		String customerOrderCode = null;
		
		String ip = InetUtils.getRealIpAddr(request);
		String domain = getDomain(request);
		
		try {
        	BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
			    sb.append(line);
			}
			tradeRequest = JsonUtils.toObject(sb.toString(), TradeRequest.class);
		} catch (IOException e1) {
			logger.info("代收接口下单接收参数异常：{}", e1);
		}
		
		try {
			if(tradeRequest == null){
				throw new ReceiveException(ReceiveCodeEnum.PARAMS_ERROR.getCode(), ReceiveCodeEnum.PARAMS_ERROR.getMessage());
			}
			logger.info("代收接口下单原始请求：{}", tradeRequest);

			String cipherText = tradeRequest.getCipherText();
			ownerId = tradeRequest.getCustomerNo();
			String requestBodyStr = dataValidateService.decryptData(ownerId, cipherText);
			receiveRequest = JsonUtils.toObject(requestBodyStr, new TypeReference<ReceiveRequest>(){});
			dataValidateService.validateParams(receiveRequest);
			receiveRequest.setOwnerId(ownerId);
			customerOrderCode = receiveRequest.getCustomerOrderCode();
			dataValidateService.validateParams(request);
			// 发起代扣
			tradeResponse = receiveRequestService.singleRequest(receiveRequest);
		} catch (Exception e) {
			logger.error("商户号:[{}],代收下单出异常了！异常信息:{}", ownerId, e);
			if(tradeResponse == null){
				tradeResponse = new TradeResponse();
				tradeResponse.setCustomerOrderCode(customerOrderCode);
				tradeResponse.convertResCode(e, tradeResponse);
			}
		}
		try {
			customerReqInfoService.create(new CustomerReqInfo(tradeRequest, receiveRequest,ip,domain));
		} catch (Exception e) {
			logger.error("商户号:[{}],代付记录下单请求出异常了！异常信息:{}", tradeRequest.getCustomerNo(), e);
		}

		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("customerNo", tradeRequest.getCustomerNo());
		resMap.put("version", tradeRequest.getVersionCode());

		try {
			resMap.put("cipherText", dataValidateService.encryptData(tradeRequest.getCustomerNo(), JsonUtils.toJsonString(tradeResponse)));
		} catch (Exception e) {
			logger.error("商户号:[{}],代收加密响应参数异常！异常信息:{}", tradeRequest.getCustomerNo(), e);
		}
		
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JsonUtils.toJsonString(resMap));
			response.getWriter().close();
		} catch (IOException e) {
			logger.error("商户号:[{}],订单号:[{}]代付交易响应参数异常！异常信息:{}", tradeRequest.getCustomerNo(), e);
		}
	}

	@RequestMapping("/receiveQuery")
	public void receiveQuery(HttpServletRequest request, HttpServletResponse response) {
		QueryRequest queryRequest = null;
		QueryResponse queryResponse = null;
		String ip = InetUtils.getRealIpAddr(request);
		String domain = getDomain(request);
		try {
        	BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
			    sb.append(line);
			}
			queryRequest = JsonUtils.toObject(sb.toString(), QueryRequest.class);
		} catch (IOException e1) {
			logger.info("代收接口查询接收参数异常：{}", e1);
		}
		String customerOrderCode = "";
		try {
			logger.info("代收接口查询原始请求信息：{}", queryRequest);
//			dataValidateService.validateParams(queryRequest);
			dataValidateService.checkCustomer(queryRequest.getCustomerNo(),ip,domain);
			String cipherText = dataValidateService.decryptData(queryRequest.getCustomerNo(),queryRequest.getCipherText());

			logger.info("商户号为【{}】的代收查询原始请求明文：{}", queryRequest.getCustomerNo(), cipherText);
			Map<String,String> queryParams = JsonUtils.toObject(cipherText, new TypeReference<Map<String,String>>(){});
			if(queryParams == null || StringUtils.isBlank(queryParams.get("customerOrderCode"))){
				throw new ReceiveException(ReceiveCodeEnum.PARAMS_ERROR.getCode(), ReceiveCodeEnum.PARAMS_ERROR.getMessage());
			}
			customerOrderCode = (String)queryParams.get("customerOrderCode");
			queryResponse = receiveRequestService.singleQuery(queryRequest.getCustomerNo(), customerOrderCode);
		} catch (Exception e) {
			logger.error("商户号:[{}],订单号:[{}]代收查询出异常了！异常信息:{}", queryRequest.getCustomerNo(), customerOrderCode, e);
			if(queryResponse == null){
				queryResponse = new QueryResponse();
			}
			queryResponse = queryResponse.convertResCode(e, queryResponse);
			queryResponse.setCustomerOrderCode(customerOrderCode);
			queryResponse.setCustomerNo(queryRequest.getCustomerNo());
		}

		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("customerNo", queryRequest.getCustomerNo());
		resMap.put("cipherText", dataValidateService.encryptData(queryRequest.getCustomerNo(), JsonUtils.toJsonString(queryResponse)));

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JsonUtils.toJsonString(resMap));
			response.getWriter().close();
		} catch (IOException e) {
			logger.error("商户号:[{}],订单号:[{}]代收查询响应参数异常！异常信息:{}", queryRequest.getCustomerNo(), e);
		}
	}

	private static String getDomain(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
	}
}