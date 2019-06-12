package com.yl.chat.web.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lefu.commons.utils.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.api.interfaces.AgentOperInterface;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.chat.service.UserService;
import com.yl.chat.wecaht.model.User;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.dpay.hessian.service.DpayFacade;

/**
 * 结算
 * 
 * @author Administrator
 *
 */
@Controller
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Resource
	DpayFacade dpayFacade;

	@Resource
	private UserService userService;

	@Resource
	private CustOperInterface custOperInterface;

	@Resource
	private AgentOperInterface agentOperInterface;
	
	List<Map<String, Object>> dayquerylist;

	Map<String, Object> dpaydetail;

	@Resource
	private CustomerInterface customerInterface;

	Customer cust;

	CustomerSettle custSettle;


	@RequestMapping("dfQuery")
	public String dfQuery(){
		return "pay/dfQuery";
	}


	/**
	 * 结算订单查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "dfQueryRequest" )
	public void dfQueryResult(HttpServletRequest request,int currentPage,int totalPage,String startCompleteDate,String endCompleteDate,PrintWriter out) {
		try {
			Page page = new Page();
			page.setTotalPage(totalPage);
			page.setCurrentPage(currentPage);
			String openid = (String) request.getSession().getAttribute("openId");
			User user = userService.findUserOpenid(openid);// 根据openid 查询商户编号
			Map<String, Object> params = new HashMap<>();//交易订单请求参数
			params.put("ownerId", user.getCustomer());
			params.put("startCompleteDate", startCompleteDate+" 00:00:00");
			params.put("endCompleteDate", endCompleteDate+" 23:59:59");
			page = dpayFacade.findrequest(params, page);
//			List<Map<String, Object>> list = (List<Map<String, Object>>) resMap.get("result");
//			page = (Page) resMap.get("page");
			JSONObject resJson = new JSONObject();
			resJson.put("result", page.getObject());
			resJson.put("page", page);
			out.print(resJson);
		} catch (Exception e) {
			logger.error("结算订单查询异常", e);
		}
	}
	

	/**
	 * 结算订单详情
	 */
	@RequestMapping("dfQueryDetail")
	public String dfQueryDetail(HttpServletRequest request,String requestNo, Model model){
		try {
			String openid = (String) request.getSession().getAttribute("openId");
			User user = userService.findUserOpenid(openid);// 根据openid 查询商户编号
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ownerId", user.getCustomer());
			params.put("requestNo", requestNo);
			Map<String, Object> dpaydetail = dpayFacade.findrequestDetail(params);
			cust = customerInterface.getCustomer(user.getCustomer());
			model.addAttribute("cust", cust);
			model.addAttribute("dpaydetail", dpaydetail);
		} catch (Exception e) {
			logger.error("结算详情异常", e);
		}
		return "pay/dfQueryResultDetail";
	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public CustomerSettle getCustSettle() {
		return custSettle;
	}

	public void setCustSettle(CustomerSettle custSettle) {
		this.custSettle = custSettle;
	}

	public List<Map<String, Object>> getDayquerylist() {
		return dayquerylist;
	}

	public void setDayquerylist(List<Map<String, Object>> dayquerylist) {
		this.dayquerylist = dayquerylist;
	}
}