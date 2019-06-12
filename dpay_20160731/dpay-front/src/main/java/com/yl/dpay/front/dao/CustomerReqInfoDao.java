package com.yl.dpay.front.dao;

import com.yl.dpay.front.model.CustomerReqInfo;

/**
 * 商户请求信息数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月24日
 * @version V1.0.0
 */
public interface CustomerReqInfoDao {

	/**
	 * 保存商户请求参数
	 * @param customerReqInfo
	 */
	public void insert(CustomerReqInfo customerReqInfo);

}
