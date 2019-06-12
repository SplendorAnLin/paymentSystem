package com.yl.receive.front.service;

/**
 * 数据校验服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public interface DataValidateService {

	public  <T> void validateParams(T object, Class<?>... groups);

	/**
	 * 数据加密
	 * @param partner
	 * @param signData
	 * @return
	 */
	public String encryptData(String partner, String signData);

	/**
	 * 解密数据
	 * @param customerNo
	 * @param cipherText
	 * @return
	 */
	public String decryptData(String customerNo, String cipherText);
	
	/**
	 * @param customerNo
	 * @param amount
	 * @param ip
	 * @param domain
	 */
	public void checkCustomer(String customerNo, double amount, String ip, String domain);
	
	/**
	 * @param customerNo
	 * @param ip
	 * @param domain
	 */
	public void checkCustomer(String customerNo, String ip, String domain);
}
