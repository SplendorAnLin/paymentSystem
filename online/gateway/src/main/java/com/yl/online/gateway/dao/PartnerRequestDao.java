package com.yl.online.gateway.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.online.model.model.PartnerRequest;

/**
 * 合作方请求信息数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public interface PartnerRequestDao {
	/**
	 * 创建合作方请求流水
	 * @param partnerRequest 合作方请求信息
	 */
	void create(PartnerRequest partnerRequest);
	
	/**
	 * 根据合作方请求流水号查询合作方请求流水
	 * @param requestCode 合作方请求流水号
	 * @return PartnerRequest
	 */
	PartnerRequest findByOutOrderId(@Param("outOrderId")String outOrderId,@Param("receiver")String receiver);
}
