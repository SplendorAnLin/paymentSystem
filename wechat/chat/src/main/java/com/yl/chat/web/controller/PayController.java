package com.yl.chat.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yl.boss.api.interfaces.DeviceInterface;
import com.yl.chat.service.UserService;
import com.yl.chat.wecaht.model.User;

@Controller
public class PayController {

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);
	
	@Resource
	DeviceInterface deviceInterface;

	@Resource
	private UserService userService;
	
	
	@RequestMapping("payH5")
	public void payH5(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		String openid = (String) request.getSession().getAttribute("openId");
		User user = userService.findUserOpenid(openid);// 根据openid 查询商户编号
		String url=deviceInterface.getDeviceUrl(user.getCustomer());
		try {
			if (url!=null&&!"".equals(url)) {
				response.sendRedirect(url);
			} else {
				session.setAttribute("message", "暂无使用权限，请联系客服！");
				response.sendRedirect("/message");
			}
		} catch (IOException e) {
			logger.error("h5跳转异常:{}",e);
		}
	}
}