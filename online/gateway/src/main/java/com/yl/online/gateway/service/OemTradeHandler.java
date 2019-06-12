package com.yl.online.gateway.service;

import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.model.model.PartnerRequest;

public interface OemTradeHandler {

	public String requestOme(PartnerRequest partnerRequest) throws BusinessException;
}
