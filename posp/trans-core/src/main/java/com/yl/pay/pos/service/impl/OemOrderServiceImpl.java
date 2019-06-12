package com.yl.pay.pos.service.impl;


import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.CustomerCertBean;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.pay.pos.bean.OemPosOrderBean;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.service.IOemOrderService;
import com.yl.pay.pos.util.HttpUtils;
import com.yl.pay.pos.util.MD5;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Title: 订单  服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class OemOrderServiceImpl extends BaseService implements IOemOrderService{

	private static final Log log = LogFactory.getLog(OemOrderServiceImpl.class);

	private CustomerInterface customerInterface;
	
	public Map<String,String> create(Order order) {
		Map<String,String > respMap = new HashMap<String,String>();
		CustomerCertBean customerCertBean = customerInterface.findCustomerCertByCustNo(order.getCustomerNo());
		String url = customerCertBean.getEnterpriseUrl()+"/trade";
		log.info("++++++++++++OEM生成订单调用接口请求URL:"+url);
		
		OemPosOrderBean opob = generateCreate(order);
		try {
			String data = generateData(opob);
			log.info("++++++++++++OEM生成订单调用接口请求数据:"+data);
			String rspCode = HttpUtils.sendReq(url, data, "GET");
			log.info("++++++++++++OEM生成订单调用接口请求结果:"+rspCode);
			respMap = JsonUtils.toObject(rspCode, Map.class);
			return respMap;
		} catch (Exception e) {
			log.error("+++++++++OEM生成订单异常,orderNo:" + "",e);
			respMap.put("result", "FAILED");
			respMap.put("respCode", TransExceptionConstant.SYSTEM_EXCEPTION);
			return respMap;
		}
		

	}
	

	public Map<String,String> complete(Order order) {
		Map<String,String > respMap = new HashMap<String,String>();
		CustomerCertBean customerCertBean = customerInterface.findCustomerCertByCustNo(order.getCustomerNo());
		String url = customerCertBean.getEnterpriseUrl()+"/complete";
		log.info("++++++++++++OEM完成订单调用接口请求URL:"+url);
		
		OemPosOrderBean opob = generateComplete(order);
		try {
			String data = generateData(opob);
			log.info("++++++++++++OEM完成订单调用接口请求数据:"+data);
			String rspCode = HttpUtils.sendReq(url, data, "GET");
			log.info("++++++++++++OEM完成订单调用接口请求结果:"+rspCode);
			respMap = JsonUtils.toObject(rspCode, Map.class);
			if(!"SUCCESS".equals(respMap.get("result"))){
				log.error("+++++++++++++++OEM完成订单失败，respCode:"+respMap.get("respCode"));
			}
		} catch (Exception e) {
			log.error("+++++++++OEM完成订单异常,orderNo:" + "",e);
			respMap.put("result", "FAILED");
			respMap.put("respCode", TransExceptionConstant.SYSTEM_EXCEPTION);
		}
		
		return respMap;
		
	}
	
	private OemPosOrderBean generateCreate(Order order){
		OemPosOrderBean oemPosOrderBean = new OemPosOrderBean();
		oemPosOrderBean.setOrderId(order.getId());
		oemPosOrderBean.setTransType(order.getTransType());
        oemPosOrderBean.setCustomerNo(order.getCustomerNo());
        oemPosOrderBean.setCustomerOrderCode(order.getCustomerOrderCode());
        oemPosOrderBean.setExternalId(order.getExternalId());
        oemPosOrderBean.setPosBatch(order.getPosBatch());
        oemPosOrderBean.setPosCati(order.getPosCati());
        oemPosOrderBean.setPosRequestId(order.getPosRequestId());
        oemPosOrderBean.setPan(order.getPan());
        oemPosOrderBean.setCardType(order.getCardType());
        oemPosOrderBean.setAmount(order.getAmount());
        oemPosOrderBean.setCurrencyType(order.getCurrencyType());
        oemPosOrderBean.setDescription(order.getDescription());
        oemPosOrderBean.setCreateTime(order.getCreateTime());
        oemPosOrderBean.setCompleteTime(order.getCompleteTime());
        oemPosOrderBean.setSettleTime(order.getSettleTime());
        oemPosOrderBean.setStatus(order.getStatus());
        oemPosOrderBean.setCreditStatus(order.getCreditStatus());
        oemPosOrderBean.setCreditTime(order.getCreditTime());
        oemPosOrderBean.setFinalPaymentId(order.getFinalPaymentId());
        oemPosOrderBean.setIsIC(order.getIsIC());
        oemPosOrderBean.setBankCost(order.getCustomerFee());
        oemPosOrderBean.setBusinessType(order.getBusinessType());
        oemPosOrderBean.setIsSyn(order.getIsSyn());
        oemPosOrderBean.setShopId(order.getShop().getId());
        oemPosOrderBean.setCustomerId(order.getCustomer().getId());
        oemPosOrderBean.setCardinfoId(order.getCardInfo().getId());
		return oemPosOrderBean;
	}
	
	private OemPosOrderBean generateComplete(Order order){
		OemPosOrderBean oemPosOrderBean = new OemPosOrderBean();
		oemPosOrderBean.setOrderId(order.getId());
		oemPosOrderBean.setTransType(order.getTransType());
		oemPosOrderBean.setStatus(order.getStatus());
		oemPosOrderBean.setCompleteTime(order.getCompleteTime());
		oemPosOrderBean.setAuthorizationCode(order.getAuthorizationCode());
        oemPosOrderBean.setCustomerNo(order.getCustomerNo());
        oemPosOrderBean.setCustomerOrderCode(order.getCustomerOrderCode());
        oemPosOrderBean.setBankCost(order.getCustomerFee());
        oemPosOrderBean.setExternalId(order.getExternalId());
        oemPosOrderBean.setPosBatch(order.getPosBatch());
        oemPosOrderBean.setPosCati(order.getPosCati());
        oemPosOrderBean.setPosRequestId(order.getPosRequestId());
        oemPosOrderBean.setCreditStatus(order.getCreditStatus());
        oemPosOrderBean.setResponCode(order.getResponCode());
		return oemPosOrderBean;
	}
	
	private String generateData(OemPosOrderBean oemPosOrderBean) throws Exception{
		String bussinessBody = JsonUtils.toJsonString(oemPosOrderBean);
		String customerKey = customerInterface.getCustomerMD5Key(oemPosOrderBean.getCustomerNo());
		String sign = MD5.MD5Encode(customerKey+bussinessBody,"utf-8").toUpperCase();
		String data = 
					"apiCode=YL-PAY" + "&" + 
					"inputCharset=UTF-8" + "&" + 
					"signType=MD5" + "&" + 
					"orderType=POS" + "&" + 
					"baseCustomerNo=" + oemPosOrderBean.getCustomerNo() + "&" + 
					"bussinessBody=" + bussinessBody + "&" +
					"sign=" + sign;
		
		return data;
	}


	public CustomerInterface getCustomerInterface() {
		return customerInterface;
	}


	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}
	
	
}
