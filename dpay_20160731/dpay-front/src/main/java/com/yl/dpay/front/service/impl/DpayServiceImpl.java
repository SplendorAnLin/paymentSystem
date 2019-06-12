package com.yl.dpay.front.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.dpay.front.common.DpayException;
import com.yl.dpay.front.model.DpayInfoBean;
import com.yl.dpay.front.model.DpayQueryResBean;
import com.yl.dpay.front.model.DpayTradeResBean;
import com.yl.dpay.front.service.DpayService;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.beans.RequestBean;
import com.yl.dpay.hessian.beans.ResponseBean;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.dpay.hessian.service.QueryFacade;

/**
 * 代付服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月24日
 * @version V1.0.0
 */
@Service("dpayService")
public class DpayServiceImpl implements DpayService {
	private static Logger log = LoggerFactory.getLogger(DpayServiceImpl.class);

	@Resource
	private DpayFacade dpayFacade;
	@Resource
	private QueryFacade queryFacade;

	@Override
	public DpayTradeResBean singleRequest(DpayInfoBean dpayInfoBean, String customerNo) {

		RequestBean requestBean = new RequestBean();
		requestBean.setAccountName(dpayInfoBean.getAccountName().replaceAll(" ","").trim());
		Pattern p = Pattern.compile("[^0-9]");  
        Matcher accNo = p.matcher(dpayInfoBean.getAccountNo());
		requestBean.setAccountNo(accNo.replaceAll("").trim());
		requestBean.setAccountType(dpayInfoBean.getAccountType());
		requestBean.setAmount(Double.parseDouble(dpayInfoBean.getAmount()));
		requestBean.setCerNo(dpayInfoBean.getCerNo());
		requestBean.setCerType(dpayInfoBean.getCerType());
		requestBean.setDescription(dpayInfoBean.getDescription());
		requestBean.setOwnerId(customerNo);
		requestBean.setRequestNo(dpayInfoBean.getCutomerOrderCode());
		requestBean.setRequestType("INTERFACE");
		requestBean.setBankName(dpayInfoBean.getBankName());
		if("OPEN".equals(dpayInfoBean.getAccountType())){
			requestBean.setBankCode(dpayInfoBean.getBankCode());
		}else{
			requestBean.setCardType(dpayInfoBean.getCardType());
			if("CREDIT".equals(dpayInfoBean.getCardType())){
				requestBean.setCvv(dpayInfoBean.getCvv());
				requestBean.setValidity(dpayInfoBean.getValidity());
			}
		}

		ResponseBean response = null;
		DpayTradeResBean dpayTradeResBean = null;

		try {

			response = dpayFacade.interfaceSingleRequest(requestBean);
			dpayTradeResBean = convertTradeResBen(response);

		} catch (Exception e) {
			if(dpayTradeResBean == null){
				dpayTradeResBean = new DpayTradeResBean();
			}
			log.error("singleRequest dpay-front error customerNo[{}],customerOrderId[{}]", "CustomerNo", dpayInfoBean.getCutomerOrderCode(), e);
			if (e instanceof DpayException) {
				dpayTradeResBean.setResponseCode(((DpayException) e).getCode());
				dpayTradeResBean.setResponseMsg(DpayExceptionEnum.getMessage(dpayTradeResBean.getResponseCode()));
			}else if (e instanceof DpayRuntimeException) {
				dpayTradeResBean.setResponseCode(((DpayRuntimeException) e).getCode());
				dpayTradeResBean.setResponseMsg(DpayExceptionEnum.getMessage(dpayTradeResBean.getResponseCode()));
			} else {
				dpayTradeResBean.setResponseCode(DpayExceptionEnum.SYSERR.getCode());
				dpayTradeResBean.setResponseMsg(DpayExceptionEnum.SYSERR.getMessage());
			}
		}

		return dpayTradeResBean;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public DpayQueryResBean findByCustOrderId(String customerNo, String customerOrderId) {

		DpayQueryResBean dpayQueryResBean = new DpayQueryResBean();

		String queryId = "dpayInterfaceRequest";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ownerId", customerNo);
		params.put("ownerRole", "CUSTOMER");
		params.put("pagingPage", 1);
		params.put("requestNo", customerOrderId);

		Map<String, Object> returnMap = queryFacade.query(queryId, params);
		List valueList = (List) returnMap.get(QueryFacade.VALUELIST);

		// 数据封装
		if (valueList == null || valueList.size() == 0) {
			throw new DpayException(DpayExceptionEnum.CUST_ORDER_NO_EXIST.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.CUST_ORDER_NO_EXIST.getMessage()));
		}
		Map<String, Object> map = (Map<String, Object>) valueList.get(0);
		dpayQueryResBean.setAmount(String.valueOf(map.get("amount")));
		dpayQueryResBean.setCustomerNo(customerNo);
		dpayQueryResBean.setCutomerOrderCode(String.valueOf(map.get("request_no")));
		dpayQueryResBean.setDescription(String.valueOf(map.get("description")));
		dpayQueryResBean.setOrderId(String.valueOf(map.get("flow_no")));
		String orderTime = null;
		if (StringUtils.notBlank(String.valueOf(map.get("finish_date"))) && !String.valueOf(map.get("finish_date")).equals("null")) {
			orderTime = String.valueOf(map.get("finish_date"));
		} else {
			orderTime = String.valueOf(map.get("create_date"));
		}
		if (orderTime != null) {
			orderTime = orderTime.replace(".0", "");
			orderTime = orderTime.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
		}
		dpayQueryResBean.setOrderTime(orderTime);

		// 转码
		dpayQueryResBean = convertPayStatus(dpayQueryResBean, String.valueOf(map.get("status")),
				String.valueOf(map.get("error_msg")));
		return dpayQueryResBean;

	}

