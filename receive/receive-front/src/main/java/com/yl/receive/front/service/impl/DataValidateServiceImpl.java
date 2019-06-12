package com.yl.receive.front.service.impl;

import javax.annotation.Resource;
import javax.validation.Validator;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.receive.front.common.Base64Utils;
import com.yl.receive.front.common.RSAUtils;
import com.yl.receive.front.common.ReceiveCodeEnum;
import com.yl.receive.front.common.ReceiveException;
import com.yl.receive.front.model.ReceiveRequest;
import com.yl.receive.front.service.DataValidateService;
import com.yl.receive.hessian.CustomerReceiveConfigHessian;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.receive.hessian.enums.ReceiveConfigStatus;

/**
 * 数据校验服务实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
@Service("dataValidateService")
public class DataValidateServiceImpl implements DataValidateService {

	private Logger logger = LoggerFactory.getLogger(DataValidateServiceImpl.class);
	@Resource
	private Validator validator;
	@Resource
	private CustomerReceiveConfigHessian customerReceiveConfigHessian;
	@Resource
	private CustomerInterface customerInterface;

	@Override
	public <T> void validateParams(T object, Class<?>... groups) {
//		String errorMsg = "";
//		Set<ConstraintViolation<T>> set = validator.validate(object, groups);
//		for (ConstraintViolation<T> constraintViolation : set) {
//			errorMsg = errorMsg + constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage() + "  ";
//		}
//		if (StringUtils.notBlank(errorMsg)) {
//			throw new ReceiveException(ReceiveCodeEnum.PARAMS_ERROR.getCode(), ReceiveCodeEnum.PARAMS_ERROR.getMessage() + "[" + errorMsg + "]");
//		}
		if(object instanceof ReceiveRequest){
			if(((ReceiveRequest) object).getAccNoType().equals("DEBIT_CARD") || ((ReceiveRequest) object).getAccNoType().equals("CREDIT_CARD")){
				if(!checkCard(((ReceiveRequest) object).getAccountNo())){
					throw new ReceiveException(ReceiveCodeEnum.PARAMS_ERROR.getCode(), ReceiveCodeEnum.PARAMS_ERROR.getMessage());
				}
			}
		}
	}

	/**
	 * 交易数据加密
	 */
	@Override
	public String encryptData(String customerNo, String signData) {
		String plainStr = "";
		try {
//			String signKey = customerInterface.getCustomerKey(customerNo, KeyType.RSA).getPrivateKey();
			String signKey = customerReceiveConfigHessian.findByOwnerId(customerNo).getPrivateCer();
			if (StringUtils.notBlank(signKey)) {
				plainStr = Base64Utils.encode(RSAUtils.encryptByPrivateKey(signData.getBytes(), signKey));
				logger.debug("商户编号为【" + customerNo + "】的代扣请求报文加密后原串：{}", plainStr);
			} else {
				throw new ReceiveException(ReceiveCodeEnum.NOT_OPEN.getCode(), ReceiveCodeEnum.NOT_OPEN.getMessage());
			}
		} catch (Exception e) {
			logger.error("实时代扣交易数据加密时异常：{}", e);
			if (e instanceof ReceiveException) {
				throw (ReceiveException) e;
			} else {
				throw new ReceiveException(ReceiveCodeEnum.NOT_OPEN.getCode(), ReceiveCodeEnum.NOT_OPEN.getMessage(), e);
			}
		}
		return plainStr;
	}

	@Override
	public String decryptData(String customerNo, String cipherText) {
		String plainText = "";
		try {
//			String privateKey = customerInterface.getCustomerKey(customerNo, KeyType.RSA).getPrivateKey();
			String privateKey = customerReceiveConfigHessian.findByOwnerId(customerNo).getPrivateCer();
			if (StringUtils.notBlank(privateKey)) {
				plainText = new String(RSAUtils.decryptByPrivateKey(Base64Utils.decode(cipherText), privateKey), "UTF-8");
				logger.debug("商户编号为【" + customerNo + "】的代扣请求报文解密后原串：{}", plainText);
			} else {
				throw new ReceiveException(ReceiveCodeEnum.NOT_OPEN.getCode(), ReceiveCodeEnum.NOT_OPEN.getMessage());
			}
		} catch (Exception e) {
			logger.error("代扣交易数据解密时异常：{}", e);
			if (e instanceof ReceiveException) {
				throw (ReceiveException) e;
			} else {
				throw new ReceiveException(ReceiveCodeEnum.NOT_OPEN.getCode(), ReceiveCodeEnum.NOT_OPEN.getMessage(), e);
			}
		}
		return plainText;
	}

	@Override
	public void checkCustomer(String customerNo, double amount, String ip,
			String domain) {
		ReceiveConfigInfoBean bean = customerReceiveConfigHessian.findByOwnerIdAndStatus(customerNo, ReceiveConfigStatus.TRUE);
		if(bean == null){
			throw new ReceiveException(ReceiveCodeEnum.NOT_OPEN.getCode(), ReceiveCodeEnum.NOT_OPEN.getMessage());
		}
		
		if(bean.getSingleMaxAmount() < amount){
			throw new ReceiveException(ReceiveCodeEnum.SINGLE_AMOUNT_ERROR.getCode(), ReceiveCodeEnum.SINGLE_AMOUNT_ERROR.getMessage());
		}
		if(10d > amount){
			throw new ReceiveException(ReceiveCodeEnum.SINGLE_LOW_AMOUNT_ERROR.getCode(), ReceiveCodeEnum.SINGLE_LOW_AMOUNT_ERROR.getMessage());
		}
		
		if(StringUtils.isNotBlank(bean.getCustIp())){
			if(!bean.getCustIp().equals(ip)){
				throw new ReceiveException(ReceiveCodeEnum.IP_ERROR.getCode(), ReceiveCodeEnum.IP_ERROR.getMessage());
			}
		}
		if(StringUtils.isNotBlank(bean.getDomain())){
			if(domain.indexOf(bean.getDomain()) < 0){
				throw new ReceiveException(ReceiveCodeEnum.DOMIAN_ERROR.getCode(), ReceiveCodeEnum.DOMIAN_ERROR.getMessage());
			}
		}
	}

	@Override
	public void checkCustomer(String customerNo, String ip, String domain) {
		ReceiveConfigInfoBean bean = customerReceiveConfigHessian.findByOwnerIdAndStatus(customerNo, ReceiveConfigStatus.TRUE);
		if(bean == null){
			throw new ReceiveException(ReceiveCodeEnum.NOT_OPEN.getCode(), ReceiveCodeEnum.NOT_OPEN.getMessage());
		}
		if(StringUtils.isNotBlank(bean.getCustIp())){
			if(!bean.getCustIp().equals(ip)){
				throw new ReceiveException(ReceiveCodeEnum.IP_ERROR.getCode(), ReceiveCodeEnum.IP_ERROR.getMessage());
			}
		}
		if(StringUtils.isNotBlank(bean.getDomain())){
			if(domain.indexOf(bean.getDomain()) < 0){
				throw new ReceiveException(ReceiveCodeEnum.DOMIAN_ERROR.getCode(), ReceiveCodeEnum.DOMIAN_ERROR.getMessage());
			}
		}
	}
	
	private boolean checkCard(String cardNo) {
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

}
