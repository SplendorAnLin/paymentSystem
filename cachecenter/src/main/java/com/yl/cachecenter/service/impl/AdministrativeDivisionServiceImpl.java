package com.yl.cachecenter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.yl.cachecenter.Constants;
import com.yl.cachecenter.model.AdministrativeDivision;
import com.yl.cachecenter.model.LefuCodeToUnionPayCode;
import com.yl.cachecenter.service.AdministrativeDivisionService;

/**
 * 行政区划码业务实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Service("administrativeDivisionService")
public class AdministrativeDivisionServiceImpl implements AdministrativeDivisionService {
	
	private static final Logger logger = LoggerFactory.getLogger(AdministrativeDivisionServiceImpl.class);
	@Resource
	private JedisPool jedisPool;

	@Override
	public void add(List<AdministrativeDivision> ads) {
		if (ads == null || ads.size() == 0) return;
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			for (AdministrativeDivision ad : ads) {
				int index = ads.indexOf(ad);
				if (index > 1 && index % 1000 == 1) {
					transaction = jedis.multi();
				}
				Map<String, String> map = new HashMap<>();
				map.put("adCode", ad.getAdCode());
				map.put("adName", ad.getAdName());
				transaction.hmset(Constants.ADCODE_KEY_PREFIX + ad.getAdCode() , map);
				if ((index != 0 && index % 1000 == 0) || index == ads.size() - 1) {
					transaction.exec();
				}
			}
		} catch (JedisConnectionException e) {
			logger.error("", e);
			jedisPool.returnBrokenResource(jedis);
			throw e;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	@Override
	public void addLefuCodeToUnionPayCode(List<LefuCodeToUnionPayCode> lps) {
		if (lps == null || lps.size() == 0) return;
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			for (LefuCodeToUnionPayCode lp : lps) {
				int index = lps.indexOf(lp);
				if (index > 1 && index % 1000 == 1) {
					transaction = jedis.multi();
				}
				Map<String, String> map = new HashMap<>();
				map.put("lefuCode", lp.getLefuCode());
				map.put("unionPayCode", lp.getUnionPayCode());
				transaction.hmset(Constants.LEFUCODETOUNIONPAYCODE_KEY_PREFIX + lp.getLefuCode() , map);
				if ((index != 0 && index % 1000 == 0) || index == lps.size() - 1) {
					transaction.exec();
				}
			}
		} catch (JedisConnectionException e) {
			logger.error("", e);
			jedisPool.returnBrokenResource(jedis);
			throw e;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AdministrativeDivision search(String adCode) {
		AdministrativeDivision ad = new AdministrativeDivision();
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			transaction.hgetAll(Constants.ADCODE_KEY_PREFIX + adCode);
			List<Object> execResult = transaction.exec();
			Map<String,String> fieldValues = (Map<String, String>) execResult.get(0);
			ad.setAdCode(fieldValues.get("adCode"));
			ad.setAdName(fieldValues.get("adName"));
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
		return ad;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LefuCodeToUnionPayCode searchLefuCodeToUnionPayCode(String lefuCode) {
		LefuCodeToUnionPayCode lp = new LefuCodeToUnionPayCode();
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction transaction = jedis.multi();
			transaction.hgetAll(Constants.LEFUCODETOUNIONPAYCODE_KEY_PREFIX + lefuCode);
			List<Object> execResult = transaction.exec();
			Map<String,String> fieldValues = (Map<String, String>) execResult.get(0);
			lp.setLefuCode(fieldValues.get("lefuCode"));
			lp.setUnionPayCode(fieldValues.get("unionPayCode"));
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
		return lp;
	}

}
