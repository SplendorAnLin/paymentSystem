package com.yl.boss.api.interfaces;

import java.util.Map;

import com.lefu.commons.utils.Page;

/**
 * 秘钥远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月20日
 * @version V1.0.0
 */
public interface SecretKeyInterface {

	/**
	 * 根据条件查询秘钥信息
	 * @param params
	 * @return
	 */
	Page secretKeyQuery(Map<String,Object> params);
}
