package com.yl.cachecenter.service;

import com.yl.cachecenter.bean.PartnerBatch;
import com.yl.cachecenter.bean.PartnerQueryReq;

/**
 * 合伙人服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public interface PartnerServcie {

	public PartnerBatch query(PartnerQueryReq req);

	public void add(PartnerBatch partnerBatch);

}
