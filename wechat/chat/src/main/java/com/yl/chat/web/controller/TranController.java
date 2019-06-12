package com.yl.chat.web.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lefu.commons.utils.Page;
import com.yl.agent.api.interfaces.AgentOperInterface;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.chat.service.UserService;
import com.yl.chat.wecaht.model.User;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.online.model.model.Order;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;

/**
 * 交易
 * 
 * @author Administrator
 *
 */

@Controller
public class TranController {

	private static final Logger logger = LoggerFactory.getLogger(TranController.class);

	@Resource
	private UserService userService;

	@Resource
	private OnlineTradeQueryHessianService onlineTradeQueryHessianService;

	@Resource
	private CustOperInterface custOperInterface;

	@Resource
	private AgentOperInterface agentOperInterface;

	@Resource
	private CustomerInterface customerInterface;

	List<Order> orders;

	Order order;

	Customer customer;


	/**
	 * 交易订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("payorder")
	public String payOrder(HttpServletRequest req, HttpServletResponse resp) {
		return "online/payOrderQuery";
	}


	/**
	 * 交易订单结果
	 */
	@RequestMapping(value = "payOrderResult", produces = "text/html;charset=UTF-8" )
	public void payOrderRsult(HttpServletRequest request, PrintWriter out,int currentPage,int totalPage, String startSuccessPayTime, String endSuccessPayTime) {
		try {
			Page page = new Page();
			page.setTotalPage(totalPage);
			page.setCurrentPage(currentPage);
			String openid = (String) request.getSession().getAttribute("openId");
			User user = userService.findUserOpenid(openid);// 根据openid 查询商户编号
			Map<String, Object>  params = new HashMap<>();//交易订单请求参数
			params.put("payer", user.getCustomer());
			params.put("startSuccessPayTime", startSuccessPayTime + " 00:00:00");
			params.put("endSuccessPayTime", endSuccessPayTime + " 23:59:59");
			page = onlineTradeQueryHessianService.findTradeOrder(page, params);
//			List<Map<String, Object>> list = (List<Map<String, Object>>) resMap.get("result");
//			page = (Page) resMap.get("page");
			JSONObject resJson = new JSONObject();
			resJson.put("result", page.getObject());
			resJson.put("page", page);
			out.print(resJson);
	    }	catch (Exception e) {
			logger.error("交易订单结果异常{}", e);
		}
	}

	/**
	 * 交易详情
	 * @return
	 */
	@RequestMapping("payOrderDetail")
	public String payOrderDetail(HttpServletRequest request,Model model,String code){
		 try {
		 	String openid = (String)request.getSession().getAttribute("openId");
		 	User user = userService.findUserOpenid(openid);//根据openid 查询商户编号
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("payer", user.getCustomer());
			params.put("code", code);
			order = onlineTradeQueryHessianService.findTradeOrderDetail(params);
			customer = customerInterface.getCustomer(user.getCustomer());
		 	model.addAttribute("customer", customer);
		 	model.addAttribute("order", order);
		} catch (Exception e) {
			logger.error("交易订单详情异常", e);
			return "error/error";
		}
		return  "online/payOrderQueryDetail";
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}