package com.yl.online.gateway.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.online.model.model.PartnerQueryRequest;

/**
 * 合作方查询请求信息数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public interface PartnerQueryRequestDao {
	/**
	 * 创建合作方查询请求流水
	 * @param PartnerQueryRequest 合作方查询请求信息
	 */
	void create(PartnerQueryRequest PartnerQueryRequest);
	
	/**
	 * 根据合作方请求流水号查询合作方请求流水
	 * @param requestCode 合作方请求流水号
	 * @return PartnerQueryRequest
	 */
	PartnerQueryRequest findByOutOrderId(@Param("tradeOrderCode")String tradeOrderCode);
}
