package com.yl.online.gateway.web.controller.b2c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.online.model.enums.OrderStatus;
import com.yl.online.model.model.Order;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;

/**
 * 衫德网关支付页面通知
 * 
 * @author 聚合支付有限公司
 * @since 2017年3月7日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/shandGatewayPage")
public class Shand100001GwFrtCompleteController {

	private static Logger logger = LoggerFactory.getLogger(Shand100001GwFrtCompleteController.class);
	@Resource
	private InternetbankHessianService internetbankHessianService;
	@Resource
	private InterfaceRequestHessianService interfaceRequestHessianService;
	@Resource
	private OnlineTradeHessianService onlineTradeHessianService;
	@Resource
	private CustomerInterface customerInterface;
	private static Properties prop = new Properties();

	static {
		try {
			prop.load(new InputStreamReader(Shand100001GwFrtCompleteController.class.getClassLoader().getResourceAsStream("serverHost.properties"), "UTF-8"));
		} catch (IOException e) {
			logger.error("system ClassLoader error:", e);
		}
		// 加载证书
//		try {
//			CertUtil.init("classpath:cer/sand.cer", "classpath:cer/yl-b2c.pfx", "123456");
//		} catch (Exception e) {
//			logger.error("衫德网银支付加载证书异常:{}", e);
//		}
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "completePay")
	public String completeTrade(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("*************** 衫德网关支付页面通知START ****************");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			logger.info("衫德网关支付页面通知原文参数: [{}]", sb.toString());
			String [] paramsStr = sb.toString().split("&");
			Map<String, String> paramsMap = new HashMap<>();
			for(String param : paramsStr){
				String [] tmp = param.split("=");
				paramsMap.put(tmp[0], tmp.length>1?URLDecoder.decode(tmp[1]):"");
			}
			JSONObject body = JSONObject.fromObject(JSONObject.fromObject(paramsMap.get("data")).get("body"));
			JSONObject head = JSONObject.fromObject(JSONObject.fromObject(paramsMap.get("data")).get("head"));
			logger.info("衫德网关支付页面通知 响应体: [{}]", body);
			logger.info("衫德网关支付页面通知 响应头: [{}]", head);
//			logger.info("衫德验证签名:"+CryptoUtil.verifyDigitalSign(paramsMap.get("data").getBytes("UTF-8"), Base64Utils.decode(String.valueOf(head.get("sign"))), CertUtil.getPublicKey(), "SHA1WithRSA"));
			
			Map<String,String> completeMap = new HashMap<>();
			completeMap.put("interfaceCode", "SHAND100001-B2C");
			completeMap.put("interfaceRequestID", String.valueOf(body.get("orderCode")));
			if("P1".equals(String.valueOf(body.get("orderStatus")))){
				completeMap.put("tranStat", "SUCCESS");
			}else{
				completeMap.put("tranStat", "UNKNOW");
			}
			completeMap.put("amount", String.valueOf(Double.parseDouble(String.valueOf(body.get("totalAmount")))));
			completeMap.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			completeMap.put("completeTime", head.getString("respTime"));
			completeMap.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
			completeMap.put("responseCode", head.getString("respCode"));
			completeMap.put("responseMessage", head.getString("respMsg"));
			completeMap.put("interfaceOrderID", body.getString("tradeNo"));
			InternetbankSalesResponseBean internetbankResponseBean = new InternetbankSalesResponseBean();
			internetbankResponseBean.setInterfaceCode("SHAND100001-B2C");
			internetbankResponseBean.setInternetResponseMsg(completeMap);
			internetbankResponseBean.setSynchronize(false);
			internetbankHessianService.complete(null,internetbankResponseBean);
			
			InterfaceRequestQueryBean bean = interfaceRequestHessianService.findRequestByInterfaceReqId(String.valueOf(body.get("orderCode")));

			Order order = onlineTradeHessianService.queryOrderByCode(bean.getBussinessFlowID());
			Customer customer = customerInterface.getCustomer(order.getReceiver());
			try {
				if (order.getStatus() == OrderStatus.SUCCESS) {
					model.addAttribute("customerNo", order.getReceiver());
					model.addAttribute("customerName", customer.getShortName());
					model.addAttribute("amount", order.getAmount());
					model.addAttribute("outOrderId", order.getAmount());
					return "success";
				}
			} catch (Exception e) {
				logger.info("网银支付页面回调异常：{}", e);
				return "error/error";
			}
			return "error/error";
		} catch (Exception e) {
			logger.error("衫德网关支付页面通知处理异常：{}", e);
		}
		return "error/error";

	}

}
