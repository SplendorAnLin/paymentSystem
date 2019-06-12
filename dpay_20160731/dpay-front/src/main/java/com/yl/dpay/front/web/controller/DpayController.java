package com.yl.dpay.front.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.InetUtils;
import com.yl.dpay.front.common.CheckParams;
import com.yl.dpay.front.common.DpayException;
import com.yl.dpay.front.model.CustomerReqInfo;
import com.yl.dpay.front.model.DpayInfoBean;
import com.yl.dpay.front.model.DpayQueryReqBean;
import com.yl.dpay.front.model.DpayQueryResBean;
import com.yl.dpay.front.model.DpayTradeReqBean;
import com.yl.dpay.front.model.DpayTradeResBean;
import com.yl.dpay.front.service.CustomerReqInfoService;
import com.yl.dpay.front.service.DataValidateService;
import com.yl.dpay.front.service.DpayService;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;

import net.sf.json.JSONObject;

/**
 * 代付接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月24日
 * @version V1.0.0
 */
@Controller
public class DpayController {
	private static Logger log = LoggerFactory.getLogger(DpayController.class);
	@Resource
	private DataValidateService dataValidateService;
	@Resource
	private DpayService dpayService;
	@Resource
	private CustomerReqInfoService customerReqInfoService;

	@RequestMapping("dpayTrade")
	public void dpayTrade(HttpServletRequest request, HttpServletResponse response) {
		DpayTradeReqBean dpayTradeReqBean = null;
		DpayTradeResBean dpayTradeResBean = null;
		String customerOrderId = null;
		DpayInfoBean dpayInfoBean = null;
		String ip = InetUtils.getRealIpAddr(request);
		String domain = CheckParams.getDomain(request);
        try {
        	BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
			    sb.append(line);
			}
			dpayTradeReqBean = JsonUtils.toObject(sb.toString(), DpayTradeReqBean.class);
		} catch (IOException e1) {
			log.info("代付接口下单接收参数异常：{}", e1);
		}

		try {
			if(dpayTradeReqBean == null){
				throw new DpayRuntimeException(DpayExceptionEnum.PARAMSERR.getCode(), DpayExceptionEnum.getConvertMsg(DpayExceptionEnum.PARAMSERR.getCode()));
			}
			log.info("代付接口下单原始请求信息：{}", dpayTradeReqBean);
			CheckParams.validateParams(dpayTradeReqBean);
			dataValidateService.checkCustomerAndReferer(dpayTradeReqBean.getCustomerNo(),ip,domain);
			String cipherText = dataValidateService.decryptData(dpayTradeReqBean.getCustomerNo(), dpayTradeReqBean.getCipherText());
			dpayInfoBean = CheckParams.convertApplyData(cipherText);

			log.info("商户号为【{}】的代付下单原始请求明文：{}", dpayTradeReqBean.getCustomerNo(), dpayInfoBean.toString());
			customerOrderId = dpayInfoBean.getCutomerOrderCode();
			dpayTradeResBean = dpayService.singleRequest(dpayInfoBean, dpayTradeReqBean.getCustomerNo());
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
		}

		dpayTradeResBean.setResponseCode(DpayExceptionEnum.getConvertCode(dpayTradeResBean.getResponseCode()));
		dpayTradeResBean.setResponseMsg(DpayExceptionEnum.getConvertMsg(dpayTradeResBean.getResponseCode()));

		try {
			customerReqInfoService.create(new CustomerReqInfo(dpayTradeReqBean, dpayInfoBean,ip,domain));
		} catch (Exception e) {
			log.error("商户号:[{}],代付记录下单请求出异常了！异常信息:{}", dpayTradeReqBean.getCustomerNo(), e);
		}

		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("customerNo", dpayTradeReqBean.getCustomerNo());
		resMap.put("version", dpayTradeReqBean.getVersionCode());
//		resMap.put("responseCode", dpayTradeResBean.getResponseCode());
//		resMap.put("responseMsg", dpayTradeResBean.getResponseMsg());
		
