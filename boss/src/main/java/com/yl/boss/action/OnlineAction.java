package com.yl.boss.action;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.Constant;
import com.yl.boss.bean.InterfacePolicyQueryBean;
import com.yl.boss.bean.PartnerRouterQueryBean;
import com.yl.boss.bean.TradeOrderBean;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Customer;
import com.yl.boss.enums.OperateType;
import com.yl.boss.service.CustomerService;
import com.yl.boss.utils.DateUtils;
import com.yl.online.gateway.hessian.SalesNotifyHessianService;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.model.model.Order;
import com.yl.online.trade.hessian.*;
import com.yl.online.trade.hessian.bean.*;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceProviderQueryBean;
import com.yl.payinterface.core.hessian.InterfaceProviderHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.realAuth.hessian.RoutingTemplateHessianService;
import org.codehaus.jackson.type.TypeReference;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 交易系统控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class OnlineAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 2129120623360256242L;

	private InterfaceProviderHessianService interfaceProviderHessianService;
	private OnlineInterfacePolicyHessianService onlineInterfacePolicyHessianService;
	private PayInterfaceHessianService payInterfaceHessianService;
	private OnlinePartnerRouterHessianService onlinePartnerRouterHessianService;
	private OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	private List<InterfaceProviderQueryBean> interfaceProviderQueryBeans;
	private List<InterfaceInfoBean> interfaceInfoBeans;
	private InterfacePolicyBean interfacePolicyBean;
	private InterfacePolicyQueryBean interfacePolicyQueryBean;
	private List<InterfacePolicyProfileBean> profiles;
	private String interfaceType;
	private List<InterfacePolicyBean> interfacePolicyBeans;
	private List<PartnerRouterProfileBean> partnerProfiles;
	private PartnerRouterBean partnerRouterBean;
	private PartnerRouterQueryBean partnerRouterQueryBean;
	private Object partnerRouters;
	private Object interfacePolicys;
	private Page page;
	private Object tradeOrders;
	private String orderCode;
	private Object tradeOrder;
	private List<Payment> payments;
	private TradeOrderBean tradeOrderBean;
	private String sumInfo;
	private List<Order> order;
	private OperateType operateType;
	private String customerNo;
	private String productType;
	private String startTime;
	private String endTime;
	private Double maxAmount;
	private Double minAmount;
	private Double dayMax;
	private String reason;
	private String operator;
	private Object customerCofig;
	private OnlineCustomerConfigHessianService onlineCustomerConfigHessianService;
	private OnlineCustomerConfigHistoryHessianService onlineCustomerConfigHistoryHessianService;
	private CustomerConfig customerConfig;
	private CustomerService customerService;
	private String msg;
	private List<InterfaceInfoBean> listInterfaceInfoBean = new ArrayList<InterfaceInfoBean>();
	private Map<String,String> MaplistInterfaceInfoBean = new HashMap<String,String>();
	private Map<String,String> MapInterfacePolicyBean = new HashMap<String,String>();
	private String orderTimeStart;
	private String orderTimeEnd;
	private String successPayTimeStart;
	private String successPayTimeEnd;
	private SalesNotifyHessianService salesNotifyHessianService;
	private Map<String, String> notifyParams = new HashMap<>();
	private String notifyTime;
	private String notifyStatus;
	private String notifyCount;
	private String notifyUrl;
	private String agentNo;
	private RoutingTemplateHessianService routingTemplateHessianService;
	private List<Map<String, Object>> result;
	
	/**
	 * 查询所有提供方信息
	 * 
	 * @return
	 */
	public String findAllProvider() {
		interfaceProviderQueryBeans = interfaceProviderHessianService.findAllProvider();
		return "findAllProvider";
	}

	/**
	 * 查询所有接口信息
	 * 
	 * @return
	 */
	public String findAllInterfaceInfo() {
		interfaceInfoBeans = payInterfaceHessianService.queryInterfaceInfo();
		return "findAllInterfaceInfo";
	}

	/**
	 * 添加路由信息
	 * 
	 * @return
	 */
	public String routerAdd() {
		try {
			for (int i = interfacePolicyBean.getProfiles().size() - 1; i >= 0; i--) {
				if (interfacePolicyBean.getProfiles().get(i) == null) {
					interfacePolicyBean.getProfiles().remove(i);
				}
			}
			
			onlineInterfacePolicyHessianService.createInterfacePolicy(interfacePolicyBean);
			getHttpRequest().setAttribute("msg", "true");
		} catch (Exception e) {
			getHttpRequest().setAttribute("msg", "false");
		}
		return "routerAdd";
	}

	/**
	 * 根据接口类型查询路由模板
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String findRouterTemplates() {
		if (interfaceType.equals("BINDCARD_AUTH")) {
			List<Map> list = routingTemplateHessianService.findAllTemplate();
			if (list != null) {
				result = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = new HashMap<>();
					map = list.get(i);
					if (map.get("BUSINESS_TYPE").equals("BINDCARD_AUTH")) {
						if (map.get("STATUS").equals("NORMAL")) {
							map.put("status", "TRUE");
						} else {
							map.put("status", "FALSE");
						}
						map.put("code", map.get("CODE"));
						map.put("name", map.get("NAME"));
						result.add(map);
					}
				}
			}
			return SUCCESS;
		} else {
			interfacePolicyBeans = onlineInterfacePolicyHessianService.queryInterfacePolicyByInterfaceType(interfaceType);
		}
		return "findRouterTemplates";
	}

	/**
	 * 添加商户路由
	 * 
	 * @return
	 */
	public String partnerRouterAdd() {
		try {
			for (int i = partnerProfiles.size() - 1; i >= 0; i--) {
				if (partnerProfiles.get(i) == null) {
					partnerProfiles.remove(i);
				}
			}
			partnerRouterBean.setProfiles(partnerProfiles);
			onlinePartnerRouterHessianService.createPartnerRouter(partnerRouterBean);
			getHttpRequest().setAttribute("msg","true");
		} catch (Exception e) {
			getHttpRequest().setAttribute("msg","false");
		}
		return "partnerRouterAdd";
	}
	
	
	/**
	 * 根据ID查找商户交易配置信息
	 */
	public String findConfigById(){
		customerConfig = onlineCustomerConfigHessianService.findById(customerConfig.getId());
		return "ConfigUpdata";
	}
	
	/**
	 * 根据商户编号查询时候存在  同时反馈商户全称
	 */
	public String checkCustomerNo(){
		Customer customer = customerService.findByCustNo(customerNo);
		if(customer != null){
			msg = customer.getFullName();
		}else{
			msg = "FALSE";
		}
		return SUCCESS;
	}
	
	/**
	 * 商户交易配置修改
	 */
	public String customerConfigUpdata(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		customerConfig.setCreateTime(new Date());
		onlineCustomerConfigHessianService.modifyConfig(customerConfig,reason,auth.getRealname());
		return SUCCESS;
	}
	
	/**
	 * 商户交易配置历史查询
	 */
	@SuppressWarnings("rawtypes")
	public String customerConfigHistory(){
		Map<String, Object> params = ObjectToMap(customerConfig);
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		page = (Page) onlineCustomerConfigHistoryHessianService.findAllById(page, params);
		customerCofig = page.getObject();
		return "ConfigHistory";
	}
	
	/**
	 * 新增配置
	 */
	public String createConfig(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerNo", customerNo);
		params.put("productType", productType);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("maxAmount", maxAmount);
		params.put("minAmount", minAmount);
		params.put("dayMax", dayMax);
		onlineCustomerConfigHessianService.create(params,reason,auth.getRealname());
		return SUCCESS;
	}
	
	public String queryProductTypeExistsByCustNo(){
		msg = String.valueOf(onlineCustomerConfigHessianService.queryProductTypeExistsByCustNo(getHttpRequest().getParameter("customerNo"), getHttpRequest().getParameter("productType")));
		return SUCCESS;
	}
	
	/**
	 * 分页查询商户交易配置
	 */
	public String customerConfigQuery(){
		Map<String, Object> params = ObjectToMap(customerConfig);
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		page = (Page) onlineCustomerConfigHessianService.findAll(page, params);
		customerCofig = page.getObject();
		return "ConfigQuery";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public String findAllTradeOrderAndFee() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!successPayTimeStart.equals("")) {
			tradeOrderBean.setSuccessPayTimeStart(sdf.parse(successPayTimeStart));
		}
		if (!successPayTimeEnd.equals("")) {
			tradeOrderBean.setSuccessPayTimeEnd(sdf.parse(successPayTimeEnd));
		}
		if (!orderTimeStart.equals("")) {
			tradeOrderBean.setOrderTimeStart(sdf.parse(orderTimeStart));
		}
		if (!orderTimeEnd.equals("")) {
			tradeOrderBean.setOrderTimeEnd(sdf.parse(orderTimeEnd));
		}
		Map<String, Object> params = ObjectToMap(tradeOrderBean);
		if (agentNo!=null){
			params.put("agentNo",agentNo);
		}
		params.put("system", "BOSS");
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		page = (Page) onlineTradeQueryHessianService.findAllTradeOrderAndFee(page, params);
		tradeOrders = page.getObject();
		return "findAllTradeOrderAndFee";
	}

	/**
	 * 商户路由分页查询
	 * 
	 * @return
	 * @author qiujian 2016年10月4日
	 */
	public String findAllPartnerRouter() {
		Map<String, Object> params = ObjectToMap(partnerRouterQueryBean);
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		page = (Page) onlinePartnerRouterHessianService.findAllPartnerRouter(page, params);
		partnerRouters = page.getObject();
		return "findAllPartnerRouter";
	}

	/**
	 * 跳转到商户路由查看详细、修改信息
	 * 
	 * @return
	 * @author qiujian 2016年10月4日
	 */
	public String toPartnerRouterMofidyOrDetail() {
		if (operateType.equals(OperateType.QUERY)) {
			partnerRouterBean = onlinePartnerRouterHessianService.queryPartnerRouterByCode(partnerRouterBean.getCode());
			partnerProfiles = partnerRouterBean.getProfiles();
			
//			for (PartnerRouterProfileBean p : partnerProfiles) {
//				interfacePolicyBean = onlineInterfacePolicyHessianService.queryInterfacePolicyByCode(p.getTemplateInterfacePolicy());
//				//listInterfacePolicyBean.add(interfacePolicyBean);
//			}
			
			//版本2
			List<InterfacePolicyBean> listInterfacePolicyBean = new ArrayList<InterfacePolicyBean>();
			
			//循环匹配出需要的模板
			for (PartnerRouterProfileBean p : partnerProfiles) {
				if(p.getTemplateInterfacePolicy() != null && p.getTemplateInterfacePolicy() != ""){
					interfacePolicyBean = onlineInterfacePolicyHessianService.queryInterfacePolicyByCode(p.getTemplateInterfacePolicy());
					listInterfacePolicyBean.add(interfacePolicyBean);
				}
			}
			//循环筛选出已存在的，保证当前所需无重复
			for (int i = 0; i < listInterfacePolicyBean.size(); i++) {
				MapInterfacePolicyBean.put(listInterfacePolicyBean.get(i).getCode(), listInterfacePolicyBean.get(i).getName());
			}
			
			return "toPartnerRouterDetail";

		}
		if (operateType.equals(OperateType.UPDATE)) {
			partnerRouterBean = onlinePartnerRouterHessianService.queryPartnerRouterByCode(partnerRouterBean.getCode());
			partnerProfiles = partnerRouterBean.getProfiles();
//			for (PartnerRouterProfileBean p : partnerProfiles) {
//				interfacePolicyBean = onlineInterfacePolicyHessianService.queryInterfacePolicyByCode(p.getTemplateInterfacePolicy());
//				listInterfacePolicyBean.add(interfacePolicyBean);
//			}
			
			//版本2
			List<InterfacePolicyBean> listInterfacePolicyBean = new ArrayList<InterfacePolicyBean>();
			
			//循环匹配出需要的模板
			for (PartnerRouterProfileBean p : partnerProfiles) {
				if(p.getTemplateInterfacePolicy() != null && p.getTemplateInterfacePolicy() != ""){
					interfacePolicyBean = onlineInterfacePolicyHessianService.queryInterfacePolicyByCode(p.getTemplateInterfacePolicy());
					listInterfacePolicyBean.add(interfacePolicyBean);
				}
			}
			
			if(listInterfacePolicyBean != null){
				
				//循环筛选出已存在的，保证当前所需无重复
				for (int i = 0; i < listInterfacePolicyBean.size(); i++) {
					MapInterfacePolicyBean.put(listInterfacePolicyBean.get(i).getCode(), listInterfacePolicyBean.get(i).getName());
				}
				
			}
			
			return "toPartnerRouterMofidy";
		}
		return "toPartnerRouterQuery";
	}

	/**
	 * @Description 查询路由名称
	 * @return
	 * @date 2016年11月15日
	 */
	public String findRouterNames() {
		interfacePolicyBean = onlineInterfacePolicyHessianService.queryInterfacePolicyByCode(interfacePolicyBean.getCode());
		return "findRouterNames";
	}

	/**
	 * 商户路由信息修改
	 * 
	 * @return
	 * @author qiujian 2016年10月6日
	 */
	public String partnerRouterModify() {
		try {
			for (int i = partnerProfiles.size() - 1; i >= 0; i--) {
				if (partnerProfiles.get(i) == null) {
					partnerProfiles.remove(i);
				}
			}
			partnerRouterBean.setProfiles(partnerProfiles);
			this.onlinePartnerRouterHessianService.updatePartnerRouterByPage(partnerRouterBean);
			getHttpRequest().setAttribute("msg", "true");
		} catch (Exception e) {
			getHttpRequest().setAttribute("msg", "false");
		}
		return "toPartnerRouterMofidy";
	}

	/**
	 * 商户模板分页查询
	 * 
	 * @return
	 * @author qiujian 2016年10月4日
	 */
	public String findAllInterfacePolicy() {
		Map<String, Object> params = ObjectToMap(interfacePolicyQueryBean);
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		page = (Page) onlineInterfacePolicyHessianService.findAllInterfacePolicy(page, params);
		interfacePolicys = page.getObject();
		return "findAllInterfacePolicy";
	}

	/**
	 * 跳转到路由模板查看详细、修改信息
	 * 
	 * @return
	 * @author qiujian 2016年10月4日
	 */
	public String toRouterMofidyOrDetail() {
		if (operateType.equals(OperateType.QUERY)) {
			interfacePolicyBean = onlineInterfacePolicyHessianService.queryInterfacePolicyByCode(interfacePolicyBean.getCode());
			
			profiles = interfacePolicyBean.getProfiles();
			interfaceInfoBeans = payInterfaceHessianService.queryInterfaceInfo();
			
			List<InterfaceInfoForRouterBean> listInterfaceInfoForRouterBean = new ArrayList<InterfaceInfoForRouterBean>();
			
			
			for (InterfacePolicyProfileBean p : profiles) {
				if(p.getInterfaceInfos() != null){
					listInterfaceInfoForRouterBean = p.getInterfaceInfos();
					for (InterfaceInfoForRouterBean i : listInterfaceInfoForRouterBean) {
						if(i.getInterfaceCode() != null){
							for (int j = 0; j < interfaceInfoBeans.size(); j++) {
								if(i.getInterfaceCode().equals(interfaceInfoBeans.get(j).getCode()) ){
									MaplistInterfaceInfoBean.put(interfaceInfoBeans.get(j).getCode(),interfaceInfoBeans.get(j).getName());
								}
							}
						}
					}
				}
			}
			
			return "toRouterDetail";

		}
		if (operateType.equals(OperateType.UPDATE)) {
			interfacePolicyBean = onlineInterfacePolicyHessianService.queryInterfacePolicyByCode(interfacePolicyBean.getCode());
			
			profiles = interfacePolicyBean.getProfiles();
			interfaceInfoBeans = payInterfaceHessianService.queryInterfaceInfo();
			
			List<InterfaceInfoForRouterBean> listInterfaceInfoForRouterBean = new ArrayList<InterfaceInfoForRouterBean>();
			
			
			for (InterfacePolicyProfileBean p : profiles) {
				if(p.getInterfaceInfos() != null){
					listInterfaceInfoForRouterBean = p.getInterfaceInfos();
					for (InterfaceInfoForRouterBean i : listInterfaceInfoForRouterBean) {
						if(i.getInterfaceCode() != null){
							for (int j = 0; j < interfaceInfoBeans.size(); j++) {
								if(i.getInterfaceCode().equals(interfaceInfoBeans.get(j).getCode()) ){
									MaplistInterfaceInfoBean.put(interfaceInfoBeans.get(j).getCode(),interfaceInfoBeans.get(j).getName());
								}
							}
						}
					}
				}
			}
			return "toRouterMofidy";
		}
		return "toRouterQuery";
	}

	/**
	 * 路由模板修改
	 * 
	 * @return
	 * @author qiujian 2016年10月9日
	 */
	public String routerModify() {
		try {
			this.onlineInterfacePolicyHessianService.updateInterfacePolicy(interfacePolicyBean);
			msg = "true";
		} catch (Exception e) {
			msg = "false";
		}
		return SUCCESS;
	}

	/**
	 * 查找一个订单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findOrderByCode() {
		tradeOrder = onlineTradeQueryHessianService.findOrderByCode(orderCode);
		payments = (List<Payment>) onlineTradeQueryHessianService.findPaymentByOrderCode(orderCode);
		notifyParams = salesNotifyHessianService.findNotifyInfo(orderCode);
		notifyStatus = notifyParams.get("notifyStatus");
		notifyTime = notifyParams.get("notifyTime");
		notifyCount = notifyParams.get("notifyCount");
		notifyUrl = notifyParams.get("notifyUrl");
		return "findOrderByCode";
	}

	/**
	 * 重新通知
	 */
	public void reNotifyMerOrder(){
		try{
			tradeOrder = onlineTradeQueryHessianService.findOrderByCode(orderCode);
			Order order = (Order)tradeOrder;
			if (StringUtils.isEmpty(order.getNotifyURL())) {
				notifyParams.put("respCode", "OTHER");
				notifyParams.put("msg", "通知地址为空");
				logger.info("重新补发,通知地址为空");
				write(JsonUtils.toJsonString(notifyParams));
				return;
			}
			salesNotifyHessianService.notify(order.getCode(),order.getStatus().name(),order.getRequestCode(),order.getReceiver());
			notifyParams = salesNotifyHessianService.findNotifyInfo(orderCode);
			notifyParams.put("respCode", "SUCCESS");
			write(JsonUtils.toJsonString(notifyParams));
		}catch (Exception e){
			logger.error("", e);
			notifyParams.put("respCode", "FAILED");
			write(JsonUtils.toJsonString(notifyParams));
		}
	}

	private Map<String, Object> ObjectToMap(Object object) {
		Map<String, Object> map = new HashMap<>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(object);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}
		return map;
	}

	/**
	 * 查询统计
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public String payOrderSum() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!successPayTimeStart.equals("")) {
			tradeOrderBean.setSuccessPayTimeStart(sdf.parse(successPayTimeStart));
		}
		if (!successPayTimeEnd.equals("")) {
			tradeOrderBean.setSuccessPayTimeEnd(sdf.parse(successPayTimeEnd));
		}
		if (!orderTimeStart.equals("")) {
			tradeOrderBean.setOrderTimeStart(sdf.parse(orderTimeStart));
		}
		if (!orderTimeEnd.equals("")) {
			tradeOrderBean.setOrderTimeEnd(sdf.parse(orderTimeEnd));
		}
		Map<String, Object> params = ObjectToMap(tradeOrderBean);
		params.put("system", "BOSS");
		if (agentNo!=null){
			params.put("agentNo",agentNo);
		}
		sumInfo = onlineTradeQueryHessianService.orderFeeSum(params);
		Map<String, Object> map = JsonUtils.toObject(sumInfo, HashMap.class);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String am = "0";
		String rf = "0";
		String pf = "0";
		String al = "0";
		if (map != null) {
			if (map.get("rf") != null) {
				rf = df.format(Double.parseDouble(map.get("rf").toString()));
			}
			if (map.get("pf") != null) {
				pf = df.format(Double.parseDouble(map.get("pf").toString()));
			}
			if (map.get("am") != null) {
				am = df.format(Double.parseDouble(map.get("am").toString()));
			}
			if (map.get("al") != null) {
				al = map.get("al").toString();
			}
		}
		Map<String, String> m = new HashMap<String, String>();
		m.put("am", am);
		m.put("pf", pf);
		m.put("rf", rf);
		m.put("al", al);
		sumInfo = JsonUtils.toJsonString(m);
		return "payOrderSum";
	}

	/**
	 * daochu
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public String payOrderExport() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!successPayTimeStart.equals("")) {
			tradeOrderBean.setSuccessPayTimeStart(DateUtils.getMinTime(sdf.parse(successPayTimeStart)));
		}
		if (!successPayTimeEnd.equals("")) {
			tradeOrderBean.setSuccessPayTimeEnd(DateUtils.getMaxTime(sdf.parse(successPayTimeEnd)));
		}
		if (!orderTimeStart.equals("")) {
			tradeOrderBean.setOrderTimeStart(DateUtils.getMinTime(sdf.parse(orderTimeStart)));
		}
		if (!orderTimeEnd.equals("")) {
			tradeOrderBean.setOrderTimeEnd(DateUtils.getMaxTime(sdf.parse(orderTimeEnd)));
		}
		Map<String, Object> params = ObjectToMap(tradeOrderBean);
		if (agentNo!=null){
			params.put("agentNo",agentNo);
		}
		params.put("system", "BOSS");
		String info = onlineTradeQueryHessianService.orderInfoExport(params);
		order = JsonUtils.toObject(info, new TypeReference<List<Order>>() {
		});
		return "payOrderExport";
	}

	/**
	 * ajax根据商户编号查询商户路由信息
	 * @return
	 */
	public String ajaxQueryCustRouteByCustomerNo(){
		try {
			List<Map<String,Object>> result = onlinePartnerRouterHessianService.queryCustRouterByCustomerNo(getHttpRequest().getParameter("customerNo"));
			msg = JsonUtils.toJsonString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setInterfaceProviderHessianService(InterfaceProviderHessianService interfaceProviderHessianService) {
		this.interfaceProviderHessianService = interfaceProviderHessianService;
	}

	public List<InterfaceProviderQueryBean> getInterfaceProviderQueryBeans() {
		return interfaceProviderQueryBeans;
	}

	public void setInterfaceProviderQueryBeans(List<InterfaceProviderQueryBean> interfaceProviderQueryBeans) {
		this.interfaceProviderQueryBeans = interfaceProviderQueryBeans;
	}

	public void setPayInterfaceHessianService(PayInterfaceHessianService payInterfaceHessianService) {
		this.payInterfaceHessianService = payInterfaceHessianService;
	}

	public List<InterfaceInfoBean> getInterfaceInfoBeans() {
		return interfaceInfoBeans;
	}

	public void setInterfaceInfoBeans(List<InterfaceInfoBean> interfaceInfoBeans) {
		this.interfaceInfoBeans = interfaceInfoBeans;
	}

	public InterfacePolicyBean getInterfacePolicyBean() {
		return interfacePolicyBean;
	}

	public void setInterfacePolicyBean(InterfacePolicyBean interfacePolicyBean) {
		this.interfacePolicyBean = interfacePolicyBean;
	}

	public List<InterfacePolicyProfileBean> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<InterfacePolicyProfileBean> profiles) {
		this.profiles = profiles;
	}

	public void setOnlineInterfacePolicyHessianService(OnlineInterfacePolicyHessianService onlineInterfacePolicyHessianService) {
		this.onlineInterfacePolicyHessianService = onlineInterfacePolicyHessianService;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public List<InterfacePolicyBean> getInterfacePolicyBeans() {
		return interfacePolicyBeans;
	}

	public void setInterfacePolicyBeans(List<InterfacePolicyBean> interfacePolicyBeans) {
		this.interfacePolicyBeans = interfacePolicyBeans;
	}

	public List<PartnerRouterProfileBean> getPartnerProfiles() {
		return partnerProfiles;
	}

	public void setPartnerProfiles(List<PartnerRouterProfileBean> partnerProfiles) {
		this.partnerProfiles = partnerProfiles;
	}

	public PartnerRouterBean getPartnerRouterBean() {
		return partnerRouterBean;
	}

	public void setPartnerRouterBean(PartnerRouterBean partnerRouterBean) {
		this.partnerRouterBean = partnerRouterBean;
	}

	public void setOnlinePartnerRouterHessianService(OnlinePartnerRouterHessianService onlinePartnerRouterHessianService) {
		this.onlinePartnerRouterHessianService = onlinePartnerRouterHessianService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setOnlineTradeQueryHessianService(OnlineTradeQueryHessianService onlineTradeQueryHessianService) {
		this.onlineTradeQueryHessianService = onlineTradeQueryHessianService;
	}

	public Object getTradeOrders() {
		return tradeOrders;
	}

	public void setTradeOrders(Object tradeOrders) {
		this.tradeOrders = tradeOrders;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Object getTradeOrder() {
		return tradeOrder;
	}

	public void setTradeOrder(Object tradeOrder) {
		this.tradeOrder = tradeOrder;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public TradeOrderBean getTradeOrderBean() {
		return tradeOrderBean;
	}

	public void setTradeOrderBean(TradeOrderBean tradeOrderBean) {
		this.tradeOrderBean = tradeOrderBean;
	}

	public String getSumInfo() {
		return sumInfo;
	}

	public void setSumInfo(String sumInfo) {
		this.sumInfo = sumInfo;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public PartnerRouterQueryBean getPartnerRouterQueryBean() {
		return partnerRouterQueryBean;
	}

	public void setPartnerRouterQueryBean(PartnerRouterQueryBean partnerRouterQueryBean) {
		this.partnerRouterQueryBean = partnerRouterQueryBean;
	}

	public Object getPartnerRouters() {
		return partnerRouters;
	}

	public void setPartnerRouters(Object partnerRouters) {
		this.partnerRouters = partnerRouters;
	}

	public OperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}

	public InterfacePolicyQueryBean getInterfacePolicyQueryBean() {
		return interfacePolicyQueryBean;
	}

	public void setInterfacePolicyQueryBean(InterfacePolicyQueryBean interfacePolicyQueryBean) {
		this.interfacePolicyQueryBean = interfacePolicyQueryBean;
	}

	public Object getInterfacePolicys() {
		return interfacePolicys;
	}

	public void setInterfacePolicys(Object interfacePolicys) {
		this.interfacePolicys = interfacePolicys;
	}

	public List<InterfaceInfoBean> getListInterfaceInfoBean() {
		return listInterfaceInfoBean;
	}

	public void setListInterfaceInfoBean(List<InterfaceInfoBean> listInterfaceInfoBean) {
		this.listInterfaceInfoBean = listInterfaceInfoBean;
	}

	public Map<String, String> getMaplistInterfaceInfoBean() {
		return MaplistInterfaceInfoBean;
	}

	public void setMaplistInterfaceInfoBean(Map<String, String> maplistInterfaceInfoBean) {
		MaplistInterfaceInfoBean = maplistInterfaceInfoBean;
	}

	public Map<String, String> getMapInterfacePolicyBean() {
		return MapInterfacePolicyBean;
	}

	public void setMapInterfacePolicyBean(Map<String, String> mapInterfacePolicyBean) {
		MapInterfacePolicyBean = mapInterfacePolicyBean;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public Object getCustomerCofig() {
		return customerCofig;
	}

	public void setCustomerCofig(Object customerCofig) {
		this.customerCofig = customerCofig;
	}

	public CustomerConfig getCustomerConfig() {
		return customerConfig;
	}

	public void setCustomerConfig(CustomerConfig customerConfig) {
		this.customerConfig = customerConfig;
	}

	public void setOnlineCustomerConfigHessianService(
			OnlineCustomerConfigHessianService onlineCustomerConfigHessianService) {
		this.onlineCustomerConfigHessianService = onlineCustomerConfigHessianService;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	public Double getDayMax() {
		return dayMax;
	}

	public void setDayMax(Double dayMax) {
		this.dayMax = dayMax;
	}

	public void setOnlineCustomerConfigHistoryHessianService(
			OnlineCustomerConfigHistoryHessianService onlineCustomerConfigHistoryHessianService) {
		this.onlineCustomerConfigHistoryHessianService = onlineCustomerConfigHistoryHessianService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(String orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public String getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(String orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public String getSuccessPayTimeStart() {
		return successPayTimeStart;
	}

	public void setSuccessPayTimeStart(String successPayTimeStart) {
		this.successPayTimeStart = successPayTimeStart;
	}

	public String getSuccessPayTimeEnd() {
		return successPayTimeEnd;
	}

	public void setSuccessPayTimeEnd(String successPayTimeEnd) {
		this.successPayTimeEnd = successPayTimeEnd;
	}

	public Map<String, String> getNotifyParams() {
		return notifyParams;
	}

	public void setNotifyParams(Map<String, String> notifyParams) {
		this.notifyParams = notifyParams;
	}

	public void setSalesNotifyHessianService(SalesNotifyHessianService salesNotifyHessianService) {
		this.salesNotifyHessianService = salesNotifyHessianService;
	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(String notifyCount) {
		this.notifyCount = notifyCount;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public RoutingTemplateHessianService getRoutingTemplateHessianService() {
		return routingTemplateHessianService;
	}

	public void setRoutingTemplateHessianService(RoutingTemplateHessianService routingTemplateHessianService) {
		this.routingTemplateHessianService = routingTemplateHessianService;
	}

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
}