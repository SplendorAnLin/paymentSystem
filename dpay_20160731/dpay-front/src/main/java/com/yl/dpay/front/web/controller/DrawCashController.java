package com.yl.dpay.front.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.Md5Util;
import com.yl.dpay.front.model.DpayTradeReqBean;
import com.yl.dpay.front.model.DpayTradeResBean;
import com.yl.dpay.front.service.CustomerReqInfoService;
import com.yl.dpay.front.service.DataValidateService;
import com.yl.dpay.front.service.DpayService;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;

/**
 * 代付提现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
@Controller
public class DrawCashController {
	private static Logger log = LoggerFactory.getLogger(DrawCashController.class);
	@Resource
	private DataValidateService dataValidateService;
	@Resource
	private DpayService dpayService;
	@Resource
	private CustomerReqInfoService customerReqInfoService;

	@RequestMapping("drawCash")
	public void drawCash(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> reqParams = null;
		DpayTradeResBean dpayTradeResBean = null;
		DpayTradeReqBean dpayTradeReqBean = null;
		String customerOrderId = null;
		String customerNo = null;
        try {
        	BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
			    sb.append(line);
			}
			reqParams = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>() {});
		} catch (IOException e1) {
			log.info("代付提现下单接收参数异常：{}", e1);
		}

		try {
			if(reqParams == null){
				throw new DpayRuntimeException(DpayExceptionEnum.PARAMSERR.getCode(), DpayExceptionEnum.getConvertMsg(DpayExceptionEnum.PARAMSERR.getCode()));
			}
			customerNo = reqParams.get("customerNo");
			checkSign(reqParams);
			log.info("代付提现下单原始请求信息：{}", reqParams);
			dpayTradeResBean = dpayService.drawCash(customerNo, Double.parseDouble(reqParams.get("amount")));
			if (dpayTradeResBean != null) {
				dpayTradeResBean.setCustomerParam(dpayTradeReqBean.getCustomerParam());
			}
		} catch (Exception e) {
			log.error("商户号:[{}],代付下单出异常了！异常信息:{}", dpayTradeReqBean.getCustomerNo(), e);
			if (dpayTradeResBean == null) {
				dpayTradeResBean = new DpayTradeResBean();
			}
			dpayTradeResBean.convertResCode(e, dpayTradeResBean);
			dpayTradeResBean.setCustomerOrderCode(customerOrderId);
			dpayTradeResBean.setCustomerParam(dpayTradeReqBean.getCustomerParam());
			dpayTradeResBean.setOrderCode(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		}

		dpayTradeResBean.setResponseCode(DpayExceptionEnum.getConvertCode(dpayTradeResBean.getResponseCode()));
		dpayTradeResBean.setResponseMsg(DpayExceptionEnum.getConvertMsg(dpayTradeResBean.getResponseCode()));

		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("responseCode", dpayTradeResBean.getResponseCode());
		resMap.put("responseMsg", dpayTradeResBean.getResponseMsg());
		resMap.put("timestamp", dpayTradeResBean.getOrderTime());
		String sign = Md5Util.hmacSign(JsonUtils.toJsonString(resMap), resMap.get("timestamp").substring(2, 10));
		resMap.put("sign", sign);
		
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONObject.fromObject(resMap).toString());
			response.getWriter().close();
		} catch (IOException e) {
			log.error("商户号:[{}],订单号:[{}]代付交易响应参数异常！异常信息:{}", dpayTradeReqBean.getCustomerNo(), e);
		}
	}
	
	public static void checkSign(Map<String, String> params){
		Map<String, String> resMap = new HashMap<>();
		resMap.put("amount", params.get("amount"));
		resMap.put("customerNo", params.get("customerNo"));
		resMap.put("timestamp", params.get("timestamp"));
		if(Md5Util.hmacSign(JsonUtils.toJsonString(resMap), resMap.get("timestamp").substring(2, 10)).equals(params.get("sign"))){
			return;
		}
		throw new DpayRuntimeException(DpayExceptionEnum.SIGNERR.getCode(), DpayExceptionEnum.SIGNERR.getMessage());
	}

}
