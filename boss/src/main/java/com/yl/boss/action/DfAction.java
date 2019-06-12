package com.yl.boss.action;

import com.lefu.commons.cache.bean.DictionaryType;
import com.lefu.commons.cache.tags.DictionaryUtils;
import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.pay.common.util.DigestUtil;
import com.pay.common.util.Md5Util;
import com.yl.boss.Constant;
import com.yl.boss.entity.Agent;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Customer;
import com.yl.boss.service.AgentService;
import com.yl.boss.service.CustomerService;
import com.yl.boss.utils.GengeratePassword;
import com.yl.boss.utils.RSAUtils;
import com.yl.dpay.hessian.beans.*;
import com.yl.dpay.hessian.service.DpayConfigFacade;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.sms.SmsUtils;
import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonNode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 代付控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class DfAction extends Struts2ActionSupport {
	private static final long serialVersionUID = 1L;
	private QueryFacade queryFacade;
	private ServiceConfigFacade serviceConfigFacade;
	private ServiceConfigBean serviceConfigBean;
	private DpayConfigFacade dpayConfigFacade;
	private DpayConfigBean dpayConfigBean;
	private RouteConfigBean routeConfigBean;
	private CustomerService customerService;
	private AgentService agentService;
	private DpayFacade dpayFacade;
	private String auditPassword;
	private String msg;
	private String phone;
	private String userName;
	private static Properties prop = new Properties();
	private ServiceConfigHistoryBean serviceConfigHistoryBean;
	private String interfaceType;
	private List<RouteConfigBean> routeConfigBeanList;
	private RouteInfoBean routeInfoBean;
	private Map<String,Object> supportPayList;

	static {
		try {
			prop.load(DfAction.class.getClassLoader().getResourceAsStream("system.properties"));
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	@Override
	public HttpServletRequest getHttpRequest() {
		return super.getHttpRequest();
	}

	@Override
	public void clearErrors() {
		super.clearErrors();
	}

	// 检查是否开通短信验证
	public void isUsePhoneCheck(){
		try{
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			if("TRUE".equals(auth.getUsePhoneCheck())){
				write("1");
			}else{
				write("0");
			}
		}catch(Exception e){
			write("0");
		}
	}
	/**
	 * 单独开通代付信息
	 */
	public void serviceConfigAdd(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String dpayPass="";
		try {
			Map<String, Object> keyMap = RSAUtils.genKeyPair();
			serviceConfigBean.setPrivateKey(RSAUtils.getPrivateKey(keyMap));
			serviceConfigBean.setPublicKey(RSAUtils.getPublicKey(keyMap));
			dpayPass = GengeratePassword.getExchangeCode().toString();
			serviceConfigBean.setComplexPassword(Md5Util.hmacSign(dpayPass,"DPAY"));
			serviceConfigFacade.openDF(serviceConfigBean,auth.getRealname());
		} catch (Exception e) {
			logger.info("帐号:"+serviceConfigBean.getOwnerId()+"创建代付信息失败，异常信息{}", e);
			write("false");
		}
		try {
			SmsUtils.sendCustom(String.format(Constant.SMS_DF_OPEN, userName,dpayPass),serviceConfigBean.getPhone());
			write("true");
		} catch (IOException e) {
			logger.error("", e);
			write("false");
		}
	}
	// 校验手机验证码
	public void checkVerifyCodeEqual(){
		try{
			String verifyCode =getHttpRequest().getParameter("verifyCode");
			String sessionCode =(String)getSessionMap().get("verifyCode");
			if(sessionCode!=null&&verifyCode!=null&&sessionCode.equals(verifyCode)){
				// 通知前提输入信息
				write("1");
			}else{
				write("0");
			}
		}catch(Exception e){
			write("0");
		}
	}
	
	// 校验审核密码
	public void verifyPassword(){
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		if(StringUtils.isBlank(auditPassword)){
			write("false");
		}
		String npwd =DigestUtil.md5(auditPassword);
		if(StringUtils.notBlank(auth.getAuditPassword())){
			if(auth.getAuditPassword().equals(npwd)){
				write("true");
			}
		}
		write("false");
	}
	
	// 校验手机号验证码和审核密码
	public void applyVerifyWithVerifyCode(){
		String  verifyCode =getHttpRequest().getParameter("verifyCode");
		String sessionCode =(String)getSessionMap().get("verifyCode");
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		if(sessionCode!=null&&verifyCode!=null&&sessionCode.equals(verifyCode)){
			ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
			if(serviceConfigBean==null){
				write("false");
			}
			if(serviceConfigBean.getComplexPassword().equals(Md5Util.hmacSign(auditPassword, "DPAY"))){
				write("true");
			}
			write("false");

		}else{
			write("false");
		}
	}
	/**
	 * 代付历史查询
	 */
	public String historyById(){
		try {
			String queryId="historyQuery";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = queryFacade.history(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户代付配置查询
	 * @return
	 */
	public String serviceConfigQuery(){
		try {
			String queryId = "dpayConfigQuery";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据手机号，查询代付信息
	 * @return
	 */
	public String phoneVerifymsg(){
		try {
			if (serviceConfigFacade.findByPhone(phone)==null) {
				msg="true";
			}else{
				msg="false";
			}
		} catch (Exception e) {
			msg="false";
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 检查代付配置
	 * @return
	 */
	public String checkDfConfig(){
		String owner_id=getHttpRequest().getParameter("owner_id");
		serviceConfigBean=serviceConfigFacade.findServiceConfigById(owner_id);
		if (serviceConfigBean!=null) {
			write("false");
		}else {
			write("true");
		}
		return SUCCESS;
	}
	/**
	 * 重置代付密码
	 */
	public void resetDfPwd() {
		String owner_id=getHttpRequest().getParameter("id");
		serviceConfigFacade.dfComplexPWDReset(owner_id);
	}
	/**
	 * 代付服务信息ID查询
	 * @return
	 */
	public String serviceConfigFindId(){
		String owner_id=getHttpRequest().getParameter("owner_id");
		serviceConfigBean=serviceConfigFacade.findServiceConfigById(owner_id);
		return SUCCESS;
	}
	/**
	 * 根据id查询用户信息用于自动填写代付配置
	 * @return
	 */
	public String getAccInfo(){
		String owner_id=getHttpRequest().getParameter("owner_id");
		Customer customer=customerService.findByCustNo(owner_id);
		Agent agent=agentService.findByNo(owner_id);
		if (customer!=null) {
			write(JsonUtils.toJsonString(customer));
		}else if (agent!=null) {
			write(JsonUtils.toJsonString(agent));
		}else {
			write("false");
		}
		return SUCCESS;
	}
	/**
	 * 修改用户代付配置
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String serviceConfigUpdate(){
		try {
			serviceConfigFacade.update(serviceConfigBean);
			serviceConfigBean = serviceConfigFacade.findServiceConfigById(serviceConfigBean.getOwnerId());
			if(serviceConfigBean!=null){
				Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
				serviceConfigHistoryBean = new ServiceConfigHistoryBean(serviceConfigBean,auth.getRealname());
				if(serviceConfigHistoryBean!=null){
					serviceConfigFacade.insert(serviceConfigHistoryBean);
				}
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	// 发送验证码
	@SuppressWarnings("unchecked")
	public void sendVerifyCode(){
		String type=getHttpRequest().getParameter("type");
		Random random =new Random();
		// 生成验证码6位随机数字
		String verifyCode=""+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10);
		// 放到session中
		getSessionMap().put("verifyCode", verifyCode);
		// 发送手机验证码
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		
		try{
			String smsStr="";
			if("0".equals(type)){
				smsStr=String.format(Constant.SMS_AUDIT_TYPE0, verifyCode);
			}else if("1".equals(type)){
				smsStr=String.format(Constant.SMS_OPEN_TYPE1, verifyCode);
			}else if("2".equals(type)){
				smsStr=String.format(Constant.SMS_CLOSE_TYPE2, verifyCode);
			}
			SmsUtils.sendCustom(smsStr, auth.getUsername());
			// 通知前提输入信息
			write("true");
		}catch(Exception e){
			write("false");
		}

	}

	/**
	 * 付款审核
	 */
	public void remitAudit(){
		String msg = "";
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String code = getHttpRequest().getParameter("code");
			String flag = getHttpRequest().getParameter("flag");
			String[] codes = code.split(",");
			int failNum = 0;
			String failFlows = "";
			int successNum = 0;
			String successFlows = "";
			msg = "";
			flag = flag.equals("TRUE")?"PASS":"REMIT_REFUSE";
			for (String co : codes) {
				try {
					String lock = CacheUtils.get(Constant.LockPrefix0 + co, String.class);
					if (StringUtils.isBlank(lock)) {
						logger.info("运营账号：" + auth.getUsername() + "，订单号：" + co + " 加锁 ");
						CacheUtils.setEx(Constant.LockPrefix0 + co, "lock", 120);
						try {
							logger.info("代付 付款审核流水号 code:" + co + "flag:" + flag);
							dpayFacade.remitAudit(co, flag, auth.getUsername());
							successNum++;
							successFlows += ",\"" + co + "\"";
						} catch (Exception e) {
							failNum++;
							failFlows += ",\"" + co + "\"";
							logger.error(e.getMessage());
						}
					} else {
						failNum++;
						failFlows += ",\"" + co + "_重复提交\"";
					}
				}catch (Exception e) {
					logger.error("系统异常", e);
				}finally {
					CacheUtils.del(Constant.LockPrefix0 + co);
					logger.info("运营账号：" +  auth.getUsername() + "，订单号：" + co + " 解锁 ");
				}
			}
			if (failNum != 0) {
				msg = "{\"result\":0,\"success\":" + successNum + ",\"fail\":" + failNum;
			} else {
				msg = "{\"result\":1,\"success\":" + successNum + ",\"fail\":" + 0;
			}
			if (failFlows.length() > 0) {
				failFlows = failFlows.substring(1);
				msg += ",\"failFlows\":[" + failFlows + "]";
			} else {
				msg += ",\"failFlows\":[]";
			}
			if (successFlows.length() > 0) {
				successFlows = successFlows.substring(1);
				msg += ",\"successFlows\":[" + successFlows + "]";
			} else {
				msg += ",\"successFlows\":[]";
			}
			msg += "}";

		} catch (Exception e) {
			logger.error("系统异常:", e);
		}

		this.write(msg);
	}
	
	/**
	 * 代付统计
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getDPayCount(){
		try {
			String queryId = "dpayRequestCount";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			List valueList = (List) returnMap.get(QueryFacade.VALUELIST);
			Map<String, Object> map = (Map<String, Object>) valueList.get(0);
			if(map != null && map.get("flow_no") !=null && map.get("amount") != null){
				getHttpResponse().getWriter().write(map.get("flow_no") + "," + map.get("amount") + "," + map.get("fee"));
			}else{
				getHttpResponse().getWriter().write("0,0,0");
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
	}

	/**
	 * 代付查询                                                                                       6564
	 */
	@SuppressWarnings("rawtypes")
	public String dfQuery() {
		try {
			String queryId = "dpayRequest";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 代付查询
	 */
	@SuppressWarnings("rawtypes")
	public String dfAuditQuery() {
		try {
			String queryId = "dpayAuditRequest";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			String status = (String) params.get("status");
			Map<String, Object> statusMap = convertDfStatus(status);
			params.putAll(statusMap);

			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 代付导出
	 */
	@SuppressWarnings("rawtypes")
	public String dfExport() {

		try {
			String queryId = "dpayRequestExport";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			String status = (String) params.get("status");
			Map<String, Object> statusMap = convertDfStatus(status);
			params.putAll(statusMap);
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 跳转代付出款配置
	 */
	public String toDpayConfig(){
		dpayConfigBean = dpayConfigFacade.findDfConfig();
		if(dpayConfigBean == null){
			dpayConfigBean = new DpayConfigBean();
		}
		return SUCCESS;
	}
	
	/**
	 * 代付出款配置
	 */
	public void dpayConfig(){
		String msg = "FAILED";
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		dpayConfigBean.setOper(auth.getRealname());
		try {
			dpayConfigFacade.config(dpayConfigBean);
			msg = "SUCCESS";
		} catch (Exception e) {
			logger.error("", e);
		}
		write(msg);
	}
	
	/**
	 * 代付出款配置历史
	 */
	@SuppressWarnings("rawtypes")
	public String dpayConfigHistory(){
		try {
			String queryId = "dpayConfigHistory";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 跳转代付路由配置
	 */
	public String toRouteConfig(){
		routeConfigBean = dpayConfigFacade.findRouteConfig();
		if(routeConfigBean == null){
			routeConfigBean = new RouteConfigBean();
		}
		return SUCCESS;
	}
	
	/**
	 * 代付路由配置
	 */
	public void routeConfig(){
		String msg = "FAILED";
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		try {
			dpayConfigFacade.updateRoute(routeConfigBean, auth.getRealname());
			msg = "SUCCESS";
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		write(msg);
	}
	
	/**
	 * 代付路由配置历史
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String routeConfigHistory(){
		try {
			String queryId = "routeConfigHistory";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
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

	/**
	 * 显示状态-数据库状态的转换 AUDIT_WAIT 待审核 AUDIT_REFUSE 审核拒绝 HANDLING 处理中 SUCCESS 付款成功
	 * FAILED 付款失败 REFUND_WAIT 待退款 REFUNDED 已退款（通过与拒绝）
	 * 
	 * @param status
	 *            显示状态
	 * @return
	 */
	private Map<String, Object> convertDfStatus(String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("AUDIT_WAIT".equals(status)) {
			map.put("df.auditStatus", "WAIT");
		} else if ("AUDIT_REFUSE".equals(status)) {
			map.put("df.auditStatus", "REFUSE");
		} else if ("HANDLING".equals(status)) {
			map.put("df.handling", "HANDLING");
		} else if ("SUCCEED".equals(status)) {
			map.put("dfRecord.handleStatus", "SUCCEED");
		} else if ("FAILED".equals(status)) {
			map.put("dfRecord.handleStatus", "FAILED");
		} else if ("REFUND_WAIT".equals(status)) {
			map.put("tk.auditStatus", "WAIT");
		} else if ("REFUNDED".equals(status)) {
			map.put("tk.refunded", "REFUNDED");
		}
		return map;
	}


	public boolean checkCard(String cardNo) {
		if (!NumberUtils.isNumber(cardNo) || cardNo.length() < 14)
			return false;

		String[] nums = cardNo.split("");
		int sum = 0;
		int index = 1;
		for (int i = 0; i < nums.length; i++) {
			if ((i + 1) % 2 == 0) {
				if ("".equals(nums[nums.length - index])) {
					continue;
				}
				int tmp = Integer.parseInt(nums[nums.length - index]) * 2;
				if (tmp >= 10) {
					String[] t = String.valueOf(tmp).split("");
					tmp = Integer.parseInt(t[1]) + Integer.parseInt(t[2]);
				}
				sum += tmp;
			} else {
				if ("".equals(nums[nums.length - index]))
					continue;
				sum += Integer.parseInt(nums[nums.length - index]);
			}
			index++;
		}
		if (sum % 10 != 0) {
			return false;
		}
		return true;
	}
	
	public List<Map<String, String>> getCnaps(String bankNames) throws Exception {
		if (bankNames == null || "".equals(bankNames)) {
			return new ArrayList<Map<String, String>>();
		}
		String words = "";
		if (bankNames.indexOf(",") > -1) {
			String[] bankName = bankNames.split(",");
			for (int i = 0; i < bankName.length; i++) {
				if (i == 0) {
					words += "words=" + URLEncoder.encode(bankName[i], "UTF-8");
					continue;
				}
				words += "&words=" + URLEncoder.encode(bankName[i], "UTF-8");
			}
		} else {
			words = "words=" + URLEncoder.encode(bankNames, "UTF-8");
			words += "&1=1";
		}
		List<Map<String, String>> list = null;
		try {
			String url = (String) prop.get("cachecenter.service.url")
					+ "/cnaps/batchSearch.htm?fields=clearingBankCode&fields=code&fields=providerCode&fields=clearingBankLevel&fields=name";
			String resData = HttpClientUtils.send(Method.POST, url, words, true, Charset.forName("UTF-8").name(), 40000);
			JsonNode cnapsNodes = JsonUtils.getInstance().readTree(resData);
			list = new ArrayList<>();
			for (JsonNode cnapsNode : cnapsNodes) {
				if (cnapsNode.size() == 0) {
					Map<String, String> map = new HashMap<String, String>();
					list.add(map);
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("code", cnapsNode.get("code").toString());
				map.put("clearingBankCode", cnapsNode.get("clearingBankCode").toString());
				map.put("providerCode", cnapsNode.get("providerCode").toString());
				map.put("clearingBankLevel", cnapsNode.get("clearingBankLevel").toString());
				map.put("name", cnapsNode.get("name").toString());
				if ("null".equals(map.get("code"))) {
					map.put("code", "");
				}
				list.add(map);
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return list;
	}

	public List<Map<String, Object>> getCardInfo(String panNos) throws Exception {
		if (panNos == null || "".equals(panNos)) {
			return new ArrayList<Map<String, Object>>();
		}
		String cardsNo = "";
		if (panNos.indexOf(",") > -1) {
			String[] panNoArr = panNos.split(",");
			for (int i = 0; i < panNoArr.length; i++) {
				if (i == 0) {
					cardsNo = "cardNos=" + panNoArr[i];
					continue;
				}
				cardsNo += "&" + "cardNos=" + panNoArr[i];
			}
		} else {
			cardsNo = "cardNos=" + panNos;
		}
		List<Map<String, Object>> list = null;
		try {
			String url = (String) prop.get("cachecenter.service.url") + "/iin/batchRecognition.htm?" + cardsNo;
			String resData = HttpClientUtils.send(Method.POST, url, "", false, "");
			JsonNode iinNode = JsonUtils.getInstance().readTree(resData);

			list = new ArrayList<>();
			for (JsonNode iins : iinNode) {
				if (iins.size() == 0) {
					Map<String, Object> map = new HashMap<>();
					map.put("providerCode", "");
					list.add(map);
					continue;
				}
				Map<String, Object> map = new HashMap<>();
				map.put("providerCode", iins.get("providerCode"));
				list.add(map);
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return list;
	}

	public List<Map<String, String>> getSabkByProCode(List<Map<String, Object>> proCodes) throws Exception {
		if (proCodes == null || proCodes.size() == 0) {
			return new ArrayList<Map<String, String>>();
		}
		String providerCode = "";
		for (int i = 0; i < proCodes.size(); i++) {
			if (proCodes.get(i) == null || "".equals(proCodes.get(i))) {
				continue;
			}
			providerCode = "&providerCode=" + proCodes.get(i).get("providerCode") + "&clearBankLevel=1";
		}
		List<Map<String, String>> list = null;
		try {
			String url = (String) prop.get("cachecenter.service.url") + "/cnaps/batchSearch.htm?limit=1&fields=providerCode&fields=clearingBankCode" + providerCode;
			String resData = HttpClientUtils.send(Method.POST, url, "", false, "");
			JsonNode sabkCodes = JsonUtils.getInstance().readTree(resData);
			list = new ArrayList<>();
			for (JsonNode sabkCode : sabkCodes) {
				if (sabkCode.size() == 0) {
					Map<String, String> map = new HashMap<String, String>();
					list.add(map);
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("clearingBankCode", sabkCode.get("clearingBankCode").toString());
				list.add(map);
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return list;
	}

	public Map<String, String> getSabkCode(String proCode, String clearBankLevel) {
		String res = "";
		try {
			res = "word=&";
			res = res + "providerCode=" + proCode + "&";
			res = res + "clearBankLevel=" + clearBankLevel;
			String url = (String) prop.get("cachecenter.service.url")
					+ "/cnaps/suggestSearch.htm?fields=providerCode&fields=name&fields=clearingBankCode&fields=providerCode";
			String resData = HttpClientUtils.send(Method.POST, url, res, true, Charset.forName("UTF-8").name(), 6000);
			JsonNode sabkCodes = JsonUtils.getInstance().readTree(resData);

			Map<String, String> map = null;

			for (JsonNode sabkCode : sabkCodes) {
				if (sabkCode.size() != 0) {
					map = new HashMap<String, String>();
					map.put("clearingBankCode", sabkCode.get("clearingBankCode").toString());
				}
			}
			return map;
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
		return null;
	}

	public void toCachecenterCnaps() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String res = "";
		try {
			res = res + "word=" + request.getParameterMap().get("word")[0] + "&";
			res = res + "providerCode=" + request.getParameterMap().get("providerCode")[0] + "&";
			res = res + "clearBankLevel=" + request.getParameterMap().get("clearBankLevel")[0];
			String url = (String) prop.get("cachecenter.service.url")
					+ "/cnaps/suggestSearch.htm?fields=providerCode&fields=name&fields=clearingBankCode&fields=providerCode";
			String resData = HttpClientUtils.send(Method.POST, url, res, true, Charset.forName("UTF-8").name(), 6000);
			write(resData);
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	public void toCachecenterIin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String res = "";
		try {
			res = res + "cardNo=" + request.getParameterMap().get("cardNo")[0];
			String url = (String) prop.get("cachecenter.service.url") + "/iin/recognition.htm?" + res;
			String resData = HttpClientUtils.send(Method.POST, url, "", true, Charset.forName("UTF-8").name(), 6000);
			write(resData);
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}
	
	/**
	 * 代付路由查询
	 * @return
	 */
	public String dfRouteQuery(){
		try {
			String queryId = "routeConfig";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 代付路由新增
	 * @return
	 */
	public String dfRouteAdd(){
		try {
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			dpayConfigFacade.createRoute(routeConfigBean, auth.getRealname());
			getHttpRequest().setAttribute("success", "true");
		} catch (Exception e) {
			getHttpRequest().setAttribute("success", "false");
		}
		return SUCCESS;
	}
	
	/**
	 * 根据编号查询单条路由信息
	 * @return
	 */
	public String dfRouteQueryByCode(){
		
		routeConfigBean = dpayConfigFacade.findByCode(routeConfigBean.getCode());
		try {
			queryDictionaryRangedList();
		} catch (Exception e) {
			
		}
		if(getHttpRequest().getParameter("operate").equals("update")){
			return "update";
		}else{
			return "detail";
		}
	}
	
	/**
	 * 根据编号查询单条代付路由信息是否存在
	 * @return
	 */
	public String dfRouteQueryByCodeBool(){
		try {
			routeConfigBean = dpayConfigFacade.findByCode(routeConfigBean.getCode());
			if(routeConfigBean != null){
				msg = "true";
			}else {
				msg = "false";
			}
		} catch (Exception e) {
			msg = "false";
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有支持支付类型
	 * @return
	 */
	public String dfRouteQueryDictionary(){
		queryDictionaryRangedList();
		return SUCCESS;
	}
	
	/**
	 * 查询所有支持支付类型
	 * @return
	 */
	public void queryDictionaryRangedList(){
		DictionaryType dictionaryTypeRanged = new DictionaryType();
		//获取所有支持银行类型
		dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + Constant.INTERFACE_PROVIDER);
		List<com.lefu.commons.cache.bean.Dictionary> bankCodeList = dictionaryTypeRanged.getDictionaries();
		
		//获取所有支持账户类型
		dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + Constant.ACCOUNT_TYPE);
		List<com.lefu.commons.cache.bean.Dictionary> accountTypeList = dictionaryTypeRanged.getDictionaries();
		
		//获取所有支持卡类型
		dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + "DPAY_CARD_TYPE");
		List<com.lefu.commons.cache.bean.Dictionary> cardTypeList = dictionaryTypeRanged.getDictionaries();
		
		//认证类型
		dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + Constant.CER_TYPE);
		List<com.lefu.commons.cache.bean.Dictionary> cerTypeList = dictionaryTypeRanged.getDictionaries();
		
		supportPayList = new HashMap<String,Object>();
		supportPayList.put("bankCodeList",bankCodeList);
		supportPayList.put("accountTypeList", accountTypeList);
		supportPayList.put("cardTypeList", cardTypeList);
		supportPayList.put("cerTypeList", cerTypeList);
	}
	
	/**
	 * 代付路由修改
	 * @return
	 */
	public String dfRouteUpdate(){
		try {
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			dpayConfigFacade.updateRoute(routeConfigBean, auth.getRealname());
			getHttpRequest().setAttribute("success", "true");
		} catch (Exception e) {
			getHttpRequest().setAttribute("success", "false");
		}
		return SUCCESS;
	}
	
	public QueryFacade getQueryFacade() {
		return queryFacade;
	}

	public void setQueryFacade(QueryFacade queryFacade) {
		this.queryFacade = queryFacade;
	}

	public ServiceConfigFacade getServiceConfigFacade() {
		return serviceConfigFacade;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public ServiceConfigBean getServiceConfigBean() {
		return serviceConfigBean;
	}

	public void setServiceConfigBean(ServiceConfigBean serviceConfigBean) {
		this.serviceConfigBean = serviceConfigBean;
	}

	public DpayConfigFacade getDpayConfigFacade() {
		return dpayConfigFacade;
	}

	public void setDpayConfigFacade(DpayConfigFacade dpayConfigFacade) {
		this.dpayConfigFacade = dpayConfigFacade;
	}

	public DpayConfigBean getDpayConfigBean() {
		return dpayConfigBean;
	}

	public void setDpayConfigBean(DpayConfigBean dpayConfigBean) {
		this.dpayConfigBean = dpayConfigBean;
	}

	public RouteConfigBean getRouteConfigBean() {
		return routeConfigBean;
	}

	public void setRouteConfigBean(RouteConfigBean routeConfigBean) {
		this.routeConfigBean = routeConfigBean;
	}

	public void setDpayFacade(DpayFacade dpayFacade) {
		this.dpayFacade = dpayFacade;
	}

	public String getAuditPassword() {
		return auditPassword;
	}

	public void setAuditPassword(String auditPassword) {
		this.auditPassword = auditPassword;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public AgentService getAgentService() {
		return agentService;
	}

	public String getUserName() {
		return userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setAgentService(AgentService agentService) {
		this.agentService = agentService;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ServiceConfigHistoryBean getServiceConfigHistoryBean() {
		return serviceConfigHistoryBean;
	}

	public void setServiceConfigHistoryBean(ServiceConfigHistoryBean serviceConfigHistoryBean) {
		this.serviceConfigHistoryBean = serviceConfigHistoryBean;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public List<RouteConfigBean> getRouteConfigBeanList() {
		return routeConfigBeanList;
	}
	public void setRouteConfigBeanList(List<RouteConfigBean> routeConfigBeanList) {
		this.routeConfigBeanList = routeConfigBeanList;
	}
	public RouteInfoBean getRouteInfoBean() {
		return routeInfoBean;
	}
	public void setRouteInfoBean(RouteInfoBean routeInfoBean) {
		this.routeInfoBean = routeInfoBean;
	}
	public Map<String, Object> getSupportPayList() {
		return supportPayList;
	}
	public void setSupportPayList(Map<String, Object> supportPayList) {
		this.supportPayList = supportPayList;
	}
	
}