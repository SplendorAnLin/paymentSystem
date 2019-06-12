package com.yl.dpay.core.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.dpay.core.entity.CustomerReqInfo;

/**
 * 商户请求信息数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface CustomerReqInfoDao {

	/**
	 * 保存商户请求参数
	 * @param customerReqInfo
	 */
	public void insert(CustomerReqInfo customerReqInfo);

	/**
	 * 查询商户请求记录
	 * @param customerNo
	 * @param customerOrderCode
	 * @param requestType
	 * @return
	 */
	public CustomerReqInfo findByCutomerOrderCode(@Param("customerNo") String customerNo, @Param("customerOrderCode") String customerOrderCode,
			@Param("requestType") String requestType);

}
