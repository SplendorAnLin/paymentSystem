package com.yl.boss.action;

import javax.annotation.Resource;

import com.yl.boss.entity.SecretKey;
import com.yl.boss.service.SecretKeyService;

/**
 * 秘钥控制器
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月13日
 * @version V1.0.0
 */
public class SecretKeyAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 1L;

	@Resource
	SecretKeyService secretKeyService;
	
	private SecretKey secretKey;
	
	/**
	 * 秘钥新增
	 * @return
	 */
	public String secretKeyAdd(){
		try {
			secretKeyService.secretKeyAdd(secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 根据ID查询单条密钥信息
	 * @return
	 */
	public String secretKeyById(){
		try {
			secretKey = secretKeyService.secretKeyById(Long.parseLong(getHttpRequest().getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 修改密钥信息
	 * @return
	 */
	public String secretKeyUpdate(){
		try {
			secretKeyService.secretKeyUpdate(secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public SecretKey getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}
}
