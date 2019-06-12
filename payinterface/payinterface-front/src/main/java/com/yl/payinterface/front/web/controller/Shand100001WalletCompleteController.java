package com.yl.payinterface.front.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yl.payinterface.front.utils.Shand100001.CertUtil;
import com.yl.payinterface.front.utils.Shand100001.CryptoUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import cfca.org.bouncycastle.util.encoders.Base64;

/**
 * 衫德微信支付通知
 * 
 * @author AnLin
 * @since 2016年4月24日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/shandWallet")
public class Shand100001WalletCompleteController {

	private Logger logger = LoggerFactory.getLogger(Shand100001WalletCompleteController.class);

	@Resource
	private WalletpayHessianService walletpayHessianService;

	static{
		try {
			CertUtil.init("/home/cer/sand/sand.cer", "/home/cer/sand/sand.pfx", "123456");
		} catch (Exception e) {

		}
	}

	@RequestMapping(value = "completePay")
	public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
		logger.info("*************** 衫德银联钱包支付通知START ****************");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			logger.info("衫德银联钱包支付通知原文参数: [{}]", sb.toString());
			String [] paramsStr = sb.toString().split("&");
			Map<String, String> paramsMap = new HashMap<>();
			for(String param : paramsStr){
				String [] tmp = param.split("=");
				paramsMap.put(tmp[0], tmp.length>1?URLDecoder.decode(tmp[1]):"");
			}
			JSONObject body = JSONObject.fromObject(JSONObject.fromObject(paramsMap.get("data")).get("body"));
			logger.info("衫德银联钱包支付通知 响应头: [{}]", body);
			logger.info("衫德银联钱包支付通知 响应体: [{}]", JSONObject.fromObject(paramsMap.get("data")).get("head"));
			boolean flag = CryptoUtil.verifyDigitalSign(paramsMap.get("data").getBytes("UTF-8"), Base64.decode(paramsMap.get("sign")), CertUtil.getPublicKey(), "SHA1WithRSA");
			logger.info("衫德银联钱包支付 签名验证结果: [{}]", flag);
			if(!flag){
				logger.info("衫德银联钱包支付 签名验证失败: [{}]", sb.toString());
			}else{
				Map<String,String> walletPayResponseBean = new HashMap<>();
				walletPayResponseBean.put("interfaceCode", "SD100001-UNIONPAY_NATIVE");
				walletPayResponseBean.put("interfaceRequestID", String.valueOf(body.get("orderCode")));
				if("1".equals(String.valueOf(body.get("orderStatus")))){
					walletPayResponseBean.put("tranStat", "SUCCESS");
				}else{
					walletPayResponseBean.put("tranStat", "UNKNOWN");
				}
				walletPayResponseBean.put("amount", String.valueOf(Double.parseDouble((String)body.get("buyerPayAmount"))/100));
				walletPayResponseBean.put("responseCode", String.valueOf(body.get("orderStatus")));
				if("1".equals(String.valueOf(body.get("orderStatus")))){
					walletPayResponseBean.put("responseMessage", "交易成功");
				}
				walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				walletPayResponseBean.put("completeTime", body.getString("payTime"));
				walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
				walletPayResponseBean.put("interfaceOrderID", body.getString("tradeNo"));
				walletpayHessianService.complete(walletPayResponseBean);
				response.getWriter().write("respCode=000000");
			}
		} catch (Exception e) {
			logger.error("衫德银联钱包支付通知处理异常：{}", e);
		}
	}
}