package com.yl.cachecenter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.yl.cachecenter.Constants;
import com.yl.cachecenter.bean.PartnerBatch;
import com.yl.cachecenter.bean.PartnerQueryReq;
import com.yl.cachecenter.model.Partner;
import com.yl.cachecenter.service.PartnerServcie;

/**
 * 合伙人服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
@Service("partnerServcie")
public class PartnerServcieImpl implements PartnerServcie {
	private static final Logger logger = LoggerFactory.getLogger(PartnerServcieImpl.class);
	@Resource
	private JedisPool jedisPool;

	public PartnerBatch query(PartnerQueryReq req) {
		PartnerBatch partnerBatch = new PartnerBatch();
		List<Partner> partnerList = new ArrayList<Partner>();

		Jedis jedis = jedisPool.getResource();
		try {
			for (String partnerCode : req.getPartnerCodes()) {
				List<String> fieldValues = jedis.hmget(Constants.PARTNER_KEY_PREFIX + partnerCode, req.getFields());
				Partner partnerInfo = buildPartnerBatch(fieldValues, req.getFields());
				partnerInfo.setCode(partnerCode);
				partnerList.add(partnerInfo);
			}
			partnerBatch.setPartners(partnerList);
			return partnerBatch;
		} catch (JedisConnectionException e) {
			logger.error("", e);
			jedisPool.returnBrokenResource(jedis);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		} finally {
			jedisPool.returnResource(jedis);
		}

	}

	public void add(PartnerBatch partnerBatch) {

		List<Partner> partners = partnerBatch.getPartners();
		if (partners == null || partners.size() == 0) return;
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			for (Partner partnerInfo : partners) {
				Map<String, String> map = new HashMap<>();
				map.put("name", partnerInfo.getName());
				map.put("nickname", partnerInfo.getNickname());
				transaction.hmset(Constants.PARTNER_KEY_PREFIX + partnerInfo.getCode(), map);
			}
			transaction.exec();
		} catch (JedisConnectionException e) {
			logger.error("添加商户信息出错了", e);
			jedisPool.returnBrokenResource(jedis);
			throw e;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	private Partner buildPartnerBatch(List<String> fieldValues, String[] fields) throws Exception {
		Partner partner = new Partner();

		if (fieldValues != null && fieldValues.size() > 0) {
			for (String value : fieldValues) {
				if (value == null) {
					continue;
				}
				String fidledName = fields[fieldValues.indexOf(value)];
				if (fidledName != null) {
					PropertyUtils.setSimpleProperty(partner, fidledName, value);
				}
			}
		}
		return partner;
	}

}