	/**
	 * 支付状态转化
	 * @return
	 */
	private DpayQueryResBean convertPayStatus(DpayQueryResBean dpayQueryResBean,String requestStatus, String remitMsg) {

		if (StringUtils.notBlank(requestStatus)) {
			if (requestStatus.equals("SUCCESS")) {
				dpayQueryResBean.setResponseCode(DpayExceptionEnum.REMITSUCCESS.getCode());
				dpayQueryResBean.setResponseMsg(DpayExceptionEnum.REMITSUCCESS.getMessage());
			} else if (requestStatus.equals("FAILED")) {
				dpayQueryResBean.setResponseCode(DpayExceptionEnum.REMITFAILED.getCode());
				if (remitMsg == null || remitMsg.equals("null")) {
					remitMsg = "";
				} else {
					remitMsg = "," + remitMsg;
				}
				dpayQueryResBean.setResponseMsg(DpayExceptionEnum.REMITFAILED.getMessage() + remitMsg);
			}else if (requestStatus.equals("HANDLEDING")) {
				dpayQueryResBean.setResponseCode(DpayExceptionEnum.HANDLE.getCode());
				dpayQueryResBean.setResponseMsg(DpayExceptionEnum.HANDLE.getMessage());
			}else if (requestStatus.equals("WAIT")){
				dpayQueryResBean.setResponseCode(DpayExceptionEnum.WAITAUDIT.getCode());
				dpayQueryResBean.setResponseMsg(DpayExceptionEnum.WAITAUDIT.getMessage());
			} else {
				throw new DpayException(DpayExceptionEnum.SYSERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.SYSERR.getCode()));
			}
		} else {
			throw new DpayException(DpayExceptionEnum.SYSERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.SYSERR.getCode()));
		}

		return dpayQueryResBean;
	}

	public DpayTradeResBean convertTradeResBen(ResponseBean responseBean) {
		DpayTradeResBean dpayTradeResBean = new DpayTradeResBean();
		dpayTradeResBean.setCustomerOrderCode(responseBean.getRequestNo());
		dpayTradeResBean.setOrderCode(responseBean.getFlowNo());
		dpayTradeResBean.setOrderTime(responseBean.getHandleTime());
		dpayTradeResBean.setResponseCode(responseBean.getResponseCode());
		dpayTradeResBean.setResponseMsg(responseBean.getResponseMsg());
		return dpayTradeResBean;
	}

	@Override
	public DpayTradeResBean drawCash(String customerNo, double amount) {
		DpayTradeResBean dpayTradeResBean = null;
		try {
			ResponseBean responseBean = dpayFacade.drawCash(customerNo, amount);
			dpayTradeResBean = new DpayTradeResBean();
			dpayTradeResBean.setResponseCode(responseBean.getResponseCode());
			dpayTradeResBean.setResponseMsg(responseBean.getResponseMsg());
			dpayTradeResBean.setOrderTime(responseBean.getHandleTime());
			return dpayTradeResBean;
		} catch (Exception e) {
			throw new DpayException(DpayExceptionEnum.SYSERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.SYSERR.getCode()));
		}
	}

}
