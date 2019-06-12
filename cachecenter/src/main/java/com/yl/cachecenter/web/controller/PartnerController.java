package com.yl.cachecenter.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yl.cachecenter.bean.PartnerBatch;
import com.yl.cachecenter.bean.PartnerQueryReq;
import com.yl.cachecenter.service.PartnerServcie;

/**
 * 查找商户信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/partner")
public class PartnerController {

	private static final Logger logger = LoggerFactory.getLogger(PartnerController.class);
	@Autowired
	PartnerServcie partnerInfoServcie;

	/**
	 * 根据商编查找
	 */
	@RequestMapping("query")
	@ResponseBody
	public PartnerBatch queryByParner(@Validated @RequestBody PartnerQueryReq req) {
		PartnerBatch partnerBatch = null;
		try {
			partnerBatch = partnerInfoServcie.query(req);
			partnerBatch.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询商户信息出异常了", e);
			if (partnerBatch == null) {
				partnerBatch = new PartnerBatch();
			}
			partnerBatch.setSuccess(false);

		}
		return partnerBatch;
	}

	@RequestMapping("add")
	@ResponseBody
	public PartnerBatch batchAddPartner(@Validated @RequestBody PartnerBatch partnerBatch) {
		PartnerBatch partnerBatchResult = new PartnerBatch();
		try {
			partnerInfoServcie.add(partnerBatch);
			partnerBatchResult.setSuccess(true);
		} catch (Exception e) {
			logger.error("添加商户信息出异常了", e);
			partnerBatch.setSuccess(false);
		}
		return partnerBatchResult;

	}

}
