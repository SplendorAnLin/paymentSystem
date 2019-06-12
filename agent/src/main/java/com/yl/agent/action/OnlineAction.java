package com.yl.agent.action;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.type.TypeReference;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.Constant;
import com.yl.agent.bean.TradeOrderBean;
import com.yl.agent.entity.Authorization;
import com.yl.agent.utils.DateUtils;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.model.Order;
import com.yl.online.trade.hessian.OnlineInterfacePolicyHessianService;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import com.yl.online.trade.hessian.bean.InterfacePolicyBean;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;
import com.yl.online.trade.hessian.bean.PartnerRouterProfileBean;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceProviderQueryBean;
import com.yl.payinterface.core.hessian.InterfaceProviderHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;


/**
 * 交易系统控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月28日
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
	private List<InterfacePolicyProfileBean> profiles;
	private String interfaceType;
	private List<InterfacePolicyBean> interfacePolicyBeans;
	private List<PartnerRouterProfileBean> partnerProfiles;
	private PartnerRouterBean partnerRouterBean;
	private Page page;
	private Object tradeOrders;
	private String orderCode;
	private Object tradeOrder;
	private List<Payment> payments;
	private TradeOrderBean tradeOrderBean;
	private String sumInfo;
	private List<Order> order;

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
		for (int i = profiles.size() - 1; i >= 0; i--) {
			if (profiles.get(i) == null) {
				profiles.remove(i);
			}
		}
		interfacePolicyBean.setProfiles(profiles);
		onlineInterfacePolicyHessianService.createInterfacePolicy(interfacePolicyBean);
		return "routerAdd";
	}

	/**
	 * 根据接口类型查询路由模板
	 * 
	 * @return
	 */
	public String findRouterTemplates() {
		interfacePolicyBeans = onlineInterfacePolicyHessianService.queryInterfacePolicyByInterfaceType(interfaceType);
		return "findRouterTemplates";
	}

	/**
	 * 添加商户路由
	 * 
	 * @return
	 */
	public String partnerRouterAdd() {
		for (int i = partnerProfiles.size() - 1; i >= 0; i--) {
			if (partnerProfiles.get(i) == null) {
				partnerProfiles.remove(i);
			}
		}
		partnerRouterBean.setProfiles(partnerProfiles);
		onlinePartnerRouterHessianService.createPartnerRouter(partnerRouterBean);
		return "partnerRouterAdd";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String findAllTradeOrderAndFee() {
		if (tradeOrderBean.getSuccessPayTimeStart() != null) {
			tradeOrderBean.setSuccessPayTimeStart(DateUtils.getMinTime(tradeOrderBean.getSuccessPayTimeStart()));
		}
		if (tradeOrderBean.getSuccessPayTimeEnd() != null) {
			tradeOrderBean.setSuccessPayTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getSuccessPayTimeEnd()));
		}
		if (tradeOrderBean.getOrderTimeStart() != null) {
			tradeOrderBean.setOrderTimeStart(DateUtils.getMinTime(tradeOrderBean.getOrderTimeStart()));
		}
		if (tradeOrderBean.getOrderTimeEnd() != null) {
			tradeOrderBean.setOrderTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getOrderTimeEnd()));
		}
		Map<String, Object> params = ObjectToMap(tradeOrderBean);
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		params.put("agentNo", auth.getAgentNo());
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		page = (Page) onlineTradeQueryHessianService.findAllTradeOrderAndFee(page, params);
		if (null!=page) {
			tradeOrders = page.getObject();
		}
		return "findAllTradeOrderAndFee";
	}

	/**
	 * 查找一个订单
	 * 
	 * @return
	 */
	public String findOrderByCode() {
		tradeOrder = onlineTradeQueryHessianService.findOrderByCode(orderCode);
		payments = (List<Payment>) onlineTradeQueryHessianService.findPaymentByOrderCode(orderCode);
		return "findOrderByCode";
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
	 */
	public String payOrderSum() {
		if(tradeOrderBean != null){
			if (tradeOrderBean.getSuccessPayTimeStart() != null) {
				tradeOrderBean.setSuccessPayTimeStart(DateUtils.getMinTime(tradeOrderBean.getSuccessPayTimeStart()));
			}
			if (tradeOrderBean.getSuccessPayTimeEnd() != null) {
				tradeOrderBean.setSuccessPayTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getSuccessPayTimeEnd()));
			}
			if (tradeOrderBean.getOrderTimeStart() != null) {
				tradeOrderBean.setOrderTimeStart(DateUtils.getMinTime(tradeOrderBean.getOrderTimeStart()));
			}
			if (tradeOrderBean.getOrderTimeEnd() != null) {
				tradeOrderBean.setOrderTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getOrderTimeEnd()));
			}
		}
		Map<String, Object> pmap = ObjectToMap(tradeOrderBean);
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		pmap.put("agentNo", auth.getAgentNo());
		sumInfo = onlineTradeQueryHessianService.orderFeeSum(pmap);
		Map<String, Object> map = JsonUtils.toObject(sumInfo, HashMap.class);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String am = "0";
		String pf = "0";
		String al = "0";
		if (map != null) {
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
		Map<String,String> jMap = new HashMap<String,String>();
		jMap.put("am", am);
		jMap.put("pf", pf);
		jMap.put("al", al);
		sumInfo = JsonUtils.toJsonString(jMap);
		return "payOrderSum";
	}
	/**
	 * daochu
	 * @return
	 */
	public String payOrderExport() {
		if (tradeOrderBean.getSuccessPayTimeStart() != null) {
			tradeOrderBean.setSuccessPayTimeStart(DateUtils.getMinTime(tradeOrderBean.getSuccessPayTimeStart()));
		}
		if (tradeOrderBean.getSuccessPayTimeEnd() != null) {
			tradeOrderBean.setSuccessPayTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getSuccessPayTimeEnd()));
		}
		if (tradeOrderBean.getOrderTimeStart() != null) {
			tradeOrderBean.setOrderTimeStart(DateUtils.getMinTime(tradeOrderBean.getOrderTimeStart()));
		}
		if (tradeOrderBean.getOrderTimeEnd() != null) {
			tradeOrderBean.setOrderTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getOrderTimeEnd()));
		}
		Map<String, Object> pmap = ObjectToMap(tradeOrderBean);
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		pmap.put("agentNo", auth.getAgentNo());
		String info = onlineTradeQueryHessianService.orderInfoExport(pmap);
		order = JsonUtils.toObject(info, new TypeReference<List<Order>>() {});
		return "payOrderExport";
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

}
