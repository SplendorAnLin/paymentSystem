package com.yl.boss.action;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.bean.AuthBean;
import com.lefu.hessian.util.HessianSignUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.boss.Constant;
import com.yl.boss.bean.InterfaceInfo;
import com.yl.boss.bean.InterfaceInfoParam;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.InterfaceReconBillConfig;

/**
 * 支付接口控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class PayinterfaceAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 3964273096862299426L;

	private PayInterfaceHessianService payInterfaceHessianService;
	private String configs;
	private InterfaceInfo infoBean;
	private InterfaceInfoBean infoBeans;
	private Page page;
	private List<InterfaceInfoBean> interfaceInfoBeanList;
	private InterfaceInfoParam interfaceInfoParam;
	private String code;
	private String providerCode;
	private String interfaceProName;
	private List<Map<Object, List<String>>> supProvider = new ArrayList<Map<Object, List<String>>>();
	private InterfaceInfoBean interfaceInfoBean;
	private String cardTypeStr = "";
	private Map<Object, String[]> supProviders = new HashMap<Object, String[]>();
	private Properties tradeConfigs;
	private InterfaceReconBillConfig interfaceReconBillConfig;
	private String interfaceNameCode;
	private String msg;
	private String interfaceTypes;
	
	/**
	 * 通过接口名称查询接口编号
	 */
	public String checkInterfaceName(){
		try {
			infoBeans=payInterfaceHessianService.queryByName(java.net.URLDecoder.decode(getHttpRequest().getParameter("interfaceNameCode"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(infoBeans != null){
			msg=infoBeans.getCode();
		}
		return SUCCESS;
	}
	
	/**
	 * 保存支付接口
	 * 
	 * @return
	 */
	public String save() {
		try {
			AuthBean auth = new AuthBean();
			Authorization operator = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			auth.setOperator(operator.getUsername());
			auth.setInvokeSystem("BOSS");
			InterfaceInfoBean interfaceInfoBean = JsonUtils.toJsonToObject(infoBean, InterfaceInfoBean.class);
			Properties tradeConfigs = new Properties();
			// 交易配置排序
			String[] configParams = configs.split("\r\n");
			for (String configParam : configParams) {
				configParam = configParam.trim().replace("\r", "").replace("\n", "");
				String[] singleParam = configParam.split("=");
				if (singleParam.length >2||singleParam.length<1) {
					continue;
				}
				String param0 = singleParam[0] == null ? "" : singleParam[0].trim();
				String param1="";
				if (singleParam.length!=1) {
					param1 = singleParam[1] == null ? "" : singleParam[1].trim();
				}
				tradeConfigs.put(param0, param1);
			}
			interfaceInfoBean.setTradeConfigs(JsonUtils.toJsonString(tradeConfigs));
			tradeConfigSort(interfaceInfoBean);
			payInterfaceHessianService.interfaceInfoSave(auth, interfaceInfoBean);

		} catch (Exception e) {
			logger.error("", e);
		}
		return "save";
	}

	/**
	 * 查询接口信息分页
	 * 
	 * @return
	 */
	public String findAll() {
		try {
			HttpServletRequest request = this.getHttpRequest();
			page = new Page();
			int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
			if (currentPage > 1) {
				page.setCurrentPage(currentPage);
			}
			page.setCurrentResult((currentPage - 1) * page.getShowCount());

			page = payInterfaceHessianService.queryAll(page, ObjectToMap(interfaceInfoParam));
			interfaceInfoBeanList = JsonUtils.toObject((String) page.getObject(), new TypeReference<List<InterfaceInfoBean>>() {
			});
		} catch (Exception e) {
			logger.error("", e);
		}
		return "findAllInterfaceInfo";// "interfaceInfo/interfaceInfoQueryResult";
	}
	public String findAllInterface() {
		interfaceInfoBeanList=payInterfaceHessianService.queryInterfaceInfo();
//		List<Map<String,String>> allInterface=new ArrayList<Map<String,String>>();
//		for (InterfaceInfoBean interfaceInfo : interfaceInfoBeanList) {
//			System.out.println(interfaceInfo);
//		}
		return "interfaceInfoBeanList";
	}

	

	// 交易配置重新排序
	public void tradeConfigSort(InterfaceInfoBean interfaceInfoBean) {
		Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
		});
		List<String> paramNames = new ArrayList<String>();
		for (Object key : properties.keySet()) {
			paramNames.add(key.toString());
		}
		Collections.sort(paramNames, String.CASE_INSENSITIVE_ORDER);
		Properties properties2 = new Properties();
		for (String key : paramNames) {
			properties2.put(key, properties.get(key));
		}
		interfaceInfoBean.setTradeConfigs(JsonUtils.toJsonString(properties2));
	}

	/**
	 * 查看详细
	 * 
	 * @return
	 */
	public String toDetail() {
		try {
			String info = payInterfaceHessianService.queryByCode(code);
			interfaceInfoBean = JsonUtils.toObject(info, new TypeReference<InterfaceInfoBean>() {
			});
			tradeConfigs = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
			});
		} catch (Exception e) {
			logger.error("", e);
		}
		return "interfaceInfoDetail";// "interfaceInfo/interfaceInfoDetail";
	}

	// @RequestMapping("toUpdateInterfaceInfo")
	public String toUpdate() {
		try {
			String info = payInterfaceHessianService.queryByCode(code);
			interfaceInfoBean = JsonUtils.toObject(info, new TypeReference<InterfaceInfoBean>() {
			});
			tradeConfigs = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
			});
		} catch (Exception e) {
			logger.error("", e);
		}
		return "toUpdateInterfaceInfo";// "interfaceInfo/interfaceInfoModify";
	}

	// @RequestMapping(value = "updateInterfaceInfo")
	public String update() {
		try {
			AuthBean auth = new AuthBean();
			Authorization operator = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			auth.setOperator(operator.getUsername());
			auth.setInvokeSystem("BOSS");
			// bean复制
			InterfaceInfoBean interfaceInfoBean = JsonUtils.toJsonToObject(infoBean, InterfaceInfoBean.class);
			Properties tradeConfigs = new Properties();
			// 交易配置排序
			String[] configParams = configs.split("\r\n");
			for (String configParam : configParams) {
				configParam = configParam.trim().replace("\r", "").replace("\n", "");
				String[] singleParam = configParam.split("=");
				if (singleParam.length >2||singleParam.length<1) {
					continue;
				}
				String param0 = singleParam[0] == null ? "" : singleParam[0].trim();
				String param1="";
				if (singleParam.length!=1) {
					param1 = singleParam[1] == null ? "" : singleParam[1].trim();
				}
				tradeConfigs.put(param0, param1);
			}
			interfaceInfoBean.setTradeConfigs(JsonUtils.toJsonString(tradeConfigs));
			tradeConfigSort(interfaceInfoBean);
			String sign = HessianSignUtils.md5DigestAsHex(StringUtils.concatToSB(auth.toString(), interfaceInfoBean.toString(), "payinterface").toString().getBytes());
			auth.setSign(sign);
			payInterfaceHessianService.interfaceInfoModify(auth, interfaceInfoBean);

		} catch (Exception e) {
			logger.error("", e);
		}
		return "updateInterfaceInfo";// "redirect:findAllInterfaceInfo.htm";
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
	
	/**
	 * 根据接口类型查询支付接口信息
	 * @return
	 */
	public String interfaceInfoCodeQueryBy(){
		try {
			if(interfaceTypes != null && !interfaceTypes.equals("")){
				List<String> interfaceTypesList = new ArrayList<String>();
				interfaceTypesList.add(interfaceTypes);
				msg = JsonUtils.toJsonString(payInterfaceHessianService.interfaceInfoCodeQueryBy(interfaceTypesList));
			}
		} catch (Exception e) {
			msg = null;
		}
		return SUCCESS;
	}

	public void setPayInterfaceHessianService(PayInterfaceHessianService payInterfaceHessianService) {
		this.payInterfaceHessianService = payInterfaceHessianService;
	}

	public String getConfigs() {
		return configs;
	}

	public void setConfigs(String configs) {
		this.configs = configs;
	}

	public InterfaceInfo getInfoBean() {
		return infoBean;
	}

	public void setInfoBean(InterfaceInfo infoBean) {
		this.infoBean = infoBean;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<InterfaceInfoBean> getInterfaceInfoBeanList() {
		return interfaceInfoBeanList;
	}

	public void setInterfaceInfoBeanList(List<InterfaceInfoBean> interfaceInfoBeanList) {
		this.interfaceInfoBeanList = interfaceInfoBeanList;
	}

	public InterfaceInfoParam getInterfaceInfoParam() {
		return interfaceInfoParam;
	}

	public void setInterfaceInfoParam(InterfaceInfoParam interfaceInfoParam) {
		this.interfaceInfoParam = interfaceInfoParam;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getInterfaceProName() {
		return interfaceProName;
	}

	public void setInterfaceProName(String interfaceProName) {
		this.interfaceProName = interfaceProName;
	}

	public List<Map<Object, List<String>>> getSupProvider() {
		return supProvider;
	}

	public void setSupProvider(List<Map<Object, List<String>>> supProvider) {
		this.supProvider = supProvider;
	}

	public InterfaceInfoBean getInterfaceInfoBean() {
		return interfaceInfoBean;
	}

	public void setInterfaceInfoBean(InterfaceInfoBean interfaceInfoBean) {
		this.interfaceInfoBean = interfaceInfoBean;
	}

	public String getCardTypeStr() {
		return cardTypeStr;
	}

	public void setCardTypeStr(String cardTypeStr) {
		this.cardTypeStr = cardTypeStr;
	}

	public Map<Object, String[]> getSupProviders() {
		return supProviders;
	}

	public void setSupProviders(Map<Object, String[]> supProviders) {
		this.supProviders = supProviders;
	}

	public PayInterfaceHessianService getPayInterfaceHessianService() {
		return payInterfaceHessianService;
	}

	public Properties getTradeConfigs() {
		return tradeConfigs;
	}

	public void setTradeConfigs(Properties tradeConfigs) {
		this.tradeConfigs = tradeConfigs;
	}
	public String getInterfaceNameCode() {
		return interfaceNameCode;
	}

	public void setInterfaceNameCode(String interfaceNameCode) {
		this.interfaceNameCode = interfaceNameCode;
	}

	public InterfaceInfoBean getInfoBeans() {
		return infoBeans;
	}

	public void setInfoBeans(InterfaceInfoBean infoBeans) {
		this.infoBeans = infoBeans;
	}

	public InterfaceReconBillConfig getInterfaceReconBillConfig() {
		return interfaceReconBillConfig;
	}

	public void setInterfaceReconBillConfig(InterfaceReconBillConfig interfaceReconBillConfig) {
		this.interfaceReconBillConfig = interfaceReconBillConfig;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getInterfaceTypes() {
		return interfaceTypes;
	}

	public void setInterfaceTypes(String interfaceTypes) {
		this.interfaceTypes = interfaceTypes;
	}
}
