package com.yl.online.gateway.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.online.gateway.dao.PartnerQueryRequestDao;
import com.yl.online.gateway.mapper.PartnerQueryRequestMapper;
import com.yl.online.gateway.utils.CodeBuilder;
import com.yl.online.model.model.PartnerQueryRequest;

/**
 * 合作方查询请求信息数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Repository("partnerQueryRequestDao")
public class PartnerQueryRequestDaoImpl implements PartnerQueryRequestDao {

	@Resource
	private PartnerQueryRequestMapper partnerQueryRequestMapper;
	
	@Override
	public void create(PartnerQueryRequest PartnerQueryRequest) {
		PartnerQueryRequest.setCode(CodeBuilder.build("PQR"));
		PartnerQueryRequest.setCreateTime(new Date());
		PartnerQueryRequest.setVersion(System.currentTimeMillis());
		partnerQueryRequestMapper.create(PartnerQueryRequest);
	}

	@Override
	public PartnerQueryRequest findByOutOrderId(String tradeOrderCode) {
		return partnerQueryRequestMapper.findByOutOrderId(tradeOrderCode);
	}

}
