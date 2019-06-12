package com.yl.agent.action;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.pay.common.util.StringUtil;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.agent.utils.CodeBuilder;
import com.yl.agent.utils.QRcodeUtil;
import com.yl.boss.api.bean.AgentDeviceOrderBean;
import com.yl.boss.api.bean.DeviceTypeBean;
import com.yl.boss.api.enums.PurchaseType;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.DeviceInterface;

/**
 * 设备订单控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月26日
 * @version V1.0.0
 */
public class DeviceOrderAction extends Struts2ActionSupport{
	
	private static final long serialVersionUID = 2602166925461282263L;
	private static final Logger logger = LoggerFactory.getLogger(DeviceOrderAction.class);
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(new InputStreamReader(DeviceOrderAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("RechargeAction load Properties error:", e);
		}
	}

	private AgentInterface agentInterface;
	private List<DeviceTypeBean> deviceType;
	private CustomerInterface customerInterface;
	private Page page;
	private AgentDeviceOrderBean agentDeviceOrderBean;
	private Object agentDeviceOrderInfo;
	private Object deviceInfo;
	private String msg;
	private String outOrderId;
	private DeviceInterface deviceInterface;
	private String customerPayNo;
	private double total;
	private String sign;
	private String url;
	private String redirectUrl;
	private String notifyUrl;


	/**
	 * 分享注册MD5处理
	 */
	public String calculation(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String partnerString = auth.getAgentNo() + "713120d0cea3c08a751f1d8fb38aa301";
		try {
			sign = DigestUtils.md5DigestAsHex(partnerString.getBytes("UTF-8"));
			url = prop.getProperty("quickOpen") + "agentNo=" + auth.getAgentNo() + "&checkNo=" + sign;
			msg = QRcodeUtil.getBase64(url);
		} catch (Exception e) {
			logger.error("MD5出异常了！！", e);
		}
		return SUCCESS;
	}

