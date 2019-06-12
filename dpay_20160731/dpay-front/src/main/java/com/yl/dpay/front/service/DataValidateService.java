package com.yl.dpay.front.service;

/**
 * 时间验证服务接口
 * 
 * @author AnLin
 * @since 2016年5月24日
 * @version V1.0.0
 */
public interface DataValidateService {

	public String decryptData(String customerNo, String signData);

	public String encryptData(String customerNo,Object dataObj);
	
	public void checkCustomerAndReferer(String customerNo,String ip,String domain);

}