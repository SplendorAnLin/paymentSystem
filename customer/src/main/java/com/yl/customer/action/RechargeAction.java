package com.yl.customer.action;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.pay.common.util.StringUtil;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;

/**
 * 充值
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class RechargeAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -5439984799234172860L;
	private CustomerInterface customerInterface;
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(new InputStreamReader(RechargeAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("RechargeAction load Properties error:", e);
		}
	}
	
	public void rechargeSign(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		CustomerKey customerKey = customerInterface.getCustomerKey(auth.getCustomerno(), KeyType.MD5);
		Map<String, Object> resParams = new HashMap<String, Object>();
		if(customerKey == null){
			resParams.put("status", "FAILED");
			resParams.put("msg", "商户密钥不存在！");
			write(JsonUtils.toJsonString(resParams));
		}
		String sign = sign(getHttpRequest().getParameterMap(), customerKey.getKey());
		if(StringUtil.notNull(sign)){
			resParams.put("sign", sign);
			resParams.put("partner", auth.getCustomerno());
			resParams.put("status", "SUCCESS");
			resParams.put("payUrl", prop.get("customer.recharge.url"));
			write(JsonUtils.toJsonString(resParams));
		}else{
			resParams.put("status", "FAILED");
			resParams.put("msg", "签名失败！");
			write(JsonUtils.toJsonString(resParams));
		}
		
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
			sign = DigestUtils.md5DigestAsHex(partnerString.getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error("MD5出异常了！！", e);
		}
		return sign;
	}

	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}

}
