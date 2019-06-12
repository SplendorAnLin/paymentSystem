package com.yl.online.gateway.web.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.boss.api.bean.Agent;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.sms.SmsUtils;

@Controller
@RequestMapping("quickOpen")
public class QuickOpenCustomerController {

	private static final Logger logger = LoggerFactory.getLogger(QuickOpenCustomerController.class);

	private static final String KEY = "713120d0cea3c08a751f1d8fb38aa301";

	@Resource
	private CustomerInterface customerInterface;

	@Resource
	private AgentInterface agentInterface;

	@RequestMapping("open")
	public void open(HttpServletResponse response, HttpServletRequest request, String phoneNo, String password, String verifyCode, String agentNo) {
		Object check = request.getSession().getAttribute("verifyCode");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			if (StringUtils.isNotBlank(verifyCode) && verifyCode.equals(check)) {
				Customer customer = new Customer();
				customer.setPhoneNo(phoneNo);
				customer.setAgentNo(agentNo);
				customerInterface.createQuick(customer, password);
				writer.write("SUCCESS");
			} else {
				writer.write("VERIFYCODE_ERROR");
			}
		} catch (Exception e) {
			logger.info("快速入网失败：{}", e);
			writer.write("ERROR");
		}
	}


	@RequestMapping("quickOpensuccess")
	public String quickOpensuccess() {
		return "quickOpen/success";
	}


	@RequestMapping("appOpenCustomer")
	public String appOpenCustomer() {
		return "quickOpen/appOpenCustomer";
	}


	@RequestMapping("toOpen")
	public String toOpen(Model model, String agentNo, String checkNo) {
		String signSource = agentNo + KEY;
        try {
            String calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes("utf-8"));
            if (!calSign.equals(checkNo)) {
            	model.addAttribute("merchantCode", agentNo);
            	model.addAttribute("responseMessage", "请输入正确的分享链接");
            	return "error/error";
            }
        } catch (UnsupportedEncodingException e) {
        	model.addAttribute("merchantCode", agentNo);
        	model.addAttribute("responseMessage", "请输入正确的分享链接");
            return "error/error";
        }
		model.addAttribute("agentNo", agentNo);
		Agent agent = agentInterface.getAgent(agentNo);
		model.addAttribute("agentName", agent.getFullName());
		return "quickOpen/quickOpenCustomer";
	}


	@RequestMapping(value = "sendVerifyCode", method = RequestMethod.POST)
	public void sendVerifyCode(HttpServletRequest request, HttpServletResponse response, Model model, String phone) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String check = customerInterface.checkPhone(phone);
			if ("TRUE".equals(check)) {
				if (request.getSession() == null) {
					HttpSession session = request.getSession(true);
					session.getAttribute("AUTH");
				}
				Random random = new Random();
				// 生成验证码6位随机数字
				String verifyCode = "" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
				// 放到session中
				request.getSession().setAttribute("verifyCode", verifyCode);
				// 发送短信
				SmsUtils.sendCustom(String.format(com.yl.online.gateway.Constants.SMS_QROPEN_ACC, verifyCode), phone);
			}
			writer.print(check);
		} catch (Exception e) {
			logger.info("获取验证码失败：{}", e);
			writer.write("ERROR");
		}
	}


	@RequestMapping("appOpen")
	public void appOpen(HttpServletRequest request, HttpServletResponse response, String agentNo, String phoneNo,
			String password, String verifyCode) {
		Object check = request.getSession().getAttribute("verifyCode");
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			if (StringUtils.isNotBlank(verifyCode) && verifyCode.equals(check)) {
				Customer customer = new Customer();
				customer.setPhoneNo(phoneNo);
				if (StringUtils.isNotBlank(agentNo)) {
					Agent pAgent = agentInterface.findByPhone(agentNo);
					Agent agent = agentInterface.getAgent(agentNo);
					if (agent == null && pAgent == null) {
						printWriter.write("FALSE");
						return;
					}
					if (agent != null && pAgent == null) {
						customer.setAgentNo(agentNo);
					} else if (agent == null && pAgent != null) {
						customer.setAgentNo(pAgent.getAgentNo());
					}
				}
				customerInterface.createQuick(customer, password);
				printWriter.write("SUCCESS");
			} else {
				printWriter.write("VERIFYCODE_ERROR");
			}
		} catch (Exception e) {
			logger.info("快速入网失败：{}", e);
			printWriter.write("ERROR");
		}
	}
}