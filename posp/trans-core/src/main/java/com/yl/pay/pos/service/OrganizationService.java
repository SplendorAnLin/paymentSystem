package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.Organization;


/**
 * 用户管理类业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface OrganizationService {
	public Organization findByCode(String code);
}