	/**
	 * 二次支付
	 * @return
	 */
	public String payAgain(){
		msg = agentInterface.getConfigKey();
		String key = customerInterface.getCustomerMD5Key(msg);
		agentDeviceOrderBean = agentInterface.toPayAgain(outOrderId);
		//agentDeviceOrderBean.setOutOrderId(CodeBuilder.build("PA", "yyyyMMdd"));//生产新订单号
		agentInterface.updateAgentDeviceOrder(agentDeviceOrderBean, "二次支付");
		outOrderId = agentDeviceOrderBean.getOutOrderId();
		total = agentDeviceOrderBean.getTotal();
		url = (String) prop.get("agnet.device.order.pay");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		customerPayNo = sdf.format(new Date());
		redirectUrl = prop.getProperty("redirectUrl");
		notifyUrl = prop.getProperty("notifyUrl");
		Map<String, String> resParams = new HashMap<String, String>();
		resParams.put("apiCode", "YL-PAY");
		resParams.put("inputCharset", "UTF-8");
		resParams.put("partner", msg);
		resParams.put("outOrderId",outOrderId);
		resParams.put("currency", "CNY");
		resParams.put("retryFalg", "TRUE");
		resParams.put("submitTime", customerPayNo);
		resParams.put("redirectUrl", redirectUrl);
		resParams.put("notifyUrl", notifyUrl);
		resParams.put("timeout", "1D");
		resParams.put("productName", "水牌采购");
		resParams.put("payType", "ALL");
		resParams.put("bankCardNo", "");
		resParams.put("accMode", "GATEWAY");
		resParams.put("wxNativeType", "PAGE");
		resParams.put("signType", "MD5");
		resParams.put("amount", String.valueOf(total));
		ArrayList<String> paramNames = new ArrayList<>(resParams.keySet());
		Collections.sort(paramNames);
		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!"sign".equals(paramName) && !"gatewayUrl".equals(paramName) && !"url".equals(paramName)
					&& resParams.get(paramName).toString() != "" && !"key".equals(paramName)) {
				signSource.append(paramName).append("=").append(resParams.get(paramName).toString());
				if (iterator.hasNext())
					signSource.append("&");
			}
		}
		String partnerString = signSource.toString();
		partnerString += key;
		try {
			if (StringUtils.notBlank("UTF-8")) {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes("UTF-8"));
			} else {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes());
			}
			sign = partnerString;
		} catch (Exception e) {
			logger.error("MD5出异常了！！", e);
		}
		return SUCCESS;
	}
	
	public String getQrCode(){
		msg=deviceInterface.getCustomerPayNo(customerPayNo);
		return SUCCESS;
	}
	
	public String addDeviceOrder(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		try {
			agentDeviceOrderBean.setOwnerId(auth.getAgentNo());
			agentDeviceOrderBean.setUser(auth.getUsername());
			agentDeviceOrderBean.setPurchaseChannel(PurchaseType.SERVICE_PROVIDER);//服务商
			agentDeviceOrderBean.setOutOrderId(outOrderId);// 支付订单号
			agentInterface.addAgentDeviceOrder(agentDeviceOrderBean, auth.getUsername());
			msg="true";
		} catch (Exception e) {
			msg="false";
			logger.info(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String pay(){
		try {
			String customerNo = agentInterface.getConfigKey();
			String key = customerInterface.getCustomerMD5Key(customerNo);
			Map<String, Object> resParams = new HashMap<String, Object>();
			if(key == null || customerNo == null){
				resParams.put("status", "FAILED");
				resParams.put("msg", "商户编号或密钥不存在！");
				write(JsonUtils.toJsonString(resParams));
			}
			String sign = sign(getHttpRequest().getParameterMap(), key);
			if(StringUtil.notNull(sign)){
				resParams.put("sign", sign);
				resParams.put("partner", customerNo);
				resParams.put("status", "SUCCESS");
				resParams.put("payUrl", prop.get("agnet.device.order.pay"));
				write(JsonUtils.toJsonString(resParams));
			}else{
				resParams.put("status", "FAILED");
				resParams.put("msg", "签名失败！");
				write(JsonUtils.toJsonString(resParams));
			}
		} catch (Exception e) {
			logger.error("获取签名失败！", e);
		}
		return SUCCESS;
	}

	public String sign(Map<String, String[]> params, String key){
		String sign = null;
		ArrayList<String> paramNames = new ArrayList<>(params.keySet());
		Collections.sort(paramNames);
		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!"sign".equals(paramName) && !"gatewayUrl".equals(paramName) && !"url".equals(paramName) && params.get(paramName)[0] != "" && !"key".equals(paramName)) {
				signSource.append(paramName).append("=").append(params.get(paramName)[0]);
				if (iterator.hasNext()) signSource.append("&");
			}
		}
		String partnerString = signSource.toString();
		partnerString += key;
		try {
			sign = DigestUtils.md5DigestAsHex(partnerString.getBytes());
		} catch (Exception e) {
			logger.error("MD5出异常了！！", e);
		}
		return sign;
	}
	
	
	public String getDeviceOrder() {
		if (page == null) {
			page = new Page<>();
		}
		Map<String, Object> params = retrieveParams(getHttpRequest().getParameterMap());
		params.put("pagingPage",getHttpRequest().getParameter("currentPage"));
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		params.put("agent_no", auth.getAgentNo());
		page=agentInterface.queryAgentDeviceOrderByAgentNo(params);
		agentDeviceOrderInfo=page.getObject();
		return SUCCESS;
	}
	
	public String getDevice() {
		if (page == null) {
			page = new Page<>();
		}
		Map<String, Object> params = retrieveParams(getHttpRequest().getParameterMap());
		params.put("pagingPage",getHttpRequest().getParameter("currentPage"));
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		params.put("agent_no", auth.getAgentNo());
		page=agentInterface.queryAgentDeviceByAgentNo(params);
		deviceInfo=page.getObject();
		return SUCCESS;
	}
	
	public String toAddDeviceOrder(){
		msg = agentInterface.getConfigKey();
		deviceType=agentInterface.getDeviceList();
		return SUCCESS;
	}
	/**
	 * 查看设备
	 */
	@SuppressWarnings("unused")
	private  String  toCheckCustomer(){
		//根据商编查询
		
		
		return SUCCESS;
	}
	
	
	/**
	 * 参数转换
	 */
	private Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap.put(key, Array.get(value, 0).toString().trim());
			}
		}
		return resultMap;
	}
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public Object getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(Object deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public AgentInterface getAgentInterface() {
		return agentInterface;
	}

	public void setAgentInterface(AgentInterface agentInterface) {
		this.agentInterface = agentInterface;
	}
	public List<DeviceTypeBean> getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(List<DeviceTypeBean> deviceType) {
		this.deviceType = deviceType;
	}

	public AgentDeviceOrderBean getAgentDeviceOrderBean() {
		return agentDeviceOrderBean;
	}

	public void setAgentDeviceOrderBean(AgentDeviceOrderBean agentDeviceOrderBean) {
		this.agentDeviceOrderBean = agentDeviceOrderBean;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getAgentDeviceOrderInfo() {
		return agentDeviceOrderInfo;
	}

	public void setAgentDeviceOrderInfo(Object agentDeviceOrderInfo) {
		this.agentDeviceOrderInfo = agentDeviceOrderInfo;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	public CustomerInterface getCustomerInterface() {
		return customerInterface;
	}

	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}
	public String getCustomerPayNo() {
		return customerPayNo;
	}

	public void setCustomerPayNo(String customerPayNo) {
		this.customerPayNo = customerPayNo;
	}

	public DeviceInterface getDeviceInterface() {
		return deviceInterface;
	}

	public void setDeviceInterface(DeviceInterface deviceInterface) {
		this.deviceInterface = deviceInterface;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}