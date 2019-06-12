package com.yl.boss.action;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.bean.AuthBean;
import com.lefu.hessian.util.HessianSignUtils;
import com.yl.payinterface.core.bean.InterfaceProviderBean;
import com.yl.payinterface.core.bean.InterfaceProviderHistory;
import com.yl.payinterface.core.bean.InterfaceProviderQueryBean;
import com.yl.payinterface.core.hessian.InterfaceProviderHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;

/**
 * 接口提供方控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class InterfaceProviderAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 4470548551349118194L;
	private PayInterfaceHessianService payInterfaceHessianService;
	private InterfaceProviderHessianService interfaceProviderHessianService;
	private List<InterfaceProviderHistory> interfaceProviderHistoryList;
	private InterfaceProviderQueryBean interfaceProvider;
	private List<InterfaceProviderQueryBean> interfaceProviderBeanList;
	private InterfaceProviderQueryBean interfaceProviderBean;
	private String createTimeStart;
	private String createTimeEnd;
	private String fullName;
	private String shortName;
	private Page page;
	private String msg = "";
	private String code;

	public String save() {
		try {
			Authorization operator = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			AuthBean auth = new AuthBean();
			InterfaceProviderBean interfaceProviderBean = JsonUtils.toJsonToObject(interfaceProvider, InterfaceProviderBean.class);
			String sign = HessianSignUtils.md5DigestAsHex(StringUtils.concatToSB(auth.toString(), interfaceProviderBean.toString(), "payinterface").toString().getBytes());
			auth.setSign(sign);
			auth.setOperator(operator.getUsername());
			auth.setInvokeSystem("BOSS");
			payInterfaceHessianService.interfaceProviderSave(auth, interfaceProviderBean);

		} catch (Exception e) {
		}
		return "save";// "interfaceProvider/interfaceProviderQuery";
	}

	public String update() {
		try {
			AuthBean auth = new AuthBean();
			InterfaceProviderBean interfaceProviderBean = JsonUtils.toJsonToObject(interfaceProvider, InterfaceProviderBean.class);
			String sign = HessianSignUtils.md5DigestAsHex(StringUtils.concatToSB(auth.toString(), interfaceProviderBean.toString(), "payinterface").toString().getBytes());
			auth.setSign(sign);
			payInterfaceHessianService.interfaceProviderModify(auth, interfaceProviderBean);
		} catch (Exception e) {
		}
		return "update";// "redirect:query.htm";
	}

	public String history(){
		try {
			HttpServletRequest request = this.getHttpRequest();
			if(page == null){
				page = new Page();
			}
			int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
			if (currentPage > 1) {
				page.setCurrentPage(currentPage);
			}
			page.setCurrentResult((currentPage - 1) * page.getShowCount());
			Map<String, Object> params = new HashMap<>();
			params.put("interfaceProviderCode", code);
			
			page = interfaceProviderHessianService.queryHistoryPageAll(page,params);
			
			interfaceProviderHistoryList = (List<InterfaceProviderHistory>) page.getObject();
		} catch (Exception e) {
			logger.error("", e);
		}
		return "history";
	}
	
	@SuppressWarnings("unchecked")
	public String findAll() {
		try {
			HttpServletRequest request = this.getHttpRequest();
			page = new Page();
			int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
			if (currentPage > 1) {
				page.setCurrentPage(currentPage);
			}
			page.setCurrentResult((currentPage - 1) * page.getShowCount());
			Map<String, Object> maps = new HashMap<>();
			maps.put("fullName", fullName);
			maps.put("shortName", shortName);
			maps.put("code", code);
			maps.put("createTimeStart", createTimeStart);
			maps.put("createTimeEnd",createTimeEnd);
			page = interfaceProviderHessianService.queryAll(page,maps);
			interfaceProviderBeanList = (List<InterfaceProviderQueryBean>) page.getObject();
		} catch (Exception e) {
			logger.error("", e);
		}
		return "query";// "interfaceProvider/interfaceProviderQueryResult";
	}

	public String toUpdate() {
		try {
			interfaceProviderBean = interfaceProviderHessianService.queryByCode(code);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "toUpdate";// "interfaceProvider/interfaceProviderModify";
	}

	public String findProviderByCode() {
		interfaceProvider = interfaceProviderHessianService.queryByCode(code);
		if (interfaceProvider != null) {
			msg = "SUCCESS";
		}
		return "findProviderByCode";
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
		}
		return map;
	}
	
	public String findAllProvider(){
		interfaceProviderBeanList = interfaceProviderHessianService.findAllProvider();
		Map<String,Object> m = new HashMap<>();
		for (InterfaceProviderQueryBean interfacePolicyBean : interfaceProviderBeanList) {
			m.put(interfacePolicyBean.getCode(), interfacePolicyBean.getFullName());
		}
		msg = JsonUtils.toJsonString(m);
		return SUCCESS;
	}
	
	public PayInterfaceHessianService getPayInterfaceHessianService() {
		return payInterfaceHessianService;
	}

	public void setPayInterfaceHessianService(PayInterfaceHessianService payInterfaceHessianService) {
		this.payInterfaceHessianService = payInterfaceHessianService;
	}

	public InterfaceProviderHessianService getInterfaceProviderHessianService() {
		return interfaceProviderHessianService;
	}

	public void setInterfaceProviderHessianService(InterfaceProviderHessianService interfaceProviderHessianService) {
		this.interfaceProviderHessianService = interfaceProviderHessianService;
	}

	public InterfaceProviderQueryBean getInterfaceProvider() {
		return interfaceProvider;
	}

	public void setInterfaceProvider(InterfaceProviderQueryBean interfaceProvider) {
		this.interfaceProvider = interfaceProvider;
	}

	public List<InterfaceProviderQueryBean> getInterfaceProviderBeanList() {
		return interfaceProviderBeanList;
	}

	public void setInterfaceProviderBeanList(List<InterfaceProviderQueryBean> interfaceProviderBeanList) {
		this.interfaceProviderBeanList = interfaceProviderBeanList;
	}

	public InterfaceProviderQueryBean getInterfaceProviderBean() {
		return interfaceProviderBean;
	}

	public void setInterfaceProviderBean(InterfaceProviderQueryBean interfaceProviderBean) {
		this.interfaceProviderBean = interfaceProviderBean;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List<InterfaceProviderHistory> getInterfaceProviderHistoryList() {
		return interfaceProviderHistoryList;
	}

	public void setInterfaceProviderHistoryList(List<InterfaceProviderHistory> interfaceProviderHistoryList) {
		this.interfaceProviderHistoryList = interfaceProviderHistoryList;
	}
}
