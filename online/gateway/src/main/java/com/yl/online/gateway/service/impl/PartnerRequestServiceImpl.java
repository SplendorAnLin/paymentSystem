package com.yl.online.gateway.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.dao.PartnerRequestDao;
import com.yl.online.gateway.service.PartnerRequestService;
import com.yl.online.model.model.PartnerRequest;

/**
 * 合作方请求信息业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Service("partnerRequestService")
public class PartnerRequestServiceImpl implements PartnerRequestService {
	@Resource
	private PartnerRequestDao partnerRequestDao;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.online.trade.service.PartnerRequestService#save(com.lefu.online.trade.model.PartnerRequest)
	 */
	@Override
	public void save(PartnerRequest partnerRequest) {
		partnerRequestDao.create(partnerRequest);
	}

	@Override
	public PartnerRequest queryPartnerRequestByOutOrderId(String requestCode,String receiver) {
		if(StringUtils.isBlank(requestCode)) throw new RuntimeException(ExceptionMessages.PARAM_NOT_EXISTS);
		return partnerRequestDao.findByOutOrderId(requestCode,receiver);
	}

}
