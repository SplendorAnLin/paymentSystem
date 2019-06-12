package com.yl.receive.front.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.receive.front.common.Base64Utils;
import com.yl.receive.front.common.RSAUtils;
import com.yl.receive.front.common.ReceiveCodeEnum;
import com.yl.receive.front.common.ReceiveException;
import com.yl.receive.front.service.EncryptAndDecryptService;

/**
 * 数据加解密服务实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
@Service("encryptAndDecryptService")
public class EncryptAndDecryptServiceImpl implements EncryptAndDecryptService {

	private Logger logger = LoggerFactory.getLogger(EncryptAndDecryptServiceImpl.class);
	@Resource
	private CustomerInterface customerInterface;

	/**
	 * 解密
	 */
	@Override
	public String decodeData(String customerNo, String cipherText) {
		String plainText = "";
		try {
			String privateKey = customerInterface.getCustomerKey(customerNo, KeyType.RSA).getPrivateKey();
			logger.debug("私钥{}", privateKey);
			if (StringUtils.notBlank(privateKey)) {
				plainText = new String(RSAUtils.decryptByPrivateKey(Base64Utils.decode(cipherText), privateKey), "UTF-8");
				logger.debug("商户编号为:{}的代收请求报文解密后原串：{}",customerNo, plainText);
			} else {
				throw new ReceiveException(ReceiveCodeEnum.NOT_OPEN.getCode(), ReceiveCodeEnum.NOT_OPEN.getMessage());
			}
		} catch (Exception e) {
			logger.error("代收交易数据解密时异常：{}", e);
			throw new ReceiveException(ReceiveCodeEnum.DECRYPT_ERROR.getCode(), ReceiveCodeEnum.DECRYPT_ERROR.getMessage(), e);
		}
		return plainText;
	}

	/**
	 * 加密
	 */
	@Override
	public String encodeData(String customerNo, String plainTextStr) {
		String cipherText = "";
		try {
			String privateKey = customerInterface.getCustomerKey(customerNo, KeyType.RSA).getPrivateKey();
			logger.debug("私钥{}", privateKey);
			if (StringUtils.notBlank(privateKey)) {
				cipherText = Base64Utils.encode(RSAUtils.encryptByPrivateKey(plainTextStr.getBytes(), privateKey));
				logger.debug("商户编号为【" + customerNo + "】的代收请求报文加密前原串：{}", plainTextStr);
			} else {
				throw new ReceiveException(ReceiveCodeEnum.NOT_OPEN.getCode(), ReceiveCodeEnum.NOT_OPEN.getMessage());
			}
		} catch (Exception e) {
			logger.error("代收交易数据加密时异常：{}", e);
			throw new ReceiveException(ReceiveCodeEnum.ENCRYPT_ERROR.getCode(), ReceiveCodeEnum.ENCRYPT_ERROR.getMessage(), e);
		}
		return cipherText;
	}
}
