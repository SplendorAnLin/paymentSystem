package com.yl.client.front.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.client.front.common.ConverMap;
import com.yl.client.front.common.DictUtils;
import com.yl.client.front.service.FeeInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("feeInfoService")
public class FeeInfoServiceImpl implements FeeInfoService{
	private static Logger log = LoggerFactory.getLogger(FeeInfoServiceImpl.class);
	
	@Resource
	private CustomerInterface customerInterface;
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCustomerAllFee(String customerNo){
		String temp = customerInterface.customerProductInfo(customerNo);
        Map map;
		if (temp!=null&&temp.length()>0) {
			JSONArray list=JSONArray.fromObject(temp);
			if (list!=null&&list.size()>0) {
				List<Map<String, Object>> reqList = new ArrayList<>();
				try {
					for (int i = 0; i < list.size(); i++) {
						map = JsonUtils.toJsonToObject(list.get(i), Map.class);
						map.put("createTime", JSONObject.fromObject(map.get("createTime")).get("time"));
						map.put("templateInterfacePolicy", null);//无效,但是APP得保留
						reqList.add(map);
					}
					return DictUtils.listOfDict(DictUtils.listOfDict(reqList, "SHARE_PAYTYPE", "productType"),"PAYTYPE_IMG","productType","_IMG");
				} catch (Exception e) {
					log.error("商户费率处理异常,{}",e);
				}
			}
		}
		return null;
	}

	public List<Map<String, Object>> getCustomerFeeIn(String customerNo){
		List<Map<String, Object>> list=getCustomerAllFee(customerNo);
		if (list == null) {
			return new ArrayList<Map<String, Object>>();
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("productType").equals("REMIT") && list.get(i).get("productType").equals("HOLIDAY_REMIT")) {
				list.remove(list.get(i));
				i--;
			}
		}
		return list;
	}

	public List<Map<String, Object>> getCustomerFee(String customerNo, List<Map<String, Object>> payFee){
		List<Map<String, Object>> list=getCustomerAllFee(customerNo);
		List<Map<String, Object>> dpayFee = new ArrayList<>();
		if (list == null) {
			return dpayFee;
		}
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			// 付款产品和收款产品分开返回给APP
			if (list.get(i).get("productType").equals("REMIT") && list.get(i).get("productType").equals("HOLIDAY_REMIT")) {
				dpayFee.add(map);
			} else {
				// 产品中文名
				if ("QUICKPAY".equals(map.get("productType"))) {
					map.put("productType_CN", "快捷支付");
				}else if ("AUTHPAY".equals(map.get("productType"))) {
					map.put("productType_CN", "认证支付");
				}
				// 赋值APP打开方式
				if (map.get("use")!=null) {
					map.put("use", map.get("use")+","+productType(map.get("productType").toString()));
				}else {
					map.put("use", productType(map.get("productType").toString()));
				}
				payFee.add(map);
			}
		}
		return list;
	}

	/**
	 * 产品类型
	 * @param str
	 * @return
	 */
	private String productType(String str){
		switch (str) {
			case "ALIPAY"://扫码
			case "WXNATIVE":
			case "UNIOUPAYNATIVE":
				return "B_SCAN";
			case "ALIPAYMICROPAY"://主扫
			case "WXMICROPAY":
				return "C_SCAN";
			case "MPOS"://pos
				return "POS";
			case "AUTHPAY"://POS带出卡界面属性
			case "QUICKPAY":
				return "POS,CARD";
			default:
				return "OTHER";
		}
	}

	public List<Map<String, Object>> getCustomerFeeOut(String customerNo){
		List<Map<String, Object>> list=getCustomerAllFee(customerNo);
		if (list == null) {
			return new ArrayList<Map<String, Object>>();
		}
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).get("productType").equals("REMIT") && !list.get(i).get("productType").equals("HOLIDAY_REMIT")) {
				list.remove(list.get(i));
				i--;
			}
		}
		return list;
	}
}