package com.yl.online.gateway.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.online.gateway.dao.PartnerRequestDao;
import com.yl.online.gateway.mapper.PartnerRequestMapper;
import com.yl.online.gateway.utils.CodeBuilder;
import com.yl.online.model.model.PartnerRequest;

/**
 * 合作方请求信息数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Repository("partnerRequestDao")
public class PartnerRequestDaoImpl implements PartnerRequestDao {
	@Resource
	private PartnerRequestMapper partnerRequestMapper;

	@Override
	public void create(PartnerRequest partnerRequest) {
		partnerRequest.setCode(CodeBuilder.build("PR"));
		partnerRequest.setCreateTime(new Date());
		partnerRequest.setVersion(System.currentTimeMillis());
		partnerRequestMapper.create(partnerRequest);
	}

	@Override
	public PartnerRequest findByOutOrderId(String requestCode,String receiver) {
		return partnerRequestMapper.findByOutOrderId(requestCode, receiver);
	}

}
