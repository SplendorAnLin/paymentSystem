package com.yl.online.gateway.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.bean.AuthBean;
import com.lefu.hessian.util.HessianSignUtils;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.service.impl.PageNotifyPartnerSalesResult;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.hessian.InternetbankHessianService;

/**
 * 交易完成
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月23日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/callback")
public class MOCKCallbackController {
	
	private static final Logger logger = LoggerFactory.getLogger(MOCKCallbackController.class);
	@Resource
	private InternetbankHessianService internetbankHessianService;
	@Resource
	private PageNotifyPartnerSalesResult pageNotifyPartnerSalesResult;

	@RequestMapping("mock")
	public void complete(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {

			// 获取参数
			String orderid = request.getParameter("orderid");
			String amount = request.getParameter("amount");
			String tranStat = request.getParameter("tranStat");
			String notifyDate = request.getParameter("notifyDate");

			// 封装响应实体Bean
			InternetbankSalesResponseBean internetbankSalesResponseBean = new InternetbankSalesResponseBean();
			internetbankSalesResponseBean.setInterfaceCode("B2C_MOCK");
			internetbankSalesResponseBean.setSynchronize(true);
			Map<String, String> internetResponseMsg = new LinkedHashMap<String, String>();
			internetResponseMsg.put("orderid", orderid);
			internetResponseMsg.put("amount", amount);
			internetResponseMsg.put("tranStat", tranStat);
			internetResponseMsg.put("notifyDate", notifyDate);
			internetbankSalesResponseBean.setInternetResponseMsg(internetResponseMsg);
			
			// 获取交易配置
			AuthBean auth = new AuthBean();
			auth.setInvokeSystem(Constants.APP_NAME);
			auth.setOperator("ICBC_FRONT");
			auth.setTimestamp(System.currentTimeMillis());
			auth.setSign(HessianSignUtils.md5DigestAsHex(StringUtils.concatToSB(auth.toString(), internetbankSalesResponseBean.toString(), "payinterface").toString().getBytes()));
			
			internetbankHessianService.complete(auth, internetbankSalesResponseBean);
			// 通知商户支付结果
			pageNotifyPartnerSalesResult.pageNotifyPartner(auth, response, orderid);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}