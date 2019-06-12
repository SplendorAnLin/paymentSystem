package com.yl.online.trade.test.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通用跳转
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月22日
 * @version V1.0.0
 */
@Controller
public class SimpleJspController {
	
	
	@RequestMapping("toTradeSubmit")
	public String receivePayRequest(HttpServletRequest request, HttpServletResponse response) {
		return "/trade/tradeSubmit";
	}
	@RequestMapping("toWXTradeSubmit")
	public String toWXTradeSubmit(HttpServletRequest request, HttpServletResponse response) {
		return "/trade/WXtradeSubmit";
	}

	@RequestMapping("toPay")
    public String toPay(){
	    return "/trade/toPay";
    }

	@RequestMapping("toRealAuth")
	public String toRealAuth(HttpServletRequest request, HttpServletResponse response) {
		return "/trade/realAuthSubmit";
	}
	@RequestMapping("toQuick")
	public String toQuick(HttpServletRequest request, HttpServletResponse response) {
		return "/trade/quickTradeSubmit";
	}

}
