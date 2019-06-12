package com.yl.dpay.front.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.dpay.front.common.Base64Utils;
import com.yl.dpay.front.common.DpayException;
import com.yl.dpay.front.common.RSAUtils;
import com.yl.dpay.front.service.DataValidateService;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;
import com.yl.dpay.hessian.service.ServiceConfigFacade;

/**
 * 时间验证服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月24日
 * @version V1.0.0
 */
@Service("dataValidateService")
public class DataValidateServiceImpl implements DataValidateService {
	
	private static Logger log = LoggerFactory.getLogger(DataValidateServiceImpl.class);
	
	@Resource
	private ServiceConfigFacade serviceConfigFacade;

	@Transactional
	public String decryptData(String customerNo, String signData) {
		String plainStr = "";
		try {
			String signKey = serviceConfigFacade.getPrivateKey(customerNo);
			plainStr = new String(RSAUtils.decryptByPrivateKey(Base64Utils.decode(signData), signKey));
		} catch (Exception e) {	
			if (e instanceof DpayException) {
				throw (DpayException) e;
			} else {
				throw new DpayException(DpayExceptionEnum.DECRYPTFAILED.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.DECRYPTFAILED.getCode()), e);
			}
		}
		return plainStr;
	}

	@Transactional
	public String encryptData(String customerNo,Object dataObj) {
		try {
			String signKey = serviceConfigFacade.getPrivateKey(customerNo);
			return Base64Utils.encode(RSAUtils.encryptByPrivateKey(JsonUtils.toJsonString(dataObj).getBytes(), signKey));
		} catch (Exception e) {
			throw new DpayException(DpayExceptionEnum.SIGNERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.SIGNERR.getCode()), e);
		}
	}

	@Transactional
	public void checkCustomerAndReferer(String customerNo,String ip,String domain) {
		ServiceConfigBean configBean = serviceConfigFacade.query(customerNo);
		if(configBean == null || !configBean.getValid().equals("TRUE")){
			throw new DpayException(DpayExceptionEnum.NOTOPEN.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.NOTOPEN.getCode()));
		}
		if(StringUtils.isNotBlank(configBean.getCustIp())){
			if(configBean.getCustIp().indexOf(ip) <= -1){
				log.error("商户ip不匹配 代付配置ip:{},商户服务器ip:{}", configBean.getCustIp(),ip);
				throw new DpayException(DpayExceptionEnum.IPERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.IPERR.getCode()));
			}
		}
//		if(StringUtils.isNotBlank(configBean.getDomain())){
//			if(configBean.getDomain().indexOf(domain) < 0){
//				log.error("商户域名不匹配 代付配置域名:{},商户服务器域名:{}", configBean.getDomain(),domain);
//				throw new DpayException(DpayExceptionEnum.DOMAINERR.getCode(), DpayExceptionEnum.getErrMsg(DpayExceptionEnum.DOMAINERR.getCode()));
//			}
//		}
	}

}
