package com.yl.dpay.front.common;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.dpay.front.model.DpayInfoBean;
import com.yl.dpay.front.model.DpayQueryReqBean;
import com.yl.dpay.front.model.DpayTradeReqBean;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;

/**
 * 参数校验工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月21日
 * @version V1.0.0
 */
public class CheckParams {

	private static Logger log = LoggerFactory.getLogger(CheckParams.class);
	
	public static boolean checkCard(String cardNo) {
		if (!NumberUtils.isNumber(cardNo) || cardNo.length() < 14) return false;
//		String[] nums = cardNo.split("");
//		int sum = 0;
//		int index = 1;
//		for (int i = 0; i < nums.length; i++) {
//			if ((i + 1) % 2 == 0) {
//				if ("".equals(nums[nums.length - index])) {
//					continue;
//				}
//				int tmp = Integer.parseInt(nums[nums.length - index]) * 2;
//				if (tmp >= 10) {
//					String[] t = String.valueOf(tmp).split("");
//					tmp = Integer.parseInt(t[1]) + Integer.parseInt(t[2]);
//				}
//				sum += tmp;
//			} else {
//				if ("".equals(nums[nums.length - index])) continue;
//				sum += Integer.parseInt(nums[nums.length - index]);
//			}
//			index++;
//		}
//		if (sum % 10 != 0) {
//			return false;
//		}
		return true;
	}
	
	public static void validateParams(Object object) {
		if (object == null) {
			throw new DpayException(DpayExceptionEnum.PARAMSERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.PARAMSERR.getCode()));
		}
		if (object instanceof DpayInfoBean) {
			DpayInfoBean bean = (DpayInfoBean) object;
			if (StringUtils.isBlank(bean.getAccountName()) || bean.getAccountName().getBytes().length > 200) {
				throw new DpayException(DpayExceptionEnum.ACCNAMEERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.ACCNAMEERR.getCode()));
			}
			if (StringUtils.isBlank(bean.getAccountNo()) || bean.getAccountNo().getBytes().length > 50) {
				throw new DpayException(DpayExceptionEnum.ACCNOERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.ACCNOERR.getCode()));
			} else {
				Pattern p = Pattern.compile("[^0-9]");  
		        Matcher accNo = p.matcher(bean.getAccountNo());
		        if(accNo.replaceAll("").trim().length() != bean.getAccountNo().length()){
		        	throw new DpayException(DpayExceptionEnum.ACCNOERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.ACCNOERR.getCode()));
		        }
				
			}
			if (StringUtils.isBlank(bean.getAccountType()) || bean.getAccountType().getBytes().length > 20
					|| (!bean.getAccountType().equals("OPEN") && !bean.getAccountType().equals("INDIVIDUAL"))) {
				throw new DpayException(DpayExceptionEnum.ACCTYPEERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.ACCTYPEERR.getCode()));
			}
			if (StringUtils.isBlank(bean.getAmount()) || bean.getAmount().getBytes().length > 20
					|| new BigDecimal(bean.getAmount()).compareTo(new BigDecimal(0)) <= 0
					|| (bean.getAmount().length() - bean.getAmount().indexOf(".") > 3 && bean.getAmount().indexOf(".") != -1)) {
				throw new DpayException(DpayExceptionEnum.AMOUNTERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.AMOUNTERR.getCode()));
			}
			if (StringUtils.isBlank(bean.getCutomerOrderCode()) || bean.getCutomerOrderCode().getBytes().length > 30) {
				throw new DpayException(DpayExceptionEnum.CUSTORDERERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.CUSTORDERERR.getCode()));
			}
			if (StringUtils.isNotBlank(bean.getDescription()) && bean.getDescription().getBytes().length > 200) {
				throw new DpayException(DpayExceptionEnum.USERAGEERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.USERAGEERR.getCode()));
			}
			if(StringUtils.isBlank(bean.getBankName())){
				throw new DpayException(DpayExceptionEnum.OPENBANKERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.OPENBANKERR.getCode()));
			}
			if (StringUtils.isBlank(bean.getNotifyUrl()) || bean.getNotifyUrl().getBytes().length > 256) {
				throw new DpayException(DpayExceptionEnum.NOTIFYADDRERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.NOTIFYADDRERR.getCode()));
			}
			if(bean.getAccountType().equals("INDIVIDUAL")){
				if (StringUtils.isBlank(bean.getCardType()) || (!bean.getCardType().equals("DEBIT")&&!bean.getCardType().equals("CREDIT"))) {
					throw new DpayException(DpayExceptionEnum.CARDTYPEERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.CARDTYPEERR.getCode()));
				}else{
					if(bean.getCardType().equals("CREDIT")){
						if(StringUtils.isBlank(bean.getCvv()) || !NumberUtils.isNumber(bean.getCvv()) || bean.getCvv().length() > 3){
							throw new DpayException(DpayExceptionEnum.CVVERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.CVVERR.getCode()));
						}
						if(StringUtils.isBlank(bean.getValidity()) || !NumberUtils.isNumber(bean.getValidity()) || bean.getValidity().length() > 4){
							throw new DpayException(DpayExceptionEnum.VALIDATEERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.VALIDATEERR.getCode()));
						}
					}
				}
			}
			if(StringUtils.isBlank(bean.getCerType())){
				throw new DpayException(DpayExceptionEnum.CERTYPEERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.CERTYPEERR.getCode()));
			}else{
				if("ID".equals(bean.getCerType()) || "PROTOCOL".equals(bean.getCerType())){
					if(StringUtils.isBlank(bean.getCerNo())){
						throw new DpayException(DpayExceptionEnum.CERNOERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.CERNOERR.getCode()));
					}
				}else{
					if(!"NAME".equals(bean.getCerType())){
						throw new DpayException(DpayExceptionEnum.CERTYPEERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.CERTYPEERR.getCode()));
					}
				}
			}
			
			if (StringUtils.isNotBlank(bean.getNotifyUrl()) && (bean.getNotifyUrl().getBytes().length > 256 || bean.getNotifyUrl().indexOf("http://") != 0)) {
				if (bean.getNotifyUrl().indexOf("https://") != 0) {
					throw new DpayException(DpayExceptionEnum.NOTIFYADDRERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.NOTIFYADDRERR.getCode()));
				}
			}
		}

		if (object instanceof DpayTradeReqBean) {
			DpayTradeReqBean bean = (DpayTradeReqBean) object;
			if (StringUtils.isBlank(bean.getCustomerRequestTime()) || bean.getCustomerRequestTime().length() != 14) {
				throw new DpayException(DpayExceptionEnum.DATEERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.DATEERR.getCode()));
			}
		}
		
		if (object instanceof DpayQueryReqBean) {
			DpayQueryReqBean bean = (DpayQueryReqBean) object;
			if(bean.getCustomerNo() == null){
				throw new DpayException(DpayExceptionEnum.PARAMSERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.PARAMSERR.getCode()));
			}
		}
	}
	
	public static DpayInfoBean convertApplyData(String cipherText) throws Exception {

		DpayInfoBean dpayInfoBean = null;
		try {
			dpayInfoBean = JsonUtils.toObject(cipherText, DpayInfoBean.class);
			CheckParams.validateParams(dpayInfoBean);
		} catch (Exception e) {
			log.error("dpay convertApplyData", e);
			if (e instanceof DpayException) {
				throw (DpayException) e;
			} else {
				throw new DpayException(DpayExceptionEnum.PARAMSERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.PARAMSERR.getCode()), e);
			}
		}
		return dpayInfoBean;

	}

	public static String getDomain(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
	}

}
