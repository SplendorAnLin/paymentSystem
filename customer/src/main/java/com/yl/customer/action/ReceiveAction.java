package com.yl.customer.action;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.customer.entity.Authorization;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.dpay.hessian.utils.RSAUtils;
import com.yl.receive.hessian.CustomerReceiveConfigHessian;
import com.yl.receive.hessian.ReceiveFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

/**
 * 代收控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class ReceiveAction  extends Struts2ActionSupport{
	private static final long serialVersionUID = 8995432988301364380L;
	private ReceiveFacade recfFacade;
	private ReceiveConfigInfoBean receiveConfigInfo;
	private CustomerReceiveConfigHessian customerReceiveConfigHessian;
	private String errorsMsg;
	private String customerNo;
	private String msg;
	
	/**
	 * 代收订单Export
	 * @return
	 */
	public String receiveOrderExport(){
		try {
			String queryId = "receiveOrderExport";
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("customer_no", auth.getCustomerno());
			Map<String, Object> returnMap = recfFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 代收订单合计查询
	 * @return
	 */
	public String receiveOrderSum(){
		try {
			String queryId = "receiveOrderSum";
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("customer_no", auth.getCustomerno());
			Map<String, Object> returnMap = recfFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
			if(returnMap != null){
				msg = JsonUtils.toJsonString(returnMap.get("valueList"));
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 代收更换密钥
	 */
	public void dsChangeKeys(){
		try {
			Map<String, Object> keysMap = RSAUtils.genKeyPair();
			String resStr = null;
			String puk = RSAUtils.getPrivateKey(keysMap);
			if (puk.indexOf("\r\n") > -1) {
				resStr = RSAUtils.getPrivateKey(keysMap).replaceAll("\r\n", "&#92;r&#92;n\r\n") + "_"
						+ RSAUtils.getPublicKey(keysMap).replaceAll("\r\n", "&#92;r&#92;n\r\n");
			} else {
				resStr = RSAUtils.getPrivateKey(keysMap).replaceAll("\n", "&#92;r&#92;n\r\n") + "_"
						+ RSAUtils.getPublicKey(keysMap).replaceAll("\n", "&#92;r&#92;n\r\n");
			}
			getHttpResponse().getWriter().write(resStr);
		} catch (Exception e) {
			logger.error("商户:" + customerNo + "生成代收密钥异常: {}", e);
		}
	}
	
	/**
	 * 获取原密钥,并且新生成一对密钥
	 */
	public void getDsChangeKeys() {
		try {
			receiveConfigInfo = customerReceiveConfigHessian.findByOwnerId(customerNo);
			if (receiveConfigInfo != null) {
				Map<String, Object> keysMap = RSAUtils.genKeyPair();
				String resStr = null;
				if (receiveConfigInfo.getPublicCer().indexOf("\r\n") > -1) {
					resStr = receiveConfigInfo.getPublicCer().replaceAll("\r\n", "&#92;r&#92;n\r\n") + "_"
							+ RSAUtils.getPublicKey(keysMap).replaceAll("\r\n", "&#92;r&#92;n\r\n") + "_"
							+ RSAUtils.getPrivateKey(keysMap).replaceAll("\r\n", "&#92;r&#92;n\r\n");
				} else {
					resStr = receiveConfigInfo.getPublicCer().replaceAll("\n", "&#92;r&#92;n\r\n") + "_"
							+ RSAUtils.getPublicKey(keysMap).replaceAll("\n", "&#92;r&#92;n\r\n") + "_"
							+ RSAUtils.getPrivateKey(keysMap).replaceAll("\n", "&#92;r&#92;n\r\n");
				}
				getHttpResponse().getWriter().write(resStr);
			} else {
				getHttpResponse().getWriter().write("");
			}
		} catch (Exception e) {
			logger.error("商户:" + customerNo + "生成代收密钥异常: {}", e);
		}
	}
	
	/**
	 * 更新商户密钥
	 */
	public String dsPayKeyUpdate() {
		try {
			receiveConfigInfo.setPublicCer(receiveConfigInfo.getPublicCer().replaceAll("&#92;r&#92;n", ""));
			receiveConfigInfo.setPrivateCer(receiveConfigInfo.getPrivateCer().replaceAll("&#92;r&#92;n", ""));
			customerReceiveConfigHessian.updateKeys(receiveConfigInfo);
			errorsMsg = "success";
		} catch (Exception e) {
			logger.error("商户:" + customerNo + "更新代收密钥异常: {}", e);
			errorsMsg = "failed";
		}
		return SUCCESS;
	}
	
	/**
	 * 代收订单查询
	 * @return
	 */
	public String receiveOrderQuery(){
		try {
			String queryId = "receiveOrderQuery";
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("customer_no", auth.getCustomerno());
			Map<String, Object> returnMap = recfFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
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

	public ReceiveFacade getRecfFacade() {
		return recfFacade;
	}

	public void setRecfFacade(ReceiveFacade recfFacade) {
		this.recfFacade = recfFacade;
	}

	public ReceiveConfigInfoBean getReceiveConfigInfo() {
		return receiveConfigInfo;
	}

	public void setReceiveConfigInfo(ReceiveConfigInfoBean receiveConfigInfo) {
		this.receiveConfigInfo = receiveConfigInfo;
	}

	public CustomerReceiveConfigHessian getCustomerReceiveConfigHessian() {
		return customerReceiveConfigHessian;
	}

	public void setCustomerReceiveConfigHessian(CustomerReceiveConfigHessian customerReceiveConfigHessian) {
		this.customerReceiveConfigHessian = customerReceiveConfigHessian;
	}

	public String getErrorsMsg() {
		return errorsMsg;
	}

	public void setErrorsMsg(String errorsMsg) {
		this.errorsMsg = errorsMsg;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}