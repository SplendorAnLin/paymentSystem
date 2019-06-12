package com.yl.online.gateway.web.controller.wechat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.hessian.bean.AuthBean;
import com.yl.online.gateway.service.impl.PageNotifyPartnerSalesResult;

/**
 * 微信扫码回调控制器
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/callback")
public class WechatNativeCallbackController {

	private static final Logger logger = LoggerFactory.getLogger(WechatNativeCallbackController.class);
	
	@Resource
	private PageNotifyPartnerSalesResult pageNotifyPartnerSalesResult;
	
	@RequestMapping("wechatNative")
	public void wechatNative(HttpServletResponse response, String orderCode){
		try {
			pageNotifyPartnerSalesResult.pageNotifyPartner(new AuthBean(), response, orderCode);
		} catch (Exception e) {
			logger.info("微信页面支付订单号：{},页面支付失败！异常信息：{}", orderCode, e);
		}
	}
}
