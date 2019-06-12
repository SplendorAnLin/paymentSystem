package com.yl.online.gateway.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.online.gateway.dao.PartnerQueryRequestDao;
import com.yl.online.gateway.service.PartnerQueryRequestService;
import com.yl.online.model.model.PartnerQueryRequest;

/**
 * 合作方查询请求信息业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Service
public class PartnerQueryRequestServiceImpl implements PartnerQueryRequestService {
	@Resource
	private PartnerQueryRequestDao partnerQueryRequestDao;
	
	@Override
	public void save(PartnerQueryRequest partnerQueryRequest) {
		partnerQueryRequestDao.create(partnerQueryRequest);
	}

	@Override
	public PartnerQueryRequest queryPartnerQueryRequestByOutOrderId(String requestCode) {
		return null;
	}

}