		log.info("商户号为【{}】的代付下单原始响应明文：{}", dpayTradeReqBean.getCustomerNo(), dpayTradeResBean.toString());
		try {
			resMap.put("cipherText", dataValidateService.encryptData(dpayTradeReqBean.getCustomerNo(), dpayTradeResBean));
		} catch (Exception e) {
			log.error("商户号:[{}],代付加密响应参数异常！异常信息:{}", dpayTradeReqBean.getCustomerNo(), e);
		}
		
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONObject.fromObject(resMap).toString());
			response.getWriter().close();
		} catch (IOException e) {
			log.error("商户号:[{}],订单号:[{}]代付交易响应参数异常！异常信息:{}", dpayTradeReqBean.getCustomerNo(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("dpayQuery")
	public void dpayQuery( HttpServletRequest request, HttpServletResponse response) {
		DpayQueryReqBean dpayQueryReqBean = null;
		DpayQueryResBean dpayQueryResBean = new DpayQueryResBean();
		String ip = InetUtils.getRealIpAddr(request);
		String domain = CheckParams.getDomain(request);
		try {
        	BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
			    sb.append(line);
			}
			dpayQueryReqBean = JsonUtils.toObject(sb.toString(), DpayQueryReqBean.class);
		} catch (IOException e1) {
			log.info("代付接口查询接收参数异常：{}", e1);
		}
		String customerOrderCode = "";
		try {
			log.info("代付接口查询原始请求信息：{}", dpayQueryReqBean);
			CheckParams.validateParams(dpayQueryReqBean);
			dataValidateService.checkCustomerAndReferer(dpayQueryReqBean.getCustomerNo(),ip,domain);
			String cipherText = dataValidateService.decryptData(dpayQueryReqBean.getCustomerNo(),dpayQueryReqBean.getCipherText());

			log.info("商户号为【{}】的代付查询原始请求明文：{}", dpayQueryReqBean.getCustomerNo(), cipherText);
			Map<String,String> queryParams = JsonUtils.toObject(cipherText, HashMap.class);
			if(queryParams == null || StringUtils.isBlank(queryParams.get("customerOrderCode"))){
				throw new DpayException(DpayExceptionEnum.PARAMSERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.PARAMSERR.getCode()));
			}
			customerOrderCode = (String)queryParams.get("customerOrderCode");
			dpayQueryResBean = dpayService.findByCustOrderId(dpayQueryReqBean.getCustomerNo(), customerOrderCode);
		} catch (Exception e) {
			log.error("商户号:[{}],订单号:[{}]代付查询出异常了！异常信息:{}", dpayQueryReqBean.getCustomerNo(), customerOrderCode, e);
			if(dpayQueryResBean == null){
				dpayQueryResBean = new DpayQueryResBean();
			}
			dpayQueryResBean = dpayQueryResBean.convertResCode(e, dpayQueryResBean);
			dpayQueryResBean.setCutomerOrderCode(customerOrderCode);
			dpayQueryResBean.setCustomerNo(dpayQueryReqBean.getCustomerNo());
		}
		dpayQueryResBean.setResponseCode(DpayExceptionEnum.getConvertCode(dpayQueryResBean.getResponseCode()));
		dpayQueryResBean.setResponseMsg(DpayExceptionEnum.getConvertMsg(dpayQueryResBean.getResponseCode()));

		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("customerNo", dpayQueryResBean.getCustomerNo());
		resMap.put("cipherText", dataValidateService.encryptData(dpayQueryResBean.getCustomerNo(), dpayQueryResBean));

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONObject.fromObject(resMap).toString());
			response.getWriter().close();
		} catch (IOException e) {
			log.error("商户号:[{}],订单号:[{}]代付查询响应参数异常！异常信息:{}", dpayQueryReqBean.getCustomerNo(), e);
		}
	}

	@RequestMapping("callback")
	@ResponseBody
	public void callback(HttpServletRequest request, HttpServletResponse response){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			response.getWriter().write("SUCCESS");
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
