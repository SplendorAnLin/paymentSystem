package com.yl.boss.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;

import net.sf.json.JSONObject;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.pay.common.util.Md5Util;
import com.yl.boss.Constant;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.CustomerKey;
import com.yl.boss.service.CustomerKeyService;

/**
 * 商户密钥控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class CustomerKeyAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -3551574728619524727L;
	private CustomerKeyService customerKeyService;
	private CustomerKey customerKey;
	
	public String create(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		customerKeyService.create(customerKey, auth.getRealname());
		return SUCCESS;
	}
	
	public String update(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		customerKeyService.update(customerKey, auth.getRealname());
		return SUCCESS;
	}
	
	public String findById(){
		customerKey = customerKeyService.findById(customerKey.getId());
		return SUCCESS;
	}
	
	public void checkCustomerKey(){
		customerKey = customerKeyService.findBy(customerKey.getCustomerNo(), customerKey.getProductType());
		if(customerKey == null){
			write("TRUE");
		}else{
			write("FALSE");
		}
	}
	
	/**
	 * 客户端查询密钥接口
	 */
	public void cliQueryCustomerKey(){
		Map<String, String> reqParams = null;
		Map<String, String> resParams = null;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)getHttpRequest().getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine())!=null){
			    sb.append(line);
			}
			reqParams = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>() {});
			
			// check params
			if(reqParams == null){
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "01");
				resParams.put("responseMsg", "参数错误");
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));
			
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
				return;
			}
			
			// check sign
			Map<String, String> params = new HashMap<>();
			params.put("customerNo", reqParams.get("customerNo"));
			params.put("timestamp", reqParams.get("timestamp"));
			if(!Md5Util.hmacSign(JsonUtils.toJsonString(params), reqParams.get("timestamp").substring(2, 10)).equals(reqParams.get("sign"))){
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "01");
				resParams.put("responseMsg", "签名错误");
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
				return;
			}
			
			customerKey = customerKeyService.findBy(reqParams.get("customerNo"), KeyType.MD5);
			
			if (customerKey != null && StringUtils.notBlank(customerKey.getKey())) {
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "00");
				resParams.put("responseMsg", "成功");
				resParams.put("key", customerKey.getKey());
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
				return;
			} else {
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "01");
				resParams.put("responseMsg", "商户密钥不存在");
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
				return;
			}
		} catch (Exception e) {
			logger.error(e.toString());
			resParams = new HashMap<String, String>();
			resParams.put("responseCode", "01");
			resParams.put("responseMsg", "系统异常");
			resParams.put("timestamp", String.valueOf(System.nanoTime()));
			resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));
		
			try {
				getHttpResponse().setCharacterEncoding("UTF-8");
				getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
				getHttpResponse().getWriter().close();
			} catch (IOException e1) {
				logger.error(e1.toString());
			}
			return;
		}
	}

	public CustomerKeyService getCustomerKeyService() {
		return customerKeyService;
	}

	public void setCustomerKeyService(CustomerKeyService customerKeyService) {
		this.customerKeyService = customerKeyService;
	}

	public CustomerKey getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(CustomerKey customerKey) {
		this.customerKey = customerKey;
	}

}
