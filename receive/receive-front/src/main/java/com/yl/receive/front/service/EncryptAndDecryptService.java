package com.yl.receive.front.service;

/**
 * 数据加解密接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public interface EncryptAndDecryptService {

	/**
	 * 解密数据
	 * @param partner
	 * @param plainTextStr
	 * @return
	 */
	public String decodeData(String partner, String plainTextStr);

	/**
	 * 解密数据
	 * @param customerNo
	 * @param cipherText
	 * @return
	 */
	public String encodeData(String customerNo, String cipherTextStr);
}
